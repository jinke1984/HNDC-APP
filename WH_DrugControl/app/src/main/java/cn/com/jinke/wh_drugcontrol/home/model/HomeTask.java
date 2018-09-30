package cn.com.jinke.wh_drugcontrol.home.model;

import java.io.Serializable;

/**
 * Created by jinke on 2017/9/7.
 */

public class HomeTask implements Serializable {

    private String taskName;
    private int taskImage;
    private int taskNum;

    public HomeTask() {
        this("", 0, 0);
    }

    public HomeTask(String taskName, int taskImage, int taskNum) {
        this.taskName = taskName;
        this.taskImage = taskImage;
        this.taskNum = taskNum;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskImage() {
        return taskImage;
    }

    public void setTaskImage(int taskImage) {
        this.taskImage = taskImage;
    }

    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }
}
