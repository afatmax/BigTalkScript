package com.example.bigtalkscript.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.bigtalkscript.Util.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class HelloController {
 
    @RequestMapping("/")
    public String index(String jsonString) {
        return "wel";
    }

    @RequestMapping("/insert")
    @ResponseBody
    public JSONObject insert(ArrayList<JSON> jsonArrayList){


        return new JSONObject();
    }
}