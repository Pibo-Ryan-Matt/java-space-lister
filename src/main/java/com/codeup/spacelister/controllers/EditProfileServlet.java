package com.codeup.spacelister.controllers;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "controllers.EditProfileServlet", urlPatterns = "/edit")
public class EditProfileServlet extends HttpServlet {

   protected void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       request.getRequestDispatcher("/WEB-INF/edit.jsp").forward(request, response);
   }

}
