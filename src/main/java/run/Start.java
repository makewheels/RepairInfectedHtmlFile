package run;

import java.io.File;

import util.RepairUtil;

public class Start {

	public static void main(String[] args) {
		String folder = "D:\\BaiduNetdiskDownload\\Android\\Android核心技术\\1_Android核心基础_15天精讲精练\\Android_课件及源码\\05_消息机制与异步任务\\google-gson-2.1\\gson-2.1-javadoc";
		File[] files = new File(folder).listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				continue;
			}
			int result = RepairUtil.repairFile(file);
			if (result == 1) {
				System.out.println(file.getPath());
			}
		}
	}

}
