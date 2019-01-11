package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * 文件工具类
 * 
 * @author Administrator
 *
 */
public class FileUtil {

	/**
	 * 保存字符串到文件
	 * 
	 * @param folder
	 * @param filename
	 * @param content
	 */
	public static void saveToFile(String folder, String filename, String content) {
		FileOutputStream fop = null;
		File file;
		try {
			file = new File(folder + File.separator + filename);
			fop = new FileOutputStream(file);
			if (!file.exists()) {
				file.createNewFile();
			}
			byte[] contentInBytes = content.getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fop.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读文本文件
	 * 
	 * @param filepath
	 * @return
	 */
	public static String readTextFile(String filepath) {
		StringBuilder stringBuilder = new StringBuilder();
		String line = "";
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(filepath));
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			line = bufferedReader.readLine();
			while (line != null) {
				stringBuilder.append(line + "\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	/**
	 * 文件大小
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileSize(File file) {
		long length = file.length();
		final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
		int digitGroups = (int) (Math.log10(length) / Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(length / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

}
