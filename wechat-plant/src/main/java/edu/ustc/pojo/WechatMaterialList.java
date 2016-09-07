package edu.ustc.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class WechatMaterialList {

    @JSONField(name = "item_count")
    private Integer totalCount;

    @JSONField(name = "item_count")
    private Integer itemCount;

    @JSONField(name = "item")
    private List<WechatMaterialItem> item;

    @JSONField(name = "errcode")
    private String errorCode;

    @JSONField(name = "errmsg")
    private String errorMessage;

    private long refreshTime;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public List<WechatMaterialItem> getItem() {
        return item;
    }

    public void setItem(List<WechatMaterialItem> item) {
        this.item = item;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }
}
