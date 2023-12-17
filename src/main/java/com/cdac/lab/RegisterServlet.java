package com.cdac.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String myname = request.getParameter("name1");
		String myemail = request.getParameter("email1");
		String mypassword = request.getParameter("password1");
		String mygender = request.getParameter("gender1");
		String mycity = request.getParameter("city1");
		
		PrintWriter out = response.getWriter();
		
		// database connectivity
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/adv_java", "vishal", "");
			String sql = "INSERT INTO Register(name, email, password, gender, city) VALUES (?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, myname);
			ps.setString(2, myemail);
			ps.setString(3, mypassword);
			ps.setString(4, mygender);
			ps.setString(5, mycity);
			
			int rs = ps.executeUpdate();
			
			if(rs > 0) {
				response.setContentType("text/html");
				out.print("<h3 style='color:green'>User registration successful</h3>");
				RequestDispatcher rd = request.getRequestDispatcher("/Register.jsp");
				rd.include(request, response);
			}
			else {
				response.setContentType("text/html");
				out.print("<h3 style='color:red'>User registration failed !</h3>");
				RequestDispatcher rd = request.getRequestDispatcher("/Register.jsp");
				rd.include(request, response);
			}
		}catch(Exception e) {
			e.printStackTrace();
			
			response.setContentType("text/html");
			out.print("<h3 style='color:red'>Exception occured : "+e.getMessage()+"</h3>");
			RequestDispatcher rd = request.getRequestDispatcher("/Register.jsp");
			rd.include(request, response);
		}
	}
}
