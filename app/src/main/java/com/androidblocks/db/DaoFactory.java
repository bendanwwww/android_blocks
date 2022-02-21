package com.androidblocks.db;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库对象工厂
 *
 * @author lsy
 */
public class DaoFactory {

    private static final Map<Class, SqliteAbstract> DAO_MAP = new HashMap<>();

    public synchronized static SqliteAbstract getDao(Class<? extends SqliteAbstract> daoClass, Context context) {
        if (DAO_MAP.containsKey(daoClass)) {
            return DAO_MAP.get(daoClass);
        }
        try {
            Constructor constructor = daoClass.getDeclaredConstructor(Context.class, SQLiteDatabase.CursorFactory.class);
            SqliteAbstract daoObj = (SqliteAbstract) constructor.newInstance(context, null);
            DAO_MAP.put(daoClass, daoObj);
            return daoObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
