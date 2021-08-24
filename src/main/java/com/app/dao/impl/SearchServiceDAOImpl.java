package com.app.dao.impl;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

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
	// UserRegistration
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
			int productPrice=0,flag=0;
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
		try(Connection connection=MySqlDbConnection.getConnection()){
			int flag=0;
			String sql="UPDATE shop.order SET order_status = 'Received' WHERE order_cust_id = ?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, customerIdref);
			flag=preparedStatement.executeUpdate();
			if(flag!=0) {
				log.info("Order marked as received");
			}
		}
		catch(ClassNotFoundException | SQLException e){
			log.error(e);
			throw new BusinessException("Internal Error");
		}
	}
	//Employee login
	public int employeeLogin() throws BusinessException{
		int flag=0;
		String loginId="empLogin";
		String password="passWord";
		if(loginId.equals("empLogin")&&password.equals("passWord")){
			flag=1;
		}
		return flag;
	}
	
	//Insert into Products
	public void intoProducts(String productName, int productPrice,String productCategory) throws BusinessException{
		try(Connection connection=MySqlDbConnection.getConnection()){
		String sql="insert into product (product_name,p_price,p_category) values (?,?,?)";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setString(1, productName);
		preparedStatement.setInt(2, productPrice);
		preparedStatement.setString(3, productCategory);
		preparedStatement.execute();
		log.info("Product added Successfully");
		}
		catch(ClassNotFoundException | SQLException e){
			log.error(e);
			throw new BusinessException("Internal Error");
		}
		
	}
	//View by customer name
	public void customersByName(String subName) throws BusinessException{
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="SELECT cust_pwd,cust_email,cust_name from customer where cust_name like ?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, "%"+subName+"%");
			ResultSet resultSet=preparedStatement.executeQuery();
			if(!resultSet.next()) {log.info("No such users found");
			
			}
			log.info("--------------------------------------------------------------------------");
			log.info("CustomerPassword        CustomerEmail               CustomerName");
			log.info("--------------------------------------------------------------------------");
			while(resultSet.next()) {
				log.info(resultSet.getString("cust_pwd")+"            "+resultSet.getString("cust_email")+"                  "+resultSet.getString("cust_name"));
			}
		}
		catch(ClassNotFoundException | SQLException e){
			log.error(e);
			throw new BusinessException("Internal Error");
		}
	}
	public void customersByMail(String subMail) throws BusinessException{
		//View by customer mail
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="SELECT cust_pwd,cust_email,cust_name from customer where cust_email like ?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, "%"+subMail+"%");
			ResultSet resultSet=preparedStatement.executeQuery();
			if(!resultSet.next()) {log.info("No such users found");
			
			}
			log.info("--------------------------------------------------------------------------");
			log.info("CustomerPassword        CustomerEmail               CustomerName");
			log.info("--------------------------------------------------------------------------");
			while(resultSet.next()) {
				log.info(resultSet.getString("cust_pwd")+"            "+resultSet.getString("cust_email")+"                  "+resultSet.getString("cust_name"));
			}
		}
		catch(ClassNotFoundException | SQLException e){
			log.error(e);
			throw new BusinessException("Internal Error");
		}
	}
	public void customersById(int id) throws BusinessException{
		//View by customerId
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="SELECT cust_pwd,cust_email,cust_name from customer where cust_id= ?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(!resultSet.next()) {log.info("No such users found");
			}
			else {
			log.info("--------------------------------------------------------------------------");
			log.info("CustomerPassword        CustomerEmail               CustomerName");
			log.info("--------------------------------------------------------------------------");
			if(resultSet!=null) {
				log.info(resultSet.getString("cust_pwd")+"            "+resultSet.getString("cust_email")+"                  "+resultSet.getString("cust_name"));
			}
			while(resultSet.next()) {
				log.info(resultSet.getString("cust_pwd")+"            "+resultSet.getString("cust_email")+"                  "+resultSet.getString("cust_name"));
			}
			}
		}
		catch(ClassNotFoundException | SQLException e){
			log.error(e);
			throw new BusinessException("Internal Error");
		}
		
	}
}