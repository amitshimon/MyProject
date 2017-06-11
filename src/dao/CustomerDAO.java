package dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import beans.Coupon;
import beans.Customer;
import exception.CreateFailException;
import exception.GeneralException;
import exception.ObjectAlreadyExistException;
import exception.ObjectDontExistException;
import exception.RemoveFail;
import exception.UpdateFailsException;
import web.beans.WebCustomer;

public interface CustomerDAO {

	public String createCustomer(Customer c) throws SQLException, GeneralException, CreateFailException;

	public String removeCustomer(Customer c) throws SQLException, GeneralException, RemoveFail;

	public String updateCustomer(Customer c) throws SQLException, ObjectDontExistException, UpdateFailsException;

	public WebCustomer getCustomer(long id) throws SQLException, ObjectDontExistException, GeneralException;

	public ArrayList<WebCustomer> getAllCustomers() throws SQLException, ObjectAlreadyExistException, ObjectDontExistException, Exception;

	public Collection<Coupon> getCoupons(long id) throws  ObjectDontExistException,ObjectDontExistException, GeneralException, ParseException;

	public Customer login(String password, String custName) throws SQLException, ObjectAlreadyExistException, GeneralException;

	Collection<Coupon> getCoupons() throws ObjectDontExistException, GeneralException;
}
