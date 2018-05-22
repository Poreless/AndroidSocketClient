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
	private int connect_timeout = 2000;//  ���� socket ���ӵĳ�ʱʱ�䣬��λ�� ms
	private int transfer_timeout = 10000;//  ���� socket ���ӵĳ�ʱʱ�䣬��λ�� ms
	
	public FileDownLoadSocketThread() {
		super();
	}

	public void work(final String cmd,final String filepath) {
		new Thread(new Runnable() {// Socket �������Լ����ݴ��������¿����߳��й����������� UI �߳�������������ʹ�������
			@Override
			public void run() {
			// TODO Auto-generated method stub
			doloadTask(cmd,filepath);
		}
		}).start();
	}
	
	private void connect() throws IOException {// ���ӷ���˺���
		InetSocketAddress address = new InetSocketAddress(serverIp, socketPort);
		socket = new Socket();
		socket.connect(address, connect_timeout);
		System.out.println("���ӳɹ�..");
		if(!isDebug){// �������ڵ���ģʽ�������� socket ���ݴ��䳬ʱ
			socket.setSoTimeout(transfer_timeout);// ���ô������ݵĳ�ʱʱ��
		}
	}
	
	private void doloadTask(String cmd,String filepath) {
		try {
			connect();
			InputStream in = socket.getInputStream();      
			OutputStream out = socket.getOutputStream();      
			out.write((cmd + "\n").getBytes("gbk")); 
			out.write((filepath+ "\n").getBytes("gbk")); 
			out.flush(); // �����壬ȷ�����͵������      
			File f = new File(path + cmd);
			f.createNewFile();
			Log.e("0136", path + cmd);
			OutputStream song = new FileOutputStream(f);      
			DataInputStream dis = new DataInputStream(        
					new BufferedInputStream(in));      
			DataOutputStream dos = new DataOutputStream(        
					new BufferedOutputStream(song));      
			fileSize = (int) dis.readLong() - 1; 

			System.out.println("��ʼ����");      
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
			System.out.println("�ļ��������");      
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
				out.flush(); // �����壬ȷ�����͵������      
				File f = new File(path + functionName);
				Log.e("0136", path + functionName);
				OutputStream song = new FileOutputStream(f);      
				DataInputStream dis = new DataInputStream(        
						new BufferedInputStream(in));      
				DataOutputStream dos = new DataOutputStream(        
						new BufferedOutputStream(song));      
				fileSize = (int) dis.readLong() - 1;      
				System.out.println("��ʼ����");      
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
				System.out.println("�ļ��������");      
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


