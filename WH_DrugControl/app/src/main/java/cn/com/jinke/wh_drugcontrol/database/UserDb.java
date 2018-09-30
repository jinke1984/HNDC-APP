package cn.com.jinke.wh_drugcontrol.database;

import org.xutils.DbManager;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.database.table.TableUser;

/**
 * Created by jinke on 2017/5/2.
 */

public class UserDb extends BaseDb {

    private TableUser mTableUser;

    /**
     * 用户库构造函数
     *
     * @param dbName
     * @param dbVersion
     * @param listener
     */
    public UserDb(String dbName, int dbVersion, DbManager.DbUpgradeListener listener) {
        super(dbName, dbVersion, listener);
    }

    @Override
    protected List<BaseTable> getTables() {
        List<BaseTable> list = new ArrayList<>();
        mTableUser = new TableUser(getDbUtils());
        list.add(mTableUser);
        return list;
    }

    @Override
    protected void clearCache() {
    }

    public TableUser getTableUser() {
        return mTableUser;
    }

}
