package run;

import java.io.File;

import util.RepairUtil;

public class Start {

	public static void main(String[] args) {
		String folderPath = "D:\\workSpace\\sts\\BusConfig\\resources\\timetable\\official";
		File[] files = new File(folderPath).listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				continue;
			}
			if (RepairUtil.isFileNeedRepair(file)) {
				RepairUtil.repairFile(file);
				System.out.println(file.getPath());
			}
		}
	}

}
