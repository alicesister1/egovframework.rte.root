package egovframework.rte.fdl.xml.ehcache;


import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

import org.jdom.*;
import org.jdom.input.*;

import egovframework.rte.fdl.xml.EgovConcreteDOMFactory;
import egovframework.rte.fdl.xml.EgovConcreteSAXFactory;
import egovframework.rte.fdl.xml.EgovDOMValidatorService;
import egovframework.rte.fdl.xml.EgovSAXValidatorService;
import egovframework.rte.fdl.xml.SharedObject;
import egovframework.rte.fdl.xml.XmlLog;
import egovframework.rte.fdl.xml.exception.UnsupportedException;

import java.net.*;

/**
 * CategoryControllerTest is TestCase of CategoryController
 * 
 * @author Byunghun Woo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/context-xmltest.xml" })

public class CacheXMLTest {
	@Resource(name = "domconcreteCont")
	EgovConcreteDOMFactory domconcrete = null;
	
	@Resource(name = "saxconcreteCont")
	EgovConcreteSAXFactory saxconcrete = null;

	String cacheServerIP;
	int cacheServerPort=0;
	String Storekey;
	String Retrievekey;
	String XMLFileName;
	Logger logger  = XmlLog.getLogger(CacheXMLTest.class);
	String XMLFileName1 = "spring/context-sql.xml";
	String fileName = Thread.currentThread().getContextClassLoader().getResource(XMLFileName1).getFile();
	
	public void setXMLFileName(String XMLFileName)
	{
		this.XMLFileName = XMLFileName;
	}
	
    public void setPortNIp(String cacheServerIP,int cacheServerPort)
    {
    	this.cacheServerIP = cacheServerIP;
    	this.cacheServerPort = cacheServerPort; //64208
    }
    
    public void setStorekey(String Storekey)
    {
    	this.Storekey = Storekey;
    }
    
    public void setRetrievekey(String Retrievekey)
    {
    	this.Retrievekey = Retrievekey;
    }
    
    public void sendCacheServer(List list)
	{
		 Socket socket = null;
		 ObjectOutputStream oos= null;
		 ObjectInputStream ooi= null;
		 SharedObject sObject = null;
		 
		 try {
			   socket = new Socket(cacheServerIP, cacheServerPort);
			   oos = new ObjectOutputStream(socket.getOutputStream());
			   sObject = new SharedObject(Storekey,list);
			   oos.writeObject(sObject);
			   ooi = new ObjectInputStream(socket.getInputStream());
			   sObject = (SharedObject)ooi.readObject();
			   
			   
			   logger.debug("서버로 부터 Message :" +sObject.getValue());
			  } catch(Throwable t) {
			   t.printStackTrace();
			 } finally {
			   try { oos.close(); } catch(Throwable t) {t.printStackTrace();}
			   try { socket.close(); } catch(Throwable t) {t.printStackTrace();}
			  }
	}
    
    public SharedObject getCacheServer()
	{
		 Socket socket = null;
		 ObjectOutputStream oos= null;
		 ObjectInputStream ooi= null;
		 SharedObject sObject = null;
		 
		 try {
			   socket = new Socket(cacheServerIP, cacheServerPort);
			   oos = new ObjectOutputStream(socket.getOutputStream());
			   sObject = new SharedObject("*",Retrievekey);
			   oos.writeObject(sObject);
			   ooi = new ObjectInputStream(socket.getInputStream());
			   sObject = (SharedObject)ooi.readObject();
			  } catch(Throwable t) {
			   t.printStackTrace();
			   System.exit(1);
			  } finally {
			   try { oos.close(); } catch(Throwable t) {t.printStackTrace();}
			   try { socket.close(); } catch(Throwable t) {t.printStackTrace();}
			  }
		return	  sObject;  
	}
	
    public void viewEelement(List list)
    {
    	Iterator i = list.iterator();
        while (i.hasNext()) {
        	Element element = (Element) i.next();
        	  List attList = element.getAttributes();
        	  if (attList.size() != 0) 
        	  {
                  // 역시 속성리스트를 다시 iterator 로 담고
                  Iterator ii = attList.iterator();

                  while(ii.hasNext()) {
                          /** Attribute 파싱 **/
                          // iterator 로 부터 하나의 속성을 꺼내와서...
                          Attribute at = (Attribute)ii.next();
                          logger.debug("attribute : " + at.getName() +"   attribute value : " + at.getValue());
                          logger.debug("Element1 Name :"+(String)element.getName() +"  Element1 Value:"+(String)element.getValue());
                  }        // end of while
            }        // end of 속성 if
            List list2 = element.getChildren();
            if(list2.size() > 1)
            {
            	viewEelement(list2);
            }
    }
    }
    
    @Test
    public void ModuleTest() throws IOException,JDOMException{
		// TODO Auto-generated method stub
		EgovDOMValidatorService domValidator = null;
		EgovSAXValidatorService saxValidator = null;
		String cacheServerIP = "127.0.0.1";
		String Storekey = "1";
		int cacheServerPort = 64208;
		CacheXMLAgent cxa = new CacheXMLAgent();
		cxa.setPortNIp(cacheServerIP, cacheServerPort);
		cxa.setStorekey(Storekey);
		cxa.setXMLFileName(XMLFileName1);
		
	    saxValidator = saxconcrete.CreateSAXValidator();
		
		SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new File(fileName));

      //  List list = domValidator.getResult(doc,"//*[@*]");
        List list = saxValidator.getResult(doc,"//*[@*]");
      
       
        // 1. 캐쉬에 저장
        cxa.sendCacheServer(list);
        
        // 2. 캐쉬로부터 조회
       
       /*
        cxa.setRetrievekey("1");
        SharedObject sobject =  cxa.getCacheServer();
        List list_ = (List)sobject.getValue();
        cxa.viewEelement(list_);
        */
   }
}
