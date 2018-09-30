package cn.com.jinke.wh_drugcontrol.me.manager;

import cn.com.jinke.wh_drugcontrol.database.DbManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.message.manager.MessageManager;

/**
 * Created by jinke on 2017/7/20.
 */

public class MasterManager {

    private UserCard mUserCard;

    private static MasterManager instance;

    private MasterManager(){

    }

    public static MasterManager getInstance(){
        if (instance == null) {
            synchronized (MasterManager.class) {
                if (instance == null) {
                    instance = new MasterManager();
                }
            }
        }
        return instance;
    }

    public UserCard getUserCard() {
        return mUserCard;
    }

    public void setUserCard(UserCard mUserCard) {
        this.mUserCard = mUserCard;
    }

    public void init(UserCard aUserCard){
        if(aUserCard != null){
            setUserCard(aUserCard);
            DbManager.getUserDb().getTableUser().saveUser(aUserCard);
            MessageManager.init(); //确保登录才初始化消息数据到内存
        }
    }

    //删除 上次登录的用户信息
    public void delete(){
        UserCard old = getUserCard();
        if(old != null){
            DbManager.getUserDb().getTableUser().delete();
            setUserCard(null);
        }
    }

    public void init(){
        UserCard userCard = DbManager.getUserDb().getTableUser().getUserCard();
        if(userCard != null){
            setUserCard(userCard);
        }
    }

    public void clear(){
        if(getUserCard() != null){
            setUserCard(null);
        }
    }
}
