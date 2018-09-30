package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by xkr on 2017/10/31.
 */

public class AcceptRecoveryInfoEntity implements Serializable {
    private String stationname;
    private String indate;
    private String levelname;
    private String jztjcs;
    private String jytjcs;
    private String jyazcs;
    private String leader;
    private String leaderphone;
    private String id_card;
    private String name;
    private ArrayList<DrugQueryJYYYEntity> mJYYXList; //就业意向
    private ArrayList<DrugBFJZEntity> mBFList;        //帮扶救助
    private ArrayList<DrugSTJCEntity> mSTJCList;      //身体检查
    private ArrayList<DrugXLZXEntity> mXLZXList;      //心理咨询

}
