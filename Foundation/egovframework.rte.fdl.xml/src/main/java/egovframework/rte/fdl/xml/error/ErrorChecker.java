package egovframework.rte.fdl.xml.error;

import java.util.Set;
import java.util.HashSet;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * DefaultHandler를 상속하는 클래스로, XML 파서가 파싱을 할 때
 *               파싱하는 XML 문서에서 에러가 발생하면 에러 메시지를 받아
 *               Set에 저장하는 역할을 한다
 * @author 개발프레임웍크 실행환경 개발팀 김종호
 * @since 2009.03.18 
 * @version 1.0
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일      수정자              수정내용
 *  ---------   ---------   -------------------------------
 * 2009.03.18    김종호        최초생성
 * 
 * </pre>
 */
public class ErrorChecker extends DefaultHandler {
                /** 에러 메시지를 저장하는 Set **/
                private final Set errorReport = new HashSet();

                /**
                 * DefaultHandler의 error 메소드를 오버라이드하는 메소드로,
                 * XML 문서 파싱시 error 레벨에 해당하는 에러가 발생하면
                 * 파서가 error() 메소드에 에러 메시지를 전달한다.
                 * @param e - SAXParseException
                 */
                public void error(SAXParseException e) {
                        errorReport.add("Parsing error:  " + e.getMessage());
                }
                
                /**
                 * DefaultHandler의 warning 메소드를 오버라이드하는 메소드로,
                 * XML 문서 파싱시 warning 레벨에 해당하는 에러가 발생하면
                 * 파서가 warning() 메소드에 에러 메시지를 전달한다.
                 * @param e - SAXParseException
                 */
                public void warning(SAXParseException e) {
                        errorReport.add("Parsing problem:  " + e.getMessage());
                }

                /**
                 * DefaultHandler의 fatalError 메소드를 오버라이드하는 메소드로,
                 * XML 문서 파싱시 fatalError 레벨에 해당하는 에러가 발생하면
                 * 파서가 fatalError() 메소드에 에러 메시지를 전달한다.
                 * @param e - SAXParseException
                 */
                public void fatalError(SAXParseException e) {
                        errorReport.add("Parsing Fatal error:  " + e.getMessage());
                }

                /**
                 * XML 문서 파싱시 발생한 에러를 저장하고 있는 에러 메시지들을 반환.
                 * @param e - SAXParseException
                 * @return Set
                 */
                public Set getErrorReport() {
                        return errorReport;
                }
        }
