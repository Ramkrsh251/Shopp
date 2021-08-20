package com.app.model;

public class Customer {
 private int cust_id;
 private  String cust_pwd;
 private String cust_name;
 private String cust_email;
	
	private Cart cart;
	private Order order;
	private Product product;
	
public int getCust_id() {
	return cust_id;
}
public void setCust_id(int cust_id) {
	this.cust_id = cust_id;
}
public String getCust_pwd() {
	return cust_pwd;
}
public void setCust_pwd(String cust_pwd) {
	this.cust_pwd = cust_pwd;
}
public String getCust_name() {
	return cust_name;
}
public void setCust_name(String cust_name) {
	this.cust_name = cust_name;
}
public String getCust_email() {
	return cust_email;
}
public void setCust_email(String cust_email) {
	this.cust_email = cust_email;
}
public Customer() {
	super();
	// TODO Auto-generated constructor stub
}
public Cart getCart() {
	return cart;
}
public void setCart(Cart cart) {
	this.cart = cart;
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
	return "Customer [cust_id=" + cust_id + ", cust_pwd=" + cust_pwd + ", cust_name=" + cust_name + ", cust_email="
			+ cust_email + ", cart=" + cart + ", order=" + order + ", product=" + product + "]";
}

}
