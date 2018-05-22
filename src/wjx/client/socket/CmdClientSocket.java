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
	public static int SERVER_MSG_OK=0;// 用于发送给句柄的消息类型 , 放在消息的 arg2 中，表示服务端正常
	public static int SERVER_MSG_ERROR=1;// 表示服务端出错
	public static String STATUS_OK="ok";// 服务端正常时，返回的消息识别字符串
	public static final String KEY_SERVER_ACK_MSG = "KEY_SERVER_ACK_MSG";
	public static boolean isDebug=true;
	private int port;
	private String ip;
	private int connect_timeout = 2000;//  设置 socket 连接的超时时间，单位： ms
	private int transfer_timeout = 10000;//  设置 socket 连接的超时时间，单位： ms
	private Handler handler;// 句柄的 Message 对象
	private Socket socket;
	private int msgType=SERVER_MSG_ERROR;//msgType=0, 服务端正常执行， msgType=1 ，服务端执行出错
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
			connect();// 连接服务端，若有异常，被捕捉
			writeCmd(cmd);// 向服务端发送命令，未关闭输出流
			msgList = readSocketMsg();// 读取 socket 输入流信息，并将结果存入 msgList 列表
				close();// 关闭 Socket 的输入流、输出流
		} catch (IOException e) {
		// TODO Auto-generated catch block
		msgType=SERVER_MSG_ERROR;// 若捕捉到异常，则设置 msgType 为 SERVER_MSG_ERROR （实际值为 1 ）
	
		msgList.add(e.toString());// 在 msgList 列表中放入错误信息
			e.printStackTrace();
		}
		Message message = handler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putStringArrayList(KEY_SERVER_ACK_MSG, msgList);// 回传数据需要对 msgList 的 size 进行判断，大于 0 才为有效
		message.arg2=msgType;
		// 句柄 bundle 在 handleMessage(Message msg) 函数中首先对消息的 arg2 进行判断，若是 SERVER_MSG_ERROR 类型，则不更新列表， Toast 显示错误信息
		// 若 message.arg2 是 SERVER_MSG_OK ，则更新列表 UI
		message.setData(bundle);
		handler.sendMessage(message);//  通过句柄通知主 UI 数据传输完毕，并回传数据
	}
	
	public void work(final String cmd) {
		new Thread(new Runnable() {// Socket 的连接以及数据传输需在新开辟线程中工作，若在主 UI 线程中阻塞操作会使程序崩溃
			@Override
			public void run() {
			// TODO Auto-generated method stub
			doCmdTask(cmd);
		}
		}).start();
	}
	private void connect() throws IOException {// 连接服务端函数
		InetSocketAddress address = new InetSocketAddress(ip, port);
		socket = new Socket();
		socket.connect(address, connect_timeout);
		if(!isDebug){// 若不处于调试模式，则设置 socket 数据传输超时
			socket.setSoTimeout(transfer_timeout);// 设置传输数据的超时时间
		}
	}
	private void writeCmd(String cmd) throws IOException {
	// TODO Auto-generated method stub
		BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());//socket.getOutputStream() 是输出流， BufferedOutputStream 则将其封装为带缓冲的输出流
		// OutputStreamWriter writer=new OutputStreamWriter(os);// 默认的字符编码，有可能是 GB2312 也有可能是 UTF-8 ，取决于系统
		// // 建议不要用默认字符编码，而是指定 UTF-8 ，以保证发送接收字符编码一致，不至于出乱码
		// 输出流是字节传输的，还不具备字符串直接写入功能，因此再将其封装入 OutputStreamWriter ，使其支持字符串直接写入
		writer=new OutputStreamWriter(os,"UTF-8");// 尝试将字符编码改成 "GB2312"
		writer.write("1\n");// 未真正写入的输出流，仅仅在内存中
		writer.write(cmd+"\n");
		writer.flush();// 写入输出流，真正将数据传输出去
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
			msgList.add(status);// 将服务端的错误信息放入消息列表
		}
		for(int i=1;i<lineNum;i++){// 反馈状态已读，故从 1 开始索引
			String str = bufferedReader.readLine();
			msgList.add(str);
		}
		return msgList;
	}
	
	private void close() throws IOException {
		bufferedReader.close();// 最后再关闭输入输出流
		writer.close();
		socket.close();// 事实上 socket 已经在以上的流关闭时被关闭
	}
}
