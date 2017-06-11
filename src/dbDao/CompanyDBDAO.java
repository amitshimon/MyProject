package dbDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import beans.Company;
import beans.Coupon;
import beans.CouponType;
import convert.object.Convert;
import convert.object.ConvertToString;
import convert.object.Email;
import dao.CompenyDAO;
import exception.CreateFailException;
import exception.GeneralException;
import exception.InvalidLoginException;
import exception.ObjectDontExistException;
import exception.RemoveFail;
import exception.UpdateFailsException;
import web.beans.WebCompany;

public class CompanyDBDAO implements CompenyDAO {
	private ConnectionPoolSingleton con;
	private Connection connection;

	// Class constructor when initialization made connection to db
	public CompanyDBDAO() {
		con = ConnectionPoolSingleton.getInstance();
	}

	// Create company method
	@Override
	public String createCompany(Company company) throws CreateFailException {

		String companyName = company.getCompanyName();
		String password = company.getPassword();
		String email = company.getEmail();
		String sql = "INSERT INTO `company` (`company_name`, `password`, `email`)" + "VALUES ('" + companyName + "','"
				+ password + "', '" + email + "')";

		try {
			connection = con.getConnection();
			connection.createStatement().execute(sql);

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw new CreateFailException("Create Fail");
		} finally {
			con.returnConnection(connection);
		}
		String message = "Successful addition";
		return message;
	}

	// Method remove company and company coupons from db
	@Override
	public String removeCompany(Company company) throws SQLException, RemoveFail {
		long idCoupon;
		connection = con.getConnection();
		long id = company.getId();
		Coupon c = new Coupon();
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		ResultSet results = connection.createStatement()
				.executeQuery("SELECT idcoupon FROM `company_coupon` where idcompany = " + id);
		String sql = "DELETE FROM `company_coupon` WHERE `idcompany`=" + id;
		connection.createStatement().execute(sql);
		while (results.next()) {
			idCoupon = results.getLong("idcoupon");

			c.setId(idCoupon);
			couponDBDAO.removeCoupon(c);
		}
		String sql1 = "DELETE  FROM `company` WHERE `id`=" + id;

		try {

			connection.createStatement().execute(sql1);
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw new RemoveFail("Remove Company fails");
		} finally {
			con.returnConnection(connection);
		}
		String message = "Successful removal";
		return message;
	}

	// Update method for company details
	@Override
	public String updateCompany(Company company) throws UpdateFailsException {

		String sql = "";
		long id = company.getId();
		String email = company.getEmail();
		String password = company.getPassword();
		sql = "UPDATE `company` SET `email`='" + email + "', `password`='" + password + "' WHERE `id`=" + id;

		try {
			connection = con.getConnection();
			connection.createStatement().execute(sql);
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw new UpdateFailsException("Update fails");
		} finally {
			con.returnConnection(connection);
		}
		String message = "Successfully updated ";
		return message;
	}

	// Return a company object with all the details on a specific company from
	// the db
	@Override
	public WebCompany getCompanyById(long id) throws ObjectDontExistException {

		try {

			connection = con.getConnection();

			ResultSet results = connection.createStatement()
					.executeQuery("SELECT `id`, `password`, `company_name`,`email` FROM `company` WHERE `id`=" + id);
			Convert convert = new Convert();
			WebCompany webCompany = null;
			Company company = null;
			while (results.next()) {
				webCompany = new WebCompany();
				company = new Company();
				company.setId(results.getLong("id"));
				company.setCompanyName(results.getString("company_name"));
				company.setPassword(results.getString("password"));
				company.setEmail(results.getString("email"));
				company.setCoupons(getCoupon(company));
				webCompany = convert.convertCompany(company);

			}

			return webCompany;

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw new ObjectDontExistException("Company dont exist ");
		} finally {
			con.returnConnection(connection);
		}

	}

