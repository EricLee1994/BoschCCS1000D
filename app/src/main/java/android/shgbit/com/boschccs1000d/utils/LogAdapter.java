package android.shgbit.com.boschccs1000d.utils;

import android.content.Context;
import android.shgbit.com.boschccs1000d.R;
import android.shgbit.com.boschccs1000d.models.LogInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 2017/1/23.
 */

public class LogAdapter extends BaseAdapter {

    private List<Map<String, Object>> mLogList;
    private Context context;
    private LayoutInflater layoutInflater;

    public LogAdapter(Context context, List<Map<String, Object>> mLogList){
        this.context = context;
        this.mLogList = mLogList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class Element{
        public TextView tvTime;
        public TextView tvLog;
    }

    @Override
    public int getCount() {
        return mLogList.size();
    }

    @Override
    public Object getItem(int i) {
        return mLogList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Element element = null;
        if (view == null){
            element = new Element();
            view = layoutInflater.inflate(R.layout.listview_item, null);
            element.tvLog = (TextView) view.findViewById(R.id.tvLog);
            element.tvTime = (TextView) view.findViewById(R.id.tvTime);
            view.setTag(element);
        }else {
            element = (Element) view.getTag();
        }

        element.tvLog.setText((String) mLogList.get(i).get("info"));
        element.tvTime.setText((String) mLogList.get(i).get("time"));
        return view;
    }
}
