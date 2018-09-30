package cn.com.jinke.wh_drugcontrol.database;

import org.xutils.ex.DbException;

import java.io.IOException;

import cn.com.jinke.wh_drugcontrol.message.model.MessageEntity;
import cn.com.jinke.wh_drugcontrol.utils.ShareConstant;

/**
 * @Author: jinke
 * @Date: 2016年3月30日 下午18:03:25
 * @Description: 数据库管理器
 */
public class DbManager implements ShareConstant {

    private final static String TAG = "database";

    /**
     * 用户库数据版本号
     */
    public static final int DB_USER_VERSION = 1;

    /**
     * 吸毒人员缓存库版本号
     */
    public static final int DB_PERSON_VERSION = 1;

    /**
     * 消息数据库版本号
     */
    public static final int DB_MESSAGE_VERSION = 2;

    /**
     *  用户库
     */
    public static final String DATABASE_NAME_USER = "elecheck_user.sqlite";

    /**
     *  吸毒人员缓存库
     */
    public static final String DATABASE_NAME_PERSON = "elecheck_person.sqlite";

    /**
     *  消息库
     */
    public static final String DATABASE_NAME_MESSAGE = "elecheck_message.sqlite";
    public static final String DATABASE_NAME_MYMESSAGE = "elecheck_mymessage.sqlite";
    public static final String DATABASE_NAME_CHATDRUGS = "elecheck_chatdrugs.sqlite";

    private static UserDb sUserDb;
    private static PersonDb sPersonDb;
    private static MessageDb sMessageDb;
    private DbManager() {
    }

    /**
     * 初始化数据库
     */
    public static void initDatabase() {
        sUserDb = new UserDb(DATABASE_NAME_USER, DB_USER_VERSION, sUserListener);
        sPersonDb = new PersonDb(DATABASE_NAME_PERSON, DB_PERSON_VERSION, sPersonListener);
        sMessageDb = new MessageDb(DATABASE_NAME_MESSAGE, DB_MESSAGE_VERSION, sMessageListener);
    }

    public static UserDb getUserDb() {
        return sUserDb;
    }

    public static PersonDb getPersonDb() {
        return sPersonDb;
    }

    public static MessageDb getMessageDb() {
        return sMessageDb;
    }

    public static void closeUserDb() {
        if (sUserDb != null) {
            try {
                sUserDb.getDbUtils().close();
            } catch (IOException e) {
                //log("close user db error. " + e.toString());
                e.printStackTrace();
            }
            sUserDb = null;
        }
    }

    public static void closePersonDb() {
        if (sPersonDb != null) {
            try {
                sPersonDb.getDbUtils().close();
            } catch (IOException e) {
                //log("close user db error. " + e.toString());
                e.printStackTrace();
            }
            sPersonDb = null;
        }
    }

    public static void closeMessageDb() {
        if (sMessageDb != null) {
            try {
                sMessageDb.getDbUtils().close();
            } catch (IOException e) {
                //log("close user db error. " + e.toString());
                e.printStackTrace();
            }
            sMessageDb = null;
        }
    }

    public static void clearCache() {
        sUserDb.clearCache();
        sPersonDb.clearCache();
        sMessageDb.clearCache();
    }

    /**
     * 数据库的升级
     */
    private static org.xutils.DbManager.DbUpgradeListener sUserListener = new org.xutils.DbManager.DbUpgradeListener() {

        @Override
        public void onUpgrade(org.xutils.DbManager dbManager, int oldVersion, int newVersion) {

        }
    };

    private static org.xutils.DbManager.DbUpgradeListener sPersonListener = new org.xutils.DbManager.DbUpgradeListener() {
        @Override
        public void onUpgrade(org.xutils.DbManager db, int oldVersion, int newVersion) {

        }
    };

    private static org.xutils.DbManager.DbUpgradeListener sMessageListener = new org.xutils.DbManager.DbUpgradeListener() {
        @Override
        public void onUpgrade(org.xutils.DbManager db, int oldVersion, int newVersion) {

            //学习园地添加appNewsList这个字段 add by jinke 2018-4-17
            if(oldVersion <  2){
                try{
                    db.addColumn(MessageEntity.class, "appNewsList");
                }catch(DbException e){
                    e.printStackTrace();
                }
            }
        }
    };

    private static org.xutils.DbManager.DbUpgradeListener sMyMessageListener = new org.xutils.DbManager.DbUpgradeListener() {
        @Override
        public void onUpgrade(org.xutils.DbManager db, int oldVersion, int newVersion) {

        }
    };

    private static org.xutils.DbManager.DbUpgradeListener sChatDrugListener = new org.xutils.DbManager.DbUpgradeListener() {
        @Override
        public void onUpgrade(org.xutils.DbManager db, int oldVersion, int newVersion) {

        }
    };

}
