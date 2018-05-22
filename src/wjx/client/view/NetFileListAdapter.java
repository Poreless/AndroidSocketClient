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
			tv3.setText("");// 若是文件夹，不显示长度，后期可以考虑改进成显示该文件夹里的文件目录数
		} else {
			iv.setImageResource(image_file_id);// 后期对图标可改进，能区分图片、文档、音乐等常用扩展名，并用不同的图标显示
			tv3.setText(fileData.getFileSizeStr());
		}
		tv1.setText(fileData.getFileName());
		tv2.setText(fileData.getFileModifiedDate());
		return convertView;
	}
}
