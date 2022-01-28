package my.sui.shou.ji;

public enum Classify {
    DEFAULT_EMPTY("",""),
    IN_OTHER_HONGBAO("其他收入","红包"),
    OUT_JUJIA_FANGZHU("居家物业","房租"),
    OUT_YILIAO_YAOPING("医疗保健","药品费"),
    OUT_XINGCHE_GONGJIAO("行车交通","公共交通"),
    OUT_SHIPING_EAT("食品酒水","早午晚餐"),
    OUT_SHIPING_FLUT("食品酒水","水果食品"),
    ;

    //分类
    private String type;
    //子分类
    private String subType;

    Classify(String type, String subType) {
        this.type = type;
        this.subType = subType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }
}
