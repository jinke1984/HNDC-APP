package cn.com.jinke.wh_drugcontrol.database;

import org.xutils.DbManager;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.database.table.TableMessage;

/**
 * Created by jinke on 2017/9/26.
 */

public class MessageDb extends BaseDb{

    private TableMessage mTableMessage;

    /**
     * 用户库构造函数
     *
     * @param dbName
     * @param dbVersion
     * @param listener
     */
    public MessageDb(String dbName, int dbVersion, DbManager.DbUpgradeListener listener) {
        super(dbName, dbVersion, listener);
    }

    @Override
    protected List<BaseTable> getTables() {
        List<BaseTable> list = new ArrayList<>();
        mTableMessage = new TableMessage(getDbUtils());
        list.add(mTableMessage);
        return list;
    }

    @Override
    protected void clearCache() {
    }

    public TableMessage getTableMessage() {
        return mTableMessage;
    }
}
