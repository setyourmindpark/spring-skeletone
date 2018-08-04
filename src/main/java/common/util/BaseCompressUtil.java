package common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

public class BaseCompressUtil {
	private static boolean debug = false;
	
	/**
	 * 압축파일이 존재하는 디렉토리에 압축 해제
	 * 
	 * @param zippedFile
	 * @throws IOException
	 */
	public static void unzip(File zippedFile) throws IOException {
		unzip(zippedFile, Charset.defaultCharset().name());
	}
	
	public static void unzip(File zippedFile, String encoding ) throws IOException {
		String path = zippedFile.getParentFile()
				+File.separator
				+zippedFile.getName().substring(0,zippedFile.getName().lastIndexOf("."));
		unzip(zippedFile, new File(path), encoding);
	}
	
	public static void unzip(File zippedFile, File destDir) throws IOException {
		unzip(new FileInputStream(zippedFile), destDir, Charset.defaultCharset().name());
	}
	
	public static void unzip(File zippedFile, File destDir, String encoding)
	throws IOException {
		unzip(new FileInputStream(zippedFile), destDir, encoding);
	}
	
	public static void unzip(InputStream is, File destDir) throws IOException{
		unzip(is, destDir, Charset.defaultCharset().name());
	}
	
	public static void unzip( InputStream is, File destDir, String encoding)
	throws IOException {
		ZipArchiveInputStream zis ;
		ZipArchiveEntry entry ;
		String name ;
		File target ;
		int nWritten = 0;
		BufferedOutputStream bos ;
		byte [] buf = new byte[1024 * 8];

		ensureDestDir(destDir);
		
		zis = new ZipArchiveInputStream(is, encoding, false);
		while ( (entry = zis.getNextZipEntry()) != null ){
			name = entry.getName();
			target = new File (destDir, name);
			if ( entry.isDirectory() ){
				ensureDestDir(target);
			} else {
				target.createNewFile();
				bos = new BufferedOutputStream(new FileOutputStream(target));
				while ((nWritten = zis.read(buf)) >= 0 ){
					bos.write(buf, 0, nWritten);
				}
				bos.close();
				debug ("file : " + name);
			}
		}
		zis.close();
	}
	
	public static void ensureDestDir(File dir) throws IOException {
		
		if ( ! dir.exists() ) {
			dir.mkdirs(); /*  does it always work? */
			debug ("dir  : " + dir);
		}
		
	}
	
	/**
	 * compresses the given file(or dir) and creates new file under the same directory.
	 * @param src file or directory
	 * @throws IOException
	 */
	public static void zip(File src) throws IOException{
		zip(src, Charset.defaultCharset().name(), true);
	}
	/**
	 * zips the given file(or dir) and create 
	 * @param src file or directory to compress
	 * @param includeSrc if true and src is directory, then src is not included in the compression. if false, src is included.
	 * @throws IOException
	 */
	public static void zip(File src, boolean includeSrc) throws IOException{
		zip(src, Charset.defaultCharset().name(), includeSrc);
	}
	/**
	 * compresses the given src file (or directory) with the given encoding
	 * @param src
	 * @param charSetName
	 * @param includeSrc
	 * @throws IOException
	 */
	public static void zip(File src, String charSetName, boolean includeSrc) throws IOException {
		zip( src, src.getParentFile(), charSetName, includeSrc);
	}
	/**
	 * compresses the given src file(or directory) and writes to the given output stream.
	 * @param src
	 * @param os
	 * @throws IOException
	 */
	public static void zip(File src, OutputStream os) throws IOException {
		zip(src, os, Charset.defaultCharset().name(), true);
	}
	/**
	 * compresses the given src file(or directory) and create the compressed file under the given destDir. 
	 * @param src
	 * @param destDir
	 * @param charSetName
	 * @param includeSrc
	 * @throws IOException
	 */
	public static void zip(File src, File destDir, String charSetName, boolean includeSrc) throws IOException {
		String fileName = src.getName();
		if ( !src.isDirectory() ){
			int pos = fileName.lastIndexOf(".");
			if ( pos >  0){
				fileName = fileName.substring(0, pos);
			}
		}
		fileName += ".zip";
		ensureDestDir(destDir);
		
		File zippedFile = new File ( destDir, fileName);
		if ( !zippedFile.exists() ) zippedFile.createNewFile();
		zip(src, new FileOutputStream(zippedFile), charSetName, includeSrc);
	}
	
