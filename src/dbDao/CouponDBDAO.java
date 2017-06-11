package dbDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import beans.Coupon;
import beans.CouponType;
import convert.object.ConvertToString;
import dao.CouponDAO;
import exception.ObjectDontExistException;
import exception.UpdateFailsException;

public class CouponDBDAO implements CouponDAO {
	private ConnectionPoolSingleton con;
	private Connection connection;
	static Coupon advertisingCoupon = new Coupon();

	static {
		advertisingCoupon.setAmount(1);
		advertisingCoupon.setAmount(0);
		advertisingCoupon.setEndDate("2018-09-09");
		advertisingCoupon.setStartDate("2017-09-01");
		advertisingCoupon.setMassage("Buy this Coupon space");
		advertisingCoupon.setId(0);
		advertisingCoupon.setTitle("Buy this Coupon space");
		advertisingCoupon.setPrice(0);
		advertisingCoupon.setImag("Buy this Coupon space");
		advertisingCoupon.setType(CouponType.CAMPING);
	}

	// Class constructor when initialization made connection to db
	public CouponDBDAO() {
		con = ConnectionPoolSingleton.getInstance();
	}

	// Create coupon method
	@Override
	public String createCoupon(Coupon coupon, long idCompay) {

		connection = con.getConnection();
		try {
			String title = coupon.getTitle();
			int amount = coupon.getAmount();
			double price = coupon.getPrice();
			String massage = coupon.getMassage();
			String imag = coupon.getImag();
			String type = coupon.getType().name();
			String start_Date = coupon.getStartDate();
			String end_Date = coupon.getEndDate();
			String sql = "INSERT INTO `coupon` (`titel`, `amount`, `massage`, `price`, `imag`,`start_date`,`end_date`,`type`)"
					+ " VALUES  ('" + title + "', '" + amount + "', '" + massage + "', '" + price + "', '" + imag
					+ "','" + start_Date + "', '" + end_Date + "','" + type + "')";
			String sql2 = "SELECT id FROM coupon where titel='" + title + "'";
			connection.createStatement().execute(sql);
			ResultSet resultSet = connection.createStatement().executeQuery(sql2);
			long idCoupon = 0;
			while (resultSet.next()) {
				idCoupon = resultSet.getLong("id");
			}

			String sql3 = "INSERT INTO `company_coupon` (`idcoupon`, `idcompany`)" + " VALUES  ('" + idCoupon + "', '"
					+ idCompay + "')";
			connection.createStatement().execute(sql3);
			String message = "Successfully created";
			return message;
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			con.returnConnection(connection);
		}
		String message = "Failed to create";
		return message;

	}

	// Remove coupon from db method
	@Override
	public String removeCoupon(Coupon coupon) {

		long id = coupon.getId();
		String sql3 = "DELETE FROM `coupon` WHERE `id`=" + id;
		String sql2 = "DELETE FROM `customer_coupon` WHERE `idcoupon`=" + id;
		String sql = "DELETE FROM `company_coupon` WHERE `idcoupon`=" + id;
		connection = con.getConnection();
		try {

			connection.createStatement().execute(sql);
			connection.createStatement().execute(sql2);
			connection.createStatement().execute(sql3);
			String message = "Successfully removed";
			return message;
		} catch (SQLException exp) {

		} finally {
			con.returnConnection(connection);
		}
		String message = "Failed to removed";
		return message;
	}

	// Update coupon details in db
	@Override
	public String updateCoupon(Coupon coupon) {
		String endDate = coupon.getEndDate();
		int amount = coupon.getAmount();
		String massage = coupon.getMassage();
		String title = coupon.getTitle();
		double price = coupon.getPrice();
		long id = coupon.getId();
		String sql = "UPDATE `coupon` SET titel='" + title + "' , price='" + price + "', amount='" + amount
				+ "', massage='" + massage + "', end_date='" + endDate + "' WHERE id=" + id;
		try {
			connection = con.getConnection();
			connection.createStatement().execute(sql);
			String message = "Successfully updated";
			System.out.println(message);
			return message;
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			con.returnConnection(connection);
		}
		System.err.println("xf bf");
		String message = "Failed to updated";
		return message;
	}

