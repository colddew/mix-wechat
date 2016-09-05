package edu.ustc.config;

public enum MassSendErrorCode {

    ADVERTISEMENT("10001", "涉嫌广告"),
    POLITICS("20001", "涉嫌政治"),
    SOCIETY("20004", "涉嫌社会"),
    EROTICISM("20002", "涉嫌色情"),
    CRIME("20006", "涉嫌违法犯罪"),
    CHEAT("20008", "涉嫌欺诈"),
    COPYRIGHT("20013", "涉嫌版权"),
    MUTUAL_PROMOTION("22000", "涉嫌互推(互相宣传)"),
    OTHERS("21000", "涉嫌其他");

    private String code;
    private String description;

    MassSendErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
