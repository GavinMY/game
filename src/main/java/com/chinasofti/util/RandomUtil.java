package com.chinasofti.util;

import java.security.SecureRandom;
import org.apache.commons.lang.StringUtils;


public class RandomUtil {
	
	public static String getRandoms(int num) throws Exception {
		SecureRandom sr = new SecureRandom();
		double nextLong = sr.nextDouble();
		return StringUtils.substring(String.valueOf(nextLong),String.valueOf(nextLong).length() - num);
	}
}
