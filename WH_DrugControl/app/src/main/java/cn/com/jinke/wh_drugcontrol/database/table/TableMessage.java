package cn.com.jinke.wh_drugcontrol.database.table;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import cn.com.jinke.wh_drugcontrol.database.BaseTable;
import cn.com.jinke.wh_drugcontrol.message.model.MessageEntity;

/**
 * Created by jinke on 2017/9/26.
 */

public class TableMessage extends BaseTable {

    public TableMessage(DbManager dbUtils){
        super(dbUtils);
    }

    public void saveMessage(final MessageEntity messageEntity){
        submit(new Runnable() {
            @Override
            public void run() {
                try{
                    mDbUtils.saveOrUpdate(messageEntity);
                }catch (DbException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void saveMessage(final ArrayList<MessageEntity> list){
        submit(new Runnable() {
            @Override
            public void run() {
                try{
                    mDbUtils.saveOrUpdate(list);
                }catch (DbException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public List<MessageEntity> getMessageList(){
        return submit(new Callable<List<MessageEntity>>() {
            @Override
            public List<MessageEntity> call() throws Exception {
                List<MessageEntity> list = mDbUtils.findAll(MessageEntity.class);
                if (list == null) {
                    list = new ArrayList<MessageEntity>();
                }
                return list;
            }
        });
    }

    public MessageEntity getMessage(){
        return submit(new Callable<MessageEntity>() {
            @Override
            public MessageEntity call() throws Exception {
                List<MessageEntity> result = mDbUtils.findAll(MessageEntity.class);
                return result.get(0);
            }
        });
    }

    public void delete(){
        try{
            mDbUtils.delete(MessageEntity.class);
        }catch (DbException e){
            e.printStackTrace();
        }
    }
}
