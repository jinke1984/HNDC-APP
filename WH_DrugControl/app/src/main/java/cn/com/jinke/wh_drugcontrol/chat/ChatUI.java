package cn.com.jinke.wh_drugcontrol.chat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.utils.EmptyUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.netease.nim.avchatkit.AVChatKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.chat.adapter.ChatAdapter;
import cn.com.jinke.wh_drugcontrol.chat.adapter.ChatAdapterListenter;
import cn.com.jinke.wh_drugcontrol.chat.manager.AudioRecorder;
import cn.com.jinke.wh_drugcontrol.chat.manager.ChatManager;
import cn.com.jinke.wh_drugcontrol.chat.manager.Constans;
import cn.com.jinke.wh_drugcontrol.chat.model.ChatMessageInfo;
import cn.com.jinke.wh_drugcontrol.chat.model.RoomInfo;
import cn.com.jinke.wh_drugcontrol.chat.model.TypeConstants;
import cn.com.jinke.wh_drugcontrol.customview.AppTwoDialog;
import cn.com.jinke.wh_drugcontrol.customview.chat.RecordButton;
import cn.com.jinke.wh_drugcontrol.customview.chat.RecordButtonListener;
import cn.com.jinke.wh_drugcontrol.customview.chat.RecordStrategy;
import cn.com.jinke.wh_drugcontrol.manager.PullToRefreshHelper;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.PictureUI;
import cn.com.jinke.wh_drugcontrol.utils.AlbumUtils;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.AudioUtils;
import cn.com.jinke.wh_drugcontrol.utils.BitmapUtils;
import cn.com.jinke.wh_drugcontrol.utils.FileImageUpload;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.jpush.android.api.JPushInterface;

/**
 * 聊天界面
 *
 * @author Administrator
 */
