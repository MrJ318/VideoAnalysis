package com.jevon.videoanalysis.been;

import cn.bmob.v3.BmobObject;

/**
 * @Author: Mr.J
 * @CreateDate: 2019/9/21 15:59
 */
public class AnalysisUrl extends BmobObject {

    private String order;
    private String url;

    public AnalysisUrl() {
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
