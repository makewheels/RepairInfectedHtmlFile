package run;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import util.RepairUtil;

/**
 * 列出所有感染了的html文件<br>
 * 把这些文件的路径，保存到一个文本文件中
 * 
 * @author spring
 * @date 2019-01-19 23:57:13
 */
public class FindAllInfectedHtml {
	// 保存html文件路径的文件
	public static String htmlFileListPath = "D:\\workSpace\\sts\\RepairInfectedHtmlFile\\src\\main\\resources\\infectedHtml.txt";

	public static void main(String[] args) throws IOException {
		// 目录
		File folder = new File("D:\\workSpace");
		// 找文件
		Collection<File> htmlFiles = FileUtils.listFiles(folder, new IOFileFilter() {
			@Override
			public boolean accept(File file) {
				// 如果是html文件并且需要修复
				boolean isHtmlFile = FilenameUtils.getExtension(file.getName()).equals("html");
				boolean needRepair = RepairUtil.isFileNeedRepair(file);
				if (isHtmlFile && needRepair) {
					return true;
				} else {
					return false;
				}
			}

			@Override
			public boolean accept(File dir, String name) {
				return false;
			}
		}, TrueFileFilter.INSTANCE);
		// 保存结果
		File htmlListFile = new File(htmlFileListPath);
		htmlListFile.delete();
		for (File file : htmlFiles) {
			if (RepairUtil.isFileNeedRepair(file)) {
				String path = file.getPath();
				FileUtils.write(htmlListFile, path + "\n", Charset.defaultCharset(), true);
				System.out.println(path);
			}
		}
		System.out.println(htmlFiles.size());
	}

}
