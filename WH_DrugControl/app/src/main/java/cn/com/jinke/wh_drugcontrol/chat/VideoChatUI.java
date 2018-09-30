package cn.com.jinke.wh_drugcontrol.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.utils.EmptyUtils;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import cn.com.jinke.wh_drugcontrol.BuildConfig;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;

/**
 * Created by xkr on 2018/1/10.
 */
@Route(path = RouteUtils.R_CHAT_VIDEO)
public class VideoChatUI extends JitsiMeetActivity implements CodeConstants {

    /**
     * 房间号
     */
    private String roomNo;

    /**
     * 远程视频的地址
     */
    private String VIDEO_URL = "https://hainanjindu.cn/";

    private URL url = null;


    @Override
    protected JitsiMeetView initializeView() {
        JitsiMeetView view = super.initializeView();
        // XXX In order to increase (1) awareness of API breakages and (2) API
        // coverage, utilize JitsiMeetViewListener in the Debug configuration of
        // the app.

        if (BuildConfig.DEBUG && view != null) {
            view.setListener(new JitsiMeetViewListener() {
                private void on(String name, Map<String, Object> data) {
                    // Log with the tag "ReactNative" in order to have the log
                    // visible in react-native log-android as well.
                    Log.d(
                            "ReactNative",
                            JitsiMeetViewListener.class.getSimpleName() + " "
                                    + name + " "
                                    + data);
                }

                @Override
                public void onConferenceFailed(Map<String, Object> data) {
                    on("CONFERENCE_FAILED", data);
                }

                @Override
                public void onConferenceJoined(Map<String, Object> data) {
                    on("CONFERENCE_JOINED", data);
                }

                @Override
                public void onConferenceLeft(Map<String, Object> data) {
                    on("CONFERENCE_LEFT", data);
                }

                @Override
                public void onConferenceWillJoin(Map<String, Object> data) {
                    on("CONFERENCE_WILL_JOIN", data);
                }

                @Override
                public void onConferenceWillLeave(Map<String, Object> data) {
                    on("CONFERENCE_WILL_LEAVE", data);
                }

                @Override
                public void onLoadConfigError(Map<String, Object> data) {
                    on("LOAD_CONFIG_ERROR", data);
                }
            });
        }

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // As this is the Jitsi Meet app (i.e. not the Jitsi Meet SDK), we do
        // want the Welcome page to be enabled. It defaults to disabled in the
        // SDK at the time of this writing but it is clearer to be explicit
        // about what we want anyway.
        Intent intent = getIntent();
        if(intent != null){
            roomNo = intent.getStringExtra(B_ID);
        }
        try {
            if(EmptyUtils.isEmpty(roomNo)){
                roomNo = "";
            }
            url = new URL(VIDEO_URL+roomNo);
            //url = new URL(VIDEO_URL);
            AppLogger.d("url : "+url.toString());
            setWelcomePageEnabled(true);
            //setDefaultURL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            finish();
        }

        super.onCreate(savedInstanceState);
        loadURL(url);

    }


}
