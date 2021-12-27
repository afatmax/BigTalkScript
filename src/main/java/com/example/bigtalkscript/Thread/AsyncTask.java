package com.example.bigtalkscript.Thread;
import com.example.bigtalkscript.Modules.Game;
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
        ArrayList<Game> games = GrabTool.fileToArrayList("/Users/jim/workfile/IdeaProjects/BigTalkScript/pointMessage.txt");
        for (int i = 0; i < games.size(); i++) {
            int[] ints = new int[]{games.get(i).getX(),games.get(i).getY(),games.get(i).getRed(),games.get(i).getGreen(),games.get(i).getBlue()};
            System.out.println(Arrays.toString(ints));
            GrabTool.MouseResponse(ints);
        }
       // int[] ints = GrabTool.setPoint();

    }

}

