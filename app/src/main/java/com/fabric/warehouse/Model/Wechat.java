package com.fabric.warehouse.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 6193 on 2016/4/13.
 */
public class Wechat {

    @SerializedName("errcode")
    String errcode;

    @SerializedName("errmsg")
    String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
