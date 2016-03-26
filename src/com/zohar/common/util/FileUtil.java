package com.zohar.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FileUtil {
	public static BufferedReader br = null;
	public static InputStreamReader reader = null;
	public static BufferedWriter writer = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static BufferedReader readFile(String filePath){
		try {
			File file = new File(filePath);
	        if (file.isFile() && file.exists()){
				reader = new InputStreamReader(new FileInputStream(file));
	            br = new BufferedReader(reader);
	            return br;
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedWriter writeFile(String filePath){
		File file = new File(filePath);
        try {
            writer = new BufferedWriter(new FileWriter(file));
            return writer;
        } catch(Exception e){
        	e.printStackTrace();
        }
        return null;
	}
	
	public static void closeFile(){
		try {
			if (br!=null){
				br.close();
			}
			if (reader!=null){
				reader.close();
			}
			if (writer!=null){
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<File> dirFiles(String dirPath, final String ext){
		File dir = new File(dirPath);
		List<File> fileList = null;
		
		if (dir.isDirectory() && dir.exists()){
			File[] files = dir.listFiles(new FileFilter(){
				@Override
				public boolean accept(File pathname) {
					String filename = pathname.getName();
					int index = filename.lastIndexOf(".");
					if (index>-1){
						String fileExt = filename.substring(index);
						if (fileExt.equalsIgnoreCase("."+ext)){
							return true;
						}
					}
					return false;
				}
			});
			
			fileList = Arrays.asList(files);
		}
		
		return fileList;
	}
	
	/**
	 * backup a file with timestamp. format like [[yyyy-MM-dd]] in the fileBak argument
	 */
	public static void backUpFile(String filePath, String fileBak) {
		File source = new File(filePath);
		String fileBakName = fileBak;
		if (fileBak.indexOf("[[")>-1){
			String format = RegExpUtil.regFindFirstByFirstGroup("\\[\\[([^\\]]+)\\]\\]", fileBak);
			SimpleDateFormat df = new SimpleDateFormat(format);
			String stamp = df.format(new Date());
			fileBakName = fileBakName.replaceFirst("[["+format+"]]", "["+stamp+"]");
		}
		File target = new File(fileBakName);
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

}
