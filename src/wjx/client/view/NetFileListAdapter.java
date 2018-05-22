package wjx.client.view;

import java.util.ArrayList;

import wjx.client.app.R;

import wjx.client.data.NetFileData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NetFileListAdapter extends ArrayAdapter<NetFileData> {
	private ArrayList<NetFileData> netFileList;
	private Context context;

	public NetFileListAdapter(Context context,
			ArrayList<NetFileData> netFileList) {
		super(context, android.R.layout.simple_list_item_1, netFileList);
		this.netFileList = netFileList;
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		int image_folder_id = R.drawable.folder;
		int image_file_id = R.drawable.file;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.file_row_view, null, false);
		}
		ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
		TextView tv1 = (TextView) convertView.findViewById(R.id.textView1);
		TextView tv2 = (TextView) convertView.findViewById(R.id.textView2);
		TextView tv3 = (TextView) convertView.findViewById(R.id.textView3);
		NetFileData fileData = netFileList.get(position);
		if (fileData.isDirectory()) {
			iv.setImageResource(image_folder_id);
			tv3.setText("");// �����ļ��У�����ʾ���ȣ����ڿ��Կ��ǸĽ�����ʾ���ļ�������ļ�Ŀ¼��
		} else {
			iv.setImageResource(image_file_id);// ���ڶ�ͼ��ɸĽ���������ͼƬ���ĵ������ֵȳ�����չ�������ò�ͬ��ͼ����ʾ
			tv3.setText(fileData.getFileSizeStr());
		}
		tv1.setText(fileData.getFileName());
		tv2.setText(fileData.getFileModifiedDate());
		return convertView;
	}
}
