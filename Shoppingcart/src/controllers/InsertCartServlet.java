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

import dao.DAObridge;
import dbcon.StoreDAO;
import model.BillingDetails;
import model.Items;

@WebServlet("/InsertCartServlet")

public class InsertCartServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<BillingDetails> l = new ArrayList<>();
		StoreDAO gap = DAObridge.get();
		HttpSession session = request.getSession();
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
			l = gap.proposedbill(itemsList);

		} catch (Exception e) {

			e.printStackTrace();
		}
		session.setAttribute("bill", l);
	}
}