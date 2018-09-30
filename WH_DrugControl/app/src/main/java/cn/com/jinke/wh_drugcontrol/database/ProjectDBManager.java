package cn.com.jinke.wh_drugcontrol.database;

import org.xutils.DbManager;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.utils.AppLogger;

/**
 * Created by admin on 2017/10/24.
 */

public class ProjectDBManager {
    String TAG = ProjectDBManager.class.getSimpleName();
    static ProjectDBManager mInstance = new ProjectDBManager();
    DbManager.DaoConfig daoConfig;

    private ProjectDBManager() {
        daoConfig =
                new DbManager.DaoConfig()
                        .setDbName("gy_drug_db")
                        .setDbVersion(1)
                        .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                            @Override
                            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                                AppLogger.d(TAG,"onUpgrade oldVersion "+oldVersion +" newVersion "+newVersion);
                            }
                        });
    }

    public static ProjectDBManager getInstance() {
        if (mInstance == null) {
            synchronized (ProjectDBManager.class) {
                mInstance = new ProjectDBManager();
            }
        }
        return mInstance;
    }

    public DbManager getDbManager() {
        return x.getDb(daoConfig);
    }


}
