package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/10/31.
 */

public class DrugPersonSynthesisEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String zxdw;   //执行单位
    private String zxdq;   //执行地区
    private String dqzt;   //当前状态
    private String zxksrq; //执行开始日期
    private String zxjsrq; //执行结束日期
    private String zxbdrq; //执行报到日期
    private String gkzrr;  //管控责任人
    private String state;

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    private int type;

    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    public String getZxdw()
    {
        return zxdw;
    }
    public void setZxdw(String zxdw)
    {
        this.zxdw = zxdw;
    }
    public String getZxdq()
    {
        return zxdq;
    }
    public void setZxdq(String zxdq)
    {
        this.zxdq = zxdq;
    }
    public String getDqzt()
    {
        return dqzt;
    }
    public void setDqzt(String dqzt)
    {
        this.dqzt = dqzt;
    }
    public String getZxksrq()
    {
        return zxksrq;
    }
    public void setZxksrq(String zxksrq)
    {
        this.zxksrq = zxksrq;
    }
    public String getZxjsrq()
    {
        return zxjsrq;
    }
    public void setZxjsrq(String zxjsrq)
    {
        this.zxjsrq = zxjsrq;
    }
    public String getZxbdrq()
    {
        return zxbdrq;
    }
    public void setZxbdrq(String zxbdrq)
    {
        this.zxbdrq = zxbdrq;
    }
    public String getGkzrr()
    {
        return gkzrr;
    }
    public void setGkzrr(String gkzrr)
    {
        this.gkzrr = gkzrr;
    }
}
