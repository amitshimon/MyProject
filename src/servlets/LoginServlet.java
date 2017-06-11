package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import exception.GeneralException;
import exception.InvalidLoginException;
import facade.ClientFacad;
import facade.CouponClienFacade;
import facade.CouponSystem;

/**
 * Servlet implementation class LodingServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/* Empty constructor */
	public LoginServlet() {
		
	}

	/*
	 * The service method get the first call from the client check his session
	 * is the session true, if it is true the method kill the session, if it is false the
	 * client get new session. Then the method check`s the password, user name,
	 * and type parameters from the user by calling the client facade login
	 * method. If the method return facade object the method check for the type
	 * of the client, then request call request dispatcher method that send the
	 * user to his home page. */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession s = request.getSession(false);
		if (s != null) {
			s.invalidate();
		}
		s = request.getSession(true);
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		ClientFacad cf = ClientFacad.valueOf(type);

		try {
			CouponClienFacade f;
			CouponSystem sys = CouponSystem.getInstance();
			f = sys.login(user, password, cf);
			s.setAttribute("facade", f);
			if (f != null) {
				switch (cf) {
				case ADMIN:
					request.getRequestDispatcher("/adminSPA.html").forward(request, response);
					break;
				case CUSTOMER:
					request.getRequestDispatcher("/customerSPA.html").forward(request, response);
					break;
				case COMPANY:
					request.getRequestDispatcher("/companySPA.html").forward(request, response);
					break;
				default:
					request.getRequestDispatcher("/loginSPA.html").forward(request, response);
					break;
				}
			} else {
				response.sendRedirect("/loginSPA.html");
			}
		} catch (GeneralException e) {
			e.printStackTrace();
		} catch (InvalidLoginException e) {
			e.printStackTrace();
		}
	}

}
