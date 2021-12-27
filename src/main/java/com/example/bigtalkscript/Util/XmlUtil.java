package com.example.bigtalkscript.Util;

import com.example.bigtalkscript.Modules.Game;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlUtil {


    /**
     * 本方法判断指定文件是否与指定信息类有重复的<title>属性内容,有则修改该信息,否则添加该信息
     *
     * @param file - 指定文件
     * @param game - 指定信息类
     */
    public static void reviseGame(File file, Game game) {

        try {

            InputStream in = new FileInputStream(file);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            List<Element> elements = root.elements();
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            boolean FileIsExist = false;

            for (Element element : elements) {
                // 如果文件存在一样的<title>属性内容,则修改该相关的其他信息
                if (game.getTitle() != null && game.getTitle().equals(element.elementText("title"))) {
                    element.element("x").setText(String.valueOf(game.getX()));
                    element.element("y").setText(String.valueOf(game.getY()));
                    element.element("width").setText(String.valueOf(game.getWidth()));
                    element.element("height").setText(String.valueOf(game.getHeight()));
                    element.element("red").setText(String.valueOf(game.getRed()));
                    element.element("green").setText(String.valueOf(game.getGreen()));
                    element.element("blue").setText(String.valueOf(game.getBlue()));
                    FileIsExist = true;
                    break;
                }
            }

            // 如果文件不存在<title>属性内容,则向文件添加该信息
            if (!FileIsExist) {
                // 添加DOM节点
                Element element = root.addElement("game");
                element.addElement("title").addText(game.getTitle());
                element.addElement("x").addText(String.valueOf(game.getX()));
                element.addElement("y").addText(String.valueOf(game.getY()));
                element.addElement("width").addText(String.valueOf(game.getWidth()));
                element.addElement("height").addText(String.valueOf(game.getHeight()));
                element.addElement("red").addText(String.valueOf(game.getRed()));
                element.addElement("green").addText(String.valueOf(game.getGreen()));
                element.addElement("blue").addText(String.valueOf(game.getBlue()));
            }

            // 写入文件
            FileOutputStream fos = new FileOutputStream(file);
            XMLWriter writer = new XMLWriter(fos, format);
            writer.write(doc);
            // 结束操作
            writer.close();
            in.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    /**
     * 本方法将文件的所有信息存储到列表中并返回
     *
     * @param file - 指定文件
     * @return - 返回信息列表
     */
    public static List<Game> getAllGame(File file) {

        try {

            InputStream in = new FileInputStream(file);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            List<Game> games = new ArrayList<>();
            List<Element> elements = root.elements();

            for (Element element : elements) {
                // 遍历并把所有标签存储到列表中
                Game game = new Game();
                game.setTitle(element.elementText("title"));
                game.setX(Integer.valueOf(element.elementText("x")));
                game.setY(Integer.valueOf(element.elementText("y")));
                game.setWidth(Integer.valueOf(element.elementText("width")));
                game.setHeight(Integer.valueOf(element.elementText("height")));
                game.setRed(Integer.valueOf(element.elementText("red")));
                game.setGreen(Integer.valueOf(element.elementText("green")));
                game.setBlue(Integer.valueOf(element.elementText("blue")));
                games.add(game);
            }

            return games;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        }
    }

    /**
     * 本方法会删除指定文件所有的根标签下的子标签,仅保留根标签
     *
     * @param file - 指定文件
     */
    public static void removeAllGame(File file) {

        try {

            InputStream in = new FileInputStream(file);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            List<Element> elements = root.elements();
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");

            for (Element element : elements) {
                // 遍历并删除所有子标签
                element.detach();
            }

            // 写入文件
            FileOutputStream fos = new FileOutputStream(file);
            XMLWriter writer = new XMLWriter(fos, format);
            writer.write(doc);
            // 结束操作
            writer.close();
            in.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }



}
