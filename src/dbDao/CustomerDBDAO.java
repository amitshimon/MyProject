package dbDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import beans.Coupon;
import beans.CouponType;
import beans.Customer;
import convert.object.Convert;
import convert.object.ConvertToString;
import convert.object.Email;
import dao.CustomerDAO;
import exception.GeneralException;
import exception.InvalidLoginException;
import exception.ObjectAlreadyExistException;
import exception.ObjectDontExistException;
import exception.ThereIsNoSuchPrice;
import exception.UpdateFailsException;
import web.beans.WebCustomer;

public class CustomerDBDAO implements CustomerDAO {
	private ConnectionPoolSingleton con;
	private Connection connection;

	// Class constructor when initialization made connection to db

	public CustomerDBDAO() {
		con = ConnectionPoolSingleton.getInstance();
	}

	// Create customer method
	@Override
	public String createCustomer(Customer customer) throws GeneralException {

		try {
			connection = con.getConnection();
			String custName = customer.getCustName();
			String password = customer.getPassword();
			String email = customer.getEmail();

			String sql = "INSERT INTO `customer` ( `customer_name`, `password`, `email`) " + "VALUES ('" + custName
					+ "', '" + password + "','" + email + "')";
			connection = con.getConnection();
			connection.createStatement().execute(sql);

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {

			con.returnConnection(connection);
		}
		String message = "Successful addition";
		return message;
	}

	// Remove customer from db method and remove his coupons from the customer
	// coupon table
	@Override
	public String removeCustomer(Customer customer) throws GeneralException {

		long id = customer.getId();
		String sql = "DELETE  FROM `customer` WHERE `id`=" + id;
		String sql1 = "delete from customer_coupon where idcustomer=" + id;
		try {
			connection = con.getConnection();
			connection.createStatement().execute(sql1);
			connection.createStatement().execute(sql);
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw new GeneralException("removed failed");

		} finally {
			con.returnConnection(connection);
		}
		String message = "Successful removal";
		return message;
	}

	// Update customer details method
	@Override
	public String updateCustomer(Customer customer) throws ObjectDontExistException, UpdateFailsException {

		long id = customer.getId();
		String password = customer.getPassword();
		String email = customer.getEmail();

		String sql = "UPDATE `db_dao`.`customer` SET `password`='" + password + "', `email`='" + email + "' WHERE `id`="
				+ id;

		try {
			connection = con.getConnection();
			connection.createStatement().execute(sql);
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw new UpdateFailsException("Update failed");

		} finally {
			con.returnConnection(connection);
		}
		String message = "Successful updated";
		return message;

	}

	// Return customer details by id from db method
	@Override
	public WebCustomer getCustomer(long id) throws GeneralException {
		connection = con.getConnection();
		ResultSet results = null;
		try {
			results = connection.createStatement().executeQuery("SELECT * FROM `customer` WHERE `id`=" + id);
			Convert convert = new Convert();
			Customer customer = null;
			WebCustomer webCustomer = null;
			while (results.next()) {
				webCustomer = new WebCustomer();
				customer = new Customer();
				customer.setId(results.getLong("id"));
				customer.setCustName(results.getString("customer_name"));
				customer.setPassword(results.getString("password"));
				customer.setEmail(results.getString("email"));
				customer.setCoupons(this.getCoupons(customer.getId()));
				webCustomer = convert.convertCustomer(customer);
			}

			return webCustomer;
		} catch (SQLException | ObjectDontExistException e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			con.returnConnection(connection);
		}
		return null;

	}

	// Return a list of customers details in arraylist
	@Override
	public ArrayList<WebCustomer> getAllCustomers() {
		try {

			connection = con.getConnection();
			ArrayList<WebCustomer> customers = new ArrayList<WebCustomer>();
			WebCustomer webCustomer = null;
			ResultSet results = connection.createStatement().executeQuery("SELECT * FROM `customer` ");
			Convert convert = new Convert();
			Customer customer = null;

			while (results.next()) {
				webCustomer = new WebCustomer();
				customer = new Customer();
				customer.setId(results.getLong("id"));
				customer.setCustName(results.getString("customer_name"));
				customer.setPassword(results.getString("password"));
				customer.setEmail(results.getString("email"));
				customer.setCoupons(this.getCoupons(customer.getId()));
				webCustomer = convert.convertCustomer(customer);
				customers.add(webCustomer);

			}

			return customers;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();

		} finally {
			con.returnConnection(connection);
		}
		return null;

	}

	// Return all customer coupons in a arraylist method
	@Override
	public ArrayList<Coupon> getCoupons(long id) throws ObjectDontExistException, GeneralException {
		ConvertToString convert = new ConvertToString();
		connection = con.getConnection();
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		ResultSet results = null;
		try {
			results = connection.createStatement().executeQuery(
					"SELECT co.id, co.titel,co.type, co.start_date, co.end_date, co.amount, co.massage, co.price, co.imag  FROM coupon co inner join customer_coupon cc on co.id = cc.idcoupon where cc.idcustomer="
							+ id);
			Coupon coupon = null;
			while (results.next()) {
				coupon = new Coupon();
				coupon.setAmount(results.getInt("amount"));
				coupon.setEndDate(results.getString("end_date"));
				coupon.setMassage(results.getString("massage"));
				coupon.setStartDate(results.getString("start_date"));
				coupon.setPrice(results.getDouble("price"));
				coupon.setTitle(results.getString("titel"));
				coupon.setType(CouponType.valueOf(results.getString("type")));
				coupon.setImag(convert.imageToAngular(results.getString("imag")));
				coupon.setId(results.getLong("id"));
				coupons.add(coupon);
			}
			
			return coupons;

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			con.returnConnection(connection);
		}
		return null;
	}

	// login method
	@Override
	public Customer login(String password, String custName) throws GeneralException {
		connection = con.getConnection();
		boolean login = false;
		try {

			ResultSet results = connection.createStatement()
					.executeQuery("SELECT * FROM customer WHERE customer_name='" + custName + "'");
			Customer customer = null;
			while (results.next()) {
				customer = new Customer();
				customer.setPassword(results.getString("password"));
				customer.setCustName(results.getString("customer_name"));

				if (customer.getPassword().equals(password) && customer.getCustName().equals(custName)) {
					login = true;
					Customer customer1 = new Customer();
					customer1.setCustName(results.getString("customer_name"));
					customer1.setEmail(results.getString("email"));
					customer1.setPassword(results.getString("password"));
					customer1.setId(results.getLong("id"));
					customer1.setCoupons(this.getCoupons(customer1.getId()));
					return customer1;
				} else {
					try {
						throw new InvalidLoginException("login false");
					} catch (InvalidLoginException e) {
						try {
							connection.rollback();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						e.printStackTrace();
					}
				}

			}

		} catch (SQLException | ObjectDontExistException e) {
			try {
				throw new ObjectAlreadyExistException("not login.." + login + "...");
			} catch (ObjectAlreadyExistException e1) {
				e1.printStackTrace();
			} finally {
				con.returnConnection(connection);
			}
		}

		return null;

	}

	public String getCustomerByName(String customrName) throws Exception {
		try {
			ArrayList<String> names = new ArrayList<String>();
			connection = con.getConnection();
			ResultSet results = connection.createStatement()
					.executeQuery("select customer_name from customer where customer_name='" + customrName + "'");
			String name = null;
			while (results.next()) {
				name = results.getString("customer_name");
				names.add(name);
			}
			return name;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.returnConnection(connection);
		}
		return null;
	}

	// Method return coupon by its type
	public Collection<Coupon> getCouponsByType(CouponType type, long id) throws ObjectDontExistException {
		ResultSet results = null;
		ConvertToString convertToString = new ConvertToString();
		String couponType = type.name();
		connection = con.getConnection();
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		try {
			results = connection.createStatement().executeQuery(
					"SELECT cc.idcustomer, co.id, co.titel, co.start_date, co.end_date, co.amount, co.massage, co.price, co.imag, co.type FROM coupon co inner join customer_coupon cc on co.id = cc.idcoupon where co.type='"
							+ couponType + "' and idcustomer ='" + id + "'");
			Coupon coupon = null;
			while (results.next()) {
				coupon = new Coupon();
				coupon.setAmount(results.getInt("amount"));
				coupon.setEndDate(results.getString("end_date"));
				coupon.setMassage(results.getString("massage"));
				coupon.setStartDate(results.getString("start_date"));
				coupon.setImag(convertToString.imageToAngular(results.getString("imag")));
				coupon.setPrice(results.getDouble("price"));
				coupon.setTitle(results.getString("titel"));
				coupon.setType(CouponType.valueOf(results.getString("type")));
				coupon.setId(results.getLong("id"));
				coupons.add(coupon);
			}
			
			return coupons;

		} catch (SQLException e) {
			try {
				throw new ObjectAlreadyExistException("we have a problem");
			} catch (ObjectAlreadyExistException e1) {
				e.printStackTrace();
			} finally {
				con.returnConnection(connection);
			}
			return coupons;
		}

	}

	// Method return coupon by its price
	public Collection<Coupon> getCouponByPrice(double price, double price2) throws ThereIsNoSuchPrice {
		ResultSet results = null;
		ConvertToString convertToString = new ConvertToString();
		connection = con.getConnection();
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		try {
			results = connection.createStatement().executeQuery(
					"SELECT co.id, co.titel,co.type, co.start_date, co.end_date, co.amount, co.massage, co.price, co.imag FROM coupon co inner join customer_coupon cc on co.id = cc.idcoupon where co.price between'"
							+ price + "'and'" + price2 + "'");
			Coupon coupon = null;
			while (results.next()) {

				coupon = new Coupon();
				coupon.setAmount(results.getInt("amount"));
				coupon.setImag(convertToString.imageToAngular(results.getString("imag")));
				coupon.setMassage(results.getString("massage"));
				coupon.setStartDate(results.getString("start_date"));
				coupon.setEndDate(results.getString("end_date"));
				coupon.setPrice(results.getDouble("price"));
				coupon.setTitle(results.getString("titel"));
				coupon.setType(CouponType.valueOf(results.getString("type")));
				coupon.setId(results.getLong("id"));
				coupons.add(coupon);

			}
			
			return coupons;

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw new ThereIsNoSuchPrice("There is no such price");
		} finally {
			con.returnConnection(connection);
		}

	}

	// Method return all purchased coupon
	public Collection<Coupon> getAllPurchasedCoupon() throws GeneralException {
		ConvertToString convertToString = new ConvertToString();
		ResultSet results = null;
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		connection = con.getConnection();

		try {
			results = connection.createStatement().executeQuery(
					"SELECT co.id, co.titel,co.type, co.start_date, co.end_date, co.amount, co.massage, co.price, co.imag FROM coupon co inner join customer_coupon cc on co.id = cc.idcoupon ");
			Coupon coupon = null;
			while (results.next()) {
				coupon = new Coupon();
				coupon.setId(results.getLong("id"));
				coupon.setAmount(results.getInt("amount"));
				coupon.setEndDate(results.getString("end_date"));
				coupon.setStartDate(results.getString("start_date"));
				coupon.setMassage(results.getString("massage"));
				coupon.setTitle(results.getString("titel"));
				coupon.setImag(convertToString.imageToAngular(results.getString("imag")));
				coupon.setType(CouponType.valueOf(results.getString("type")));
				coupon.setPrice(results.getDouble("price"));
				coupons.add(coupon);
			}
		
			return coupons;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw new GeneralException("Something unexpected happened, please agian.");
		} finally {
			con.returnConnection(connection);
		}

	}

	public void insertIntoJoinTable(Customer customer) {
		connection = con.getConnection();
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) customer.getCoupons();
		long idCustomer = customer.getId();
		for (int i = 0; i < coupons.size(); i++) {
			long idCoupon = coupons.get(i).getId();
			System.out.println("idcoupon" + idCoupon);
			String sql2 = "INSERT INTO customer_coupon (`idcustomer`, `idcoupon`) VALUES ('" + idCustomer + "', '"
					+ idCoupon + "');";

			try {
				connection.createStatement().execute(sql2);
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				con.returnConnection(connection);
			}
		}
	}

	@Override
	public Collection<Coupon> getCoupons() throws ObjectDontExistException, GeneralException {
		ConvertToString convertToString = new ConvertToString();
		connection = con.getConnection();
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		ResultSet results = null;
		try {
			results = connection.createStatement().executeQuery("SELECT * from coupon");
			Coupon coupon = null;
			while (results.next()) {
				coupon = new Coupon();
				coupon.setAmount(results.getInt("amount"));
				coupon.setEndDate(results.getString("end_date"));
				coupon.setMassage(results.getString("massage"));
				coupon.setStartDate(results.getString("start_date"));
				coupon.setPrice(results.getDouble("price"));
				coupon.setTitle(results.getString("titel"));
				coupon.setType(CouponType.valueOf(results.getString("type")));
				coupon.setImag(convertToString.imageToAngular(results.getString("imag")));
				coupon.setId(results.getLong("id"));
				coupons.add(coupon);
			}
			
			return coupons;

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			con.returnConnection(connection);
		}
		return null;
	}

	public void forgotPassword(String email, String compName) {
		try {
			Email sendEmail = new Email();
			connection = con.getConnection();
			ResultSet results = connection.createStatement().executeQuery(
					"SELECT password FROM customer WHERE customer_name='" + compName + "'and email='" + email + "'");
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
