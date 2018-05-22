package wjx.client.operator;

import java.util.ArrayList;
import wjx.client.socket.CmdClientSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.widget.Toast;

public class ShowNonUiUpdateCmdHandler extends Handler {
	private Context context;

	public ShowNonUiUpdateCmdHandler(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		Bundle bundle = msg.getData();
		ArrayList<String> list = bundle
				.getStringArrayList(CmdClientSocket.KEY_SERVER_ACK_MSG);
		/*
		 * if(msg.arg2==CmdClientSocket.SERVER_MSG_ERROR){
		 * Toast.makeText(context, list.toString(), Toast.LENGTH_SHORT).show();
		 * }else{ // 后期可考虑震动提示 // Toast.makeText(context, list.get(0),
		 * Toast.LENGTH_SHORT).show(); }
		 */
		super.handleMessage(msg);
	}
}