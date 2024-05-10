package controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAObridge;
import dbcon.StoreDAO;

@WebServlet("/CheckPincodeServlet")
public class CheckPincodeServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StoreDAO gap = DAObridge.get();
		String pincode = request.getParameter("pincode");
		String prodid = request.getParameter("prodid");
		boolean state = false;
		try {
			state = gap.checkpincode(pincode, prodid);
		} catch (Exception e) {
			System.out.println(e);
		}
		response.setContentType("text/plain");
		// Create a PrintWriter object to write response
		PrintWriter out = response.getWriter();

		// Write the state as the response
		out.print(state);

		// Close the PrintWriter
		out.close();
	}

}
