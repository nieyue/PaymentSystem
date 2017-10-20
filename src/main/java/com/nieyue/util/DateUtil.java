package com.nieyue.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 日期格式化类
 * @author yy
 *
 */
public class DateUtil {
	/**
	 *@param n为小时段，默认24段，即
	 * 段时间的时间设置
	 * @return
	 */
	public static Date getDayPeriod(int n ){ 
		if(n<=0 ||n>24){
			n=24;
		}
		//long dayseconds=86400;//一天的秒数；
		long daystart=getStartTime().getTime()/1000;//当日初始秒数
		Date date =new Date();
		long daynow = date.getTime()/1000;//现在的秒数
		long daycha=daynow-daystart;//相差秒数
		
		long every=24/n;//如n=3;every=8 即8小时记录一次
		long everyseconds=every*60*60;//小时段的秒数
		long nown=daycha/everyseconds;//第几次
		Date rd = new Date((nown*everyseconds+daystart)*1000);
		return rd;
	}  
	/**
	 *@param n为小时段，默认24段，即
	 * 段时间的时间设置
	 * @return
	 */
	public static Date getDayPeriod(int n ,Date date){ 
		if(n<=0 ||n>24){
			n=24;
		}
		//long dayseconds=86400;//一天的秒数；
		long daystart=getStartTime().getTime()/1000;//当日初始秒数
		 date =new Date();
		long daynow = date.getTime()/1000;//现在的秒数
		long daycha=daynow-daystart;//相差秒数
		
		long every=24/n;//如n=3;every=8 即8小时记录一次
		long everyseconds=every*60*60;//小时段的秒数
		long nown=daycha/everyseconds;//第几次
		Date rd = new Date((nown*everyseconds+daystart)*1000);
		return rd;
	}
	/**
	 * 获取当日开始时间
	 * @return
	 */
	 public static Date getStartTime(){  
		Date date =new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		long nd = date.getTime()/1000*1000;
		return new Date(nd);
	}  
	  
	/**
	 * 获取当日结束时间
	 * @return
	 */
	 public static Date getEndTime(){  
		Date date =new Date();
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		long nd = date.getTime()/1000*1000+999;
		return new Date(nd);
	} 
	 /**
	  * 获取当前时间到当日结束时间差  
	  * 单位 ： 秒
	  * @return
	  */
	 public static long currentToEndTime(){
		 Date date=new Date();
		 long miao = (getEndTime().getTime()-date.getTime())/1000;
		 return miao;
	 } 
	/**
	 * 格式化时间yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentTime(){
	Date dt=new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
	String nowTime="";
	nowTime= df.format(dt);//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
	return nowTime;
	}
	/**
	 * 格式化时间yyyy-MM-dd
	 * @return
	 */
	public static String getCurrentTimeDay(){
	Date dt=new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置显示格式
	String nowTime="";
	nowTime= df.format(dt);//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
	return nowTime;
	}
	/**
	 * date格式化时间 format
	 * @return
	 */
	public static String dateFormatSimpleDate(Date date,String format){
		DateFormat df = new SimpleDateFormat(format);//设置显示格式
		String nowTime="";
		nowTime= df.format(date);//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
		return nowTime;
	}
	/**
	 * 格式化时间"yyyyMMddHHmmss
	 * @return
	 */
	public static String getOrdersTime(){
		Date dt=new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置显示格式
		String nowTime="";
		nowTime= df.format(dt);//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
		return nowTime;
	}
	/**
	 * 格式化时间"yyyyMMdd
	 * @return
	 */
	public static String getImgDir(){
		Date dt=new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
		DateFormat df = new SimpleDateFormat("yyyyMMdd");//设置显示格式
		String nowTime="";
		nowTime= df.format(dt);//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
		return nowTime;
	}
	
