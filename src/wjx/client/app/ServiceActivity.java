package wjx.client.app;

import wjx.client.operator.ShowNonUiUpdateCmdHandler;
import wjx.client.socket.CmdClientSocket;
import android.animation.TimeAnimator.TimeListener;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class ServiceActivity extends Activity {
	String ip = "192.168.56.1";
	Integer port=8019;
	Button bt10,bt11,bt12,bt13;
	TimePicker timepicker;
	EditText et8,et9;
	String cmd,str;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.third_layout);
		timepicker = (TimePicker) findViewById(R.id.timePicker1);
		timepicker.setIs24HourView(true);
		bt10 = (Button) findViewById(R.id.button_10);
		bt11 = (Button) findViewById(R.id.button_11);
		bt12 = (Button) findViewById(R.id.button_12);
		bt13 = (Button) findViewById(R.id.button_13);
		et8 = (EditText) findViewById(R.id.editText8);
		et9 = (EditText) findViewById(R.id.editText9);
		ShowNonUiUpdateCmdHandler handler3 = new ShowNonUiUpdateCmdHandler(ServiceActivity.this);
		final CmdClientSocket cmdClientSocket3 = new CmdClientSocket(ip, port,
				handler3);
		bt10.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		bt11.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				cmd = et8.getText().toString();
				cmdClientSocket3.work("cmd:"+cmd);
			}
		});
		
		bt12.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				str = et9.getText().toString();
				cmdClientSocket3.work("cps:"+str);
				//cmdClientSocket3.work("key:vk_control+vk_v");
				Toast.makeText(ServiceActivity.this, "已剪切至焦点处..",
						Toast.LENGTH_LONG).show();
			}
		});
		
		bt13.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("time", timepicker.getCurrentHour()+":"+timepicker.getCurrentMinute());
				String cmd2 ="at "+timepicker.getCurrentHour()+":"+timepicker.getCurrentMinute()+" Shutdown -s";
				String cmd3 ="shutdown -s -t 3600";
				String cmd4 ="shutdown -a";
				cmdClientSocket3.work("cmd:"+cmd2);
			}
		});
	}
}
