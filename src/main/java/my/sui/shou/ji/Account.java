package my.sui.shou.ji;

public enum Account {
    DEFAULT_EMPTY("",""),
    VITTUAL_WEIXIN("微信钱包","微信钱包"),
    CARD_PINGAN("平安工资卡","平安工资卡"),
    CREDIT_ZHAOHANG("招行young卡","招行young卡"),
    ;

    //账户
    private String account;

    //子账户
    private String subAccount;

    Account(String account, String subAccount) {
        this.account = account;
        this.subAccount = subAccount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }
}
