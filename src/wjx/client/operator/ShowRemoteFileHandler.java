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
	// 该句柄通过传入的主 UI 活动上下文（ context ）和 listView 对象，当主 UI 通过 dir 命令向远程端发出列出目录请求后
	// 远程端通过数据回传给客户端的 socket ，而该句柄作为客户端 socket 的句柄参数，进而能通过客户端句柄的消息触发
	// handleMessage 函数，对消息进行解析
	// 消息解析后，按 NetFileData 类生成文件列表，并对 listView 对象进行文件列表更新
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
	public void handleMessage(Message msg) {   //重写handMessage对应sendMesseage
		// TODO Auto-generated method stub
		netFileList = new ArrayList<NetFileData>();
		Bundle bundle = msg.getData();
		final ArrayList<String> ack = bundle
				.getStringArrayList(CmdClientSocket.KEY_SERVER_ACK_MSG);
		final String filePath = ack.get(0);// 第 0 个位置是文件列表的路径
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
				//Toast.makeText(context, "第"+(arg2+1)+"项被点击", Toast.LENGTH_SHORT).show();
				//新建一个线程用于传输需要主进程重新dir的消息
				//代码实现
				String ChildPath = new NetFileData(ack.get(arg2+1), filePath).getFileName();
				Log.e("wjx", ChildPath);
				Bundle bundle2 = new Bundle(); 
				bundle2.putString(KEY_SERVER_CHILDPATH, ChildPath);
				Message message2 = handler.obtainMessage();
				message2.setData(bundle2);
				handler.sendMessage(message2);

			}
		});*/
		listView.setAdapter(adapter);// 直接在句柄里实现文件列表视图更新
		super.handleMessage(msg);
	}
}
