package com.app;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.app.dao.SearchServiceDAO;
import com.app.dao.impl.SearchServiceDAOImpl;
import com.app.exception.BusinessException;
import com.app.model.Customer;
import com.app.search.service.impl.SearchServiceImpl;
import com.search.service.SearchService;



public class Main {
	private static Logger log = Logger.getLogger(Main.class);
	public static void main(String[] args) {
		SearchServiceDAO searchServiceDAO=new SearchServiceDAOImpl();
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		log.info("Hey,Welcome");
		log.info("Please choose from below options");
		int ch=0;
		do {
		log.info("-----------------------------");
		log.info("1) Login as a User");
		log.info("2) Employee login");
		log.info("3) New User?Register your self");
		log.info("-----------------------------");
		try {
			ch = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
		}
		switch(ch) {
		case 1:	
			log.info("-----------------------------");
			log.info("        User Login           ");
			log.info("-----------------------------");
			
			SearchService search=new SearchServiceImpl();
			log.info("Enter your User-Name");
			String userName=sc.nextLine();
			log.info("Enter your Password");
			String u_pwd=sc.nextLine();
			try {
				search.LoginService(userName,u_pwd);
				}
				catch(BusinessException e){
					log.warn(e);
				}
			try {
			int success=search.LoginService(userName,u_pwd);
			if(success==1) {
				int userChoice=0;
				do {
				log.info("-----------------------------");
				log.info("Logged-in as: "+userName);
				log.info("-----------------------------");
				log.info("Select from below options");
				log.info("1)  View the list of products with price."); 
				log.info("2) Add a product to the cart");
				log.info("3) View Cart");
				log.info("4) Place Order");
				log.info("5)Mark my order as received");
				 userChoice=Integer.parseInt(sc.nextLine());
				switch(userChoice) {
				case 1:
					try {
						searchServiceDAO.viewProducts();
						}
						catch(BusinessException e){
							log.warn(e);
						}
					continue;
				case 2:
					//Adding products to cart
					log.info("Please select the product ID to choose");
					int productId=Integer.parseInt(sc.nextLine());
					log.info("please enter the quantity");
					int quantity=Integer.parseInt(sc.nextLine());
					try {
						search.addProduct(productId,quantity);
					}
					catch(BusinessException e){
						log.warn(e);
					}
					continue;
				case 3:
					//View Cart
					try {
						searchServiceDAO.viewCart();
						}
						catch(BusinessException e){
							log.warn(e);
						}
					break;
				case 4:
					//Confirm order
					try {
						searchServiceDAO.confirmOrder();
						}
						catch(BusinessException e){
							log.warn(e);
						}
					break;
				case 5:
						//Mark order as Received
					try {
						searchServiceDAO.markOrder();
						}
						catch(BusinessException e){
							log.warn(e);
						}
					break;
				default:
					log.info("please select a valid option");
					break;
				}
				}
				while(userChoice!=6);
				
				
				log.info("-----------------------------");
			}
			else {
				log.info("Invalid Credentials");
			}
			}
			catch(BusinessException e ) {
				log.warn(e.getMessage());
			}
			break;
		case 2:
			int employeeChoice=0;
			int flag=0;
			log.info("Employee login");
			try {
				
				flag=searchServiceDAO.employeeLogin();
				}
				catch(BusinessException e){
					log.warn(e);
				}
				if(flag==1) {
						log.info("Employee Login Succcessfull");
						log.info("-------------------------------");
						log.info("Please choose from below options");
						log.info("1) Insert into products");
						log.info("2) Search customers by Filters:");
						employeeChoice=Integer.parseInt(sc.nextLine());
						switch(employeeChoice) {
							case 1:
								try {
							log.info("Enter product name");
							String productName=sc.nextLine();
							log.info("Enter product price");
							int productPrice=Integer.parseInt(sc.nextLine());
							log.info("Enter product category");
							String productCategory=sc.nextLine();
							searchServiceDAO.intoProducts(productName,productPrice,productCategory);
							}
							catch(BusinessException e){
								log.warn(e);
							}
								break;
							case 2:
								log.info("View Customers by:");
								log.info("                  1) Customer Name");
								log.info("                  2) Customer Mail");
								log.info("                  3) Customer ID");
								int empChoice=Integer.parseInt(sc.nextLine());
								switch(empChoice) {
								case 1:
									try {
										log.info("please enter a part of the customer name");
										String subName=sc.nextLine();
										searchServiceDAO.customersByName(subName);
										}
										catch(BusinessException e){
											log.warn(e);
										}
									break;
								case 2:
									try {
										String subMail=sc.nextLine();
										searchServiceDAO.customersByMail(subMail);
										}
										catch(BusinessException e){
											log.warn(e);
										}
									break;
								case 3:
									try {
										int userId=Integer.parseInt(sc.nextLine());
										searchServiceDAO.customersById(userId);
										}
										catch(BusinessException e){
											log.warn(e);
										}
									break;
								default:
									break;
								}
							break;
							default:
								log.info("please Enter Valid Choice");
				}
		}
		
			break;
		case 3:
			SearchService searchService=new SearchServiceImpl();
			log.info("User Registration");
			Customer customer=new Customer();
			log.info("Please enter: User id,Password,User Name and User email");
			log.info("enter customer ID");
			customer.setCust_id(Integer.parseInt(sc.nextLine()));
			log.info("Enter password");
			customer.setCust_pwd(sc.nextLine());
			log.info("Enter UserName");
			customer.setCust_name(sc.nextLine());
			log.info("Enter emailID");
			customer.setCust_email(sc.nextLine());
			try {
			searchService.RegisterService(customer);
			}
			catch(BusinessException e){
				log.warn(e);
			}
			break;
		default:
			log.warn(
					"Invalid Search Option... Choice should be only number between 1-9 only. Kindly Retry ");
			break;
		}
		}
	 while (ch != 4);	
	}

}
