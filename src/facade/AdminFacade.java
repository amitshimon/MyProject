package facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import beans.Company;
import beans.Customer;
import convert.object.Message;
import dbDao.CompanyDBDAO;
import dbDao.CustomerDBDAO;
import exception.CreateFailException;
import exception.GeneralException;
import exception.InvalidLoginException;
import exception.ObjectAlreadyExistException;
import exception.ObjectDontExistException;
import exception.UpdateFailsException;
import web.beans.WebCompany;
import web.beans.WebCustomer;

public class AdminFacade implements CouponClienFacade {
	private AdminFacade adminFacade = null;

	// Empty class constructor
	public AdminFacade() {

	}

	// Create company method, checks if company name exist in db if it is method
	// throw object already exist exception else call the create method in
	// CompanyDBDAO return Message object
	public Message createCompany(Company company)
			throws ObjectAlreadyExistException, GeneralException, CreateFailException {
		Message message = new Message();
		message.setMessage("Company exsist");
		CompanyDBDAO companyDbDao = new CompanyDBDAO();
		String companyName = company.getCompanyName();
		String compName = companyDbDao.getCompanyByName(companyName);
		try {
			if (!companyName.equals(compName)) {

				message.setMessage(companyDbDao.createCompany(company));
			} else {

			}
		} catch (Exception e) {
			throw new ObjectAlreadyExistException("Customer exist");
		} finally {
			return message;
		}

	}
	// method create customer after it checks if there is not existing name like
		// him in the db, if in the db have name like that the method throw object
		// already exist exception, else it call the create method inside
		// CustomerDBDAO return message object
		public Message createCustomer(Customer customer) {

			Message message = new Message();
			message.setMessage("Customer exsist");
			CustomerDBDAO customerDbDao = new CustomerDBDAO();
			String customerName = customer.getCustName();
			try {
				String custName = customerDbDao.getCustomerByName(customerName);
				if (!customerName.equals(custName)) {
					message.setMessage(customerDbDao.createCustomer(customer));
				} else {

				}
			} catch (Exception e) {
				throw new ObjectAlreadyExistException("Customer exist");
			} finally {
				return message;
			}

		}
	// Remove company from db method, create CompanyDBDAO object than call its
	// remove method that remove all of its details and coupons from db.
	public String removeCompany(Company company) throws Exception {
		CompanyDBDAO companyDbDao = new CompanyDBDAO();
		return companyDbDao.removeCompany(company);
	}

	// Method update company details create object from CompanyDBDAO than call
	// its update method
	public String updateCompany(Company company) throws SQLException, UpdateFailsException {
		CompanyDBDAO companyDbDao = new CompanyDBDAO();
		return companyDbDao.updateCompany(company);
	}

	// Method return company by id call that method inside CompanyDBDAO
	public WebCompany getCompany(long id) throws Exception {
		CompanyDBDAO companyDbDao = new CompanyDBDAO();
		WebCompany selectedCompany = companyDbDao.getCompanyById(id);
		return selectedCompany;
	}

	// method create CompanyDBDAO object than call his getAllCompanies method,
	// return arrayList of company
	public Collection<WebCompany> getAllCompanies() throws Exception {
		CompanyDBDAO companyDbDao = new CompanyDBDAO();
		return companyDbDao.getAllCompanies();

	}

	
	// update customer details method create CustomerDBDAO object than call its
		// updateCustomer method
		public Message updateCustomer(Customer customer) throws SQLException, ObjectDontExistException {
			Message message = new Message();
			message.setMessage("Update Fails");
			CustomerDBDAO customerDbDao = new CustomerDBDAO();
			try {
				message.setMessage(customerDbDao.updateCustomer(customer));
			} catch (UpdateFailsException e) {

				e.printStackTrace();
			}finally {
				return message;	
			}
			
		}
	// method create customerDBDAO object than call its remove method
	public String removeCustomer(Customer customer) throws SQLException {
		CustomerDBDAO customerDbDao = new CustomerDBDAO();
		try {
			return customerDbDao.removeCustomer(customer);
		} catch (GeneralException e) {

			e.printStackTrace();
		}
		return null;
	}

	// method create CustomerDBDAO object than call getCustomer inside that
	// class, method return customer by id.
	public WebCustomer getCustomer(long id) throws SQLException {
		CustomerDBDAO customerDbDao = new CustomerDBDAO();
		try {
			return customerDbDao.getCustomer(id);
		} catch (GeneralException e) {
			e.printStackTrace();
		}
		return null;

	}

	// Method that return all customers in the db, first it makes object from
	// CustomerDBDAO class then call getAllCustomers method inside inside that
	// class
	public ArrayList<WebCustomer> getAllCustomers() throws ObjectAlreadyExistException {
		ArrayList<WebCustomer> coustomers = new ArrayList<WebCustomer>();
		CustomerDBDAO customerDbDao = new CustomerDBDAO();
		try {
			coustomers = customerDbDao.getAllCustomers();
			return coustomers;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Login method for Admin
	public boolean login(String compName, String password) throws InvalidLoginException {
		boolean bol = false;
		if (compName.equals("admin") && password.equals("1234")) {
			bol = true;
			this.adminFacade = new AdminFacade();
			return bol;
		} else {
			throw new InvalidLoginException("wrong password or user name");
		}

	}

	@Override
	public void forgotPassword(String compName, String email) throws GeneralException, InvalidLoginException {

	}

}
