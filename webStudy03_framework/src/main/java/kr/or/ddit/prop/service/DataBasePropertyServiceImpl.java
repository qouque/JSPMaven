package kr.or.ddit.prop.service;

import java.util.Calendar;
import java.util.List;

import kr.or.ddit.prop.dao.DataBasePropertyDAO_JDBC;
import kr.or.ddit.prop.dao.IDataBasePropertiesDAO;
import kr.or.ddit.vo.DataBase_PropertiesVO;

public class DataBasePropertyServiceImpl implements IDataBasePropertyService {

	// 결합력 최상!! HCLC(High Cohesion, Loose Coupling)
	IDataBasePropertiesDAO dao = new DataBasePropertyDAO_JDBC();
	
	@Override
	public List<DataBase_PropertiesVO> readDataBaseProperties(DataBase_PropertiesVO param) {
		List<DataBase_PropertiesVO> list = dao.selectDataBaseProperties(param);
		Calendar cal = Calendar.getInstance();
		String ptrn = "%s[%tc]";
		for(DataBase_PropertiesVO vo : list) {
			vo.setProperty_Name(String.format(ptrn, vo.getProperty_Name(), cal));
			
		}
		return list;
	}

	@Override
	public List<String> readAllProperty_names() {
		List<String> list = dao.selectAllProperty_Names();
		return list;
	}

}
