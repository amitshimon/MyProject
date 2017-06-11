package web.beans;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import beans.Coupon;

@XmlRootElement
public class WebCompany implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String compName;
	private String email;
	private Collection<Coupon> coupons;

	// Empty constructor
	public WebCompany() {

	}

	// Constructor with all company details
	public WebCompany(String compName, String email, Collection<Coupon> coupons) {
		this.compName = compName;
		this.email = email;
		this.coupons = coupons;
	}

	// Get id method
	public long getId() {
		return id;
	}

	// Set id method
	public void setId(long id) {
		this.id = id;
	}

	// Get company name method
	public String getCompanyName() {
		return compName;
	}

	// Set company name method
	public void setCompanyName(String compName) {
		this.compName = compName;
	}

	// Get company email method
	public String getEmail() {
		return email;
	}

	// Set company name method
	public void setEmail(String email) {
		this.email = email;
	}

	// Get company coupons method
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	// Set company coupons method
	public void setCoupons(Collection<Coupon> coupons) {

		this.coupons = coupons;

	}
	// Tostring method
	@Override
	public String toString() {
		return "WebCompany [id=" + id + ", compName=" + compName + ", email=" + email + ", coupons=" + coupons + "]";
	}

	
	

}
