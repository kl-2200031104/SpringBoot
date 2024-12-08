package com.real.estate.Ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.real.estate.Bean.BaseBean;
import com.real.estate.Bean.UserBean;
import com.real.estate.Model.UserModel;
import com.real.estate.Utility.DataUtility;
import com.real.estate.Utility.DataValidator;
import com.real.estate.Utility.PropertyReader;
import com.real.estate.Utility.ServletUtility;

/**
 * Servlet implementation class LoginCtl
 */
@WebServlet(name = "LoginCtl", urlPatterns = "/login")
public class LoginCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	public static final String OP_SINGIN = "SignIn";
	public static final String OP_SING_UP = "SignUp";
	public static final String OP_LOGOUT = "Logout";
    public LoginCtl() {
        super();
    }
    
    @Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		String op = request.getParameter("operation");

		if (OP_SING_UP.equalsIgnoreCase(op) || OP_LOGOUT.equalsIgnoreCase(op)) {
			return true;
		}

		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getvalue("error.require", "Login Id"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getvalue("error.require", "Password"));
			pass = false;

		}

		return pass;

	}
    
	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();

		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		populateDTO(bean, request);

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		HttpSession session = request.getSession(false);

		UserBean bean = (UserBean) populateBean(request);
		if (OP_LOGOUT.equalsIgnoreCase(op)) {
			session = request.getSession(false);
			session.invalidate();
			ServletUtility.setSuccessMessage("LogoutSucessfully", request);
			ServletUtility.forward(REView.LOGIN_VIEW, request, response);
			return;
		}
		ServletUtility.setbean(bean, request);
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession(true);

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();

		if (OP_SINGIN.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			try {
				bean = model.Authenticate(bean.getEmail(), bean.getPassword());
				if (bean != null) {
					long rollId = bean.getRoleid();
						session.setAttribute("user", bean);
						ServletUtility.setbean(bean, request);
						ServletUtility.redirect("jsp/Home.jsp", request, response);
						return;

				} else {
					bean = (UserBean) populateBean(request);
					ServletUtility.setbean(bean, request);
					ServletUtility.setErrorMessage("Invalid Id and Password", request);
					ServletUtility.forward(getView(), request, response);
				}
			} catch (Exception e) {
			}

		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return REView.LOGIN_VIEW;
	}

}