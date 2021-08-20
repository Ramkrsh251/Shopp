package com.app.model;

public class Product {

	private int p_id;
	private int p_price;
	private String p_category;
	private Cart Cart;
	private Customer customer;
	private Order order;
	
	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	public int getP_price() {
		return p_price;
	}
	public void setP_price(int p_price) {
		this.p_price = p_price;
	}
	public String getP_category() {
		return p_category;
	}
	public void setP_category(String p_category) {
		this.p_category = p_category;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Cart getCart() {
		return Cart;
	}
	public void setCart(Cart cart) {
		Cart = cart;
	}
	
	@Override
	public String toString() {
		return "Product [p_id=" + p_id + ", p_price=" + p_price + ", p_category=" + p_category + ", Cart=" + Cart
				+ ", customer=" + customer + ", order=" + order + "]";
	}
	public Product() {
		// TODO Auto-generated constructor stub
	}	
}
