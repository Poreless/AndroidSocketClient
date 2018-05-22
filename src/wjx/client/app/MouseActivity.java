package wjx.client.app;


import wjx.client.operator.ShowNonUiUpdateCmdHandler;
import wjx.client.operator.ShowRemoteFileHandler;
import wjx.client.socket.CmdClientSocket;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MouseActivity extends Activity {
	Button bt2,bt3,bt4,bt5,bt6,bt7,bt8;	
	EditText et3, et4,et5,et6,et7;
	String ip = "192.168.56.1";
	Integer port=8019;
	String ValueX,ValueY,AddX,AddY,RollX;
	Boolean num=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.second_layout);
		bt8 = (Button) findViewById(R.id.button_8);
		bt7 = (Button) findViewById(R.id.button_7);
		bt6 = (Button) findViewById(R.id.button_6);
		bt5 = (Button) findViewById(R.id.button_5);
		bt4 = (Button) findViewById(R.id.button_4);
		bt3 = (Button) findViewById(R.id.button_3);
		bt2 = (Button) findViewById(R.id.button_2);
		et3 = (EditText) findViewById(R.id.editText3);
		et4 = (EditText) findViewById(R.id.editText4);
		et5 = (EditText) findViewById(R.id.editText5);
		et6 = (EditText) findViewById(R.id.editText6);
		et7 = (EditText) findViewById(R.id.editText7);
		ShowNonUiUpdateCmdHandler handler2 = new ShowNonUiUpdateCmdHandler(MouseActivity.this);
		final CmdClientSocket cmdClientSocket2 = new CmdClientSocket(ip, port,
				handler2 );
/*		ValueX = et3.getText().toString();
		ValueY = et4.getText().toString();	*/	  //程序在此处执行极快，无法获取输入内容
/*		AddX = et5.getText().toString();
		AddY = et6.getText().toString();
		RollX = et7.getText().toString();*/
		bt2.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		bt3.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ValueX = et3.getText().toString();
				ValueY = et4.getText().toString();	
				cmdClientSocket2.work("mva:"+ValueX+","+ValueY);
			}
		});
		bt4.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AddX = et5.getText().toString();
				AddY = et6.getText().toString();
				cmdClientSocket2.work("mov:"+AddX+","+AddY);
			}
		});
		bt5.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				RollX = et7.getText().toString();
				cmdClientSocket2.work("rol:"+RollX);
			}
		});
		bt6.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				cmdClientSocket2.work("clk:left");
				Toast.makeText(MouseActivity.this, "左键已点击..",
						Toast.LENGTH_LONG).show();
			}
		});
		bt7.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(num==false){
					cmdClientSocket2.work("clk:left_press");
					Toast.makeText(MouseActivity.this, "左键已按下，注意释放",
							Toast.LENGTH_SHORT).show();
				}
				else{
					cmdClientSocket2.work("clk:left_release");
					Toast.makeText(MouseActivity.this, "左键已释放..",
							Toast.LENGTH_SHORT).show();
				}
				num=(num==false)?true:false;
					
			}
		});
		bt8.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				cmdClientSocket2.work("clk:right");
				Toast.makeText(MouseActivity.this, "右键已点击..",
						Toast.LENGTH_SHORT).show();
			}
		});
	}


}
