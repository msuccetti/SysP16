package common;
/*
 * Copyright 2011 - 2013 NTB University of Applied Sciences in Technology
 * Buchs, Switzerland, http://www.ntb.ch/inf
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 *   
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */


import java.io.PrintStream;

public class Logger {

	public static final int NONE = -1;
	public static final int ERROR = 0;
	public static final int WARNING = 1;
	public static final int NOTE = 2;
	public static final int STATE_INFO = 3;
	public static final int STATE_INFO_DETAILED = 4;
	private static final String mLevelIdentifier[] = { "Error  | ", "Warning| ", "Note   | ", "StInfo | ","StInfDe| " };
	
	private static int mLogLevel = STATE_INFO; // The actual log level
	private static PrintStream mStream=null;

	private Logger() {}// private constructor to prevent instantiation


	public static void setLogLevel(int aLogLevel) {
		mLogLevel = aLogLevel;
	}

	public static int getLogLevel() {
		return mLogLevel;
	}

	public static void setPrintStream(PrintStream aStream){
		mStream = aStream;
	}
	
	public static void newLine(int aLogLevel){
		if (aLogLevel>mLogLevel){
			return;
		}
		mStream.println("");
	}
	public static void log(int aLogLevel, String... aMessages) {
		// do nothing if no target stream is defined
		if(mStream==null){
			return;
		}
		
		// do nothing if log level to low
		if(aLogLevel > mLogLevel){
			return;
		}
		
		mStream.print(mLevelIdentifier[aLogLevel]);
		for(int i=0;i<aMessages.length;i++){
			mStream.print(aMessages[i]);
		}
		mStream.println("");
	}
}
