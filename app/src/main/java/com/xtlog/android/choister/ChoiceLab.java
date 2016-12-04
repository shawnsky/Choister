package com.xtlog.android.choister;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 2016/11/19.
 */

public class ChoiceLab {
    private static ChoiceLab sChoiceLab;
    private List<Choice> mChoices;


    public static ChoiceLab get(Context context){
        if(sChoiceLab == null){
            sChoiceLab = new ChoiceLab(context);
        }
        return sChoiceLab;
    }
    private ChoiceLab(Context context){
        mChoices = new ArrayList<>();
        addDefaultChoice("A");
        addDefaultChoice("B");
        addDefaultChoice("C");
        addDefaultChoice("D");
        addDefaultChoice("去");
        addDefaultChoice("不去");
        addDefaultChoice("买");
        addDefaultChoice("不买");
        addDefaultChoice("吃");
        addDefaultChoice("不吃");



    }

    public List<Choice> getChoices(){
        return mChoices;
    }

    public Choice getChoice(UUID id){
        for(Choice choice:mChoices){
            if(choice.getId().equals(id)){
                return choice;
            }
        }
        return null;
    }

    public void addChoice(Choice c){
        mChoices.add(c);
    }

    public void deleteChoice(UUID id){
        for(Choice c:mChoices){
            if(c.getId().equals(id)){
                mChoices.remove(c);
                break;
            }
        }
    }
    public void addDefaultChoice(String eg){
        Choice c = new Choice();
        c.setDesc(eg);
        mChoices.add(c);
    }


}
