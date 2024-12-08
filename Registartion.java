package com.real.estate.Ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.real.estate.Bean.BaseBean;
import com.real.estate.Bean.UserBean;
import com.real.estate.Exception.ApplicationException;
import com.real.estate.Exception.DuplicateRecordException;
import com.real.estate.Model.UserModel;
import com.real.estate.Utility.DataUtility;
import com.real.estate.Utility.DataValidator;
import com.real.estate.Utility.PropertyReader;
import com.real.estate.Utility.ServletUtility;

/**
 * Servlet implementation class RegistrationCtl
 */
@WebServlet(name = "RegistrationCtl", urlPatterns = "/register")
public class RegistrationCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_SAVE = "Save";
	public static final String OP_RESET = "Reset";
	public static final String OP_NEW = "New";
	public static final String OP_MYPROFILE = "MyProfile";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationCtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("in validation");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getvalue("error.require", "Name"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getvalue("error.name", "Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getvalue("error.require", "Email Id"));
			pass = false;

		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getvalue("error.login", "Email Id"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getvalue("error.require", "Password"));
			pass = false;

		}

		else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getvalue("error.password", "Password"));
			return false;

		}
		if (DataValidator.isNull(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", PropertyReader.getvalue("error.require", "Phone No"));
			pass = false;

		}

		if ("-----Select-----".equalsIgnoreCase(request.getParameter("role"))) {
			request.setAttribute("role", PropertyReader.getvalue("error.require", "Role"));
			pass = false;
		}
		return pass;
	}

	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));
		bean.setRoleid(DataUtility.getLong(request.getParameter("role")));
		if (bean.getRoleid() == 2) {
			bean.setRolename("Dealer");
		} else {
			bean.setRolename("Buyers");
		}
		populateDTO(bean, request);
		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("in do post");
		UserModel model = new UserModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		UserBean bean = new UserBean();
		bean = (UserBean) populateBean(request);

		if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(REView.REGISTRATION_CTL, request, response);
			return;
		}

		if (OP_SAVE.equalsIgnoreCase(op)) {
			bean = (UserBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setbean(bean, request);
				ServletUtility.setSuccessMessage("User Successfully Registered", request);
				ServletUtility.forward(getView(), request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setbean(bean, request);
				ServletUtility.setErrorMessage(e.getMessage(), request);
				ServletUtility.forward(getView(), request, response);

			} catch (ApplicationException e) {

				e.printStackTrace();
			} catch (Exception e) {

				e.printStackTrace();
			}
			ServletUtility.forward(getView(), request, response);

		}
	}

	@Override
	protected String getView() {
		return REView.REGISTRATION_VIEW;
	}

}
