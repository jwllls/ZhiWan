package com.zhiwan.hamitao.base_module.xg;

/**
 * <p>推送透传类型的枚举类
 *
 * @author lian
 */
public enum AiNotificationTypeEnum {
    VIDEO_APPLY(1, "视频邀请"),
    NOTIFICATION_ALL(2, "全站通告"),;

    private AiNotificationTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private int type;
    private String desc;

    public static AiNotificationTypeEnum getType(int key) {
        AiNotificationTypeEnum[] types = AiNotificationTypeEnum.values();
        for (AiNotificationTypeEnum type : types) {
            if (key == type.type) {
                return type;
            }
        }
        return null;
    }

    public static boolean valid(byte key) {
        AiNotificationTypeEnum[] types = AiNotificationTypeEnum.values();
        for (AiNotificationTypeEnum type : types) {
            if (key == type.type) {
                return true;
            }
        }
        return false;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
