package com.ktmmobile.mcp.common.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeUtil {

	private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    /**
     * <pre>
     * 설명     : 현재 날짜 기준으로 인자값(일) 더한 Date return
     * @param day
     * @return
     * </pre>
     */
    public static Date getDateToCurrent(int day) {
        Calendar cal = Calendar.getInstance();
        if (day != 0){
            cal.add(Calendar.DATE, day);
        }
        return new Date(cal.getTimeInMillis());
    }

    /**
     * check date string validation with the default format "yyyyMMdd".
     * @param s date string you want to check with default format "yyyyMMdd".
     * @return date Date
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 format 에 맞지 않는 경우.
     **/
    public static Date check(String s)
            throws ParseException {
        return check(s, "yyyyMMdd");
    }

    /**
     *날짜를 표현하는 형식을 변경하여 변경된 문자열을 리턴한다.
     * @param s 날짜를 나타내는 문자열
     * @param format 소스(s) 날짜의 형식을 설명하는 문자열 ,예) "yyyy-MM-dd"
     * @param toformat 변경될 날짜의 형식을 설명하는 문자열 ,예) "yyyy-MM-dd"
     *  @return toformat형태로 변경된 날짜를 표시하는 문자열
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 format 에 맞지 않는 경우.
     */
    public static String changeFormat(String s, String format, String toformat)
            throws ParseException {
        Date date = check(s, format);
        SimpleDateFormat formatter =
                new SimpleDateFormat(toformat, Locale.KOREA);
        String dateString = formatter.format(date);
        return dateString;

    }

    /**
     *날짜를 표현하는 형식을 변경하여 변경된 문자열을 리턴한다.
     * @param date 날짜를 나타내는 Date객체
     * @param toformat 변경될 날짜의 형식을 설명하는 문자열 ,예) "yyyy-MM-dd"
     *  @return toformat형태로 변경된 날짜를 표시하는 문자열
     */
    public static String changeFormat(Date date,  String toformat)
    {

        SimpleDateFormat formatter =
                new SimpleDateFormat(toformat, Locale.KOREA);
        String dateString = formatter.format(date);
        return dateString;

    }

    /**
     * check date string validation with an user defined format.
     * @param s date string you want to check.
     * @param format string representation of the date format. For example, "yyyy-MM-dd".
     * @return date Date
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 format 에 맞지 않는 경우 발생
     */
    public static Date check(String s, String format)
            throws ParseException {
        if (s == null)
            throw new ParseException(
                    "date string to check is null",
                    0);
        if (format == null)
            throw new ParseException(
                    "format string to check date is null",
                    0);

        SimpleDateFormat formatter =
                new SimpleDateFormat(format, Locale.KOREA);
        Date date = null;
        try {
            date = formatter.parse(s);
        } catch (ParseException e) {
            /*
            throw new ParseException(
                e.getMessage() + " with format \"" + format + "\"",
                e.getErrorOffset()
            );
             */
            throw new ParseException(
                    " wrong date:\"" + s + "\" with format \"" + format + "\"",
                    0);
        }

        if (!formatter.format(date).equals(s))
            throw new ParseException(
                    "Out of bound date:\""
                            + s
                            + "\" with format \""
                            + format
                            + "\"",
                            0);
        return date;
    }

    /**
     * check date string validation with the default format "HH:mm:ss".
     * @param s date string you want to check with default format "HH:mm:ss"
     * @return <tt>true</tt> 날짜 형식이 맞고, 존재하는 날짜일 때.
     *                 <tt>false</tt> 날짜 형식이 맞지 않거나, 존재하지 않는 날짜일 때
     */
    public static boolean isValid(String s) {
        return isValid(s, "HH:mm:ss");
    }

    /**
     * "HH:mm" 또는 "HH/mm" 형태의 <code>Date</code> 객체를 리턴한다.
     * @param s "HH:mm" 또는 "HH/mm" 형태의 현재 시각(몇시 몇분)을 나타내는 문자열
     * @return 인자로 전달된 시각에 해당하는 <code>Date</code> 객체
     * @throws ParseException 인자로 전달된 시각이 지정된 포멧("HH:mm" or "HH/mm" 에) 맞지 않거나 올바른 시간이 아닐경우 발생.
     */
    public static Date getDateInstance(String s)
            throws ParseException {
        String format = "HH:mm";

        if (!isValid(s, "HH:mm")) {
            if (isValid(s, "HH/mm")) {
                format = "HH/mm";
            } else {
                throw new ParseException("wrong data or format", 0);
            }
        }
        return check(s, format);
    }

    /**
     * check date string validation with an user defined format.
     * @param s date string you want to check.
     * @param format string representation of the date format. For example, "yyyy-MM-dd".
     * @return <tt>true</tt> 날짜 형식이 맞고, 존재하는 날짜일 때.
     *                 <tt>false</tt> 날짜 형식이 맞지 않거나, 존재하지 않는 날짜일 때
     */
    public static boolean isValid(String s, String format) {
        /*
                if ( s == null )
                    throw new NullPointerException("date string to check is null");
                if ( format == null )
                    throw new NullPointerException("format string to check date is null");
         */
        SimpleDateFormat formatter =
                new SimpleDateFormat(format, Locale.KOREA);
        Date date = null;
        try {
            date = formatter.parse(s);
        } catch (ParseException e) {
            return false;
        }

        if (!formatter.format(date).equals(s))
            return false;

        return true;
    }

    /**
     * 현재 날짜를 "yyyy-MM-dd" 형태의 포멧으로 표현하는 문자열을 리턴한다.
     * @return formatted string representation of current day with  "yyyy-MM-dd".
     */
    public static String getDateString() {
        SimpleDateFormat formatter =
                new SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.KOREA);
        return formatter.format(new Date());
    }

    /**
     *
     * 오늘 날짜를 숫자로 리턴한다.
     *<code>getNumberByPattern("dd");</code>
     * @return 오늘 날짜.(1~31)
     * @see #getNumberByPattern(String)
     */
    public static int getDay() {
        return getNumberByPattern("dd");
    }

    /**
     *
     * 오늘 날짜를 숫자로 리턴한다.
     *<code>getNumberByPattern("dd");</code>
     * @return 오늘 날짜.(1~31)
     * @throws ParseException
     * @see #getNumberByPattern(String)
     */
    public static int getDay(String dates, String spattern) throws ParseException {
        return getNumberByPattern(dates, spattern, "dd");
    }

    /**
     *
     * 올해를 숫자로 리턴한다.
     * <code> getNumberByPattern("yyyy");</code>
     * @return 올해를 표현하는 4자리 숫자(예:2005)
     * @see #getNumberByPattern(String)
     */
    public static int getYear() {
        return getNumberByPattern("yyyy");
    }

    /**
     *
     * 이번달을 숫자로 리턴한다.
     * <code>getNumberByPattern("MM");</code>
     * @return 이번달을 표현하는 숫자 (1~12)
     * @see #getNumberByPattern(String)
     */
    public static int getMonth() {
        return getNumberByPattern("MM");
    }
    /**
     *
     * 현재 시간을 리턴한다.
     * <code>getNumberByPattern("HH");</code>
     * @return 현재 시간을 표현하는 숫자(1~24)
     * @see #getNumberByPattern(String)
     */
    public static int getHour() {
        return getNumberByPattern("HH");
    }

    /**
     *
     * 현재 시각의 분을 리턴한다.
     *<code>getNumberByPattern("mm");</code>
     * @return 현재 시각중 분을 표현하는 숫자(0~59)
     * @see #getNumberByPattern(String)
     */
    public static int getMin() {
        return getNumberByPattern("mm");
    }

    /**
     *
     *인자로 전달된 패턴에 해당하는 값을 숫자로 리턴한다.
     *
     * 코드 사용예:
     * 	<p><blockquote><pre>
     *  int currentYearValue = DateTimeUtil.getNumberByPattern("yyyy");
     * </pre></blockquote>
     *
     *
     * @param pattern  "yyyy, MM, dd, HH, mm, ss,SSS"
     * @return 현재의 날짜,달,연,시간,분,초 등을 나타내는 숫자값
     */
    public static int getNumberByPattern(String pattern) {
        SimpleDateFormat formatter =
                new SimpleDateFormat(pattern, Locale.KOREA);
        String dateString = formatter.format(new Date());
        return Integer.parseInt(dateString);
    }

    /**
     *인자로 전달된 시각을 표현하는 문자열에서 특정 부분의(년도 or 시 or 분 or 초 ...) 값을 숫자로 리턴한다.
     *
     *시각을 표현하는 문자열 2005/01/21 12:45:31 에서 초 부분을 나타내는 값을 얻어오려면 아래와 같이 코딩하면 된다.
     * <p>코드 사용예:
     * 	<p><blockquote><pre>
     *  int seconds = DateTimeUtiil.getNumberByPattern("2005/01/21 12:45:31","yyyy/MM/dd hh:mm:ss","ss");
     * </pre></blockquote>
     * @param dates 기준 시각
     * @param spattern <code>dates</code> 시각을 표현하는 날짜 포멧
     * @param pattern  "yyyy, MM, dd, HH, mm, ss and more"
     * @return formatted string representation of current day and time with  your pattern.
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 spattern format 에 맞지 않는 경우.
     */
    public static int getNumberByPattern(
            String dates,
            String spattern,
            String pattern)
                    throws ParseException {
        SimpleDateFormat formatter =
                new SimpleDateFormat(pattern, Locale.KOREA);
        String dateString = formatter.format(check(dates, spattern));
        return Integer.parseInt(dateString);
    }

    /**
     * 현재 시각을  인자로 전달된 형태의 포멧으로 표현하는 문자열을 리턴한다.
     *  코드 사용예:
     * 	<p><blockquote><pre>
     * String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss:SSS");
     * </pre></blockquote>
     *
     * @param  pattern  "yyyy, MM, dd, HH, mm, ss and more"
     * @return formatted string representation of current day and time with  your pattern.
     */
    public static String getFormatString(String pattern) {
        SimpleDateFormat formatter =
                new SimpleDateFormat(pattern, Locale.KOREA);
        String dateString = formatter.format(new Date());
        return dateString;
    }

    /**
     * String으로 전달된 날짜를  인자로 전달된 형태의 포멧으로 표현하는 문자열을 리턴한다.
     *  코드 사용예:
     * 	<p><blockquote><pre>
     * String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss:SSS");
     * </pre></blockquote>
     *
     * @param  pattern  "yyyy, MM, dd, HH, mm, ss and more"
     * @return formatted string representation of current day and time with  your pattern.
     */
    public static String getFormatString(String param, String paramPattern, String returnPattern) {
        SimpleDateFormat parmFormatter = new SimpleDateFormat(paramPattern, Locale.KOREA);
        Date date = new Date();
        try {
            date = parmFormatter.parse(param);
        } catch (ParseException e) {
            logger.debug("ParseException");
        }

        SimpleDateFormat formatter = new SimpleDateFormat(returnPattern, Locale.KOREA);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 현재 시각을 "yyyyMMdd" 형태의 문자열로 표현하여 리턴한다.
     * 예) "20040205"
     * <code>getFormatString("yyyyMMdd");</code>
     * @return formatted string representation of current day with  "yyyyMMdd".
     * @see #getFormatString(String)
     */
    public static String getShortDateString() {
        return getFormatString("yyyyMMdd");
    }

    /**
     * 현재 시각을 "HHmmss" 형태의 문자열로 표현하여 리턴한다.
     * <code>getFormatString("HHmmss");</code>
     * @return formatted string representation of current time with  "HHmmss".
     * 	 @see #getFormatString(String)
     */
    public static String getShortTimeString() {

        return getFormatString("HHmmss");
    }

    /**
     * 현재 시각을 "yyyy-MM-dd-HH:mm:ss:SSS" 형태의 문자열로 표현하여 리턴한다.
     * <code>getFormatString("yyyy-MM-dd-HH:mm:ss:SSS");</code>
     * @return formatted string representation of current time with  "yyyy-MM-dd-HH:mm:ss".
     * @see #getFormatString(String)
     */
    public static String getTimeStampString() {

        return getFormatString("yyyy-MM-dd-HH:mm:ss:SSS");
    }

    /**
     *  현재 시각을 "HH:mm:ssSSS" 형태의 문자열로 표현하여 리턴한다.
     *  코드 사용예:
     * 	<p><blockquote><pre>
     * String timeString= getFormatString("HH:mm:ss");
     * </pre></blockquote>
     * @return formatted string representation of current time with  "HH:mm:ss".
     *	@see #getFormatString(String)
     */
    public static String getTimeString() {
        return getFormatString("HH:mm:ss");
    }

    /**
     * 인자로 전달된 "yyyyMMdd" 형태의 날짜가 무슨 요일 인지 리턴한다.
     * 요일에 해당하는 값은 숫자로 리턴되고 이 값은 1~7에 해당한다.
     *
     * 사용예:
     * <p><blockquote><pre>
     * String s = "20000529";
     *  int dayOfWeek = whichDay(s);
     *  if (dayOfWeek == Calendar.MONDAY)
     *      월요일: " + dayOfWeek
     *  if (dayOfWeek == Calendar.TUESDAY)
     *      화요일: " + dayOfWeek
     * </pre></blockquote>
     *
     * return days between two date strings with default defined format.(yyyyMMdd)
     * @param s date string you want to check.
     * @return 다음의 값중 하나를 리턴.<pre>
     *          1: 일요일 (Calendar.SUNDAY)
     *          2: 월요일 (Calendar.MONDAY)
     *          3: 화요일 (Calendar.TUESDAY)
     *          4: 수요일 (Calendar.WENDESDAY)
     *          5: 목요일 (Calendar.THURSDAY)
     *          6: 금요일 (Calendar.FRIDAY)
     *          7: 토요일 (Calendar.SATURDAY)
     * </pre>
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이  "yyyyMMdd" 형식에 맞지 않는 경우.
     *
     */
    public static int whichDay(String s) throws ParseException {
        return whichDay(s, "yyyyMMdd");
    }

    public static String whichDay(String s, int expStyle) throws ParseException {
        String returnStr = "";
        int whichDay = whichDay(s, "yyyyMMdd");

        if(expStyle == 0){	//영대소문자 3글자 표기
            if(whichDay == 1){
                returnStr = "Sun";
            }else if(whichDay == 2){
                returnStr = "Mon";
            }else if(whichDay == 3){
                returnStr = "Tue";
            }else if(whichDay == 4){
                returnStr = "Wen";
            }else if(whichDay == 5){
                returnStr = "Thu";
            }else if(whichDay == 6){
                returnStr = "Fri";
            }else if(whichDay == 7){
                returnStr = "Sat";
            }
        }
        return returnStr;
    }

    /**
     * 인자로 전달된 <code>format</code>형태의 날짜 <code>s</code>가 무슨 요일 인지 리턴한다.
     * 요일에 해당하는 값은 숫자로 리턴되고 이 값은 1~7에 해당한다.
     *
     * 사용예:
     * <p><blockquote><pre>
     * String s = "2000-05-29";
     *  int dayOfWeek = whichDay(s,"yyyy-MM-dd");
     *  if (dayOfWeek == Calendar.MONDAY)
     *      월요일: " + dayOfWeek
     *  if (dayOfWeek == Calendar.TUESDAY)
     *      화요일: " + dayOfWeek
     * </pre></blockquote>
     *
     * @param s date string you want to check.
     * @param format 날짜를 표현하는 포멧.
     * @return 다음의 값중 하나를 리턴.<pre>
     *          1: 일요일 (Calendar.SUNDAY)
     *          2: 월요일 (Calendar.MONDAY)
     *          3: 화요일 (Calendar.TUESDAY)
     *          4: 수요일 (Calendar.WENDESDAY)
     *          5: 목요일 (Calendar.THURSDAY)
     *          6: 금요일 (Calendar.FRIDAY)
     *          7: 토요일 (Calendar.SATURDAY)
     * </pre>
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이  format 형식에 맞지 않는 경우.
     */
    public static int whichDay(String s, String format)
            throws ParseException {
        SimpleDateFormat formatter =
                new SimpleDateFormat(format, Locale.KOREA);
        Date date = check(s, format);

        Calendar calendar = formatter.getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 인자로 전달된 <code>from</code> 날짜와 <code>to</code> 날짜 사이의 '날(day)'차이를 리턴한다. 두 날짜의 표현포멧은 "yyyyMMdd"이다.
     * <p>
     * 2005년 1월1일부터 2005년3월25일 사이의 날짜수를 구하는 코드:
     * 	<p><blockquote><pre>
     * 		int daysCount=DateTimeUtil.daysBetween("20050101","20050325");
     * </pre></blockquote>
     * return days between two date strings with default defined format.("yyyyMMdd")
     * @param from date string
     * @param to date string
     * @return  두 날짜 사이의 '날(day)'의 차이.
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 "yyyyMMdd" 형식에 맞지 않는 경우.
     */
    public static int daysBetween(String from, String to)
            throws ParseException {
        return daysBetween(from, to, "yyyyMMdd");
    }

    /**
     * 인자로 전달된 <code>from</code> 날짜와 <code>to</code> 날짜 사이의 '날(day)'차이를 리턴한다. 이때 두 날짜를 표현하는 포멧은 <code>format</code>을 사용한다.
     * <p>2005년 1월1일부터 2005년3월25일 사이의 날짜수를 구하는 코드:
     * 	<p><blockquote><pre>
     * 		int daysCount=DateTimeUtil.daysBetween("20050101","20050325","yyyyMMdd");
     * </pre></blockquote>
     * @param from date string
     * @param to date string
     * @param format 두 시각을 표현하는 포멧  문자열.
     * @return  두 시각 사이의 '날(day)'의 차이.
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이  format 형식에 맞지 않는 경우.
     */
    public static int daysBetween(String from, String to, String format)
            throws ParseException {
        Date d1 = check(from, format);
        Date d2 = check(to, format);

        long duration = d2.getTime() - d1.getTime();

        return (int) (duration / (1000 * 60 * 60 * 24));
        // seconds in 1 day
    }

    /**
     * 인자로 전달된 date 값이 현재 시간 이후 여부
     * @param d1 date
     * @return */
    public static boolean chcekToAfterDate(Date d1) {
        return d1.compareTo(new Date()) > 0 ;
    }

    /**
     * 인자로 전달된 <code>from</code>시각과   <code>to</code> 시각 사이의 '시간 (time)'차이를 리턴한다. 두 시각의 표현포멧은 "yyyyMMdd"이다.
     *<p>2005년 1월1일부터 2005년3월25일 사이의 시간을  구하는 코드:
     * 	<p><blockquote><pre>
     * 		int timesCount=DateTimeUtil.timesBetween("20050101","20050325");
     * </pre></blockquote>
     * @param from date string
     * @param to date string
     * @return  두 시각 사이의 '시간(time)'의 차이.
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 "yyyyMMdd" 형식에 맞지 않는 경우.
     */
    public static int timesBetween(String from, String to)
            throws ParseException {
        return timesBetween(from, to, "yyyyMMdd");
    }

    /**
     * 인자로 전달된 <code>from</code> 시각과  <code>to</code> 시각 사이의 '시간(time)'차이를 리턴한다. 두 시각의 표현포멧은 <code>format</code>을 사용한다.
     * <p>2005년 1월1일부터 11시와  2005년3월25일 23시 사이의 시간을 구하는 코드:
     * 	<p><blockquote><pre>
     * 		int timesCount=DateTimeUtil.timesBetween("2005/01/01/ 11","2005/03/25 23","yyyy/MM/dd hh");
     * </pre></blockquote>
     * @param from date string
     * @param to date string
     * @param format 시각들을 표현하는 포멧 문자열.
     * @return  두 시각 사이의 '시간(time)'의 차이.
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>format</code> 형식에 맞지 않는 경우.
     */
    public static int timesBetween(String from, String to, String format)
            throws ParseException {

        Date d1 = check(from, format);
        Date d2 = check(to, format);

        long duration = d2.getTime() - d1.getTime();

        return (int) (duration / (1000 * 60 * 60));
        // seconds in 1 day
    }
    /**
     * 인자로 전달된 <code>from</code> 시각과  <code>to</code> 시각 사이의 '분(minute)'차이를 리턴한다.  두 시간의 표현포멧은 <code>format</code>을 사용한다.
     * <p<2005년 1월1일 11시 10분 부터 2005년3월25일 23시 59분 사이의 '분'을 구하는 코드:
     * 	<p><blockquote><pre>
     * 		int minCount=DateTimeUtil.minsBetween("2005/01/01 11:10","2005/03/25 23:59","yyyy/MM/dd hh:mm");
     * </pre></blockquote>
     * @param from date string
     * @param to date string
     * @param format 시각들을 표현하는 포멧 문자열.
     * @return  두 시각  사이의 '시간(time)'의 차이.
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>format</code> 형식에 맞지 않는 경우.
     */
    public static int minsBetween(String from, String to, String format)
            throws ParseException {

        Date d1 = check(from, format);
        Date d2 = check(to, format);

        long duration = d2.getTime() - d1.getTime();

        return (int) (duration / (1000 * 60));
        // seconds in 1 day
    }

    /**
     * 인자로 전달된 <code>from</code> 시각과  <code>to</code> 시각 사이의 개월수 차이를  리턴한다.  두 시각의 표현 포멧은 <code>"yyyyMMdd"</code>를 사용한다.
     * 2005년 1월1일부터 2005년3월25일 사이의 개월수 차 를 표현하는 문자열을 구하는 코드:
     * 	<p><blockquote><pre>
     * 		int monthGap=DateTimeUtil.monthsBetween("20050101","20050325");
     *
     * </pre></blockquote>
     * @param from date string
     * @param to date string
     * @return  두 날짜 사이의 개월수 차이
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>"yyyyMMdd"</code> 형식에 맞지 않는 경우.
     */
    public static int monthsBetween(String from, String to)
            throws ParseException {
        return monthsBetween(from, to, "yyyyMMdd");
    }
    /**
     * 인자로 전달된 <code>from</code> 시각과  <code>to</code> 시각 사이의 개월수 차이를  리턴한다.  두 시각의 표현 포멧은 <code>format</code>을 사용한다.
     * 2005년 1월1일 11시 10분 부터 2005년3월25일 23시 59분 사이의 개월수 차 를 표현하는 문자열을 구하는 코드:
     * 	<p><blockquote><pre>
     * 		int monthGap=DateTimeUtil.monthsBetween("2005/01/01 11:10","2005/03/25 23:59","yyyy/MM/dd hh:mm");
     * </pre></blockquote>
     * @param from date string
     * @param to date string
     * @param format 시각들을 표현하는 포멧 문자열.
     * @return  두 날짜 사이의 개월수 차이
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>format</code> 형식에 맞지 않는 경우.
     */
    public static int monthsBetween(String from, String to, String format)
            throws ParseException {
        Date fromDate = check(from, format);
        Date toDate = check(to, format);

        // if two date are same, return 0.
        if (fromDate.compareTo(toDate) == 0)
            return 0;

        SimpleDateFormat yearFormat =
                new SimpleDateFormat("yyyy", Locale.KOREA);
        SimpleDateFormat monthFormat =
                new SimpleDateFormat("MM", Locale.KOREA);
        SimpleDateFormat dayFormat =
                new SimpleDateFormat("dd", Locale.KOREA);

        int fromYear = Integer.parseInt(yearFormat.format(fromDate));
        int toYear = Integer.parseInt(yearFormat.format(toDate));
        int fromMonth = Integer.parseInt(monthFormat.format(fromDate));
        int toMonth = Integer.parseInt(monthFormat.format(toDate));
        int fromDay = Integer.parseInt(dayFormat.format(fromDate));
        int toDay = Integer.parseInt(dayFormat.format(toDate));

        int result = 0;
        result += (toYear - fromYear) * 12;
        result += toMonth - fromMonth;

        //        if (((toDay - fromDay) < 0) ) result += fromDate.compareTo(toDate);
        // ceil과 floor의 효과
        if ( toDay - fromDay > 0 )
            result += toDate.compareTo(fromDate);

        return result;
    }

    /**
     * 인자로 전달된 <code>from</code> 시각과  <code>to</code> 시각 사이의 년도 차이를 리턴한다. 두 시각의 표현 포멧은 "yyyyMMdd"이다.

     * <p> 1975년 2월 5일 태어난 사람의 현재 만 나이를 구하는 코드:
     *  <p><blockquote><pre>
     * int age=DateTimeUtil.ageBetween("19750205",getFormatString("yyyyMMdd"));
     * </pre></blockquote>
     * @param  from date string
     * @param  to date string
     * @return 두 날짜 사이의 년도 차이(나이)를 리턴한다.
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>"yyyyMMdd"</code> 형식에 맞지 않는 경우.
     */
    public static int ageBetween(String from, String to)
            throws ParseException {
        return ageBetween(from, to, "yyyyMMdd");
    }

    /**
     * 현재 시각을 기준으로 인자로 전달된 날짜 사이의 연도 차이를 리턴한다. 날짜의 표현 포멧은 "yyyyMMdd"이다.
     * return age between two date strings with default defined format.("yyyyMMdd")
     *<p> 1975년 2월 5일 태어난 사람의 현재 만 나이를 구하는 코드:
     *  <p><blockquote><pre>
     * int age=DateTimeUtil.age("19750205");
     * </pre></blockquote>
     * @param birth 비교하고자 하는 기준이 되는 날짜
     * @return 현재 날짜와 birth 사이의 년도 차이를 리턴한다.
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>"yyyyMMdd'</code> 형식에 맞지 않는 경우.
     * @see #ageBetween(String, String)
     */
    public static int age(String birth) throws ParseException {
        return ageBetween(birth, getFormatString("yyyyMMdd"), "yyyyMMdd");
    }

    /**
     * 인자로 전달된 <code>from</code> 시각과  <code>to</code> 시각 사이의 년도 차이를 리턴한다. 두 시각의 표현 포멧은<code>format</code>을 사용한다.
     * <p>1975년 2월 5일 태어난 사람의 현재 만 나이를 구하는 코드:
     *  <p><blockquote><pre>
     * int age=DateTimeUtil.ageBetween("19750205",getFormatString("yyyyMMdd"),"yyyyMMdd");
     * </pre></blockquote>
     * @param  from date string
     * @param  to date string
     * @param format 날짜를 표현할 때 사용할 표현 포멧을 나타내는 문자열.
     * @return 두 날짜 사이의 년도 차이를 리턴한다.
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>format</code> 형식에 맞지 않는 경우.
     */
    public static int ageBetween(String from, String to, String format)
            throws ParseException {
        return (daysBetween(from, to, format) / 365);
    }

    /**
     * 인자로 전달된 날짜 <code>s</code>를 기준으로 특정 일(day) 수를 더한 날짜를 표현하는 문자열을 리턴한다. 날짜의 포현 포멧은 "yyyyMMdd"를 사용한다.
     * <p>2005년 2월 25일에서 일주일(7일) 후의 날짜를 표현하는 문자열을 얻어오는 코드 사용예:
     * 	<p><blockquote><pre>
     * String dateString=DateTimeUtil.addDays("20050225",7);
     * </pre></blockquote>
     * @param s 기준 날짜
     * @param day 더할 일수
     * @return 더해진 날짜를 표현하는 문자열
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>"yyyyMMdd"</code> 형식에 맞지 않는 경우.
     */
    public static String addDays(String s, int day)
            throws ParseException {
        return addDays(s, day, "yyyyMMdd");
    }

    /**
     * 오늘을 기준으로 특정 일(day) 수를 더한 날짜를 표현하는 문자열을 리턴한다. 날짜의 포현 포멧은 "yyyyMMdd"를 사용한다.
     * <p>오늘을 기준으로 일주일(7일) 후의 날짜를 표현하는 문자열 얻어오는  코드 사용예:
     * 	<p><blockquote><pre>
     * String dateString=DateTimeUtil.addDays(7);
     * </pre></blockquote>
     * @param day 더할 일수
     * @return 더해진 날짜를 표현하는 문자열
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>"yyyyMMdd"</code> 형식에 맞지 않는 경우.
     */
    public static String addDays(int day) throws ParseException {
        return addDays(getShortDateString(), day, "yyyyMMdd");
    }

    /**
     * 오늘기준으로  특정 일(day) 수를 더한 날짜를 구한 후 인자로 전달된 <code>format</code> 형식으로 표현하는 문자열을 리턴한다.
     * <p>오늘을 기준으로 일주일(7일) 후의 날짜를 "yyyy/MM/dd" 형식으로 표현하는 문자열 얻어오는  코드 사용예:
     * 	<p><blockquote><pre>
     * String dateString=DateTimeUtil.addDays(7,"yyyy/MM/dd");
     * </pre></blockquote>
     * @param day 더할 일수
     * @param format 날짜 표현 포멧
     * @return 더해진 날짜를 표현하는 문자열
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>"yyyyMMdd"</code> 형식에 맞지 않는 경우.
     */
    public static String addDays(int day, String format)
            throws ParseException {
        String today = getShortDateString();
        String tmp = addDays(today, day, "yyyyMMdd");
        return changeFormat(tmp, "yyyyMMdd", format);
    }

    /**
     * 인자로 전달된 날짜 <code>s</code> 에서  특정 일(day) 수를 더한 날짜를 인자로 전달된 <code>format</code> 형식으로 표현하는 문자열을 리턴한다.
     * <p>2005년 2월 25일에서 일주일(7일) 후의 날짜를 표현하는 문자열 얻어오는  코드 사용예:
     * 	<p><blockquote><pre>
     * String dateString=DateTimeUtil.addDays("20050225",7,"yyyyMMdd");
     * </pre></blockquote>
     * 	@param s 기준 날짜
     * @param day 더할 일수
     * @param format 날짜 표현 포멧
     * @return 더해진 날짜를 표현하는 문자열
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>format</code> 형식에 맞지 않는 경우.
     */
    public static String addDays(String s, int day, String format)
            throws ParseException {
        SimpleDateFormat formatter =
                new SimpleDateFormat(format, Locale.KOREA);
        Date date = check(s, format);

        long tempTime = (long) day * 1000 * 60 * 60 * 24;
        date.setTime( date.getTime() + tempTime);
        return formatter.format(date);
    }
    /**
     * 인자로 전달된 시각 <code>s</code> 에서  특정 시간(time)을  더한 시각을  인자로 전달된 <code>format</code> 형식으로 표현하는 문자열을 리턴한다.
     * <p>2005년 2월 25일에서 일주일(7일) 후의 날짜를 표현하는 문자열 얻어오는  코드 사용예:
     * 	<p><blockquote><pre>
     * String dateString=DateTimeUtil.addDays("20050225",7,"yyyyMMdd");
     * </pre></blockquote>
     * 	@param s 기준 날짜
     * @param time 더할 시간
     * @param format 날짜 표현 포멧
     * @return 더해진 시각을 표현하는 문자열
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>format</code> 형식에 맞지 않는 경우.
     */
    public static String addTimes(String s, int time, String format)
            throws ParseException {
        SimpleDateFormat formatter =
                new SimpleDateFormat(format, Locale.KOREA);
        Date date = check(s, format);

        date.setTime(date.getTime() + (long)  1000 * 60 * 60 * time );
        return formatter.format(date);
    }
    /**
     * 현재시각 에서  특정 시간(time)을  더한 시각을  인자로 전달된 <code>format</code> 형식으로 표현하는 문자열을 리턴한다.
     * <p>현재시각에서 23시간 후를 표현하는 문자열 얻어오는  코드 사용예:
     * 	<p><blockquote><pre>
     * String dateString=DateTimeUtil.addDays(23,"yyyy/MM/dd hh:mm");
     * </pre></blockquote>
     * @param time 더할 시간
     * @param format 시각 표현 포멧
     * @return 더해진 시각을 표현하는 문자열
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>format</code> 형식에 맞지 않는 경우.
     */
    public static String addTimes( int time, String format)
            throws ParseException {
        String fomatted=		getFormatString(format);
        return addTimes(fomatted,time,format);
    }

    /**
     * 세계표준시(Universal Time Coordinated)를 "yyyy-MM-ddTHH:mm:ss:SSSZ" 형태의 포멧으로 리턴한다.
     * @return UTC time
     */
    public static String getUTCTimeString()
    {
        String ret="";
        try{
            ret=addTimes(-9,"yyyy-MM-dd HH:mm:ss:SSS ");
            char rets[]=ret.toCharArray();
            rets[10]='T';
            rets[23]='Z';
            ret=new String(rets);

        }catch(ParseException e)
        {
            logger.debug("ParseException");
        }
        return ret;
    }
    /**
     * 인자로 전달된 날짜 <code>s</code>를 기준으로 특정 개월(month) 수를 더한 날짜를 표현하는 문자열을 리턴한다. 날짜의 포현 포멧은 "yyyyMMdd"를 사용한다.
     * <p>2005년 2월 25일에서 7개월 전의 날짜를 표현하는 문자열을 얻어오는 코드 사용예:
     * 	<p><blockquote><pre>
     * String monthString=DateTimeUtil.addDays("20050225",-7);
     * </pre></blockquote>
     * @param s 기준 날짜
     * @param month 더할 개월수
     * @return 더해진 날짜를 표현하는 문자열
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>"yyyyMMdd"</code> 형식에 맞지 않는 경우.
     */
    public static String addMonths(String s, int month) throws ParseException {
        return addMonths(s, month, "yyyyMMdd");
    }

    /**
     * 인자로 전달된 시각 <code>s</code> 에서  특정 개월(month)을  더한 시각을  인자로 전달된 <code>format</code> 형식으로 표현하는 문자열을 리턴한다.
     * <p>2005년 2월 25일에서 7개월 후의 날짜를 표현하는 문자열 얻어오는  코드 사용예:
     * 	<p><blockquote><pre>
     * String dateString=DateTimeUtil.addDays("20050225",7,"yyyyMMdd");
     * </pre></blockquote>
     * 	@param s 기준 날짜
     * @param addMonth 더할 개월
     * @param format 날짜 표현 포멧
     * @return 더해진 시각을 표현하는 문자열
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>format</code> 형식에 맞지 않는 경우.
     */
    public static String addMonths(String s, int addMonth, String format)
            throws ParseException {
        SimpleDateFormat formatter =
                new SimpleDateFormat(format, Locale.KOREA);
        Date date = check(s, format);

        SimpleDateFormat yearFormat =
                new SimpleDateFormat("yyyy", Locale.KOREA);
        SimpleDateFormat monthFormat =
                new SimpleDateFormat("MM", Locale.KOREA);
        SimpleDateFormat dayFormat =
                new SimpleDateFormat("dd", Locale.KOREA);
        int year = Integer.parseInt(yearFormat.format(date));
        int month = Integer.parseInt(monthFormat.format(date));
        int day = Integer.parseInt(dayFormat.format(date));

        month += addMonth;
        if (addMonth > 0) {
            while (month > 12) {
                month -= 12;
                year += 1;
            }
        } else {
            while (month <= 0) {
                month += 12;
                year -= 1;
            }
        }
        DecimalFormat fourDf = new DecimalFormat("0000");
        DecimalFormat twoDf = new DecimalFormat("00");
        String tempDate = fourDf.format(year) + twoDf.format(month) + twoDf.format(day);
        Date targetDate = null;

        try {
            targetDate = check(tempDate, "yyyyMMdd");
        } catch (ParseException pe) {
            day = lastDay(year, month);
            tempDate =
                    fourDf.format(year) + twoDf.format(month) + twoDf.format(day);
            targetDate = check(tempDate, "yyyyMMdd");
        }

        return formatter.format(targetDate);
    }
    /**
     * 인자로 전달된 날짜 <code>s</code>를 기준으로 특정 연도(year) 수를 더한 날짜를 표현하는 문자열을 리턴한다. 날짜의 포현 포멧은 "yyyyMMdd"를 사용한다.
     * <p>2005년 2월 28일에서 3년 전의 날짜를 표현하는 문자열을 얻어오는 코드 사용예:
     * 	<p><blockquote><pre>
     * String yearString=DateTimeUtil.addDays("20050228",-3);
     * </pre></blockquote>
     * @param s 기준 날짜
     * @param year 더할 년수
     * @return 더해진 날짜를 표현하는 문자열
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>"yyyyMMdd"</code> 형식에 맞지 않는 경우.
     */
    public static String addYears(String s, int year)
            throws ParseException {
        return addYears(s, year, "yyyyMMdd");
    }
    /**
     * 인자로 전달된 날짜 <code>s</code>를 기준으로 특정 연도(year) 수를 더한 날짜를 표현하는 문자열을 리턴한다. 날짜의 포현 포멧은 <code>format</code>를 사용한다.
     * <p>2005년 2월 28일에서 3년 전의 날짜를 표현하는 문자열을 얻어오는 코드 사용예:
     * 	<p><blockquote><pre>
     * String yearString=DateTimeUtil.addDays("20050228",-3,"yyyyMMdd");
     * </pre></blockquote>
     * @param s 기준 날짜
     * @param year 더할 년수
     * @param format 날짜를 표현하는 포현 포멧
     * @return 더해진 날짜를 표현하는 문자열
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>format</code> 형식에 맞지 않는 경우.
     */
    public static String addYears(String s, int year, String format)
            throws ParseException {
        SimpleDateFormat formatter =
                new SimpleDateFormat(format, Locale.KOREA);
        Date date = check(s, format);
        date.setTime(
                date.getTime() + (long) year * 1000 * 60 * 60 * 24 * (365 + 1) );
        return formatter.format(date);
    }

    /**
     * 인자로 전달된 날짜 <code>src</code>에 해당하는 달의 마지막 날을 표현하는 날짜를 리턴한다. 날짜 표시 포멧으로 "yyyyMMdd"을 사용한다.
     *  코드 사용예:
     * 	<p><blockquote><pre>
     * String datesStr=DateTimeUtil.lastDayOfMonth("20050203");
     * datesStr.equals("20050228");
     * </pre></blockquote>
     * @param src 기준이 되는 날짜
     * @return src에 날짜중 그 달의 마지막 날을 표시하는 문자열
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>"yyyyMMdd"</code> 형식에 맞지 않는 경우.
     */

    public static String lastDayOfMonth(String src)
            throws ParseException {
        return lastDayOfMonth(src, "yyyyMMdd");
    }
    /**
     * 인자로 전달된 날짜 <code>src</code>에 해당하는 달의 마지막 날을 표현하는 날짜를 리턴한다. 날짜 표시 포멧으로 <code>format</code>을 사용한다.
     *  코드 사용예:
     * 	<p><blockquote><pre>
     * String datesStr=DateTimeUtil.lastDayOfMonth("20050203","yyyyMMdd");
     * datesStr.equals("20050228");
     * </pre></blockquote>
     * @param src 기준이 되는 날짜
     * @param format 날짜를 표시하는 표시포멧
     * @return src에 날짜중 그 달의 마지막 날을 표시하는 문자열
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>format</code> 형식에 맞지 않는 경우.
     */
    public static String lastDayOfMonth(String src, String format)
            throws ParseException {
        SimpleDateFormat formatter =
                new SimpleDateFormat(format, Locale.KOREA);
        Date date = check(src, format);

        SimpleDateFormat yearFormat =
                new SimpleDateFormat("yyyy", Locale.KOREA);
        SimpleDateFormat monthFormat =
                new SimpleDateFormat("MM", Locale.KOREA);

        int year = Integer.parseInt(yearFormat.format(date));
        int month = Integer.parseInt(monthFormat.format(date));
        int day = lastDay(year, month);

        DecimalFormat fourDf = new DecimalFormat("0000");
        DecimalFormat twoDf = new DecimalFormat("00");
        String tempDate = fourDf.format(year) + twoDf.format(month) + twoDf.format(day);
        date = check(tempDate, format);

        return formatter.format(date);
    }

    /**
     * 특정 연도 특정 월의 일수를 리턴한다.
     * <p>2005 년 2월은 몇일이 존재하는지 확인하는  코드 사용예:
     * 	<p><blockquote><pre>
     * int days=DateTimeUtil.lastDay(2005,2);
     * </pre></blockquote>
     * @param year 년도
     * @param month 개월
     * @return 마지막 일자(일 수)
     */
    private static int lastDay(int year, int month) {
        int day = 0;
        switch (month) {
        case 1 :
        case 3 :
        case 5 :
        case 7 :
        case 8 :
        case 10 :
        case 12 :
            day = 31;
            break;
        case 2 :
            if (year % 4 == 0) {
                if ((year % 100) == 0 && (year % 400) != 0) {
                    day = 28;
                } else {
                    day = 29;
                }
            } else {
                day = 28;
            }
            break;
        default :
            day = 30;
        }
        return day;
    }
    /**
     * 인자로 전달된 날짜를 나타내는 <code>s</code> 문자열이 "yyyy/MM/dd HH:mm" 형식에 맞는지 확인한다.
     * @param s 확인하려는 날짜를 나타내는 문자열
     * @return 인자로 전달된 날짜를 나타내는 문자열.
     * @exception ParseException 잘못된 날짜이거나. 날짜를 표현하는 문자열이 <code>"yyyy/MM/dd HH:mm"</code> 형식에 맞지 않는 경우.
     */
    public static String checkDateTime(String s) throws ParseException {
        check(s, "yyyy/MM/dd HH:mm");
        return s;
    }

    /**
     * 기준 시각 (<code>checkTime</code>)이 시작시각(<code>startTime</code>) 과 종료시각(<code>endTime</code>) 사이에 위치하는지 여부를 리턴한다.
     * 인자로 전달되는 시각들은 "HH:mm" 또는  "HH/mm" 형태의 포멧이어야 한다.
     * <p>영업시간이 오전 9시부터 18시 30분까지 일 경우 현재 시간에 영업이 가능한 지 확인하는  코드 사용예:
     * 	<p><blockquote><pre>
     *	boolean isOpen = DateTimeUtil.isMiddleTime("09:00","18:30",getFormatString("HH:mm"));
     * </pre></blockquote>
     * @param startTime 시작 시각 ("HH:mm" or "HH/mm" 포멧)
     * @param endTime 종료 시각 ("HH:mm" or "HH/mm" 포멧)
     * @param checkTime 기준 시각 ("HH:mm" or "HH/mm" 포멧)
     * @return <tt>true</tt> 기준 시각 (<code>checkTime</code>)이 시작시각(<code>startTime</code>) 과 종료시각(<code>endTime</code>) 사이에 위치한다.
     * @throws ParseException 인자로 전달된 시각이 지정된 포멧("HH:mm" or "HH/mm" 에) 맞지 않거나 올바른 시간이 아닐경우 발생.
     */
    public static boolean isMiddleTime(
            String startTime,
            String endTime,
            String checkTime)
                    throws ParseException {
        Date a = getDateInstance(startTime);
        Date b = getDateInstance(endTime);
        Date c = getDateInstance(checkTime);
        return isMiddleTime(a, b, c);

    }
    /**
     * 기준 시각 (<code>checkTime</code>)이 시작시각(<code>startTime</code>) 과 종료시각(<code>endTime</code>) 사이에 위치하는지 여부를 리턴한다.
     * @param startTime 시작 시각
     * @param endTime 종료 시각
     * @param checkTime 기준 시각
     * @return <tt>true</tt> 기준 시각 (<code>checkTime</code>)이 시작시각(<code>startTime</code>) 과 종료시각(<code>endTime</code>) 사이에 위치한다.
     */
    public static boolean isMiddleTime(
            Date startTime,
            Date endTime,
            Date checkTime) {

        if (startTime.before(endTime)) {
            if (endTime.after(checkTime) && startTime.before(checkTime))
                return true;
        } else {
            if (endTime.after(checkTime) || startTime.before(checkTime)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 현재 시각이 시작시각(<code>startTime</code>) 과 종료시각(<code>endTime</code>) 사이에 위치하는지 여부를 리턴한다.
     * 인자로 전달되는 시각들은 "HH:mm" 또는  "HH/mm" 형태의 포멧이어야 한다.
     * <p>영업시간이 오전 9시부터 18시 30분까지 일 경우 현재 시간에 영업이 가능한 지 확인하는  코드 사용예:
     * 	<p><blockquote><pre>
     *	boolean isOpen = DateTimeUtil.isMiddleTime("09:00","18:30");
     * </pre></blockquote>
     * @param startTime 시작 시각 ("HH:mm" or "HH/mm" 포멧)
     * @param endTime 종료 시각 ("HH:mm" or "HH/mm" 포멧)
     * @return <tt>true</tt> 현재 시각이 시작 시각(<code>startTime</code>) 과 종료 시각(<code>endTime</code>) 사이에 위치한다.
     * @throws ParseException 인자로 전달된 시각이 지정된 포멧("HH:mm" or "HH/mm" 에) 맞지 않거나 올바른 시간이 아닐경우 발생.
     */
    public static boolean isMiddleTime(String startTime,String endTime) throws ParseException {

        String curTime=getFormatString("HH:mm");
        return isMiddleTime(startTime,endTime,curTime);


    }

    public static int getWeek(String inDate) throws ParseException {
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
        date = dateFormat.parse(inDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int week = cal.get(Calendar.DAY_OF_WEEK);
        return week;

    }


    public static int getWeekMonth(String inDate) throws ParseException {
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
        date = dateFormat.parse(inDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int week = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        return week;

    }

    public static long getCurrentTime(){
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }

    /**
     * 현재 월의 마지막 일자를 String 형태로 return
     * @return String lastDay ("dd") 형태
     * @throws ParseException
     */
    public static String getLastDayOfThisMonth() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
        Date currentTime = new Date ( );
        String toDay = formatter.format ( currentTime );
        String lastDay = DateTimeUtil.lastDayOfMonth(toDay);
        lastDay = lastDay.substring(6);
        return lastDay;
    }


    /**
     * 현재 일자,시간 기준으로 시작일 <= 현재 <= 종료일 을 boolean 형태로 return
     * @param startDate 년 월 일 시 분 초 , 14자리
     * @param endDate 년 월 일 시 분 초 , 14자리
     * @return
     */
    public static boolean checkValidDate(String startDate, String endDate) {

    	boolean retBool = false;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
        String today = formatter.format(new Date());

    	long todayLong = Long.parseLong(today);

    	if(startDate != null && !"".equals(startDate) && startDate.length() == 14
    			&& endDate != null && !"".equals(endDate) && endDate.length() == 14) {

    		if((Long.parseLong(startDate) <= todayLong) && (todayLong <= Long.parseLong(endDate))) {
    			retBool = true;
    		}

    	}

    	return retBool;
    }

    /**
	 * 현재일이 시작일과 종료일사이의 날자인지 비교
	 * @param pstngStartDate
	 * @param pstngEndDate
	 * @return
	 * @throws ParseException
	 */
	public static boolean isMiddleDate(String pstngStartDate, String pstngEndDate) throws ParseException {
	    LocalDate localdate = LocalDate.now();
	    LocalDate startLocalDate = LocalDate.parse(pstngStartDate);
	    LocalDate endLocalDate = LocalDate.parse(pstngEndDate);
	    // endDate는 포함하지 않으므로 +1일을 해줘야한다.
	    endLocalDate = endLocalDate.plusDays(1);

	    return (!localdate.isBefore(startLocalDate)) && (localdate.isBefore(endLocalDate));
	}

	/**
	 * 현재일시간이 시작일시간과 종료일시간사이의 날자인지 비교
	 * @param pstngStartDateTime : 2022-01-31 00:00:00
	 * @param pstngEndDateTime : 9999-12-31 23:59:59
	 * @return
	 * @throws ParseException
	 */
	public static boolean isMiddleDateTime(String pstngStartDateTime, String pstngEndDateTime) throws ParseException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime localdatetime = LocalDateTime.now();
	    LocalDateTime startLocalDateTime = LocalDateTime.parse(pstngStartDateTime, formatter);
	    LocalDateTime endLocalDateTime = LocalDateTime.parse(pstngEndDateTime, formatter);

	    
	    return (!localdatetime.isBefore(startLocalDateTime)) && (localdatetime.isBefore(endLocalDateTime));
	}

	/**
	 * 현재일시간이 시작일시간과 종료일시간사이의 날자인지 비교
	 * @param pstngStartDateTime : 20220101000000
	 * @param pstngEndDateTime : 20241231235959
	 * @return
	 * @throws ParseException
	 */
	public static boolean isMiddleDateTime2(String pstngStartDateTime, String pstngEndDateTime) throws ParseException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

		LocalDateTime localdatetime = LocalDateTime.now();
	    LocalDateTime startLocalDateTime = LocalDateTime.parse(pstngStartDateTime, formatter);
	    LocalDateTime endLocalDateTime = LocalDateTime.parse(pstngEndDateTime, formatter);

	    
	    return (!localdatetime.isBefore(startLocalDateTime)) && (localdatetime.isBefore(endLocalDateTime));
	}

	//js ver 대체
	public static int getJsver() {
		return DateTimeUtil.getMin();
	}

    public static boolean isToday(String str) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDate date;
        try {
            date = LocalDate.parse(str, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        LocalDate now = LocalDate.now();

        return now.equals(date);
    }
}
