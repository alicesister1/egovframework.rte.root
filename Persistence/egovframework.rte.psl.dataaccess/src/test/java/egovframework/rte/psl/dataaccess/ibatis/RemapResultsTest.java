package egovframework.rte.psl.dataaccess.ibatis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.dataaccess.TestBase;
import egovframework.rte.psl.dataaccess.dao.MapTypeDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/context-*.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class RemapResultsTest extends TestBase {

    @Resource(name = "mapTypeDAO")
    MapTypeDAO mapTypeDAO;

    @Before
    public void onSetUp() throws Exception {
        SimpleJdbcTestUtils.executeSqlScript(
            new SimpleJdbcTemplate(dataSource), new ClassPathResource(
                "META-INF/testdata/sample_schema_ddl_" + usingDBMS + ".sql"),
            true);

        // init data
        SimpleJdbcTestUtils.executeSqlScript(
            new SimpleJdbcTemplate(dataSource), new ClassPathResource(
                "META-INF/testdata/sample_schema_initdata_" + usingDBMS
                    + ".sql"), true);
    }

    @SuppressWarnings("unchecked")
    @Test
    // @ExpectedException(BadSqlGrammarException.class)
    // tibero 인 경우는 UncategorizedSQLException 이 되돌려지므로
    // 메서드 내부에서 try ~ catch 로 처리토록 변경
    public void testReplaceTextAllQueryExpectedException() throws Exception {

        try {
            // selectQuery
            Map<String, Object> map = new HashMap<String, Object>();
            StringBuilder selectQuery = new StringBuilder();
            selectQuery.append("select * from DEPT");

            map.put("selectQuery", selectQuery.toString());

            // select
            List<Map> resultList =
                mapTypeDAO.getSqlMapClientTemplate().queryForList(
                    "selectUsingReplacedAllQuery", map);

            assertNotNull(resultList);
            assertEquals(4, resultList.size());
            assertTrue(resultList.get(0).containsKey("deptNo"));

            map.clear();
            selectQuery = new StringBuilder();
            selectQuery.append("select * from DEPT ");
            selectQuery.append("where DEPT_NAME like '%ES%' ");
            selectQuery.append("order by DEPT_NO DESC ");

            map.put("selectQuery", selectQuery.toString());

            // select
            // 위에서 동일한 resultset metadata 가 사용되는
            // 경우는(table과 select 절이 같은 경우)
            // replaced text 처리의 쿼리 사용에 문제가 없음.
            // cf.) resultset metadata 는 caching 되고 있음에
            // 유의!
            resultList =
                mapTypeDAO.getSqlMapClientTemplate().queryForList(
                    "selectUsingReplacedAllQuery", map);

            assertNotNull(resultList);
            // 20,'RESEARCH','DALLAS' -- R'ES'EARCH
            // 30,'SALES','CHICAGO' -- SAL'ES'
            assertEquals(2, resultList.size());
            assertTrue(resultList.get(0).containsKey("deptNo"));

            map.clear();
            selectQuery = new StringBuilder();
            selectQuery.append("select * from EMP ");

            map.put("selectQuery", selectQuery.toString());

            // select
            // 위에서 resultset metadata 가 달라지는 경우
            // replaced text 처리의 쿼리 사용시 최초에 caching 된
            // resultset metadata 에 현재 조회하는 정보가 없으므로 에러
            // 발생함!
            resultList =
                mapTypeDAO.getSqlMapClientTemplate().queryForList(
                    "selectUsingReplacedAllQuery", map);

            fail("이 라인이 수행될 수 없습니다.");
        } catch (BadSqlGrammarException be) {
            assertNotNull(be);
        } catch (UncategorizedSQLException ue) {
            // tibero 인 경우 Spring 에서
            // UncategorizedSQLException <--
            // NestedSQLException <-- TbSQLException 으로
            // 처리됨
            assertNotNull(ue);
//            assertTrue(ue.getCause().getCause() instanceof TbSQLException);
        } catch (Exception e) {
            e.printStackTrace();
            fail("기대한 exception 이 아닙니다.");
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReplaceTextRemapResultsAllQuery() throws Exception {

        // selectQuery
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("select * from DEPT");

        map.put("selectQuery", selectQuery.toString());

        // select
        List<Map> resultList =
            mapTypeDAO.getSqlMapClientTemplate().queryForList(
                "selectUsingReplacedAllQueryUsingRemapResults", map);

        assertNotNull(resultList);
        assertEquals(4, resultList.size());
        assertTrue(resultList.get(0).containsKey("deptNo"));

        map.clear();
        selectQuery = new StringBuilder();
        selectQuery.append("select * from EMP ");

        map.put("selectQuery", selectQuery.toString());

        // select
        // 위에서 resultset metadata 가 달라지는 경우라도
        // remapResults="true" 로 설정하여
        // resultset metadata 를 caching 하지 않으므로 에러 발생
        // 않음
        resultList =
            mapTypeDAO.getSqlMapClientTemplate().queryForList(
                "selectUsingReplacedAllQueryUsingRemapResults", map);

        assertNotNull(resultList);
        assertEquals(14, resultList.size());
        assertTrue(resultList.get(0).containsKey("empNo"));
    }
}
