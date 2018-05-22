package wjx.client.data;

import java.util.ArrayList;

import wjx.client.hotkey.*;

public class HotKeyData {// �ȼ��Զ����࣬Ŀǰֻ���ȼ��������Լ���Ӧ������������Ա
	private String hotkeyName = "";
	private String hotkeyCmd = "";

	public HotKeyData(String hotkeyName, String hotkeyCmd) {
		super();
		this.hotkeyName = hotkeyName;
		this.hotkeyCmd = hotkeyCmd;
	}

	public static ArrayList<HotKeyData> getHotkeyList(NetFileData fileData) {
		// ���� fileType ��չ��������������Щ�ȼ��б�
		// �˴�ֻ���� ppt ���ȼ���Ĭ�ϵ��ȼ�
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
		if (fileData.getFileTypeStr() == 0) {// �����ļ���
			String fileName = fileData.getFileName();
			int idx = fileName.lastIndexOf(".");
			if (idx > 0) {// ����չ��
				fileType = fileName.substring(idx + 1);
			}
		}
		return fileType;// �����ļ��л�������չ������ fileType=""
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