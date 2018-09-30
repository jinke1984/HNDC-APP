package cn.com.jinke.wh_drugcontrol.message.model;

import java.io.Serializable;

/**
 * @author jinke
 * @date 2018/6/5
 * @description 历史消息推送实体
 */
public class HistoryMessage implements Serializable {

    /**
     * id : cbf81d6e533e474ca17b5a008f4f24f8
     * userId : 168a1d055e4248b2aaf3141152a2af6e
     * sendNo : 190e35f7e04f9923d60
     * type : 16
     * content : <p>测试司法信息推送带图12测试司法信息推送带图12测试司法信息推送带图12测试司法信息推送带图12测试司法信息推送带图12测试司法信息推送带图12测试司法信息推送带图12测试司法信息推送带图12测试司法信息推送带图12</p>
     * createTime : 2018-05-10 17:59:56
     * personId : 79690a8addca4ebf9bde4d157c422e05
     * sendName : 崔圣
     * sex :
     * appNewsList : [{'title':'测试司法信息推送带图12','link':'queryPushInfo/main.html?command=queryPushInfo&id=3b3248f68f5f44ff81db5bc9309c14be','img_url':'/upload/natel/20180510/6d4ee16de05343619ec0e8a2e8ce2898.jpg'}]
     */

    private String id;
    private String userId;
    private String sendNo;
    private String type;
    private String content;
    private String createTime;
    private String personId;
    private String sendName;
    private String sex;
    private String appNewsList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSendNo() {
        return sendNo;
    }

    public void setSendNo(String sendNo) {
        this.sendNo = sendNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAppNewsList() {
        return appNewsList;
    }

    public void setAppNewsList(String appNewsList) {
        this.appNewsList = appNewsList;
    }
}
