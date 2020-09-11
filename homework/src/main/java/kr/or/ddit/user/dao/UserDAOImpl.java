package kr.or.ddit.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.utils.JDBCUtils;
import kr.or.ddit.vo.UserVO;

public class UserDAOImpl implements IUserDAO {

	
	
	@Override
	public List<UserVO> selectAllUser() {
		List<UserVO> list = new ArrayList<>();
		try(
			Connection conn = JDBCUtils.getConnection();
			Statement stmt = conn.createStatement();
		){
			
			String sql = "SELECT * FROM users";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(
					UserVO.getBuilder().uesrid(rs.getString("userid"))
									   .usernm(rs.getString("usernm"))
									   .pass(rs.getString("pass"))
									   .reg_dt(rs.getString("reg_dt"))
									   .alias(rs.getString("alias"))
									   .addr1(rs.getString("addr1"))
									   .addr2(rs.getString("addr2"))
									   .zipcode(rs.getString("zipcode"))
									   .filename(rs.getString("filename"))
									   .realfilename(rs.getString("realfilename"))
									   .build()
				);
			}
			
			
		}catch(SQLException e) {
			e.getStackTrace();
			throw new RuntimeException(e);
		}
		
		
		return list;
	}

	@Override
	public List<String> selectAlluserId() {
		List<String> list = new ArrayList<>();
		
		try(
			Connection conn = JDBCUtils.getConnection();
			Statement stmt = conn.createStatement();
		){
			
			String sql = "SELECT userid from users";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(rs.getString(1));
			}
			
		}catch (SQLException e) {
			e.getStackTrace();
			throw new RuntimeException(e);
		}
		
		return list;
	}

}
