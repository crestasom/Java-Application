package com.crestasom.zipmaker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipMaker {
	/**
	 * Method for creating password protected zip file
	 * 
	 * @param sourcePath
	 * @param destDir
	 * @throws ZipException
	 * @throws IOException
	 */
	public static void compressWithPassword(String sourcePath, String destPath) throws ZipException, IOException {
		String passFile = "/password.csv";
		File password = new File(destPath + "//" + passFile);
		File root = new File(sourcePath);
		// find all the directories inside the source folder
		String[] dirs = root.list((dir, name) -> {
			return new File(dir, name).isDirectory();
		});
		FileWriter writer = new FileWriter(password);
		for (String dir : dirs) {
			writer.write(dir + "," + dir + "\n");
			String srcPath = sourcePath + "\\" + dir;
			String destDir = destPath + "\\" + dir + ".zip";
			ZipFile zipFile = new ZipFile(destDir);
			// Setting parameters
			ZipParameters zipParameters = new ZipParameters();
			zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
			zipParameters.setEncryptFiles(true);
			zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			zipParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			// Setting password
			zipParameters.setPassword(dir);
			Arrays.asList(new File(srcPath).listFiles()).forEach(a -> {
				try {
					if (a.isDirectory()) {
						zipFile.addFolder(a, zipParameters);
					} else {
						zipFile.addFile(a, zipParameters);
					}
				} catch (ZipException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

		}
		writer.close();
	}

}
