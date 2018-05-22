package wjx.client.operator;

import java.util.ArrayList;

import wjx.client.app.MainActivity;
import wjx.client.data.NetFileData;
import wjx.client.socket.CmdClientSocket;
import wjx.client.view.NetFileListAdapter;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ShowRemoteFileHandler extends Handler {
	// �þ��ͨ��������� UI ������ģ� context ���� listView ���󣬵��� UI ͨ�� dir ������Զ�̶˷����г�Ŀ¼�����
	// Զ�̶�ͨ�����ݻش����ͻ��˵� socket �����þ����Ϊ�ͻ��� socket �ľ��������������ͨ���ͻ��˾������Ϣ����
	// handleMessage ����������Ϣ���н���
	// ��Ϣ�����󣬰� NetFileData �������ļ��б����� listView ��������ļ��б����
	private Context context;
	private ListView listView;
	static ArrayList<NetFileData> netFileList;
	//private Handler handler;
	public static final String KEY_SERVER_CHILDPATH="KEY_SERVER_CHILDPATH";
	public ShowRemoteFileHandler(Context context, ListView listView) {
		super();
		this.context = context;
		this.listView = listView;
	}

	public static ArrayList<NetFileData> getNetFileList() {
		return netFileList;
	}

	public void setNetFileList(ArrayList<NetFileData> netFileList) {
		this.netFileList = netFileList;
	}

	@Override
	public void handleMessage(Message msg) {   //��дhandMessage��ӦsendMesseage
		// TODO Auto-generated method stub
		netFileList = new ArrayList<NetFileData>();
		Bundle bundle = msg.getData();
		final ArrayList<String> ack = bundle
				.getStringArrayList(CmdClientSocket.KEY_SERVER_ACK_MSG);
		final String filePath = ack.get(0);// �� 0 ��λ�����ļ��б��·��
		for (int i = 1; i < ack.size(); i++) {
			String fileInfo = ack.get(i);
			NetFileData netFileData = new NetFileData(fileInfo, filePath);
			netFileList.add(netFileData);
		}
		NetFileListAdapter adapter = new NetFileListAdapter(context,
				netFileList);
		
		/*listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(context, "��"+(arg2+1)+"����", Toast.LENGTH_SHORT).show();
				//�½�һ���߳����ڴ�����Ҫ����������dir����Ϣ
				//����ʵ��
				String ChildPath = new NetFileData(ack.get(arg2+1), filePath).getFileName();
				Log.e("wjx", ChildPath);
				Bundle bundle2 = new Bundle(); 
				bundle2.putString(KEY_SERVER_CHILDPATH, ChildPath);
				Message message2 = handler.obtainMessage();
				message2.setData(bundle2);
				handler.sendMessage(message2);

			}
		});*/
		listView.setAdapter(adapter);// ֱ���ھ����ʵ���ļ��б���ͼ����
		super.handleMessage(msg);
	}
}