@SuppressLint("SimpleDateFormat")
@Route(path = RouteUtils.R_CHAT_UI)
public class ChatUI extends ProjectBaseUI implements OnRefreshListener2<ListView>, OnItemClickListener,
        OnClickListener, OnItemLongClickListener, ChatAdapterListenter {
    String TAG = ChatUI.class.getSimpleName();

    @ViewInject(R.id.chat_et)
    private EditText mChatET;

    @ViewInject(R.id.chat_et_line)
    private View mChatETLine;

    @ViewInject(R.id.chat_btn)
    private Button mChatBtn;

    @ViewInject(R.id.chat_layout)
    private LinearLayout mChatLayout;

    @ViewInject(R.id.photo)
    private TextView mPhotoIV;

    @ViewInject(R.id.audio_iv)
    private ImageView mAudioIV;

    @ViewInject(R.id.chat_btn_voice)
    private RecordButton mVoiceBtn;

    @ViewInject(R.id.add_iv)
    private ImageView mAddImg;

    @ViewInject(R.id.take_photo)
    private TextView mTakePhoto;

    @ViewInject(R.id.take_video)
    private TextView mTakeVideo;

    @ViewInject(R.id.photo_layout)
    private LinearLayout mPhotoLayout;

    @ViewInject(R.id.audio_mkf)
    private ImageView mAudioMkf;

    @ViewInject(R.id.pull_to_refresh_list_view)
    private PullToRefreshListView mPullToRefreshListView;

    @ViewInject(R.id.tool_bar_layout)
    private RelativeLayout mToolBarRL;

    private int isSendMessage = 1;
    private InputMethodManager imm;
    private RecordStrategy mAudioRecorder = null;
    private MediaPlayer player = null;

    private ChatAdapter mChatAdapter = null;

    Persion mPersion;
    private String mName;
    private String mID_CARD;
    private String userId;
    String userSex;
    UserCard userCard;

    private String mChatStr;
    private ArrayList<ChatMessageInfo> ChatList = new ArrayList<>();
    public static int isPresence = 0;

    /**
     * true代表聊天消息，false代表群发消息
     */
    private boolean isGroup = true;

    private int[] MSG = new int[]{MESSAGESUCCESS, CHAT_FRESH, PLAY, CHAT_ROOM_NUMBER, ACCESS_NET_FAILED};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        isPresence = 2;
        registerMessages(MSG);
    }

    @Override
    protected void onInitData() {
        //登录网易云客户端(1.登录成功直接使用 2.登录失败弹出对话框提示是否重新登录)
        doLogin();
    }

    @Override
    public void onInitView() {
        // TODO Auto-generated method stub
        JPushInterface.clearAllNotifications(this);
        Intent intent = getIntent();
        if (intent != null) {
            mPersion = (Persion) intent.getSerializableExtra(CHAT_PERSION);
            mName = mPersion.getRealName();
            mID_CARD = mPersion.getIdentityCard();
            isGroup = intent.getBooleanExtra(BOOLEAN, isGroup);
        }
        userCard = MasterManager.getInstance().getUserCard();
        Assert.assertNotNull(userCard);
        if (userCard != null) {
            userId = userCard.getUserId();
            userSex = "0";
        }
        mPhotoIV.setOnClickListener(this);
        mAudioIV.setOnClickListener(this);
        mAddImg.setOnClickListener(this);
        mTakePhoto.setOnClickListener(this);
        mTakeVideo.setOnClickListener(this);
        mAudioMkf.setOnClickListener(this);

        mAudioRecorder = new AudioRecorder();
        mVoiceBtn.setAudioRecord(mAudioRecorder);

        if(isGroup){
            if(mToolBarRL.getVisibility() == View.GONE){
                mToolBarRL.setVisibility(View.VISIBLE);
            }
        }else {
            if(mToolBarRL.getVisibility() == View.VISIBLE){
                mToolBarRL.setVisibility(View.GONE);
            }
        }

        // 设置开始录音的监听
        mVoiceBtn.setRlistener(new RecordButtonListener() {

            @Override
            public void startRecord() {
                // 开始录音时如果播放器正在播放，停掉
                if (AudioUtils.getInstance().isPlaying()) {
                    int position = AudioUtils.getInstance().getPlayPosition();
                    AudioUtils.getInstance().stopPlay();
                    if (position >= 0) {
                        ChatMessageInfo tempEntity = ChatList.get(position);
                        updateView(position, tempEntity);
                    }
                }
            }
        });

        if (EmptyUtils.isNotEmpty(mID_CARD) && mID_CARD.length() < 18) {
            mChatLayout.setVisibility(View.GONE);
        }
        Header header = getHeader();
        if (header != null) {
            header.rightLayout.setVisibility(View.GONE);
            header.titleText.setText(mName);
            header.leftText.setVisibility(View.VISIBLE);
            header.leftText.setText(getString(R.string.back));
        }

        PullToRefreshHelper.initHeader(mPullToRefreshListView);
        mPullToRefreshListView.setOnRefreshListener(this);
        mPullToRefreshListView.setOnItemClickListener(this);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        mChatAdapter = new ChatAdapter(this, this);
        mChatAdapter.setData(ChatList);
        mPullToRefreshListView.setAdapter(mChatAdapter);

        mChatET.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mChatETLine.setBackgroundColor(0xFF4999E2);
                    mPhotoLayout.setVisibility(View.GONE);
                    isSendMessage = 1;
                } else {
                    mChatETLine.setBackgroundColor(0xFFD2D2D2);
                    imm.hideSoftInputFromWindow(mChatET.getWindowToken(), 0);
                }
            }
        });

        mChatET.addTextChangedListener(new EditChangedListener());

        imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        mChatET.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                return false;
            }

        });

        mChatET.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_SEND) {

                }
                return false;
            }
        });

        mChatBtn.setOnClickListener(new OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View v) {
                sendOutMessage();
            }
        });
        scrollMyListViewToBottom();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    1);

        }
        loadDataInInit();
    }

    /**
     * 向外发送消息
     */
    public void sendOutMessage() {
        mChatStr = mChatET.getText().toString().trim();
        if (TextUtils.isEmpty(mChatStr)) {
            showToast(getString(R.string.qsrxx));
            mChatET.setText("");
            return;
        }
        mChatET.setText("");
        ChatMessageInfo chatMessageInfo = ChatManager.getInstance().generateChatMessageInfo(userCard, mPersion,
                TypeConstants.ChatMsgType.TYPE_TEXT, mChatStr);
        ChatList.add(chatMessageInfo);
        mChatAdapter.setData(ChatList);
        mChatAdapter.notifyDataSetChanged();
        scrollMyListViewToBottom();
        ChatManager.getInstance().sendMsgWithHttp(chatMessageInfo);
        saveChatRecord2Db(chatMessageInfo);
    }

    /**
     * 保存聊天记录到数据库
     *
     * @param outMsg
     */
    void saveChatRecord2Db(ChatMessageInfo outMsg) {
        ChatManager.getInstance().saveTableItemInfo(outMsg);
    }

    private void scrollMyListViewToBottom() {
        mPullToRefreshListView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                mPullToRefreshListView.getRefreshableView().setSelection(mChatAdapter.getCount() - 1);
            }
        });
    }


    /**
     * 从数据库中读取当前两人的所有聊天记录
     */
    private void loadDataInInit() {
        mChatAdapter.getData().clear();
        ChatList.clear();
        mChatAdapter.notifyDataSetInvalidated();
        List<ChatMessageInfo> list = null;
        try {
            list = ChatManager.getInstance().queryChatRecordList(userId, mPersion.getPersonID(), ChatList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list != null) {
            ChatList.addAll(list);
            mChatAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 从数据库中获取最新的那条聊天记录
     */
    private void loadNewestChatDatFromDb() {
        ChatMessageInfo newestChat = ChatManager.getInstance().getNewestChatItem(userId, mPersion.getPersonID());
        if (EmptyUtils.isNotEmpty(newestChat)) {
            ChatList.add(newestChat);
            mChatAdapter.notifyDataSetChanged();
            scrollMyListViewToBottom();
        }
    }

    /**
     * 下拉加载更多消息
     */
    private void loadMoreDataInPullDownToRefresh() {
        List<ChatMessageInfo> moreChatList = null;
        try {
            moreChatList = ChatManager.getInstance().queryChatRecordList(userId, mPersion.getPersonID(), ChatList.size());
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mPullToRefreshListView.onRefreshComplete();
                }
            });
            AppLogger.e(TAG, "loadMoreDataInPullDownToRefresh " + mPullToRefreshListView.getState());
        } catch (Exception e) {
            mPullToRefreshListView.onRefreshComplete();
            AppLogger.e(TAG, "loadMoreDataInPullDownToRefresh " + e.getMessage());
            e.printStackTrace();
        }
        if (EmptyUtils.isNotEmpty(moreChatList)) {
            ChatList.addAll(0, moreChatList);
            mChatAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        scrollMyListViewToBottom();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MESSAGESUCCESS:
                // 重新从数据库取数据
                loadDataInInit();
                scrollMyListViewToBottom();
                break;
            case CHAT_FRESH:
                loadNewestChatDatFromDb();
                break;
            case PLAY:
                // 播放语音
                if (mAudioRecorder != null) {
                    String filePath = mAudioRecorder.getFilePath();
                    this.uploadMessageFile(filePath, TypeConstants.ChatMsgType.FILE_TYPE_VOICE, null);
                }
                break;
            case CHAT_ROOM_NUMBER:
                break;
            case ACCESS_NET_FAILED:
            default:
                dismissLoading();
                break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        isPresence = 1;
        if (player != null) {
            player.stop();
        }
        if (AudioUtils.getInstance().isPlaying()) {
            AudioUtils.getInstance().stopPlay();
            mChatAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void longClickLayout(int position, View view) {
        if (AudioUtils.getInstance().isPlaying()) {
            AudioUtils.getInstance().stopPlay();
            mChatAdapter.notifyDataSetChanged();
        }
        deletDialog(position + 1);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long id) {
        if (AudioUtils.getInstance().isPlaying()) {
            AudioUtils.getInstance().stopPlay();
            mChatAdapter.notifyDataSetChanged();
        }
        deletDialog(position);
        return true;
    }

    private void deletDialog(final int position) {

        AppTwoDialog dialog = new AppTwoDialog(ChatUI.this,
                getString(R.string.sfsc), getString(R.string.tishi), true, null, null);
        dialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.two_ok:
                        ChatMessageInfo removeObj = ChatList.get(position - 1);
                        AppLogger.d("remove " + removeObj.toString());
                        ChatList.remove(removeObj);
                        mChatAdapter.notifyDataSetChanged();
                        ChatManager.getInstance().deleteTableEntity(removeObj);
                        break;
                    default:
                        break;
                }
            }

        });
        dialog.show();
    }

    private void showKeyBoard(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        imm.showSoftInput(editText, 0);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.add_iv: // 点击编辑框右边图标
                if (isSendMessage == 1) {
                    mChatET.setFocusable(false);
                    mChatET.setFocusableInTouchMode(true);
                    mPhotoLayout.setVisibility(View.VISIBLE);
                    isSendMessage = 2;
                    // 强制变为文本输入
                    mVoiceBtn.setVisibility(View.GONE);
                    mChatET.setVisibility(View.VISIBLE);
                    mChatETLine.setVisibility(View.VISIBLE);
                    mAudioMkf.setVisibility(View.VISIBLE);
                    mAudioIV.setVisibility(View.GONE);
                } else {
                    mPhotoLayout.setVisibility(View.GONE);
                    isSendMessage = 1;
                }

                break;
            case R.id.audio_iv: // 点击编辑框左边图标
                mVoiceBtn.setVisibility(View.GONE);
                mChatET.setVisibility(View.VISIBLE);
                mChatETLine.setVisibility(View.VISIBLE);
                mAudioMkf.setVisibility(View.VISIBLE);
                mAudioIV.setVisibility(View.GONE);
                mPhotoLayout.setVisibility(View.GONE);
                isSendMessage = 1;
                this.showKeyBoard(mChatET);
                break;

            case R.id.audio_mkf:
                mChatET.setFocusable(false);
                mChatET.setFocusableInTouchMode(true);
                mVoiceBtn.setVisibility(View.VISIBLE);
                mChatET.setVisibility(View.GONE);
                mChatETLine.setVisibility(View.GONE);
                mAudioMkf.setVisibility(View.GONE);
                mAudioIV.setVisibility(View.VISIBLE);
                mPhotoLayout.setVisibility(View.GONE);
                isSendMessage = 1;
                break;

            case R.id.photo:
                // 点击相册图标
                AlbumUtils.openAlbum(this);
                break;

            case R.id.take_photo:
                // 点击拍照按钮
                AlbumUtils.openCamera(this);
                break;
            case R.id.take_video:
                //点击视频按钮
//                showLoading();
//                ChatManager.getInstance().getVideoChatRoomNumber(mPersion.getId());

                AVChatKit.outgoingCall(this, "1111", "1111", 1,1);
                break;
            default:
                break;
        }
    }

    /**
     * 网易云通信的登录
     */
    public void doLogin(){

        //用户帐号
        String account = "";

        //登录token
        String token = "";
        LoginInfo info = new LoginInfo(account, token);

        RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>(){

            @Override
            public void onSuccess(LoginInfo param) {

            }

            @Override
            public void onFailed(int code) {

            }

            @Override
            public void onException(Throwable exception) {

            }
        };

        NIMClient.getService(AuthService.class).login(info).setCallback(callback);
    }



    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadMoreDataInPullDownToRefresh();
    }


    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
    }

    class EditChangedListener implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub
            mChatBtn.setVisibility(View.VISIBLE);
            mAddImg.setVisibility(View.GONE);
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
            // TODO Auto-generated method stub
            if (mChatET.getText().toString().trim().length() != 0) {
                mChatBtn.setVisibility(View.VISIBLE);
                mAddImg.setVisibility(View.GONE);
            } else {
                mChatBtn.setVisibility(View.GONE);
                mAddImg.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 打开图片查看器
     *
     * @param position
     * @param urls
     */
    protected void imageBrower(Context context, int position, ArrayList<String> urls) {
        PictureUI.startActivity(this, urls, 0);
    }

    private void updateView(int itemIndex, ChatMessageInfo entity) {
        // 得到第一个可显示控件的位置，

        int visiblePosition = mPullToRefreshListView.getRefreshableView().getFirstVisiblePosition();
        int lastVisiblePostion = mPullToRefreshListView.getRefreshableView().getLastVisiblePosition();
        // 只有当要更新的view在可见的位置时才更新，不可见时，跳过不更新
        if (itemIndex + 1 - visiblePosition >= 0
                && lastVisiblePostion - (itemIndex + 1) >= 0) {
            // 得到要更新的item的view
            View view = mPullToRefreshListView.getRefreshableView().getChildAt(itemIndex + 1 - visiblePosition);
            // 调用adapter更新界面
            mChatAdapter.updateView(itemIndex, view, entity);
        }
    }

    @Override
    public void clickLayout(int arg, View view) {
        // TODO Auto-generated method stub
        final ChatMessageInfo entity = ChatList.get(arg);

        // 点击的是普通消息
        if (entity.getMsgType() == TypeConstants.ChatMsgType.TYPE_TEXT) {
            return;
        }

        // 点击的信息是图片信息
        if (entity.getMsgType() == TypeConstants.ChatMsgType.TYPE_PICTURE) {
            ArrayList<String> urls = new ArrayList<String>();
            int position = 0; // 图片索引
            int picCount = 0; // 图片总数
            // 做图片的放大显示处理， 加载消息中的所有图片信息并获取点击的图片位置索引
            for (ChatMessageInfo tempEntity : ChatList) {
                if (tempEntity.getMsgType() == 3) {
                    urls.add(tempEntity.getExtraUrl());
                    if (tempEntity.getExtraUrl().equals(entity.getExtraUrl())) {
                        position = picCount;
                    }
                    picCount += 1; // 记录总共多少张图片
                }
            }

            // 如果图片有图片信息才展示图片
            if (urls.size() > 0) {
                imageBrower(ChatUI.this, position, urls);
            }

            return;
        }

        if (AudioUtils.getInstance().isPlaying()) {
            int position = AudioUtils.getInstance().getPlayPosition();
            AudioUtils.getInstance().stopPlay();
            if (position >= 0) {
                ChatMessageInfo tempEntity = ChatList.get(position);
                updateView(position, tempEntity);
            }
            // 如果点击的是当前正在播放的那个语音消息
            if (position == arg) {
                return;
            }
        }

        AudioUtils.getInstance().play(arg, entity.getExtraUrl(),
                new AudioUtils.PlayState() {
                    @Override
                    public void onPreparedAndStartPlaySuccess() {
                        int position = AudioUtils.getInstance()
                                .getPlayPosition();
                        if (position < 0) {
                            return;
                        }
                        ChatMessageInfo tempEntity = ChatList.get(position);
                        updateView(position, tempEntity);
                    }

                    @Override
                    public void onFinishedPlay() {
                        int position = AudioUtils.getInstance()
                                .getPlayPosition();
                        AudioUtils.getInstance().stopPlay();
                        if (position < 0) {
                            return;
                        }
                        ChatMessageInfo tempEntity = ChatList.get(position);
                        updateView(position, tempEntity);
                    }

                    @Override
                    public void onErrorPlay() {
                        int position = AudioUtils.getInstance()
                                .getPlayPosition();
                        AudioUtils.getInstance().stopPlay();
                        if (position < 0) {
                            return;
                        }
                        ChatMessageInfo tempEntity = ChatList.get(position);
                        updateView(position, tempEntity);
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {}

    // 存储数据
    public boolean saveBitmap(Bitmap bitmap, String path) {
        try {
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            boolean result = bitmap.compress(Bitmap.CompressFormat.JPEG, 75,
                    fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1 && requestCode != 3) {
            return;
        }
        switch (requestCode) {
            case AlbumUtils.OPEN_ALBUM:
                if (data == null){
                    return;
                }
                String urlPath = AlbumUtils.getDirFromAlbumUri(data.getData());
                Bitmap bitmap = BitmapUtils.getLowMemoryBitmap(urlPath, 720 * 1280,
                        true);
                String filePath = Environment.getExternalStorageDirectory()
                        .getAbsolutePath()
                        + "/"
                        + System.currentTimeMillis()
                        + ".jpg";
                this.uploadMessageFile(filePath, TypeConstants.ChatMsgType.FILE_TYPE_PICTURE, bitmap);
                break;
            case AlbumUtils.OPEN_CAMERA:

                String urlPath1 = Environment.getExternalStorageDirectory() + "/"
                        + Constans.IMAGE_MASTER_CROP;
                Bitmap bitmap1 = BitmapUtils.getLowMemoryBitmap(urlPath1,
                        720 * 1280, true);
                String filePath1 = Environment.getExternalStorageDirectory()
                        .getAbsolutePath()
                        + "/"
                        + System.currentTimeMillis()
                        + ".jpg";
                this.uploadMessageFile(filePath1, TypeConstants.ChatMsgType.FILE_TYPE_PICTURE, bitmap1);
                break;
            case AlbumUtils.PHOTO_RESULT:

                break;
            default:
                break;
        }
    }

    /**
     * 上传消息附件
     *
     * @param filePath 附件路径
     * @param type     类型 1：图片 2：语音
     */
    private void uploadMessageFile(final String filePath, final int type,
                                   final Bitmap bitmap) {
        showLoading();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                StringBuffer url = new StringBuffer();
                int msgType;
                File file = new File(filePath);
                boolean suc;
                if (type == TypeConstants.ChatMsgType.FILE_TYPE_VOICE) {
                    msgType = TypeConstants.ChatMsgType.TYPE_VOICE;
                    suc = true;
                } else {
                    msgType = TypeConstants.ChatMsgType.TYPE_PICTURE;
                    suc = saveBitmap(bitmap, filePath);
                }
                if (suc) {
                    url.append(ChatManager.getInstance().chatUploadFile());
                    url.append("&fileType=" + type);
                    url.append("&fileName=" + file.getName());
                    String json = FileImageUpload.uploadFile(file, url.toString());
                    dismissLoading();
                    if (!TextUtils.isEmpty(json)) {
                        try {
                            JSONObject resultJson = new JSONObject(json);

                            String result = resultJson.optString(K_SUCCESS);
                            if (result.equals("1")) {
                                JSONObject object = resultJson
                                        .optJSONObject(K_DATA);
                                String urlString = object.optString(K_URL);
                                // 创建消息结构
                                final ChatMessageInfo chatMessageInfo = ChatManager.getInstance().generateChatMessageInfo(userCard, mPersion,
                                        msgType, RequestHelper.getInstance().OUT_IMAGE_URL + urlString);
                                ChatManager.getInstance().sendMsgWithHttp(chatMessageInfo);
                                // 刷新界面
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        ChatList.add(chatMessageInfo);
                                        mChatAdapter.setData(ChatList);
                                        mChatAdapter.notifyDataSetChanged();
                                        scrollMyListViewToBottom();
                                        saveChatRecord2Db(chatMessageInfo);
                                    }
                                });

                            } else {
                                String tips = resultJson.optString(K_CASE);
                                showToast(tips);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (AudioUtils.getInstance().isPlaying()) {
            AudioUtils.getInstance().stopPlay();
        }

        if (mVoiceBtn != null && mVoiceBtn.getToast() != null) {
            mVoiceBtn.getToast().cancel();
            Log.i("cancel", "cancel");
        }
    }
}
