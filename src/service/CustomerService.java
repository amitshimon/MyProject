package service;

import java.text.ParseException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Coupon;
import beans.CouponType;
import exception.ObjectDontExistException;
import exception.ThereIsNoSuchPrice;
import exception.UpdateFailsException;
import facade.CustomerFacade;
import web.beans.WebCustomer;

/*The customer class in charge for all of the methods i like the 
 * customer to see. The class have two parameters, http request   
 * from the login servlet and customer facade. */
@Path("customer")
public class CustomerService {

	@Context
	private HttpServletRequest req;
	private CustomerFacade customerFacade;

	/* Empty constructor */
	public CustomerService() {

	}

	/*
	 * This method in charge for getting the id for purchase a coupon and moving
	 * him to the purchased method in the customer facade
	 */
	@Path("/purchasedCoupons")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	public void purchasedCoupons(@QueryParam("id") long id) {

		HttpSession s = req.getSession(false);
		customerFacade = (CustomerFacade) s.getAttribute("facade");
		Coupon coupon = new Coupon();
		coupon.setId(id);

		try {
			customerFacade.purchasedCoupons(coupon);
		} catch (UpdateFailsException | ObjectDontExistException | ParseException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Customer get`s all his coupon`s from the db the method return collection
	 * with the customer details and a collection of his coupon
	 */
	@Path("getAllPurchasedCoupons")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<WebCustomer> getAllPurchasedCoupons() throws Exception {
		HttpSession s = req.getSession(false);
		customerFacade = (CustomerFacade) s.getAttribute("facade");
		return customerFacade.getAllPurchasedCoupons();
	}
	@Path("getAllCoupons")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<WebCustomer> getAllCoupons() throws Exception {
		HttpSession s = req.getSession(false);
		customerFacade = (CustomerFacade) s.getAttribute("facade");
		return customerFacade.getCoupons();
	}
	/*
	 * This method in charge for receive the type form the ui call to the get
	 * all by type method in the customer facade retrieve the customer details
	 * and the coupon list and return it to the client
	 */
	@Path("/getAllPurchasedCouponByType")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_HTML)
	public Collection<WebCustomer> getAllPurchasedCouponByType(@QueryParam("type") String type) {
		HttpSession s = req.getSession(false);
		customerFacade = (CustomerFacade) s.getAttribute("facade");
		CouponType couponType = CouponType.valueOf(type);
		return customerFacade.getAllPurchasedCouponByType(couponType);

	}
	/*
	 * This method in charge for receive the price form the ui call to the get
	 * all by price method in the customer facade retrieve the customer details
	 * and the coupon list and return it to the client
	 */
	@Path("/getAllPurchasedCouponByPrice")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_HTML)
	public Collection<WebCustomer> getAllPurchasedCouponByPrice(@QueryParam("price") double price,
			@QueryParam("price2") double price2) {
		HttpSession s = req.getSession(false);
		customerFacade = (CustomerFacade) s.getAttribute("facade");
		try {
			return customerFacade.getAllPurchasedCouponByPrice(price, price2);
		} catch (ThereIsNoSuchPrice e) {
			e.printStackTrace();
		}

		return null;

	}

}
