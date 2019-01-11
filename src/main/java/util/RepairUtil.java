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
 * @author Administrator
 *
 */
public class RepairUtil {

	/**
	 * 删除script节点
	 * 
	 * @param file
	 * @return 返回0：文件正常，不需要修复 <br>
	 *         返回1：文件被感染，修复成功 <br>
	 *         返回-1：文件被感染，修复失败
	 */
	public static int repairFile(File file) {
		RandomAccessFile randomAccessFile = null;
		Document document = null;
		try {
			randomAccessFile = new RandomAccessFile(file, "r");
			if (file.length() < 10) {
				randomAccessFile.close();
				return 0;
			}
			randomAccessFile.seek(file.length() - 9);
			// 不以script标签结尾，是正常文件
			if (randomAccessFile.readLine().equals("</SCRIPT>") == false) {
				randomAccessFile.close();
				return 0;
			}
			document = Jsoup.parse(file, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements scriptElements = document.getElementsByAttributeValue("Language", "VBScript");
		// 文件正常
		if (scriptElements.size() == 0) {
			return 0;
		}
		// 两个script节点，不修复
		if (scriptElements.size() >= 2) {
			return -1;
		}
		// 开始修复
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
		return 1;
	}

}
