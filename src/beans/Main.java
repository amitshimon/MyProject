package beans;

import dbDao.CustomerDBDAO;
import exception.GeneralException;
import exception.ObjectDontExistException;
import exception.ThereIsNoSuchPrice;
import web.beans.WebCustomer;

public class Main {

	public static void main(String[] args) throws ObjectDontExistException, GeneralException, ThereIsNoSuchPrice {
		CustomerDBDAO customerDAO = new CustomerDBDAO();
//		Customer c =customerDAO.login("1234", "Amit Shimon");
//		System.out.println(customerDAO.getCouponByPrice(100, 10000));
//		System.out.println(c);
//		Customer customer = new Customer();
//		customer.setCustName("asd");
//		customer.setEmail("asd");
//		customer.setPassword("asd");
//		System.out.println(customerDAO.createCustomer(customer));
		for(WebCustomer c1 : customerDAO.getAllCustomers()){
			System.out.println(c1);
		}
		
	}

}
