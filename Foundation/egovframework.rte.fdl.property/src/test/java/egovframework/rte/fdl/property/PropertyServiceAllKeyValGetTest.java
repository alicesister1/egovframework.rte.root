package egovframework.rte.fdl.property;

import java.util.Collection;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * PropertyServiceAllKeyValGetTest
 * <p>
 * <b>NOTE</b>: 전체 키쌍 제공 여부 확인
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
public class PropertyServiceAllKeyValGetTest extends
        AbstractDependencyInjectionSpringContextTests {

    @Resource(name = "propertyService")
    protected EgovPropertyService propertyService = null;

    /**
     * 전체 키/값 쌍 제공
     * @throws Exception
     *         fail to test
     */
    @Test
    public void testGetAllKeyValue() throws Exception {

        Collection collectionKeyValue = propertyService.getAllKeyValue();
        assertEquals(15, collectionKeyValue.size());

    }
}
