package dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import beans.Coupon;
import beans.CouponType;
import exception.InvalidLoginException;
import exception.ObjectDontExistException;

public interface CouponDAO {
	public String createCoupon(Coupon c, long id) throws SQLException;

	public String removeCoupon(Coupon c) throws SQLException;

	public String updateCoupon(Coupon c) throws SQLException;

	public Coupon getCoupon(long id) throws SQLException, Exception;

	public ArrayList<Coupon> getAllCoupons(long id) throws SQLException, ObjectDontExistException, ParseException;

	public ArrayList<Coupon> getCouponByType(CouponType couponType, long id) throws SQLException, ObjectDontExistException, InvalidLoginException, ParseException;
}
