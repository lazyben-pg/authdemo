package com.lazyben.demo.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebUtil {
    public static void render(HttpServletResponse response, String resp, int statusCode) {
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(statusCode);
            response.getWriter().println(resp);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
