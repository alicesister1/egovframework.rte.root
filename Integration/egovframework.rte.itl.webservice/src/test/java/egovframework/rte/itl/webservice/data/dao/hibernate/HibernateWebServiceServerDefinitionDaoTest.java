package egovframework.rte.itl.webservice.data.dao.hibernate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.sql.DataSource;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import egovframework.rte.itl.webservice.data.WebServiceServerDefinition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/egovframework/rte/itl/webservice/data/dao/hibernate/context.xml")
@TransactionConfiguration(defaultRollback=true)
@Transactional(readOnly=false)
public class HibernateWebServiceServerDefinitionDaoTest
{
    @Autowired
    private HibernateWebServiceServerDefinitionDao dao;

    @Autowired
    private DataSource dataSource;
    
    @Before
    public void before() throws Exception
    {
        ReplacementDataSet dataSet = new ReplacementDataSet(new FlatXmlDataSet(
                ResourceUtils.getFile("classpath:egovframework/rte/itl/webservice/data/dao/hibernate/dataset.xml")));
        dataSet.addReplacementObject("[null]", null);
        
        IDatabaseConnection connection = new SpringDatabaseDataSourceConnection(dataSource);
        
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
    }
    
    @Test
    public void testReadSucceeds() throws Exception
    {
        WebServiceServerDefinition wsd = dao.getWebServiceServerDefinition("3");
        
        assertNotNull(wsd);
        assertTrue(wsd.isValid());
    }

    @Test
    public void testReadFails() throws Exception
    {
        WebServiceServerDefinition wsd = dao.getWebServiceServerDefinition("1");
        
        assertNull(wsd);
    }
}
