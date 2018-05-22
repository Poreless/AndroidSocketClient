package wjx.client.operator;

import java.util.ArrayList;
import wjx.client.hotkey.Default_HotKey;
import wjx.client.data.HotKeyData;
import wjx.client.hotkey.Video_HotKey;
import wjx.client.data.NetFileData;
import wjx.client.hotkey.PPT_HotKey;

public class HotKeyGenerator {
	public HotKeyGenerator() {
		// TODO Auto-generated constructor stub
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
}