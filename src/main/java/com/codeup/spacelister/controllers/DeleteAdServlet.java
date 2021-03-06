package com.codeup.spacelister.controllers;

import com.codeup.spacelister.dao.DaoFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/deleteAd")
public class DeleteAdServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hiddenParam = req.getParameter("selectedAdDelete");
        long id = Long.parseLong(hiddenParam);
        DaoFactory.getAdsDao().deleteEntry(id, 1);
        DaoFactory.getAdsDao().deleteEntry(id, 2);
        resp.sendRedirect("/profile");
    }
}
