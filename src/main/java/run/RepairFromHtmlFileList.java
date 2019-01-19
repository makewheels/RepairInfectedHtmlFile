package run;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;

import util.RepairUtil;

/**
 * 从html文件列表中修复
 * 
 * @author spring
 * @date 2019-01-20 00:01:07
 */
public class RepairFromHtmlFileList {

	public static void main(String[] args) throws IOException {
		File htmlListFile = new File(FindAllInfectedHtml.htmlFileListPath);
		List<String> htmlFilePathList = FileUtils.readLines(htmlListFile, Charset.defaultCharset());
		for (String filepath : htmlFilePathList) {
			RepairUtil.repairFile(new File(filepath));
			System.out.println(filepath);
		}
		System.out.println(htmlFilePathList.size());
	}

}
