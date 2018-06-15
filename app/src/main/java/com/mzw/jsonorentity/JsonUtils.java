package com.mzw.jsonorentity;

import android.text.TextUtils;
import android.util.Log;

import com.mzw.jsonorentity.bean.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * json 转实体    实体转json
 * Created by think on 2018/6/15.
 */

public class JsonUtils<T> {
    Class<T> entityClass;

    public JsonUtils(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    //实体转json
    public JSONObject entityToJson(T entity) {
        JSONObject json = new JSONObject();
        try {
            Field[] fields = entityClass.getDeclaredFields();
            for (Field f : fields) {
                String name = f.getName();//获取 字段名称
                Field ff = entityClass.getField(name);//获取Field
                Object value = ff.get(entity);
                if(value == null){
                    if(f.getType() == Boolean.class) {
                        value = false;
                    }else{
                        value = "";
                    }
                }
                if(!TextUtils.isEmpty(name)){
                    json.put(name,value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    public T jsonToEntity(String jsonStr) {
        Object item = null;
        try {
            item = entityClass.newInstance();
            if(!TextUtils.isEmpty(jsonStr) && jsonStr.startsWith("{")){
                JSONObject json = new JSONObject(jsonStr);
                Field[] fields = entityClass.getDeclaredFields();
                for (Field f : fields) {
                    String name = f.getName();//获取 字段名称
                    Class type = f.getType();
                    if(type==String.class) {
                        //反射方式赋值
                        f.set(item,json.getString(name));
                    }else if(type==Double.class) {
                        f.set(item,json.getDouble(name));
                    }else  if(type==Integer.class) {
                        f.set(item,json.getInt(name));
                    }else if(type==Long.class) {
                        f.set(item,json.getLong(name));
                    }else  if(type==Boolean.class) {
                        f.set(item,json.getBoolean(name));
                    }else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) item;
    }


    public JSONArray listToJson(List<T> list) {
        JSONArray jsonArray = new JSONArray();
        for (T t:list) {
            JSONObject jsonObject = entityToJson(t);
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }


    public List<T> jsonToList(String jsonStr) {
        List<T> list = new ArrayList<T>();
        if(!TextUtils.isEmpty(jsonStr) && jsonStr.startsWith("[")) {
            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                if (jsonArray != null && jsonArray.length() > 0) {
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        list.add(jsonToEntity(jsonObject.toString()));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
