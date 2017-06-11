package service;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import beans.Coupon;
import beans.CouponType;
import convert.object.ConvertToString;
import facade.CompanyFacade;
import web.beans.WebCompany;

/*company service class in charge for all the method
 * of the company can use,The class have two parameters,
 *  http request from the login servlet and admin facade*/
@Path("company")
public class CompanyService {

	@Context
	private HttpServletRequest req;
	private HttpServletResponse res;
	private CompanyFacade companyFacade;

	/* Empty constructor */
	public CompanyService() {

	}

	/*
	 * Update coupon method get the call from the UI with parameters that send
	 * by the client , then the method invoke the update method in company
	 * facade.
	 */
	@Path("/updateCoupon")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCoupon(@QueryParam("id") long id, @QueryParam("amount") int amount,
			@QueryParam("message") String massage, @QueryParam("title") String title, @QueryParam("price") double price,
			@QueryParam("endDate") String endDate) {
		HttpSession httpSession = req.getSession(false);
		companyFacade = (CompanyFacade) httpSession.getAttribute("facade");
		// CompanyFacade companyFacade = new CompanyFacade();
		Coupon coupon = new Coupon();
		coupon.setId(id);
		coupon.setEndDate(endDate);
		coupon.setMassage(massage);
		coupon.setPrice(price);
		coupon.setTitle(title);
		coupon.setAmount(amount);
		try {
			companyFacade.updateCoupon(coupon);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	/*
	 * Remove coupon method get the call from the UI with parameters that send
	 * by the client , then the method invoke the remove method in company
	 * facade.
	 */
	@Path("/removeCoupon")
	@GET
	@Consumes(MediaType.TEXT_HTML)
	public void removeCoupon(@QueryParam("id") long id) {
		HttpSession httpSession = req.getSession(false);
		companyFacade = (CompanyFacade) httpSession.getAttribute("facade");
		Coupon coupon = new Coupon();
		coupon.setId(id);
		try {
			companyFacade.removeCoupon(coupon);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Get coupon method get the call from the UI with parameters that send by
	 * the client, then the method invoke the get coupon method in company
	 * facade if success method return JSON array with the company and the
	 * company details to the client
	 */
	@Path("/getCoupon")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_HTML)
	public ArrayList<WebCompany> getCoupon(@QueryParam("id") long id) {
		HttpSession httpSession = req.getSession(false);
		companyFacade = (CompanyFacade) httpSession.getAttribute("facade");
		try {
			return companyFacade.getCoupon(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * Get all coupons method get the call from the UI, then the method invoke
	 * the get coupon method in company facade if success method return JSON
	 * array with the company and the company details to the client
	 */
	@Path("/getAllCoupons")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<WebCompany> getAllCoupons() {
		HttpSession httpSession = req.getSession(false);
		companyFacade = (CompanyFacade) httpSession.getAttribute("facade");
		try {
			return companyFacade.getAllCoupons();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * Get all coupons by type method get the call from the UI,with parameters
	 * that send by the client then the method invoke the get coupons method in
	 * company facade if success method return JSON array with the company and
	 * the company details to the client
	 */
	@Path("/getCouponByType")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_HTML)
	public ArrayList<WebCompany> getCouponByType(@QueryParam("type") CouponType couponType) {
		HttpSession httpSession = req.getSession(false);
		companyFacade = (CompanyFacade) httpSession.getAttribute("facade");
		try {

			return companyFacade.getCouponByType(couponType);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Create coupon method get the call from the UI,with parameters that send
	 * by the client. Then the method invoke the create coupon method in company
	 * facade, if success method return string with success.
	 */
	@Path("/createCoupon")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String createCoupon(@FormDataParam("file") InputStream uploadInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetails, @FormDataParam("price") double price,
			@FormDataParam("message") String message, @FormDataParam("type") String type,
			@FormDataParam("amount") int amount, @FormDataParam("startDate") String startDate,
			@FormDataParam("endDate") String endDate, @FormDataParam("title") String title) {
		HttpSession httpSession = req.getSession(false);
		companyFacade = (CompanyFacade) httpSession.getAttribute("facade");
		ConvertToString convertToString = new ConvertToString();
		Coupon coupon = new Coupon();
		coupon.setAmount(amount);
		coupon.setEndDate(endDate);
		coupon.setStartDate(startDate);
		coupon.setMassage(message);
		coupon.setPrice(price);
		coupon.setTitle(title);
		coupon.setType(CouponType.valueOf(type));
		coupon.setImag(convertToString.saveToDisc(uploadInputStream, fileDetails));
		try {
			if (!(companyFacade.createCoupon(coupon) == null)) {
				res.sendRedirect("/companySPA.html");
			}
		} catch (Exception e) {

		}
		return message;

	}

}
