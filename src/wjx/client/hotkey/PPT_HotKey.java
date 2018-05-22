package wjx.client.hotkey;

import java.util.ArrayList;
import wjx.client.data.*;

public class PPT_HotKey extends BaseHotKey {
	@Override
	ArrayList<HotKeyData> generateData() {
		// TODO Auto-generated method stub
		ArrayList<HotKeyData> hotKeyList = new ArrayList<HotKeyData>();
		hotKeyList.add(new HotKeyData(" 切换程序 ", "key:vk_alt+vk_shift+vk_tab"));
		hotKeyList.add(new HotKeyData("ESC", "key:VK_ESCAPE"));
		hotKeyList.add(new HotKeyData(" 下一页 ", "key:VK_PAGE_DOWN"));
		hotKeyList.add(new HotKeyData(" 上一页 ", "key:VK_PAGE_UP"));
		hotKeyList.add(new HotKeyData(" 从头放映 ", "key:VK_F5"));
		hotKeyList.add(new HotKeyData(" 当前页放映 ", "key:vk_shift+vk_f5"));
		hotKeyList.add(new HotKeyData(" 退出程序 ", "key:vk_alt+vk_f4"));
		hotKeyList.add(new HotKeyData(" 黑屏 / 正常 ", "key:vk_b"));
		hotKeyList.add(new HotKeyData(" 显示桌面 ", "key:VK_windows+vk_d"));
		return hotKeyList;
	}
}
