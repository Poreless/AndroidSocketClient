package wjx.client.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Environment;
import android.os.Looper;
import android.util.Log;

public class FileDownLoadSocketThread extends Thread {
	Socket socket;  
	InputStream in;   
	OutputStream out;   
	String path = Environment.getExternalStorageDirectory().getAbsolutePath();;   
	String functionName;   
	String serverIp = "192.168.56.1";   
	int socketPort = 8099;
	int fileSize,downLoadFileSize;  
	public static boolean isDebug=true;
	private int connect_timeout = 2000;//  设置 socket 连接的超时时间，单位： ms
	private int transfer_timeout = 10000;//  设置 socket 连接的超时时间，单位： ms
	
	public FileDownLoadSocketThread() {
		super();
	}

	public void work(final String cmd,final String filepath) {
		new Thread(new Runnable() {// Socket 的连接以及数据传输需在新开辟线程中工作，若在主 UI 线程中阻塞操作会使程序崩溃
			@Override
			public void run() {
			// TODO Auto-generated method stub
			doloadTask(cmd,filepath);
		}
		}).start();
	}
	
	private void connect() throws IOException {// 连接服务端函数
		InetSocketAddress address = new InetSocketAddress(serverIp, socketPort);
		socket = new Socket();
		socket.connect(address, connect_timeout);
		System.out.println("连接成功..");
		if(!isDebug){// 若不处于调试模式，则设置 socket 数据传输超时
			socket.setSoTimeout(transfer_timeout);// 设置传输数据的超时时间
		}
	}
	
	private void doloadTask(String cmd,String filepath) {
		try {
			connect();
			InputStream in = socket.getInputStream();      
			OutputStream out = socket.getOutputStream();      
			out.write((cmd + "\n").getBytes("gbk")); 
			out.write((filepath+ "\n").getBytes("gbk")); 
			out.flush(); // 清理缓冲，确保发送到服务端      
			File f = new File(path + cmd);
			f.createNewFile();
			Log.e("0136", path + cmd);
			OutputStream song = new FileOutputStream(f);      
			DataInputStream dis = new DataInputStream(        
					new BufferedInputStream(in));      
			DataOutputStream dos = new DataOutputStream(        
					new BufferedOutputStream(song));      
			fileSize = (int) dis.readLong() - 1; 

			System.out.println("开始下载");      
			byte[] buffer = new byte[8192];      
			while (true) {       
				int read = 0;       
				if (dis != null) {        
					read = dis.read(buffer);        
					downLoadFileSize += read;         
					}       
				if (read == -1) {     out.write((cmd + "\n").getBytes("gbk")); out.write((cmd + "\n").getBytes("gbk"));   
					break;       
				}       
				dos.write(buffer, 0, read);      
			}      
			System.out.println("文件下载完成");      
			dos.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
/*	@Override
	public void run() {    
		Looper.prepare();    
		while(!Thread.interrupted()){     
			try {      
				socket = new Socket(serverIp, socketPort);      
				InputStream in = socket.getInputStream();      
				OutputStream out = socket.getOutputStream();      
				out.write((functionName + "\n").getBytes("gbk"));      
				out.flush(); // 清理缓冲，确保发送到服务端      
				File f = new File(path + functionName);
				Log.e("0136", path + functionName);
				OutputStream song = new FileOutputStream(f);      
				DataInputStream dis = new DataInputStream(        
						new BufferedInputStream(in));      
				DataOutputStream dos = new DataOutputStream(        
						new BufferedOutputStream(song));      
				fileSize = (int) dis.readLong() - 1;      
				System.out.println("开始下载");      
				byte[] buffer = new byte[8192];      
				while (true) {       
					int read = 0;       
					if (dis != null) {        
						read = dis.read(buffer);        
						downLoadFileSize += read;         
						}       
					if (read == -1) {       
						break;       
					}       
					dos.write(buffer, 0, read);      
				}      
				System.out.println("文件下载完成");      
				dos.close();     
				} catch (UnknownHostException e) {      // TODO Auto-generated catch block     
					e.printStackTrace();     
					} catch (IOException e) {      // TODO Auto-generated catch block      
						e.printStackTrace();     
						} finally {      
							this.interrupt();     
						}    
			}   
		} */
	}


