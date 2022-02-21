package com.androidblocks.dao;

import com.androidblocks.db.SqliteAbstract;
import com.androidblocks.vo.BlockInfo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;

public class BlockInfoDAO extends SqliteAbstract<BlockInfo> {

    public BlockInfoDAO(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public String getDbName() {
        return BlockInfo.class.getName();
    }

    @Override
    public String getCreateSql() {
        return "create table " + getDbName()
                + " ( "
                + " id varchar primary key not null , "
                + " effectType varchar(50) , "
                + " `text` varchar(100) , "
                + " info varchar(500) , "
                + " color int , "
                + " startHour varchar , "
                + " endHour varchar , "
                + " startTime bigint , "
                + " endTime bigint , "
                + " proportions float , "
                + " idle varchar(10)"
                + " );";
    }
}
