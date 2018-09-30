package cn.com.jinke.wh_drugcontrol.booter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;

/**
 * @author zhaojian
 * @time 下午5:26:08
 * @date 2014-10-28
 * @class_name QDWExpandAdapter.java
 */
public abstract class ProjectExpandAdapter<P, C> extends BaseExpandableListAdapter {

    protected ArrayList<P> parentData = new ArrayList<>();
    protected ArrayList<ArrayList<C>> childData = new ArrayList<>();
    protected LayoutInflater inflater;

    public ProjectExpandAdapter() {
        inflater = (LayoutInflater) ProjectApplication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setParent(ArrayList<P> parent) {
        this.parentData = parent;
    }

    public void setChild(ArrayList<ArrayList<C>> child) {
        this.childData = child;
    }

    @Override
    public int getGroupCount() {
        if (parentData != null) return parentData.size();
        else return 0;
    }

    public boolean hasChild(int position) {
        return getChildrenCount(position) > 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (childData != null && childData.get(groupPosition) != null)
            return childData.get(groupPosition).size();
        else return 0;
    }

    @Override
    public P getGroup(int groupPosition) {
        return parentData.get(groupPosition);
    }

    @Override
    public C getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return getParentView(groupPosition, isExpanded, convertView, getGroup(groupPosition));
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return getChildViewEx(groupPosition, childPosition, isLastChild, convertView, getChild(groupPosition, childPosition));
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    abstract public View getParentView(int groupPosition, boolean isExpanded, View convertView, P p);

    abstract public View getChildViewEx(int groupPosition, int childPosition, boolean isLastChild, View convertView, C c);

    abstract public void selectGroup(int groupP);

    abstract public void selectChild(int groupP, int childP);
}
