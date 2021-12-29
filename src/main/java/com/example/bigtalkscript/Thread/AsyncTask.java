package com.example.bigtalkscript.Thread;
import com.example.bigtalkscript.Modules.Game;
import com.example.bigtalkscript.Util.GrabTool;
import com.example.bigtalkscript.Util.Logger;
import com.example.bigtalkscript.Util.MailUtil;
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
    public void tesTask(int i1){
        ArrayList<Game> games = null;
        try {
            games = GrabTool.fileToArrayList("C:\\Users\\PCJ\\Desktop\\pointMessage.txt");
            for (int i = 0; i < games.size(); i++) {
                int[] ints = new int[]{games.get(i).getX(),games.get(i).getY(),games.get(i).getRed(),games.get(i).getGreen(),games.get(i).getBlue()};
                System.out.println(Arrays.toString(ints));
                GrabTool.MouseResponse(ints);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+"-----"+i1);
    }

    @Async("taskExecutor")
    public void stringTask(String str){
        ArrayList<Game> games = null;
        try {
            games = GrabTool.fileToArrayList("D:\\ideaWork\\BigTalkScript\\pointMessage.txt");
            for (int i = 0; i < games.size(); i++) {
                int[] ints = new int[]{games.get(i).getX(),games.get(i).getY(),games.get(i).getRed(),games.get(i).getGreen(),games.get(i).getBlue()};
                System.out.println(Arrays.toString(ints));
                GrabTool.MouseResponse(ints);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }






        System.out.println(Thread.currentThread().getName()+str);
    }

    public static void main(String[] args) throws Exception {
//        ArrayList<Game> games = GrabTool.fileToArrayList("D:\\ideaWork\\BigTalkScript\\pointMessage.txt");
//        for (int i = 0; i < games.size(); i++) {
//            int[] ints = new int[]{games.get(i).getX(),games.get(i).getY(),games.get(i).getRed(),games.get(i).getGreen(),games.get(i).getBlue()};
//            System.out.println(Arrays.toString(ints));
//            GrabTool.MouseResponse(ints);
//        }
        //GrabTool.touchFile();
       // int[] ints = GrabTool.setPoint();
        //System.out.println(Arrays.toString(ints));
//        int[] ints1 = {1264, 15, 204, 204, 204};
//        GrabTool.MouseResponse(ints1);
        //MailUtil.sendMessages("813350550","lixhxgulaykxbchg","341595","出事了!","卡主了","/Users/jim/Desktop/img.png");
//        GrabTool.moveGameWindow("大话西游2经典版","\"D:\\Program Files\\Netease\\大话西游2\\xy2.exe\"",1808,952,
//                800,1800);



    }

}

