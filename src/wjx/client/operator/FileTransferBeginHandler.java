package wjx.client.operator;

import java.io.File;
import java.util.ArrayList;

import wjx.client.data.NetFileData;
import wjx.client.socket.CmdClientSocket;
import wjx.client.socket.FileDownLoadSocketThread;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class FileTransferBeginHandler extends Handler {
	private NetFileData fileData;
	String SDpath=Environment.getExternalStorageDirectory().getAbsolutePath();
	public FileTransferBeginHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public FileTransferBeginHandler(NetFileData fileData) {
		super();
		this.fileData = fileData;
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		Bundle bundle = msg.getData();
		ArrayList<String> list = bundle
				.getStringArrayList(CmdClientSocket.KEY_SERVER_ACK_MSG);
		
		/*
		此处进行文件流操作
		 */

		super.handleMessage(msg);
	}

}
