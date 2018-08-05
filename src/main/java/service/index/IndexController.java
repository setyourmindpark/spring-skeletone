package service.index;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.util.MessageUtil;

@Controller
@RequestMapping("/")
public class IndexController {
	
	protected static Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Resource(name = "indexService")
	private IndexService indexService;
	
	@RequestMapping(value= "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	@RequestMapping(value= "/hello", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> hello() {		
		
		String message = MessageUtil.getMessage("hello");
		logger.info("hello " + message);
		
//		Map<String,String> queryMap = indexService.now();
//		String now = queryMap.get("now");
//		logger.info(now);
		
		Map<String,String> data = new HashMap<String,String>();
		data.put("hello", "world");		
		return data;
	}
}
