package wjx.client.socket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class CmdClientSocket {
	public static int SERVER_MSG_OK=0;// ���ڷ��͸��������Ϣ���� , ������Ϣ�� arg2 �У���ʾ���������
	public static int SERVER_MSG_ERROR=1;// ��ʾ����˳���
	public static String STATUS_OK="ok";// ���������ʱ�����ص���Ϣʶ���ַ���
	public static final String KEY_SERVER_ACK_MSG = "KEY_SERVER_ACK_MSG";
	public static boolean isDebug=true;
	private int port;
	private String ip;
	private int connect_timeout = 2000;//  ���� socket ���ӵĳ�ʱʱ�䣬��λ�� ms
	private int transfer_timeout = 10000;//  ���� socket ���ӵĳ�ʱʱ�䣬��λ�� ms
	private Handler handler;// ����� Message ����
	private Socket socket;
	private int msgType=SERVER_MSG_ERROR;//msgType=0, ���������ִ�У� msgType=1 �������ִ�г���
	private BufferedReader bufferedReader;
	private OutputStreamWriter writer;
	public CmdClientSocket(String ip, int port, Handler handler) {
		super();
		this.port = port;
		this.ip = ip;
		this.handler = handler;
	}
	
	private void doCmdTask(String cmd) {
		ArrayList<String> msgList=new ArrayList<String>();
		try {
			connect();// ���ӷ���ˣ������쳣������׽
			writeCmd(cmd);// �����˷������δ�ر������
			msgList = readSocketMsg();// ��ȡ socket ��������Ϣ������������� msgList �б�
				close();// �ر� Socket ���������������
		} catch (IOException e) {
		// TODO Auto-generated catch block
		msgType=SERVER_MSG_ERROR;// ����׽���쳣�������� msgType Ϊ SERVER_MSG_ERROR ��ʵ��ֵΪ 1 ��
	
		msgList.add(e.toString());// �� msgList �б��з��������Ϣ
			e.printStackTrace();
		}
		Message message = handler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putStringArrayList(KEY_SERVER_ACK_MSG, msgList);// �ش�������Ҫ�� msgList �� size �����жϣ����� 0 ��Ϊ��Ч
		message.arg2=msgType;
		// ��� bundle �� handleMessage(Message msg) ���������ȶ���Ϣ�� arg2 �����жϣ����� SERVER_MSG_ERROR ���ͣ��򲻸����б� Toast ��ʾ������Ϣ
		// �� message.arg2 �� SERVER_MSG_OK ��������б� UI
		message.setData(bundle);
		handler.sendMessage(message);//  ͨ�����֪ͨ�� UI ���ݴ�����ϣ����ش�����
	}
	
	public void work(final String cmd) {
		new Thread(new Runnable() {// Socket �������Լ����ݴ��������¿����߳��й����������� UI �߳�������������ʹ�������
			@Override
			public void run() {
			// TODO Auto-generated method stub
			doCmdTask(cmd);
		}
		}).start();
	}
	private void connect() throws IOException {// ���ӷ���˺���
		InetSocketAddress address = new InetSocketAddress(ip, port);
		socket = new Socket();
		socket.connect(address, connect_timeout);
		if(!isDebug){// �������ڵ���ģʽ�������� socket ���ݴ��䳬ʱ
			socket.setSoTimeout(transfer_timeout);// ���ô������ݵĳ�ʱʱ��
		}
	}
	private void writeCmd(String cmd) throws IOException {
	// TODO Auto-generated method stub
		BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());//socket.getOutputStream() ��������� BufferedOutputStream �����װΪ������������
		// OutputStreamWriter writer=new OutputStreamWriter(os);// Ĭ�ϵ��ַ����룬�п����� GB2312 Ҳ�п����� UTF-8 ��ȡ����ϵͳ
		// // ���鲻Ҫ��Ĭ���ַ����룬����ָ�� UTF-8 ���Ա�֤���ͽ����ַ�����һ�£������ڳ�����
		// ��������ֽڴ���ģ������߱��ַ���ֱ��д�빦�ܣ�����ٽ����װ�� OutputStreamWriter ��ʹ��֧���ַ���ֱ��д��
		writer=new OutputStreamWriter(os,"UTF-8");// ���Խ��ַ�����ĳ� "GB2312"
		writer.write("1\n");// δ����д�����������������ڴ���
		writer.write(cmd+"\n");
		writer.flush();// д������������������ݴ����ȥ
	}
	private ArrayList<String> readSocketMsg() throws IOException {
		ArrayList<String> msgList=new ArrayList<String>();
		InputStream inputStream = socket.getInputStream();
		InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
		bufferedReader=new BufferedReader(reader);
		String lineNumStr = bufferedReader.readLine();
		int lineNum=Integer.parseInt(lineNumStr);
		msgType=SERVER_MSG_ERROR;
		if(lineNum<1){
			msgList.add("Receive empty message");
			return msgList;
		}
		String status = bufferedReader.readLine();
		if(status.equalsIgnoreCase(STATUS_OK)){
			msgType=SERVER_MSG_OK;
		}else{
			msgList.add(status);// ������˵Ĵ�����Ϣ������Ϣ�б�
		}
		for(int i=1;i<lineNum;i++){// ����״̬�Ѷ����ʴ� 1 ��ʼ����
			String str = bufferedReader.readLine();
			msgList.add(str);
		}
		return msgList;
	}
	
	private void close() throws IOException {
		bufferedReader.close();// ����ٹر����������
		writer.close();
		socket.close();// ��ʵ�� socket �Ѿ������ϵ����ر�ʱ���ر�
	}
}
