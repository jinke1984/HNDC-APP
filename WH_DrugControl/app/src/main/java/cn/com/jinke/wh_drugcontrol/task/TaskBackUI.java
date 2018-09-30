package cn.com.jinke.wh_drugcontrol.task;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by ${xkr} on 2017/11/21.
 * 任务  退回
 */

public class TaskBackUI extends ProjectBaseUI {

    String taskId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_back);
        registerMessages(MSG_TASK_BACK);
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        Intent intent = getIntent();
        if(intent != null){
            taskId = intent.getStringExtra("taskId");
        }
    }

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_TASK_BACK:
                dismissLoading();
                break;
        }
        return super.handleMessage(msg);
    }

//    private void doTaskBack(){
//        showLoading();
//        RequestParams requestParams = new RequestParams(TASK_BACK);
//        requestParams.addParameter("userId","");
//        requestParams.addParameter("curTaskId",taskId);
//        requestParams.addParameter("comment","");
//        x.http().post(requestParams,new CallbackWrapper<String>(MSG_TASK_BACK,2){
//            @Override
//            public void onSuccess(int state, String msg, String object, int total) {
//                super.onSuccess(state, msg, object, total);
//            }
//        });
//    }
}
