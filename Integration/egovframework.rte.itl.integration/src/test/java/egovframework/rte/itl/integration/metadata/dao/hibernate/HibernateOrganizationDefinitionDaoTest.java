package egovframework.rte.itl.integration.metadata.dao.hibernate;

import static org.junit.Assert.assertEquals;
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

import egovframework.rte.itl.integration.metadata.OrganizationDefinition;
import egovframework.rte.itl.integration.metadata.ServiceDefinition;
import egovframework.rte.itl.integration.metadata.SystemDefinition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/egovframework/rte/itl/integration/metadata/dao/hibernate/context.xml")
@TransactionConfiguration(defaultRollback=true)
@Transactional(readOnly=false)
public class HibernateOrganizationDefinitionDaoTest
{

    @Autowired
    private HibernateOrganizationDefinitionDao dao;

    @Autowired
    private DataSource dataSource;

    @Before
    public void before() throws Exception
    {
        ReplacementDataSet dataSet = new ReplacementDataSet(new FlatXmlDataSet(
                ResourceUtils.getFile("classpath:egovframework/rte/itl/integration/metadata/dao/hibernate/dataset.xml")));
        dataSet.addReplacementObject("[null]", null);
        
        IDatabaseConnection connection = new SpringDatabaseDataSourceConnection(dataSource);
        
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
    }

    @Test
    public void testReadSucceeds() throws Exception
    {
        OrganizationDefinition organization00000000 =
            dao.getOrganizationDefinition("00000000");

        assertNotNull(organization00000000);
        assertTrue(organization00000000.isValid());

        assertEquals("00000000", organization00000000.getId());
        assertEquals("Organization A", organization00000000.getName());
        assertEquals(2, organization00000000.getSystems().size());
        
        SystemDefinition systemA0 = organization00000000.getSystemDefinition("00000000");
        assertNotNull(systemA0);
        assertTrue(systemA0.isValid());
        assertEquals("00000000", systemA0.getId());
        assertEquals("System A0", systemA0.getName());
        assertEquals(true, systemA0.isStandard());
        assertEquals(1, systemA0.getServices().size());
        assertEquals(organization00000000, systemA0.getOrganization());

        ServiceDefinition serviceA0_0 = systemA0.getServiceDefinition("00000000");
        assertNotNull(serviceA0_0);
        assertTrue(serviceA0_0.isValid());
        assertEquals("00000000", serviceA0_0.getId());
        assertEquals("Service A0-0", serviceA0_0.getName());
        assertEquals("M1", serviceA0_0.getRequestMessageTypeId());
        assertEquals("M2", serviceA0_0.getResponseMessageTypeId());
        assertNull(serviceA0_0.getServiceProviderBeanId());
        assertEquals(systemA0, serviceA0_0.getSystem());
        
        OrganizationDefinition organization00000001 =
            dao.getOrganizationDefinition("00000001");
        
        assertNotNull(organization00000001);
        assertTrue(organization00000001.isValid());
        assertEquals("00000001", organization00000001.getId());
        assertEquals("Organization B", organization00000001.getName());
        assertEquals(2, organization00000000.getSystems().size());
    }

    @Test
    public void testReadFails() throws Exception
    {
        OrganizationDefinition organization00000002 =
            dao.getOrganizationDefinition("00000002");
        
        assertNull(organization00000002);
    }
}
