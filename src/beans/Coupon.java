package beans;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Coupon implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String massage;
	private double price;
	private String imag;

	// Constructor to crate coupon with all details
	public Coupon(String title, Date startDate, Date endDate, int amount, String massage, double price, String imag,
			CouponType type) {

		this.title = title;
		this.startDate = startDate;

		this.endDate = endDate;
		this.amount = amount;

		this.massage = massage;
		this.price = price;
		this.imag = imag;

		this.type = type;

	}

	// Empty constructor
	public Coupon() {

	}

	// Get coupon id method
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	// Get coupon title method
	public String getTitle() {
		return title;
	}

	// Set coupon title method
	public void setTitle(String title) {
		this.title = title;
	}

	// Get coupon start date method
	public String getStartDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String stringStartDate = df.format(startDate);
		return stringStartDate;
	}

	// Set coupon end date method
	public void setStartDate(String startDate) {
		String stringStartDate = startDate.substring(0, 10);
		this.startDate = java.sql.Date.valueOf(stringStartDate);
	}

	// Get coupon amount method
	public int getAmount() {
		return amount;
	}

	// Set coupon amount method
	public void setAmount(int amount) {
		this.amount = amount;
	}

	// Get coupon type method

	public CouponType getType() {

		return this.type;
	}

	// Set coupon type method
	public void setType(CouponType type) {
		this.type = type;

	}

	// Get coupon massage method
	public String getMassage() {
		return massage;
	}

	// Set coupon massage method
	public void setMassage(String massage) {
		this.massage = massage;
	}

	// Get coupon price method
	public double getPrice() {
		return price;
	}

	// Set coupon price method
	public void setPrice(double price) {
		this.price = price;
	}

	// Get coupon imag method
	public String getImag() {
		return imag;
	}

	// Set coupon imag method
	public void setImag(String imag) {
		this.imag = imag;
	}

	// Tostring method
	public String toString() {
		return "Coupon details : {id : " + id + ", "  + "titel : "  + title + ", " + "Start date : " + startDate + ", "
				+ "End date : " + endDate + ", " + "Amount : " + amount + ", " + "Type : " + type + ", " + "Massage : "
				+ massage + ", " + "Price : " + price + ", " + "imag : " + imag + "}";
	}

	// Get coupon end date method
	public String getEndDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String stringEndDate = df.format(endDate);
		return stringEndDate;
	}

	// Set coupon end date method
	public void setEndDate(String endDate) {
		String stringEndDate = endDate.substring(0, 10);
		this.endDate = java.sql.Date.valueOf(stringEndDate);

	}

}
