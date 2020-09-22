package kr.or.ddit.prop.service;

import java.util.List;

import kr.or.ddit.vo.DataBase_PropertiesVO;

/**
 * Business Logic Layer (Service Layer)
 * @author PC-06
 *
 */
public interface IDataBasePropertyService {
	
	
	/**
	 * property 정보를 읽고, property_name에 시간 데이터를 concat.
	 * @param param TODO
	 * @return
	 */
	public List<DataBase_PropertiesVO> readDataBaseProperties(DataBase_PropertiesVO param);
	
	public List<String> readAllProperty_names();
	
}
