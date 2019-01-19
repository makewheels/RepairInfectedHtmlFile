package util;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 修复工具类
 * 
 * @author spring
 * @date 2019-01-19 23:50:44
 */
public class RepairUtil {

	/**
	 * 文件是否需要修复
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isFileNeedRepair(File file) {
		RandomAccessFile randomAccessFile = null;
		Document document = null;
		try {
			randomAccessFile = new RandomAccessFile(file, "r");
			if (file.length() < 10) {
				randomAccessFile.close();
				return false;
			}
			randomAccessFile.seek(file.length() - 9);
			// 不以script标签结尾，不修复
			if (randomAccessFile.readLine().equals("</SCRIPT>") == false) {
				randomAccessFile.close();
				return false;
			}
			document = Jsoup.parse(file, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements scriptElements = document.getElementsByAttributeValue("Language", "VBScript");
		// 没找到病毒script标签，不修复
		if (scriptElements.size() == 0) {
			return false;
		}
		// 两个script节点，不修复
		if (scriptElements.size() >= 2) {
			return false;
		}
		return true;
	}

	/**
	 * 执行修复操作，删除尾部的script节点
	 * 
	 * @param file
	 * @return 返回0：文件正常，不需要修复 <br>
	 *         返回1：文件被感染，修复成功 <br>
	 *         返回-1：文件被感染，修复失败
	 */
	public static void repairFile(File file) {
		RandomAccessFile randomAccessFile = null;
		Document document = null;
		try {
			randomAccessFile = new RandomAccessFile(file, "r");
			document = Jsoup.parse(file, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements scriptElements = document.getElementsByAttributeValue("Language", "VBScript");
		Element scriptElement = scriptElements.get(0);
		String innerHtml = scriptElement.html();
		// script节点长度
		int nodeLength = innerHtml.getBytes().length + "<SCRIPT Language=\"VBScript\"></SCRIPT>".length();
		try {
			// 定位到script节点起始位置
			long position = file.length() - nodeLength - 10;
			randomAccessFile.seek(position);
			byte[] buff = new byte[20];
			for (int i = 0; i < buff.length; i++) {
				buff[i] = randomAccessFile.readByte();
			}
			position += new String(buff).indexOf("<SCRIPT");
			randomAccessFile.seek(0);
			// 从头读到script节点前
			List<Byte> list = new ArrayList<>();
			for (int i = 0; i < position; i++) {
				list.add(randomAccessFile.readByte());
			}
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] arr = new byte[list.size()];
			for (int i = 0; i < position; i++) {
				arr[i] = list.get(i);
			}
			fileOutputStream.write(arr);
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
