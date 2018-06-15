package com.mzw.jsonorentity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mzw.jsonorentity.bean.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout log_layout;

    JSONObject jsonObject;
    JSONArray jsonArray;
    JsonUtils<User> jsonUtils;

    long a1 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonUtils = new JsonUtils<User>(User.class);
        log_layout = (LinearLayout)findViewById(R.id.id_log_layout);

        //双击清空日志
        log_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                long a2 = System.currentTimeMillis();
                if(a2 - a1 < 500){
                    Log.i("---mzw---","双击...");
                    log_layout.removeAllViews();
                    a1 = 0;
                }else{
                    a1 = System.currentTimeMillis();
                }
            }
        });


    }

    public void entityToJson(View view) {
        logLayout(" ● ","entityToJson");
        User u = new User(1,"ZhangSan","123");
        u.aaa = true;
        jsonObject = jsonUtils.entityToJson(u);
        logLayout("    ",jsonObject.toString());
        Log.i("---mzw---",jsonObject.toString());
    }

    public void jsonToEntity(View view) {
        logLayout(" ● ","jsonToEntity");
        if(jsonObject != null){
            User u = jsonUtils.jsonToEntity(jsonObject.toString());
            logLayout("    ",u.toString());
            Log.i("---mzw---",u.toString());
        }
    }

    public void listToJson(View view) {
        logLayout(" ● ","listToJson");
        List<User> list = new ArrayList<User>();
        list.add(new User(1,"ZhangSan","123"));
        list.add(new User(2,"LiSi","123"));
        list.add(new User(3,"WangWu","123",true));
        list.add(new User(4,"LiuSan","123"));
        list.add(new User(5,"SunQi","123",true));

        jsonArray = jsonUtils.listToJson(list);
        logLayout("    ",jsonArray.toString().replaceAll("\\},","\\},\n     "));
        Log.i("---mzw---",jsonArray.toString().replaceAll("\\},","\\},\n"));
    }

    public void jsonToList(View view) {
        logLayout(" ● ","jsonToList");
        if(jsonArray != null){
            List<User> list = jsonUtils.jsonToList(jsonArray.toString());
            for (User u :list) {
                logLayout("    ",u.toString());
                Log.i("---mzw---",u.toString());
            }
        }
    }





    private void logLayout(String str1,String str2) {
        TextView tv = new TextView(this);
        tv.setPadding(2,8,2,0);
        tv.setTextSize(10);
        tv.setText(str1 +""+str2);
        log_layout.addView(tv);
    }
}
