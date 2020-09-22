package kr.or.ddit.prop.dao;

import java.util.List;

import kr.or.ddit.vo.DataBase_PropertiesVO;

/**
 * 인터페이스의 장점
 *  - 협업
 *  - 실행코드의 캡슐화
 *  
 * @author PC-06
 *
 */
public interface IDataBasePropertiesDAO {
	
	public List<DataBase_PropertiesVO> selectDataBaseProperties(DataBase_PropertiesVO param);
	public List<String> selectAllProperty_Names();
	
}
