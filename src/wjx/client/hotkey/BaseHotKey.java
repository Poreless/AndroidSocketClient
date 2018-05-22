package wjx.client.hotkey;

import java.util.ArrayList;
import wjx.client.data.*;

public abstract class BaseHotKey {// 作为产生热键的父类，提供一个公开的方法返回热键数据
// 抽象方法 generateData() 需要子类具体去实现
	abstract ArrayList<HotKeyData> generateData();

	public BaseHotKey() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<HotKeyData> getHotkeyList() {
		return generateData();
	}
}