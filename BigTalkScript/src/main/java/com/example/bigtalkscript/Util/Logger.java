package com.example.bigtalkscript.Util;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {


    private enum Level {
        DEBUG("D"),
        INFO("I"),
        ERROR("E");
        private String displayName;

        Level(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }


    public static void d(String msg, Object... args) {

        log(Level.DEBUG, null, msg, args);

    }

    public static void i(String msg, Object... args) {
        log(Level.INFO, null, msg, args);

    }

    public static void e(Throwable e, String msg, Object... args) {
        log(Level.ERROR, e, msg, args);

    }


    public static void e(String msg, Object... args) {
        log(Level.ERROR, null, msg, args);
    }


    private static void log(Level level, Throwable throwable, String msg, Object... args) {
        StringBuffer sb = new StringBuffer();
        String className = Thread.currentThread().getStackTrace()[3].getClassName();//调用的类名
        String simpleClassName = className.substring(className.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();//调用的方法名
        int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();//调用的行数
        sb.append(parse("{", "}", "[{}]-[{}]]-{}.{}({}.java:{})\n", level.getDisplayName(), new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS").format(new Date()), className, methodName, simpleClassName, lineNumber))
                .append(parse("{", "}", msg, args));
        if (throwable != null) {
            try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
                throwable.printStackTrace(pw);
                sb.append("\n").append(sw.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (level.equals(Level.ERROR)) {
            System.err.println(sb.toString());
        } else {
            System.out.println(sb.toString());
        }

    }

    /**
     * 将字符串text中由openToken和closeToken组成的占位符依次替换为args数组中的值
     *
     * @param openToken
     * @param closeToken
     * @param text
     * @param args
     * @return
     */
    private static String parse(String openToken, String closeToken, String text, Object... args) {
        if (args == null || args.length <= 0) {
            return text;
        }
        int argsIndex = 0;
        if (text == null || text.isEmpty()) {
            return "";
        }
        char[] src = text.toCharArray();
        int offset = 0;
        // search open token
        int start = text.indexOf(openToken, offset);
        if (start == -1) {
            return text;
        }
        final StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        while (start > -1) {
            if (start > 0 && src[start - 1] == '\\') {
                // this open token is escaped. remove the backslash and continue.
                builder.append(src, offset, start - offset - 1).append(openToken);
                offset = start + openToken.length();
            } else {
                // found open token. let's search close token.
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                builder.append(src, offset, start - offset);
                offset = start + openToken.length();
                int end = text.indexOf(closeToken, offset);
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        // this close token is escaped. remove the backslash and continue.
                        expression.append(src, offset, end - offset - 1).append(closeToken);
                        offset = end + closeToken.length();
                        end = text.indexOf(closeToken, offset);
                    } else {
                        expression.append(src, offset, end - offset);
                        offset = end + closeToken.length();
                        break;
                    }
                }
                if (end == -1) {
                    // close token was not found.
                    builder.append(src, start, src.length - start);
                    offset = src.length;
                } else {
                    ///////////////////////////////////////仅仅修改了该else分支下的个别行代码////////////////////////
                    String value = (argsIndex <= args.length - 1) ?
                            (args[argsIndex] == null ? "" : args[argsIndex].toString()) : expression.toString();
                    builder.append(value);
                    offset = end + closeToken.length();
                    argsIndex++;
                    ////////////////////////////////////////////////////////////////////////////////////////////////
                }
            }
            start = text.indexOf(openToken, offset);
        }
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
}
