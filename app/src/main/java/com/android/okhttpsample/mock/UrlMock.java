package com.android.okhttpsample.mock;

import java.util.ArrayList;

/**
 * @since 18/4/4
 */

public class UrlMock {
    private static ArrayList<String> urlList = new ArrayList();

    static {
        urlList.add("https://www.baidu.com/");
        urlList.add("http://www.qq.com/");
        urlList.add("https://github.com/");
        urlList.add("http://www.tingyun.com/");
        urlList.add("http://www.vogella.com/index.html");
        urlList.add("http://101.132.24.156/d/d.swf");
    }

    public static String mock() {
        double random = Math.random();
        int index = (int) (urlList.size() * random);
        return urlList.get(index);
    }


}
