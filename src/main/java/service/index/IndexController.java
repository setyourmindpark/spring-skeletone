package service.index;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import service.util.MessageUtil;

@RestController
public class IndexController {
	
	protected static Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Resource(name = "indexService")
	private IndexService indexService;
	
	/**
	 * test api
	 */
	@RequestMapping(value= "/", method = RequestMethod.GET)	
	public Map<String,String> index() {		
		
		// test
//		String hello = MessageUtil.getMessage("hello");
//		System.out.println(hello);
//		
//		Map queryMap = indexService.now();
//		System.out.println(queryMap.get("now"));		
		
		logger.info("hello!");
		
		Map<String,String> data = new HashMap<String,String>();
		data.put("hello", "world");		
		return data;
	}
}
