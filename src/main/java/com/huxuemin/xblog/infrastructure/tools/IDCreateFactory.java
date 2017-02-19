package com.huxuemin.xblog.infrastructure.tools;

public class IDCreateFactory {
	private final static int prime = 8;
	static int loopNum = 1;
	
	public static synchronized long getId(){
		long result = System.currentTimeMillis();
		loopNum = loopNum%prime;
		result = result * prime + (loopNum ++);
		return result;
	}
}
