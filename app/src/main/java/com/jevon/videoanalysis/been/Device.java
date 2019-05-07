package com.jevon.videoanalysis.been;

import cn.bmob.v3.BmobObject;

/**
 * @Author: Mr.J
 * @CreateDate: 2019/4/26 13:02
 */
public class Device extends BmobObject {

    private String name;
    private String borard;
    private String cpu;
    private Integer sdk;

    public Device(String name, String borard, String cpu[], Integer sdk) {
        this.name = name;
        this.borard = borard;
        this.sdk = sdk;
        for (String c : cpu) {
            this.cpu += c + "-";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBorard() {
        return borard;
    }

    public void setBorard(String borard) {
        this.borard = borard;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public Integer getSdk() {
        return sdk;
    }

    public void setSdk(Integer sdk) {
        this.sdk = sdk;
    }
}
