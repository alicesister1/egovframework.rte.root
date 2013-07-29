package egovframework.rte.psl.dataaccess.mybatis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.ListOrderedMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.dataaccess.TestBase;
import egovframework.rte.psl.dataaccess.dao.MapTypeMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/context-*.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class MapTypeParameterTest extends TestBase {

    @Resource(name = "mapTypeMapper")
    MapTypeMapper mapTypeMapper;

    @Before
    public void onSetUp() throws Exception {
        SimpleJdbcTestUtils.executeSqlScript(
            new SimpleJdbcTemplate(dataSource), new ClassPathResource(
                "META-INF/testdata/sample_schema_ddl_" + usingDBMS + ".sql"),  true);
    }

    public Map<String, Object> makeMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deptNo", new BigDecimal(90));
        map.put("deptName", "test Dept");
        map.put("loc", "test Loc");
        return map;
    }

    public void checkResult(Map<String, Object> map,
            Map<String, Object> resultMap) {
        assertNotNull(resultMap);

        // mysql 인 경우 select 한 칼럼 alias 대소문자가 보존됨
        if (isMysql) {
            assertEquals(map.get("deptNo"), resultMap.get("deptNo"));
            assertEquals(map.get("deptName"), resultMap.get("deptName"));
            assertEquals(map.get("loc"), resultMap.get("loc"));
        } else {
            // 일반 resultType을 HashMap 으로 명시하면 key가 대문자로 전달됨에 유의! (ex. deptNo X -> DEPTNO)
            assertEquals(map.get("deptNo"), resultMap.get("DEPTNO"));
            assertEquals(map.get("deptName"), resultMap.get("DEPTNAME"));
            assertEquals(map.get("loc"), resultMap.get("LOC"));
        }
    }

    @Test
    public void testMapTypeInsert() throws Exception {
        Map<String, Object> map = makeMap();

        // insert
        mapTypeMapper.insertDept("insertDeptUsingMap", map);

        // select
        Map<String, Object> resultMap = mapTypeMapper.selectDept("selectDeptUsingMap", map);

        // check
        checkResult(map, resultMap);
    }

    @Test
    public void testMapTypeUpdate() throws Exception {
        Map<String, Object> map = makeMap();

        // insert
        mapTypeMapper.insertDept("insertDeptUsingMap", map);

        // data change
        map.put("deptName", "upd Dept");
        map.put("loc", "upd loc");

        // update
        int effectedRows = mapTypeMapper.updateDept("updateDeptUsingMap", map);
        assertEquals(1, effectedRows);

        // select
        Map<String, Object> resultMap =
            mapTypeMapper.selectDept("selectDeptUsingMap", map);

        // check
        checkResult(map, resultMap);
    }

    @Test
    public void testMapTypeDelete() throws Exception {
        Map<String, Object> map = makeMap();

        // insert
        mapTypeMapper.insertDept("insertDeptUsingMap", map);

        // delete
        int effectedRows = mapTypeMapper.deleteDept("deleteDeptUsingMap", map);
        assertEquals(1, effectedRows);

        // select
        Map<String, Object> resultMap =
            mapTypeMapper.selectDept("selectDeptUsingMap", map);

        // null 이어야 함
        assertNull(resultMap);
    }

    @Test
    public void testEgovMapTest() throws Exception {
        Map<String, Object> map = makeMap();

        // insert
        mapTypeMapper.insertDept("insertDeptUsingMap", map);

        // select
        Map<String, Object> resultMap =
            mapTypeMapper.selectDept("selectDeptUsingEgovMap", map);

        // check
        assertNotNull(resultMap);
        // resultType을 EgovMap으로 명시하면
        // key 를 camel case 로 변환하여 전달해줌
        // EgovMap 은 ListOrderedMap 을 extends 하고 있음
        assertTrue(resultMap instanceof ListOrderedMap);
        assertEquals(map.get("deptNo"), resultMap.get("deptNo"));
        assertEquals(map.get("deptName"), resultMap.get("deptName"));
        assertEquals(map.get("loc"), resultMap.get("loc"));
    }
}
