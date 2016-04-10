package com.wang.xiaoke.fileexplorer;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/9.
 */
public class Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName;//文件名
    private File path;//文件路径
    private int state;//文件类型
    private boolean isCheck;//选中的将被删除

    public Entity() {
        state = 0;
        path = null;
        fileName = null;
        isCheck = false;
    }

    public int getState() {
        return state;
    }

    public File getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean getIsCheck() {
        return isCheck;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public String toString() {
        return "[fileName:" + fileName + ",path:" + path.toString()
                + ",state:" + state + ",isCheck:" + isCheck + "]";
    }
}
