package cn.com.jinke.wh_drugcontrol.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectApplication;
import cn.com.jinke.wh_drugcontrol.customview.UpdateDialog;
import cn.com.jinke.wh_drugcontrol.manager.model.VersionInfo;
import cn.com.jinke.wh_drugcontrol.thread.Dispatcher;
import cn.com.jinke.wh_drugcontrol.utils.ActivityHelper;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.StorageUtil;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CommonRequestParams;

/**
 * @Author: lufengwen
 * @Date: 2016年3月30日 上午10:10:12
 * @Description: 检查更新管理器
 */
public class VersionManager extends UrlSetting implements CodeConstants {
	private static int sCurrentVersionCode;
	private static String sVersionName;
	private static boolean sIsUpgrade = false;
	public static boolean isDownload;
	public static boolean isPause;

	public final static int UPDATE_DOWNLOAD_FILE_SIZE = Integer.MAX_VALUE - 1;
	public final static int UPDATE_CLOSE_DIALOG = Integer.MAX_VALUE - 2;

	public static int getVersionCode() {
		return sCurrentVersionCode;
	}

	public static void setVersionCode(int versionCode) {
		VersionManager.sCurrentVersionCode = versionCode;
	}

	public static String getVersionName() {
		return sVersionName;
	}

	public static void setVersionName(String versionName) {
		VersionManager.sVersionName = versionName;
	}

	public static void setUpgrade(boolean upgrade) {
		sIsUpgrade = upgrade;
	}

	public static boolean isUpgrade() {
		return sIsUpgrade;
	}

	public static void startCheckUpdate() {
		startCheckUpdate(new GetVersionData());
	}

	public static void startCheckUpdate(final VersionDataCallback callback) {
		getUpdateInfo(callback);
	}



	/**
	 * 获取最新的版本信息
	 * @param callback 1 筑城阳光 2 数据禁毒
	 */
	private static void getUpdateInfo(final VersionDataCallback callback) {
		if (callback == null) {
			return;
		}
		CommonRequestParams.Builder builder = new CommonRequestParams.Builder(CommonManager.getInstance().UPDATEURL);
		builder.putParams(COMMAND,FINDAPPVERSION);
		builder.putParams(TYPE,2);
		x.http().post(builder.build(), new CallbackWrapper<VersionInfo>(2) {

			@Override
			public void onSuccess(int state, String msg, final VersionInfo info, int total) {
				if (info != null && !TextUtils.isEmpty(info.getDownPath())) {
					Dispatcher.runOnNewThread(new Runnable() {
						@Override
						public void run() {
							//String url = UPDATE_URL + "/" + info.getPath();
							//long size = getFileSize(url);
							//info.setPackageSize(size);
							callback.onCallback(info);
						}
					});
				}
				else {
					callback.onCallback(info);
				}
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				//log("getUpdateInfo error. " + ex.toString());
				callback.onCallback(null);

			}
		});
	}

	public static interface VersionDataCallback {
		public void onCallback(VersionInfo versionInfo);
	}

