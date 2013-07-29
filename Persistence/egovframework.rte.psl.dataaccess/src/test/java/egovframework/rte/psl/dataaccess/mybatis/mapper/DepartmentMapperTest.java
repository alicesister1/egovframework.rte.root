package egovframework.rte.psl.dataaccess.mybatis.mapper;

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
import egovframework.rte.psl.dataaccess.mapper.DepartmentMapper;
import egovframework.rte.psl.dataaccess.vo.DeptVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/context-*.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DepartmentMapperTest extends TestBase {

    @Resource(name = "departmentMapper")
    DepartmentMapper departmentMapper;

    @Before
    public void onSetUp() throws Exception {
        // 외부에 sql file 로부터 DB 초기화 (기존 테이블 삭제/생성 및 초기데이터 구축)
        // Spring 의 SimpleJdbcTestUtils 사용,
        // continueOnError 플래그는 true로 설정 - cf.) DDL 이 포함된 경우 rollback 에 유의
        SimpleJdbcTestUtils.executeSqlScript(
            new SimpleJdbcTemplate(dataSource), new ClassPathResource(
                "META-INF/testdata/sample_schema_ddl_" + usingDBMS + ".sql"), true);
    }

    public DeptVO makeVO() {
    	DeptVO vo = new DeptVO();
        vo.setDeptNo(new BigDecimal(10));
        vo.setDeptName("총무부");
        vo.setLoc("본사");
        return vo;
    }

    public void checkResult(DeptVO vo, DeptVO resultVO) {
        assertNotNull(resultVO);
        assertEquals(vo.getDeptNo(), resultVO.getDeptNo());
        assertEquals(vo.getDeptName(), resultVO.getDeptName());
        assertEquals(vo.getLoc(), resultVO.getLoc());
    }

    @Test
    public void testInsert() throws Exception {
    	DeptVO vo = makeVO();

        // insert
        departmentMapper.insertDepartment(vo);

        // select
        DeptVO resultVO = departmentMapper.selectDepartment(vo.getDeptNo());

        // check
        checkResult(vo, resultVO);
    }

    @Test
    public void testUpdate() throws Exception {
    	DeptVO vo = makeVO();

        // insert
    	departmentMapper.insertDepartment(vo);

        // data change
        vo.setDeptName("개발부");
        vo.setLoc("연구소");

        // update
        int effectedRows = departmentMapper.updateDepartment(vo);
        assertEquals(1, effectedRows);

        // select
        DeptVO resultVO = departmentMapper.selectDepartment(vo.getDeptNo());

        // check
        checkResult(vo, resultVO);
    }

    @Test
    public void testDelete() throws Exception {
    	DeptVO vo = makeVO();

        // insert
    	departmentMapper.insertDepartment(vo);

        // delete
        int effectedRows = departmentMapper.deleteDepartment(vo.getDeptNo());
        assertEquals(1, effectedRows);

        // select
        DeptVO resultVO = departmentMapper.selectDepartment(vo.getDeptNo());

        // null 이어야 함
        assertNull(resultVO);
    }
}
