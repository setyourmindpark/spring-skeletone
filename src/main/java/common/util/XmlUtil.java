package common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
//https://beyondj2ee.wordpress.com/2014/02/25/big-xml%ED%8C%8C%EC%9D%BC%EC%9D%84-%EC%9D%BD%EC%9D%84%EB%95%8C-stax%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EC%9E%90/
//http://howtodoinjava.com/xml/java-xml-dom-parser-example-tutorial/
//https://dzone.com/articles/xml-json-hashmap
public class XmlUtil {
	///Users/jaehunpark/Documents/jaehunpark/development/eclipse/r2r3_tmp/20170307/E5836DE8D1AD4A41964A0C466D472588.xml
	
	
//	public static List<?> convertXMLToMap(String path) {
//	List<String> list = null;
//	try{
//		String jsonStr = convertXMLToJSON(path);
//		ObjectMapper mapper = new ObjectMapper();
//		Map<String, Object> jsonInMap = mapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {});
//		list = new ArrayList<String>(jsonInMap.keySet());
//
//		for (String key : list) {
//			System.out.println(key + ": " + jsonInMap.get(key));
//		}
//
//	}catch (Exception e) {
//		// TODO: handle exception
//		e.printStackTrace();
//	}
//	
//	return list;
//}

//public static Map<String,Object> convertXMLToMap(String path) {
//	Map<String, Object> map = null;
//	
//	try{
//		String jsonStr = convertXMLToJSON(path);
//		ObjectMapper mapper = new ObjectMapper();
//		Map<String, Object> jsonInMap = mapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {});
//		List<String> list = new ArrayList<String>(jsonInMap.keySet());
//		map = new LinkedHashMap<String,Object>();			// 순서보장을위해 LinkedHashMap을 사용함
//		System.out.println("----------------------- xml to map -----------------------");
//		for (String key : list) {
//			System.out.println(key + ": " + jsonInMap.get(key));
//			map.put(key, jsonInMap.get(key));
//		}
//		System.out.println("----------------------- xml to map -----------------------");
//	}catch (Exception e) {
//		// TODO: handle exception
//		e.printStackTrace();
//	}
//	return map;
//}
	
	
	public static String convertXMLToString(File file) {
		try {
			return IOUtils.toString(new FileInputStream(file));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public static String convertXMLToString(String path) {
		try {
			return IOUtils.toString(new FileInputStream(new File(path)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public static JSONObject convertXMLToJSON(String path) {
		try{
			String xml = convertXMLToString(path);
			return convertXMLStringToJSON(xml);
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public static JSONObject convertXMLToJSON(File file) {
		try{
			String xml = convertXMLToString(file);
			return convertXMLStringToJSON(xml);
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	
	public static JSONObject convertXMLStringToJSON(String xmlStr) {
		JSONObject jsonObj = null;
		try{
			XMLSerializer xmlSerializer = new XMLSerializer();
			JSON json = xmlSerializer.read(xmlStr);
			System.out.println("----------------------- xml to json -----------------------");
			System.out.println(json.toString(2));
			System.out.println("----------------------- xml to json -----------------------");
			jsonObj = (JSONObject)new JSONParser().parse(json.toString());
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return jsonObj;
	}
	
	
	
}