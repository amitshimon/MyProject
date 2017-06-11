package facade;

import exception.GeneralException;
import exception.InvalidLoginException;

/*Coupon client facade interface */
public interface CouponClienFacade {

	/* Login signature */
	public boolean login(String compName, String password) throws GeneralException, InvalidLoginException;
	
	public void forgotPassword(String compName, String email) throws GeneralException, InvalidLoginException;
}
