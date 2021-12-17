package com.example.bigtalkscript;

import com.alibaba.fastjson.JSONObject;
import com.example.bigtalkscript.Modules.AsyncTask;
import com.example.bigtalkscript.Util.FileUtil;
import com.example.bigtalkscript.Util.JsonUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jim
 */
@SpringBootApplication
public class BigTalkScriptApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigTalkScriptApplication.class, args);
        AsyncTask asyncTask = new AsyncTask();
        asyncTask.tesTask(4);
        asyncTask.stringTask("嗨");
        //asyncTask.checkAvtiveThreadNum();
//        String file = FileUtil.findFile("conf.json");
//        JSONObject json = JsonUtils.getJson(file);


    }

}
