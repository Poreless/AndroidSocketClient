package wjx.client.app;
import java.io.File;
import java.util.ArrayList;
import wjx.client.data.NetFileData;

import wjx.client.app.R;
import wjx.client.operator.HotKeyGenerator;
import wjx.client.operator.ShowNonUiUpdateCmdHandler;
import wjx.client.operator.ShowRemoteFileHandler;
import wjx.client.socket.CmdClientSocket;
import wjx.client.socket.FileDownLoadSocketThread;
import wjx.client.view.HotKeyDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView tv2;
	EditText et1, et2;
	ListView listView;
	Handler handler;
	Button bt1,bt9,bt13;
	String ParentPath="";
	String OldPath="";
	ArrayList<NetFileData> netFileList;
	ArrayList<String> pathname = new ArrayList<String>();
	String ip;
	Integer port;
	ShowNonUiUpdateCmdHandler showNonUiUpdateCmdHandler;
	CmdClientSocket cmdClientSocket;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
/*		Button bt1 = (Button) findViewById(R.id.button1);
		Button bt2 = (Button) findViewById(R.id.button2);*/
		tv2 = (TextView) findViewById(R.id.textView2);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		listView = (ListView) findViewById(R.id.listView1);
		bt1 = (Button) findViewById(R.id.button1);
		bt9 = (Button) findViewById(R.id.button9);
		bt13 = (Button) findViewById(R.id.button13);
		ShowRemoteFileHandler showRemoteFileHandler = new ShowRemoteFileHandler(
				MainActivity.this, listView);
		ip = et1.getText().toString();
		String portStr = et2.getText().toString();
		port = Integer.parseInt(portStr);
		cmdClientSocket = new CmdClientSocket(ip, port,
				showRemoteFileHandler);
		showNonUiUpdateCmdHandler=new ShowNonUiUpdateCmdHandler(MainActivity.this);
		//cmdClientSocket.work("home:\\");
		//cmdClientSocket.work("dir:...");// �г��̷�
	//	cmdClientSocket.work("dir:\\");
/*		handler = new Handler(new Handler.Callback() {   //����handler�Ļش�
			
			@Override
			public boolean handleMessage(Message msg) {
				// TODO Auto-generated method stub
				Bundle data = msg.getData();
				String pathMessage = data.getString(ShowRemoteFileHandler.KEY_SERVER_CHILDPATH);
				Log.e("MessagePath", pathMessage);
				String ip = et1.getText().toString();
				String portStr = et2.getText().toString();
				Integer port = Integer.parseInt(portStr);
				ShowRemoteFileHandler showRemoteFileHandler = new ShowRemoteFileHandler(
						MainActivity.this, listView);
				CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port,
						showRemoteFileHandler);
				cmdClientSocket.work("dir:c:\\"+pathMessage);
				return false;
			}
		});*/
		
		registerForContextMenu(listView);
		bt13.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				cmdClientSocket.work("dir:...");// �г��̷�
				
			}
		});
		listView.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
/*				NetFileData fileData = (NetFileData) arg0.getItemAtPosition(arg2);
				String pwd=fileData.getFilePath();*/
				netFileList=ShowRemoteFileHandler.getNetFileList();
				String ChildPath = netFileList.get(arg2).getFileName();
				if(ChildPath.equals("...")){
					ParentPath="";
					cmdClientSocket.work("dir:...");// �г��̷�
				}
				else{
					OldPath = ParentPath;
					ParentPath=ParentPath+"\\"+ChildPath;
					/*Toast.makeText(MainActivity.this, arg2, Toast.LENGTH_SHORT).show();*/
					if(netFileList.get(arg2).isDirectory()){
						cmdClientSocket.work("dir:"+ParentPath);					
						Log.e("2212",  ParentPath);
					}else{
						cmdClientSocket.work("open:"+ParentPath);
						ParentPath = OldPath;
					}
				}
			}
		});
		/*bt1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cmdClientSocket.work("dir:c:\\");
				ParentPath="c:";
			}
		});
		bt2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cmdClientSocket.work("dir:D:\\");
				ParentPath="D:";
			}
		});*/
		bt1.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//finish();  //������ǰactivity
				Intent intent = new Intent(MainActivity.this, MouseActivity.class);
				intent.putExtra("extra_ip", ip);
				intent.putExtra("extra_port", port);
				startActivity(intent);
			}
		});
		bt9.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//finish();  //������ǰactivity
				Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
				intent.putExtra("extra_ip", ip);
				intent.putExtra("extra_port", port);
				startActivity(intent);
			}
		});
		
	}


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.file_list_context_menu, menu);//R.menu.file_list_context_menu Ϊ�����Ĳ˵�
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		int pos=contextMenuInfo.position;
		NetFileData netFileData=(NetFileData) listView.getAdapter().getItem(pos);// ���� listView Ϊ��ʾ�ļ��б����ͼ
		switch(item.getItemId()){
		case R.id.item1://  �����ȼ��Ի���
		showHotKeyDialog(netFileData);// �ܸ��� netFileData ���;���������Ӧ���ȼ��Ի���
		break;
		case R.id.item2://  ���ؽ���
		{
			cmdClientSocket.work("dlf:"+netFileData.getFilePath()+"\\"+netFileData.getFileName());  //����������½���

			FileDownLoadSocketThread socketThread = new FileDownLoadSocketThread();
			socketThread.work(netFileData.getFileName(),netFileData.getFilePath());
			break;
		}
		default :break;
		}
		return super.onContextItemSelected(item);
	}
	public void showHotKeyDialog(NetFileData netFileData){
		CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port,showNonUiUpdateCmdHandler);
		//showNonUiUpdateCmdHandler ��������� socket �Ľ�����Ϣ����Զ�̷������ȷִ����������κ� UI ���£���Զ�̷���˳��� Toast ��ʾ������Ϣ
		new HotKeyDialog(MainActivity.this, HotKeyGenerator.getHotkeyList(netFileData), " �ȼ������� ", cmdClientSocket).show();
		//HotKeyDialog �Ĺ��캯��Ϊ�� public HotKeyDialog(Context context,ArrayList<HotKeyData> hotKeyList,String title,CmdClientSocket cmdClientSocket)
		// ���� Context context Ϊ������
	}
}