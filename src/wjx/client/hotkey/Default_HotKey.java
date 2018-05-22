package wjx.client.hotkey;
import wjx.client.data.*;

import java.util.ArrayList;

public class Default_HotKey extends BaseHotKey {// 默认热键，不针对特定扩展名文件
	@Override
	ArrayList<HotKeyData> generateData() {
		// TODO Auto-generated method stub
		ArrayList<HotKeyData> hotKeyList = new ArrayList<HotKeyData>();
		hotKeyList.add(new HotKeyData(" 切换程序 ", "key:vk_alt+vk_shift+vk_tab"));
		hotKeyList.add(new HotKeyData(" 退出 ", "key:vk_alt+vk_f4"));
		hotKeyList.add(new HotKeyData(" 最大化 ", "key:vk_windows+vk_up"));
		hotKeyList.add(new HotKeyData(" 还原 / 最小化 ", "key:vk_windows+vk_down"));
		return hotKeyList;
	}
}