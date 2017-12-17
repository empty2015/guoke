package com.jiaye.guoke.util;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/12/16.
 */

public class GsonManager {
    private static GsonManager instance = null;
    private Gson gson;
    private GsonManager(){
        gson = new Gson();
    }
    public static GsonManager getInstance(){
       if(instance == null){
           instance = new GsonManager();
       }
        return instance;
    }

    public Gson getGson(){
        return gson;
    }
}
