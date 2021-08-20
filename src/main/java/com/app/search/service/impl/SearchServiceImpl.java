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
		
		if(customer!=null) {
			 flag=searchServiceDAO.RegisterService(customer);
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