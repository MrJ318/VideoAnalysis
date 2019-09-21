package com.jevon.videoanalysis.been;

import cn.bmob.v3.BmobObject;

/**
 * @Author: Mr.J
 * @CreateDate: 2019/9/21 15:36
 */
public class BrowseInfo extends BmobObject {

    private String name;
    private String sdk;
    private String cpu;
    private String board;
    private String userAgent;
    private String url;

    public BrowseInfo() {
        this.setTableName("browse_info");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
