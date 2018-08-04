package common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.LoggerFactory;
public class StringUtil {

	/**
     * double value --> format string
     * @param   v_value   <code>double value</code>.
     * @return  String
     */
    public static String formatDouble(double v_value) throws Exception {
        return formatDouble(v_value, "###,###,##0.00");
    }

    /**
     * double value --> format string
     * @param   v_value   <code>double value</code>.
     * @param   v_type    <code>return type</code>.
     * @return  String
     */
    public static String formatDouble(double v_value, String v_type) throws Exception {
        DecimalFormat displayDouble = new DecimalFormat(v_type);
        String v_formatvalue = "";
        try {
            v_formatvalue = displayDouble.format(v_value);
        } catch(Exception e){}
        return v_formatvalue;
    }

    /**
     * long value --> format string
     * @param   v_value   <code>long value</code>.
     * @return  String
     */
    public static String formatLong(long v_value) throws Exception {
        return formatLong(v_value, "###,###,##0");
    }

    /**
     * long value --> format string
     * @param   v_value   <code>long value</code>.
     * @param   v_type    <code>return type</code>.
     * @return  String
     */
    public static String formatLong(long v_value, String v_type) throws Exception {
        DecimalFormat displayLong = new DecimalFormat(v_type);
        String v_formatvalue = "";
        try {
            v_formatvalue = displayLong.format(v_value);
        } catch(Exception e){}
        return v_formatvalue;
    }

    /**
     * int value --> format string
     * @param   v_value   <code>int value</code>.
     * @return  String
     */
    public static String formatInt(int v_value) throws Exception {
        return formatLong(new Integer(v_value).longValue(), "###,###,##0");
    }

    /**
     * int value --> format string
     * @param   num   <code>int value</code>.
     * @param   pattern
     * @return  String
     */
    public static String formatNumber(int num, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        return (df.format((double)num)).toString();
    }

    /**
     * double value --> format string
     * @param   num         <code>double value</code>.
     * @param   pattern     <code>return type</code>.
     * @return  String
     */
    public static String formatNumber(double num, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        return (df.format(num)).toString();
    }

    /**
     * float value --> format string
     * @param   v_value   <code>float value</code>.
     * @return  String
     */
    public static String formatFloat(float v_value) throws Exception {
        return formatDouble(new Float(v_value).doubleValue(), "###,###,##0.00");
    }

    /**
     * String value --> format string
     * @param   v_value   <code>String value</code>.
     * @return  String
     */
    public static String formatString(String v_value) throws Exception {
        return formatDouble(Double.parseDouble(v_value), "###,###,##0.00");
    }

    /**
     * String value --> format string
     * @param   v_value   <code>String value</code>.
     * @param   v_type   <code>String type</code>.
     * @return  String
     */
    public static String formatString(String v_value, String v_type) throws Exception {
        return formatDouble(Double.parseDouble(v_value), v_type);
    }

    /**
     * english --> korean
     *
     * @param   en   <code>�ٲ� ���� ���ڿ�</code>.
     * @return  String  Korean
     */
    public static String asc2ksc(String en) throws Exception {
        String new_str = null;
        try{
            if (en == null) new_str ="";
            else new_str = new String(en.getBytes("8859_1"), "KSC5601");
        } catch(Exception e){}
        return new_str;
    }

    /**
     * ���� --> �ѱ�
     *
     * @param   en   <code>�ٲ� ���� ���ڹ迭</code>.
     * @return  String[]  Korean
     */
    public static String[] asc2ksc(String[] en) throws Exception {
        String[] new_str = null;
        try{
            new_str = new String[en.length];
            for(int i=0;i<en.length;i++)
            new_str[i] = asc2ksc(en[i]);
        } catch(Exception e){}
        return new_str;
    }

    /**
     * Korean --> English
     *
     * @param   ko   <code>�ٲ� �ѱ� ���ڿ�</code>.
     * @return  String
     */
    public static String ksc2asc(String ko) throws Exception {
        String new_str = null;
        try{
            new_str = new String(ko.getBytes("KSC5601"), "8859_1");
        } catch(Exception e){}
        return new_str;
    }

    /**
     * Korean --> English
     *
     * @param   ko   <code>�ٲ� �ѱ� ���ڹ迭</code>.
     * @return  String[]
     */
    @SuppressWarnings("null")
	public static String[] ksc2asc(String[] ko) throws Exception {
        String[] new_str = null;
        try{
            for(int i=0;i<ko.length;i++)
            new_str[i] = ksc2asc(ko[i]);
        } catch(Exception e){}
        return new_str;
    }

