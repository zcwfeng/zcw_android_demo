package top.zcwfeng.jetpack.mvp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import top.zcwfeng.jetpack.R;
import top.zcwfeng.jetpack.mvp.db.Student;


public class GoodsAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	private List<Student> goods;

	public GoodsAdapter(Context context, List<Student> girs) {
		inflater = LayoutInflater.from(context);
		this.goods = girs;
	}

	@Override
	public int getCount() {
		return goods.size();
	}

	@Override
	public Object getItem(int position) {
		return goods.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = inflater.inflate(R.layout.item, null);
		Student  g = (Student) goods.get(position);
//		ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
//		iv_icon.setImageResource(g.icon);


		TextView tv_like = (TextView) view.findViewById(R.id.tv_like);
		tv_like.setText("number:"+g.getUid());
		
		TextView tv_style = (TextView) view.findViewById(R.id.tv_style);
		tv_style.setText(g.getName());
		
		return view;
	}

}