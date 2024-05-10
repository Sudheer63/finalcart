package dbcon;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.BillingDetails;
import model.Items;
import model.Products;

public class DB_Properties implements StoreDAO {
	private Connection con = Dbcon.getDbcon();
	private PreparedStatement ps;
	private ResultSet rs;
	private ArrayList<String> categories = new ArrayList<>();
	private ArrayList<Products> products = new ArrayList<>();
	private CallableStatement cs;

	public ArrayList<String> getAllCategories() {
		try {
			cs = con.prepareCall("{call getAllCategories()}");
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				categories.add(rs.getInt(1) + "");
				categories.add(rs.getString(2));
			}
			rs.close();
			cs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}

	@Override
	public ArrayList<Products> getAllProducts() throws SQLException {

		cs = con.prepareCall("{call getAllProd()}");
		rs = cs.executeQuery();
		products = getProductsFromResultSet(rs);
		rs.close();
		cs.close();
		return products;
	}

	@Override
	public ArrayList<Products> getAllProductsId(String id) throws SQLException {

		cs = con.prepareCall("{call getProdByCat(?)}");
		cs.setInt(1, Integer.parseInt(id));
		rs = cs.executeQuery();
		products = getProductsFromResultSet(rs);
		rs.close();
		cs.close();
		return products;
	}

	public ArrayList<Products> getProductsFromResultSet(ResultSet rs) {
		try {
			while (rs.next()) {
				Products p = new Products();
				p.setProduct_id(rs.getInt("proid"));
				p.setProduct_name(rs.getString("name"));
				p.setProduct_price(rs.getInt("price"));
				p.setProduct_image(rs.getString("imgpath"));
				p.setProduct_catid(rs.getInt("catid"));
				products.add(p);
				System.out.println(rs.getInt("proid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public ArrayList<Products> getAllProductSort(String sortid) throws SQLException {
		cs = con.prepareCall("{call getAllProdSort(?)}");
		cs.setInt(1, Integer.parseInt(sortid));
		rs = cs.executeQuery();
		products = getProductsFromResultSet(rs);
		rs.close();
		cs.close();
		return products;
	}

	@Override
	public ArrayList<Products> getCatProductsSort(String catid, String sortid) throws SQLException {
		cs = con.prepareCall("{call getCatProdSort(?,?)}");
		cs.setInt(1, Integer.parseInt(catid));
		cs.setInt(2, Integer.parseInt(sortid));
		rs = cs.executeQuery();
		products = getProductsFromResultSet(rs);
		rs.close();
		cs.close();
		return products;
	}

	private double getgst(int proid) throws SQLException {
		cs = con.prepareCall("{?=call getgst(?)}");
		cs.registerOutParameter(1, Types.DECIMAL);
		cs.setInt(2, proid);
		cs.execute();
		double gst = cs.getBigDecimal(1).doubleValue();
		return gst;
	}

	// @Override
	// public void createOrder(int cust, List<Items> list) throws SQLException {
	// String insertOrderQuery = "INSERT INTO orders225 (order_date, price, custid) VALUES (CURRENT_DATE, ?, ?)
	// RETURNING orderid";
	// ps = con.prepareStatement(insertOrderQuery);
	// ps.setLong(1, calculateTotalPrice(list));
	// ps.setInt(2, cust);
	// rs = ps.executeQuery();
	//
	// int orderId = -1;
	// if (rs.next()) {
	// orderId = rs.getInt(1);
	// }
	//
	// String insertOrderProductQuery = "INSERT INTO orderproducts225 (orderid, prodid, quantity, price) VALUES (?, ?,
	// ?, ?)";
	// ps = con.prepareStatement(insertOrderProductQuery);
	// for (Items i : list) {
	// ps.setInt(1, orderId);
	// ps.setInt(2, i.getProid());
	// ps.setInt(3, i.getQuantity());
	// ps.setFloat(4, i.getPrice());
	// ps.addBatch();
	// }
	// ps.executeBatch();
	//
	// }

	public ArrayList<BillingDetails> proposedbill(List<Items> list) throws SQLException {
		ArrayList<BillingDetails> bill = new ArrayList<BillingDetails>();
		double priceratio = 0;
		double pricetoquantity = 0;
		double gst = 0;
		double sc_individual_gst = 0;
		double prodwithoutgst = 0;
		double ingst = 0;
		double count = gettotal(list);
		double total_shipping_charges = getshippingcharges(count);
		double sc_individual = 0;
		double total_individual = 0;
		for (Items i : list) {

			gst = getgst(i.getProid());

			// prod inclusive gst calculation
			prodwithoutgst = (i.getPrice() / (1 + (gst / 100)));
			ingst = i.getPrice() - prodwithoutgst;

			// individual shipping charges
			pricetoquantity = i.getPrice() * i.getQuantity();
			priceratio = pricetoquantity / count;
			System.out.println(priceratio);
			sc_individual = total_shipping_charges * priceratio;
			System.out.println(sc_individual);
			// individual shipping charges gst
			sc_individual_gst = sc_individual * (gst / 100);
			System.out.println(sc_individual_gst + "      a");

			total_individual = pricetoquantity + sc_individual + sc_individual_gst;
			bill.add(new BillingDetails(i.getProid(), i.getName(), i.getPrice(), i.getQuantity(),
					Math.round(prodwithoutgst), Math.round(ingst), Math.round(pricetoquantity),
					Math.round(sc_individual), Math.round(sc_individual_gst), Math.round(total_individual)));
			cs.close();
		}

		return bill;
	}

	private double getshippingcharges(double count) throws SQLException {
		double sc = 0;
		cs = con.prepareCall("{?=call getshipping(?)}");
		cs.registerOutParameter(1, Types.NUMERIC);
		cs.setInt(2, (int) count);
		cs.execute();
		sc = cs.getBigDecimal(1).doubleValue();
		return sc;
	}

	private double gettotal(List<Items> list) {
		double count = 0;
		for (Items i : list) {
			count += i.getPrice() * i.getQuantity();

		}
		return count;
	}

	public boolean checkpincode(String pincode, String prodid) throws SQLException {
		System.out.println("entered");
		int pin = Integer.parseInt(pincode);
		int id = Integer.parseInt(prodid);
		System.out.println(pin + "   " + id);
		cs = con.prepareCall("{?=call IsDeliveryAvailable(?,?)}");
		cs.registerOutParameter(1, Types.BOOLEAN);
		cs.setInt(2, id);
		cs.setInt(3, pin);
		cs.execute();
		boolean t = cs.getBoolean(1);
		System.out.println(t);
		return t;

	}

	@Override
	public void createOrder(int i, List<Items> i2) throws SQLException {
		// TODO Auto-generated method stub

	}

	public double discountcoupon(double amount, double couponcode) throws SQLException {
		cs = con.prepareCall("{?=call getdiscount(?,?)}");
		cs.registerOutParameter(1, Types.NUMERIC);
		cs.setDouble(2, couponcode);
		cs.setDouble(3, amount);
		cs.execute();
		double discount = cs.getBigDecimal(1).doubleValue();
		if (discount > 0) {
			updatecouponreport();
		}
		return discount;

	}

	private void updatecouponreport() {
		// TODO Auto-generated method stub

	}
}