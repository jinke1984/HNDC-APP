package cn.com.jinke.wh_drugcontrol.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.Locale;

import cn.com.jinke.wh_drugcontrol.BuildConfig;
import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectApplication;


/**
 * @author jinke
 * @date 2018/3/20
 * @description 拍照和相册的帮助类
 */
public class AlbumUtils {

    public static final int OPEN_CAMERA = 4;
    public static final int OPEN_ALBUM = 2;
    public static final int PHOTO_RESULT = 3;
    public static final int REQUEST_CODE_PICK_IMAGE = 5;
    public static final int REQUEST_CODE_IMAGE_CAPTURE = 6;
    public static String IMAGE_MASTER_CROP = "camera_result_temp";
    public static final String OUT_PATH = "OUT_PATH";
    public static final String FRONT_CAMERA = "FRONT_CAMERA";
    public static final String IMAGE_UNSPECIFIED = "image/*";

    private static String mPath = null;

    /**
     * 打开摄像头
     * @param activity
     */
    public static void openCamera(Activity activity) {
        openCamera(activity, null, false);
    }

    public static void openCamera(Fragment fragment) {
        openCamera(fragment, null, false);
    }

    public static void openCamera(Activity activity, String a_strPath, boolean front) {
        openCamera(activity, null, a_strPath, front);
    }

    public static void openCamera(Fragment fragment, String a_strPath, boolean front) {
        openCamera(null, fragment, a_strPath, front);
    }