	// Return all coupon details by id method
	@Override
	public Coupon getCoupon(long id) throws ObjectDontExistException, ParseException {
		ConvertToString convertToString = new ConvertToString();
		try {
			connection = con.getConnection();
			ResultSet results = connection.createStatement().executeQuery("SELECT * FROM coupon where id = " + id);
			Coupon coupon = null;
			while (results.next()) {
				coupon = new Coupon();
				coupon.setId(results.getLong("id"));
				coupon.setAmount(results.getInt("amount"));
				coupon.setEndDate(results.getString("end_date"));
				coupon.setPrice(results.getDouble("price"));
				coupon.setStartDate(results.getString("start_date"));
				coupon.setType(CouponType.valueOf(results.getString("type")));
				coupon.setImag(convertToString.imageToAngular(results.getString("imag")));
				coupon.setTitle(results.getString("titel"));
				coupon.setMassage(results.getString("massage"));
			}
			return coupon;
		} catch (SQLException exp) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			exp.printStackTrace();

			exp.printStackTrace();
		} finally {
			con.returnConnection(connection);
		}
		return null;
	}

	// Return all coupons details in arraylist
	@Override
	public ArrayList<Coupon> getAllCoupons(long id) throws ObjectDontExistException, ParseException {
		ConvertToString convertToString = new ConvertToString();
		try {
			ResultSet results;
			connection = con.getConnection();
			results = connection.createStatement().executeQuery(
					"SELECT * FROM coupon co inner join company_coupon cc on co.id = cc.idcoupon where cc.idcompany = "
							+ id);
			ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			Coupon coupon = null;
			while (results.next()) {

				coupon = new Coupon();
				coupon.setId(results.getLong("id"));
				coupon.setAmount(results.getInt("amount"));
				coupon.setEndDate(results.getString("end_date"));
				coupon.setPrice(results.getDouble("price"));
				coupon.setStartDate(results.getString("start_date"));
				coupon.setImag(convertToString.imageToAngular(results.getString("imag")));
				coupon.setTitle(results.getString("titel"));
				coupon.setMassage(results.getString("massage"));
				coupon.setType(CouponType.valueOf(results.getString("type")));
				coupons.add(coupon);

			}
			if (coupons.size() <= 1) {
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
				coupons.add(c);
			}
			return coupons;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();

			e.printStackTrace();
		} finally {
			con.returnConnection(connection);
		}
		return null;
	}

	// Return coupon details by type in arraylist
	@Override
	public ArrayList<Coupon> getCouponByType(CouponType couponType, long id)
			throws ObjectDontExistException, ParseException {
		ConvertToString convertToString = new ConvertToString();
		String type = couponType.name();
		try {

			connection = con.getConnection();
			ResultSet result = connection.createStatement().executeQuery(
					"SELECT * FROM coupon co inner join company_coupon cc on co.id = cc.idcoupon where co.type ='"
							+ type + "'and cc.idcompany =" + id);
			Coupon coupon = null;
			ArrayList<Coupon> coupons = new ArrayList<Coupon>();

			while (result.next()) {
				coupon = new Coupon();
				coupon.setAmount(result.getInt("amount"));
				coupon.setEndDate(result.getString("end_date"));
				coupon.setMassage(result.getString("massage"));
				coupon.setStartDate(result.getString("start_date"));
				coupon.setImag(convertToString.imageToAngular(result.getString("imag")));
				coupon.setPrice(result.getDouble("price"));
				coupon.setTitle(result.getString("titel"));
				coupon.setType(CouponType.valueOf(result.getString("type")));
				coupon.setId(result.getLong("id"));
				coupons.add(coupon);
			}
			if (coupons.size() <= 1) {
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
				coupons.add(c);
			}
			return coupons;
		} catch (SQLException exp) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			con.returnConnection(connection);
		}
		return null;
	}

	public String getCouponByTitle(String title) {
		ResultSet results = null;
		String dbTitle = null;
		try {
			connection = con.getConnection();
			results = connection.createStatement().executeQuery("SELECT titel FROM coupon where titel='" + title + "'");

			while (results.next()) {
				dbTitle = results.getString("titel");
			}
			return dbTitle;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			con.returnConnection(connection);
		}
		return null;

	}

	public void updateAmount(int amount, long id) throws UpdateFailsException {
		String sql = null;
		amount = amount - 1;
		connection = con.getConnection();
		sql = "UPDATE `coupon` SET `amount`='" + amount + "' WHERE `id`='" + id + "'";
		try {
			connection.createStatement().execute(sql);
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw new UpdateFailsException("Update Fails...!");

		} finally {
			con.returnConnection(connection);
		}
	}

}
