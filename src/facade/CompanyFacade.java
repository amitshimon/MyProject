package facade;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import beans.Company;
import beans.Coupon;
import beans.CouponType;
import convert.object.Convert;
import dbDao.CompanyDBDAO;
import dbDao.CouponDBDAO;
import exception.GeneralException;
import exception.InvalidLoginException;
import exception.ObjectAlreadyExistException;
import exception.ObjectDontExistException;
import web.beans.WebCompany;

public class CompanyFacade implements CouponClienFacade {
	private Company company = null;

	// Empty class constructor
	public CompanyFacade() {

	}

	// Method calls the getCouponbyName from the CouponDBDAO that
	// return String Coupon title, method checks if title already exists if it
	// find a coupon with compatible title it throw Object already exists
	// exception and if there is no compatible title the method call the create
	// coupon in the CouponDBDAO class
	public String createCoupon(Coupon coupon) throws ObjectAlreadyExistException {
		String message = "";
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		// coupon.setCompany(company);
		String title = coupon.getTitle();
		String dbTitle = couponDBDAO.getCouponByTitle(title);
		try {
			if (title.equals(dbTitle)) {
				throw new ObjectAlreadyExistException("Coupon alredy exists");
			} else {
				message = couponDBDAO.createCoupon(coupon, company.getId());
			}

		} catch (ObjectAlreadyExistException e) {
		}
		return message;

	}

	// Method call update coupon method in the CouponDBDAO class
	public String updateCoupon(Coupon coupon) throws SQLException {
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		return couponDBDAO.updateCoupon(coupon);
	}

	// Method call remove coupon method in the CouponDBDAO class
	public String removeCoupon(Coupon coupon) throws SQLException {
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		return couponDBDAO.removeCoupon(coupon);
	}

	// Method return coupon by id, it call coupon method in the CouponDBDAO
	// class
	public ArrayList<WebCompany> getCoupon(long id) throws Exception {
		ArrayList<WebCompany> companise = new ArrayList<WebCompany>();
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		Coupon c = new Coupon();
		c.setAmount(0);
		c.setEndDate("2018-09-09");
		c.setStartDate("2017-09-01");
		c.setMassage("Buy this Coupon space");
		c.setId(0);
		c.setTitle("Buy this Coupon space");
		c.setPrice(0);
		c.setImag("Buy this Coupon space");
		c.setType(CouponType.CAMPING);
		coupons.add(couponDBDAO.getCoupon(id));
		coupons.add(c);
		company.setCoupons(coupons);
		Convert convert = new Convert();
		WebCompany webCompany = convert.convertCompany(company);
		companise.add(webCompany);
		return companise;
	}

	// Method return ArrayList<Coupon> by call the get all coupon in CouponDBDAO
	public ArrayList<WebCompany> getAllCoupons() throws SQLException {
		// company.setId(17);
		ArrayList<WebCompany> companise = new ArrayList<WebCompany>();
		Convert con = new Convert();
		WebCompany webCompany = new WebCompany();
		webCompany = con.convertCompany(company);
		CouponDBDAO couponDBDAO = new CouponDBDAO();

		try {

			webCompany.setCoupons(couponDBDAO.getAllCoupons(company.getId()));
			companise.add(webCompany);
			return companise;
		} catch (ObjectDontExistException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Method return ArrayList<Coupon> by call the get all coupon by type in
	// CouponDBDAO
	public ArrayList<WebCompany> getCouponByType(CouponType couponType) throws SQLException {

		ArrayList<WebCompany> companies = new ArrayList<WebCompany>();
		Convert con = new Convert();
		WebCompany webCompany = new WebCompany();
		webCompany = con.convertCompany(company);
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		try {
			webCompany.setCoupons(couponDBDAO.getCouponByType(couponType, company.getId()));
			companies.add(webCompany);
			return companies;
		} catch (ObjectDontExistException | ParseException e) {
			e.printStackTrace();
		}
		return null;

	}

	// Login method calls the login inside the CompanyDBDAO class
	public boolean login(String compName, String password) throws GeneralException, InvalidLoginException {
		boolean bol = false;
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		try {

			if (companyDBDAO.login(password, compName) != null) {
				this.company = companyDBDAO.login(password, compName);
				bol = true;
				return bol;
			} else {
				throw new InvalidLoginException("wrong password or user name");
			}
		} catch (SQLException e) {
		}
		return bol;
	}

	@Override
	public void forgotPassword(String compName, String email) throws GeneralException, InvalidLoginException {
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		try {
			companyDBDAO.forgotPassword(email, compName);	
		} catch (Exception e) {
		}
		
	}

}