    private static void openCamera(Activity a_oActivity, Fragment fragment, String a_strPath, boolean front) {
        if (a_oActivity == null && fragment == null) {
            return;
        }

        if (!checkSdcard()) {
            Toast.makeText(ProjectApplication.getContext(),
                    R.string.insertsdcard, Toast.LENGTH_LONG).show();
            return;
        }

        Context context;
        if (a_oActivity != null) {
            context = a_oActivity;
        } else {
            context = fragment.getContext();
        }

        Intent intent = getCameraIntent(context, a_strPath, front);

        File tempFile = new File(Environment.getExternalStorageDirectory().getPath()
                + File.separatorChar + IMAGE_MASTER_CROP);
        if (!tempFile.getParentFile().exists()){
            tempFile.getParentFile().mkdirs();
        }

        if (intent != null) {
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (a_oActivity != null) {
                if(currentapiVersion < Build.VERSION_CODES.N){
                    a_oActivity.startActivityForResult(intent, OPEN_CAMERA);
                }else{
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri photoUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", tempFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    a_oActivity.startActivityForResult(intent, OPEN_CAMERA);
                }
            } else {
                if(currentapiVersion < Build.VERSION_CODES.N){
                    fragment.startActivityForResult(intent, OPEN_CAMERA);
                }else{
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri photoUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", tempFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    fragment.startActivityForResult(intent, OPEN_CAMERA);
                }
            }
        } else {
            Toast.makeText(ProjectApplication.getContext(),
                    R.string.notSupport, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 检查Sdcard
     * @return
     */
    public static boolean checkSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private static final Intent getCameraIntent(Context context, String path, boolean front) {
        if (TextUtils.isEmpty(path)) {
            path = Environment.getExternalStorageDirectory().getPath()
                    + File.separatorChar + IMAGE_MASTER_CROP;
        }

        File file = new File(path);
        if (file.exists()){
            file.delete();
        }
        Intent intent = new Intent();

        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        if (front) {
            intent.putExtra("camerasensortype", 2);
        }

        if (isIntentExist(ProjectApplication.getContext(), intent)) {
            String manufacturer = Build.MANUFACTURER.toLowerCase(Locale.getDefault());
            if (VERSION.SDK_INT > 11 && manufacturer != null
                    && manufacturer.contains("samsung")) {
                intent = new Intent();
                intent.putExtra(ActivityCamera.OUT_PATH, path);
                intent.putExtra(ActivityCamera.FRONT_CAMERA, front);
                intent.setClass(context, ActivityCamera.class);
            }
        } else {
            intent = null;
        }

        return intent;
    }


    public static boolean isIntentExist(Context a_oContext, Intent a_oIntent) {
        boolean isExist = false;
        // PackageManager鏄敤浜庡垽鏂璉ntent杞悜鏄惁鎴愬姛鐨勭鐞嗗�?
        PackageManager packageManager = a_oContext.getPackageManager();
        List<ResolveInfo> componentList = packageManager.queryIntentActivities(
                a_oIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (componentList.size() > 0) {
            isExist = true;
        }
        return isExist;
    }

    /**
     * 打开相册
     */
    public static void openAlbum(Activity activity) {
        openAlbum(activity, null);
    }

    public static void openAlbum(Fragment fragment) {
        openAlbum(null, fragment);
    }

    @SuppressLint({"NewApi"})
    private static void openAlbum(Activity activity, Fragment fragment) {
        if (activity == null && fragment == null) {
            return;
        }

        boolean isKitKat = VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (!checkSdcard()) {
            Toast.makeText(ProjectApplication.getContext(),
                    R.string.insertsdcard, Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent();

        if (!isKitKat) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }

        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_UNSPECIFIED);

        if (isIntentExist(ProjectApplication.getContext(), intent)) {
            if (activity != null) {
                activity.startActivityForResult(intent, OPEN_ALBUM);
            } else {
                fragment.startActivityForResult(intent, OPEN_ALBUM);
            }
        } else {
            Toast.makeText(ProjectApplication.getContext(),
                    R.string.notSupport, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 裁剪
     *
     * @param a_path
     * @param a_oActivity
     */
    public static void startPhotoZoom(String a_path, Activity a_oActivity) {
        startPhotoZoom(a_path, a_oActivity, null);
    }

    public static void startPhotoZoom(String a_path, Fragment fragment) {
        startPhotoZoom(a_path, null, fragment);
    }

    private static void startPhotoZoom(String a_path, Activity activity, Fragment fragment) {
        if (activity == null && fragment == null) {
            return;
        }
        if (a_path == null || "".equals(a_path)) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(PictureCutActivity.PATH_STRING, a_path);
        if (activity != null) {
            intent.setClass(activity, PictureCutActivity.class);
            activity.startActivityForResult(intent, PHOTO_RESULT);
        } else {
            intent.setClass(fragment.getActivity(), PictureCutActivity.class);
            fragment.startActivityForResult(intent, PHOTO_RESULT);
        }
    }

    public static String getSdCardDir() {
        String path = "";
        if (checkSdcard()) {
            path = Environment.getExternalStorageDirectory().getPath();
        }

        return path;
    }

    @SuppressLint({"SdCardPath", "NewApi"})
    public static String getDirFromAlbumUri(Uri a_uri) {
        String path = null;
        Cursor l_Cursor = null;
        try {
            if (VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //TODO 系统版本4.4及4.4以上替换了媒体库的方法
                String wholeID = DocumentsContract.getDocumentId(a_uri);
                String id = wholeID.split(":")[1];
                String[] column = {MediaStore.Images.Media.DATA};
                String sel = MediaStore.Images.Media._ID + "=?";
                Cursor cursor = ProjectApplication.getContext()
                        .getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
                                sel, new String[]{id}, null);
                int columnIndex = cursor.getColumnIndex(column[0]);
                if (cursor.moveToFirst()) {
                    path = cursor.getString(columnIndex);
                }
                cursor.close();
            } else {
                l_Cursor = ProjectApplication.getContext().getContentResolver()
                        .query(a_uri, null, null, null, null);
//				l_Cursor.moveToFirst();
//				path = l_Cursor.getString(l_Cursor
//						.getColumnIndex(MediaStore.Images.Media.DATA));
                int column_index = l_Cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                l_Cursor.moveToFirst();
                path = l_Cursor.getString(column_index);
            }
        } catch (Exception e) {
            boolean isNewVersionSystem = false;
            int indexOf = -1;
            String imgPathString = a_uri.toString();
            Locale locale = Locale.getDefault();

            if (imgPathString.toLowerCase(locale).contains("/sdcard/")) {
                indexOf = imgPathString.toLowerCase(locale).indexOf("/sdcard/");
            } else if (imgPathString.toLowerCase(locale).contains("/sdcard0/")) {
                indexOf = imgPathString.toLowerCase(locale)
                        .indexOf("/sdcard0/");
            } else if (imgPathString.toLowerCase(locale).contains(
                    "/storage/emulated/0/")) {
                indexOf = imgPathString.toLowerCase(locale).indexOf(
                        "/storage/emulated/0/");
                isNewVersionSystem = true;
            }

            if (-1 != indexOf) {
                if (isNewVersionSystem) {
                    path = getSdCardDir() + "/"
                            + imgPathString.substring(indexOf + 20);
                } else {
                    path = getSdCardDir() + "/"
                            + imgPathString.substring(indexOf + 8);
                }
            } else {
                indexOf = imgPathString.toLowerCase(locale).indexOf("/data/");
                if (-1 != indexOf){
                    path = imgPathString.substring(indexOf);
                }
            }
        } finally {
            if (l_Cursor != null){
                l_Cursor.close();
            }
        }
        return path;
    }

    /**
     * 网页相册上传头像
     * @return
     */
    public static Intent choosePicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return Intent.createChooser(intent, null);
    }

    /**
     * 网页拍照上传头像
     * @return
     */
    public static Intent takeBigPicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File tempFile = new File(getNewPhotoPath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            if (!tempFile.getParentFile().exists()){
                tempFile.getParentFile().mkdirs();
            }
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Context context = ProjectApplication.getContext();
            Uri photoUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        }else{
            intent.putExtra(MediaStore.EXTRA_OUTPUT, newPictureUri(getNewPhotoPath()));
        }

        mPath = tempFile.getAbsolutePath();
        return intent;
    }

    private static String getNewPhotoPath() {
        return StorageUtil.PERSION_SAVE_PATH + "/" + System.currentTimeMillis() + ".jpg";
    }

    private static Uri newPictureUri(String path) {
        return Uri.fromFile(new File(path));
    }

    private static boolean isFileExists(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File f = new File(path);
        if (!f.exists()) {
            return false;
        }
        return true;
    }

    public static String retrievePath(Context context, Intent sourceIntent, Intent dataIntent) {
        String picPath = null;
        try {
            Uri uri;
            if (dataIntent != null) {
                uri = dataIntent.getData();
                if (uri != null) {
                    picPath = getPath(context, uri);
                }
                if (isFileExists(picPath)) {
                    return picPath;
                }

            }

            if (sourceIntent != null) {
                uri = sourceIntent.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
                if (uri != null) {
                    String scheme = uri.getScheme();
                    if (scheme != null && scheme.startsWith("file")) {
                        picPath = uri.getPath();
                    }else {
                        picPath = mPath;
                    }
                }

                if (!TextUtils.isEmpty(picPath)) {
                    File file = new File(picPath);
                    if (!file.exists() || !file.isFile()) {

                    }
                }
            }
            return picPath;
        } finally {

        }
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return String.format("%s/%s", Environment
                            .getExternalStorageDirectory().getPath(), split[1]);
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;

        try {
            cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}
                    , selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
