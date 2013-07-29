package egovframework.rte.fdl.string;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author sjyoon
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/context-*.xml" })
public class EgovNumericUtilTest {

	Log log = LogFactory.getLog(getClass());

	@Before
    public void onSetUp() throws Exception {

    	log.debug("###### EgovNumericUtilTest.onSetUp START ######");
        
        log.debug("###### EgovNumericUtilTest.onSetUp END ######");
        
    }
    
    @After
    public void onTearDown() throws Exception {
    	
    	log.debug("###### EgovNumericUtilTest.onTearDown START ######");
    	
    	log.debug("###### EgovNumericUtilTest.onTearDown END ######");
    }
    
	/**
	 * [Flow #-1] Positive Case : 
	 */
	@Test
	public void testIsNumber() throws Exception {

		assertFalse(EgovNumericUtil.isNumber("abc"));
		
		assertFalse(EgovNumericUtil.isNumber("!@"));
		
		assertFalse(EgovNumericUtil.isNumber("ab-123"));
		
		assertTrue(EgovNumericUtil.isNumber("-123"));
		
		assertTrue(EgovNumericUtil.isNumber("1234"));
		
	}
	
	@Test
	public void testPlus() throws Exception {

		assertEquals("400", EgovNumericUtil.plus("151", "249"));

		assertEquals("400.0000", EgovNumericUtil.plus("151.7531", "248.2469"));

		assertEquals("400.000", EgovNumericUtil.plus("151.7531", "248.2469", 3));

		assertEquals("399.9654", EgovNumericUtil.plus("151.7531", "248.2123"));

		assertEquals("399.966", EgovNumericUtil.plus("151.7531", "248.2123", 3, EgovNumericUtil.ROUND_UP));

		assertEquals("399.965", EgovNumericUtil.plus("151.7531", "248.2123", 3, EgovNumericUtil.ROUND_DOWN));

		assertEquals("399.97", EgovNumericUtil.plus("151.7531", "248.2123", 2, EgovNumericUtil.ROUND_HALF_UP));
	}
	
	@Test
	public void testMinus() throws Exception {

		assertEquals("89", EgovNumericUtil.minus("240", "151"));
		
		assertEquals("96.4938", EgovNumericUtil.minus("248.2469", "151.7531"));
		
		assertEquals("96.49380", EgovNumericUtil.minus("248.2469", "151.7531", 5));
		
		assertEquals("96.4592", EgovNumericUtil.minus("248.2123", "151.7531"));
		
		assertEquals("96.460", EgovNumericUtil.minus("248.2123", "151.7531", 3, EgovNumericUtil.ROUND_UP));
		
		assertEquals("96.459", EgovNumericUtil.minus("248.2123", "151.7531", 3, EgovNumericUtil.ROUND_DOWN));
		
		assertEquals("96.46", EgovNumericUtil.minus("248.2123", "151.7531", 2, EgovNumericUtil.ROUND_HALF_UP));
	}

	@Test
	public void testMultiply() throws Exception {

		assertEquals("180", EgovNumericUtil.multiply("15", "12"));
		
		assertEquals("189.6135", EgovNumericUtil.multiply("15.23", "12.45"));

		assertEquals("189.61350", EgovNumericUtil.multiply("15.23", "12.45", 5));
		
		assertEquals("189.614", EgovNumericUtil.multiply("15.23", "12.45", 3, EgovNumericUtil.ROUND_UP));
		
		assertEquals("189.613", EgovNumericUtil.multiply("15.23", "12.45", 3, EgovNumericUtil.ROUND_DOWN));
		
		assertEquals("189.61", EgovNumericUtil.multiply("15.23", "12.45", 2, EgovNumericUtil.ROUND_HALF_UP));
	}
	
	@Test
	public void testDivide() throws Exception {

		assertEquals("1.25", EgovNumericUtil.divide("15", "12"));
		
		Class<Exception> exceptionClass = null;
		try {
			assertEquals("1.2232931726907630522088353413655", EgovNumericUtil.divide("15.23", "12.45"));
		} catch (Exception e) {
			log.error("### Exception : " + e.toString());
			exceptionClass = (Class<Exception>) e.getClass();
		} finally {
			assertEquals(ArithmeticException.class, exceptionClass);
		}
		
		assertEquals("1.22", EgovNumericUtil.divide("15.23", "12.45", 5));
		
		assertEquals("1.224", EgovNumericUtil.divide("15.23", "12.45", 3, EgovNumericUtil.ROUND_UP));
		
		assertEquals("1.223", EgovNumericUtil.divide("15.23", "12.45", 3, EgovNumericUtil.ROUND_DOWN));
		
		assertEquals("1.22", EgovNumericUtil.divide("15.23", "12.45", 2, EgovNumericUtil.ROUND_HALF_UP));
	}
	
	@Test
	public void testScale() throws Exception {

		assertEquals("151.754", EgovNumericUtil.setScale("151.7531", 3, EgovNumericUtil.ROUND_UP));
		
		assertEquals("151.753", EgovNumericUtil.setScale("151.7531", 3, EgovNumericUtil.ROUND_DOWN));
		
		assertEquals("151.753", EgovNumericUtil.setScale("151.7531", 3, EgovNumericUtil.ROUND_HALF_UP));
		
	}
}
