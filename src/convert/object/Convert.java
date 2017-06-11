package convert.object;

import beans.Company;
import beans.Customer;
import web.beans.WebCompany;
import web.beans.WebCustomer;

public class Convert {

	public WebCompany convertCompany(Company company) {
		WebCompany webCompany = new WebCompany();
		webCompany.setCompanyName(company.getCompanyName());
		webCompany.setEmail(company.getEmail());
		webCompany.setCoupons(company.getCoupons());
		webCompany.setId(company.getId());
		return webCompany;

	}

	public WebCustomer convertCustomer(Customer customer) {
		WebCustomer webCustomer = new WebCustomer();
		webCustomer.setCoupons(customer.getCoupons());
		webCustomer.setCustomerName(customer.getCustName());
		webCustomer.setCustomerEmail(customer.getEmail());
		webCustomer.setId(customer.getId());
		return webCustomer;

	}

}
