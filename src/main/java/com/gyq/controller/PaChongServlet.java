package com.gyq.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyq.dao.YiQingDao;
import com.gyq.entity.YiQing;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PaChongServlet extends HttpServlet {
    String json = "{}";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = req.getParameter("address");
        String date = req.getParameter("date");
        if (address != null && address.trim().length() > 0 && date != null && date.trim().length() > 0) {
            YiQingDao dao = new YiQingDao();
            YiQing yiQing = dao.QueryYiQing(date, address);
            ObjectMapper om = new ObjectMapper();
            json = om.writeValueAsString(yiQing);
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter pw = resp.getWriter();
            pw.println(json);
            pw.flush();
            pw.close();


        }
    }
}
