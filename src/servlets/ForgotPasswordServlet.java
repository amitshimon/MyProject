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
import facade.CouponSystem;

@WebServlet("/forgot")
public class ForgotPasswordServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForgotPasswordServlet() {

	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession s = request.getSession(false);
		if (s != null) {
			s.invalidate();
		}
		s = request.getSession(true);
		String user = request.getParameter("user");
		String email = request.getParameter("email");
		String type = request.getParameter("type");
		ClientFacad cf = ClientFacad.valueOf(type);

		CouponSystem sys = CouponSystem.getInstance();
		try {
			sys.forgotPassword(user, email, cf);
			request.getRequestDispatcher("/loginSPA.html").forward(request, response);
		} catch (GeneralException | InvalidLoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
