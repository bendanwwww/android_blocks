package com.androidblocks.dao;

import java.util.List;

import com.androidblocks.db.SqliteAbstract;
import com.androidblocks.vo.BlockInfo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;

public class BlockInfoDAO extends SqliteAbstract<BlockInfo> {

    private static final String DB_FILE_NAME = "block_info";
    private static final int DB_VERSION = 1;

    public BlockInfoDAO(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, DB_FILE_NAME, factory, DB_VERSION);
    }

    public List<BlockInfo> queryAll() {
        String sql = "select * from " + getDbName() + " where delete_state = 0;";
        return query(sql, BlockInfo.class);
    }

    public void batchUpdate(List<BlockInfo> blockInfoList) {
        for (BlockInfo blockInfo : blockInfoList) {
            merge(blockInfo);
        }
    }

    @Override
    public String getDbName() {
        return BlockInfo.class.getSimpleName();
    }

    @Override
    public String getCreateSql() {
        return "create table " + getDbName()
                + " ( "
                + " id varchar primary key not null , "
                + " effect_type varchar(50) , "
                + " `text` varchar(100) , "
                + " info varchar(500) , "
                + " color int , "
                + " start_hour varchar , "
                + " end_hour varchar , "
                + " start_time bigint , "
                + " end_time bigint , "
                + " proportions float , "
                + " idle varchar(10) , "
                + " delete_state int"
                + " );";
    }
}
