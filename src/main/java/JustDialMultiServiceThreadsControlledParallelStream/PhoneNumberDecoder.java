package main.java.JustDialMultiServiceThreadsControlledParallelStream;

import java.util.HashMap;
import java.util.Map;

public class PhoneNumberDecoder {

	private static Map<String, String> meta = new HashMap<String, String>();

	static {
		meta.put("mobilesv icon-dc", "+");
		meta.put("mobilesv icon-ba", "-");
		meta.put("mobilesv icon-fe", "(");
		meta.put("mobilesv icon-hg", ")");
		meta.put("mobilesv icon-acb", "0");
		meta.put("mobilesv icon-yz", "1");
		meta.put("mobilesv icon-wx", "2");
		meta.put("mobilesv icon-vu", "3");
		meta.put("mobilesv icon-ts", "4");
		meta.put("mobilesv icon-rq", "5");
		meta.put("mobilesv icon-po", "6");
		meta.put("mobilesv icon-nm", "7");
		meta.put("mobilesv icon-lk", "8");
		meta.put("mobilesv icon-ji", "9");
	}

	public static String identify(String input) {
		return meta.get(input);
	}

}
