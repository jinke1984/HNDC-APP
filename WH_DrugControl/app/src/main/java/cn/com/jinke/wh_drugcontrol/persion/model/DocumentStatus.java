package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * DocumentStatus
 * Created by jinke on 2017/8/28.
 */

public class DocumentStatus implements Serializable {
    public static final int STATUS_0 = 0; // 执行中 默认状态
    public static final int STATUS_1 = 1; // 死亡 自动执行归档
    public static final int STATUS_2 = 2; // 请假
    public static final int STATUS_3 = 3; // 中止 继续执行后 变为 0执行中
    public static final int STATUS_4 = 4; // 终止 手动归档 专干发起申请
    public static final int STATUS_5 = 5; // 变更执行地 自动执行归档
    public static final int STATUS_9 = 9; // 解除 手动归档 专干发起申请

    private String id; // 主键
    private int dealStatus = STATUS_4; // 档案状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(int dealStatus) {
        this.dealStatus = dealStatus;
    }
}
