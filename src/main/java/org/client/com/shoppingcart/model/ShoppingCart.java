package org.client.com.shoppingcart.model;

public class ShoppingCart {
    private String uuid;
    private String spid;
    private String account;
    private int number;

    public ShoppingCart() {
    }

    public ShoppingCart(String uuid, String spid, String account, int number) {
        this.uuid = uuid;
        this.spid = spid;
        this.account = account;
        this.number = number;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "uuid='" + uuid + '\'' +
                ", spid='" + spid + '\'' +
                ", account='" + account + '\'' +
                ", number=" + number +
                '}';
    }
}
