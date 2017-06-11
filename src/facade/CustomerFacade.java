package facade;

import java.awt.HeadlessException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import beans.Coupon;
import beans.CouponType;
import beans.Customer;
import convert.object.Convert;
import dbDao.CouponDBDAO;
import dbDao.CustomerDBDAO;
import exception.GeneralException;
import exception.InvalidLoginException;
import exception.ObjectDontExistException;
import exception.ThereIsNoSuchPrice;
import exception.UpdateFailsException;
import web.beans.WebCustomer;

public class CustomerFacade implements CouponClienFacade {

	private Customer customer;

	// class constructor
	public CustomerFacade() {

	}

	public void purchasedCoupons(Coupon coupon) throws UpdateFailsException, ObjectDontExistException, ParseException {
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		long id = coupon.getId();
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		coupon = couponDBDAO.getCoupon(id);
		coupons.add(coupon);
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		customer.setCoupons(coupons);
		int amount = coupon.getAmount();

		try {
			couponDBDAO.updateAmount(amount, id);
			customerDBDAO.insertIntoJoinTable(customer);

		} catch (UpdateFailsException e) {

			e.printStackTrace();
		}

	}

	public Collection<WebCustomer> getAllPurchasedCoupons() throws ObjectDontExistException {
		Convert convert = new Convert();
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		try {
			Collection<WebCustomer> customers = new ArrayList<WebCustomer>();
			WebCustomer webCustomer = new WebCustomer();
			webCustomer = convert.convertCustomer(customer);
			webCustomer.setCoupons(customerDBDAO.getCoupons(this.customer.getId()));
			customers.add(webCustomer);
			return customers;
		} catch (GeneralException e) {
			e.printStackTrace();
		}

		return null;

	}

	public Collection<WebCustomer> getCoupons() {
		Convert convert = new Convert();
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		Collection<WebCustomer> customers = new ArrayList<WebCustomer>();
		WebCustomer webCustomer = new WebCustomer();
		webCustomer = convert.convertCustomer(customer);
		try {
			webCustomer.setCoupons(customerDBDAO.getCoupons());
		} catch (ObjectDontExistException | GeneralException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customers.add(webCustomer);
		return customers;

	}

	public Collection<WebCustomer> getAllPurchasedCouponByType(CouponType type) {
		Convert convert = new Convert();
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		try {
			Collection<WebCustomer> customers = new ArrayList<WebCustomer>();
			WebCustomer webCustomer = new WebCustomer();
			webCustomer = convert.convertCustomer(customer);
			webCustomer.setCoupons(customerDBDAO.getCouponsByType(type, customer.getId()));
			customers.add(webCustomer);
			return customers;
		} catch (HeadlessException | ObjectDontExistException e) {

			e.printStackTrace();
		}

		return null;
	}

	public Collection<WebCustomer> getAllPurchasedCouponByPrice(double price, double price2) throws ThereIsNoSuchPrice {
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		Convert convert = new Convert();
		Collection<WebCustomer> customers = new ArrayList<WebCustomer>();
		WebCustomer webCustomer = new WebCustomer();
		webCustomer = convert.convertCustomer(customer);
		webCustomer.setCoupons(customerDBDAO.getCouponByPrice(price, price2));
		customers.add(webCustomer);
		return customers;

	}

	/* Login function for customers */
	public boolean login(String custName, String password) throws InvalidLoginException {
		boolean bol = false;
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		try {
			if (customerDBDAO.login(password, custName) != null) {
				this.customer = customerDBDAO.login(password, custName);
				bol = true;
				return bol;
			} else {
				throw new InvalidLoginException("wrong password or user name");
			}
		} catch (GeneralException e) {
			e.printStackTrace();
		}
		return bol;

	}

	@Override
	public void forgotPassword(String compName, String email) throws GeneralException, InvalidLoginException {
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		try {
			customerDBDAO.forgotPassword(email, compName);	
		} catch (Exception e) {
		}
		
	}
}
