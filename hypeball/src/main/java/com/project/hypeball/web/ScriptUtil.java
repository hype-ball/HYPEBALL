package com.project.hypeball.web;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ScriptUtil {

    public static void init(HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
    }

    public static void alert(HttpServletResponse response, String alertText) throws IOException {
        init(response);
        PrintWriter out = response.getWriter();
        out.println(String.format("<script>alert('%s');</script>", alertText));
        out.flush();
    }

    public static void movePage(HttpServletResponse response, String nextPage) throws IOException {
        init(response);
        PrintWriter out = response.getWriter();
        out.println(String.format("<script>location.href='%s';</script>", nextPage));
        out.flush();
    }

    public static void alertAndMovePage(HttpServletResponse response, String alertText, String nextPage) throws IOException {
        init(response);
        PrintWriter out = response.getWriter();
        out.println(String.format("<script>alert('%s'); location.href='%s';</script>", alertText, nextPage));
        out.flush();
    }

    public static void alertAndBackPage(HttpServletResponse response, String alertText) throws IOException {
        init(response);
        PrintWriter out = response.getWriter();
        out.println(String.format("<script>alert('%s'); history.go(-1);</script>", alertText));
        out.flush();
    }
}