    /**
     * �ѱ� --> ����
     *
     * @param   s   <code>�ٲ� �ѱ� ���ڿ�</code>.
     * @param   from   <code>from</code>.
     * @param   to   <code>to</code>.
     * @return  String
     */
    public static String replace(String s, String from, String to) throws Exception {
        if (s == null) return null;

        String result = s;
        try {
            if (!from.equals(to)) {     // from �� to �� �ٸ���
                int index = result.indexOf(from);
                int length = to.length();

                while (index >= 0) {
                    if (index == 0)
                        result = to + result.substring(from.length());
                    else
                        result = result.substring(0,index) + to + result.substring(index+from.length());

                    index = result.indexOf(from,index+length);
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    public static String getNullToBlank(String src){
        if(src == null){
        	src = "";
        	return src;
        }else{
        	return src;
        }
    }
    public static String getNullToHyphen(String src){
        if(src == null){
        	src = "-";
        	return src;
        }else if(equals(src,"")){
        	src = "-";
        	return src;
    	}else{
        	return src;
        }
    }

    public static String getNullToBlank(int nSrc) throws Exception {
    	String src;
        if(nSrc == 0){
        	src = "";
        	return src;
        }else{
        	return toString(nSrc);
        }
    }
    public static String getNullToHyphen(int nSrc) throws Exception {
    	String src;
        if(nSrc == 0){
        	src = "-";
        	return src;
        }else{
        	return toString(nSrc);
        }
    }

    public static String handleBK(String src){
        if(src == null || isEqualString(src,"")){
        	src = "null";
        	return src;
        }else{
        	return src;
        }
    }

    public static String handleCR(String src){
    	int nPos1 = src.indexOf("\r\n");
    	if(nPos1>=0){
    		src = getReplace(src, "\r\n", "'||chr(10)||".concat(System.getProperty("line.separator")).concat("'"));
    	}else{
    		int nPos2 = src.indexOf("\n");
    		if(nPos2>=0){
    			src = StringUtil.getReplace(src, "\n", "'||chr(10)||".concat(System.getProperty("line.separator")).concat("'"));
    		}
    	}

    	//getReplace(src, System.getProperty("line.separator"), "'||chr(10)||"+System.getProperty("line.separator")+"'");

    	return "'".concat(src).concat("'");
    }

    public static String getBlankNullToZero(String src){
        if(src == null){
        	src = "0";
        	return src;
        }else if(isEqualString(src,"")){
        	src = "0";
        	return src;
        }else{
        	return src;
        }
    }

    public static String chgStr(String src, String cpr, String val){
    	src = getNullToBlank(src);

    	if(isEqualString(src,cpr)){
    		src = val;
    	}

    	return src;
    }

    public static String getNullToNo(String src){
        if(src == null){
        	src = "N";
        	return src;
        }else{
        	return src;
        }
    }

    /**
     *
     * @param   str         <code>��</code>.
     * @param   separate    <code>������</code>.
     * @return  String[]
     */
	public static String[] parseString(String str, String separate)
    {
		if(str==null)
			str="";

		List<String> strList = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(str, separate);
        String s = null;

		while( st.hasMoreTokens() )
        {
            s = st.nextToken().trim();

            strList.add( new String( s ) );
        }

		String[] returnStr = new String[strList.size()];
		for(int i=0 ; i<strList.size(); i++){
			returnStr[i] = strList.get(i);
		}

		return returnStr;
    }

    /**
     * ���ڿ��� trim �Ѵ�. �ҽ����� String.trim() �� ������� �ʴ´�.
     *
     * @param   s   <code>�ٲ� �ѱ� ���ڿ�</code>.
     * @return  String
     */
    public static String trim(String s) throws Exception {
        if (s == null) return null;

        String result = null;
        try {
            result = s.trim();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    /**
     * �ڹ��� substring �� �Լ��� ����, �ҽ����� String.substring() �� ������� �ʴ´�.
     * ��, �ҽ����� substring �� ����ϴ°��� �����߰��� ��ư� �ϴ� ������ �ǹǷ� ���� ����� ���� �� �Լ��� ����Ѵ�.
     * @param   s   <code>�ٲ� �ѱ� ���ڿ�</code>.
     * @param   start   <code>����</code>.
     * @param   end   <code>��</code>.
     * @return  String
     */
    public static String subString(String s,int start,int end) throws Exception {
        if (s == null) return null;

        String result = null;
        try {
            result = s.substring(start,end);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    /**
     * �ڹ��� substring �� �Լ��� ����, �ҽ����� String.substring() �� ������� �ʴ´�.
     * ��, �ҽ����� substring �� ����ϴ°��� �����߰��� ��ư� �ϴ� ������ �ǹǷ� ���� ����� ���� �� �Լ��� ����Ѵ�.
     * @param   s   <code>�ٲ� �ѱ� ���ڿ�</code>.
     * @param   start   <code>����</code>.
     * @return  String
     */
    public static String subString(String s,int start) throws Exception {
        if (s == null) return null;

        String result = null;
        try {
            result = s.substring(start);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    /**
     * asp �� mid �Լ��� �����Ͽ���. (������.start �� 1 base �� �ƴϰ� 0 base)
     * @param   s   <code>�ٲ� �ѱ� ���ڿ�</code>.
     * @param   start   <code>����</code>.
     * @param   count   <code>count</code>.
     * @return  String
     */
    public static String midString(String s,int start,int count) throws Exception {
        if (s == null) return null;

        String result = null;
        try {
            if (start >= s.length())
                result = "";
            else if (s.length() < start+count)
                result = s.substring(start,s.length());
            else
                result = s.substring(start,start+count);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    /**
     * asp �� mid �Լ��� �����Ͽ���. (������.start �� 1 base �� �ƴϰ� 0 base)
     * @param   s   <code>�ٲ� �ѱ� ���ڿ�</code>.
     * @param   start   <code>����</code>.
     * @return  String
     */
    public static String midString(String s,int start) throws Exception {
        return midString(s,start,s.length());
    }

    /**
     * asp �� right �Լ��� �����Ͽ���.
     * @param   s   <code>�ٲ� �ѱ� ���ڿ�</code>.
     * @param   count   <code>count</code>.
     * @return  String
     */
    public static String rightString(String s, int count) throws Exception {
        if (s == null) return null;

        String result = null;
        try {
            if (count == 0) // ������ 0 �̸� �����
                result = "";
            else if (count > s.length()) // ���ڿ� ���̺��� ũ�� ���ڿ� ��ü��
                result = s;
            else
                result = s.substring(s.length()-count,s.length()); // ������ count ��ŭ ����
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    /**
     * asp �� left �Լ��� �����Ͽ���.
     * @param   s   <code>�ٲ� �ѱ� ���ڿ�</code>.
     * @param   count   <code>count</code>.
     * @return  String
     */
    public static String leftString(String s, int count) throws Exception {
        if (s == null) return null;

        String result = null;
        try {
            if (count == 0) // ������ 0 �̸� �����
                result = "";
            else if (count > s.length()) // ���ڿ� ���̺��� ũ�� ���ڿ� ��ü��
                result = s;
            else
                result = s.substring(0,count); // ���� count ��ŭ ����
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    /**
     * asp �� CInt �Լ�
     * @param   s   <code>�ٲ� �ѱ� ���ڿ�</code>.
     * @return  int
     */
    public static int toInt(String s) throws Exception {
        int i = 0;

        try {
        	if(equals(s,"")) s = "0";

        	i = Integer.parseInt(s.replaceAll("[^0-9]+",""));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return i;
    }
    public static double toDouble(String s) throws Exception {
        double i = 0.00;

        try {
        	if(equals(s,"")) s = "0.00";

        	i = Double.parseDouble(s.replaceAll("[^0-9]+",""));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return i;
    }

    public static int toInt(Object obj) throws Exception {
        int i = 0;

        try {
            i = Integer.parseInt(ObjectUtils.toString(obj));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return i;
    }
    public static double toDouble(Object obj) throws Exception {
        double i = 0.00;

        try {
            i = Double.parseDouble(ObjectUtils.toString(obj));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return i;
    }

    /** float --> int
     * @param   float
     * @return  int
     */
    public static int toInt(float f) throws Exception {
        int i = 0 ;

        try {
            i = (new Float(f)).intValue();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return i;
    }

    /** double to int
     * @param   double
     * @return  int
     */
    public static int toInt(double d) throws Exception {
        int i = 0 ;

        try {
            i = (new Double(d)).intValue();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return i;
    }

    /** String �� long ������ ��ȯ�Ѵ�
     * @param   String
     * @return  long
     */
    public static long toLong(String s) throws Exception {
        long l=0L;

        try {
            l = Long.parseLong(s);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return l;
    }

    /** asp �� CStr �Լ�
     * @param   int
     * @return  String
     */
    public static String toString(int i) throws Exception {
        String s = "";

        try {
            s = String.valueOf(i);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return s;
    }
    /** double to string
     * @param   double
     * @return  String
     */
    public static String toString(double d) throws Exception {
        String s = "";

        try {
            s = String.valueOf(d);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return s;
    }

    /** ���ڿ��� null ���θ� üũ�Ѵ�.
     * @param   String  ���ڿ�
     * @return  String
     */
    public static String checkNull(String s) {
        if (s == null)  {
            return "";
        }  else  {
            return s;
        }
    }

    /** ���ڿ��� null ���θ� üũ�Ѵ�.
     * @param   String  ���ڿ�
     * @return  String
     */
    public static String checkNull(String s, String r) {
        if (s == null)  {
            return r;
        }  else  {
            return s;
        }
    }

    /** ������ �����ٶ� ���ѵ� ���̸� �ʰ��ϸ� �޺κ��� ¥���� "..." ���� ��ġ�Ѵ�.
     * @param   String  ���ڿ�
     * @param   int     �ڸ� ���ڿ� ����
     * @return  String
     */
    public static String formatTitle(String title,int max) {
        if (title==null) return null;

        if (title.length() <= max)
            return title;
        else
            return "<font title=\""+title+"\">"+title.substring(0,max)+"...</font>";
    }

    /** ������ �����ٶ� ���ѵ� ���̸� �ʰ��ϸ� �޺κ��� ¥���� "..." ���� ��ġ�Ѵ�.
     * @param   String  ���ڿ�
     * @param   int     �ڸ� ���ڿ� ����
     * @return  String
     */
    public static String cutString(String str, int max) {
        if (str==null) return null;

        if (str.length() <= max)
            return str;
        else
            return str.substring(0,max)+"...";
    }

    /** �ֹι�ȣ�� �����ȣ ���̱�
     * @param   String
     * @param   int
     * @return  String
     */
    public static String formatJumin(String p_string,int indx) {
        if (p_string==null) return "";

        if (p_string.length() <= indx)
            return p_string;
        else
            return p_string.substring(0,indx)+"-" + p_string.substring(indx);
    }

    /** br to tag
     * @param   String  �ٲ� ���ڿ�
     * @return  String  ���н��� ����Ű ���� <br>�� ġȯ�Ͽ� �����Ѵ�.
     */
    public static String convertBr(String content) {
        int             length =content.length();
        StringBuffer    buffer =new StringBuffer();

        for(int i=0; i<length;++i) {
            String comp =content.substring(i, i+1);
            if (("\r").compareTo(comp)==0) {
                comp = content.substring(++i, i+1);
                if ("\n".compareTo(comp)==0)
                    buffer.append("<br>\r");
                else
                    buffer.append("\r");
            }
            buffer.append(comp);
        }
        return buffer.toString();
    }

    /**
     * Space, CR, LF�� HTML�±׷� �ٲپ��ش�.
     * @param String strVal �ٲ� ���ڿ�
     * @return String
     */
    public static String replaceHTML(String strVal) {
        String strReturn = "";
        String strTemp = "";

        int size = strVal.length();

        for(int i = 0; i < size; i ++) {
            if(strVal.charAt(i) == '\n')    { strTemp += " <br>\n"; continue; }
            if(strVal.charAt(i) == '\r')    { continue; }
            if(i < size-1)
                if(strVal.substring(i, i+2).equals("  "))   { strTemp += "&nbsp"; continue; }

            strTemp += String.valueOf(strVal.charAt(i));
        }


        strReturn = strTemp;

        return strReturn;
    }

    /**
     * Space, CR, LF�� HTML�±׷� �ٲپ��ش�.
     * @param String strVal �ٲ� ���ڿ�
     * @return String
     */
    public static String replaceHTML2(String strVal) {
        String strReturn = "";
        String strTemp = "";

        int size = strVal.length();

        for(int i = 0; i < size; i ++) {
            if(strVal.charAt(i) == '\n')    { strTemp += " <br>"; continue; }
            if(strVal.charAt(i) == '\r')    { continue; }
            if(i < size-1)
                if(strVal.substring(i, i+2).equals("  "))   { strTemp += "&nbsp"; continue; }

            strTemp += String.valueOf(strVal.charAt(i));
        }


        strReturn = strTemp;

        return strReturn;
    }

    /**
     * ����� ���� ������ �տ� ">"�� ÷���Ѵ�.
     * @param String strVal �ٲ� ���ڿ�
     * @return  String
     */
    public static String replaceReply(String strVal) {
        String strReturn = ">";

        int size = strVal.length();

        for(int i = 0; i < size; i ++) {
            if(strVal.charAt(i) == '\n')    strReturn += "\n>";
            else if(strVal.charAt(i) == '\r') {}
            else                            strReturn += String.valueOf(strVal.charAt(i));
        }

        return strReturn;
    }


    public static String isSelected(String strVal, String strObj) {
        String strSelected = "";

        if (strVal.equals(strObj)) strSelected = " selected";

        return strSelected;
    }

    public static String isChecked(String strVal, String strObj) {
        String strSelected = "";

        if (strVal.equals(strObj)) strSelected = " checked";

        return strSelected;
    }

    /**
     * <pre>
     * str���� rep�� �ش��ϴ� String�� tok�� replace
     * </pre>
     *
     * @param str -
     * @param rep -
     * @param tok -
     *
     * @return String
     */
    public static String getReplace(String str, String rep, String tok) {
        String retStr = "";

        if (str == null || "".equals(str))
            return "";

        for (int i = 0, j = 0;(j = str.indexOf(rep, i)) > -1; i = j + rep.length()) {
            retStr += (str.substring(i, j) + tok);
        }
        return (str.indexOf(rep) == -1)
            ? str
            : retStr + str.substring(str.lastIndexOf(rep) + rep.length(), str.length());
    }

    /**
     * <pre>
     * HTML�� ����Ͽ� �Ϻ� Ư�����ڸ� ��ȯ
     * & --->> &amp;
     * < --->> &lt;
     * > --->> &gt;
     * ' --->> &acute;
     * " --->> &quot;
     * | --->> &brvbar;
     * </pre>
     *
     * @param str -
     *
     * @return String
     */
    public static String getSpecialCharacters(String str) {
        str = getReplace(str, "&", "&amp;");
        str = getReplace(str, "<", "&lt;");
        str = getReplace(str, ">", "&gt;");
        str = getReplace(str, "'", "&acute;");
        str = getReplace(str, "\"", "&quot;");
        str = getReplace(str, "|", "&brvbar;");
        str = getReplace(str, " ", "&nbsp;");

        str = getReplace(str, "\n", "<BR>");
        str = getReplace(str, "\r", "");

        return str;
    }

    /**
     * 두개의 스트링이 같은 값인지 비교.
     * <br>
     * @param
     * @return
     * @throws
     */
    public static boolean isEqualString(String val1, String val2) {
    	if(val1==null || val2==null)
    		return false;
    	else
    		return val1.equals(val2);
    }

    /**
     * 두개의 스트링이 같은 값인지 비교.
     * <br>
     * @param
     * @return
     * @throws
     */
    public static boolean equals(String val1, String val2) {
/*
    	if(val1==null)
    		val1 = "";
    	if(val2==null)
    		val2 = "";

    	return val1.equals(val2);
*/
    	if(val1==null || val2==null)
    		return false;
    	else
    		return val1.equals(val2);
    }

    /**
     * Exception으로 받은 StackTraceElement로부터 로그 Text를 만들어 낸다.
     * @param
     * @return long
     * @throws
     */
    public static String getlogText(Exception e) {
    	Writer writer = new StringWriter();
    	e.printStackTrace(new PrintWriter(writer));

    	return writer.toString();
    }

    /**
     * Exception으로 받은 StackTraceElement로부터 로그 Text를 만들어 낸다.
     * @param
     * @return long
     * @throws
     */
    public static String getlogText(String strlogTitle, Exception e) {
    	Writer writer = new StringWriter();
    	e.printStackTrace(new PrintWriter(writer));

    	return strlogTitle.concat("\n").concat(writer.toString());
    }

    /**
     * Exception으로 받은 StackTraceElement로부터 로그 Text를 만들어 낸다.
     * @param
     * @return long
     * @throws
     */
    public static String getlogText(String strlogTitle, Throwable e) {
    	Writer writer = new StringWriter();
    	e.printStackTrace(new PrintWriter(writer));

    	return strlogTitle.concat("\n").concat(writer.toString());
    }

    /**
     * Exception으로 받은 StackTraceElement로부터 로그 Text를 만들어 낸다.
     * @param
     * @return long
     * @throws
     */
    public static Map<String,String> getlogTextMap(Exception e) {
    	Writer writer = new StringWriter();
    	e.printStackTrace(new PrintWriter(writer));

    	Map<String,String> retMap = new HashMap<String,String>();
    	retMap.put("exception", writer.toString());

    	return retMap;
    }

    /**
     * Exception으로 받은 StackTraceElement로부터 로그 Text를 만들어 낸다.
     * @param
     * @return long
     * @throws
     */
    public static List<Map<String,String>> getlogTextList(Exception e) {
    	Writer writer = new StringWriter();
    	e.printStackTrace(new PrintWriter(writer));

    	Map<String,String> retMap = new HashMap<String,String>();
    	retMap.put("exception", writer.toString());

    	List<Map<String,String>> retList = new ArrayList<Map<String,String>>();

    	return retList;
    }

    /**
     * Exception으로 받은 StackTraceElement로부터 로그 Text를 만들어 낸다.
     * @param
     * @return long
     * @throws
     */
    public static String getlogTextHtml(Exception e) {
    	Writer writer = new StringWriter();
    	e.printStackTrace(new PrintWriter(writer));

    	return getSpecialCharacters(writer.toString());
    }

    /**
     * Exception으로 받은 StackTraceElement로부터 로그 Text를 만들어 낸다.
     * @param
     * @return long
     * @throws
     */
    public static Map<String,String> getlogTextMapHtml(Exception e) {
    	Writer writer = new StringWriter();
    	e.printStackTrace(new PrintWriter(writer));

    	Map<String,String> retMap = new HashMap<String,String>();
    	retMap.put("exception", getSpecialCharacters(writer.toString()));

    	return retMap;
    }

    /**
     * Exception으로 받은 StackTraceElement로부터 로그 Text를 만들어 낸다.
     * @param
     * @return long
     * @throws
     */
    public static List<Map<String,String>> getlogTextListHtml(Exception e) {
    	Writer writer = new StringWriter();
    	e.printStackTrace(new PrintWriter(writer));

    	Map<String,String> retMap = new HashMap<String,String>();
    	retMap.put("exception", getSpecialCharacters(writer.toString()));

    	List<Map<String,String>> retList = new ArrayList<Map<String,String>>();

    	return retList;
    }

    /**
     * Map안의 element 타입을 모두 String으로 변환
     * @param	Map map
     * @return	Map
     * @throws
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String,String> convertMapElementToString(Object map) {
    	Map retMap = null;
    	if(map != null){
    		retMap = new LinkedHashMap();

    		Set keySet = ((Map)map).keySet();
			Iterator it = keySet.iterator();

			while (it.hasNext()) {
				Object element = it.next();
				String keyName = (String) element; // Map의 키값들을 꺼냈다...

				if(keyName!=null && !StringUtil.isEqualString("",keyName)){
					if(((Map)map).get(keyName)!=null){
						retMap.put(keyName, ((Map)map).get(keyName).toString());
					}else{
						retMap.put(keyName, "");
					}
				}
			}
    	}
    	return retMap;
    }

    /**
     * Map안의 element 타입을 모두 String으로 변환
     * @param	Map map
     * @return	Map
     * @throws
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String,String> convertMapElementToString(Map map) {
    	Map retMap = null;
    	if(map != null){
    		retMap = new LinkedHashMap();

    		Set keySet = map.keySet();
			Iterator it = keySet.iterator();

			while (it.hasNext()) {
				Object element = it.next();
				String keyName = (String) element; // Map의 키값들을 꺼냈다...

				if(keyName!=null && !StringUtil.isEqualString("",keyName)){
					if(map.get(keyName)!=null){
						retMap.put(keyName, map.get(keyName).toString());
					}else{
						retMap.put(keyName, "");
					}
				}
			}
    	}
    	return retMap;
    }

    /**
     * List안의 Map을 꺼내 Map안의 element 타입을 모두 String으로 변환
     * @param	List list
     * @return	List
     * @throws
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map<String,String>> convertListElementToString(List list) {
		List retList = null;
    	if(list != null){
    		retList = new ArrayList();

    		for(int i=0,n=list.size(); i<n; i++){
    			retList.add(convertMapElementToString((Map)list.get(i)));
    		}
    	}
    	return retList;
    }

    /**
     * Map안의 element 타입을 모두 String으로 변환
     * @param	Map map
     * @return	Map
     * @throws
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String,String> convertMapElementToStringForDistribute(Map map) {
    	Map retMap = null;
    	if(map != null){
    		retMap = new LinkedHashMap();

    		Set keySet = map.keySet();
			Iterator it = keySet.iterator();

			while (it.hasNext()) {
				Object element = it.next();
				String keyName = (String) element; // Map의 키값들을 꺼냈다...

				if(keyName!=null && !StringUtil.isEqualString("",keyName)){
					if(map.get(keyName)!=null){
						// 값중에 '을 ''으로 바꾸어 준다.
						try {
							retMap.put(keyName, StringUtil.replace(map.get(keyName).toString(), "'", "''"));
						} catch (Exception e) {
							retMap.put(keyName, map.get(keyName).toString());
						}
					}else{
						// String이 아니면 null로 표시...
						retMap.put(keyName, "");
					}
				}
			}
    	}
    	return retMap;
    }

    /**
     * List안의 Map을 꺼내 Map안의 element 타입을 모두 String으로 변환
     * @param	List list
     * @return	List
     * @throws
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String,String>> convertListElementToStringForDistribute(List list) {
    	List retList = null;
    	if(list != null){
    		retList = new ArrayList();

    		for(int i=0,n=list.size(); i<n; i++){
    			retList.add(convertMapElementToStringForDistribute((Map)list.get(i)));
    		}
    	}
    	return retList;
    }

	/**
     * String을 대문자로 치환하여 리턴.
     * <br>
     * @param
     * @return
     * @throws
     */
    public static String toUpperCaseString(String value) {
    	if(value==null)
    		return value;
    	else
    		return value.toUpperCase();
    }

	/**
     * String을 소문자로 치환하여 리턴.
     * <br>
     * @param
     * @return
     * @throws
     */
    public static String toLowerCaseString(String value) {
    	if(value==null)
    		return value;
    	else
    		return value.toLowerCase();
    }

	/**
     * String ==> UTF8 byteArray
     * <br>
     * @param
     * @return
     * @throws
     */
    public static byte[] stringToByteArray(String value) {
    	byte[] new_bytes = null;

    	try {
            if (value == null)
            	return new_bytes;
			else
				new_bytes = value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			LoggerFactory.getLogger("error").error("[stringToByteArray(String value)] UnsupportedEncodingException :", e);
		}

        return new_bytes;
    }

	/**
     * byteArray ==> UTF-8 String
     * <br>
     * @param
     * @return
     * @throws
     */
    public static String byteArrayToString(byte[] arrByte) {
        String new_str = null;

    	try {
            if (arrByte == null)
            	return new_str;
			else
				new_str = new String(arrByte, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LoggerFactory.getLogger("error").error("[byteArrayToString(byte[] arrByte)] UnsupportedEncodingException :", e);
		}

        return new_str;
    }


    /**
     * <br>
     * @param String value
     * @param String value
     * @return String
     * @throws Exception
     */
    public static String formatRuntime(String value) throws Exception {
    	value = replaceAll(value,":","");
    	value = replaceAll(value,"-","");
    	value = replaceAll(value,",","");
    	value = replaceAll(value,";","");

    	if(value.length()<6){
			return value;
		}else{
			return	subString(value,0,2).concat(":").concat(
						subString(value,2,4)).concat(":").concat(
							subString(value,4,6));
		}
    }

    /**
     * 20100122111111 ==> 2010-01-22 11:11:11 로 변환.
     * <br>
     * @param String value
     * @param String value
     * @return String
     * @throws Exception
     */
    public static String formatDate(String value) throws Exception {
		if(value.length()!=14){
			return value;
		}else{
			return	subString(value,0,4).concat("-").concat(
						subString(value,4,6)).concat("-").concat(
							subString(value,6,8)).concat(" ").concat(
								subString(value,8,10)).concat(":").concat(
									subString(value,10,12)).concat(":").concat(
										subString(value,12,14));
		}
    }

    /**
     * 20100122 ==> 2010-01-22 로 변환.
     * <br>
     * @param String value
     * @param String value
     * @return String
     * @throws Exception
     */
    public static String formatDay(String value) throws Exception {
    	if(value==null)
    		return "";

    	value = trim(value);

    	if(value.length()==8 ){
			return	subString(value,0,4).concat("-").concat(
						subString(value,4,6)).concat("-").concat(
							subString(value,6,8));
		}else if(value.length()==6 ){
			return	subString(value,0,4).concat("-").concat(
						subString(value,4,6));
		}else{
			return value;
		}
    }
    public static String formatDay() throws Exception {
    	Calendar cal = Calendar.getInstance();
    	String strYear = toString(cal.get(Calendar.YEAR));
    	String strMonth = toString(cal.get(Calendar.MONTH)+1);
    	if(strMonth.length()==1) strMonth = "0"+strMonth;
    	String strDate = toString(cal.get(Calendar.DATE));
    	if(strDate.length()==1) strDate = "0"+strDate;

    	return strYear + "-" + strMonth + "-" + strDate;
    }
    public static String formatDayDot() throws Exception {
    	Calendar cal = Calendar.getInstance();
    	String strYear = toString(cal.get(Calendar.YEAR));
    	String strMonth = toString(cal.get(Calendar.MONTH)+1);
    	if(strMonth.length()==1) strMonth = "0"+strMonth;
    	String strDate = toString(cal.get(Calendar.DATE));
    	if(strDate.length()==1) strDate = "0"+strDate;

    	return strYear + "." + strMonth + "." + strDate;
    }

    /**
     * 20100122 ==> 2010-01-22 로 변환.
     * <br>
     * @param String value
     * @param String value
     * @return String
     * @throws Exception
     */
    public static String formatDayDot(String value) throws Exception {
    	if(value==null)
    		return "";

    	value = trim(value);

    	if(value.length()==8 ){
			return	subString(value,0,4).concat(".").concat(
						subString(value,4,6)).concat(".").concat(
							subString(value,6,8));
		}else if(value.length()==6 ){
			return	subString(value,0,4).concat(".").concat(
						subString(value,4,6));
		}else{
			return value;
		}
    }

    /**
     * 20100122 ==> 2010-01-22 로 변환.
     * <br>
     * @param String value
     * @param String value
     * @return String
     * @throws Exception
     */
    public static String formatDayKr(String value) throws Exception {
    	if(value==null)
    		return "";

    	value = trim(value);

    	if(value.length()==8 ){
			return	subString(value,0,4).concat("년 ").concat(
						subString(value,4,6)).concat("월 ").concat(
							subString(value,6,8).concat("일"));
		}else if(value.length()==6 ){
			return	subString(value,0,4).concat("년 ").concat(
						subString(value,4,6).concat("월 "));
		}else if(value.length()==4 ){
			return	value.concat("년 ");
		}else{
			return value;
		}
    }

    /**
     * 20100122 ==> 10-01-22 로 변환.
     * <br>
     * @param String value
     * @param String value
     * @return String
     * @throws Exception
     */
    public static String formatDay6(String value) throws Exception {
    	if(value==null)
    		return "";

    	value = trim(value);

    	if(value.length()==8 ){
			return	subString(value,0,4).concat("-").concat(
						subString(value,4,6)).concat("-").concat(
							subString(value,6,8));
		}else if(value.length()==6 ){
			return	subString(value,0,4).concat("-").concat(
						subString(value,4,6));
		}else{
			return value;
		}
    }

    /**
     * 문자열이 null이면 ""로 치환하여 준다.
     * <br>
     * @param String src
     * @return String
     * @throws
     */
    public static Object getNullToBlank(Object obj){
        if(obj == null){
        	obj = "";
        	return obj;
        }else{
        	return obj;
        }
    }

    /**
     * 문자열이 ""이면 0으로 치환하여 준다.
     * <br>
     * @param String src
     * @return String
     * @throws
     */
    public static String getBlankToZero(String src){
        if(src == null || isEqualString(src,"")){
        	src = "0";
        	return src;
        }else{
        	return src;
        }
    }

    /**
     * 문자열이 "0"이면 "-"로 치환하여 준다.
     * <br>
     * @param String src
     * @return String
     * @throws
     */
    public static String getZeroToMinus(String src){
        if(src == null || isEqualString(src,"") || isEqualString(src,"0")){
        	src = "-";
        	return src;
        }else{
        	return src;
        }
    }
    /**
     * 문자열이 "0"이면 "-"로 치환하여 준다.
     * <br>
     * @param String src
     * @return String
     * @throws
     */
    public static String getZeroToMinus(int src) throws Exception{
    	String retStr = "";
    	if(src == 0){
        	retStr = "-";
        	return retStr;
        }else{
        	return toString(src);
        }
    }

    /**
     * 문자열이 "0"이면 ""로 치환하여 준다.
     * <br>
     * @param String src
     * @return String
     * @throws
     */
    public static String getZeroToBlank(String src){
        if(src == null || isEqualString(src,"") || isEqualString(src,"0")){
        	src = "";
        	return src;
        }else{
        	return src;
        }
    }
    /**
     * 문자열이 "0"이면 ""로 치환하여 준다.
     * <br>
     * @param String src
     * @return String
     * @throws
     */
    public static String getZeroToBlank(int src) throws Exception{
    	String retStr = "";
    	if(src == 0){
        	retStr = "";
        	return retStr;
        }else{
        	return toString(src);
        }
    }

    /**
     * String을 Integer로 변환할때, 변환가능한지의 여부
     * <br>
     * @param String s
     * @return boolean
     * @throws Exception
     */
    public static boolean isIntegerString(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
        	return false;
        }
    }

    /**
     * String을 Integer로 변환하는 함수,
     * 에러 발생시 주어진 defVal값으로 default 리턴한다.
     * <br>
     * @param String s   <code>바꿀 한글 문자열</code>
     * @return int
     * @throws Exception
     */
    public static int toInt(String s, int defVal) {
        int i = defVal;
    	if(s==null || isEqualString(s,"")){
    		return i;
    	}

    	try {
            i = Integer.parseInt(s);
        } catch (Exception e) {
			LoggerFactory.getLogger("error").error(getlogText("[toInt(String s, int defVal)] Exception :", e));
        }

        return i;
    }

    /**
     * String을 Long으로 변환하는 함수,
     * 에러 발생시 주어진 defVal값으로 default 리턴한다.
     * <br>
     * @param String s   <code>바꿀 한글 문자열</code>
     * @return int
     * @throws Exception
     */
    public static long toLong(String s, long defVal) {
        long i = defVal;
    	if(s==null || isEqualString(s,"")){
    		return i;
    	}

    	try {
            i = Long.parseLong(s);
        } catch (Exception e) {
			LoggerFactory.getLogger("error").error(getlogText("[toInt(String s, int defVal)] Exception :", e));
        }

        return i;
    }

    /**
     * long --> int
     * <br>
     * @param float
     * @return int
     * @throws Exception
     */
    public static int toInt(long l) {
        return (new Long(l)).intValue();
    }

    /**
     * long to string
     * <br>
     * @param   double
     * @return  String
     * @throws Exception
     */
    public static String toString(long l) {
        return String.valueOf(l);
    }

    /**
     * object to string
     * <br>
     * @param   double
     * @return  String
     * @throws Exception
     */
    public static String toString(Object obj) {
        return ObjectUtils.toString(obj);
    }

    /**
     * 2010-01-28 13:13:13 의 형식으로 들어온 데이터를 20100128131313 의 형식으로 변경한다.
     * <br>
     * @param String str
     * @param String rep
     * @param String tok
     * @return String
     * @throws
     */
    public static String getReplaceTime(String str) {
        if (str == null || isEqualString("",str))
            return "";

        return getReplace(getReplace(getReplace(str, ":", ""), " ", ""), "-", "");
    }

    /**
     * 랜덤하게 양의 정수를 구한다.
     * @param
     * @return long
     * @throws
     */
    public static long getRandomNumber() {
    	Random oRandom = new Random();

    	// 랜덤하게 양의 정수(int) 를 추출한다.
    	long i = oRandom.nextInt(2147483647);

    	return i;
    }

    /**
     * 16자리의 랜덤 스트링을 만든다.
     * @param
     * @return String
     * @throws
     */
    public static String getRandomStr() {
    	String randStr =  new RandomStringBuilder().
    	           putLimitedChar(RandomStringBuilder.ALPHABET_UPPER_CASE).
    	           putLimitedChar(RandomStringBuilder.SPECIAL).
    	           putExcludedChar("=+*_^`%><?.,&$/\\\"'\"{}[]-@~:|#;()").
    	           setLength(32).build();
    	return randStr;
    }

    /**
	 * src가 null 거나 ""이면 ""값으로 리턴한다.
	 *
	 * @param src
	 *            문자열
	 * @return
	 */
	public static String nvl(String src) {
		return nvl(src, "");
	}

	/**
	 * <PRE>
	 *
	 * src가 null거나 ""이면 tgt값으로 치환하여 return한다.
	 * @param src 문자열
	 * @param tgt 치환할 문자열
	 * @return 치환된 String
	 *
	 * </PRE>
	 */
	public static String nvl(String src, String tgt) {
		String res = tgt;
		if (tgt == null)
			res = "";
		if (src == null)
			return res;
		else if (src.equals(""))
			return res;
		else
			return src;
	}

	/**
	 * Object가 null 일경우 기본문자열을 리턴한다.
	 *
	 * @param obj
	 * @param def
	 *            기본 문자열
	 * @return
	 */
	public static String nvl(Object obj) {
		return nvl(obj, "");
	}

	/**
	 * Object가 null 일경우 기본문자열을 리턴한다.
	 *
	 * @param obj
	 * @param def
	 *            기본 문자열
	 * @return
	 */
	public static String nvl(Object obj, String def) {
		if (obj == null)
			return def;
		return obj.toString();
	}

	/**
	 * 입력받은 String을 원하는 길이만큼 원하는 문자로 오른쪽을 채워주는 함수.
	 *
	 * @param str
	 *            : 입력 문자열
	 * @param len
	 *            : 채우고 싶은 길이
	 * @param pad
	 *            : 채울문자
	 * @return 채워진 문자열
	 */
	public static String rpad(String str, int len, char pad) {
		String result = str;
		int templen = len - result.getBytes().length;
		for (int i = 0; i < templen; i++) {
			result = result + pad;
		}
		return result;
	}

	/**
	 * 입력받은 String을 원하는 길이만큼 원하는 문자로 왼쪽을 채워주는 함수.
	 *
	 * @param str
	 *            : 입력 문자열
	 * @param len
	 *            : 채우고 싶은 길이
	 * @param pad
	 *            : 채울문자
	 * @return 채워진 문자열
	 */
	public static String lpad(String str, int len, char pad) {
		String result = str;
		int templen = len - result.getBytes().length;
		for (int i = 0; i < templen; i++)
			result = pad + result;
		return result;
	}

	/**
	 * <PRE>
	 *
	 * 우편번호 타입으로 변환한다. (예) 630521 --> 630-521
	 *
	 * </PRE>
	 *
	 * @param postno
	 *            우편번호
	 * @return 변환된 우편번호 문자열
	 */
	public static String makePostType(String postno) {
		if (postno == null || postno.length() == 0)
			return "";
		if (postno.length() != 6)
			return postno;
		String postno1 = postno.substring(0, 3);
		String postno2 = postno.substring(3, 6);
		return (postno1 + "-" + postno2);
	}

	/**
	 * <PRE>
	 *
	 * 전화 번호  타입으로 변환한다. (예) 0101234567 --> 010-123-4567
	 *
	 * </PRE>
	 *
	 * @param tel
	 *            전화번호
	 * @return 변환된 전화번호 문자열
	 */
	public static String makeTelType(String tel) {
		if (tel == null || tel.length() == 0)
			return "";
		String DELEMETER = "-";
		StringBuffer sb = new StringBuffer();
		String tnum = "";
		if (tel.startsWith("02")) {
			sb.append("02").append(DELEMETER);
			tnum = tel.substring(2);
		} else {
			sb.append(tel.substring(0, 3)).append(DELEMETER);
			tnum = tel.substring(3);
		}

		if (tnum.length() == 7) {
			sb.append(tnum.substring(0, 3)).append(DELEMETER).append(
					tnum.substring(3));
		} else if (tnum.length() == 8) {
			sb.append(tnum.substring(0, 4)).append(DELEMETER).append(
					tnum.substring(4));
		} else {
			return tel;
		}
		return sb.toString();
	}

	/**
	 * <PRE>
	 *
	 * 주민등록 번호 타입으로 변환한다. (예) 6305211234567 --> 630521-1234567
	 *
	 * </PRE>
	 *
	 * @param postno
	 *            주민번호
	 * @return 변환된 주민번호 문자열
	 */
	public static String makeJuminType(String jumin) {
		if (jumin == null || jumin.length() == 0)
			return "";
		if (jumin.length() != 13)
			return jumin;
		String postno1 = jumin.substring(0, 6);
		String postno2 = jumin.substring(6);
		return (postno1 + "-" + postno2);
	}

	/**
	 * <PRE>
	 *
	 * Post Code의 앞 세자리를 반환한다. (예) 123456 --> 123
	 *
	 * </PRE>
	 *
	 * @param postcode
	 *            우편번호 문자열
	 * @return 우편번호 앞 세자리 문자열
	 */
	public static String getPostCode1(String postcode) {
		if (postcode == null || postcode.length() == 0)
			return "";

		if (postcode.length() == 6)
			return postcode.substring(0, 3);
		else
			return postcode;
	}

	/**
	 * <PRE>
	 *
	 * Post Code의 뒷 세자리를 반환한다. (예) 123456 --> 456
	 *
	 * </PRE>
	 *
	 * @param postcode
	 *            우편번호 문자열
	 * @return 우편번호 뒷 세자리 문자열
	 */
	public static String getPostCode2(String postcode) {
		if (postcode == null || postcode.length() == 0)
			return "";

		if (postcode.length() == 6)
			return postcode.substring(3);
		else
			return postcode;
	}

	/**
	 * <PRE>
	 *
	 * 전화 번호의 국번을 반환한다. (예) 0101234567 --> 010,  01112345678 -> 011
	 *
	 * </PRE>
	 *
	 * @param tel
	 *            전화번호
	 * @return 국번 문자열
	 */
	public static String getTelNum1(String tel) {
		if (tel == null || tel.length() == 0)
			return "";
		if (tel.startsWith("02")) {
			return "02";
		} else {
			return tel.substring(0, 3);
		}
	}

	/**
	 * <PRE>
	 *
	 * 전화 번호의 앞번호를 반환한다. (예) 0101234567 --> 123,  01112345678 -> 1234
	 *
	 * </PRE>
	 *
	 * @param tel
	 *            전화번호
	 * @return 전화번호 앞번호 문자열
	 */
	public static String getTelNum2(String tel) {
		if (tel == null || tel.length() == 0)
			return "";
		if (tel.startsWith("02")) {
			if ((tel.length() - 2) == 7)
				return tel.substring(2, 5);
			else if ((tel.length() - 2) == 8)
				return tel.substring(2, 6);
			else
				return tel;
		} else {
			if ((tel.length() - 3) == 7)
				return tel.substring(3, 6);
			else if ((tel.length() - 3) == 8)
				return tel.substring(3, 7);
			else
				return tel;
		}
	}

	/**
	 * <PRE>
	 *
	 * 전화 번호의 뒷번호를 반환한다. (예) 0101234567 --> 4567,  01112345678 -> 5678
	 *
	 * </PRE>
	 *
	 * @param tel
	 *            전화번호
	 * @return 전화번호 뒷번호 문자열
	 */
	public static String getTelNum3(String tel) {

		if (tel == null || tel.length() == 0)
			return "";
		if (tel.startsWith("02")) {
			if ((tel.length() - 2) == 7) {
				// logger.debug( tel.substring( 5 ) );
				return tel.substring(5);
			} else if ((tel.length() - 2) == 8) {
				// logger.debug( tel.substring( 6 ) );
				return tel.substring(6);
			} else
				return tel;
		} else {
			if ((tel.length() - 3) == 7) {
				// logger.debug( tel.substring( 6 ) );
				return tel.substring(6);
			} else if ((tel.length() - 3) == 8) {
				// logger.debug( tel.substring( 7 ) );
				return tel.substring(7);
			} else
				return tel;
		}
	}

	/**
	 * <PRE>
	 *
	 * 성별 Type을 받아서 "남자, 여자"를 반환한다. (예) W -> 여자 , femail -> 여자 , m -> 남자
	 *
	 * </PRE>
	 *
	 * @param gender
	 * @return "남자/여자"
	 */
	public static String getGender(String gender) {
		if (gender == null || gender.length() == 0)
			return "";

		String gen = gender.toUpperCase();
		if (gen.equals("F") || gen.equals("FEMAIL") || gen.equals("W") || gender.equals("WOMAN") || gender.equals("2")) {
			return "여";
		} else if (gen.equals("M") || gen.equals("MAIL") || gender.equals("MAN") || gender.equals("1")) {
			return "남";
		} else {
			return gender;
		}
	}

	/**
	 * <pre>
	 *  space 를 _로 변환해서 리턴한다(get방식에서의 한글 스페이스 문제)
	 *  (예 : 홍 길 동 --> 홍_길_동)
	 *
	 * </pre>
	 *
	 * @param args
	 *            : 스페이스를 포함한 한글 문자열
	 * @return '_'를 포함한 한글 문자열
	 */
	public String encodeSpace(String str) {

		StringTokenizer st = new StringTokenizer(str);
		String spString = "";
		while (st.hasMoreTokens()) {
			spString = spString + st.nextToken() + "_";
		}
		return spString;
	}

	/**
	 * <pre>
	 *
	 *  _ 를 space로 변환해서 리턴한다(get방식에서의 한글 스페이스 문제)
	 *  (예 : 홍_길_동 --> 홍 길 동)
	 *
	 * </pre>
	 *
	 * @param args
	 *            : '_'를 포함한 한글 문자열
	 * @return '_'를 제외한 한글 문자열
	 */
	public String decodeSpace(String str) {
		StringTokenizer st = new StringTokenizer(str, "_");
		String returnStr = "";
		while (st.hasMoreTokens()) {
			returnStr = returnStr + st.nextToken() + " ";
		}
		return returnStr;
	}

	/**
	 * <PRE>
	 *
	 * 문자열중 특정문자를 제거한다 (예) 2001/01/01 ==> 20010101
	 *
	 * </PRE>
	 *
	 * @param str
	 *            대상문자열
	 * @param tok
	 *            제거할 문자열
	 * @return 완성된 문자열
	 */
	public static String remove(String str, String tok) {
		return replaceAll(str, tok, "");
	}

	/**
	 * Replace string start from given offset to given length.
	 *
	 * @return String Replaced string.
	 * @param str
	 *            Original string.
	 * @param off
	 *            Given offset.
	 * @param len
	 *            Given length.
	 * @param replace
	 *            Given replcae string.
	 */
	public static String replace(String str, int off, int len, String replace) {
		StringBuffer buff = new StringBuffer(str);
		buff.replace(off, off + len, replace);
		return buff.toString();
	}

	/**
	 * Relace string start from given offset and replace one str2 with given
	 * replace string.
	 *
	 * @return Replaced string.
	 * @param str1
	 *            Original string.
	 * @param off
	 *            Given offset.
	 * @param str2
	 *            Given string that will be replaced by other string.
	 * @param replace
	 *            Given replcae string.
	 */
	public static String replace(String str1, int off, String str2,
			String replace) {
		off = str1.indexOf(str2, off);
		if (off == -1)
			return str1;

		StringBuffer buff = new StringBuffer(str1);
		buff.replace(off, off + str2.length(), replace);
		return buff.toString();
	}

	/**
	 * Relace string start from given offset and replace all of str2 with given
	 * replace string.
	 *
	 * @return Replaced string.
	 * @param str1
	 *            Original string.
	 * @param off
	 *            Given offset
	 * @param str2
	 *            Given source string.
	 * @param replace
	 *            Given target string.
	 */
	public static String replaceAll(String str1, int off, String str2,
			String replace) {
		if (str1 == null || str2 == null || replace == null)
			return str1;

		off = str1.indexOf(str2, off);
		StringBuffer buff = new StringBuffer(str1);
		while (off != -1) {
			buff.replace(off, off + str2.length(), replace);
			str1 = buff.toString();
			if (off + str2.length() < str1.length())
				off = str1.indexOf(str2, off + replace.length());
			else
				off = -1;
		}
		return str1;
	}

	/**
	 * Replace all source string to given target string.
	 *
	 * @return Replcaed string.
	 * @param str1
	 *            Original string.
	 * @param str2
	 *            Given source string.
	 * @param replace
	 *            Given target string.
	 */
	public static String replaceAll(String str1, String str2, String replace) {
		return replaceAll(str1, 0, str2, replace);
	}

	/**
	 * <pre>
	 *
	 *  str1 문자열을 str2 문자열로 파싱하여 문자배열로 리턴한다.
	 *  String str1 : 파싱할 문자
	 * <br>
	 *
	 *  String str2 : 토큰
	 *
	 * </pre>
	 *
	 * @param str1
	 *            : Original string.
	 * @param str2
	 *            : 파싱할 문자
	 * @return Vector
	 */
	/*
	 * public static Vector split( String str1, String str2 ) { String str = "";
	 * String newStr = str1; Vector v = new Vector( ); while( newStr.length( ) >
	 * 0 ) { if (newStr.indexOf( str2 ) >= 0) { //구분자가 있다면 int ord =
	 * newStr.indexOf( str2 ); //구분자의 위치 str = newStr.substring( 0, ord ); //구분자
	 * 앞까지 잘라냄 v.addElement( new String( str ) ); newStr = newStr.substring( ord
	 * + 1 ); } else { v.addElement( new String( newStr ) ); break; } } return
	 * v; }
	 */

	/**
	 * 입력받은 문자열 중 size(byte)를 넘지 않도록 계산해서 해당 문자열을 돌려줌.
	 *
	 * @param p_str
	 *            : 문자열
	 * @param p_len
	 *            : length
	 * @return
	 */
	public static String getShortString(String p_str, int p_len) {
		boolean chkFlag = false;
		String strName = p_str.trim();
		byte[] arName = strName.getBytes();
		if (arName.length > p_len) {
			for (int idx = 0; idx < p_len; idx++) {
				if (arName[idx] < 0) { // 0x80 이상 ( 1byte짜리. 키값들)
					chkFlag = !chkFlag; // true로
				} else {
					chkFlag = false; // false로
				}
			}
			if (chkFlag) {
				strName = new String(arName, 0, p_len + 1); // 홀수이면
			} else {
				strName = new String(arName, 0, p_len); // 짝수일때
			}
		} else {
			strName = new String(arName, 0, arName.length);
		}
		return strName;
	}

	/**
	 * 주어진 str을 max 만큼 자르고 뒤에 '...'을 붙인다.
	 *
	 * <pre>
	 *
	 * // for limited to 18 characters.
	 * ex) 'What are you doing now?' -> 'What are you doing...'
	 *
	 * </pre>
	 *
	 * @param str
	 *            Given original string.
	 * @param maxNum
	 *            Limited maximum length.
	 * @return Result string.
	 */
	public static String getShortStatement(String str, int maxNum) {
		return str.length() > maxNum ? getShortString(str, maxNum) + "..."
				: str;
	}

	/**
	 * 문자열에서 숫자만을 가져온다.
	 *
	 * @return java.lang.String
	 * @param str
	 *            java.lang.String
	 */
	public static String getRawDigit(String str) {
		char[] c = str.toCharArray();
		StringBuffer buff = new StringBuffer();
		try {
			for (int i = 0; i < c.length; i++)
				if (Character.isDigit(c[i]))
					buff.append(c[i]);
		} catch (Exception ex) {
		}
		return buff.toString();
	}

	/**
	 * Altibase에서는 Numeric, int 등 숫자 필드에서 Empty String을 입력하려고 하면 Error 발생 Null이
	 * 입력가능한 필드는 null로 입력 할 수 있도록 변환
	 *
	 * @param inStr
	 * @return
	 */
	public static String changeEmptyStringToNULL(String inStr) {
		if (inStr == null)
			return null;

		if (inStr.equals(""))
			return null;
		else
			return inStr;
	}

	/**
	 * 특수문자를 특수이름으로 변경
	 *
	 * @param text
	 * @return
	 */
	public static String changeEntityReference(String text) {
		String result = text;

		result = StringUtil.replaceAll(result, "&", "&amp;");
		result = StringUtil.replaceAll(result, "'", "&apos;");
		result = StringUtil.replaceAll(result, "\"", "&quot;");
		result = StringUtil.replaceAll(result, "<", "&lt;");
		result = StringUtil.replaceAll(result, ">", "&gt;");

		return result;

	}

	/**
	 * 특수이름을 특수문자로 변경
	 *
	 * @param text
	 * @return
	 */
	public static String changeNormalText(String text) {
		String result = text;

		result = StringUtil.replaceAll(result, "&gt;", ">");
		result = StringUtil.replaceAll(result, "&lt;", "<");
		result = StringUtil.replaceAll(result, "&quot;", "\"");
		result = StringUtil.replaceAll(result, "&apos;", "'");
		result = StringUtil.replaceAll(result, "&amp;", "&");

		return result;

	}

	/**
	 * return random String
	 *
	 * @param minValue
	 * @param maxValue
	 * @param fixLength
	 * @return
	 */
	public static String getRandomString(int minValue, int maxValue,
			int fixLength) {
		double value = NumberUtil.getRandom(minValue, maxValue);
		String str = Double.toString(value);

		int index = str.indexOf(".");
		str = str.substring(0, index);

		if (fixLength == -1)
			return str;
		index = str.length();

		while (index < fixLength) {
			str = "0" + str;

			index++;
		}
		return str;
	}

	/**
	 * 인자로 받은 String 값이 enumString 값에 해당하는지 체크한다.
	 *
	 * @param String
	 *            []
	 * @param String
	 * @return boolean
	 */
	public static boolean checkEnumString(String[] list, String val) {
		if (val == null)
			return false;

		for (String s : list) {
			if (s == null)
				continue;
			if (s.equals(val))
				return true;
		}

		return false;
	}

	/**
	 * base64 3번 암호화
	 *
	 * @param src
	 * @return
	 */
	public static String encode(String src){
		if(nvl(src).equals(""))
			return "";
		else
			return new String(Base64.encodeBase64(Base64.encodeBase64(Base64.encodeBase64(src.getBytes()))));
	}


	/**
	 * base64 3번 복호화
	 *
	 * @param src
	 * @return
	 */
	public static String decode(String src){
		if(nvl(src).equals(""))
			return "";
		else{
			//return new String(Base64.decodeBase64(Base64.decodeBase64(Base64.decodeBase64(src))));
			return new String(Base64.decodeBase64(Base64.decodeBase64(Base64.decodeBase64(src.getBytes()))));
		}
	}

	public static String getAge(String birthDay) throws Exception {
    	if(birthDay==null || equals(birthDay,""))
    		return "";

    	birthDay = replaceAll(birthDay,"-","");

		Calendar cal= Calendar.getInstance ();

		int year	= toInt(subString(birthDay,0,4));
		int month	= toInt(subString(birthDay,4,6));
		int day		= toInt(subString(birthDay,6,8));

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DATE, day);

        Calendar now = Calendar.getInstance ();

        int age = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
        if ((cal.get(Calendar.MONTH) > now.get(Calendar.MONTH))
            || (cal.get(Calendar.MONTH) == now.get(Calendar.MONTH) 
            && cal.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH))
        ){
        	age--;
        }

        return toString(age);
	}

	public static int getInt(String num) throws Exception {
    	if(num==null || equals(num,""))
    		return 0;

        return toInt(num);
	}

    @SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj){
        if( obj instanceof String ) return obj==null || "".equals(obj.toString().trim());
        else if( obj instanceof List ) return obj==null || ((List)obj).isEmpty();
        else if( obj instanceof Map ) return obj==null || ((Map)obj).isEmpty();
        else if( obj instanceof Object[] ) return obj==null || Array.getLength(obj)==0;
        else return obj==null;
    }
     
    public static boolean isNotEmpty(Object obj){
        return !isEmpty(obj);
    }


    /**
    * 함수명 :  데이터 처리
    * @FuncDesc : 날짜 사이에 '/'를 넣는다.
    *
    * @param amountString raw string
    * @return formatted string
    * @Author     :
    * @history 수정일자 / 수정자 / 수정내용
    * 2004.10.06 /  / 최초작성
    */
    public static String formatdate(String wdate) {
        if(wdate == null) return "";
        if(wdate.length() < 8) return wdate;
        return wdate.substring(0,4)+"."+wdate.substring(4,6)+"."
            +wdate.substring(6,8);
    }
    /**
    * 함수명 :  데이터 처리
    * @FuncDesc : 날짜 사이에 구분자를 넣는다.
    *
    * @param wdate 날짜(yyyymmdd)
    * @param gubun 구분자
    * @return formatted string
    * @Author     :
    * @history 수정일자 / 수정자 / 수정내용
    * 2004.10.06 /  / 최초작성
    */
    public static String formatdate(String wdate, String gubun) {
        if(wdate == null) return "";
        if(wdate.length() < 8) return wdate;
        return wdate.substring(0,4)+gubun+wdate.substring(4,6)
            +gubun+wdate.substring(6,8);
    }
}
