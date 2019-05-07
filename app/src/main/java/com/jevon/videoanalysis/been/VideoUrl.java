package com.jevon.videoanalysis.been;

import cn.bmob.v3.BmobObject;

/**
 * @Author: Mr.J
 * @CreateDate: 2019/4/26 16:40
 */
public class VideoUrl extends BmobObject {

    private String url;
    private String userAgent;

    public VideoUrl() {
    }

    public VideoUrl(String url, String userAgent) {
        this.url = url;
        this.userAgent = userAgent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
