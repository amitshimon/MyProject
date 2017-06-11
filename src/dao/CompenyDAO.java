package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import beans.Company;
import beans.Coupon;
import exception.CreateFailException;
import exception.GeneralException;
import exception.InvalidLoginException;
import exception.RemoveFail;
import exception.UpdateFailsException;
import web.beans.WebCompany;

public interface CompenyDAO {
	public String createCompany(Company company) throws SQLException, CreateFailException;

	public String removeCompany(Company company) throws SQLException, RemoveFail;

	public String updateCompany(Company company) throws SQLException, UpdateFailsException;

	public WebCompany getCompanyById(long id) throws SQLException, Exception;

	public Collection<WebCompany> getAllCompanies()throws SQLException, Exception;

	public ArrayList<Coupon> getCoupon(Company company) throws Exception ; 

	public Company login(String password, String compName)throws SQLException, GeneralException, InvalidLoginException;

}
