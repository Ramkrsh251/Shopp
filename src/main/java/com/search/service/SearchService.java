package com.search.service;

import com.app.exception.BusinessException;
import com.app.model.Customer;

public interface SearchService {
	public int  RegisterService(Customer customer)throws BusinessException;
	public int LoginService(String a,String b)throws BusinessException;
	public int addProduct(int ProductId,int quantity) throws BusinessException;
}
