package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;
	private String email;

	// Empty constructor
	public Customer() {

	}

	// Constructor to create a customer
	public Customer(String custName, String password, String email,Collection<Coupon> coupons ) {

		this.custName = custName;
		this.password = password;
		this.email = email;
		this.coupons = coupons;
	}

	// Get customer id method
	public long getId() {

		return id;
	}

	// Set customer id method
	public void setId(long id) {

		this.id = id;
	}

	// Get customer name method
	public String getCustName() {
		return custName;
	}

	// Set customer name method
	public void setCustName(String custName) {
		this.custName = custName;
	}

	// Get customer password method
	public String getPassword() {
		return password;
	}

	// Set customer password method
	public void setPassword(String password) {
		this.password = password;
	}

	// Get customer coupons in arraylist method
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	// Set customer coupons in arraylist method
	public void setCoupons(Collection<Coupon> collection) {
		this.coupons = collection;
	}

	// Tosrtring method
	public String toString() {
		return "Customer details: id=" + id + "\nCustomer name = " + custName + "\nPassword=" + password + "\nEmail="
				+ email + "\nCoupons=" + coupons + "";

	}

	// Get customer email method
	public String getEmail() {
		return email;
	}

	// Set customer email method
	public void setEmail(String email) {
		this.email = email;
	}
}
