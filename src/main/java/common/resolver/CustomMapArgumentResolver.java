package common.resolver;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import common.map.ParamMap;

public class CustomMapArgumentResolver implements HandlerMethodArgumentResolver{
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return ParamMap.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		ParamMap paramMap = new ParamMap();
		
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		Enumeration<?> enumeration = request.getParameterNames();
		
		String reqKey = null;
		String[] reqValues = null;
		while(enumeration.hasMoreElements()){
			reqKey = (String) enumeration.nextElement();
			reqValues = request.getParameterValues(reqKey);
//			System.out.println("reqKey : "+(String)reqKey +" | reqValues : "+ Arrays.toString(reqValues));
			if(reqValues != null){
				paramMap.put(reqKey, (reqValues.length > 1) ? reqValues:reqValues[0] );
			}
		}
		
		// 2017.03.10 multipart 도 자동으로 binding 되도록 한다 by jaehunpark
		if(webRequest.getNativeRequest() instanceof MultipartHttpServletRequest){		//instance 가 MultipartHttpServletRequest 이 있으면
			MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)webRequest.getNativeRequest();
	    	Iterator<String> iterator = mptRequest.getFileNames();
	    	List<Map<String,Object>> mptList = new ArrayList<Map<String,Object>>(); 
			
	    	String mptReqKey = null;
			MultipartFile mptFile = null;
			while(iterator.hasNext()){
				mptReqKey = (String) iterator.next();
				mptFile = mptRequest.getFile(mptReqKey);
//				System.out.println("mptReqKey : "+(String) mptReqKey +" | [ this parameter has a file ] ");
				if(mptFile != null){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put(mptReqKey, mptFile);
					mptList.add(map);
					//commandMap.put(mptReqKey, mptFile);
				}
			}
			paramMap.put("mptFiles", mptList);			
		}
		
		return paramMap;
	}

}