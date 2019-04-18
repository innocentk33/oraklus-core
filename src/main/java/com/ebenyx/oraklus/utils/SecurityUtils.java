package com.ebenyx.oraklus.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Service
public class SecurityUtils {

	private final static Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

	private static final String ALPHA_CAPS  = "ABCDEFGHJKMNPQRSTUVWXYZ";
	private static final String ALPHA   = "abcdefghjkmnpqrstuvwxyz";
	private static final String NUM     = "123456789";
	private static final String SPL_CHARS   = "!@#$%&*=+";

	public static String loginErrorMessage = new String();

	public static String securePassword(String passwordToHash, String salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes("UTF-8"));
			byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			return generatedPassword;
	}

	public static boolean matchesPassword(String password, String token, String encryption){
		try{
			if(securePassword(password, token).equals(encryption))
			return true;
			else
			return false;
		} catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
			return false;
		}
	}

	public static String generateWord(int length)	{
		String chars = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ123456789";
		StringBuilder pass = new StringBuilder();
		for(int x=0;x<length;x++)   {
			int i = (int)Math.floor(Math.random() * (chars.length() -1));
			pass.append(chars.charAt(i));
		}
		return pass.toString();
	}

	public static String generateWord(int length, String chars)	{
			StringBuilder pass = new StringBuilder();
		for(int x=0;x<length;x++)   {
			int i = (int)Math.floor(Math.random() * (chars.length() -1));
			pass.append(chars.charAt(i));
		}
		return pass.toString();
	}

	public static String generateSecurePassword(int minLen, int maxLen, int noOfCAPSAlpha, int noOfDigits, int noOfSplChars) {
		if(minLen > maxLen)
			throw new IllegalArgumentException("Min. Length > Max. Length!");
		if( (noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen )
			throw new IllegalArgumentException ("Min. Length should be at least sum of (CAPS, DIGITS, SPL CHARS) Length!");
		Random rnd = new Random();
		int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
		char[] pswd = new char[len];
		int index = 0;
		for (int i = 0; i < noOfCAPSAlpha; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
		}
		for (int i = 0; i < noOfDigits; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
		}
		for (int i = 0; i < noOfSplChars; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
		}
		for(int i = 0; i < len; i++) {
			if(pswd[i] == 0) {
				pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
			}
		}
		return new String(pswd);
	}

	private static int getNextIndex(Random rnd, int len, char[] pswd) {
		int index = rnd.nextInt(len);
		while(pswd[index = rnd.nextInt(len)] != 0);
		return index;
	}

}