	public static void zip(File [] filesToZip, OutputStream os, String encoding ) {
		
	}
	
	public static void zip(File src, OutputStream os, String charsetName, boolean includeSrc)
	throws IOException {
		ZipArchiveOutputStream zos = new ZipArchiveOutputStream(os);
		zos.setEncoding(charsetName);
		FileInputStream fis ;
		
		int length ;
		ZipArchiveEntry ze ;
		byte [] buf = new byte[8 * 1024];
		String name ;
		
		Stack<File> stack = new Stack<File>();
		File root ;
		if ( src.isDirectory() ) {
			if( includeSrc ){
				stack.push(src);
				root = src.getParentFile();
			}
			else {
				File [] fs = src.listFiles();
				for (int i = 0; i < fs.length; i++) {
					stack.push(fs[i]);
				}
				root = src;
			}
		} else {
			stack.push(src);
			root = src.getParentFile();
		}
		
		while ( !stack.isEmpty() ){
			File f = stack.pop();
			name = toPath(root, f);
			if ( f.isDirectory()){
				debug ("dir  : " + name);
				File [] fs = f.listFiles();
				for (int i = 0; i < fs.length; i++) {
					if ( fs[i].isDirectory() ) stack.push(fs[i]);
					else stack.add(0, fs[i]);
				}
			} else {
				debug("file : " + name);
				ze = new ZipArchiveEntry(name);
				zos.putArchiveEntry(ze);
				fis = new FileInputStream(f);
				while ( (length = fis.read(buf, 0, buf.length)) >= 0 ){
					zos.write(buf, 0, length);
				}
				fis.close();
				zos.closeArchiveEntry();
			}
		}
		zos.close();
	}
	private static String toPath(File root, File dir){
		String path = dir.getAbsolutePath();
		path = path.substring(root.getAbsolutePath().length()).replace(File.separatorChar, '/');
		if ( path.startsWith("/")) path = path.substring(1);
		if ( dir.isDirectory() && !path.endsWith("/")) path += "/" ;
		return path ;
	}
	private static void debug(String msg){
		if( debug ) System.out.println(msg);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//## jaehunpark
	
	//압축풀기전 모든 파일과 폴더이름들을 리턴 
		public static List<String> scanFiles(File zipFile) throws IOException{
	    	ZipArchiveInputStream zis ;
			ZipArchiveEntry entry ;
			String name ;
			File target ;
			int nWritten = 0;
			BufferedOutputStream bos ;
			byte [] buf = new byte[1024 * 8];
	    	List<String> list = new ArrayList<String>();
			InputStream fis = new FileInputStream(zipFile);
			
			zis = new ZipArchiveInputStream(fis, Charset.defaultCharset().name(), false);
			while ( (entry = zis.getNextZipEntry()) != null ){
				name = entry.getName();
				list.add(name);
			}
			zis.close();
			
			return list;
		}
		
		
		//압축풀기전 폴더존재유무 검사
		public static boolean isFolderExsist(File zipFile) throws IOException{
	    	ZipArchiveInputStream zis ;
			ZipArchiveEntry entry ;
			String name ;
	    	
			InputStream fis = new FileInputStream(zipFile);
			zis = new ZipArchiveInputStream(fis, Charset.defaultCharset().name(), false);
			while ( (entry = zis.getNextZipEntry()) != null ){
				name = entry.getName();
				if(entry.isDirectory()) return true;
			}
			zis.close();
			return false;
		}
		
		//zip파일의 모든 file 객체를 가져온다(압축풀지않고 파일을 조사하여 file 객체를리턴함)
	  	public static List<File> getFilesByGlancing(File zipFile) throws IOException{
	  		List<File> list = new ArrayList<File>();
	  		ZipArchiveEntry entry ;
			InputStream fis = new FileInputStream(zipFile);
			ZipArchiveInputStream zis = new ZipArchiveInputStream(fis, Charset.defaultCharset().name(), false);
			
			while ( (entry = zis.getNextZipEntry()) != null ){
				String name = entry.getName();
				String extension = name.substring(name.lastIndexOf("."));
				File tmpFile = BaseFileUtil.convertStreamToTmpFile(zis,extension);
				//System.out.println(name+" 임시로 만들 파일");
				list.add(tmpFile);
			}
			zis.close();
			
			return list;
	  	}
	  	
	  
}
