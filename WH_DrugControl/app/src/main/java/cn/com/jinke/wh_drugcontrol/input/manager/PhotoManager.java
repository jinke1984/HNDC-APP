package cn.com.jinke.wh_drugcontrol.input.manager;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.io.File;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectApplication;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Photo;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.FileImageUpload;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ETYPE;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CommonRequestParams;


/**
 * Created by jinke on 2017/9/2.
 */

public class PhotoManager extends UrlSetting implements CodeConstants, MsgKey {

    private static PhotoManager instance;

    private PhotoManager() {

    }

    public static PhotoManager getInstance() {
        if (instance == null) {
            synchronized (PhotoManager.class) {
                if (instance == null) {
                    instance = new PhotoManager();
                }
            }
        }
        return instance;
    }

    public void uploadPicWithUserId(String filepath, String position, int type) throws JSONException {
        UserCard userCard = MasterManager.getInstance().getUserCard();
        upLoadPic(filepath, position, userCard.getUserId(), type);
    }

    /**
     * 提交图片
     *
     * @param filepath 图片的物理路径
     * @param position 提交图片的当前得力位置
     * @param id       id(扩展字段，目前是用户id)
     * @param type     上传图片的类型
     * @throws JSONException
     */
    public void upLoadPic(String filepath, String position, String id, int type) throws JSONException {

        File file = new File(filepath);
        String urlBuffe = getHeadURL()+SAVEFILE_URL +
                "&fileName=" + file.getName() +
                "&position=" + position +
                "&userId=" + id;

        String json = FileImageUpload.uploadFile(file, urlBuffe);
        if (!json.equals("0")) {
            //上传文件成功
            Photo photo = new Photo();
            JSONObject jsonObject = new JSONObject(json);
            int suc = jsonObject.optInt(SUCC);
            JSONObject dataObject = jsonObject.optJSONObject(DATA);
            String url = dataObject.optString(URL);
            photo.setType(type);
            photo.setUrl(url);
            MessageProxy.sendMessage(UPLOAD_PHOTO_SUCCESS, suc, photo);
        } else {
            //上传文件失败
            MessageProxy.sendMessage(UPLOAD_PHOTO_SUCCESS, FAIL, "");
        }
    }

    /**
     * 外网查看附件的方法
     * @param filePath
     */
    public void getOutFile(final String filePath, final ImageView showIV, final Context context, final String imageUrl){
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(GET_PC_FILE);
        builder.putParams(FILEPATH, filePath);
        x.http().post(builder.build(), new CallbackWrapper<Void>(GET_PIC_FILE, 2) {

            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                StringBuffer url = new StringBuffer();
                url.append(imageUrl);
                url.append(filePath);
                Glide.with(context).load(url.toString()).placeholder(R.drawable.item_default_head)
                        .error(R.drawable.item_default_head).into(showIV);
            }
        });
    }

    /**
     * 内外网查看附件的方法
     * @param filePath
     * @param showIV
     */
    public void getPic(String filePath, final ImageView showIV){
        //TODO true 代表内网, false 代表外网

        if(TextUtils.isEmpty(filePath)){
            return;
        }

        String imageUrl = RequestHelper.getInstance().getImageRequestHeader();
        boolean netSwitch = ShareUtils.getInstance().get(ESHARE.SYS, SWITCH_NET, ETYPE.BOOL);
        final Context context = ProjectApplication.getContext();
        StringBuffer url = new StringBuffer();
        url.append(imageUrl);
        url.append(filePath);
        if(netSwitch){
            //内网
            Glide.with(context).load(url.toString()).placeholder(R.drawable.item_default_head)
                    .error(R.drawable.item_default_head).into(showIV);
        }else{
            //外网
            getOutFile(filePath, showIV, context, imageUrl);
        }
    }

    private String getHeadURL(){
        return RequestHelper.getInstance().getRequestHeader();
    }
}
