package egovframework.rte.fdl.excel.download;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.fdl.excel.vo.UsersVO;


@Controller
public class CategoryExcelController {

	Log log  = LogFactory.getLog(getClass());

    @RequestMapping("/sale/listExcelCategory.do")
    public ModelAndView selectCategoryList() throws Exception {

        log.debug("### selectCategoryList start !!!");
        
        List<Map> lists = new ArrayList<Map>();
        
        Map<String, String> mapCategory = new HashMap<String, String>();
        mapCategory.put("id", "0000000001");
        mapCategory.put("name", "Sample Test");
        mapCategory.put("description", "This is initial test data.");
        mapCategory.put("useyn", "Y");
        mapCategory.put("reguser", "test");
        
        lists.add(mapCategory);
        log.debug("### selectCategoryList lists.add1");
        
        mapCategory.put("id", "0000000002");
        mapCategory.put("name", "test Name");
        mapCategory.put("description", "test Deso1111");
        mapCategory.put("useyn", "Y");
        mapCategory.put("reguser", "test");
        
        lists.add(mapCategory);
        log.debug("### selectCategoryList lists.add2");
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("category", lists);

        return new ModelAndView("categoryExcelView", "categoryMap", map);
    }
    
    @RequestMapping("/sale/listExcelVOCategory.do")
    public ModelAndView selectCategoryVOList() throws Exception {

        log.debug("### selectCategoryVOList start !!!");
        
        List<UsersVO> lists = new ArrayList<UsersVO>();
        
        UsersVO users = new UsersVO();
        
        
        //Map<String, String> mapCategory = new HashMap<String, String>();
        users.setId("0000000001");
        users.setName("Sample Test");
        users.setDescription("This is initial test data.");
        users.setUseYn("Y");
        users.setRegUser("test");
        
        lists.add(users);
        log.debug("### selectCategoryVOList lists.add1");
        
        users.setId("0000000002");
        users.setName("test Name");
        users.setDescription("test Deso1111");
        users.setUseYn("Y");
        users.setRegUser("test");

        
        lists.add(users);
        log.debug("### selectCategoryVOList lists.add2");
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("category", lists);

        return new ModelAndView("categoryExcelView", "categoryMap", map);
    }
}
