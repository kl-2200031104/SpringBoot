package com.real.estate.Ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.real.estate.Bean.PropertyBean;
import com.real.estate.Bean.UserBean;
import com.real.estate.Model.PropertyModel;
import com.real.estate.Model.UserModel;
import com.real.estate.Utility.DataUtility;
import com.real.estate.Utility.ServletUtility;

/**
 * Servlet implementation class PropertyListCtl
 */
@WebServlet(name = "PropertyListCtl", urlPatterns = "/propertylist")
public class PropertyListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
   
    public PropertyListCtl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PropertyModel model = new PropertyModel();
		PropertyBean bean = null;
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			model.delete(id);
			ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
		}

		List list = null;
		
		HttpSession session = request.getSession(false);
		UserBean bean2 = (UserBean) session.getAttribute("user");
		long roleid = bean2.getRoleid();
		if (roleid == 2) {
			try {
				list = model.dealerlist(bean2.getId());
			} catch (Exception e) {
			}
		} else if(roleid == 0){
		try {
			list = model.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else {
			try {
				list = model.list();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (list == null && list.size() == 0) {
			ServletUtility.setErrorMessage("No record found", request);
		}
		ServletUtility.setList(list, request);
		ServletUtility.forward(getView(), request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@Override
	protected String getView() {
		return REView.PROPERTY_LIST_VIEW;
	}

}
