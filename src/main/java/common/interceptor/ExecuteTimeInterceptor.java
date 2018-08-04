package common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//1
public class ExecuteTimeInterceptor extends HandlerInterceptorAdapter{

	private static final Logger log = LoggerFactory.getLogger(ExecuteTimeInterceptor.class);

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		long startTime = (Long) request.getAttribute("startTime");
		long endTime = System.currentTimeMillis();
		long executeTime = endTime - startTime;
		// log it
		if (log.isDebugEnabled()) {
//			log.debug("[" + handler + "] executeTime : " + executeTime + "ms");
			log.debug("[executeTime : " + executeTime + "ms]");
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		
		if (log.isDebugEnabled()) {
	        log.debug("Request URI: " + request.getRequestURI());
	    }


		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);

		return true;
	}
	
	

}
