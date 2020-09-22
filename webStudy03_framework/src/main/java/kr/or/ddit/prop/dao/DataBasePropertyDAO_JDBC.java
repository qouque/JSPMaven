package kr.or.ddit.prop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.vo.DataBase_PropertiesVO;

public class DataBasePropertyDAO_JDBC implements IDataBasePropertiesDAO {

	static {
		// 클래스가 메모리에 로딩될때 실행
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	public Connection getConnection() throws SQLException{
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "lhu";
		String password = "java";
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	
	@Override
	public List<DataBase_PropertiesVO> selectDataBaseProperties(DataBase_PropertiesVO param) {
		
		
		String sql = "SELECT PROPERTY_NAME, PROPERTY_VALUE, DESCRIPTION FROM DATABASE_PROPERTIES";
		StringBuffer where = null;
		if(param != null) {
			where = new StringBuffer();
			String ptrn = " %s LIKE '%%%s%%' ";
			if(StringUtils.isNotBlank(param.getProperty_Name())) {
				where.append(String.format(ptrn, "PROPERTY_NAME", param.getProperty_Name()));
//				sql += "and PROPERTY_NAME = '" + param.getProperty_Name() + "'";
			}
			if(StringUtils.isNotBlank(param.getProperty_Value())) {
				if(where.length() > 0) {
					where.append(" AND ");
				}
				where.append(String.format(ptrn, "PROPERTY_VALUE", param.getProperty_Value()));
//				sql += "and PROPERTY_VALUE = '" + param.getProperty_Value() + "'";
			}
			if(StringUtils.isNotBlank(param.getDescription())) {
				if(where.length() > 0) {
					where.append(" AND ");
				}
				where.append(String.format(ptrn, "DESCRIPTION", param.getDescription()));
//				sql += "and DESCRIPTION = '" + param.getDescription() + "'";
			}
			if(where.length() > 0) {
				where.insert(0, " WHERE ");
			}
		}
		sql = sql + where;
		
		//System.out.println(sql);
		List<DataBase_PropertiesVO> list = new ArrayList<>();
		
		try(
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
		){
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				DataBase_PropertiesVO dbpVO = 
						DataBase_PropertiesVO.getBuilder().property_Name(rs.getString("PROPERTY_NAME"))
														  .property_Value(rs.getString("PROPERTY_VALUE"))
														  .description(rs.getString("DESCRIPTION"))
														  .build();
				list.add(dbpVO);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	} 

	@Override
	public List<String> selectAllProperty_Names() {
		List<String> list = new ArrayList<>();
		try(
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
		){
			
			String sql = "SELECT PROPERTY_NAME FROM DATABASE_PROPERTIES";
			//System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				list.add(rs.getString("PROPERTY_NAME"));
			}
			return list;
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}

}
