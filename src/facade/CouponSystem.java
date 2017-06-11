package facade;

import exception.GeneralException;
import exception.InvalidLoginException;

/*A singleton class that in charge for the day clean threat, and referring the client to the right facade.  */
public class CouponSystem {

	private DailyCleanThread task = null;
	private static CouponSystem instance = null;

	/*
	 * The method check if the class is initialized, if the class already have
	 * an instance the instance is return to the client if not the class
	 * generated a new instance for the client.
	 */
	public static CouponSystem getInstance() {
		if (instance == null) {
			instance = new CouponSystem();
		}
		return instance;

	}

	/*
	 * This private constructor in charge for the day cleaning initializing and
	 * for the call to his start task
	 */
	private CouponSystem() {
		task = new DailyCleanThread();
		task.start();
	}

	/*
	 * This login method in charge for the first login, the method check`s the
	 * the type parameter that send from the servlet when the type is match the
	 * class initializing the right facade, and send the user name and password
	 * for checking.
	 */
	public CouponClienFacade login(String name, String password, ClientFacad type)
			throws GeneralException, InvalidLoginException {
		CouponClienFacade couponClienFacade = null;
		switch (type) {
		case ADMIN:
			couponClienFacade = new AdminFacade();
			break;
		case CUSTOMER:
			couponClienFacade = new CustomerFacade();
			break;
		case COMPANY:
			couponClienFacade = new CompanyFacade();
			break;
		default:
			couponClienFacade = null;
			break;
		}
		couponClienFacade.login(name, password);
		return couponClienFacade;
	}

	public void forgotPassword(String name, String email, ClientFacad type)
			throws GeneralException, InvalidLoginException {
		CouponClienFacade couponClienFacade = null;
		switch (type) {
		case CUSTOMER:
			couponClienFacade = new CustomerFacade();
			break;
		case COMPANY:
			couponClienFacade = new CompanyFacade();
			break;
		case ADMIN:
			break;
		default:
			break;
		}
		couponClienFacade.forgotPassword(name, email);
	}

	/* This method in charge for closing the cleaning thread */
	public void shutDown() {
		task = new DailyCleanThread();
		task.stopTask();
	}
}
