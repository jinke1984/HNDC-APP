package cn.com.jinke.wh_drugcontrol.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.ex.DbException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import cn.com.jinke.wh_drugcontrol.thread.Dispatcher;
import cn.com.jinke.wh_drugcontrol.thread.SingleThreadPool;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;

/**
 * @Author: jinke
 * @Date: 2016年3月30日 下午18:03:25
 * @Description: 数据表基类
 */
public abstract class BaseTable {

    protected final String TAG = getClassName();
    private String mClassName;
    protected DbManager mDbUtils;
    protected SQLiteDatabase mSQLiteDatabase;

    public BaseTable(DbManager dbUtils) {
        this.mDbUtils = dbUtils;
        this.mSQLiteDatabase = mDbUtils.getDatabase();
    }

    protected String getClassName() {
        if (TextUtils.isEmpty(mClassName)) {
            mClassName = getClass().getSimpleName();
        }
        return mClassName;
    }

    protected void setSQLiteDatabase(SQLiteDatabase db) {
        mSQLiteDatabase = db;
    }

    public Cursor rawQuery(String sql) {
        return mSQLiteDatabase.rawQuery(sql, null);
    }

    protected <T> T submit(Callable<T> callable) {
        try {
            Future<T> futrue = SingleThreadPool.getThreadPool().submit(callable);
            return futrue.get();
        } catch (Exception e) {
            //log(e.toString());
            e.printStackTrace();
        }
        return null;
    }

    protected boolean submit(Runnable runnable) {
        boolean result = false;
        try {
            Dispatcher.runOnSingleThread(runnable);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            //log(e.toString());
        }
        return result;
    }

    protected boolean submit(final Object dbEntity) {
        return submit(new Runnable() {
            @Override
            public void run() {
                try {
                    mDbUtils.saveOrUpdate(dbEntity);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void createTable() {
    }

    /**
     * 插入表
     *
     * @param entity
     * @param
     * @return
     */
    public boolean insert(Object entity) {
        try {
            mDbUtils.save(entity);
        } catch (DbException e) {
            AppLogger.e(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 根据查询sql 获取列表数据
     * @param sql
     * @param clazz
     * @return
     */
    protected List<?> getListWithQuerySql(String sql, Class<?> clazz){
        List result = new ArrayList<>();
        try {
            Cursor cursor =  mDbUtils.execQuery(sql);
            while (cursor.moveToNext()) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    Object jsonValue = "";
                    Column param = field.getAnnotation(Column.class);
                    if (param == null || TextUtils.isEmpty(param.name())) {
                        continue;
                    }
                    if (field.getType() == Integer.class) {
                        jsonValue = cursor.getInt(cursor.getColumnIndex(param.name()));
                        field.setInt(field.getName(),(Integer)jsonValue);
                    } else if (field.getType() == String.class) {
                        jsonValue = cursor.getString(cursor.getColumnIndex(param.name()));
                        field.set(field.getName(),jsonValue);
                    } else if (field.getType() == Long.class) {
                        jsonValue = cursor.getLong(cursor.getColumnIndex(param.name()));
                        field.setLong(field.getName(),(Long) jsonValue);
                    } else if (field.getType() == Float.class) {
                        jsonValue = cursor.getFloat(cursor.getColumnIndex(param.name()));
                        field.setFloat(field.getName(),(Float) jsonValue);
                    }
                }
                Constructor<?> constructor = clazz.getConstructor();
                result.add(constructor.newInstance());
            }
            return result;
        } catch (Exception e) {
            AppLogger.e(e.getMessage());
            return null;
        }
    }
}
