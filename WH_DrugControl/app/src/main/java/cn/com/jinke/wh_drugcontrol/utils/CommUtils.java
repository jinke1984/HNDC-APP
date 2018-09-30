package cn.com.jinke.wh_drugcontrol.utils;

import android.content.Context;
import android.text.TextUtils;
import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectApplication;
import cn.com.jinke.wh_drugcontrol.input.model.Nation;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;

/**
 * Created by jinke on 2017/7/28.
 */

public class CommUtils implements CodeConstants{

    private static CommUtils instance;

    private List<String> mNationList = new ArrayList<>();
    private List<String> mStzkList = new ArrayList<>();
    private List<String> mHyzkList = new ArrayList<>();

    private CommUtils() {

    }

    public static CommUtils getInstance() {
        if (instance == null) {
            synchronized (CommUtils.class) {
                if (instance == null) {
                    instance = new CommUtils();
                }
            }
        }
        return instance;
    }

    public void assetsNationToList(){

        InputStream nation = getClass().getResourceAsStream("/assets/nation.json");
        String s = convertStreamToString(nation);
        List<Nation> list = new Gson().fromJson(s, new TypeToken<List<Nation>>(){}.getType());
        for(Nation n : list){
            String text = n.getText();
            mNationList.add(text);
        }
    }

    public void assetsStzkToList(){
        InputStream stzk = getClass().getResourceAsStream("/assets/stzk.json");
        String s = convertStreamToString(stzk);
        List<Nation> list = new Gson().fromJson(s, new TypeToken<List<Nation>>(){}.getType());
        for(Nation n : list){
            String text = n.getText();
            mStzkList.add(text);
        }
    }

    public void assetsHyzkToList(){
        InputStream hyzk = getClass().getResourceAsStream("/assets/hyzk.json");
        String s = convertStreamToString(hyzk);
        List<Nation> list = new Gson().fromJson(s, new TypeToken<List<Nation>>(){}.getType());
        for(Nation n : list){
            String text = n.getText();
            mHyzkList.add(text);
        }
    }

