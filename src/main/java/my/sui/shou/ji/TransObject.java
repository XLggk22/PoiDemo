package my.sui.shou.ji;

import java.util.Date;

public class TransObject {

    private Date transTime;
    private String transType;
    private String merchant;
    private String goods;
    private String cleanType;
    private String transAmt;
    private String payMethod;
    private String transStatus;
    private String transOrder;
    private String merchantOrder;

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getCleanType() {
        return cleanType;
    }

    public void setCleanType(String cleanType) {
        this.cleanType = cleanType;
    }

    public String getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getTransOrder() {
        return transOrder;
    }

    public void setTransOrder(String transOrder) {
        this.transOrder = transOrder;
    }

    public String getMerchantOrder() {
        return merchantOrder;
    }

    public void setMerchantOrder(String merchantOrder) {
        this.merchantOrder = merchantOrder;
    }

    @Override
    public String toString() {
        return "TransObject{" +
                "transTime='" + transTime + '\'' +
                ", transType='" + transType + '\'' +
                ", merchant='" + merchant + '\'' +
                ", goods='" + goods + '\'' +
                ", cleanType='" + cleanType + '\'' +
                ", transAmt='" + transAmt + '\'' +
                ", payMethod='" + payMethod + '\'' +
                ", transStatus='" + transStatus + '\'' +
                ", transOrder='" + transOrder + '\'' +
                ", merchantOrder='" + merchantOrder + '\'' +
                '}';
    }
}
