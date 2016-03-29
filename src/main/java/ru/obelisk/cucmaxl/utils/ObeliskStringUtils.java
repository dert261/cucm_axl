package ru.obelisk.cucmaxl.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ObeliskStringUtils {
	
	public static String getTraceToLog(Exception e){
		StringWriter stack = new StringWriter();
		e.printStackTrace(new PrintWriter(stack));
		return stack.toString();
	}
}
