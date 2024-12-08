package com.real.estate.Ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.real.estate.Bean.BaseBean;
import com.real.estate.Bean.PropertyBean;
import com.real.estate.Model.PropertyModel;
import com.real.estate.Utility.DataUtility;
import com.real.estate.Utility.ServletUtility;

@WebServlet(name = "AllProperty", urlPatterns = "/allProperty")
public class AllProperty extends BaseCtl {
	private static final long serialVersionUID = 1L;
	public static final String OP_SEARCH = "Search";

	public AllProperty() {
		super();
	}

	@Override
	protected void preload(HttpServletRequest request) {
		PropertyModel model = new PropertyModel();
		try {
			List locationlist = model.locationlist();
			request.setAttribute("location", locationlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PropertyModel model = new PropertyModel();
		PropertyBean bean = null;
		long id = DataUtility.getLong(request.getParameter("id"));
		List list = null;
		try {
			list = model.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletUtility.setList(list, request);
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		PropertyBean bean = new PropertyBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLocation(DataUtility.getString(request.getParameter("location")));
		populateDTO(bean, request);
		return bean;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
	    long id = DataUtility.getLong(request.getParameter("id"));
		String location = DataUtility.getString(request.getParameter("location"));
		System.out.println("ID :" + id);
		
		System.out.println("Location Name:" + location);
		
		PropertyModel model = new PropertyModel();
		PropertyBean bean = new PropertyBean();
		bean = (PropertyBean) populateBean(request);
		bean.setLocation(location);
		List list = null;
		if (OP_SEARCH.equalsIgnoreCase(op)) {
			try {
				list = model.search(bean);
				ServletUtility.setList(list, request);
				ServletUtility.setbean(bean, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ServletUtility.forward(getView(), request, response);
		}
	}

	@Override
	protected String getView() {
		return REView.ALL_PROPERTY_VIEW;
	}
}
