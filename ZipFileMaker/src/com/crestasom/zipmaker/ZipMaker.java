package com.crestasom.zipmaker;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipMaker {
	/*
	 * public static void main(String[] args) { ZipMaker pzip = new ZipMaker(); try
	 * { pzip.compressWithPassword("c:\\temp"); } catch (ZipException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * 
	 * try { pzip.unCompressPasswordProtectedFiles("G:\\files.zip"); } catch
	 * (ZipException e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * 
	 * }
	 */

	/**
	 * Method for creating password protected zip file
	 * 
	 * @param sourcePath
	 * @throws ZipException
	 * @throws IOException
	 */
	public static void compressWithPassword(String sourcePath) throws ZipException, IOException {
		String passFile = sourcePath + "/password.csv";
		File password = new File(passFile);
		System.out.println("sourcePath:" + sourcePath);
		File root = new File(sourcePath);
		String[] dirs = root.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				System.out.println(name);
				return new File(dir, name).isDirectory();
			}
		});
		FileWriter writer = new FileWriter(password);
		for (String dir : dirs) {
			writer.write(dir + "," + dir + "\n");
			String srcPath = sourcePath + "\\" + dir;
			System.out.println("srcPath:" + srcPath);
			String destPath = srcPath + ".zip";
			System.out.println("Destination " + destPath);
			ZipFile zipFile = new ZipFile(destPath);
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
					zipFile.addFile(a, zipParameters);
				} catch (ZipException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

		}
		writer.close();
	}

	/**
	 * Method for unzipping password protected file
	 * 
	 * @param sourcePath
	 * @throws ZipException
	 */
	private void unCompressPasswordProtectedFiles(String sourcePath) throws ZipException {
		String destPath = getFileName(sourcePath);
		System.out.println("Destination " + destPath);
		ZipFile zipFile = new ZipFile(sourcePath);
		// If it is encrypted then provide password
		if (zipFile.isEncrypted()) {
			zipFile.setPassword("pass@123");
		}
		zipFile.extractAll(destPath);
	}

	private String getFileName(String filePath) {
		// Get the folder name from the zipped file by removing .zip extension
		return filePath.substring(0, filePath.lastIndexOf("."));
	}
}
