package cn.com.jinke.wh_drugcontrol.utils;

import android.app.Activity;
import android.view.View;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.customview.AppOneDialog;
import cn.com.jinke.wh_drugcontrol.persion.manager.PersionManager;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.persion.model.DocumentStatus;

/**
 * DocUtil
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/10/27
 */

public class DocUtil {
    private DocUtil() {
    }

    public static boolean checkDocStatus(Activity activity, int[] filterDocStatus, boolean finishAct) {
        Document document = PersionManager.getInstance().getDocument();
        int status = DocumentStatus.STATUS_4;
        if (document != null) status = document.getDealStatus();

        boolean inFilter = false;
        if (filterDocStatus != null && document != null) {
            for (int sta : filterDocStatus) {
                if (sta == status) {
                    inFilter = true;
                    break;
                }
            }
        }
        if (inFilter) {
            switch (status) {
            case DocumentStatus.STATUS_0:
            case DocumentStatus.STATUS_1:
                showTipsDialog(activity, activity.getString(R.string.reject_doc_status1), finishAct);
                break;
            case DocumentStatus.STATUS_3:
            case DocumentStatus.STATUS_4:
                showTipsDialog(activity, activity.getString(R.string.reject_doc_status3), finishAct);
                break;
            case DocumentStatus.STATUS_9:
                showTipsDialog(activity, activity.getString(R.string.reject_doc_status9), finishAct);
                break;

            default:
                break;
            }
        }

        return inFilter;
    }

    public static boolean checkDocStatus(Activity activity, int[] filterDocStatus) {
        return checkDocStatus(activity, filterDocStatus, true);
    }

    private static void showTipsDialog(final Activity activity, String message, final boolean finishAct) {
        final AppOneDialog dialog = new AppOneDialog(activity, message,
                activity.getString(R.string.tishi), false, activity.getString(R.string.qd));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!activity.isFinishing() && finishAct) {
                    activity.finish();
                } else {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

}
