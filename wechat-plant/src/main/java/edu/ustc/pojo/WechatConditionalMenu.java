package edu.ustc.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class WechatConditionalMenu extends WechatMenu {

    @JSONField(name = "matchrule")
    private WechatMatchRule matchRule;

    public WechatMatchRule getMatchRule() {
        return matchRule;
    }

    public void setMatchRule(WechatMatchRule matchRule) {
        this.matchRule = matchRule;
    }
}