    public List<Nation> assetsCommToList(String aPath){
        InputStream jtqk = getClass().getResourceAsStream(aPath);
        String s = convertStreamToString(jtqk);
        List<Nation> list = new Gson().fromJson(s, new TypeToken<List<Nation>>(){}.getType());
        return list;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public List<String> getNationList() {
        return mNationList;
    }

    public List<String> getStzkList() {
        return mStzkList;
    }

    public List<String> getHyzkList() {
        return mHyzkList;
    }

    public String getSexZw(String sex){

        Context context = ProjectApplication.getContext();
        String sexZW = null;
        if("1".equals(sex)){
            sexZW = context.getString(R.string.nan);
        }else if("0".equals(sex)){
            sexZW = context.getString(R.string.nv);
        }
        return sexZW;
    }
    public String getSexZw(int sex){

        Context context = ProjectApplication.getContext();
        String sexZW = null;
        switch (sex){
            case 1:
                sexZW = context.getString(R.string.nan);
                break;
            case 0:
                sexZW = context.getString(R.string.nv);
                break;
            default:
                break;
        }

        return sexZW;
    }

    public String getWhcdZW(String whcd){

        Context context = ProjectApplication.getContext();
        String whcdZW = null;
        if("1".equals(whcd)){
            whcdZW = context.getString(R.string.wm);
        }else if("2".equals(whcd)){
            whcdZW = context.getString(R.string.xx);
        }else if("3".equals(whcd)){
            whcdZW = context.getString(R.string.cz);
        }else if("4".equals(whcd)){
            whcdZW = context.getString(R.string.gz);
        }else if("5".equals(whcd)){
            whcdZW = context.getString(R.string.dxzk);
        }else if("6".equals(whcd)){
            whcdZW = context.getString(R.string.dxbkjys);
        }else{
            whcdZW = context.getString(R.string.wu);
        }
        return whcdZW;
    }

    public String getWU(String string){

        Context context = ProjectApplication.getContext();
        if(string == null){
            string = context.getString(R.string.wu);
        }else{
            if(string.equals("null") || TextUtils.isEmpty(string.trim())){
                string = context.getString(R.string.wu);
            }
        }
        return string;
    }

    public String getIsFace(String string){
        Context context = ProjectApplication.getContext();
        String s = "";
        if(string == null || "null".equals(string)){
            s = context.getString(R.string.fou);
        }else{
            s = context.getString(R.string.shi);
        }
        return s;
    }

//    public String getIsFace(String string){
//
//        Context context = ProjectApplication.getContext();
//        String s = "";
//        int faceToFace = Integer.valueOf(string);
//        switch (faceToFace){
//            case 0:
//                s = context.getString(R.string.shi);
//                break;
//            case 1:
//                s = context.getString(R.string.fou);
//                break;
//            default:
//                break;
//        }
//        return s;
//    }

    public String getUrineResult(String string){

        Context context = ProjectApplication.getContext();
        String s = "";
        int faceToFace = Integer.valueOf(string);
        switch (faceToFace){
            case 1:
                s = context.getString(R.string.yinx);
                break;
            case 2:
                s = context.getString(R.string.yanx);
                break;
            default:
                break;
        }
        return s;
    }

    public String getUrineType(String string){

        Context context = ProjectApplication.getContext();
        String s = "";
        int faceToFace = Integer.valueOf(string);
        switch (faceToFace){
            case 1:
                s = context.getString(R.string.dqnj);
                break;
            case 2:
                s = context.getString(R.string.tjnj);
                break;
            default:
                break;
        }
        return s;
    }

    public ArrayList<Nation> getYesNo10AssetsList(){
        return assetsToList("/assets/yesno10.json");
    }
    public ArrayList<Nation> getYesNo12AssetsList(){
        return assetsToList("/assets/yesno12.json");
    }
    public ArrayList<Nation> getSdfsAssetsList(){
        return assetsToList("/assets/sdfs.json");
    }
    public ArrayList<Nation> getClqkAssetsList(){
        return assetsToList("/assets/clqk.json"); 
    }
    public ArrayList<Nation> getWfcdAssetsList(){
        return assetsToList("/assets/wfcd.json"); 
    }
    public ArrayList<Nation> getWordParent(){return assetsToList("/assets/word.json");}
    public ArrayList<Nation> getWordChildren(){return assetsToList("/assets/word_detail.json");}
    
    public ArrayList<Nation> assetsToList(String aPath){
        InputStream stzk = getClass().getResourceAsStream(aPath);
        String s = convertStreamToString(stzk);
        ArrayList<Nation> list = new Gson().fromJson(s, new TypeToken<List<Nation>>(){}.getType());
        return list;
    }

    public String getWorkType(int type){
        Context context = ProjectApplication.getContext();
        String s = "";
        switch(type){
            case WORK_TALK:
                s = context.getString(R.string.dqft);
                break;
            case WORK_URINE:
                s = context.getString(R.string.dqlj);
                break;
            case WORK_REPORT:
                s = context.getString(R.string.dqbg);
                break;
            case WORK_EVALUATE:
                s = context.getString(R.string.dqpg);
                break;
            case WORK_DYNAMIC:
                s = context.getString(R.string.dtxxwh);
                break;
            default:
                break;
        }
        return s;
    }

    public ArrayList<Nation> getBreakReasons() {
        return assetsToList("/assets/zzyy.json");
    }

    public ArrayList<Nation> getQzcsAssets() {
        return assetsToList("/assets/qzcs.json");
    }

    public String getBreakReasonById(String id) {
        ArrayList<Nation> reasons = getBreakReasons();
        for (Nation nation : reasons) {
            if (nation.getId().equals(id)) {
                return nation.getText();
            }
        }

        return "";
    }

    public String getSystemModel() {
        return android.os.Build.MODEL;
    }


    public boolean isFace(String string){
        boolean result = false;
        if(string == null || "null".equals(string)){
            result = false;
        }else{
            result = true;
        }
        return result;
    }


    /**
     * 人脸比对返回值与阀值的比较
     * @param value1  人脸比对返回值
     * @param value2  固定阀值 0.80
     * @return  true 是  false 否
     */
    public boolean compareToValue(double value1, double value2){
        boolean result = false;
        BigDecimal b_value1 = new BigDecimal(value1);
        BigDecimal b_value2 = new BigDecimal(value2);
        int temp = b_value1.compareTo(b_value2);
        if(temp == -1){
            result = false;
        }else{
            result = true;
        }
        return result;
    }

    public Bitmap createQRBitmap(String content) {
        try {
            if (TextUtils.isEmpty(content)) {
                return null;
            }
            final int width = ViewHelper.dp2px(ProjectApplication.getContext(), 300);
            final int height = ViewHelper.dp2px(ProjectApplication.getContext(), 300);
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix martix = writer.encode(content, BarcodeFormat.QR_CODE, width, height);
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    }
                    else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        }
        catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public StringBuilder groupWebUrl(Persion persion, Document document, String path){
        StringBuilder sb = new StringBuilder(RequestHelper.getInstance().IN_IMAGE_URL+path);
        UserCard userCard = MasterManager.getInstance().getUserCard();
        if(userCard != null){
            sb.append("personId=").append(persion.getId())
                    .append("&docId=").append(document.getId())
                    .append("&userId=").append(userCard.getUserId())
                    .append("&communityID=").append(persion.getCommunityID());
        }
        AppLogger.d("url:"+sb.toString());
        return sb;
    }

    public StringBuilder createWebUrl(Persion persion, Document document, String path, boolean isBack, String leaveId){
        StringBuilder sb = new StringBuilder(RequestHelper.getInstance().IN_IMAGE_URL+path);
        UserCard userCard = MasterManager.getInstance().getUserCard();
        if(userCard != null){
            sb.append("personId=").append(persion.getId())
                    .append("&docId=").append(document.getId())
                    .append("&userId=").append(userCard.getUserId())
                    .append("&realName=").append(persion.getRealName());

            if(isBack){
                sb.append("&leaveId=").append(leaveId);
            }
        }
        AppLogger.d("url:"+sb.toString());
        return sb;
    }

    public StringBuilder createAddUrl(String path){
        StringBuilder sb = new StringBuilder(RequestHelper.getInstance().IN_IMAGE_URL+path);
        UserCard userCard = MasterManager.getInstance().getUserCard();
        if(userCard != null){
            sb.append("personId=null").append("&controlId=")
                    .append(userCard.getUserId()).append("&userType=1");
        }
        AppLogger.d("url:"+sb.toString());
        return sb;
    }

    /**
     1">户籍迁移
     2">现住址变更
     3">自愿康复
     4">务工就业
     5">就学
     * @param reasonCode
     * @return
     */
    public String changeReason(String reasonCode){
        Context context = ProjectApplication.getContext();
        String reason = "";
        if("1".equals(reasonCode)){
            reason = context.getString(R.string.hjqy);
        }else if("2".equals(reasonCode)){
            reason = context.getString(R.string.xzzbg);
        }else if("3".equals(reasonCode)){
            reason = context.getString(R.string.zykf);
        }else if("4".equals(reasonCode)){
            reason = context.getString(R.string.wgjy);
        }else if("5".equals(reasonCode)){
            reason = context.getString(R.string.jx);
        }else {
            reason = context.getString(R.string.wu);
        }
        return reason;
    }
}
