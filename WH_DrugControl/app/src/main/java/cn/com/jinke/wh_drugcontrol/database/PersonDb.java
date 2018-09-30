package cn.com.jinke.wh_drugcontrol.database;

import org.xutils.DbManager;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.database.table.TableCachePerson;

/**
 * Created by jinke on 2017/5/2.
 */

public class PersonDb extends BaseDb {

    private TableCachePerson mTablePerson;

    /**
     * 用户库构造函数
     *
     * @param dbName
     * @param dbVersion
     * @param listener
     */
    public PersonDb(String dbName, int dbVersion, DbManager.DbUpgradeListener listener) {
        super(dbName, dbVersion, listener);
    }

    @Override
    protected List<BaseTable> getTables() {
        List<BaseTable> list = new ArrayList<>();
        mTablePerson = new TableCachePerson(getDbUtils());
        list.add(mTablePerson);
        return list;
    }

    @Override
    protected void clearCache() {
    }

    public TableCachePerson getTableCachePerson() {
        return mTablePerson;
    }

}
