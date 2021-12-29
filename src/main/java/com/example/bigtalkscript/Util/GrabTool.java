package com.example.bigtalkscript.Util;

import com.example.bigtalkscript.Modules.Game;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jim
 */
public class GrabTool {

    /**
     * 本方法会在三秒后获取当前鼠标的坐标相关信息并且设置为判断点.
     */
    public static int[] setPoint() {
        int[] array = null;
        try {
            Robot robot = new Robot();
//            robot.delay(3000);
            // 获取鼠标坐标
            PointerInfo pinfo = MouseInfo.getPointerInfo();
            Point p = pinfo.getLocation();
            int mouseX = (int) p.getX();
            int mouseY = (int) p.getY();

            //获取鼠标坐标颜色
            BufferedImage bufferedImage = robot.createScreenCapture(new Rectangle(0, 0, 2560, 1600));
            int rgb = bufferedImage.getRGB(mouseX, mouseY);
            Color myColor = new Color(rgb);
            int R = myColor.getRed();
            int G = myColor.getGreen();
            int B = myColor.getBlue();
            array = new int[]{mouseX, mouseY, R, G, B};
//          Color mouseRGB = robot.getPixelColor(mouseX, mouseY);
//          int R = mouseRGB.getRed();
//          int G = mouseRGB.getGreen();
//          int B = mouseRGB.getBlue();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(e, "发生错误", GrabTool.class);
        }
        // 返回鼠标的坐标值
        return array;
    }

    public static String touchFile() {
        // 将数据保存到磁盘中，可以持久保存数据
        int[] Point = setPoint();
        // 将数据保存到磁盘中，可以持久保存数据
        FileOutputStream File = null;
        try {
            File = new FileOutputStream("pointMessage.txt", true);
            String pointMessage = "{" + Point[0] + "," + Point[1] + "," + Point[2] + "," + Point[3]        + "," + Point[4] + "}\n";
            byte b[] = pointMessage.getBytes();
            File.write(b);
            File.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 本方法会将文件信息提取并且返回此文件信息列表
     *
     * @param FileURL - 指定的文件URL
     * @return - 返回的文件信息列表
     * @throws Exception - 如果发生 I/O 错误则抛出异常
     */
    public static ArrayList<Game> fileToArrayList(String FileURL) throws Exception {

        String string = null;
        ArrayList<Game> PointList = new ArrayList<Game>();
        //本人采用正则表达式提取数据,
        Pattern patten = Pattern.compile("\\{([^,]+),([^,]+),([^,]+),([^,]+),([^\\}]+)\\}");
        BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(FileURL)));

        while ((string = file.readLine()) != null) {
            //虽然有其他存数据办法，比如数据库，但是不可能让用户专门下载个数据库，这是一个正常的逻辑
            Matcher rule = patten.matcher(string);

            while (rule.find()) {
                Game game = new Game();
                game.setX(Integer.parseInt(rule.group(1)));
                game.setY(Integer.parseInt(rule.group(2)));
                game.setRed(Integer.parseInt(rule.group(3)));
                game.setGreen(Integer.parseInt(rule.group(4)));
                game.setBlue(Integer.parseInt(rule.group(5)));
                PointList.add(game);
            }
        }
        file.close();
        return PointList;
    }

    /**
     * 本方法会根据设定的判断点与真实点进行对比,如果颜色一致则移动鼠标到该点进行单击操作
     *
     * @param point - 判断点的相关信息
     * @throws Exception - 如果平台配置不允许使用Robot类则抛出异常
     */
    public static void MouseResponse(int[] point)  {
        // 获取判断点的信息
        int decisionX = point[0];
        int decisionY = point[1];
        int decisionR = point[2];
        int decisionG = point[3];
        int decisionB = point[4];
        // 获取真实点的颜色
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        // 如果真实点与判断点颜色一致,则执行以下操作

        // 计算鼠标位置并且移动到该位置
        //int mouseMoveX = (int) (Math.random() * 35 - 15) + decisionX;
        //int mouseMoveY = (int) (Math.random() * 35 - 15) + decisionY;
        // 修复JDK8的移动不正确的BUG
        for (int i = 0; i < 6; i++) {
            robot.mouseMove(decisionX, decisionY);
        }
        robot.delay(3000);
        BufferedImage bufferedImage = robot.createScreenCapture(new Rectangle(0, 0, 2560, 1600));
        int rgb = bufferedImage.getRGB(decisionX, decisionY);
        Color myColor = new Color(rgb);
        int mouseR = myColor.getRed();
        int mouseG = myColor.getGreen();
        int mouseB = myColor.getBlue();
//        Color decisionRGB = robot.getPixelColor(decisionX, decisionY);
//        int mouseR = decisionRGB.getRed();
//        int mouseG = decisionRGB.getGreen();
//        int mouseB = decisionRGB.getBlue();
        if (Math.abs(mouseR - decisionR) < 5 && Math.abs(mouseG - decisionG) < 5 && Math.abs(mouseB - decisionB) < 5) {
            // 模拟计算鼠标按下的间隔并且按下鼠标
            int moveTime = (int) (Math.random() * 500 + 200);
            int mousePressTime = (int) (Math.random() * 500 + 200);
            robot.delay(moveTime);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.delay(mousePressTime);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            Logger.i("mousePress:::true==="+decisionR+"::::::"+decisionG+"::::::"+decisionB);
        }else{
            Logger.i("mousePress:::false=="+mouseR+"::::::"+mouseG+"::::::"+mouseB);
        }

    }

    /**
     * 本方法会根据指定的游戏标题和游戏文件路径,开启游戏窗口并且移动到指定的位置.
     *
     * @param gameTitle  - 指定的游戏标题
     * @param gamePath   - 指定的游戏文件路径
     * @param gameX      - 指定的游戏的X轴位置
     * @param gameY      - 指定的游戏的Y轴位置
     * @param gameWidth  - 指定的游戏的窗口宽度
     * @param gameHeight - 指定的游戏的窗口高度
     *
     * @throws Exception- 如果指定的游戏路径错误 或者 发生 I/O 错误 则抛出异常
     */
    public static void moveGameWindow(String gameTitle, String gamePath, int gameX, int gameY, int gameWidth, int gameHeight) throws Exception {
        // 获取指定顶级窗口的句柄
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, gameTitle);
        if (hwnd == null) {
            // 如果没有找到游戏窗口就启动游戏窗口
            Runtime.getRuntime().exec("cmd /c " + gamePath);
            return;
        }
        // 设置指定窗口的显示状态
        User32.INSTANCE.ShowWindow(hwnd, 1);
        // 激活指定窗口
        User32.INSTANCE.SetForegroundWindow(hwnd);
        // 获取指定窗口的位置
        User32.INSTANCE.MoveWindow(hwnd, gameX, gameY, gameWidth, gameHeight, true);

    }



}
