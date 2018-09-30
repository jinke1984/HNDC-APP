package cn.com.jinke.wh_drugcontrol.input;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by jinke on 2017/9/2.
 */

@Route(path = RouteUtils.R_INPUT_ASSESS_UI)
public class AssessUI extends ProjectBaseUI {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_assess);
    }
}
