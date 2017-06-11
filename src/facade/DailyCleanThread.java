package facade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Coupon;
import dbDao.ConnectionPoolSingleton;
import dbDao.CouponDBDAO;

/*This class in charge for cleaning expired coupon`s */
public class DailyCleanThread extends Thread {
	private long timeBetween = 86400000;
	private ConnectionPoolSingleton con;
	private Connection connection;
	private boolean running = true;

	// The constructor get connection to the connection pool and create
	// instance of that class
	public DailyCleanThread() {
		con = ConnectionPoolSingleton.getInstance();
	}

	// This method call a thread every 24 hour check the coupons in the coupon
	// table if end date is expired and call the remove method in the
	// CouponDBDAO to remove these from all of tables in the db
	@Override
	public void run() {

		while (running) {
			connection = con.getConnection();
			CouponDBDAO couponDBDAO = new CouponDBDAO();
			Coupon coupon = new Coupon();
			try {
				ResultSet results = connection.createStatement()
						.executeQuery("SELECT id from coupon c where datediff(c.end_date,CURDATE()) <= 0");
				while (results.next()) {
					coupon.setId(results.getLong("id"));
					couponDBDAO.removeCoupon(coupon);
				}
				if (results.last()) {
					con.returnConnection(connection);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(timeBetween);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Method check to see if running its true the method call the interrupt and
	 * then turn running to false
	 */
	public void stopTask() {
		if (running) {
			this.interrupt();
			running = false;

		}
	}
}
