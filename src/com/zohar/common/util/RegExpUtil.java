/*
 * @(#)RegExpUtil.java	1.0 2013-5-17
 *
 */
package com.zohar.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * 
 * @author zohar
 * @version 1.0, 2013-5-17
 */
public class RegExpUtil {
	/**
	 * 根据正则表达式找出字符串中匹配的所有组的所有字符串
	 * @param regEx 正则表达式，需要含分组信息
	 * @param s 字符串
	 * @return 组的list，每个元素是该组的所有匹配的字符串的list
	 */
	public static List<List<String>> regFindAllByAllGroup(String regEx, String s){
		List<List<String>> resultList = new ArrayList<List<String>>();
		
		Pattern pat = Pattern.compile(regEx);  
		Matcher mat = pat.matcher(s);
		
		while(mat.find()){
			for (int i=0;i<mat.groupCount();i++){
				List<String> groupList = null; 
				if (resultList.size()<=i){
					groupList = new ArrayList<String>();
					resultList.add(groupList);
				} else{
					groupList = resultList.get(i);
				}
				
				groupList.add(mat.group(i+1));
			}
		}
		
		return resultList;
	}
	
	/**
	 * 根据正则表达式regEx从字符串s中取出其中第i组的全部匹配字符串
	 * @param regEx 正则表达式，需要含有分组
	 * @param s 待查字符串
	 * @param i 第i组，需要小于等于regEx的总组数
	 * @return 查找出来的匹配的全部字符串的list
	 */
	public static List<String> regFindAllByGroup(String regEx, String s, int i){
		List<List<String>> resultList = regFindAllByAllGroup(regEx, s);
		if (resultList!=null && resultList.size()>i){
			return resultList.get(i);
		} else{
			return null;
		}
	}
	
	/**
	 * 根据正则表达式regEx从字符串s中取出其中所有组的第一个匹配字符串
	 * @param regEx 正则表达式，需要含有分组
	 * @param s 待查字符串
	 * @return 所有组的第一个匹配字符串的数组
	 */
	public static List<String> regFindFirstByAllGroup(String regEx, String s){
		List<List<String>> resultList = regFindAllByAllGroup(regEx, s);
		List<String> firstStrList = new ArrayList<String>();
		for (List<String> ls: resultList){
			if (ls!=null && ls.size()>0){
				firstStrList.add(ls.get(0));
			} else{
				firstStrList.add(null);
			}
		}
		return firstStrList;
	}
	
	/**
	 * 根据正则表达式regEx从字符串s中取出其中第一组的所有匹配字符串，适用于只有一组的情况
	 * @param regEx
	 * @param s
	 * @return
	 */
	public static List<String> regFindAllByFirstGroup(String regEx, String s){
		return regFindAllByGroup(regEx, s, 0);
	}
	
	/**
	 * 根据正则表达式regEx从字符串s中取出其中第一组的第一个匹配字符串，适用于只有一组并且只有一个匹配的情况
	 * @param regEx
	 * @param s
	 * @return
	 */
	public static String regFindFirstByFirstGroup(String regEx, String s){
		List<String> list = regFindAllByFirstGroup(regEx, s);
		if (list!=null && list.size()>0){
			return list.get(0);
		} else{
			return null;
		}
	}
}
