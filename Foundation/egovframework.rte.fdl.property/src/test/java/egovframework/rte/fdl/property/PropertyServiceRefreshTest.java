package egovframework.rte.fdl.property;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * PropertyServiceRefreshTest
 * <p>
 * <b>NOTE</b>: Property Service 리로딩 기능 확인.
 * @author 실행환경 개발팀 김태호
 * @since 2009.02.01
 * @version 1.0
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.02.01  김태호          최초 생성
 * 
 * </pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/context-common.xml",
    "classpath*:/spring/context-properties.xml" })
public class PropertyServiceRefreshTest extends
        AbstractDependencyInjectionSpringContextTests {

    @Resource(name = "propertyService")
    protected EgovPropertyService propertyService = null;

    /**
     * 시스템 재가동없이 Property를 리로드
     * @throws Exception
     *         fail to test
     */
    @Test
    public void testRefreshPropertiesFiles() throws Exception {

        assertEquals("first token", propertyService
            .getString("tokens_on_multiple_lines"));

        assertEquals(new Double(1234), new Double(propertyService
            .getDouble("number.double")));

        propertyService.refreshPropertyFiles();

        assertEquals("first token", propertyService
            .getString("tokens_on_multiple_lines"));

        assertEquals(new Double(1234), new Double(propertyService
            .getDouble("number.double")));

    }
}
