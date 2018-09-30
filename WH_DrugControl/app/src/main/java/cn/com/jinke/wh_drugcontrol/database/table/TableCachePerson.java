package cn.com.jinke.wh_drugcontrol.database.table;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.List;
import java.util.concurrent.Callable;

import cn.com.jinke.wh_drugcontrol.database.BaseTable;
import cn.com.jinke.wh_drugcontrol.input.model.CachePerson;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;


/**
 * TableCachePerson
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/14
 */

public class TableCachePerson extends BaseTable implements CodeConstants {

    public TableCachePerson(DbManager dbUtils) {
        super(dbUtils);
    }

    public boolean save(final CachePerson person) {
        return person.getCacheState() == COMPLETED_DOING && submit(person);
    }

    public void delete(final CachePerson person) {
        try {
            mDbUtils.delete(CachePerson.class, WhereBuilder.b("identityCard", "=", person.getIdentityCard()));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public List<CachePerson> getCachePersonList() {
        return submit(new Callable<List<CachePerson>>() {
            @Override
            public List<CachePerson> call() throws Exception {
                return mDbUtils.findAll(CachePerson.class);
            }
        });
    }

}
