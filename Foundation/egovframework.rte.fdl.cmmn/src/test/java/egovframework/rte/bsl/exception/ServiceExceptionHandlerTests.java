package egovframework.rte.bsl.exception;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import egovframework.rte.fdl.cmmn.aspect.ExceptionTransfer;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/*.xml" })
public class ServiceExceptionHandlerTests {
	
	@Resource(name = "helloService")
	private HelloService helloService;
	@Resource(name = "ohterService")
	private HelloService otherService;
	
	@Resource
	ApplicationContext applicationContext;
	
	@Test
	public void testBizUnCheckedException() throws Exception {
		
		String name = "world";
		String resultStr ;
		
		resultStr = helloService.sayHello(name);
		assertEquals("Hello world", resultStr);
		
		LeaveaTrace tmpTrace = (LeaveaTrace) applicationContext.getBean("leaveaTrace");
		
		assertEquals(1, tmpTrace.countOfTheTraceHandlerService());
	}
	
	@Test
	public void testBizException() {
		// wrapped Exception 존재하는 경우 .
		try {
			helloService.updateMethod();
		} catch (Exception be) {
			assertTrue(be instanceof EgovBizException);
			assertTrue(((EgovBizException) be).getWrappedException() instanceof ArithmeticException);
			assertTrue("해당 데이터가 없습니다.".equals(be.getMessage()));
			ExceptionTransfer etfer = (ExceptionTransfer) applicationContext.getBean("exceptionTransfer");
			assertEquals(2, etfer.countOfTheExceptionHandlerService());
		}
	}
	
	@Test
	public void testBiz2Exception() {
		// wrapped Exception 존재하지 않는 경우 .
		try {
			otherService.updateMethod();
		} catch (Exception be) {
			assertTrue(be instanceof EgovBizException);
			assertTrue("해당 데이터가 없습니다.".equals(be.getMessage()));
			ExceptionTransfer etfer = (ExceptionTransfer) applicationContext.getBean("exceptionTransfer");
			assertEquals(2, etfer.countOfTheExceptionHandlerService());
		}
		
	}
	
}
