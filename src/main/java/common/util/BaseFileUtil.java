package common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import common.vo.FileVO;


public class BaseFileUtil {
	/** Buffer size */
    public static final int BUFFER_SIZE = 8192;

    public static final String SEPERATOR = File.separator;
    public static final String [] whiteFile = {"bmp","jpg","gif","png","jpeg","mp3","ogg","wma","wav","mpeg","wmv","mp4","swf","txt","hwp","doc","docx","ppt","pdf","pptx","xml","zip","rar","alz","egg","7z","xls","xlsx","avi","p12"};	
    /**
     * 오늘 날짜 문자열 취득.
     * ex) 20090101
     * @return
     */
    public static String getTodayString() {
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

	return format.format(new Date());
    }

    /**
     * 물리적 파일명 생성.
     * @return
     */
    public static String getPhysicalFileName() {
	return UuidUtil.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    /**
     * 파일명 변환.
     * @param filename String
     * @return
     * @throws Exception
     */
    protected static String convert(String filename) throws Exception {
	//return java.net.URLEncoder.encode(filename, "utf-8");
	return filename;
    }

    /**
     * Stream으로부터 파일을 저장함.
     * @param is InputStream
     * @param file File
     * @throws IOException
     */
    public static long saveFile(InputStream is, File file) throws IOException {
	// 디렉토리 생성
	if (! file.getParentFile().exists()) {
	    file.getParentFile().mkdirs();
	}

	OutputStream os = null;
	long size = 0L;

	try {
	    os = new FileOutputStream(file);

	    int bytesRead = 0;
	    byte[] buffer = new byte[BUFFER_SIZE];

	    while ((bytesRead = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
		size += bytesRead;
		os.write(buffer, 0, bytesRead);
	    }
	} finally {
	    if (os != null) {
		os.close();
	    }
	}

	return size;
    }
    
    public static List<FileVO> uploadFiles(HttpServletRequest request, String where, long maxFileSize) throws Exception {
    	
    	List<FileVO> list = new ArrayList<FileVO>();
    	MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
    	Iterator fileIter = mptRequest.getFileNames();
    	
    	while (fileIter.hasNext()) {
    		String key = (String)fileIter.next();
    		MultipartFile mFile = mptRequest.getFile(key);
    		FileVO vo = new FileVO();

    		String tmp = mFile.getOriginalFilename();

            if (tmp.lastIndexOf("\\") >= 0) {
            	tmp = tmp.substring(tmp.lastIndexOf("\\") + 1);
            }

            vo.setFileName(tmp);
            vo.setContentType(mFile.getContentType());
            vo.setServerSubPath(getTodayString());
            vo.setPhysicalName(getPhysicalFileName());
            vo.setSize(mFile.getSize());
            vo.setExtension(tmp.substring(tmp.lastIndexOf(".")));
            vo.setParameterName(key);
            if (tmp.lastIndexOf(".") >= 0) {
       	 		vo.setPhysicalName(vo.getPhysicalName() + tmp.substring(tmp.lastIndexOf(".")));
            }

            if (mFile.getSize() > 0) {
            	saveFile(mFile.getInputStream(), new File(WebUtil.filePathBlackList(where+SEPERATOR+vo.getServerSubPath()+SEPERATOR+vo.getPhysicalName())));
            	list.add(vo);
            }
    	}

    	return list;
    }
    
    //단건파일 업로드
    public static FileVO uploadFile(File file, String where) throws Exception {
		FileVO vo = new FileVO();

		String fileName = file.getName();
        if (fileName.lastIndexOf("\\") >= 0) {
        	fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
        }

        vo.setFileName(fileName);
        vo.setServerSubPath(getTodayString());
        vo.setPhysicalName(getPhysicalFileName());
        vo.setSize(file.length());
        vo.setExtension(fileName.substring(fileName.lastIndexOf(".")));
        if (fileName.lastIndexOf(".") >= 0) {
   	 		vo.setPhysicalName(vo.getPhysicalName() + fileName.substring(fileName.lastIndexOf(".")));
        }

        if (file.length() > 0) {
        	saveFile(new FileInputStream(file), new File(WebUtil.filePathBlackList(where+SEPERATOR+vo.getServerSubPath()+SEPERATOR+vo.getPhysicalName())));
        }
	
    	return vo;
    }
    
    //단건파일 업로드 multipartfile
    public static FileVO uploadFile(MultipartFile file, String where) throws Exception {
		FileVO vo = new FileVO();

		String fileName = file.getOriginalFilename();
        if (fileName.lastIndexOf("\\") >= 0) {
        	fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
        }
        System.out.println(fileName+" file name ");
        vo.setFileName(fileName);
        vo.setServerSubPath(getTodayString());
        vo.setPhysicalName(getPhysicalFileName());
        vo.setSize(file.getSize());
        vo.setExtension(fileName.substring(fileName.lastIndexOf(".")));
        if (fileName.lastIndexOf(".") >= 0) {
   	 		vo.setPhysicalName(vo.getPhysicalName() + fileName.substring(fileName.lastIndexOf(".")));
        }

        if (file.getSize() > 0) {
        	saveFile(file.getInputStream(), new File(WebUtil.filePathBlackList(where+SEPERATOR+vo.getServerSubPath()+SEPERATOR+vo.getPhysicalName())));
        }
	
    	return vo;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //multipartfile 을 file로 변환
    public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File( multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }
    
    //확장자 검사
    public static boolean isExtensionComfirmed(File file, String [] allowExt) throws Exception {
		return isExtensionComfirmed(file.getName(), allowExt);
    }
    
    //확장자 검사
    public static boolean isExtensionComfirmed(String fileName, String [] allowExt) throws Exception {
		for(int i = 0; i<allowExt.length; i++){
			if(allowExt[i].equals(fileName.substring(fileName.lastIndexOf(".")))){
				return true;
			}
  		}
    	return false;
    }
    
    //inputstream 을 임시 xml 파일로 변환
//    public static File convertStreamToTmpFile(InputStream is,String extension) throws IOException {
//    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss", Locale.getDefault());
//    	File tmpFile = File.createTempFile("tmp_"+format.format(new Date()), extension);
//    	tmpFile.deleteOnExit(); // delete this on JVM exist
//    	
//    	BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//    	BufferedWriter fileWriter = new BufferedWriter(new FileWriter(tmpFile));
//    	String line = null;
//    	while ((line = reader.readLine()) != null) {
//    		fileWriter.write(line + "\n");
//    	}
//    	fileWriter.flush();
//    	fileWriter.close();
//    	is.close();
//    	
//    	return tmpFile;
//    	}
    
    // input stream을 임시 file 객체로 변환
    public static File convertStreamToTmpFile(InputStream is,String extension) throws IOException {
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss", Locale.getDefault());
    	File tmpFile = File.createTempFile("tmp_"+format.format(new Date()), extension);

		OutputStream outputStream = new FileOutputStream(tmpFile);

		IOUtils.copy(is, outputStream);

		outputStream.close();

		return tmpFile;

    }

}
