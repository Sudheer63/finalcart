package controllers;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dao.DAObridge;
import dbcon.StoreDAO;

@WebServlet("/CategoriesServlet")
public class CategoriesServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
		StoreDAO gap = DAObridge.get();
		try {
			ArrayList<String> arrc = gap.getAllCategories();
			JSONObject ob = new JSONObject();
			for (int i = 0; i < arrc.size(); i += 2) {
				int j = Integer.parseInt(arrc.get(i));
				ob.put(arrc.get(i + 1), j);

			}
			res.getWriter().write(ob.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
