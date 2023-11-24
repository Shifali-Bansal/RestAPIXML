package com.newgen.srvr.xml;

import java.util.Arrays;
import java.util.Locale;

import com.newgen.iforms.common.ExtLog;
import com.newgen.iforms.user.WFLogger;

public class XMLParser {
	private String parseString;
	private String copyString;
	private int IndexOfPrevSrch;
	static ExtLog extlog = new ExtLog();

	public XMLParser() {
	}

	public XMLParser(String parseThisString) {
		copyString = parseThisString;
		parseString = toUpperCase(copyString, 0);
	}

	public void setInputXML(String ParseThisString) {
		if (ParseThisString != null) {
			copyString = ParseThisString;
			parseString = toUpperCase(copyString, 0);
			IndexOfPrevSrch = 0;
		} else {
			parseString = null;
			copyString = null;
			IndexOfPrevSrch = 0;
		}
	}

	public String getValueOf(String valueOf) {
		if (valueOf != " ") {
			try {
				return copyString.substring(
						parseString.indexOf("<" + toUpperCase(valueOf, 0) + ">") + valueOf.length() + 2,
						parseString.indexOf("</" + toUpperCase(valueOf, 0) + ">"));
			} catch (StringIndexOutOfBoundsException stringindexoutofboundsexception) {
				extlog.errorLog(null, Arrays.toString(stringindexoutofboundsexception.getStackTrace()));
			}
		}
		return "";

	}

	public String getValueOf(String valueOf, String type) {
		try {
			if (type.equalsIgnoreCase("Binary")) {
				int startPos = copyString.indexOf("<" + valueOf + ">");
				if (startPos == -1) {
					return "";
				} else {
					int endPos = copyString.lastIndexOf("</" + valueOf + ">");
					startPos += ("<" + valueOf + ">").length();
					return copyString.substring(startPos, endPos);
				}
			} else {
				return "";
			}
		} catch (StringIndexOutOfBoundsException stringindexoutofboundsexception) {
			extlog.errorLog(null, Arrays.toString(stringindexoutofboundsexception.getStackTrace()));
			return "";
		}
	}

	public String getValueOf(String valueOf, boolean fromlast) {
		try {
			if (fromlast) {
				return copyString.substring(
						parseString.indexOf("<" + toUpperCase(valueOf, 0) + ">") + valueOf.length() + 2,
						parseString.lastIndexOf("</" + toUpperCase(valueOf, 0) + ">"));
			} else {
				return copyString.substring(
						parseString.indexOf("<" + toUpperCase(valueOf, 0) + ">") + valueOf.length() + 2,
						parseString.indexOf("</" + toUpperCase(valueOf, 0) + ">"));
			}
		} catch (StringIndexOutOfBoundsException stringindexoutofboundsexception) {
			extlog.errorLog(null, Arrays.toString(stringindexoutofboundsexception.getStackTrace()));
			return "";
		}
	}

	public String getValueOf(String valueOf, int start, int end) {
		try {
			if (start >= 0) {
				int endIndex = parseString.indexOf("</" + toUpperCase(valueOf, 0) + ">", start);
				if (endIndex > start && (end == 0 || end >= endIndex)) {
					return copyString.substring(
							parseString.indexOf("<" + toUpperCase(valueOf, 0) + ">", start) + valueOf.length() + 2,
							endIndex);
				}
			}
			return "";
		} catch (StringIndexOutOfBoundsException stringindexoutofboundsexception) {
			extlog.errorLog(null, Arrays.toString(stringindexoutofboundsexception.getStackTrace()));
			return "";
		}
	}

	public String getFirstValueOf(String valueOf) {
		try {
			IndexOfPrevSrch = parseString.indexOf("<" + toUpperCase(valueOf, 0) + ">");
			return copyString.substring(IndexOfPrevSrch + valueOf.length() + 2,
					parseString.indexOf("</" + toUpperCase(valueOf, 0) + ">"));
		} catch (StringIndexOutOfBoundsException stringindexoutofboundsexception) {
			extlog.errorLog(null, Arrays.toString(stringindexoutofboundsexception.getStackTrace()));
			return "";
		}
	}

