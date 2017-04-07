package com.example.zuo.forestlocation.tool;

import android.content.Context;
import android.text.TextUtils;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.weiying.sdk.login.LoginManager;
import com.weiying.sdk.login.WYUserInfo;
import com.wesai.ticket.data.beans.MessageBean;
import com.wesai.ticket.data.beans.PeopleBean;
import com.wesai.ticket.net.IAPIService;
import com.wesai.ticket.show.model.History;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于处理跟数据库相关操作
 * Created by zuo on 2016/8/1.
 */
public class SqLite_DB_Utile {

    Context context;
    public static SqLite_DB_Utile db_utile = null;
    DbUtils historyDB;

    public SqLite_DB_Utile(Context context) {
        historyDB = DbUtils.create(context, "HistoryDB.db", 1, null);//创建数据库表
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


    /**
     * 保存用户搜索的关键字
     *
     * @param searchEdiText
     */
    public void saveSearchKeyWord(String searchEdiText) {
        try {
            List<History> mHistory = null;
            try {
                mHistory = historyDB.findAll(Selector.from(History.class).orderBy(History.ID, true).limit(15));
            } catch (DbException e) {
                e.printStackTrace();
            }

            boolean isSave = true;
            if (mHistory != null && mHistory.size() > 0) {
                for (History history : mHistory) {
                    if (history.getName().equals(searchEdiText)) {
                        isSave = false;
                    }
                }
            }
            if (isSave) {
                History hs = new History();
                hs.setName(searchEdiText);
                try {
                    historyDB.save(hs);
                } catch (DbException e) {
                    if (IAPIService.isDebug) e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**--------------------------------------------------------------------------消息信息-------------------------------------------------------------------------------------- */

    /**
     * 保存消息信息
     *
     * @param messageBean
     */
    public void saveMessage(MessageBean messageBean) {

        if (messageBean != null) {
            try {
                historyDB.save(messageBean);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 修改消息信息
     *
     * @param messageBean
     */
    public void updataMessage(MessageBean messageBean) {
        if (messageBean != null && !TextUtils.isEmpty(messageBean.getId())) {
            try {
                historyDB.update(messageBean, WhereBuilder.b("id", "=", messageBean.getId()), "status");

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
    public List getAllMessage(int page, int size) {
        try {
            List<MessageBean> messageBeens = historyDB.findAll(Selector.from(MessageBean.class).limit(size).offset(page * size));

            return messageBeens;
        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 查询消息已查看看在下未查看在上按时间排序
     * .where(WhereBuilder.b("userId", "=", userInfo.getUserId()))
     *
     * @return
     */
    public List getAllMessage() {
        try {
            WYUserInfo userInfo = LoginManager.getInstance().getUserInfo();
            if (userInfo != null && !TextUtils.isEmpty(userInfo.getUserId())) {
                List<MessageBean> baseMessageBeens = historyDB.findAll(Selector.from(MessageBean.class).where(WhereBuilder.b("userId", "=", userInfo.getUserId()).or("userId", "=", "")).orderBy("status,time", true));
                return baseMessageBeens;
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 删除对应消息信息
     *
     * @param messageBean
     */
    public void deleteMessage(MessageBean messageBean) {
        try {
            if (messageBean != null && !TextUtils.isEmpty(messageBean.getId())) {
                historyDB.delete(MessageBean.class, WhereBuilder.b("id", "=", messageBean.getId()));
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 当前是否有未读消息
     * .where(WhereBuilder.b("userId", "=", userInfo.getUserId()))
     *
     * @return
     */
    public boolean getIsNoMessage() {

        try {
            WYUserInfo userInfo = LoginManager.getInstance().getUserInfo();
            if (userInfo != null && !TextUtils.isEmpty(userInfo.getUserId())) {
                List<MessageBean> baseMessageBeens = historyDB.findAll(Selector.from(MessageBean.class).where(WhereBuilder.b("userId", "=", userInfo.getUserId()).or("userId", "=", "")).orderBy("status,time", true));
                if (baseMessageBeens != null && baseMessageBeens.size() > 0) {
                    for (int i = 0; i < baseMessageBeens.size(); i++) {
                        if (baseMessageBeens.get(i).getStatus() == 0) {
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                List<MessageBean> baseMessageBeens = historyDB.findAll(Selector.from(MessageBean.class).where(WhereBuilder.b("userId", "=", "")).orderBy("status,time", true));
                if (baseMessageBeens != null && baseMessageBeens.size() > 0) {
                    for (int i = 0; i < baseMessageBeens.size(); i++) {
                        if (baseMessageBeens.get(i).getStatus() == 0) {
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            }


        } catch (DbException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 根据id查询数据
     */

    public MessageBean getMessageById(String messageId) {
        try {
            WYUserInfo userInfo = LoginManager.getInstance().getUserInfo();
            MessageBean messageBean = null;
            if (userInfo != null && !TextUtils.isEmpty(userInfo.getUserId())) {
                List<MessageBean> baseMessageBeens = historyDB.findAll(Selector.from(MessageBean.class).where(WhereBuilder.b("id", "=", messageId)));
                if (baseMessageBeens != null && baseMessageBeens.size() > 0) {
                    messageBean = baseMessageBeens.get(0);
                    return messageBean;
                }

            }

        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**---------------------------------------------------------------------------实名信息---------------------------------------------------------------------- **/

    /**
     * 添加实名信息
     *
     * @param peopleBean
     */
    public void savePeople(PeopleBean peopleBean) {
        if (peopleBean != null) {
            try {
                historyDB.save(encryptObject(peopleBean));
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }


//

    public PeopleBean encryptObject(PeopleBean bean) {

        try {
            PeopleBean tem = (PeopleBean) bean.clone();
            tem.setName(tem.setEncrypt(tem.getName()));
            tem.setCardId(tem.setEncrypt(tem.getCardId()));
            return tem;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;

    }

    public PeopleBean decryptObject(PeopleBean bean) {
        try {
            PeopleBean tem = (PeopleBean) bean.clone();
            tem.setName(tem.setDecrypt(tem.getName()));
            tem.setCardId(tem.setDecrypt(tem.getCardId()));
            return tem;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;

    }

    /**
     * 修改实名信息
     *
     * @param peopleBean
     */
    public void updataPeople(PeopleBean peopleBean) {
        if (peopleBean != null && !TextUtils.isEmpty(peopleBean.getId())) {
            try {
                PeopleBean tem = encryptObject(peopleBean);
                historyDB.update(tem, WhereBuilder.b("id", "=", tem.getId()), "name", "cardId", "isUser");
            } catch (DbException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 获取所有实名信息
     *
     * @return
     */
    public List getAllPeople() {
        try {
            WYUserInfo userInfo = LoginManager.getInstance().getUserInfo();
            if (userInfo != null && !TextUtils.isEmpty(userInfo.getUserId())) {
                List<PeopleBean> baseMessageBeens = historyDB.findAll(Selector.from(PeopleBean.class).where(WhereBuilder.b("userId", "=", userInfo.getUserId())).orderBy("isUser DESC,addDate", true));

                if (baseMessageBeens != null) {
                    for (PeopleBean tem : baseMessageBeens) {
                        tem.setName(tem.setDecrypt(tem.getName()));
                        tem.setCardId(tem.setDecrypt(tem.getCardId()));
                    }
                }

                return baseMessageBeens;
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 删除
     *
     * @param peopleBean
     */
    public void deletePeople(PeopleBean peopleBean) {
        try {
            if (peopleBean != null && !TextUtils.isEmpty(peopleBean.getId())) {
                historyDB.delete(PeopleBean.class, WhereBuilder.b("id", "=", peopleBean.getId()));
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断是否有该信息
     *
     * @return
     */
    public boolean getIsHasPeople(PeopleBean peopleBean) {
        try {
            WYUserInfo userInfo = LoginManager.getInstance().getUserInfo();
            if (peopleBean != null && userInfo != null && !TextUtils.isEmpty(userInfo.getUserId())) {
                List<PeopleBean> baseMessageBeens = historyDB.findAll(Selector.from(PeopleBean.class).where(WhereBuilder.b("userId", "=", userInfo.getUserId())).and(WhereBuilder.b("cardId", "=", peopleBean.setEncrypt(peopleBean.getCardId()))));
                if (baseMessageBeens != null && baseMessageBeens.size() > 0) {
                    return true;
                }
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取默认用户
     *
     * @return
     */
    public PeopleBean getDefaultPeople() {
        try {
            WYUserInfo userInfo = LoginManager.getInstance().getUserInfo();
            if (userInfo != null && !TextUtils.isEmpty(userInfo.getUserId())) {
                List<PeopleBean> baseMessageBeen = historyDB.findAll(Selector.from(PeopleBean.class).where(WhereBuilder.b("userId", "=", userInfo.getUserId())).and(WhereBuilder.b("isUser", "=", true)));
                if (baseMessageBeen != null && baseMessageBeen.size() > 0) {
                    PeopleBean tem = baseMessageBeen.get(0);
                    tem.setName(tem.setDecrypt(tem.getName()));
                    tem.setCardId(tem.setDecrypt(tem.getCardId()));
                    return tem;
                }
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;

    }


}
