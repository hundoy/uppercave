package com.zohar.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;

public class ToolUtil {
	public static JFrame mainFrame = null;
	
	public static <T> boolean isNullOrEmpty(Collection<T> col){
		if (col==null || col.size()==0){
			return true;
		} else{
			return false;
		}
	}
	
	public static boolean isNullOrEmpty(Object[] arr){
		if (arr==null || arr.length==0){
			return true;
		} else{
			return false;
		}
	}
	
	public static boolean isNullOrBlank(String str){
		if (str==null || str.trim().length()==0){
			return true;
		} else{
			return false;
		}
	}
	
	public static String genTimeSuffix(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String result = df.format(new Date());
		return result;
	}

    public static String parseTime(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String result = df.format(date);
        return result;
    }

    public static String parseDate(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String result = df.format(date);
        return result;
    }

    public static String parseDateWithFormat(Date date, String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        String result = df.format(date);
        return result;
    }

	public static void backUpFile(String filePath, String fileBak) {
		File source = new File(filePath);
		File target = new File(fileBak);
		FileChannel in = null;  
	    FileChannel out = null;  
	    FileInputStream inStream = null;  
	    FileOutputStream outStream = null;  
	    try {  
	        inStream = new FileInputStream(source);  
	        outStream = new FileOutputStream(target);  
	        in = inStream.getChannel();  
	        out = outStream.getChannel();  
	        in.transferTo(0, in.size(), out);  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        try {
	        	inStream.close();
				in.close();
				outStream.close();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }  
	}

	public static int randomInt(int from, int to){
        Random random = new Random();
        return random.nextInt(to-from)+from;
    }

    public static String randomString(int length){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < length; i ++) {
            int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
            sb.append((char)(choice + random.nextInt(26)));
        }
        return sb.toString();
    }

    public static Date randomDate(int fromy, int toy){
        Random random = new Random();
        int y = random.nextInt(toy-fromy)+fromy;
        int m = random.nextInt(12); // 0-11;
        int d = random.nextInt(28)+1; // 0-27
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, y);
        cal.set(Calendar.MONTH, m);
        cal.set(Calendar.DAY_OF_MONTH, d);
        return cal.getTime();
    }

    public static float randomFloat(float fromf, float tof, float dec) {
        Random random = new Random();
        float rf = random.nextFloat()*(tof-fromf)+fromf;
        return (float)(Math.round(rf*Math.pow(10, dec))/Math.pow(10, dec));
    }

    public static String toCamelCase(String name){
        StringBuilder result = new StringBuilder();
        if (isNullOrBlank(name) || !name.contains("_")) {
            return name;
        }
        String camels[] = name.split("_");
        for (String camel :  camels) {
            if (isNullOrBlank(camel)) {
                continue;
            }
            if (result.length() == 0) {
                result.append(camel.toLowerCase());
            } else {
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }
}
