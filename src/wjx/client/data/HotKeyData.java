package wjx.client.data;

import java.util.ArrayList;

import wjx.client.hotkey.*;

public class HotKeyData {// 热键自定义类，目前只有热键的名称以及对应的命令两个成员
	private String hotkeyName = "";
	private String hotkeyCmd = "";

	public HotKeyData(String hotkeyName, String hotkeyCmd) {
		super();
		this.hotkeyName = hotkeyName;
		this.hotkeyCmd = hotkeyCmd;
	}

	public static ArrayList<HotKeyData> getHotkeyList(NetFileData fileData) {
		// 根据 fileType 扩展名，决定产生哪些热键列表
		// 此处只给了 ppt 的热键和默认的热键
		String fileType = getFileType(fileData);
		if (fileType.equalsIgnoreCase("ppt")
				| fileType.equalsIgnoreCase("pptx")
				| fileType.equalsIgnoreCase("pps")) {
			return new PPT_HotKey().getHotkeyList();
		}
		if (checkIsMovie(fileType)) {
			return new Video_HotKey().getHotkeyList();
		}
		ArrayList<HotKeyData> list = new Default_HotKey().getHotkeyList();
		return list;
	}
	
	public static String getFileType(NetFileData fileData) {
		String fileType = "";
		if (fileData.getFileTypeStr() == 0) {// 不是文件夹
			String fileName = fileData.getFileName();
			int idx = fileName.lastIndexOf(".");
			if (idx > 0) {// 有扩展名
				fileType = fileName.substring(idx + 1);
			}
		}
		return fileType;// 若是文件夹或者无扩展名，则 fileType=""
	}

	
	private static boolean checkIsMovie(String fileType) {
		if (fileType.equalsIgnoreCase("avi")
				| fileType.equalsIgnoreCase("mpeg")
				| fileType.equalsIgnoreCase("mov")
				| fileType.equalsIgnoreCase("mp4")) {
			return true;
		}
		if (fileType.equalsIgnoreCase("flv")
				| fileType.equalsIgnoreCase("flac")
				| fileType.equalsIgnoreCase("mkv")
				| fileType.equalsIgnoreCase("mp2")) {
			return true;
		}
		if (fileType.equalsIgnoreCase("mpg") | fileType.equalsIgnoreCase("wmv")
				| fileType.equalsIgnoreCase("rmvb")
				| fileType.equalsIgnoreCase("rmb")) {
			return true;
		}
		return false;
	}
	public String getHotkeyName() {
		return hotkeyName;
	}

	public void setHotkeyName(String hotkeyName) {
		this.hotkeyName = hotkeyName;
	}

	public String getHotkeyCmd() {
		return hotkeyCmd;
	}

	public void setHotkeyCmd(String hotkeyCmd) {
		this.hotkeyCmd = hotkeyCmd;
	}
}