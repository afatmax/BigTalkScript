package com.example.bigtalkscript.Modules;
import com.example.bigtalkscript.Util.GrabTool;
import com.example.bigtalkscript.Util.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author jim
 */
@Component
public class AsyncTask {

    @Async("taskExecutor")
    public void tesTask(int i){
        System.out.println(Thread.currentThread().getName()+"-----"+i);
    }

    @Async("taskExecutor")
    public void stringTask(String str){
        System.out.println(Thread.currentThread().getName()+str);
    }

    public static void main(String[] args) throws Exception {
        int[] ints = GrabTool.setPoint();
        System.out.println(Arrays.toString(ints));
        //int[] ints = new int[]{1368, 2, 204, 204, 204};
        GrabTool.MouseResponse(ints);
    }

}

