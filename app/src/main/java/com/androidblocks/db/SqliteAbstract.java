package com.androidblocks.db;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.androidblocks.utils.ClassUtils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public abstract class SqliteAbstract<T> extends SQLiteOpenHelper {

    private SQLiteDatabase writeDataBase = null;
    private SQLiteDatabase readDataBase = null;

    public SqliteAbstract(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        writeDataBase = this.getWritableDatabase();
        readDataBase = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getCreateSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * 查询数据
     * @param sql
     * @param params
     * @return
     */
    public List<T> query(String sql, Class<T> objClass, String... params) {
        List<T> resList = new ArrayList<>();
        List<JSONObject> results = getResults(sql, params);
        if (CollectionUtils.isNotEmpty(results)) {
            resList = results.stream().map(j -> JSON.parseObject(j.toString(), objClass)).collect(Collectors.toList());
        }
        return resList;
    }

    /**
     * 更新数据
     * @param obj
     * @throws Exception
     */
    public void merge(T obj) {
        Class objClass = obj.getClass();
        boolean hasPrimaryKey = ClassUtils.hasAnnotation(objClass, PrimaryKey.class);
        if (!hasPrimaryKey) {
            return;
        }
        Map<String, Object> columnMap = getColumnValueMap(obj);
        // replace into table () values ();
        if (columnMap.size() > 0) {
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("replace into ");
            sqlBuilder.append(getDbName());
            sqlBuilder.append(columnMap.keySet().stream().collect(Collectors.joining(" , ", " ( ", " ) ")));
            sqlBuilder.append(" values ");
            sqlBuilder.append(columnMap.keySet().stream().map(k -> " ? ").collect(Collectors.joining(" , ", " ( ", " ) ")));
            execute(sqlBuilder.toString(), columnMap.values().toArray(new Object[columnMap.size()]));
        }
    }

    public void execute(String sql, Object... params) {
        writeDataBase.execSQL(sql, params);
    }

    private List<JSONObject> getResults(String sql, String... params) {
        List<JSONObject> resList = new ArrayList<>();
        Cursor cursor = readDataBase.rawQuery(sql, params);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            JSONObject rowObject = new JSONObject();
            int totalColumn = cursor.getColumnCount();
            for(int i = 0 ; i < totalColumn ; i++ ) {
                if(cursor.getColumnName(i) != null) {
                    try {
                        if(cursor.getString(i) != null) {
                            rowObject.put(cursor.getColumnName(i),  cursor.getString(i));
                        } else {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            resList.add(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        return resList;
    }

    private Map<String, Object> getColumnValueMap(T obj) {
        Class objClass = obj.getClass();
        Map<String, Object> resMap = new HashMap<>();
        try {
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                boolean isPrimaryKey = field.isAnnotationPresent(PrimaryKey.class);
                if (isPrimaryKey) {
                    PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
                    String primaryKeyName = StringUtils.isEmpty(primaryKey.name()) ? field.getName() : primaryKey.name();
                    Object primaryKeyValue = objClass.getDeclaredMethod(ClassUtils.parGetName(field.getName())).invoke(obj);
                    resMap.put(primaryKeyName, primaryKeyValue);
                    continue;
                }
                boolean isColumnName = field.isAnnotationPresent(ColumnName.class);
                if (isColumnName) {
                    ColumnName columnName = field.getAnnotation(ColumnName.class);
                    String name = StringUtils.isEmpty(columnName.name()) ? field.getName() : columnName.name();
                    Object value = objClass.getDeclaredMethod(ClassUtils.parGetName(field.getName())).invoke(obj);
                    resMap.put(name, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
        return resMap;
    }

    private Map<String, Class> getColumnTypeMap(Class objClass) {
        Map<String, Class> resMap = new HashMap<>();
        try {
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                boolean isPrimaryKey = field.isAnnotationPresent(PrimaryKey.class);
                if (isPrimaryKey) {
                    PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
                    String primaryKeyName = StringUtils.isEmpty(primaryKey.name()) ? field.getName() : primaryKey.name();
                    resMap.put(primaryKeyName, field.getType());
                    continue;
                }
                boolean isColumnName = field.isAnnotationPresent(ColumnName.class);
                if (isColumnName) {
                    ColumnName columnName = field.getAnnotation(ColumnName.class);
                    String name = StringUtils.isEmpty(columnName.name()) ? field.getName() : columnName.name();
                    resMap.put(name, field.getType());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
        return resMap;
    }

    public abstract String getDbName();

    public abstract String getCreateSql();
}
