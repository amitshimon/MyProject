package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.multipart.FormDataParam;

import beans.Company;
import beans.Customer;
import convert.object.Message;
import exception.CreateFailException;
import exception.GeneralException;
import exception.ObjectAlreadyExistException;
import exception.ObjectDontExistException;
import exception.UpdateFailsException;
import facade.AdminFacade;
import web.beans.WebCompany;
import web.beans.WebCustomer;

/*Admin service class in charge for all the responsibility 
 * of the admin,The class have two parameters, http
 *  request from the login servlet and admin facade*/
@Path("admin")
public class AdminService {
	@Context
	private HttpServletRequest req;
	private AdminFacade adminFacade;

	/* Empty constructor */
	public AdminService() {

	}

	/*
	 * Remove company method get the call from the UI with parameters the client
	 * send, then the method invoke the remove method in admin facade if success
	 * method return string "success" to the client
	 */
	@Path("/removeCompany")
	@GET
	@Consumes(MediaType.TEXT_HTML)
	public String removeCompany(@QueryParam("id") long id) throws Exception {

		HttpSession httpSession = req.getSession(false);
		adminFacade = (AdminFacade) httpSession.getAttribute("facade");
		Company company = new Company();
		company.setId(id);
		return adminFacade.removeCompany(company);
	}

	/*
	 * Update company method get the call from the UI with parameters the client
	 * send, then the method invoke the update method in admin facade if success
	 * method return string "success" to the client
	 */
	@Path("/updateCompany")
	@GET
	@Consumes(MediaType.TEXT_HTML)
	public String updateCompany(@QueryParam("email") String email, @QueryParam("id") long id,
			@QueryParam("password") String password) throws SQLException, UpdateFailsException {
		HttpSession httpSession = req.getSession(false);
		adminFacade = (AdminFacade) httpSession.getAttribute("facade");
		Company company = new Company();
		System.out.println(id + "id" + password + "pass" + email + "email");
		company.setId(id);
		company.setEmail(email);
		company.setPassword(password);
		return adminFacade.updateCompany(company);
	}

	/*
	 * Get company method get the call from the UI with parameters the client
	 * send, then the method invoke the get company method in admin facade if
	 * success method return`s JSON for the company the client ask for
	 */
	@Path("/getCompany")
	@GET
	@Consumes(MediaType.TEXT_HTML)
	@Produces(MediaType.APPLICATION_JSON)
	public WebCompany getCompany(@QueryParam("id") long id) throws Exception {
		HttpSession httpSession = req.getSession(false);
		adminFacade = (AdminFacade) httpSession.getAttribute("facade");
		WebCompany webCompany = adminFacade.getCompany(id);
		return webCompany;

	}

	/*
	 * Get all companies method get the call from the UI, then the method invoke
	 * the get all method in admin facade if success method return`s JSON array
	 * for the companies the client ask for.
	 */
	@Path("getAllCompanies")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<WebCompany> getAllCompanies() throws Exception {
		HttpSession httpSession = req.getSession(false);
		adminFacade = (AdminFacade) httpSession.getAttribute("facade");
		// AdminFacade adminFacade = new AdminFacade();
		return adminFacade.getAllCompanies();

	}

	/*
	 * Remove customer method get the call from the UI with parameters the
	 * client send, then the method invoke the remove method in admin facade if
	 * success method return string "success" to the client
	 */
	@Path("/removeCustomer")
	@GET
	@Consumes(MediaType.TEXT_HTML)
	@Produces(MediaType.APPLICATION_JSON)
	public String removeCustomer(@QueryParam("id") long id) throws SQLException {
		HttpSession httpSession = req.getSession(false);
		adminFacade = (AdminFacade) httpSession.getAttribute("facade");
		AdminFacade adminFacade = new AdminFacade();
		Customer customer = new Customer();
		customer.setId(id);
		return adminFacade.removeCustomer(customer);
	}

	/*
	 * Update customer method get the call from the UI with parameters the
	 * client send, then the method invoke the update method in admin facade if
	 * success method return string "success" to the client
	 */
	@Path("/updateCustomer")
	@GET
	@Consumes(MediaType.TEXT_HTML)
	@Produces(MediaType.APPLICATION_JSON)
	public Message updateCustomer(@QueryParam("id") long id, @QueryParam("password") String password,
			@QueryParam("email") String email) throws SQLException, ObjectDontExistException {
		HttpSession httpSession = req.getSession(false);
		adminFacade = (AdminFacade) httpSession.getAttribute("facade");
		Customer customer = new Customer();
		customer.setId(id);
		customer.setEmail(email);
		customer.setPassword(password);
		return adminFacade.updateCustomer(customer);
	}

	/*
	 * Get customer method get the call from the UI with parameters that the
	 * client send, then the method invoke the get customer method in admin
	 * facade if success method return`s JSON for the customer the client ask
	 * for
	 */
	@Path("/getCustomer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_HTML)
	public WebCustomer getCustomer(@QueryParam("id") long id) throws SQLException {
		HttpSession httpSession = req.getSession(false);
		adminFacade = (AdminFacade) httpSession.getAttribute("facade");
		try {
			return adminFacade.getCustomer(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * Get all customer method get the call from the UI, then the method invoke
	 * the get all method in admin facade if success method return`s JSON array
	 * for the customers that client ask for.
	 */
	@Path("/getAllCustomers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<WebCustomer> getAllCustomers() throws ObjectAlreadyExistException {

		HttpSession httpSession = req.getSession(false);
		adminFacade = (AdminFacade) httpSession.getAttribute("facade");
		// AdminFacade adminFacade = new AdminFacade();

		try {
			return adminFacade.getAllCustomers();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/*
	 * Create company method get the call from the UI,with parameters that send
	 * by the client. then the method invoke the create company method in admin
	 * facade if success method return string with success
	 */
	@Path("/createCompany")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Message createCompany(@FormDataParam("password") String password,
			@FormDataParam("companyName") String companyName, @FormDataParam("email") String email)
			throws ObjectAlreadyExistException, GeneralException, CreateFailException {

		HttpSession s = req.getSession(false);
		adminFacade = (AdminFacade) s.getAttribute("facade");
		Company company = new Company();
		company.setCompanyName(companyName);
		company.setEmail(email);
		company.setPassword(password);
		return adminFacade.createCompany(company);
	}

	/*
	 * Create customer method get the call from the UI,with parameters that send
	 * by the client. then the method invoke the create customer method in admin
	 * facade if success method return string with success
	 */
	@Path("/createCustomer")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Message createCustomer(@FormDataParam("name") String name, @FormDataParam("password") String password,
			@FormDataParam("email") String email) throws Exception {
		HttpSession s = req.getSession(false);
		adminFacade = (AdminFacade) s.getAttribute("facade");
		// adminFacade = new AdminFacade();
		Customer customer = new Customer();
		customer.setCustName(name);
		customer.setEmail(email);
		customer.setPassword(password);
		return adminFacade.createCustomer(customer);
	}
}
