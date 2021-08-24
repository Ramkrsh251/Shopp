package com.app.search.service.impl;

import com.app.dao.SearchServiceDAO;
import com.app.dao.impl.SearchServiceDAOImpl;
import com.app.exception.BusinessException;
import com.app.model.Customer;
import com.search.service.SearchService;

public class SearchServiceImpl implements SearchService {
	private SearchServiceDAO searchServiceDAO=new SearchServiceDAOImpl();
	public int  RegisterService(Customer customer)throws BusinessException{
		int flag=0;
		String name=customer.getCust_name();
		String mail=customer.getCust_email();
		String password=customer.getCust_pwd();
		if(name.length()!=0 && name.matches("[a-zA-Z]{3,20}")) {
			if(mail.matches("[a-z0-9]{5,20}[@gmail.com]{10}")) {
				if(password.matches("[A-Z]{1}[a-zA-Z0-9]{7,30}")) {
			 flag=searchServiceDAO.RegisterService(customer);
				}
				else {
					throw new BusinessException("Invalid password... "+password+"/n1.Password must be of length 8 \n2.First letter must be in uppercase"
							+ "\n3.No special characters are allowed");
				}
			}
			else {
				throw new BusinessException("Invalid Mail Address "+mail+"\nOnly gmail address can be used");
				}
		}
		
		else {
			throw new BusinessException("Invalid name"+name+"\n Name should be of minimum 3 characters"
					+"\nAnd numbers and special characters are not allowed");
		}
		return flag;
	
	}
	public int LoginService(String customerId,String customerPwd) throws BusinessException{
		int flag=0;
	
		flag=searchServiceDAO.LoginService(customerId,customerPwd);
		return flag;
		}
	public int addProduct(int ProductId,int quantity) throws BusinessException{
		int flag=0;
		flag=searchServiceDAO.addProduct(ProductId,quantity);
		return flag;
	}
}