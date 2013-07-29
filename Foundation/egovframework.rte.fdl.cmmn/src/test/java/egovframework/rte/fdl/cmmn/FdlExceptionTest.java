package egovframework.rte.fdl.cmmn;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import egovframework.rte.fdl.cmmn.exception.FdlException;

public class FdlExceptionTest extends AbstractDependencyInjectionSpringContextTests {

	private FdlExceptionTestService fdlExceptionTestService;

	public FdlExceptionTestService getFdlExceptionTestService() {
		return fdlExceptionTestService;
	}

	public void setSampleService(FdlExceptionTestService fdlExceptionTestService) {
		this.fdlExceptionTestService = fdlExceptionTestService;
	}

	protected String[] getConfigLocations() {
		return new String[] { "classpath*:/META-INF/spring/egovframework-fdl-exception.xml" };
	}

	public void testThrowFdlException() {

		// public FdlException()
		try {
			fdlExceptionTestService.testFdlException(1);
		} catch (FdlException fe) {
			assertEquals("FdlException without message", fe.getMessage());
		}

		// public FdlException(String defaultMessage)
		try {
			fdlExceptionTestService.testFdlException(2);
		} catch (FdlException fe) {
			assertEquals("message", fe.getMessage());
		}

		// public FdlException(String defaultMessage, Throwable
		// wrappedException)
		try {
			fdlExceptionTestService.testFdlException(3);
		} catch (FdlException fe) {
			assertEquals("message", fe.getMessage());
			Exception ex = (Exception) fe.getCause();
			assertEquals("TEST FdlException", ex.getMessage());
		}

		// public FdlException(String defaultMessage, Object[]
		// messageParameters,
		// Throwable wrappedException)
		try {
			fdlExceptionTestService.testFdlException(4);
		} catch (FdlException fe) {
			assertEquals("message 1", fe.getMessage());
			Exception ex = (Exception) fe.getCause();
			assertEquals("TEST FdlException", ex.getMessage());
		}

		// public FdlException(MessageSource messageSource, String messageKey)
		try {
			fdlExceptionTestService.testFdlException(5);
		} catch (FdlException fe) {

			assertEquals("error.fdl.msg1", fe.getMessageKey());
			assertEquals("message1", fe.getMessage());

		}

		// public FdlException(MessageSource messageSource, String messageKey,
		// Throwable wrappedException)
		try {
			fdlExceptionTestService.testFdlException(6);
		} catch (FdlException fe) {

			assertEquals("error.fdl.msg2", fe.getMessageKey());
			assertEquals("message2", fe.getMessage());

			Exception ex = (Exception) fe.getCause();
			assertEquals("TEST FdlException", ex.getMessage());

		}

		// public FdlException(MessageSource messageSource, String messageKey,
		// Locale locale, Throwable wrappedException) {
		try {
			fdlExceptionTestService.testFdlException(7);
		} catch (FdlException fe) {

			assertEquals("error.fdl.msg3", fe.getMessageKey());
			assertEquals("메세지3", fe.getMessage());

			Exception ex = (Exception) fe.getCause();
			assertEquals("TEST FdlException", ex.getMessage());

		}

		// public FdlException(MessageSource messageSource, String messageKey,
		// Object[] messageParameters, Locale locale,
		// Throwable wrappedException) {
		try {
			fdlExceptionTestService.testFdlException(8);
		} catch (FdlException fe) {

			assertEquals("error.fdl.msg4", fe.getMessageKey());
			assertEquals("메세지4 파라미터 추가", fe.getMessage());

			Exception ex = (Exception) fe.getCause();
			assertEquals("TEST FdlException", ex.getMessage());

		}

		// public FdlException(MessageSource messageSource, String messageKey,
		// Object[] messageParameters, Throwable wrappedException) {
		try {
			fdlExceptionTestService.testFdlException(9);
		} catch (FdlException fe) {

			assertEquals("error.fdl.msg5", fe.getMessageKey());
			assertEquals("message5 parameter", fe.getMessage());

			Exception ex = (Exception) fe.getCause();
			assertEquals("TEST FdlException", ex.getMessage());

		}

		// public FdlException(MessageSource messageSource, String messageKey,
		// Object[] messageParameters, String defaultMessage,
		// Throwable wrappedException) {
		// default message is returned
		try {
			fdlExceptionTestService.testFdlException(10);
		} catch (FdlException fe) {

			assertEquals("error.fdl.msg66", fe.getMessageKey());
			assertEquals("default message", fe.getMessage());

			Exception ex = (Exception) fe.getCause();
			assertEquals("TEST FdlException", ex.getMessage());

		}

		// public FdlException(MessageSource messageSource, String messageKey,
		// Object[] messageParameters, String defaultMessage,
		// Throwable wrappedException) {
		// default message isn't returned
		try {
			fdlExceptionTestService.testFdlException(11);
		} catch (FdlException fe) {

			assertEquals("error.fdl.msg6", fe.getMessageKey());
			assertEquals("message6 parameter", fe.getMessage());

			Exception ex = (Exception) fe.getCause();
			assertEquals("TEST FdlException", ex.getMessage());

		}

		// public FdlException(MessageSource messageSource, String messageKey,
		// Object[] messageParameters, String defaultMessage, Locale locale,
		// Throwable wrappedException)
		try {
			fdlExceptionTestService.testFdlException(12);
		} catch (FdlException fe) {

			assertEquals("error.fdl.msg7", fe.getMessageKey());
			assertEquals("메세지7 파라미터", fe.getMessage());

			Exception ex = (Exception) fe.getCause();
			assertEquals("TEST FdlException", ex.getMessage());

		}

	}

}
