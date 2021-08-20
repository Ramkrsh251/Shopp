package com.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.app.Main;
import com.app.dao.SearchServiceDAO;
import com.app.dao.dbutil.MySqlDbConnection;
import com.app.exception.BusinessException;
import com.app.model.Customer;
	
public class SearchServiceDAOImpl implements SearchServiceDAO{
	static int customerIdref=0;
	static int cartPrice=0;
	private static Logger log = Logger.getLogger(SearchServiceDAOImpl.class);
	//Reg
	public int  RegisterService(Customer customer)throws BusinessException{
		int success=0;
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="insert into customer values(?,?,?,?)";
		     PreparedStatement preparedStatement=connection.prepareStatement(sql);
		     preparedStatement.setInt(1,customer.getCust_id());
		     preparedStatement.setString(2, customer.getCust_pwd());
		     preparedStatement.setString(3, customer.getCust_name());
		     preparedStatement.setString(4, customer.getCust_email());
		   success=preparedStatement.executeUpdate();
		    log.info("Successfully Registered");
		}
		catch(ClassNotFoundException | SQLException e){
			log.error(e);
		}
		return success;
	}
	//Login
	
	Customer customer=null;
	public int LoginService(String userName,String pwd)throws BusinessException {
		int success=0;
		
		try(Connection connection=MySqlDbConnection.getConnection()){
		String sql="select cust_name,cust_Id from Customer where cust_name=? and cust_pwd=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setString(1,userName);
		preparedStatement.setString(2, pwd);
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next()) {
			customer=new Customer();
			customer.setCust_name(resultSet.getString("cust_name"));
			customerIdref=resultSet.getInt("cust_id");
		}
		if(customer!=null) {success=1;}
		}
		catch(ClassNotFoundException | SQLException e){
			log.error(e);
			throw new BusinessException("Internal Error");
		}
		
		return success;
	}
	//View products
	public int viewProducts()throws BusinessException{
		try(Connection connection=MySqlDbConnection.getConnection()){
			
			String sql="Select p_id,product_name,p_price,p_category from product";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet!=null) {
				log.info("------------------------------------------------------------------------");
				log.info("Product id |Product Name|Product price|product Category" );
				log.info("------------------------------------------------------------------------");
			}
			while(resultSet.next()) {
				log.info(resultSet.getString("p_id")+"          "+resultSet.getString("product_name")+"          "+resultSet.getInt("p_price")+"          "+resultSet.getString("p_category"));
				
			}
			log.info("------------------------------------------------------------------------");
		}
		catch(ClassNotFoundException | SQLException e){
			log.error(e);
			throw new BusinessException("Internal Error");
		}
		return 1;
	}
	
	
	//Add products
	public int addProduct(int productId,int quantity) throws BusinessException{
		try(Connection connection=MySqlDbConnection.getConnection()){
			int productPrice=0;
			
			int flag=0;
			String sql="Select p_price from product where p_id=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1,productId);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				productPrice=resultSet.getInt("p_price");
				
			}
			cartPrice+=productPrice*quantity;
			sql="insert into cart values (?,?,?,?,?)";
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, customerIdref);
			preparedStatement.setInt(2, cartPrice);
			preparedStatement.setString(3, "Yet to confirm");
			preparedStatement.setInt(4, productId);
			preparedStatement.setInt(5,quantity);
			flag=preparedStatement.executeUpdate();
			
			log.info("Product with product id :"+productId+" added successfully ");
		}
		catch(ClassNotFoundException | SQLException e) {
			log.error(e);
		}
		return 1;
	}
	
	
	//View Cart
	public void viewCart()throws BusinessException{
			
		try(Connection connection=MySqlDbConnection.getConnection()){	
			String sql="select product_id,product_quantity,order_status,cart_value from cart where cust_cart_id=?";
			int flag=0;
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, customerIdref);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(!resultSet.next()) {log.info("Your cart is empty,Please add products to continue...");}
			else {
			log.info("Customer ID | Product Id | Product Quantity |Order Status |Cart Value ");
			if(resultSet!=null) {log.info(customerIdref+"               "+resultSet.getInt("product_id")+"                    "+resultSet.getInt("product_quantity")+"      "+resultSet.getString("Order_status")+"                    "+resultSet.getInt("cart_value"));}
			while(resultSet.next()) {
			log.info(customerIdref+"               "+resultSet.getInt("product_id")+"                    "+resultSet.getInt("product_quantity")+"      "+resultSet.getString("Order_status")+"                    "+resultSet.getInt("cart_value"));
			cartPrice+=resultSet.getInt("cart_value");
			}
			
			}
			}	
		catch(ClassNotFoundException | SQLException e){
			log.error(e);
			throw new BusinessException("Internal Error");
		}
		
	}
	//Confirm Order
	public void confirmOrder()throws BusinessException {
		try(Connection connection=MySqlDbConnection.getConnection()){
			int flag=0;
			String sql="UPDATE cart SET order_status = 'Confirmed' WHERE cust_cart_id = ?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, customerIdref);
			flag=preparedStatement.executeUpdate();
			if(flag>=1) {
			log.info("Successfully Ordered");
			sql="insert into shop.order values(?,?,?)";
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,"Confirmed");
			preparedStatement.setInt(2,customerIdref);
			preparedStatement.setInt(3,cartPrice);
			flag=preparedStatement.executeUpdate();
			log.info("Your Order value is: "+cartPrice);
			}
			
		}
		catch(ClassNotFoundException | SQLException e){
			log.error(e);
			throw new BusinessException("Internal Error");
		}
		}
	
	//Mark Received
	public void markOrder() throws BusinessException{
		
	}
}
	