	public String getFirstValueOf(String valueOf, int start) {
		try {
			IndexOfPrevSrch = parseString.indexOf("<" + toUpperCase(valueOf, 0) + ">", start);
			return copyString.substring(IndexOfPrevSrch + valueOf.length() + 2,
					parseString.indexOf("</" + toUpperCase(valueOf, 0) + ">", start));
		} catch (StringIndexOutOfBoundsException stringindexoutofboundsexception) {
			extlog.errorLog(null, Arrays.toString(stringindexoutofboundsexception.getStackTrace()));
			return "";
		}
	}

	public String getNextValueOf(String valueOf) {
		try {
			IndexOfPrevSrch = parseString.indexOf("<" + toUpperCase(valueOf, 0) + ">",
					IndexOfPrevSrch + valueOf.length() + 2);
			return copyString.substring(IndexOfPrevSrch + valueOf.length() + 2,
					parseString.indexOf("</" + toUpperCase(valueOf, 0) + ">", IndexOfPrevSrch));
		} catch (StringIndexOutOfBoundsException stringindexoutofboundsexception) {
			extlog.errorLog(null, Arrays.toString(stringindexoutofboundsexception.getStackTrace()));
			return "";
		}
	}

	public int getNoOfFields(String tag) {
		int noOfFields = 0;
		int beginPos = 0;
		try {
			for (tag = toUpperCase(tag, 0) + ">"; parseString.indexOf("<" + tag,
					beginPos) != -1; beginPos += tag.length() + 2) {
				noOfFields++;
				beginPos = parseString.indexOf("</" + tag, beginPos);
				if (beginPos == -1) {
					break;
				}
			}

		} catch (StringIndexOutOfBoundsException stringindexoutofboundsexception) {
			extlog.errorLog(null, Arrays.toString(stringindexoutofboundsexception.getStackTrace()));
		}
		return noOfFields;
	}

	public int getNoOfFields(String tag, int startPos, int endPos) {
		int noOfFields = 0;
		int beginPos = startPos;
		try {
			for (tag = toUpperCase(tag, 0) + ">"; parseString.indexOf("<" + tag, beginPos) != -1
					&& (beginPos < endPos || endPos == 0);) {
				beginPos = parseString.indexOf("</" + tag, beginPos) + tag.length() + 2;
				if (beginPos != -1 && (beginPos <= endPos || endPos == 0)) {
					noOfFields++;
				}
			}

		} catch (StringIndexOutOfBoundsException stringindexoutofboundsexception) {
			extlog.errorLog(null, Arrays.toString(stringindexoutofboundsexception.getStackTrace()));
		}
		return noOfFields;
	}

	public String getValueOf(String valueOf, String type, int from, int end) {
		try {
			if (type.equalsIgnoreCase("Binary")) {
				int startPos = copyString.indexOf("<" + valueOf + ">", from);
				if (startPos == -1) {
					return "";
				}
				int endPos = copyString.indexOf("</" + valueOf + ">", from);
				if (endPos > end) {
					return "";
				} else {
					startPos += ("<" + valueOf + ">").length();
					return copyString.substring(startPos, endPos);
				}
			} else {
				return "";
			}
		} catch (StringIndexOutOfBoundsException stringindexoutofboundsexception) {
			extlog.errorLog(null, Arrays.toString(stringindexoutofboundsexception.getStackTrace()));
			return "";
		}
	}

	public String toUpperCase(String valueOf, int end) throws StringIndexOutOfBoundsException {
		String returnStr = "";
		try {
			WFLogger.writeXML("End: " + end);
			int count = valueOf.length();
			char strChar[] = new char[count];
			valueOf.getChars(0, count, strChar, 0);
			while (count-- > 0) {
				strChar[count] = Character.toUpperCase(strChar[count]);
			}
			returnStr = new String(strChar);
		} catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
			extlog.errorLog(null, Arrays.toString(arrayindexoutofboundsexception.getStackTrace()));
		}
		return returnStr;
	}

	public String toString() {
		return copyString;
	}

	public NGXmlList createList(String paramString1, String paramString2) {
		paramString1 = paramString1.toUpperCase(Locale.ENGLISH);
		int i = this.parseString.indexOf("<" + paramString1 + ">");
		int j = 0;
		if (i == -1) {
			i = 0;
			/* 956 */ j = 0;
			/*     */ }
		/*     */ else
		/*     */ {
			/* 960 */ i += 2 + paramString1.length();
			/* 961 */ j = this.parseString.indexOf("</" + paramString1 + ">", i);
			/*     */ }
		/* 963 */ return new NGXmlList(this.copyString, paramString2, i, j);
		/*     */ }

}
