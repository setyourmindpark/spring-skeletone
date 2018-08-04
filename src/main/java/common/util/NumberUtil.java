package common.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class NumberUtil {

	/**
	 * 문자열이 숫자인지 확인한다.
	 * 소수점(.) 포함은 true 리턴
	 * 
	 * @param text 문자열.
	 * @return true/false
	 */
	public static boolean isNumeric(String text) {
		if (text.length() == 0) return false;
		for (int i=0; i<text.length(); i++) {
			if (i==0) {
				if (text.substring(i, i+1).equals("-")) continue;
			}
			if (text.substring(i, i+1).compareTo("0") < 0 || text.substring(i, i+1).compareTo("9") > 0) {
				if ( !(text.substring(i, i+1).equals(".")) ) return false;
			}
		}
		return true;
	}
	
    /**
     * <PRE>
     * 
     * String 문자열의 소수점 자리수를 정한다. (예) 10.245 --> 10.24, 9 --> 9.00
     * 
     * </PRE>
     * 
     * @param strSrc 변경할 숫자 문자열
     * @param len 소숫점 자리수
     * @return 변경된 숫자 문자열
     */
    public static String getDecimal( String strSrc, int len ) {
        int length = strSrc.length( );
        int iDec = strSrc.indexOf( '.' );
        StringBuffer convert = new StringBuffer( );
        convert.append( (iDec < 0) ? strSrc : strSrc.substring( 0, iDec ) );
        if (iDec < 0) iDec = length;
        for (int i = 1; i <= len; i++) {
            if (i == 1) convert.append( "." );
            convert.append( ((i + iDec) < length) ? strSrc.charAt( i + iDec )
                    : '0' );
        }
        return convert.toString( );
    }

    /**
     * <PRE>
     * 
     * Double 값의 소수점 자리수를 정한다. 예) 10.245 --> 10.24 , 9 --> 9.00
     * 
     * </PRE>
     * 
     * @param double 변경할 숫자
     * @param len 소숫점 자리수
     * @return 변경된 숫자 문자열
     */
    public static String getDecimal( double dNumber, int len ) {
        return getDecimal( Double.toString( dNumber ), len );
    }
	
	/**
	 * return random double
	 * 
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	public static double getRandom(int minValue, int maxValue) {
		double	value = (java.lang.Math.random() * maxValue) + minValue;
		
		return value;
	}

    /**
     * return random int
     * 
     * @return 
     */
    public static int getRandomInt( ) {
        return getRandomInt(0,100);
    }
    
    /**
     * return random int
     * 
     * @param minValue
     * @param maxValue
     * @return
     */
	public static int getRandomInt(int minValue, int maxValue) {
		double	value = getRandom(minValue, maxValue);
		String	str = Double.toString(value);
		
		int index = str.indexOf(".");
		str = str.substring(0, index);
				
		return Integer.parseInt(str);
	}
	
	/**
     * <PRE>
     * 
     * 입력받은 String을 pos로 지정한 위치에 Comma를 사용하여 나누는 함수 (금액을 표시할때 주로 사용)
     * 
     * </PRE>
     * 
     * @param src Comma로 분할할 String
     * @param pos 소숫점 자리수
     * @return Comma로 분할된 String
     */
    public static String maskComma( String src, int pos ) {
    	if( src == null || src.length() == 0 ) return "";
    	
        DecimalFormat df = new DecimalFormat( );
        DecimalFormatSymbols dfs = new DecimalFormatSymbols( );
        dfs.setGroupingSeparator( ',' );
        df.setGroupingSize( pos );
        
        // 소수점이하 값을 세자리에서 반올림하는 것을 방지하기 위해 추가.
        // 천억까지 반올림없이 동작을 하는데 조부터는 18자리부터 반올림이 시작됨. ㅡ.ㅡ
        StringBuffer sb = new StringBuffer();
        for( int i = 1 ; i <= pos*7 ; i++){
        	if( i % pos == 0 && i != pos*7 ) sb.append("#").append(",");
        	else sb.append("#");
        }
        sb.append(".######");
        
        df.applyPattern( sb.toString() );
        df.setDecimalFormatSymbols( dfs );
        String resultValue = (df.format( Double.parseDouble( src ) ))
                .toString( );
        return resultValue;
    }

    /**
     * <PRE>
     * 
     * 입력받은 long을 pos로 지정한 위치에 Comma를 사용하여 나누는 함수 (금액을 표시할때 주로 사용)
     * 
     * </PRE>
     * 
     * @param src Comma로 분할할 long
     * @return Comma로 분할된 String
     */
    public static String maskComma( long amt, int pos ) {    	
        return maskComma( (new Long( amt )).toString( ), pos );
    }

    /**
     * <PRE>
     * 
     * 입력받은 double을 pos로 지정한 위치에 Comma를 사용하여 나누는 함수 (금액을 표시할때 주로 사용)
     * 
     * </PRE>
     * 
     * @param src Comma로 분할할 double
     * @return Comma로 분할된 String
     */
    public static String maskComma( double amt, int pos ) {
        return maskComma( (new Double( amt )).toString( ), pos );
    }

    /**
     * <PRE>
     * 
     * 입력받은 String을 3자리마다 Comma를 사용하여 나누는 함수 (금액을 표시할때 주로 사용)
     * 
     * </PRE>
     * 
     * @param src Comma로 분할할 String
     * @return Comma로 분할된 String
     */
    public static String maskComma( String src ) {
        return maskComma( src, 3 );
    }

    /**
     * <PRE>
     * 
     * 입력받은 long을 3자리마다 Comma를 사용하여 나누는 함수 (금액을 표시할때 주로 사용)
     * 
     * </PRE>
     * 
     * @param src Comma로 분할할 long
     * @return Comma로 분할된 String
     */
    public static String maskComma( long amt ) {
        return maskComma( (new Long( amt )).toString( ), 3 );
    }

    /**
     * <PRE>
     * 
     * 입력받은 int를 3자리마다 Comma를 사용하여 나누는 함수 (금액을 표시할때 주로 사용)
     * 
     * </PRE>
     * 
     * @param src Comma로 분할할 int
     * @return Comma로 분할된 String
     */
    public static String maskComma( int amt ) {
        return maskComma( (new Integer( amt )).toString( ), 3 );
    }

    /**
     * <PRE>
     * 
     * 입력받은 double을 3자리마다 Comma를 사용하여 나누는 함수 (금액을 표시할때 주로 사용)
     * 
     * </PRE>
     * 
     * @param src Comma로 분할할 double
     * @return Comma로 분할된 String
     */
    static public String maskComma( double amt ) {
        return maskComma( (new Double( amt )).toString( ), 3 );
    }
    
    /**
     * <PRE>
     * 
     * 입력받은 String에서 Comma를 제거하는 함수
     * 
     * </PRE>
     * 
     * @param src Comma로 분할된 String
     * @return Comma가 제거된 String
     */
    public static String removeComma( String src ) {
        String resultValue = "";
        for (int i = 0; i < src.length( ); i++) {
            char temp = src.charAt( i );
            if (temp == ',') continue;
            resultValue = resultValue + temp;
        }
        return resultValue;
    }
    
	/**
	 * 반올림 또는 내림 하는 메소드 
     * 
	 * @param f 입력값
	 * @param len 소수점 이하 자리수
	 * @param round_type 	java.math.BigDecimal.ROUND_CEILING 
     * 						java.math.BigDecimal.ROUND_DOWN
     * 						java.math.BigDecimal.ROUND_FLOOR 
     * 						java.math.BigDecimal.ROUND_HALF_DOWN
     * 						java.math.BigDecimal.ROUND_HALF_EVEN 
     * 						java.math.BigDecimal.ROUND_HALF_UP
     * 						java.math.BigDecimal.ROUND_UP
	 * @return
	 */
    public static float round(float f, int len, int round_type) {
        float retval = 0F;
        try {
            retval = (new java.math.BigDecimal(f).setScale(len, round_type)).floatValue();
            
        }  catch (NumberFormatException nfe) { }
       
        return retval;
    }

    /**
     * 분모가 0이면 ArithmeticException이 발생하는 것을 방지하기 위한 메소드
     * 
     * @param son 분자
     * @param mother 분모
     * @return
     */
    public static float division(float son, float mother) {
        float retval = 0F;
        if (mother == 0) {
            retval = 0;
        } else {
            retval = (son / mother);
            retval = round(retval, 2, java.math.BigDecimal.ROUND_HALF_UP);
        }
        return retval;
    }
    
    @SuppressWarnings("unused")
	public static void main(String[] args){

    	String sSrc = "123456789012.1234567890123";
    	long dTest = 123456;
    	double f = 10.12345;
    	
//    	System.out.println( "11>>>"+NumberUtil.getLongDigitPattern( sSrc) );
    	System.out.println( "22>>>"+NumberUtil.maskComma(sSrc, 3) );
    	System.out.println( "22>>>"+NumberUtil.maskComma(12345.99, 3));
    	System.out.println( "22>>>"+NumberUtil.round(new Double(10.12345).floatValue(),3,java.math.BigDecimal.ROUND_HALF_UP));
    	System.out.println( "22>>>"+NumberUtil.division(2, 0));
    }
}
