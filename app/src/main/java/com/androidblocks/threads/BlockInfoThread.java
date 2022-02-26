package com.androidblocks.threads;

import com.androidblocks.commom.GlobalVariable;
import com.androidblocks.dao.BlockInfoDAO;
import com.androidblocks.db.DaoFactory;

import android.content.Context;

/**
 * 日程线程
 *
 * @author lsy
 */
public class BlockInfoThread implements Runnable {

    private static final int THREAD_SLEEP = 100;

    private BlockInfoDAO blockInfoDAO;

    public BlockInfoThread(Context context) {
        this.blockInfoDAO = (BlockInfoDAO) DaoFactory.getDao(BlockInfoDAO.class, context);
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (GlobalVariable.getBlockInfoUpdate()) {
                    blockInfoDAO.batchUpdate(GlobalVariable.getAllBlockInfoList());
                    GlobalVariable.setBlockInfoUpdate(false);
                }
                Thread.sleep(THREAD_SLEEP);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
