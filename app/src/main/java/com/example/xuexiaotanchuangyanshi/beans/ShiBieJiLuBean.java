package com.example.xuexiaotanchuangyanshi.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by chenjun on 2017/6/15.
 */


@Entity
public class ShiBieJiLuBean {

    @Id@NotNull
    private Long id;
    private String times;
    private String name;
    private String urlPath;


    @Generated(hash = 1176331601)
    public ShiBieJiLuBean(@NotNull Long id, String times, String name,
                          String urlPath) {
        this.id = id;
        this.times = times;
        this.name = name;
        this.urlPath = urlPath;
    }

    @Generated(hash = 1406514923)
    public ShiBieJiLuBean() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
