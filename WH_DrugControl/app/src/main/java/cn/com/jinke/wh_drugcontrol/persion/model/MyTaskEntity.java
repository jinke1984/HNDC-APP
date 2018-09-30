package cn.com.jinke.wh_drugcontrol.persion.model;

/**
 * Created by admin on 2017/10/31.
 */

public class MyTaskEntity {
    private static final long serialVersionUID = 1L;

    private String name;
    private int taskNub;
    private int taskType;  //1、	综合类 2、系统应用类 3、业务知识类4、档案建设类5、实际工作类   6、其他类

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public int getTaskNub()
    {
        return taskNub;
    }
    public void setTaskNub(int  taskNub)
    {
        this.taskNub = taskNub;
    }
    public int getTaskType() {
        return taskType;
    }
    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }
}
