package com.app.model;

public class Cart {
	private int cust_cart_id;
	private int cart_value;
	
	private String order_status;
	private int cart_product_id;
	private int product_quantity;
	private Customer customer;
	private Order order;
	private Product product;
	
	public int getCust_cart_id() {
		return cust_cart_id;
	}
	public void setCust_cart_id(int cust_cart_id) {
		this.cust_cart_id = cust_cart_id;
	}
	public int getProduct_quantity() {
		return product_quantity;
	}
	public void setProduct_quantity(int product_quantity) {
		this.product_quantity = product_quantity;
	}
	public int getCart_value() {
		return cart_value;
	}
	public int getCart_product_id() {
		return cart_product_id;
	}
	public void setCart_product_id(int cart_product_id) {
		this.cart_product_id = cart_product_id;
	}
	public void setCart_value(int cart_value) {
		this.cart_value = cart_value;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
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
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	
	@Override
	public String toString() {
		return "Cart [cust_cart_id=" + cust_cart_id + ", cart_value=" + cart_value + ", order_status=" + order_status
				+ ", cart_product_id=" + cart_product_id + ", product_quantity=" + product_quantity + ", customer="
				+ customer + ", order=" + order + ", product=" + product + "]";
	}
	public Cart() {
		// TODO Auto-generated constructor stub
	}
	
}
