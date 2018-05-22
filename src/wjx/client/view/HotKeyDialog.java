package wjx.client.view;

import java.util.ArrayList;

import wjx.client.app.R;
import wjx.client.data.HotKeyData;
import wjx.client.socket.CmdClientSocket;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

public class HotKeyDialog {
	private Context context;
	private ArrayList<HotKeyData> hotKeyList;// �ȼ��б����� HotKeyGridAdapter �������
	private String title;// �Ի���ı���
	private CmdClientSocket cmdClientSocket;// ���� HotKeyGridAdapter ����ͼ�������
											// cmdClientSocket ��Զ�̶˷�������

	public HotKeyDialog(Context context, ArrayList<HotKeyData> hotKeyList,
			String title, CmdClientSocket cmdClientSocket) {
		super();
		this.context = context;
		this.hotKeyList = hotKeyList;
		this.title = title;
		this.cmdClientSocket = cmdClientSocket;
	}

	public void show() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		View view = LayoutInflater.from(context).inflate(
				R.layout.hotkey_grid_view, null, false);
		GridView gridView = (GridView) view.findViewById(R.id.gridView1);
		HotKeyGridAdapter adapter = new HotKeyGridAdapter(context, hotKeyList,
				cmdClientSocket);
		gridView.setAdapter(adapter);
		builder.setView(view);
		builder.setNegativeButton("Back", null).show();
	}

}