	/** 
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到秒的字符串 
     * @param formatStr 
     * @return 
     */  
    public static String timeStamp2Date(String seconds,String format) {  
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
            return "";  
        }  
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";  
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(Long.valueOf(seconds+"000")));  
    }  
    /** 
     * 日期格式字符串转换成时间戳 
     * @param date 字符串日期 
     * @param format 如：yyyy-MM-dd HH:mm:ss 
     * @return 
     */  
    public static String date2TimeStamp(String date_str,String format){  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            return String.valueOf(sdf.parse(date_str).getTime()/1000);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
      
    /** 
     * 取得当前时间戳（精确到秒） 
     * @return 
     */  
    public static String timeStamp(){  
        long time = System.currentTimeMillis();  
        String t = String.valueOf(time/1000);  
        return t;  
    }  
    /**
     * 获取精准时间格式存储数据库
     * @param args
     */
    public static Date getFormatCurrentTime(){  
    	SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    	 String time=getCurrentTime();

    	 Timestamp time1 = null;
		try {
			time1 = new Timestamp(sdf.parse(time).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		} 
    	return time1;  
    }  
    /**
     * 获取两日期之间的相隔天数
     * @param args
     * @throws ParseException 
     */
    public static Long getSeparatedTime(Date d1,Date d2) {  
    	//String date01 = "2016-3-1 9:20:00";
    	//String date02 = "2016-3-2 9:19:00";
    	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	//Date d1 = null;
    	//Date d2 = null;
		if(isSameDate(d1, d2)){//同一天
			return 0l;
		}else if(Math.abs((d2.getTime()-d1.getTime()))<=3600*24*1000){//差24小时以内算一天
			return 1l;
		}else{
			long daysBetween = (d2.getTime()-d1.getTime())/(3600*24*1000);//两日期之间相隔的天数 	
			return daysBetween;  
		}
    }  
    /**
     * 获取从起始日期开始几天后的日期
     * @param args
     * @throws ParseException 
     */
    public static Date getFirstToDay(Date firstDate,long coupleDay) {  
    	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date lastDate = new Date(coupleDay*3600*24*1000+firstDate.getTime());//两日期之间相隔的天数 
    	return lastDate;  
    }  
    /**
     * 获取从起始时间开始之后几分钟的时间
     * @param args
     * @throws ParseException 
     */
    public static Date getFirstToSecondsTime(Date firstDate,long coupleTime) {  
    	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date lastDate = new Date(coupleTime*60*1000+firstDate.getTime());//两日期之间相隔的天数 
    	return lastDate;  
    }  
    /**
     * 格式化时间从yyyy-MM-dd HH:mm:ss到Wed Mar 02 09:19:00 CST 2016
     * @param args
     * @throws ParseException 
     */
    public static Date parseDate(String date) throws ParseException {  
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date da = sdf.parse(date);
    	return da;  
    }  
    /**
     * 是否同一天
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                        .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    //  输出结果：  
    //  timeStamp=1417792627  
    //  date=2014-12-05 23:17:07  
    //  1417792627  
    //static HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    public static void main(String[] args) {  
    	System.out.println(getCurrentTime());
    	System.out.println(getOrdersTime());
    	String timeStamp = timeStamp();  
        System.out.println("timeStamp="+timeStamp);  
          
        String date = timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");  
        System.out.println("date="+date);  
          
        String timeStamp2 = date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss");  
        System.out.println(timeStamp2); 
        System.out.println(getFormatCurrentTime().toLocaleString());
      //  HttpSession session = request.getSession();
       // session.setAttribute("nieyue", "dsfsdfsdf");
        //String nieyue = (String) session.getAttribute("nieyue");
       // System.out.println(session); 
        System.out.println(getFirstToDay(new Date(), 1).toLocaleString());
        System.out.println(getFirstToSecondsTime(new Date(), 1).toLocaleString());
        System.out.println(getImgDir());
        Date d1=new Date("2016/3/2 0:00:00");
        Date d2=new Date("2016/3/1 23:59:59");
        System.out.println(getSeparatedTime(d2,d1));
        Set<Integer> set=new HashSet<Integer>();
       boolean b = set.add(1000);
       
       System.out.println(set.iterator().next());
       System.out.println(b);
        b = set.add(10002);
       System.out.println(b);
       b = set.add(10002);
       System.out.println(b);
       b = set.add(10022);
       System.out.println(b);
       System.out.println(set);
       
       Map<String ,String> nmap=new HashMap<String,String>();
       nmap.put("id","1");
       nmap.put("sign","dsffffffff324ffds");
       nmap.put("sign_type","d2323234ffds");
       nmap.put("body","第三方第三方");
       System.err.println(nmap.toString());
      // File file =new File("E:/雅耀/2017/ios.txt");
       System.out.println("--------");
       String path="E:/雅耀/2017/ios.txt";
      //String er;
       String re="{\"signature\" = \"Ap3n5Tfd+Ur/BakLE7ToCssNHgqnb6vyk3DKfQqrpqA+KWP3Tudd3v7l49A2scUcX6qcCxLwlp3GdJ0oLkr3G7ShFEaNTy0k/naE3hYB1cI1j1F3ZiJlST/d/UC7FEcPT24vIAEd16YqNtsjYJMbbPTdQGx86+EeiCGmfT2JKEk+AAADVzCCA1MwggI7oAMCAQICCBup4+PAhm/LMA0GCSqGSIb3DQEBBQUAMH8xCzAJBgNVBAYTAlVTMRMwEQYDVQQKDApBcHBsZSBJbmMuMSYwJAYDVQQLDB1BcHBsZSBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTEzMDEGA1UEAwwqQXBwbGUgaVR1bmVzIFN0b3JlIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MB4XDTE0MDYwNzAwMDIyMVoXDTE2MDUxODE4MzEzMFowZDEjMCEGA1UEAwwaUHVyY2hhc2VSZWNlaXB0Q2VydGlmaWNhdGUxGzAZBgNVBAsMEkFwcGxlIGlUdW5lcyBTdG9yZTETMBEGA1UECgwKQXBwbGUgSW5jLjELMAkGA1UEBhMCVVMwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMmTEuLgjimLwRJxy1oEf0esUNDVEIe6wDsnnal14hNBt1v195X6n93YO7gi3orPSux9D554SkMp+Sayg84lTc362UtmYLpWnb34nqyGx9KBVTy5OGV4ljE1OwC+oTnRM+QLRCmeNxMbPZhS47T+eZtDEhVB9usk3+JM2Cogfwo7AgMBAAGjcjBwMB0GA1UdDgQWBBSJaEeNuq9Df6ZfN68Fe+I2u22ssDAMBgNVHRMBAf8EAjAAMB8GA1UdIwQYMBaAFDYd6OKdgtIBGLUyaw7XQwuRWEM6MA4GA1UdDwEB/wQEAwIHgDAQBgoqhkiG92NkBgUBBAIFADANBgkqhkiG9w0BAQUFAAOCAQEAeaJV2U51rxfcqAAe5C2/fEW8KUl4iO4lMuta7N6XzP1pZIz1NkkCtIIweyNj5URYHK+HjRKSU9RLguNl0nkfxqObiMckwRudKSq69NInrZyCD66R4K77nb9lMTABSSYlsKt8oNtlhgR/1kjSSRQcHktsDcSiQGKMdkSlp4AyXf7vnHPBe4yCwYV2PpSN04kboiJ3pBlxsGwV/ZlL26M2ueYHKYCuXhdqFwxVgm52h3oeJOOt/vY4EcQq7eqHm6m03Z9b7PRzYM2KGXHDmOMk7vDpeMVlLDPSGYz1+U3sDxJzebSpbaJmT7imzUKfggEY7xxf4czfH0yj5wNzSGTOvQ==\";  \"purchase-info\" = \"ewoJIm9yaWdpbmFsLXB1cmNoYXNlLWRhdGUtcHN0IiA9ICIyMDE2LTA0LTI4IDAzOjE4OjQ5IEFtZXJpY2EvTG9zX0FuZ2VsZXMiOwoJInVuaXF1ZS1pZGVudGlmaWVyIiA9ICJkNGU3MjFlYzY3ZWYyZmVjYTdmYmRiZDI1YTQ1Y2ZiMzdlMTBlYTdiIjsKCSJvcmlnaW5hbC10cmFuc2FjdGlvbi1pZCIgPSAiMTAwMDAwMDIwODYyMDQ3MCI7CgkiYnZycyIgPSAiMS4xIjsKCSJ0cmFuc2FjdGlvbi1pZCIgPSAiMTAwMDAwMDIwODYyMDQ3MCI7CgkicXVhbnRpdHkiID0gIjEiOwoJIm9yaWdpbmFsLXB1cmNoYXNlLWRhdGUtbXMiID0gIjE0NjE4Mzg3MjkyODUiOwoJInVuaXF1ZS12ZW5kb3ItaWRlbnRpZmllciIgPSAiOEUxOUVFQzQtMzNENy00NTM2LUI2MkUtMTEyQkFDNjhFRUNEIjsKCSJwcm9kdWN0LWlkIiA9ICIxMjQ0IjsKCSJpdGVtLWlkIiA9ICIxMTA4Nzk4MTUxIjsKCSJiaWQiID0gImNvbS5kb2N0b3JIeXMiOwoJInB1cmNoYXNlLWRhdGUtbXMiID0gIjE0NjE4Mzg3MjkyODUiOwoJInB1cmNoYXNlLWRhdGUiID0gIjIwMTYtMDQtMjggMTA6MTg6NDkgRXRjL0dNVCI7CgkicHVyY2hhc2UtZGF0ZS1wc3QiID0gIjIwMTYtMDQtMjggMDM6MTg6NDkgQW1lcmljYS9Mb3NfQW5nZWxlcyI7Cgkib3JpZ2luYWwtcHVyY2hhc2UtZGF0ZSIgPSAiMjAxNi0wNC0yOCAxMDoxODo0OSBFdGMvR01UIjsKfQ==\";  \"environment\" = \"Sandbox\";  \"pod\" = \"100\";  \"signing-status\" = \"0\";  } ";
       //String receipt="{\"signature\" = \"Am7vrfmY+FJq9g8gJDdYMGWOBJiKUUz80nAHooQFwYEZAL9wdXU7uaMiSZn75JQUjO3XfydLs2bwm9VPoKYKTGcft0LrISl7YNlQAWeVfA62F2E1qgTAGVFoTF1k0o3hJR1D/bLoum3i5PrQiScV90s0V77WVon2+B6vqUtHLsZUAAADVzCCA1MwggI7oAMCAQICCGUUkU3ZWAS1MA0GCSqGSIb3DQEBBQUAMH8xCzAJBgNVBAYTAlVTMRMwEQYDVQQKDApBcHBsZSBJbmMuMSYwJAYDVQQLDB1BcHBsZSBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTEzMDEGA1UEAwwqQXBwbGUgaVR1bmVzIFN0b3JlIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MB4XDTA5MDYxNTIyMDU1NloXDTE0MDYxNDIyMDU1NlowZDEjMCEGA1UEAwwaUHVyY2hhc2VSZWNlaXB0Q2VydGlmaWNhdGUxGzAZBgNVBAsMEkFwcGxlIGlUdW5lcyBTdG9yZTETMBEGA1UECgwKQXBwbGUgSW5jLjELMAkGA1UEBhMCVVMwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMrRjF2ct4IrSdiTChaI0g8pwv/cmHs8p/RwV/rt/91XKVhNl4XIBimKjQQNfgHsDs6yju++DrKJE7uKsphMddKYfFE5rGXsAdBEjBwRIxexTevx3HLEFGAt1moKx509dhxtiIdDgJv2YaVs49B0uJvNdy6SMqNNLHsDLzDS9oZHAgMBAAGjcjBwMAwGA1UdEwEB/wQCMAAwHwYDVR0jBBgwFoAUNh3o4p2C0gEYtTJrDtdDC5FYQzowDgYDVR0PAQH/BAQDAgeAMB0GA1UdDgQWBBSpg4PyGUjFPhJXCBTMzaN+mV8k9TAQBgoqhkiG92NkBgUBBAIFADANBgkqhkiG9w0BAQUFAAOCAQEAEaSbPjtmN4C/IB3QEpK32RxacCDXdVXAeVReS5FaZxc+t88pQP93BiAxvdW/3eTSMGY5FbeAYL3etqP5gm8wrFojX0ikyVRStQ+/AQ0KEjtqB07kLs9QUe8czR8UGfdM1EumV/UgvDd4NwNYxLQMg4WTQfgkQQVy8GXZwVHgbE/UC6Y7053pGXBk51NPM3woxhd3gSRLvXj+loHsStcTEqe9pBDpmG5+sk4tw+GK3GMeEN5/+e1QT9np/Kl1nj+aBw7C0xsy0bFnaAd1cSS6xdory/CUvM6gtKsmnOOdqTesbp0bs8sn6Wqs0C9dgcxRHuOMZ2tm8npLUm7argOSzQ==\";\"purchase-info\" = \"ewoJIm9yaWdpbmFsLXB1cmNoYXNlLWRhdGUtcHN0IiA9ICIyMDE0LTAyLTEyIDAwOjQ1OjUzIEFtZXJpY2EvTG9zX0FuZ2VsZXMiOwoJInVuaXF1ZS1pZGVudGlmaWVyIiA9ICJmNzFjODA0YmNkMDkwMDg1ZDE3Y2YwN2UyODA1YzFiMGRmYTA1M2VhIjsKCSJvcmlnaW5hbC10cmFuc2FjdGlvbi1pZCIgPSAiMTAwMDAwMDEwMTI2NTU1MSI7CgkiYnZycyIgPSAiMS4wIjsKCSJ0cmFuc2FjdGlvbi1pZCIgPSAiMTAwMDAwMDEwMTI2NTU1MSI7CgkicXVhbnRpdHkiID0gIjEiOwoJIm9yaWdpbmFsLXB1cmNoYXNlLWRhdGUtbXMiID0gIjEzOTIxOTQ3NTMzNjgiOwoJInVuaXF1ZS12ZW5kb3ItaWRlbnRpZmllciIgPSAiRjYzRTdBMzUtMDQwNi00NDVGLUE1QUEtQ0M5OTc0RDRDQTlCIjsKCSJwcm9kdWN0LWlkIiA9ICJjb20ueWNtLnBubS53aTEiOwoJIml0ZW0taWQiID0gIjgwMjc5MzM1MiI7CgkiYmlkIiA9ICJjb20ueWNtLnBubSI7CgkicHVyY2hhc2UtZGF0ZS1tcyIgPSAiMTM5MjE5NDc1MzM2OCI7CgkicHVyY2hhc2UtZGF0ZSIgPSAiMjAxNC0wMi0xMiAwODo0NTo1MyBFdGMvR01UIjsKCSJwdXJjaGFzZS1kYXRlLXBzdCIgPSAiMjAxNC0wMi0xMiAwMDo0NTo1MyBBbWVyaWNhL0xvc19BbmdlbGVzIjsKCSJvcmlnaW5hbC1wdXJjaGFzZS1kYXRlIiA9ICIyMDE0LTAyLTEyIDA4OjQ1OjUzIEV0Yy9HTVQiOwp9\";\"environment\" = \"Sandbox\";\"pod\" = \"100\";\"signing-status\" = \"0\";}";
	try {
		FileInputStream fin = new FileInputStream(path); 
	     DataInputStream da = new DataInputStream(fin); 
	   int temp ;
	   StringBuffer sb=new StringBuffer();
	   
	      while ( (temp = da.read()) != -1) { 
	                   sb.append(temp);
	      } 
	    
	      da.close(); 
	      fin.close(); 
		System.err.println(sb.toString().length());
		//byte[] s = Base64.getEncoder().encode(sb.toString().getBytes("UTF-8"));
		byte[] s =Base64.getEncoder().encode(re.toString().getBytes());
		//String s=new sun.misc.BASE64Encoder().encode(re.getBytes());
		System.out.println(new String(s));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
       //System.out.println(Base64.getEncoder().en);
    }  
}
