package com.ebenyx.oraklus.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

	public static String dateToString(Date date, String sFormat){
		return (date != null) ? new SimpleDateFormat(sFormat).format(date) : "";
	}

	public static String dateToString(LocalDate localDate){
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return (localDate != null) ? dateTimeFormatter.format(localDate) : "";
	}

	public static String dateToString(LocalDateTime localDateTime){
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return (localDateTime != null) ? dateTimeFormatter.format(localDateTime) : "";
	}

	public static Date stringToDate(String sDate, String sFormat) throws ParseException {
		return (sDate != null && sDate != "") ? new SimpleDateFormat(sFormat).parse(sDate) : null;
	}

	public static LocalDate stringToLocalDate(String date){
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(date, dateTimeFormatter);
	}

	public static LocalDateTime stringToLocalDateTime(String date){
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return LocalDateTime.parse(date, dateTimeFormatter);
	}

	public static LocalDate dateToLocaDate(Date dateToConvert) {
		return LocalDate.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault());
	}

	public static LocalDateTime dateToLocaDateTime(Date dateToConvert) {
		return LocalDateTime.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault());
	}

	public static Date localDateToDate(LocalDate dateToConvert) {
		return java.sql.Date.valueOf(dateToConvert);
	}

	public static Date localDateTimeToDate(LocalDateTime dateToConvert) {
		return java.sql.Timestamp.valueOf(dateToConvert);
	}

	public static Date addingDate(int d) throws Exception{
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, d);
		String newdate = dateformat.format(cal.getTime());
		return stringToDate(newdate, "dd/MM/yyyy");
	}

	public static Date addingDate(Date date, int d){
		try{
			if(date != null){
				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.DATE, d);
				String newdate = dateformat.format(cal.getTime());
				return stringToDate(newdate, "dd/MM/yyyy");
			}
			else
			return null;
		} catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	public static Date addingMonth(Date date, int m) throws Exception{
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, m);
		String newdate = dateformat.format(cal.getTime());
		return stringToDate(newdate, "dd/MM/yyyy");
	}

	public static Date addingYear(Date date, int y){
		return new Date();
	}

	public static int getDay(){
		Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
		return localCalendar.get(Calendar.DATE);
	}

	public static int getMonth(){
		Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
		return localCalendar.get(Calendar.MONTH) + 1;
	}

	public static int getYear(){
		Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
		return localCalendar.get(Calendar.YEAR);
	}

	public static Date getMonthLastDay(String monthFirstDate) throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(stringToDate(monthFirstDate, "dd/MM/yyyy"));
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	public static Date calculerDateEcheance(String dateEch, Integer le) throws Exception{
		Date date = DateUtils.stringToDate(dateEch, "dd/MM/yyyy");
		if(le != null && le !=0){
			String mmyyyy = dateEch.substring(2);
			String dd;
			if(le < 10)
				dd = "0"+le;
			else
				dd=""+le;
			date = DateUtils.stringToDate(dd+mmyyyy, "dd/MM/yyyy");
			if(DateUtils.stringToDate(dateEch, "dd/MM/yyyy").compareTo(date) > 0){
				date = DateUtils.addingDate(date, 30);
				mmyyyy = DateUtils.dateToString(date, "dd/MM/yyyy").substring(2);
				date = DateUtils.stringToDate(dd+mmyyyy, "dd/MM/yyyy");
			}
		}
		return date;
	}

	public static int dateDifference(Date date1, Date date2){
		long CONST_DURATION_OF_DAY = 1000l * 60 * 60 * 24;
		long diff = Math.abs(date2.getTime() - date1.getTime());
		int numberOfDay = (int) ((long)diff/CONST_DURATION_OF_DAY) - 1;
		if(numberOfDay < 0) numberOfDay = 0;
		return numberOfDay;
	}

	public static int howOld(Date birthday){
		if(birthday == null)
			return 0;
		Date today = new Date();
		LocalDate localToday = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localBirthday = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return Period.between(localBirthday, localToday).getYears();
	}

	public static Date dateGMT(){
		return convertToGMT(new Date());
	}

	public static Date convertToGMT(Date date) {
		TimeZone tz = TimeZone.getDefault();
		Date ret = new Date( date.getTime() - tz.getRawOffset() );
		if ( tz.inDaylightTime( ret )){
			Date dstDate = new Date( ret.getTime() - tz.getDSTSavings() );
			if ( tz.inDaylightTime( dstDate )) {
				ret = dstDate;
			}
		}
		return ret;
	}

	public static String stringGMT() {
		return dateToString(dateGMT(), "dd/MM/yyyy HH:mm:ss");
	}

	public static int getYears(Date naissance) {
		int annees = 0;
		int mois = 0;
		int jours = 0;
		Calendar calendrierNaissance = Calendar.getInstance();
		calendrierNaissance.setTimeInMillis(naissance.getTime());
		long maintenant = System.currentTimeMillis();
		Calendar calendrierMaintenant = Calendar.getInstance();
		calendrierMaintenant.setTimeInMillis(maintenant);
		annees = calendrierMaintenant.get(Calendar.YEAR) - calendrierNaissance.get(Calendar.YEAR);
		int moisMaintenant = calendrierMaintenant.get(Calendar.MONTH) + 1;
		int moisNaissance = calendrierNaissance.get(Calendar.MONTH) + 1;
		mois = moisMaintenant - moisNaissance;
		if (mois < 0) {
			annees--;
			mois = 12 - moisNaissance + moisMaintenant;
			if (calendrierMaintenant.get(Calendar.DATE) < calendrierNaissance.get(Calendar.DATE)) {
				mois--;
			}
		} else if (mois == 0 && calendrierMaintenant.get(Calendar.DATE) < calendrierNaissance.get(Calendar.DATE)) {
			annees--;
			mois = 11;
		}
		if (calendrierMaintenant.get(Calendar.DATE) > calendrierNaissance.get(Calendar.DATE)) {
			jours = calendrierMaintenant.get(Calendar.DATE) - calendrierNaissance.get(Calendar.DATE);
		} else if (calendrierMaintenant.get(Calendar.DATE) < calendrierNaissance.get(Calendar.DATE)) {
			int aujourdhui = calendrierMaintenant.get(Calendar.DAY_OF_MONTH);
			calendrierMaintenant.add(Calendar.MONTH, -1);
			jours = calendrierMaintenant.getActualMaximum(Calendar.DAY_OF_MONTH) - calendrierNaissance.get(Calendar.DAY_OF_MONTH) + aujourdhui;
		} else {
			jours = 0;
			if (mois == 12) {
				annees++;
				mois = 0;
			}
		}
		return annees;
	}

}

