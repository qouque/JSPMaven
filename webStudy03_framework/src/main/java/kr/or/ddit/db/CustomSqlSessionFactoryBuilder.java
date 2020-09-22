package kr.or.ddit.db;


import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * Persistence Layer 지원 framework, SQLMapper, DataMapper, ORM
 * @author PC-06
 *
 */
public class CustomSqlSessionFactoryBuilder {
	private static SqlSessionFactory sqlSessionFactory;
	
	static {
		String configFile = "kr/or/ddit/db/mybatis/configuration.xml";
		try {
			Reader reader = Resources.getResourceAsReader(configFile);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			throw new RuntimeException(e);
			
		}
	}
	
	public static SqlSessionFactory getsqlSessionFactory() {
		return sqlSessionFactory;
	}
}
