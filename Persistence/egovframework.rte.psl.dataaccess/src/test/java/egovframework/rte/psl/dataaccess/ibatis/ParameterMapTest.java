package egovframework.rte.psl.dataaccess.ibatis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
import egovframework.rte.psl.dataaccess.dao.EmpDAO;
import egovframework.rte.psl.dataaccess.vo.EmpVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/context-*.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class ParameterMapTest extends TestBase {

    @Resource(name = "empDAO")
    EmpDAO empDAO;

    @Before
    public void onSetUp() throws Exception {
        // 외부에 sql file 로부터 DB 초기화 (기존 테이블 삭제/생성 및
        // 초기데이터 구축)
        // Spring 의 SimpleJdbcTestUtils 사용,
        // continueOnError 플래그는 true로 설정 - cf.) DDL 이
        // 포함된 경우 rollback 에 유의
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

    public EmpVO makeVO() throws ParseException {
        EmpVO vo = new EmpVO();
        vo.setEmpNo(new BigDecimal(9000));
        vo.setEmpName("test Emp");
        vo.setJob("test Job");
        // 7839,'KING','PRESIDENT'
        vo.setMgr(new BigDecimal(7839));
        SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        vo.setHireDate(sdf.parse("2009-02-09"));
        // mysql 에서는 소숫점 자릿수 만큼 .00 이 달려 나와 테스트 편의상 소숫점
        // 자리수가 없도록 칼럼 선언 하였음.
        if (isMysql) {
            vo.setSal(new BigDecimal("12345"));
            vo.setComm(new BigDecimal(100));
        } else {
            vo.setSal(new BigDecimal("12345.67"));
            vo.setComm(new BigDecimal(100.00));
        }
        // 10,'ACCOUNTING','NEW YORK'
        vo.setDeptNo(new BigDecimal(10));
        return vo;
    }

    public void checkResult(EmpVO vo, EmpVO resultVO) {
        assertNotNull(resultVO);
        assertEquals(vo.getEmpNo(), resultVO.getEmpNo());
        assertEquals(vo.getEmpName(), resultVO.getEmpName());
        assertEquals(vo.getJob(), resultVO.getJob());
        assertEquals(vo.getMgr(), resultVO.getMgr());
        assertEquals(vo.getHireDate(), resultVO.getHireDate());
        assertEquals(vo.getSal(), resultVO.getSal());
        assertEquals(vo.getComm(), resultVO.getComm());
        assertEquals(vo.getDeptNo(), resultVO.getDeptNo());
    }

    @Test
    public void testParameterMapInsert() throws Exception {
        EmpVO vo = makeVO();

        // insert
        empDAO.insertEmp("insertEmpUsingParameterMap", vo);

        // select
        EmpVO resultVO = empDAO.selectEmp("selectEmp", vo);

        // check
        checkResult(vo, resultVO);
    }

    @Test
    public void testParameterMapInsertWithNullValue() throws Exception {
        EmpVO vo = new EmpVO();
        // key 설정
        vo.setEmpNo(new BigDecimal(9000));

        // parameterMap nullValue test
        vo.setEmpName("blank");
        vo.setJob("");
        // cf.) -99999.99 는 NumberFormatException 임을
        // 확인하였음!
        vo.setComm(new BigDecimal("-99999"));

        // insert
        empDAO.insertEmp("insertEmpUsingParameterMap", vo);

        // select
        EmpVO resultVO = empDAO.selectEmp("selectEmp", vo);

        // check
        assertNotNull(resultVO);
        assertEquals(vo.getEmpNo(), resultVO.getEmpNo());
        // parameterMap 설정에서 nullValue="blank" .. 에 따라
        // 해당값이 null 로 입력되었을 것임
        assertNull(resultVO.getEmpName());
        assertNull(resultVO.getJob());
        assertNull(resultVO.getComm());
    }

}
