package dbcon;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
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
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private ArrayList<String> categories;
	private ArrayList<Products> products;
	private CallableStatement cs;

	public DB_Properties() throws Exception {
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://192.168.110.48:5432/postgres", "plf_training_admin",
				"pff123");
		categories = new ArrayList<>();
		products = new ArrayList<>();
	}

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

	private double getgst(int proid, double val) throws SQLException {
		double totalPrice = 0;
		cs = con.prepareCall("{?=call getgst(?)}");
		cs.registerOutParameter(1, Types.DECIMAL);
		cs.setInt(2, proid);
		cs.execute();
		double gst = cs.getBigDecimal(1).doubleValue();
		totalPrice = val * (gst / 100);

		cs.close();
		return Math.round(totalPrice * 100.0) / 100.0;
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

	public ArrayList<BillingDetails> shippingcharges(List<Items> list) throws SQLException {
		ArrayList<BillingDetails> bill = new ArrayList<BillingDetails>();
		double shipping = 0;
		double pricetoquantity = 0;
		double gst = 0;
		double count = 0;
		cs = con.prepareCall("{?=call getshipping(?)}");
		for (Items i : list) {
			count = 0;
			pricetoquantity = i.getPrice() * i.getQuantity();
			cs.registerOutParameter(1, Types.NUMERIC);
			cs.setInt(2, (int) pricetoquantity);
			cs.execute();
			shipping = cs.getBigDecimal(1).doubleValue();
			count = pricetoquantity + shipping;
			gst = getgst(i.getProid(), count);
			count = count + gst;
			bill.add(new BillingDetails(i.getProid(), i.getName(), i.getPrice(), i.getQuantity(), pricetoquantity,
					shipping, gst, Math.round(count * 100.0) / 100.0));
		}
		cs.close();
		return bill;
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

}