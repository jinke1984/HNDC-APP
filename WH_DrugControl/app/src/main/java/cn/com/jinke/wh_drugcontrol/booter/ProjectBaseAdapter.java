package cn.com.jinke.wh_drugcontrol.booter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.JsonConstans;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;

/**
 * Created by jinke on 16/8/1.
 */
public abstract class ProjectBaseAdapter<T> extends BaseAdapter implements JsonConstans,CodeConstants, MsgKey {

    private List<T> allModel = new ArrayList<T>();
    protected LayoutInflater inflater;
    protected Activity mContext;


    public ProjectBaseAdapter(Activity context) {
        mContext = context;
        inflater = (LayoutInflater) ProjectApplication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ProjectBaseAdapter(Activity context, ArrayList<T> list) {
        mContext = context;
        allModel = list;
        inflater = (LayoutInflater) ProjectApplication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ProjectBaseAdapter<T> setData(List<T> list) {
        allModel = list;
        return this;
    }

    public ProjectBaseAdapter<T> addData(List<T> list) {
        allModel.addAll(list);
        return this;
    }

    public List<T> getData() {
        return allModel;
    }

    @Override
    public int getCount() {
        return allModel == null ? 0 : allModel.size();
    }

    @Override
    public T getItem(int position) {
        return allModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewEx(position, convertView, getItem(position));
    }

    abstract public View getViewEx(int position, View v, T t);
}
