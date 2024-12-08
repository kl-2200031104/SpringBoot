package com.real.estate.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.real.estate.Bean.PropertyBean;
import com.real.estate.Exception.ApplicationException;
import com.real.estate.Exception.DuplicateRecordException;
import com.real.estate.Utility.JDBCDataSource;

public class PropertyModel {

	public Integer nextpk() throws Exception {

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM property");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pk + 1;
	}

	public long add(PropertyBean bean) throws Exception {
		System.out.println("in add method");
		Connection conn = null;
		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();
			pk = nextpk();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO property VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, bean.getPropertytitle());
			ps.setString(3, bean.getPropertyowner());
			ps.setString(4, bean.getPropertytype());
			ps.setString(5, bean.getSqurerate());
			System.out.println("11111111111111111");
			System.out.println("222222222222222");
			ps.setString(6, bean.getPrice());
			ps.setString(7, bean.getAddresss());
			ps.setString(8, bean.getDescription());
			ps.setString(9, bean.getStatus());
			ps.setString(10, bean.getImage());
			ps.setString(11, bean.getHall());
			ps.setString(12, bean.getBalcony());
			ps.setString(13, bean.getBathroom());
			ps.setString(14, bean.getKitchan());
			ps.setString(15, bean.getBedroom());
			ps.setLong(16, bean.getUserid());
			ps.setString(17, bean.getLocation());
			System.out.println("ookkkkk");
			ps.executeUpdate();
			System.out.println("nooooookkkk");
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + e.getMessage());
			}
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return pk;
	}

	public List list() throws Exception {
		System.out.println("in model list");
		ArrayList list = new ArrayList();
		Connection conn = null;
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("select * from property");
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			PropertyBean bean = new PropertyBean();
			bean.setId(rs.getLong(1));
			bean.setPropertytitle(rs.getString(2));
			bean.setPropertyowner(rs.getString(3));
			bean.setPropertytype(rs.getString(4));
			bean.setSqurerate(rs.getString(5));
			bean.setPrice(rs.getString(6));
			bean.setAddresss(rs.getString(7));
			bean.setDescription(rs.getString(8));
			bean.setStatus(rs.getString(9));
			bean.setImage(rs.getString(10));
			bean.setHall(rs.getString(11));
			bean.setBalcony(rs.getString(12));
			bean.setBathroom(rs.getString(13));
			bean.setKitchan(rs.getString(14));
			bean.setBedroom(rs.getString(15));
			bean.setUserid(rs.getLong(16));
			bean.setLocation(rs.getString(17));
			list.add(bean);
		}
		return list;
	}

	public List locationlist() throws Exception {
		System.out.println("in model list");
		ArrayList list = new ArrayList();
		Connection conn = null;
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT distinct Location FROM property");
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			PropertyBean bean = new PropertyBean();
			//bean.setId(rs.getLong(1));
			bean.setLocation(rs.getString(1));
			list.add(bean);
		}
		return list;
	}

	public List dealerlist(long userId) throws Exception {
		System.out.println("in model list");
		ArrayList list = new ArrayList();
		Connection conn = null;
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("select * from property where userId=?");
		pstmt.setLong(1, userId);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			PropertyBean bean = new PropertyBean();
			bean.setId(rs.getLong(1));
			bean.setPropertytitle(rs.getString(2));
			bean.setPropertyowner(rs.getString(3));
			bean.setPropertytype(rs.getString(4));
			bean.setSqurerate(rs.getString(5));
			bean.setPrice(rs.getString(6));
			bean.setAddresss(rs.getString(7));
			bean.setDescription(rs.getString(8));
			bean.setStatus(rs.getString(9));
			bean.setImage(rs.getString(10));
			bean.setHall(rs.getString(11));
			bean.setBalcony(rs.getString(12));
			bean.setBathroom(rs.getString(13));
			bean.setKitchan(rs.getString(14));
			bean.setBedroom(rs.getString(15));
			bean.setUserid(rs.getLong(16));
			bean.setLocation(rs.getString(17));
			list.add(bean);
		}
		return list;
	}

	public static long delete(long id) {
		int i = 0;
		try {
			Connection conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement("DELETE from property where id=?");
			stmt.setLong(1, id);
			i = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	public PropertyBean findByPk(long pk) throws Exception {

		PropertyBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM property WHERE id=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new PropertyBean();
				bean.setId(rs.getLong(1));
				bean.setPropertytitle(rs.getString(2));
				bean.setPropertyowner(rs.getString(3));
				bean.setPropertytype(rs.getString(4));
				bean.setSqurerate(rs.getString(5));
				bean.setPrice(rs.getString(6));
				bean.setAddresss(rs.getString(7));
				bean.setDescription(rs.getString(8));
				bean.setStatus(rs.getString(9));
				bean.setImage(rs.getString(10));
				System.out.println("Image :" + bean.getImage());
				bean.setHall(rs.getString(11));
				bean.setBalcony(rs.getString(12));
				bean.setBathroom(rs.getString(13));
				bean.setKitchan(rs.getString(14));
				bean.setBedroom(rs.getString(15));
				bean.setUserid(rs.getLong(16));
				bean.setLocation(rs.getString(17));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

	public long Update(PropertyBean bean) {

		System.out.println("in model update method");
		int pk = 0;
		try {
			Connection conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"update property set propertytitle=?,propertyowner=?,propertytype=?,sqrrate=?,price=?,Address=?,description=?,status=?,image=?,hall=?,bedroom=?,kitchan=?,bathroom=?,balcony=?, userId=? ,Location=? where id=?");
			ps.setString(1, bean.getPropertytitle());
			ps.setString(2, bean.getPropertyowner());
			ps.setString(3, bean.getPropertytype());
			ps.setString(4, bean.getSqurerate());
			System.out.println("11111111111111111");
			ps.setString(5, bean.getPrice());
			ps.setString(6, bean.getAddresss());
			ps.setString(7, bean.getDescription());
			ps.setString(8, bean.getStatus());
			ps.setString(9, bean.getImage());
			ps.setString(10, bean.getHall());
			ps.setString(11, bean.getBedroom());
			ps.setString(12, bean.getKitchan());
			ps.setString(13, bean.getBathroom());
			ps.setString(14, bean.getBalcony());
			ps.setLong(15, bean.getUserid());
			ps.setString(16, bean.getLocation());
			ps.setLong(17, bean.getId());
			ps.executeUpdate();
			System.out.println("222222222222222");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pk;
	}

	public PropertyBean findByStatus(String status, long userId) throws Exception {

		PropertyBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM property WHERE status=? and userId=?");
			ps.setString(1, status);
			ps.setLong(2, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new PropertyBean();
				bean.setId(rs.getLong(1));
				bean.setPropertytitle(rs.getString(2));
				bean.setPropertyowner(rs.getString(3));
				bean.setPropertytype(rs.getString(4));
				bean.setSqurerate(rs.getString(5));
				bean.setPrice(rs.getString(6));
				bean.setAddresss(rs.getString(7));
				bean.setDescription(rs.getString(8));
				bean.setStatus(rs.getString(9));
				bean.setImage(rs.getString(10));
				System.out.println("Image :" + bean.getImage());
				bean.setHall(rs.getString(11));
				bean.setBalcony(rs.getString(12));
				bean.setBathroom(rs.getString(13));
				bean.setKitchan(rs.getString(14));
				bean.setBedroom(rs.getString(15));
				bean.setUserid(rs.getLong(16));
				bean.setLocation(rs.getString(17));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bean;
	}

	public long ReqUpdate(PropertyBean bean) {
		System.out.println("in model 11111 update method");
		int pk = 0;
		try {
			Connection conn = JDBCDataSource.getConnection();

			PropertyModel model = new PropertyModel();
			PropertyBean propertybean = findByStatus(bean.getStatus(), bean.getUserid());
			if (propertybean != null) {
				throw new DuplicateRecordException("Request already Sent");
			}

			PreparedStatement ps = conn.prepareStatement("update property set status=? where id=?");
			System.out.println("status:" + bean.getImage());
			System.out.println("ID:" + bean.getId());
			ps.setString(1, bean.getStatus());
			ps.setLong(2, bean.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pk;
	}

	public List Request() throws Exception {
		System.out.println("in model list");
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from property where status=?");
			pstmt.setString(1, "Requested");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PropertyBean bean = new PropertyBean();
				bean.setId(rs.getLong(1));
				bean.setPropertytitle(rs.getString(2));
				bean.setPropertyowner(rs.getString(3));
				bean.setPropertytype(rs.getString(4));
				bean.setSqurerate(rs.getString(5));
				bean.setPrice(rs.getString(6));
				bean.setAddresss(rs.getString(7));
				bean.setDescription(rs.getString(8));
				bean.setStatus(rs.getString(9));
				bean.setImage(rs.getString(10));
				bean.setHall(rs.getString(11));
				bean.setBalcony(rs.getString(12));
				bean.setBathroom(rs.getString(13));
				bean.setKitchan(rs.getString(14));
				bean.setBedroom(rs.getString(15));
				bean.setUserid(rs.getLong(16));
				bean.setLocation(rs.getString(17));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List byerlist(long userId) throws Exception {
		System.out.println("in model BuyerRequest list");
		ArrayList list = new ArrayList();
		Connection conn = null;
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("select * from property where userId=?");
		pstmt.setLong(1, userId);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			PropertyBean bean = new PropertyBean();
			bean.setId(rs.getLong(1));
			bean.setPropertytitle(rs.getString(2));
			bean.setPropertyowner(rs.getString(3));
			bean.setPropertytype(rs.getString(4));
			bean.setSqurerate(rs.getString(5));
			bean.setPrice(rs.getString(6));
			bean.setAddresss(rs.getString(7));
			bean.setDescription(rs.getString(8));
			bean.setStatus(rs.getString(9));
			bean.setImage(rs.getString(10));
			bean.setHall(rs.getString(11));
			bean.setBalcony(rs.getString(12));
			bean.setBathroom(rs.getString(13));
			bean.setKitchan(rs.getString(14));
			bean.setBedroom(rs.getString(15));
			bean.setUserid(rs.getLong(16));
			bean.setLocation(rs.getString(17));
			list.add(bean);
		}
		return list;
	}

	public List search(PropertyBean bean) throws Exception {
		System.out.println("In search Method");
		StringBuffer sql = new StringBuffer("select * from property where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getLocation() != null && bean.getLocation().length() > 0) {
				sql.append(" AND Location = '" + bean.getLocation() + "'");
			}
			// sql.append(" order by id desc limit 1");
		}
		System.out.println("loc :" +sql);
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			//pstmt.setString(1, bean.getLocation());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new PropertyBean();
				bean.setId(rs.getLong(1));
				bean.setPropertytitle(rs.getString(2));
				bean.setPropertyowner(rs.getString(3)); 
				bean.setPropertytype(rs.getString(4));
				bean.setSqurerate(rs.getString(5));
				bean.setPrice(rs.getString(6));
				bean.setAddresss(rs.getString(7));
				bean.setDescription(rs.getString(8));
				bean.setStatus(rs.getString(9));
				bean.setImage(rs.getString(10));
				bean.setHall(rs.getString(11));
				bean.setBalcony(rs.getString(12));
				bean.setBathroom(rs.getString(13));
				bean.setKitchan(rs.getString(14));
				bean.setBedroom(rs.getString(15));
				bean.setUserid(rs.getLong(16));
				bean.setLocation(rs.getString(17));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return list;

	}
}
