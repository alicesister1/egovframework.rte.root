package egovframework.rte.psl.dataaccess.dao;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.vo.DeptIncludesEmpListVO;
import egovframework.rte.psl.dataaccess.vo.DeptVO;
import egovframework.rte.psl.dataaccess.vo.EmpDeptSimpleCompositeVO;
import egovframework.rte.psl.dataaccess.vo.EmpExtendsDeptVO;
import egovframework.rte.psl.dataaccess.vo.EmpIncludesDeptVO;
import egovframework.rte.psl.dataaccess.vo.EmpIncludesEmpListVO;
import egovframework.rte.psl.dataaccess.vo.EmpIncludesMgrVO;
import egovframework.rte.psl.dataaccess.vo.EmpVO;

@Repository("empBatchMapper")
public class EmpBatchMapper extends EgovAbstractMapper {
	
	@Resource(name = "batchSqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

    public void insertEmp(String queryId, EmpVO vo) {
        insert(queryId, vo);
    }

    public int insertEmpUsingSelectKey(String queryId, EmpVO vo) {
		return insert(queryId, vo);
    }

    public Integer batchInsertEmp(String queryId, List<EmpVO> list) {
    	 Iterator<EmpVO> itr = list.iterator();

    	 int count = 0;
    	 
    	 while (itr.hasNext()) {
			count +=  insert(queryId, itr.next());
         }

    	 return count;
    }

    public EmpVO selectEmp(String queryId, EmpVO vo) {
        return (EmpVO) selectByPk(queryId, vo);
    }

    @SuppressWarnings("unchecked")
	public List<EmpVO> selectEmpList(String queryId, EmpVO vo) {
        return list(queryId, vo);
    }
    
	public List selectList(String statementName, int skipResults, int maxResults) {
		RowBounds rowBounds = new RowBounds(skipResults, maxResults);
        return getSqlSession().selectList(statementName, "EmpVO", rowBounds);
	}

    public EmpExtendsDeptVO selectEmpExtendsDept(String queryId, EmpVO vo) {
        return (EmpExtendsDeptVO) selectByPk(queryId, vo);
    }

	public EmpDeptSimpleCompositeVO selectEmpDeptSimpleComposite(String queryId, EmpVO vo) {
        return (EmpDeptSimpleCompositeVO) selectByPk(queryId, vo);
    }

	public EmpIncludesDeptVO selectEmpDeptComplexProperties(String queryId, EmpVO vo) {
		return (EmpIncludesDeptVO) selectByPk(queryId, vo);
	}

	public DeptIncludesEmpListVO selectDeptEmpListComplexProperties(String queryId, DeptVO vo) {
		return (DeptIncludesEmpListVO) selectByPk(queryId, vo);
    }

    @SuppressWarnings("unchecked")
	public List<DeptIncludesEmpListVO> selectDeptEmpListComplexPropertiesList(String queryId, DeptVO vo) {
		return list(queryId, vo);
    }

	public EmpIncludesMgrVO selectEmpMgrHierarchy(String queryId, EmpVO vo) {
		return (EmpIncludesMgrVO) selectByPk(queryId, vo);
	}

	public EmpIncludesEmpListVO selectEmpIncludesEmpList(String queryId, EmpVO vo) {
		return (EmpIncludesEmpListVO) selectByPk(queryId, vo);
	}
}
