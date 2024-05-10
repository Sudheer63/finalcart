package model;

public class BillingDetails {
	private int proid;
	private String prodname;
	private float price;
	private int quantity;
	private double pricewithoutgst;
	private double ingst;
	private double totalpriceperquantity;
	private double sc;
	private double scgst;
	private double finalprice;

	public BillingDetails(int proid, String prodname, float price, int quantity, double pricewithoutgst, double ingst,
			double totalpriceperquantity, double sc, double scgst, double finalprice) {
		this.setProid(proid);
		this.setProdname(prodname);
		this.setPrice(price);
		this.setQuantity(quantity);
		this.setPricewithoutgst(pricewithoutgst);
		this.setIngst(ingst);
		this.setTotalpriceperquantity(totalpriceperquantity);
		this.setSc(sc);
		this.setScgst(scgst);
		this.setFinalprice(finalprice);
	}

	public int getProid() {
		return proid;
	}

	public void setProid(int proid) {
		this.proid = proid;
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

	public double getPricewithoutgst() {
		return pricewithoutgst;
	}

	public void setPricewithoutgst(double pricewithoutgst) {
		this.pricewithoutgst = pricewithoutgst;
	}

	public double getIngst() {
		return ingst;
	}

	public void setIngst(double ingst) {
		this.ingst = ingst;
	}

	public double getTotalpriceperquantity() {
		return totalpriceperquantity;
	}

	public void setTotalpriceperquantity(double totalpriceperquantity) {
		this.totalpriceperquantity = totalpriceperquantity;
	}

	public double getSc() {
		return sc;
	}

	public void setSc(double sc) {
		this.sc = sc;
	}

	public double getScgst() {
		return scgst;
	}

	public void setScgst(double scgst) {
		this.scgst = scgst;
	}
}
