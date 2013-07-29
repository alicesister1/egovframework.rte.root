package egovframework.rte.psl.dataaccess.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("mapTypeDAO")
public class MapTypeDAO extends EgovAbstractDAO {

    public void insertDept(String queryId, Map<String, Object> map) {
        getSqlMapClientTemplate().insert(queryId, map);
    }

    public int updateDept(String queryId, Map<String, Object> map) {
        return getSqlMapClientTemplate().update(queryId, map);
    }

    public int deleteDept(String queryId, Map<String, Object> map) {
        return getSqlMapClientTemplate().delete(queryId, map);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> selectDept(String queryId,
            Map<String, Object> map) {
        return (Map<String, Object>) getSqlMapClientTemplate().queryForObject(
            queryId, map);
    }

    @SuppressWarnings("unchecked")
    public List<Map> selectDeptList(String queryId, Map searchMap) {
        return getSqlMapClientTemplate().queryForList(queryId, searchMap);
    }
}
