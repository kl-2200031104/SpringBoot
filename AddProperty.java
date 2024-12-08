package com.real.estate.Ctl;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.real.estate.Bean.BaseBean;
import com.real.estate.Bean.PropertyBean;
import com.real.estate.Bean.UserBean;
import com.real.estate.Model.PropertyModel;
import com.real.estate.Utility.DataUtility;
import com.real.estate.Utility.DataValidator;
import com.real.estate.Utility.PropertyReader;
import com.real.estate.Utility.ServletUtility;

/**
 * Servlet implementation class AddProperty
 */
@MultipartConfig(maxFileSize = 16177215)
@WebServlet(name = "AddProperty", urlPatterns = "/property")
public class AddProperty extends BaseCtl {
	private static final long serialVersionUID = 1L;
	public static final String OP_SAVE = "ADD";
	public static final String OP_RESET = "Reset";
	public static final String OP_UPDATE = "Update";
    public AddProperty() {
        super();
    }
    

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("in validation");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getvalue("error.require", "Title"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("owner"))) {
			request.setAttribute("owner", PropertyReader.getvalue("error.require", "Owner"));
			pass = false;

		}
		
		if (DataValidator.isNull(request.getParameter("Location"))) {
			request.setAttribute("Location", PropertyReader.getvalue("error.require", "Location"));
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getvalue("error.require", "description"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("sqrrate"))) {
			request.setAttribute("sqrrate", PropertyReader.getvalue("error.require", "SqrRate"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price", PropertyReader.getvalue("error.require", "Price"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("Address"))) {
			request.setAttribute("Address", PropertyReader.getvalue("error.require", "Address"));
			pass = false;

		}

		if ("-----Select-----".equalsIgnoreCase(request.getParameter("type"))) {
			request.setAttribute("type", PropertyReader.getvalue("error.require", "type"));
			pass = false;
		}
		if ("-----Select-----".equalsIgnoreCase(request.getParameter("hall"))) {
			request.setAttribute("hall", PropertyReader.getvalue("error.require", "Hall"));
			pass = false;
		}
		if ("-----Select-----".equalsIgnoreCase(request.getParameter("kitchan"))) {
			request.setAttribute("kitchan", PropertyReader.getvalue("error.require", "Kitchan"));
			pass = false;
		}
		if ("-----Select-----".equalsIgnoreCase(request.getParameter("balcony"))) {
			request.setAttribute("balcony", PropertyReader.getvalue("error.require", "Balcony"));
			pass = false;
		}
		if ("-----Select-----".equalsIgnoreCase(request.getParameter("bedroom"))) {
			request.setAttribute("bedroom", PropertyReader.getvalue("error.require", "BedRoom"));
			pass = false;
		}
		if ("-----Select-----".equalsIgnoreCase(request.getParameter("bathroom"))) {
			request.setAttribute("bathroom", PropertyReader.getvalue("error.require", "Bathroom"));
			pass = false;
		}
		return pass;
	}
	
	protected BaseBean populateBean(HttpServletRequest request) {

		PropertyBean bean = new PropertyBean();
		HttpSession session = request.getSession(false);
		UserBean existBean = (UserBean)session.getAttribute("user");
		Long userId = existBean.getId();
		bean.setUserid(userId);
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPropertytitle(DataUtility.getString(request.getParameter("title")));
		bean.setPropertyowner(DataUtility.getString(request.getParameter("owner")));
		bean.setLocation(DataUtility.getString(request.getParameter("Location")));
		bean.setPropertytype(DataUtility.getString(request.getParameter("type")));
		bean.setSqurerate(DataUtility.getString(request.getParameter("sqrrate")));
		bean.setPrice(DataUtility.getString(request.getParameter("price")));
		bean.setAddresss(DataUtility.getString(request.getParameter("Address")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		bean.setHall(DataUtility.getString(request.getParameter("hall")));
		bean.setBalcony(DataUtility.getString(request.getParameter("balcony")));
		bean.setBathroom(DataUtility.getString(request.getParameter("bathroom")));
		bean.setKitchan(DataUtility.getString(request.getParameter("kitchan")));
		bean.setBedroom(DataUtility.getString(request.getParameter("bedroom")));
		//bean.setImage(DataUtility.getString(request.getParameter("image")));
		bean.setStatus("Book");
		populateDTO(bean, request);
		return bean;

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PropertyModel model = new PropertyModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			PropertyBean bean = null;
			try {
				bean = model.findByPk(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ServletUtility.setbean(bean, request);
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected String SubImage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String savePath = DataUtility.getString(PropertyReader.getvalue("imagePath"));

		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}

		Part part = request.getPart("image");
		String fileName = extractFileName(part);
		part.write(savePath + File.separator + fileName);
		String filePath = fileName;
		System.out.println("Path----" + savePath + File.separator + fileName);
		return fileName;
	}
	
	private String extractFileName(Part part) {
		try {
			String contentDisp = part.getHeader("content-disposition");
			String[] items = contentDisp.split(";");
			for (String s : items) {
				if (s.trim().startsWith("filename")) {
					return s.substring(s.indexOf("=") + 2, s.length() - 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PropertyModel model = new PropertyModel();
		System.out.println("in do post");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		PropertyBean bean = (PropertyBean) populateBean(request);
		bean.setImage(SubImage(request, response));
		if (bean.getId() > 0) {
			System.out.println("in do post2");
			long i = model.Update(bean);
			ServletUtility.setSuccessMessage("Data Updated Successfully", request);
		} else {
			try {
				long pk = model.add(bean);
				ServletUtility.setSuccessMessage("Data Add Successfully", request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected String getView() {
		return REView.PROPERTY_VIEW;
	}

}