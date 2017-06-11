package web.beans;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import beans.Coupon;

@XmlRootElement
public class WebCustomer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customerName;
	private String customerEmail;
	private long id;
	private Collection<Coupon> Coupons;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection<Coupon> getCoupons() {
		return Coupons;
	}

	public void setCoupons(Collection<Coupon> collection) {

		this.Coupons = collection;

	}

	@Override
	public String toString() {
		return "WebCustomer:\ncustomerName=" + customerName + "\n" + " customerEmail=" + customerEmail + "\n" + " id="
				+ id + "\n" + " Coupons=" + Coupons + "";
	}
}