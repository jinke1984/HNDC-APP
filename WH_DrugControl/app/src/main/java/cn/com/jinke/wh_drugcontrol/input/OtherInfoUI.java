package cn.com.jinke.wh_drugcontrol.input;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.customview.AppListDialog;
import cn.com.jinke.wh_drugcontrol.customview.CustomEditText;
import cn.com.jinke.wh_drugcontrol.input.manager.PersonManager;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;
import cn.com.jinke.wh_drugcontrol.input.model.CachePerson;
import cn.com.jinke.wh_drugcontrol.persion.PictureUI;
import cn.com.jinke.wh_drugcontrol.thread.Dispatcher;
import cn.com.jinke.wh_drugcontrol.utils.AlbumUtils;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;

import static android.app.Activity.RESULT_OK;
import static cn.com.jinke.wh_drugcontrol.utils.AlbumUtils.OPEN_ALBUM;
import static cn.com.jinke.wh_drugcontrol.utils.AlbumUtils.PHOTO_RESULT;

/**
 * Created by jinke on 2017/7/25.
 */

public class OtherInfoUI extends BasePersonFragment {

    @ViewInject(R.id.photo_img)
    private ImageView photoIv; // 吸毒人员照片

    @ViewInject(R.id.wgy_et)
    private CustomEditText mWgyEt;

    @ViewInject(R.id.sqmj_et)
    private CustomEditText mSqmjEt;

    private PersonManager personManager = PersonManager.getInstance();

    private int[] MSG = new int[]{ADD_PERSON_PART1, ADD_PERSON_PART2, ADD_PERSON_PART3,
            ADD_PERSON_PART4, ADD_PERSON_PART5, UPLOAD_PHOTO_SUCCESS, UPLOAD_PHOTO_FAIL,
            ACCESS_NET_FAILED};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case ADD_PERSON_PART1:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                CachePerson person = (CachePerson) msg.obj;
                mPerson.setId(person.getId());

                personManager.uploadPersonPart2(mPerson);
            }
            break;
        case ADD_PERSON_PART2:
            personManager.uploadPersonPart3(mPerson);
            break;
        case ADD_PERSON_PART3:
            personManager.uploadPersonPart4(mPerson);
            break;
        case ADD_PERSON_PART4:
            personManager.uploadPersonPart5(mPerson);
            break;
        case ADD_PERSON_PART5:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                delete(mPerson);
                showToast(R.string.upload_success);

                getActivity().finish();
            } else {
                showToast(R.string.upload_failed);
            }
            break;
        case UPLOAD_PHOTO_SUCCESS:
            // 上传图片成功
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                mPerson.setPhotoAddress((String) msg.obj);
                setPersonImage(RequestHelper.getInstance().IN_IMAGE_URL + mPerson.getPhotoAddress());
            } else {
                showToast(getString(R.string.upload_attachment_err));
            }
            break;
        case UPLOAD_PHOTO_FAIL:
            // 上传图片失败
            dismissLoading();
            showToast(getString(R.string.upload_attachment_err));
            break;
        case ACCESS_NET_FAILED:
            dismissLoading();
            break;
        default:
            break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG);
    }

    @Event(value = {R.id.photo_img, R.id.upload_photo_btn, R.id.pre_btn, R.id.next_btn})
    private void onClick(View view) {
        switch (view.getId()) {
        case R.id.photo_img:
            ArrayList<String> list = new ArrayList<>(1);
            list.add(mPerson.getPhotoAddress());
            PictureUI.startActivity(getActivity(), list, 0);
            break;
        case R.id.upload_photo_btn:
            // 上传吸毒人员照片
            pickPhoto();
            break;
        case R.id.pre_btn:
            changeToFragment(3);
            break;
        case R.id.next_btn:
            // 保存
            uploadData();
            break;
        default: // no code
            break;
        }
    }

    private void uploadData() {
        onSaveData(mPerson);
        forceSaveToDb();

        if (mPerson.getCacheState1() != COMPLETED_DONE) {
            changeToFragment(0);
            showToast(R.string.common_err_tips);
        } else if (mPerson.getCacheState2() != COMPLETED_DONE) {
            changeToFragment(1);
            showToast(R.string.common_err_tips);
        } else if (mPerson.getCacheState3() != COMPLETED_DONE) {
            changeToFragment(2);
            showToast(R.string.common_err_tips);
        } else if (mPerson.getCacheState4() != COMPLETED_DONE) {
            changeToFragment(3);
            showToast(R.string.common_err_tips);
        } else if (mPerson.getCacheState5() != COMPLETED_DONE) {
            showToast(R.string.common_err_tips);

        } else {
            showLoading();
            personManager.uploadPersonPart1(mPerson);
//            personManager.uploadPersonPart2(mPerson);
//            personManager.uploadPersonPart3(mPerson);
//            personManager.uploadPersonPart4(mPerson);
//            personManager.uploadPersonPart5(mPerson);
        }
    }

    @Override
    void onSaveData(CachePerson person) {
        person.setGridPerson(mWgyEt.getText().trim());
        person.setCommunityPolice(mSqmjEt.getText().trim());
    }

    @Override
    int getContentViewResId() {
        return R.layout.ui_other_info;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView(CachePerson person) {
        mWgyEt.setText(person.getGridPerson());
        mSqmjEt.setText(person.getCommunityPolice());

        setPersonImage(RequestHelper.getInstance().IN_IMAGE_URL + person.getPhotoAddress());
    }

    private void setPersonImage(String path) {
        Glide.with(this)
                .load(path)
                .placeholder(R.drawable.default_image)
                .into(photoIv);
    }

    private void pickPhoto() {
        ArrayList<String> list = new ArrayList<>();
        list.add(getString(R.string.pz));
        list.add(getString(R.string.xc));
        String title = getString(R.string.sctp);
        AppListDialog dialog = new AppListDialog(getActivity(), title, list, true);

        final Fragment fragment = this;
        dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                switch (position) {
                case 0:
                    AlbumUtils.openCamera(fragment);
                    break;
                case 1:
                    AlbumUtils.openAlbum(fragment);
                    break;
                default:
                    break;
                }
            }

        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        switch (requestCode) {
        case OPEN_ALBUM:
            if (data == null) return;
            uploadImage(AlbumUtils.getDirFromAlbumUri(data.getData()));
//            AlbumUtils.startPhotoZoom(AlbumUtils.getDirFromAlbumUri(data.getData()), this);
            break;
        case AlbumUtils.OPEN_CAMERA:
            uploadImage(Environment.getExternalStorageDirectory()
                    + "/" + CodeConstants.IMAGE_MASTER_CROP);
//            AlbumUtils.startPhotoZoom(Environment.getExternalStorageDirectory()
//                    + "/" + CodeConstants.IMAGE_MASTER_CROP, this);
            break;
        case PHOTO_RESULT:
            if (data == null) return;
            Bundle b = data.getExtras();
            if (b != null) {
                byte[] d = b.getByteArray("data");
            }
            break;
        default:
            break;
        }
    }

    private void uploadImage(final String imagePath) {
//        setPersonImage(imagePath);

        Dispatcher.runOnSingleThread(new Runnable() {
            @Override
            public void run() {
                try {
                    showLoading();
                    PhotoManager.getInstance().upLoadPic(imagePath, "", "", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
