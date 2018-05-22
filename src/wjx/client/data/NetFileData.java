package wjx.client.data;

import java.io.File;

public class NetFileData {
	private long fileSize = 0;
	private int fileTypeStr = 0;
	private String fileName = "$error";// 文件名称，不含目录信息 , 默认值用于表示文件出错
	private String filePath = ".\\";
	private String fileSizeStr = "0";
	private boolean isDirectory = false;// true 为文件夹， false 为文件
	private String fileModifiedDate = "1970-01-01 00:00:00";


	public NetFileData(String fileInfo, String filePath) {

		String[] attrs = fileInfo.split(">");
		if (attrs.length == 4) {
			this.filePath = filePath;
			this.fileName = attrs[0];
			this.fileModifiedDate = attrs[1];
			this.fileSize = new Long(attrs[2]);
			this.isDirectory = (attrs[3].equals("1")||attrs[3].equals("2"));
			this.fileSizeStr = parseFileSize(this.fileSize);
		}
	}

	public static String parseFileSize(long fileSize) {
		String sizeStr = "";
		double KB = (double) 1024.0;
		double MB = (double) (KB * 1024.0);
		double GB = (double) (MB * 1024.0);
		double sizef = (double) (fileSize);
		if (sizef >= GB) {
			sizeStr = new String().format("%.3fGB", sizef / GB);
			return sizeStr;
		}
		if (sizef >= MB) {
			sizeStr = new String().format("%.2fMB", sizef / MB);
			return sizeStr;
		}
		if (sizef >= KB) {
			sizeStr = new String().format("%.1fKB", sizef / KB);
			return sizeStr;
		}
		sizeStr = new String().format("%dB", (int) fileSize);
		return sizeStr;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileSizeStr() {
		return fileSizeStr;
	}

	public void setFileSizeStr(String fileSizeStr) {
		this.fileSizeStr = fileSizeStr;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public String getFileModifiedDate() {
		return fileModifiedDate;
	}

	public void setFileModifiedDate(String fileModifiedDate) {
		this.fileModifiedDate = fileModifiedDate;
	}

	public int getFileTypeStr() {
		return fileTypeStr;
	}

	public void setFileTypeStr(int fileTypeStr) {
		this.fileTypeStr = fileTypeStr;
	}

}
