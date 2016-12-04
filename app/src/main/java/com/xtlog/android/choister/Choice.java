package com.xtlog.android.choister;

import java.util.Random;
import java.util.UUID;

/**
 * Created by admin on 2016/11/19.
 */

public class Choice {
    private String mColor;
    private UUID mId;
    private String mDesc;


    public Choice() {

        this(UUID.randomUUID(),getRandomColorCode());
    }

    public Choice(UUID id,String color) {
        mId = id;
        mColor = color;
    }


    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    public static String getRandomColorCode(){
        String r,g,b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length()==1 ? "0" + r : r ;
        g = g.length()==1 ? "0" + g : g ;
        b = b.length()==1 ? "0" + b : b ;

        return r+g+b;
    }
}
