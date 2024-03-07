package com.yu.init.enums;

public enum Enums {
    WAITING("waiting"),
    RUNNING("running"),
    SUCCEED("succeed"),
    ERROR("error"),
    FAIL("fail");
    private String msg;
    Enums(String msg) {
        this.msg=msg;
    }


    // 普通方法
    public String getMsg() {
        return this.msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static void main(String[] args) {
    }

}
