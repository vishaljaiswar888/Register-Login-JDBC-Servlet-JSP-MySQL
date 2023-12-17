package com.cdac.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String myemail = request.getParameter("email1");
		String mypassword = request.getParameter("password1");
		
		
		
		PrintWriter out = response.getWriter();
		
		response.setContentType("text/html");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/adv_java", "vishal", "");
			
			String sql = "SELECT * FROM Register WHERE email = ? and password = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, myemail);
			ps.setString(2, mypassword);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				HttpSession session = request.getSession();
				session.setAttribute("session_name", rs.getString("name"));
				
				RequestDispatcher rd = request.getRequestDispatcher("/Home.jsp");
				rd.include(request, response);
			}
			else
			{
				out.print("<h2 style='color:red'>Email Id or Password didn't matched !</h2>");
				RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");
				rd.include(request, response);
			}
		}catch(Exception e) {
			e.printStackTrace();
			
			out.print("<h2 style='color:red'>"+e.getMessage()+"</h2>");
			RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");
			rd.include(request, response);
		}
	}
}
