package egovframework.rte.itl.integration.type.support;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import egovframework.rte.itl.integration.metadata.RecordTypeFieldDefinition;
import egovframework.rte.itl.integration.metadata.RecordTypeDefinition;
import egovframework.rte.itl.integration.metadata.dao.RecordTypeDefinitionDao;
import egovframework.rte.itl.integration.type.CircularInheritanceException;
import egovframework.rte.itl.integration.type.ListType;
import egovframework.rte.itl.integration.type.NoSuchTypeException;
import egovframework.rte.itl.integration.type.PrimitiveType;
import egovframework.rte.itl.integration.type.RecordType;
import egovframework.rte.itl.integration.type.Type;

public class TypeLoaderUsingMetadataTest
{
    private static TypeLoaderUsingMetadata typeLoader;

    @BeforeClass
    public static void beforeClass() throws Exception
    {
        
        RecordTypeDefinitionDao recordTypeDefinitionDao =
            new RecordTypeDefinitionDao()
            {
                @SuppressWarnings("serial")
                private final RecordTypeDefinition header =
                    new RecordTypeDefinition("header", "header", null,
                            new HashMap<String, RecordTypeFieldDefinition>()
                            {{
//                                put("serviceId", new RecordTypeFieldDefinition("serviceId", "string"));
                                put("serviceId", new RecordTypeFieldDefinition("string"));
                            }}
                    );
                
                @SuppressWarnings("serial")
                private final RecordTypeDefinition recordJuminCheck =
                    new RecordTypeDefinition("recordJuminCheck", "recordJuminCheck", null,
                            new HashMap<String, RecordTypeFieldDefinition>()
                            {{
//                                put("juminNo", new RecordTypeFieldDefinition("juminNo", "string"));
//                                put("name", new RecordTypeFieldDefinition("name", "string"));
//                                put("children", new RecordTypeFieldDefinition("children", "recordJuminCheckList"));
                                put("juminNo", new RecordTypeFieldDefinition("string"));
                                put("name", new RecordTypeFieldDefinition("string"));
                                put("children", new RecordTypeFieldDefinition("recordJuminCheckList"));
                            }}
                    );
            
                @SuppressWarnings("serial")
                private final RecordTypeDefinition recordJuminCheckList =
                    new RecordTypeDefinition("recordJuminCheckList", "recordJuminCheckList", null,
                            new HashMap<String, RecordTypeFieldDefinition>()
                            {{
//                                put("list", new RecordTypeFieldDefinition("list", "recordJuminCheck[]"));
                                put("list", new RecordTypeFieldDefinition("recordJuminCheck[]"));
                            }}
                    );
                
                @SuppressWarnings("serial")
                private final RecordTypeDefinition messageJuminCheck =
                    new RecordTypeDefinition("messageJuminCheck", "messageJuminCheck", header,
                            new HashMap<String, RecordTypeFieldDefinition>()
                            {{
//                                put("body", new RecordTypeFieldDefinition("body", "recordJuminCheck"));
                                put("body", new RecordTypeFieldDefinition("recordJuminCheck"));
                            }}
                    );
                
                @SuppressWarnings("serial")
                private final RecordTypeDefinition messageJuminCheckList =
                    new RecordTypeDefinition("messageJuminCheckList", "messageJuminChecklist", header,
                            new HashMap<String, RecordTypeFieldDefinition>()
                            {{
//                                put("serviceId", new RecordTypeFieldDefinition("serviceId", "integer"));
//                                put("body", new RecordTypeFieldDefinition("body", "recordJuminCheckList"));
                                put("serviceId", new RecordTypeFieldDefinition("integer"));
                                put("body", new RecordTypeFieldDefinition("recordJuminCheckList"));
                            }}
                    );
    
                @SuppressWarnings("serial")
                private final RecordTypeDefinition messageCircularA =
                    new RecordTypeDefinition("messageCircularA", "messageCircularA", null,
                            new HashMap<String, RecordTypeFieldDefinition>()
                            {{
//                                put("data", new RecordTypeFieldDefinition("data", "integer"));
                                put("data", new RecordTypeFieldDefinition("integer"));
                            }}
                    );
                
                @SuppressWarnings("serial")
                private final RecordTypeDefinition messageCircularB =
                    new RecordTypeDefinition("messageCircularB", "messageCircularB", messageCircularA,
                            new HashMap<String, RecordTypeFieldDefinition>()
                            {{
//                                put("data", new RecordTypeFieldDefinition("data", "integer"));
                                put("data", new RecordTypeFieldDefinition("integer"));
                            }}
                    );
    
                @SuppressWarnings("serial")
                private final RecordTypeDefinition messageCircularC =
                    new RecordTypeDefinition("messageCircularC", "messageCircularC", messageCircularA,
                            new HashMap<String, RecordTypeFieldDefinition>()
                            {{
//                                put("data", new RecordTypeFieldDefinition("data", "integer"));
                                put("data", new RecordTypeFieldDefinition("integer"));
                            }}
                    );
    
                @SuppressWarnings("serial")
                private final Map<String, RecordTypeDefinition> map =
                    new HashMap<String, RecordTypeDefinition>()
                    {{
                        put(header.getId(), header);
                        put(recordJuminCheck.getId(), recordJuminCheck);
                        put(recordJuminCheckList.getId(), recordJuminCheckList);
                        put(messageJuminCheck.getId(), messageJuminCheck);
                        put(messageJuminCheckList.getId(),
                                messageJuminCheckList);
                        messageCircularA.setParent(messageCircularC);
                        put(messageCircularA.getId(), messageCircularA);
                        put(messageCircularB.getId(), messageCircularB);
                        put(messageCircularC.getId(), messageCircularC);
                    }};
                
                public RecordTypeDefinition getRecordTypeDefinition(String id)
                {
                    return map.get(id);
                }
            };
        
        typeLoader = new TypeLoaderUsingMetadata();
        typeLoader.setRecordTypeDefinitionDao(recordTypeDefinitionDao);
    }
    
