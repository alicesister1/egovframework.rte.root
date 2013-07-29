package egovframework.rte.psl.dataaccess.ibatis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import javax.annotation.Resource;

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
import egovframework.rte.psl.dataaccess.dao.DeptDAO;
import egovframework.rte.psl.dataaccess.vo.DeptVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/context-*.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class WithoutMappingCUDTest extends TestBase {

    @Resource(name = "deptDAO")
    DeptDAO deptDAO;

    @Before
    public void onSetUp() throws Exception {
        SimpleJdbcTestUtils.executeSqlScript(
            new SimpleJdbcTemplate(dataSource), new ClassPathResource(
                "META-INF/testdata/sample_schema_ddl_" + usingDBMS + ".sql"),
            true);
    }

    public DeptVO makeVO() {
        DeptVO vo = new DeptVO();
        vo.setDeptNo(new BigDecimal(90));
        vo.setDeptName("test Dept");
        vo.setLoc("test Loc");
        return vo;
    }

    public void checkResult(DeptVO vo, DeptVO resultVO) {
        assertNotNull(resultVO);
        assertEquals(vo.getDeptNo(), resultVO.getDeptNo());
        assertEquals(vo.getDeptName(), resultVO.getDeptName());
        assertEquals(vo.getLoc(), resultVO.getLoc());
    }

    @Test
    public void testSimpleInsert() throws Exception {
        DeptVO vo = makeVO();

        // insert
        deptDAO.insertDept("insertDeptSimple", vo);

        // select
        DeptVO resultVO =
            deptDAO.selectDept("selectDeptSimpleUsingResultClass", vo);

        // check
        checkResult(vo, resultVO);
    }

    @Test
    public void testSimpleUpdate() throws Exception {
        DeptVO vo = makeVO();

        // insert
        deptDAO.insertDept("insertDeptSimple", vo);

        // data change
        vo.setDeptName("upd Dept");
        vo.setLoc("upd loc");

        // update
        int effectedRows = deptDAO.updateDept("updateDeptSimple", vo);
        assertEquals(1, effectedRows);

        // select
        DeptVO resultVO =
            deptDAO.selectDept("selectDeptSimpleUsingResultClass", vo);

        // check
        checkResult(vo, resultVO);
    }

    @Test
    public void testSimpleDelete() throws Exception {
        DeptVO vo = makeVO();

        // insert
        deptDAO.insertDept("insertDeptSimple", vo);

        // delete
        int effectedRows = deptDAO.deleteDept("deleteDeptSimple", vo);
        assertEquals(1, effectedRows);

        // select
        DeptVO resultVO =
            deptDAO.selectDept("selectDeptSimpleUsingResultClass", vo);

        // null 이어야 함
        assertNull(resultVO);
    }
}
