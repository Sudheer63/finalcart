package dbcon;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.BillingDetails;
import model.Items;
import model.Products;

public interface StoreDAO {

	ArrayList<String> getAllCategories();

	ArrayList<Products> getAllProducts() throws SQLException;

	ArrayList<Products> getAllProductsId(String id) throws SQLException;

	ArrayList<Products> getAllProductSort(String catid) throws SQLException;

	ArrayList<Products> getCatProductsSort(String catid, String sortid) throws SQLException;

	void createOrder(int i, List<Items> i2) throws SQLException;

	boolean checkpincode(String pincode, String prodid) throws SQLException;

	ArrayList<BillingDetails> shippingcharges(List<Items> list) throws SQLException;
}