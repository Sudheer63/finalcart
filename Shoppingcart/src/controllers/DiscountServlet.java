package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAObridge;
import dbcon.StoreDAO;

@WebServlet("/DiscountServlet")
public class DiscountServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		StoreDAO gap = DAObridge.get();
		double discount = 0;
		double amount = Double.parseDouble(req.getParameter("amount"));
		double couponno = Double.parseDouble(req.getParameter("coupon"));
		try {
			discount = gap.discountcoupon(amount, couponno);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();

		out.print(discount);

		out.close();
	}
}
