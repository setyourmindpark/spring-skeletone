package common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 웹상에서 다운로드 작업을 수행하는 유틸리티
 *
 * @author 손권남
 *
 */
public class DownloadUtil {
  /** 다운로드 버퍼 크기 */
  private static final int BUFFER_SIZE = 8192; // 8kb

  /** 문자 인코딩 */
  private static final String CHARSET = "UTF-8";

  /**
   * 생성자 - 객체 생성 불가
   */
  private DownloadUtil() {
    // do nothing;
  }

  /**
   * 지정된 파일을 다운로드 한다.
   *
   * @param request
   * @param response
   * @param file
   *            다운로드할 파일
   *
   * @throws ServletException
   * @throws IOException
   */
  public static void download(HttpServletRequest request, HttpServletResponse response, File file, String viewFileName)
      throws ServletException, IOException {

    String mimetype = request.getSession().getServletContext().getMimeType(file.getName());

    if (file == null || !file.exists() || file.length() <= 0 || file.isDirectory()) {
      throw new IOException("파일 객체가 Null 혹은 존재하지 않거나 길이가 0, 혹은 파일이 아닌 디렉토리이다.");
    }

    InputStream is = null;

    try {
      is = new FileInputStream(file);
      download(request, response, is, file.getName(), file.length(), mimetype, viewFileName);
    } finally {
      try {
        is.close();
      } catch (Exception ex) {
      }
    }
  }

  /**
   * 해당 입력 스트림으로부터 오는 데이터를 다운로드 한다.
   *
   * @param request
   * @param response
   * @param is
   *            입력 스트림
   * @param filename
   *            파일 이름
   * @param filesize
   *            파일 크기
   * @param mimetype
   *            MIME 타입 지정
   * @throws ServletException
   * @throws IOException
   */
  public static void download(HttpServletRequest request, HttpServletResponse response, InputStream is,
      String filename, long filesize, String mimetype, String viewFileName) throws ServletException, IOException {
    String mime = mimetype;

    if (mimetype == null || mimetype.length() == 0) {
      mime = "application/octet-stream;";
    }

    byte[] buffer = new byte[BUFFER_SIZE];

    response.setContentType(mime + "; charset=" + CHARSET);

    // 아래 부분에서 euc-kr 을 utf-8 로 바꾸거나 URLEncoding을 안하거나 등의 테스트를
    // 해서 한글이 정상적으로 다운로드 되는 것으로 지정한다.
    String userAgent = request.getHeader("User-Agent");

    // attachment; 가 붙으면 IE의 경우 무조건 다운로드창이 뜬다. 상황에 따라 써야한다.
    if (userAgent != null && userAgent.indexOf("MSIE 5.5") > -1) { // MS IE 5.5 이하
      response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(viewFileName, "UTF-8") + ";");
    } else if (userAgent != null && userAgent.indexOf("MSIE") > -1) { // MS IE (보통은 6.x 이상 가정)
      response.setHeader("Content-Disposition", "attachment; filename="
          + java.net.URLEncoder.encode(viewFileName, "UTF-8") + ";");
    } else { // 모질라나 오페라
      response.setHeader("Content-Disposition", "attachment; filename="
          + new String(viewFileName.getBytes(CHARSET), "latin1") + ";");
    }

    // 파일 사이즈가 정확하지 않을때는 아예 지정하지 않는다.
    if (filesize > 0) {
      response.setHeader("Content-Length", "" + filesize);
    }

    BufferedInputStream fin = null;
    BufferedOutputStream outs = null;

    try {
      fin = new BufferedInputStream(is);
      outs = new BufferedOutputStream(response.getOutputStream());
      int read = 0;

      while ((read = fin.read(buffer)) != -1) {
        outs.write(buffer, 0, read);
      }
    } catch (IOException ex) {
        // Tomcat ClientAbortException을 잡아서 무시하도록 처리해주는게 좋다.
    	System.out.println("exception1");
    } finally {
      try {
        outs.close();
      } catch (Exception ex1) {
    	System.out.println("exception2");
      }

      try {
        fin.close();
      } catch (Exception ex2) {
    	System.out.println("exception3");
      }
    } // end of try/catch
  }
}