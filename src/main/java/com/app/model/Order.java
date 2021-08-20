package com.app.model;

public class Order {
	private String order_status ;
	private int order_cust_id;
	private int order_value;
	private Cart cart;
	private Customer customer;
	private Product product;
	
	
	
	

	public String getOrder_status() {
		return order_status;
	}





	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}





	public int getOrder_cust_id() {
		return order_cust_id;
	}





	public void setOrder_cust_id(int order_cust_id) {
		this.order_cust_id = order_cust_id;
	}





	public int getOrder_value() {
		return order_value;
	}





	public void setOrder_value(int order_value) {
		this.order_value = order_value;
	}





	public Cart getCart() {
		return cart;
	}





	public void setCart(Cart cart) {
		this.cart = cart;
	}





	public Customer getCustomer() {
		return customer;
	}





	public void setCustomer(Customer customer) {
		this.customer = customer;
	}





	public Product getProduct() {
		return product;
	}





	public void setProduct(Product product) {
		this.product = product;
	}





	@Override
	public String toString() {
		return "Order [order_status=" + order_status + ", order_cust_id=" + order_cust_id + ", order_value="
				+ order_value + ", cart=" + cart + ", customer=" + customer + ", product=" + product + "]";
	}





	public Order() {
		// TODO Auto-generated constructor stub
	}
	
}