	public static class GetVersionData implements VersionDataCallback {
		@Override
		public void onCallback(final VersionInfo versionInfo) {

			Dispatcher.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					final Activity activity = ProjectApplication.getCurrentActivity();
					if (ActivityHelper.isActivityRunning(activity)) {
						if (versionInfo != null) {
							int version = Integer.valueOf(versionInfo.getVersion());
							if (version > sCurrentVersionCode ) {
								//CommonSettings.setLastVersionCode(versionInfo.getVersionCode());
								Dispatcher.runOnUiThread(new Runnable() {
									@Override
									public void run() {
										showUpdateDialog(activity, versionInfo);
									}
								});
							}else{
								Dispatcher.runOnUiThread(new Runnable() {
									@Override
									public void run() {
										Context aContext = ProjectApplication.getContext();
										Toast.makeText(aContext, aContext.getString(R.string.bbyszx), Toast.LENGTH_SHORT).show();
									}
								});
							}
						}
					}
				}
			});
		}
	}

	/**
	 * 提示升级对话框
	 * @param context
	 * @param versionInfo
	 */
	public static void showUpdateDialog(final Context context, final VersionInfo versionInfo) {
		final long fileSize = versionInfo.getSize();
		if (context == null || versionInfo == null || fileSize <= 0) {
			return;
		}
		String message = String.format("版本：%1$s ，安装包大小：%2$sMB", versionInfo.getVersion(), String.format("%.2f", fileSize / 1024f / 1024f));
		if (!TextUtils.isEmpty(versionInfo.getDescription())) {
			try {
				message += "\n" + versionInfo.getDescription();
			}
			catch (Exception e) {
				//log("replace error. " + e.toString());
				e.printStackTrace();
			}
		}
		UpdateDialog.Builder builder = new UpdateDialog.Builder(context);
		builder.setMessage(message);
		//builder.setCancelable(versionInfo.get() < sCurrentVersionCode);
		builder.setCancelable(false);
		final UpdateDialog dialog = builder.create();
		builder.setCancelListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
				isDownload = false;
			}
		});
		builder.setDownloadListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!StorageUtil.isAvailableBlocks(fileSize)) {
					Toast.makeText(context, "sd卡空间不足", Toast.LENGTH_SHORT).show();
					return;
				}
				Dispatcher.runOnWebThread(new Runnable() {
					@Override
					public void run() {
						downApkFile(versionInfo);
					}
				});
			}
		});
		dialog.show();
	}

	/**
	 * 获取远程文件的大小
	 * @param url
	 * @return
	 */
	public static long getFileSize(String url) {

		long fileLength = -1;
		try {

			HttpURLConnection httpConnection = (HttpURLConnection) new URL(url).openConnection();
			httpConnection.setRequestProperty("User-Agent", "NetFox");
			int responseCode = httpConnection.getResponseCode();
			if (responseCode >= 400) {
				return -2;
			}
			String header;
			for (int i = 1; ; i++) {
				header = httpConnection.getHeaderFieldKey(i);
				if (header != null) {
					// 真机Content-Length，虚拟机content-length
					if ("Content-Length".equals(header)) {
						fileLength = Long.parseLong(httpConnection.getHeaderField(header));
						break;
					}
					else if ("content-length".equals(header)) {
						fileLength = Long.parseLong(httpConnection.getHeaderField(header));
						break;
					}
				}
			}
		}
		catch (Exception e) {
			fileLength = -2;
			e.printStackTrace();

		}

		return fileLength;
	}

	/**
	 * 下载升级文件
	 * @param versionInfo
	 */
	private static void downApkFile(final VersionInfo versionInfo) {
		//long serverSize = getFileSize(UPDATE_URL + "/" + versionInfo.getPath());
		long serverSize = versionInfo.getSize();
		if (serverSize <= 0) {
			return;
		}
		int currentSize = 0;
		File file = null;
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		int percent = 0;
		try {
			isDownload = true;
			isPause = false;
			File filePath = new File(StorageManager.getTempDir());
			String updateVersionName = versionInfo.getName() + ".apk";
			file = new File(filePath.getAbsolutePath(), updateVersionName);
			// 判断是否存在文件
			if (file.exists()) {
				FileInputStream fileInputStream = new FileInputStream(file);
				currentSize = fileInputStream.available();
				fileInputStream.close();
				if (serverSize == currentSize) {
					isDownload = false;
				}
			}
			else {
				file.createNewFile();
			}
			byte[] readSize = new byte[256];
			String fileUrl = RequestHelper.getInstance().getRequestHeader()+"/"+versionInfo.getDownPath();
			if (isDownload) {
				HttpURLConnection httpConnection = (HttpURLConnection) new URL(fileUrl).openConnection();
				httpConnection.setRequestProperty("User-Agent", "NetFox");
				String property = "bytes=" + currentSize + "-";
				httpConnection.setRequestProperty("RANGE", property);
				inputStream = httpConnection.getInputStream();
				outputStream = new FileOutputStream(file, true);
			}
			while (isDownload) {
				if (!isPause) {
					int readLength = inputStream.read(readSize);
					if (readLength <= 0) {
						isDownload = false;
						break;
					}
					currentSize += readLength;
					int temp = (int) (((double) currentSize) / ((double) serverSize) * 100);
					if (temp > percent) {
						percent = temp;
						MessageProxy.sendMessage(UPDATE_DOWNLOAD_FILE_SIZE, percent);
					}
					outputStream.write(readSize, 0, readLength);
				}

			}
		}
		catch (Exception e) {
			if (file != null && file.exists()) {
				file.delete();
			}
			e.printStackTrace();
			//log("downApkFile error. " + e.toString());
		}
		finally {
			try {
				if (outputStream != null) {
					outputStream.close();
					outputStream = null;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			isDownload = false;
			MessageProxy.sendEmptyMessage(UPDATE_CLOSE_DIALOG);
			if (currentSize > serverSize) {
				file.delete();
			}
			else if (currentSize == serverSize) {
				install(ProjectApplication.getContext(), file);
			}
		}
	}

	/**
	 * 安装升级文件
	 * @param context
	 * @param file
	 */
	private static void install(Context context, File file) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		//判读版本是否在7.0以上
		if (Build.VERSION.SDK_INT >= 24){
			Uri apkUri = FileProvider.getUriForFile(context, "cn.com.hy.hn_drugcontrol.fileprovider", file);
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
		}else{
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		}

		context.startActivity(intent);
	}

	public static void log(String msg) {
		AppLogger.d("version", msg, false);
	}
}
