package run;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import util.FileUtil;

/**
 * 格式化html
 * 
 * @author Administrator
 *
 */
public class FormatHtml {

	public static void main(String[] args) {
		String folderPath = "C:\\Users\\Administrator\\Desktop\\新建文件夹";
		File[] files = new File(folderPath).listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				continue;
			}
			Document document = null;
			try {
				document = Jsoup.parse(file, "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
			FileUtil.saveToFile(file.getParent(), file.getName(), document.html());
		}

	}

}
