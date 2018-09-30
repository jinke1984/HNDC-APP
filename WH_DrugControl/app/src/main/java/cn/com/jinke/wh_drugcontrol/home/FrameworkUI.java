package cn.com.jinke.wh_drugcontrol.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.cloudwalk.CloudwalkSDK;
import cn.cloudwalk.FaceInterface;
import cn.cloudwalk.libproject.progressHUD.CwProgressHUD;
import cn.cloudwalk.libproject.util.FileUtil;
import cn.com.jinke.wh_drugcontrol.BuildConfig;
import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseFragmentUI;
import cn.com.jinke.wh_drugcontrol.manager.CommonManager;
import cn.com.jinke.wh_drugcontrol.manager.FragmentTabManager;
import cn.com.jinke.wh_drugcontrol.manager.FragmentTabManager.FragmentTab;
import cn.com.jinke.wh_drugcontrol.manager.FragmentTabManager.OnTabChangedListener;
import cn.com.jinke.wh_drugcontrol.manager.VersionManager;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

import static cn.cloudwalk.libproject.util.FileUtil.assetsDataToDest;
import static cn.cloudwalk.libproject.util.FileUtil.unZipFolder;


/**
 * Created by jinke on 2017/7/10.
 */

@Route(path = RouteUtils.R_FRAMEWORK_UI)
public class FrameworkUI extends ProjectBaseFragmentUI implements OnTabChangedListener {

    @ViewInject(R.id.persion_tv)
    private TextView mPersionTab;

    @ViewInject(R.id.me_tv)
    private RelativeLayout mMeTab;

    @ViewInject(R.id.message_tv)
    private TextView mMessageTab;

    private long mLastPressBackTime;
    private FragmentTabManager mTabManager;

    public static boolean isRunning = false;
    private String mTargetUi = null;

    /**
     *  进度框
     */
    public CwProgressHUD processDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkModule();
        setContentView(R.layout.ui_framework);
        List<FragmentTab> items = new ArrayList<>();
        items.add(new FragmentTab(MeFragment.class, null, MeFragment.class.getName()));
        items.add(new FragmentTab(HomeFragment.class, null, HomeFragment.class.getName()));
        items.add(new FragmentTab(MessageFragment.class, null, MessageFragment.class.getName()));
        int containerId = R.id.framework_content_container;
        mTabManager = new FragmentTabManager(this, getSupportFragmentManager(), items, containerId);
        mTabManager.setOnTabChangedListener(this);
        onViewClick(mMeTab);
        CommonManager.getInstance().uploadPushId();
        VersionManager.startCheckUpdate();

