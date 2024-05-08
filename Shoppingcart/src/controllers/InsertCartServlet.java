package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcon.DB_Properties;
import dbcon.StoreDAO;
import model.BillingDetails;
import model.Items;

@WebServlet("/InsertCartServlet")

public class InsertCartServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		StoreDAO gap = null;
		List<BillingDetails> l = new ArrayList<>();
		HttpSession session = request.getSession();
		try {
			gap = new DB_Properties();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Items> i = new ArrayList<>();
		int val = Integer.parseInt(request.getParameter("length"));
		while (val > 0) {
			String proid = request.getParameter("proid");
			String quantity = request.getParameter("quantity");
			String price = request.getParameter("price");
			float floatValue = Float.parseFloat(price);
			String name = request.getParameter("name");

			if (proid != null && (quantity != null && price != null)) {
				Items it = new Items(Integer.parseInt(proid), Integer.parseInt(quantity), floatValue, name);
				i.add(it);
			}
			val--;
		}
		try {
			l = gap.shippingcharges(i);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (BillingDetails samp : l) {

			System.out.println(samp.getTotalbaseprice() + " " + samp.getShipchg() + " " + samp.getGst() + " "
					+ samp.getFinalprice());
		}
		session.setAttribute("bill", l);

	}
}