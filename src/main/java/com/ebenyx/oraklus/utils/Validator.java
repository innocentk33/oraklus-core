package com.ebenyx.oraklus.utils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	public static boolean isEmail(String email) {
		String EMAIL_PATTERN  = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher  matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean isProfessionalEmail(String email, String domain) {
		if(isEmail(email) && email.endsWith(domain))
			return true;
		else 
			return false;
	}

	public static boolean isPhoneNumber(String phoneNumber) {
		if (phoneNumber.matches("\\d{8}")) return true;
			return false;
	}

	public static boolean isPhoneNumber(String number, String format) {
		return false;
	}

	public static boolean isInteger(String value){
		final String INTEGER_PATTERN  = "[0-9]*[0-9]$";
		Pattern pattern = Pattern.compile(INTEGER_PATTERN);
		Matcher  matcher = pattern.matcher(value);
		return matcher.matches();
	}

	public static boolean isDouble(String value){
		final String RATE_PATTERN  = "[-+]?[0-9]*\\.?[0-9]*";
		Pattern pattern = Pattern.compile(RATE_PATTERN);
		Matcher  matcher = pattern.matcher(value);
		return matcher.matches();
	}

	public static boolean isDate(String date){
		final String DATE_PATTERN  = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
		Pattern pattern = Pattern.compile(DATE_PATTERN);
		Matcher  matcher = pattern.matcher(date);
		if(date.length() != 10)
			return false;
		return matcher.matches();
	}

	public static boolean isDateTime(String date){
		final String DATE_PATTERN  = "^(0?[1-9]|[12][0-9]|3[01])(\\/)((0[1-9])|(1[0-2]))(\\/)(20\\d{2})(\\s)(([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])$";
		Pattern pattern = Pattern.compile(DATE_PATTERN);
		Matcher  matcher = pattern.matcher(date);
		if(date.length() != 19)
			return false;
		return matcher.matches();
	}

	public static boolean isPeriod(String period) {
		final String PERIOD_PATTERN  = "^((0[1-9])|(1[0-2]))\\/(20\\d{2})$";
		Pattern pattern = Pattern.compile(PERIOD_PATTERN);
		Matcher  matcher = pattern.matcher(period);
		if(period.length() != 7)
			return false;
		return matcher.matches();
	}

	public static boolean dateOrder(Date start, Date end){
		return start.before(end);
	}

	public static boolean isTaxAccountNumber(String value){
		final String TAX_ACCOUNT_NUMBER_PATTERN  = "[0-9]*[A-Z]$";
		Pattern pattern = Pattern.compile(TAX_ACCOUNT_NUMBER_PATTERN);
		Matcher  matcher = pattern.matcher(value);
		return matcher.matches();
	}

	public static boolean isAccountNumber(String accountNumber) {
		if (accountNumber.matches("\\d{11}")) return true;
		return false;
	}

	public static boolean isRib(String rib) {
		if (rib.matches("\\d{2}")) return true;
		return false;
	}

	public static boolean isSocialNumber(String socialNumber) {
		if (socialNumber.matches("\\d{12}")) return true;
		return false;
	}

	public static boolean isPassword(String password) {
		if(password == null)
			return false;
		else if(!isInteger(password))
			return false;
		else if(password.length() != 4)
			return false;
		return true;
	}

	public static boolean isSecurePassword(String password) {
		if (password.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*=+]).{6,20})")) return true;
		return false;
	}

}