	// Return details of all companies in the db in a arraylist
	@Override
	public Collection<WebCompany> getAllCompanies() throws GeneralException {
		try {
			connection = con.getConnection();
			Collection<WebCompany> companies = new ArrayList<WebCompany>();

			ResultSet results = connection.createStatement().executeQuery("SELECT * FROM `company` ");
			Convert convert = new Convert();
			WebCompany webCompany = null;
			Company company = null;
			while (results.next()) {
				webCompany = new WebCompany();
				company = new Company();
				company.setId(results.getLong("id"));
				company.setCompanyName(results.getString("company_name"));
				company.setPassword(results.getString("password"));
				company.setEmail(results.getString("email"));
				company.setCoupons(this.getCoupon(company));
				webCompany = convert.convertCompany(company);
				companies.add(webCompany);
			}
			return companies;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw new GeneralException("Something happend");
		} finally {
			con.returnConnection(connection);
		}

	}

	// Return all the coupon of a specific company in the db return it in
	// Arraylist
	@Override
	public ArrayList<Coupon> getCoupon(Company company) throws GeneralException {
		ConvertToString convertToString = new ConvertToString();
		try {
			long id = company.getId();
			connection = con.getConnection();
			ResultSet results = connection.createStatement().executeQuery(
					"SELECT co.id, co.titel, co.start_date,co.type, co.end_date, co.amount, co.massage, co.price, co.imag FROM coupon co inner join company_coupon cc on co.id = cc.idcoupon where cc.idcompany="
							+ id);

			ArrayList<Coupon> coupons = new ArrayList<Coupon>();

			while (results.next()) {
				Coupon c = new Coupon();
				c.setPrice(results.getDouble("price"));
				c.setId(results.getLong("id"));
				c.setStartDate(results.getString("start_date"));
				c.setEndDate(results.getString("end_date"));
				c.setAmount(results.getInt("amount"));
				c.setTitle(results.getString("titel"));
				c.setImag(convertToString.imageToAngular(results.getString("imag")));
				c.setType(CouponType.valueOf(results.getString("type")));
				c.setMassage(results.getString("massage"));
				coupons.add(c);
			}
			if (coupons.size() <= 1) {
				Coupon c = new Coupon();
				c.setAmount(0);
				c.setEndDate("2088-09-09");
				c.setStartDate("2017-09-01");
				c.setMassage("Buy this Coupon space");
				c.setId(0);
				c.setTitle("Buy this Coupon space");
				c.setPrice(0);
				c.setImag("Buy this Coupon space");
				c.setType(CouponType.CAMPING);
				coupons.add(c);
			}
			company.setCoupons(coupons);

			return coupons;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw new GeneralException("Something happend");
		} finally {
			con.returnConnection(connection);
		}

	}

	// a login method
	@Override
	public Company login(String password, String compName)
			throws SQLException, GeneralException, InvalidLoginException {
		try {

			connection = con.getConnection();
			ResultSet results = connection.createStatement()
					.executeQuery("SELECT * FROM company WHERE company_name='" + compName + "'");
			while (results.next()) {
				String p = results.getString("password");
				String cN = results.getString("company_name");
				if (password.equals(p) && compName.equals(cN)) {
					Company company = new Company();
					company.setCompanyName(results.getString("company_name"));
					company.setId(results.getLong("id"));
					company.setPassword(results.getString("password"));
					company.setEmail(results.getString("email"));
					try {
						company.setCoupons(this.getCoupon(company));
					} catch (Exception e) {
						try {
							connection.rollback();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						e.printStackTrace();
					}
					return company;
				}

			}

		} catch (SQLException e) {
			throw new InvalidLoginException("Not login");
		} finally {
			con.returnConnection(connection);
		}

		return null;

	}

	// Return a company object with all the details on a specific company from
	// the db

	public String getCompanyByName(String companyName) throws GeneralException {

		try {

			connection = con.getConnection();
			String name = null;
			ResultSet results = connection.createStatement()
					.executeQuery("select company_name from company where company_name= '" + companyName + "'");

			while (results.next()) {

				name = results.getString("company_name");

			}

			if (results.last()) {
				con.returnConnection(connection);
			}
			return name;

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw new GeneralException("Somethig happend");
		} finally {
			con.returnConnection(connection);
		}

	}

	public void forgotPassword(String email, String compName) {
		try {
			Email sendEmail = new Email();
			connection = con.getConnection();
			ResultSet results = connection.createStatement().executeQuery(
					"SELECT password FROM company WHERE company_name='" + compName + "'and email='" + email + "'");
			while (results.next()) {
				String p = results.getString("password");
				sendEmail.getPassword(email, p);
			}
		} catch (SQLException e) {
		} finally {
			con.returnConnection(connection);
		}

	}

}
