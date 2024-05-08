package model;

public class BillingDetails {
	private int proid;
	private String prodname;
	private float price;
	private int quantity;
	private double totalbaseprice;
	private double shipchg;
	private double gst;
	private double finalprice;

	public BillingDetails(int proid, String prodname, float price, int quantity, double totalbaseprice, double shipchg,
			double gst, double finalprice) {
		this.setProid(proid);
		this.setProdname(prodname);
		this.setPrice(price);
		this.setQuantity(quantity);
		this.setShipchg(shipchg);
		this.setGst(gst);
		this.setTotalbaseprice(totalbaseprice);
		this.setFinalprice(finalprice);
	}

	public int getProid() {
		return proid;
	}

	public void setProid(int proid) {
		this.proid = proid;
	}

	public double getTotalbaseprice() {
		return totalbaseprice;
	}

	public void setTotalbaseprice(double totalbaseprice) {
		this.totalbaseprice = totalbaseprice;
	}

	public double getShipchg() {
		return shipchg;
	}

	public void setShipchg(double shipchg) {
		this.shipchg = shipchg;
	}

	public double getGst() {
		return gst;
	}

	public void setGst(double gst) {
		this.gst = gst;
	}

	public double getFinalprice() {
		return finalprice;
	}

	public void setFinalprice(double finalprice) {
		this.finalprice = finalprice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getProdname() {
		return prodname;
	}

	public void setProdname(String prodname) {
		this.prodname = prodname;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
