package beans;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Company implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String compName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;

	// Empty constructor
	public Company() {

	}

	// Constructor with all company details
	public Company(String compName, String password, String email, Collection<Coupon> coupons) {
		this.compName = compName;
		this.password = password;
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

	// Get company password method
	public String getPassword() {
		return password;
	}

	// Set company password method
	public void setPassword(String password) {
		this.password = password;
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
	public String toString() {
		return "Company details:\nid=" + id + "\nCompany name=" + compName + "\nPassword=" + password + "\nEmail="
				+ email + "\nCoupons=" + coupons + "\n" + "\n";
	}

}