    @Test
    public void testPrimitiveType()
    {
        Assert.assertEquals(PrimitiveType.BOOLEAN, typeLoader.getType("boolean"));
        Assert.assertEquals(PrimitiveType.STRING, typeLoader.getType("string"));
        Assert.assertEquals(PrimitiveType.BYTE, typeLoader.getType("byte"));
        Assert.assertEquals(PrimitiveType.SHORT, typeLoader.getType("short"));
        Assert.assertEquals(PrimitiveType.INTEGER, typeLoader.getType("integer"));
        Assert.assertEquals(PrimitiveType.LONG, typeLoader.getType("long"));
        Assert.assertEquals(PrimitiveType.BIGINTEGER, typeLoader.getType("biginteger"));
        Assert.assertEquals(PrimitiveType.FLOAT, typeLoader.getType("float"));
        Assert.assertEquals(PrimitiveType.DOUBLE, typeLoader.getType("double"));
        Assert.assertEquals(PrimitiveType.BIGDECIMAL, typeLoader.getType("bigdecimal"));
        Assert.assertEquals(PrimitiveType.CALENDAR, typeLoader.getType("calendar"));
    }
    
    @Test
    public void testSimpleRecord()
    {
        String id = "header";
        Type type = typeLoader.getType(id);
        Assert.assertEquals(id, type.getId());
        Assert.assertEquals(RecordType.class, type.getClass());
        Assert.assertEquals(PrimitiveType.STRING, ((RecordType)type).getFieldType("serviceId"));
    }
    
    @Test
    public void testComplexRecord()
    {
        String id = "messageJuminCheck";
        Type type = typeLoader.getType(id);
        Assert.assertEquals(id, type.getId());
        Assert.assertEquals(RecordType.class, type.getClass());
        RecordType recordType = (RecordType)type;
        Assert.assertEquals(PrimitiveType.STRING, recordType.getFieldType("serviceId"));
        Assert.assertEquals(typeLoader.getType("recordJuminCheck"), recordType.getFieldType("body"));
    }

    @Test
    public void testInheritance()
    {
        String id = "messageJuminCheckList";
        Type type = typeLoader.getType(id);
        Assert.assertEquals(id, type.getId());
        Assert.assertEquals(RecordType.class, type.getClass());
        RecordType recordType = (RecordType)type;
        Assert.assertEquals(PrimitiveType.INTEGER, recordType.getFieldType("serviceId"));
    }
    
    @Test
    public void testCircularReference()
    {
        String id = "messageJuminCheckList";
        Type type = typeLoader.getType(id);
        Assert.assertEquals(id, type.getId());
        Assert.assertEquals(RecordType.class, type.getClass());
        RecordType recordType = (RecordType)type;
        Type bodyFieldType = recordType.getFieldType("body");
        Assert.assertEquals(RecordType.class, bodyFieldType.getClass());
        RecordType bodyRecordType = (RecordType)bodyFieldType;
        Type listFieldType = bodyRecordType.getFieldType("list");
        Assert.assertEquals(ListType.class, listFieldType.getClass());
        Assert.assertEquals(typeLoader.getType("recordJuminCheck"), ((ListType)listFieldType).getElementType());
        Type elementType = ((ListType)listFieldType).getElementType();
        Assert.assertEquals(RecordType.class, elementType.getClass());
        RecordType elementRecordType = (RecordType)elementType;
        Assert.assertEquals(bodyFieldType, elementRecordType.getFieldType("children"));
    }
    
    @Test
    public void testNoSuchTypeException()
    {
        try
        {
            typeLoader.getType("noMessage");
            Assert.fail();
        }
        catch (NoSuchTypeException e)
        {
            // Success
        }
        catch (Exception e)
        {
            Assert.fail();
        }
    }
    
    @Test
    public void testCircularInheritanceException()
    {
        try
        {
            typeLoader.getType("messageCircularA");
            Assert.fail();
        }
        catch (CircularInheritanceException e)
        {
            // Success
        }
        catch (Exception e)
        {
            Assert.fail();
        }
    }
    
}
