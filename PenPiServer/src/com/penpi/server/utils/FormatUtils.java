package com.penpi.server.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtils {

	public static String currentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(new Date());
	}
}
