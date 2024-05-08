package model;

public class Items {
	private int proid;
	private int quantity;
	private float price;
	private String name;
	public Items(int proid, int quantity, float floatValue,String name) {
		this.proid = proid;
		this.quantity = quantity;
		this.price = floatValue;
		this.name=name;
	}

	public int getProid() {
		return proid;
	}

	public void setProid(int proid) {
		this.proid = proid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Product{" + "proid=" + proid + ", quantity=" + quantity + ", price=" + price + '}';
	}
}
