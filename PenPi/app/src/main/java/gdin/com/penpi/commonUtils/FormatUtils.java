package gdin.com.penpi.commonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtils {

	public static String formatTime(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}
}
