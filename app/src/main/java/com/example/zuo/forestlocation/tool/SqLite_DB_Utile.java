package com.example.zuo.forestlocation.tool;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.zuo.forestlocation.bean.LocationBean;


import org.xutils.DbManager;
import org.xutils.db.Selector;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于处理跟数据库相关操作
 * Created by zuo on 2016/8/1.
 */
public class SqLite_DB_Utile {

    Context context;
    public static SqLite_DB_Utile db_utile = null;
    DbManager historyDB;

    public SqLite_DB_Utile(Context context) {

        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("lyj_db")//创建数据库的名称
                .setDbVersion(1)//数据库版本号
                //设置数据库打开的监听
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        //开启数据库支持多线程操作，提升性能，对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                //设置数据库更新的监听
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    }
                })
                //设置表创建的监听
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager dbManager, TableEntity<?> tableEntity) {
                        Log.i("JAVA", "onTableCreated：" + tableEntity.getName());
                    }

                });
        //设置是否允许事务，默认true
        //.setAllowTransaction(true)

        historyDB = x.getDb(daoConfig);
        this.context = context;

    }

    /**
     * 初始化数据库操作类
     *
     * @param context
     * @return
     */
    public static SqLite_DB_Utile getInit(Context context) {
        if (db_utile == null) {
            db_utile = new SqLite_DB_Utile(context);
            return db_utile;
        } else {
            return db_utile;
        }
    }


    /**--------------------------------------------------------------------------消息信息-------------------------------------------------------------------------------------- */

    /**
     * 保存消息信息
     *
     * @param locationBean
     */
    public void saveLocationData(LocationBean locationBean) {

        if (locationBean != null) {
            try {
                historyDB.save(locationBean);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 修改消息信息
     *
     * @param locationBean
     */
    public void updataLocationData(LocationBean locationBean) {
        if (locationBean != null && !TextUtils.isEmpty(locationBean.getId())) {
            try {

                historyDB.saveOrUpdate(locationBean);

            } catch (DbException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 获取所有消息信息
     *
     * @return
     */
    public List getAllLocationData() {
        try {
            List<LocationBean> messageBeens = historyDB.findAll(LocationBean.class);

            return messageBeens;
        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;
    }




    /**
     * 删除对应消息信息
     *
     * @param locationBean
     */
    public void deleteLocationData(LocationBean locationBean) {
        try {
            if (locationBean != null && !TextUtils.isEmpty(locationBean.getId())) {
                historyDB.delete(LocationBean.class, WhereBuilder.b("id", "=", locationBean.getId()));
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }


}
