package cn.com.jinke.wh_drugcontrol.input.adapter;

import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectExpandAdapter;
import cn.com.jinke.wh_drugcontrol.input.model.Nation;

/**
 * Created by jinke on 2017/8/22.
 */

public class WordProcAdapter extends ProjectExpandAdapter<Nation, Nation> {

    @Override
    public View getParentView(int groupPosition, boolean isExpanded, View convertView, Nation nation) {
        ParentHolder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_word_parent, null, false);
            holder = new ParentHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ParentHolder)convertView.getTag();
        }
        holder.setItem(nation);
        return convertView;
    }


    @Override
    public View getChildViewEx(int groupPosition, int childPosition, boolean isLastChild, View convertView, Nation nation) {
        ChildHolder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_word_child, null, false);
            holder = new ChildHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ChildHolder)convertView.getTag();
        }
        holder.setItem(nation);
        return convertView;
    }

    @Override
    public void selectGroup(int groupP) {

    }

    @Override
    public void selectChild(int groupP, int childP) {

    }


    private class ParentHolder{

        @ViewInject(R.id.word_parent_title)
        private TextView mPTitleTV;

        public ParentHolder(View view){
            x.view().inject(this, view);
        }

        public void setItem(Nation nation){
            mPTitleTV.setText(nation.getText());
        }
    }

    private class ChildHolder{

        @ViewInject(R.id.word_child_title)
        private TextView mCTitleTV;

        public ChildHolder(View view){
            x.view().inject(this, view);
        }

        public void setItem(Nation nation){
            mCTitleTV.setText(nation.getText());
        }
    }


}
