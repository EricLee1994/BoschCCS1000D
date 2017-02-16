package android.shgbit.com.boschccs1000d.utils;

import android.text.format.Time;
import android.util.Log;

import com.wa.util.WAFile;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DSLog {

	private static String TAG = "DSLog";

	public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
	public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
	public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
	public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

	public static String filepath = "/sdcard/BoschCCS/Log";
	public static boolean needOutput = true;

	private static ArrayList<String> list = new ArrayList<String>();

	public static final int VERBOSE = 2;
	public static final int DEBUG = 3;
	public static final int INFO = 4;
	public static final int WARN = 5;
	public static final int ERROR = 6;

	public static int LogMaxLen = 100;
	public static int FileMaxSize = 5;
	public static int FileMaxNum = 20;

	public static void v(String tag, String msg) {
		v(tag, msg, true);
	}

	public static void v(String tag, String msg, boolean needLog) {
		if (needLog == true) {
			Log.v(tag, msg);
		}
		WriteFile(VERBOSE, tag, msg);
	}

	public static void d(String tag, String msg) {
		d(tag, msg, true);
	}

	public static void d(String tag, String msg, boolean needLog) {
		if (needLog == true) {
			Log.d(tag, msg);
		}
		WriteFile(DEBUG, tag, msg);
	}

	public static void i(String tag, String msg) {
		i(tag, msg, true);
	}

	public static void i(String tag, String msg, boolean needLog) {
		if (needLog == true) {
			Log.i(tag, msg);
		}
		WriteFile(INFO, tag, msg);
	}

	public static void e(String tag, String msg) {
		e(tag, msg, true);
	}

	public static void e(String tag, String msg, boolean needLog) {
		if (needLog == true) {
			Log.e(tag, msg);
		}
		WriteFile(ERROR, tag, msg);
	}

	public static void w(String tag, String msg) {
		w(tag, msg, true);
	}

	public static void w(String tag, String msg, boolean needLog) {
		if (needLog == true) {
			Log.w(tag, msg);
		}
		WriteFile(WARN, tag, msg);
	}

	private static void WriteFile(int level, String tag, String msg) {
		if (filepath == null) {
			return;
		}

		String strLevel = "";

		switch (level) {
		case VERBOSE:
			strLevel = "[VERBOSE]";
			break;
		case DEBUG:
			strLevel = "[DEBUG]  ";
			break;
		case INFO:
			strLevel = "[INFO]   ";
			break;
		case WARN:
			strLevel = "[WARN]   ";
			break;
		case ERROR:
			strLevel = "[ERROR]  ";
			break;
		default:
			strLevel = "[UNKNOWN]";
			break;
		}

		Time now = new Time();
		now.setToNow();

		String log = now.format3339(false) + " " + strLevel + " " + tag + " " + msg;

		try {
			Log.e(TAG, "1");
			if (needOutput == true && level >= INFO) {
				setFileNum();
				String filename = filepath + "/log-" + now.format("%Y%m%d") + ".txt";
				double filesize = getFileSize(filename);
				if (filesize < FileMaxSize) {
//					Log.e(TAG, tag + ", filesize = " + filesize + "MB <= " + FileMaxSize + "MB");
					WAFile.write(filename, true, log + "\r\n");
				} else {
					Log.e(TAG, tag + ", filesize = " + filesize + "MB > " + FileMaxSize + "MB");
					WAFile.write(filename, false, log + "\r\n");
				}

			}

			list.add(log);
			if (LogMaxLen > 0) {
				while (list.size() > LogMaxLen) {
					list.remove(0);
				}
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public static String[] ReadFile(int lines) {

		if (list == null) {
			return null;
		}

		int len = list.size() > lines ? lines : list.size();

		String[] log = new String[len];

		for (int i = 0; i < len; i++) {

			log[i] = "null";

			try {
				log[i] = list.get(list.size() - i - 1);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		return log;
	}

	private static double getFileSize(String filename) {
		File file = new File(filename);
		long size = 0;
		double filesize = 0;
		try {
			if (file.exists()) {
				size = new FileInputStream(file).available();
			}
		} catch (Throwable e) {

		}

		if (size > 0) {
			filesize = FormetFileSize(size, SIZETYPE_MB);
		}
		return filesize;
	}

	private static double FormetFileSize(long fileS, int sizeType) {
		DecimalFormat df = new DecimalFormat("#.00");
		double fileSizeLong = 0;
		switch (sizeType) {
		case SIZETYPE_B:
			fileSizeLong = Double.valueOf(df.format((double) fileS));
			break;
		case SIZETYPE_KB:
			fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
			break;
		case SIZETYPE_MB:
			fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
			break;
		case SIZETYPE_GB:
			fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
			break;
		default:
			break;
		}
		return fileSizeLong;
	}

	private static void setFileNum() {
		if (filepath == null) {
			return;
		}
		
		File file = new File(filepath);
		if (file.exists()) {
			File flist[] = file.listFiles();
			if (flist.length > FileMaxNum) {
				//删除一个，保证最多20个日志文件
				File delFile = flist[0];
				deleteFile(delFile);
			}
		}
	}

	public static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
				return;
			}

			if (file.isDirectory()) {
				File[] childFiles = file.listFiles();
				if (childFiles == null || childFiles.length == 0) {
					file.delete();
					return;
				}

				for (int i = 0; i < childFiles.length; i++) {
					deleteFile(childFiles[i]);
				}
				file.delete();
			}
			DSLog.i(TAG, "deleteFile successful！");
		} else {
			DSLog.i(TAG, "file is not exist！");
		}

	}
}