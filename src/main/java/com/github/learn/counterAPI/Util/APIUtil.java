package com.github.learn.counterAPI.Util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;

public class APIUtil {

	private static final Logger LOG = LoggerFactory.getLogger(APIUtil.class);

	/**
	 * Returns the map with given input text as key and total occurrence count
	 * as value.
	 * 
	 * @param searchText
	 * @return
	 */
	public static Map<String, Integer> getTextCounts(List<String> searchText) {
		Map<String, Integer> countResultMap = new HashMap<String, Integer>();
		try{
			String paragraphText = Constants.PARAGRAPH_TEXT;
			if (null != searchText) {
				for (String text : searchText) {
					countResultMap.put(text, StringUtils.countMatches(paragraphText, text));
				}
			}
		}catch(Exception exception){
			LOG.info("Error occurred in getTextCounts method\n"+exception);			
		}
		return countResultMap;
	}

	/**
	 * Returns the top occurrences of text as given input.
	 * 
	 * @param top
	 */
	public static List<String[]> getMaxOccurrence(int top) {
		List<String[]> result = new ArrayList<String[]>();
		try{
			Map<String, Integer> countMap = new HashMap<String, Integer>();
			setCounterMap(countMap);
			List<Entry<String, Integer>> list = sortCounterMap(countMap);
			generateCSVData(list,result,top);
		}catch(Exception exception){
			LOG.info("Error occurred in getMaxOccurrence method\n"+exception);
		}
		return result;
	}

	/**
	 * Generates the CSV data for required top words. 
	 * @param list
	 * @param result
	 */
	private static void generateCSVData(List<Entry<String, Integer>> list, List<String[]> result,int top) {
		try{
			for(int i=0;i<(list.size()>top?top:list.size());i++){
				Entry<String, Integer> entry = list.get(i);
				String[] str = new String[2];
				str[0] = entry.getKey();
				str[1] = String.valueOf(entry.getValue());
				result.add(str);
			}
		}catch(Exception exception){
			LOG.info("Error in generateCSVData method\n"+exception);			
		}
		
	}

	/**
	 * Sorts the input map by value in descending order.
	 * @param countMap
	 * @return
	 */
	private static List<Entry<String, Integer>> sortCounterMap(Map<String, Integer> countMap) {
		List<Entry<String, Integer>> list = null;
		try{
			Set<Entry<String, Integer>> set = countMap.entrySet();
			list = new ArrayList<Entry<String, Integer>>(set);
			Collections.sort(list, new Comparator<Entry<String, Integer>>() {
				public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
					return (o2.getValue()).compareTo(o1.getValue());
				}
			});
		}catch(Exception exception){
			LOG.info("Error in sortCounterMap method\n"+exception);
		}
		return list;
	}

	/**
	 * Reads/Splits each word in paragraph and sets counter for each word in map.
	 * @param countMap
	 */
	private static void setCounterMap(Map<String, Integer> countMap) {
		String text = Constants.PARAGRAPH_TEXT;
		try {
			String[] textArray = text.split(Constants.SPLIT_REGEX);
			for (String word : textArray) {
				if (countMap.containsKey(word)) {
					int counter = countMap.get(word) + 1;
					countMap.put(word, counter);
				} else if(!Constants.BLANK_STRING.equals(word.trim())){
					countMap.put(word, 1);
				}
			}
		} catch (Exception exception) {
			LOG.info("Error in setCounterMap method\n"+exception);
		}
	}
	
}
