package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dbcon.DB_Properties;
import dbcon.StoreDAO;
import model.BillingDetails;
import model.Items;

@WebServlet("/InsertCartServlet")

public class InsertCartServlet extends HttpServlet {
	// List<BillingDetails> l = new ArrayList<>();
	// HttpSession session;
	//
	// protected void doPost(HttpServletRequest request, HttpServletResponse response)
	// throws IOException, ServletException {
	// List<BillingDetails> d = new ArrayList<>();
	// StoreDAO gap = null;
	//
	// session = request.getSession();
	// try {
	// gap = new DB_Properties();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// List<Items> i = new ArrayList<>();
	// int val = Integer.parseInt(request.getParameter("length"));
	// while (val > 0) {
	// String proid = request.getParameter("proid");
	// String quantity = request.getParameter("quantity");
	// String price = request.getParameter("price");
	// float floatValue = Float.parseFloat(price);
	// String name = request.getParameter("name");
	// System.out.println(proid + " " + name);
	// if (proid != null && (quantity != null && price != null)) {
	// Items it = new Items(Integer.parseInt(proid), Integer.parseInt(quantity), floatValue, name);
	// i.add(it);
	// }
	// val--;
	// }
	// for (Items lt : i) {
	// System.out.println(lt.getName());
	// }
	// try {
	// d = gap.shippingcharges(i);
	//
	// } catch (SQLException e) {
	//
	// e.printStackTrace();
	// }
	// for (BillingDetails samp : d) {
	// l.add(samp);
	// }
	//
	// session.setAttribute("bill", l);
	//
	// }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<BillingDetails> l = new ArrayList<>();
		StoreDAO gap = null;
		HttpSession session = request.getSession();
		try {
			gap = new DB_Properties();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Items> itemsList = new ArrayList<>();
		String pro = request.getParameter("item");

		System.out.println(pro);
		JSONObject js = new JSONObject(pro);
		for (String j : js.keySet()) {
			JSONObject itemData = js.getJSONObject(j);
			System.out.println(itemData);
			int proidValue = itemData.getInt("proid");
			int itemQuantity = itemData.getInt("itemQuantity");
			float itemprice = (float) itemData.getDouble("itemprice");
			String itemname = itemData.getString("itemname");
			itemsList.add(new Items(proidValue, itemQuantity, itemprice, itemname));
		}
		for (Items i : itemsList) {
			System.out.println(i.getName());
		}
		l.clear();
		try {
			l = gap.shippingcharges(itemsList);

		} catch (Exception e) {

			e.printStackTrace();
		}
		session.setAttribute("bill", l);
	}
}