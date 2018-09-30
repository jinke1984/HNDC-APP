package cn.com.jinke.wh_drugcontrol.database.table;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import cn.com.jinke.wh_drugcontrol.database.BaseTable;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;


/**
 * Created by jinke on 2017/6/3.
 */

public class TableUser extends BaseTable {

    public TableUser(DbManager dbUtils){
        super(dbUtils);
    }

    public void saveUser(final UserCard aUser){
        submit(new Runnable() {
            @Override
            public void run() {
                try{
                    mDbUtils.saveOrUpdate(aUser);
                }catch (DbException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public List<UserCard> getUserList(){
        return submit(new Callable<List<UserCard>>() {
            @Override
            public List<UserCard> call() throws Exception {
                List<UserCard> list = mDbUtils.findAll(UserCard.class);
                if (list == null) {
                    list = new ArrayList<UserCard>();
                }
                return list;
            }
        });
    }

    public UserCard getUserCard(){
        return submit(new Callable<UserCard>() {
            @Override
            public UserCard call() throws Exception {
                List<UserCard> result = mDbUtils.findAll(UserCard.class);
                return result.get(0);
            }
        });
    }

    public void delete(){
        try{
            mDbUtils.delete(UserCard.class);
        }catch (DbException e){
            e.printStackTrace();
        }
    }
}
