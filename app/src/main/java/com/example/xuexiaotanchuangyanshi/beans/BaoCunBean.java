package com.example.xuexiaotanchuangyanshi.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/9/15.
 */
@Entity
public class BaoCunBean {
    @Id
    @NotNull
    private Long id;
    private String shipingIP;
    private String zhujiDiZhi;
    private int moban;
    private String tuisongDiZhi;
    private String gonggao;
    private boolean isShowMoshengren;
    private boolean isShowShiPingLiu;
    private boolean isHengOrShu;
    @Generated(hash = 561513398)
    public BaoCunBean(@NotNull Long id, String shipingIP, String zhujiDiZhi,
            int moban, String tuisongDiZhi, String gonggao,
            boolean isShowMoshengren, boolean isShowShiPingLiu,
            boolean isHengOrShu) {
        this.id = id;
        this.shipingIP = shipingIP;
        this.zhujiDiZhi = zhujiDiZhi;
        this.moban = moban;
        this.tuisongDiZhi = tuisongDiZhi;
        this.gonggao = gonggao;
        this.isShowMoshengren = isShowMoshengren;
        this.isShowShiPingLiu = isShowShiPingLiu;
        this.isHengOrShu = isHengOrShu;
    }
    @Generated(hash = 1469853663)
    public BaoCunBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getShipingIP() {
        return this.shipingIP;
    }
    public void setShipingIP(String shipingIP) {
        this.shipingIP = shipingIP;
    }
    public String getZhujiDiZhi() {
        return this.zhujiDiZhi;
    }
    public void setZhujiDiZhi(String zhujiDiZhi) {
        this.zhujiDiZhi = zhujiDiZhi;
    }
    public int getMoban() {
        return this.moban;
    }
    public void setMoban(int moban) {
        this.moban = moban;
    }
    public String getTuisongDiZhi() {
        return this.tuisongDiZhi;
    }
    public void setTuisongDiZhi(String tuisongDiZhi) {
        this.tuisongDiZhi = tuisongDiZhi;
    }
    public String getGonggao() {
        return this.gonggao;
    }
    public void setGonggao(String gonggao) {
        this.gonggao = gonggao;
    }
    public boolean getIsShowMoshengren() {
        return this.isShowMoshengren;
    }
    public void setIsShowMoshengren(boolean isShowMoshengren) {
        this.isShowMoshengren = isShowMoshengren;
    }
    public boolean getIsShowShiPingLiu() {
        return this.isShowShiPingLiu;
    }
    public void setIsShowShiPingLiu(boolean isShowShiPingLiu) {
        this.isShowShiPingLiu = isShowShiPingLiu;
    }
    public boolean getIsHengOrShu() {
        return this.isHengOrShu;
    }
    public void setIsHengOrShu(boolean isHengOrShu) {
        this.isHengOrShu = isHengOrShu;
    }


   


}