        if (getIntent() != null) {
            mTargetUi = getIntent().getStringExtra(TARGET_UI);

            if (mTargetUi != null) {
                ARouter.getInstance().build(mTargetUi).navigation();
            }
        }
        isRunning = true;
    }

    @Override
    protected void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onTabChanged(Fragment current, int index) {
        if (index == 1) {
            MessageProxy.sendEmptyMessage(HOME_INDEX);
        }else if(index == 2){
            MessageProxy.sendEmptyMessage(MESSAGE_INDEX);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            long current = System.currentTimeMillis();
            if (current - mLastPressBackTime > 2000) {
                mLastPressBackTime = current;
                showToast(R.string.zcdjtc);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Event(value = {R.id.persion_tv, R.id.me_tv, R.id.message_tv})
    private void onViewClick(View view) {
        int id = view.getId();
        mPersionTab.setSelected(id == R.id.persion_tv);
        mMeTab.setSelected(id == R.id.me_tv);
        mMessageTab.setSelected(id == R.id.message_tv);
        switch (id) {
        case R.id.persion_tv:
            mTabManager.setCurrent(MeFragment.class.getName());
            break;
        case R.id.me_tv:
            mTabManager.setCurrent(HomeFragment.class.getName());
            break;
        case R.id.message_tv:
            mTabManager.setCurrent(MessageFragment.class.getName());
            break;
        default:
            break;
        }
    }

    private static void initcloudwalkSDK() {
        CloudwalkSDK cloudwalkSDK = CloudwalkSDK.getInstance();
        int op = FaceInterface.FaceDetType.CW_FACE_TRACK | FaceInterface.FaceDetType.CW_FACE_KEYPT
                | FaceInterface.FaceDetType.CW_FACE_LIVENESS;
        cloudwalkSDK.setOperator(op);
        // 设置活体等级
        cloudwalkSDK.cwSetLivessLevel(liveLevel);
        //初始化以及验证用户授权码的合法性
        int initRet = cloudwalkSDK.cwInit(BuildConfig.CLOUDWALK_KEY);
        AppLogger.e("cloudwalk sdk init result"+initRet);
    }

    /**
     * 检查模型是否存在
     */
    public void checkModule(){
        //再次检查模型文件是否已拷贝到app运行目录
        final File installation = new File(getFilesDir(), MODULES);
        String storagePath = installation.getAbsolutePath();
        StringBuilder pFaceDetectFile = new StringBuilder(storagePath + File.separator + "faceDetector_2_4.mdl");
        StringBuilder pFaceKeyPtFile = new StringBuilder(storagePath + File.separator + "keypt_detect_model_sdm_9pts.bin");
        StringBuilder pFaceKeyPtTrackFile = new StringBuilder(storagePath + File.separator + "keypt_track_model_sdm_9pts.bin");
        StringBuilder pFaceQualityFile = new StringBuilder(storagePath + File.separator + "facequality_4_1.bin");
        StringBuilder pFaceLivenessFile = new StringBuilder(storagePath+ File.separator + "liveness171120.bin");
        //检测每个模型文件是否存在
        boolean allModulesExist = false;
        if (installation.exists() && new File(pFaceDetectFile.toString()).exists()
                && new File(pFaceKeyPtFile.toString()).exists() && new File(pFaceKeyPtTrackFile.toString()).exists()
                && new File(pFaceQualityFile.toString()).exists() && new File(pFaceLivenessFile.toString()).exists()) {
            allModulesExist = true;
        }

        if (!allModulesExist) {
            processDialog = CwProgressHUD.create(this).setStyle(CwProgressHUD.Style
                    .SPIN_INDETERMINATE)
                    .setLabel(getString(cn.cloudwalk.libproject.R.string.cloudwalk_copy_modules)).setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f);
            processDialog.show();
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    //
                    boolean initStage = false;
                    //data/data/package_name/files/
                    String dataDirPath = getFilesDir().getAbsolutePath();
                    try {
                        //模型文件拷贝
                        assetsDataToDest(FrameworkUI.this,MODULES_ZIP, dataDirPath + File.separator + MODULES_ZIP);
                        //接压缩模型文件
                        unZipFolder(dataDirPath + File.separator + MODULES_ZIP, dataDirPath);
                        //设置模型文件路径
                        CloudwalkSDK.getInstance().setModulePath(dataDirPath + File.separator + MODULES);
                        initStage = true;
                    } catch (IOException ex) {
                        initStage = false;
                        AppLogger.e("copy module IOException:" + ex.getMessage());
                    } catch (RuntimeException e) {
                        initStage = false;
                        AppLogger.e("copy module RuntimeException:" + e.getMessage());
                    } catch (Exception e) {
                        initStage = false;
                        AppLogger.e("copy module Exception:" + e.getMessage());
                    } finally {
                        //删除模型文件压缩包
                        File modelsZip = new File(dataDirPath + File.separator + MODULES_ZIP);
                        if (modelsZip.exists()) {
                            FileUtil.deleteFile(modelsZip);
                        }

                        if (initStage) {
                            initcloudwalkSDK();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //销毁进度对话框
                                    if (processDialog!=null &&processDialog.isShowing()) {
                                        processDialog.dismiss();
                                    }
                                }
                            });
                        } else {
                            //删除解压后模型文件
                            File modelsDir = new File(dataDirPath + File.separator + MODULES);
                            if (modelsDir.exists()) {
                                FileUtil.deleteFile(modelsDir);
                            }
                            //初始化失败
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //销毁进度对话框
                                    if (processDialog!=null &&processDialog.isShowing()) {
                                        processDialog.dismiss();
                                    }
                                    //提示初始化失败
                                    showToast(cn.cloudwalk.libproject.R.string.cloudwalk_copy_modules_failed);
                                }
                            });
                        }

                    }

                }
            });
        } else {
            CloudwalkSDK.getInstance().setModulePath(getFilesDir().getAbsolutePath() + File.separator + MODULES);
            initcloudwalkSDK();
        }
    }

}
