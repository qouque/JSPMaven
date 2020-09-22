package kr.or.ddit.vo;

import java.io.Serializable;


/**
 * Domain Layer : model data, 정보의 속성과 의미 부여및 영역제한.
 * 
 *
 */
public class DataBase_PropertiesVO implements Serializable{
	
	private String property_Name;
	private String property_Value;
	private String description;
	
	private DataBase_PropertiesVO() {}
	
	private DataBase_PropertiesVO(String property_Name, String property_Value, String description) {
		super();
		this.property_Name = property_Name;
		this.property_Value = property_Value;
		this.description = description;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((property_Name == null) ? 0 : property_Name.hashCode());
		result = prime * result + ((property_Value == null) ? 0 : property_Value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataBase_PropertiesVO other = (DataBase_PropertiesVO) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (property_Name == null) {
			if (other.property_Name != null)
				return false;
		} else if (!property_Name.equals(other.property_Name))
			return false;
		if (property_Value == null) {
			if (other.property_Value != null)
				return false;
		} else if (!property_Value.equals(other.property_Value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataBase_PropertiesVO [property_Name=" + property_Name + ", property_Value=" + property_Value
				+ ", description=" + description + "]";
	}
	public String getProperty_Name() {
		return property_Name;
	}
	public void setProperty_Name(String property_Name) {
		this.property_Name = property_Name;
	}
	public String getProperty_Value() {
		return property_Value;
	}
	public void setProperty_Value(String property_Value) {
		this.property_Value = property_Value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public static class Database_ProperitesVOBuilder{
		
		private String property_Name;
		private String property_Value;
		private String description;
		
		public Database_ProperitesVOBuilder property_Name(String property_Name) {
			this.property_Name = property_Name;
			return this;
		}
		
		public Database_ProperitesVOBuilder property_Value(String property_Value) {
			this.property_Value = property_Value;
			return this;
		}
		
		public Database_ProperitesVOBuilder description(String description) {
			this.description = description;
			return this;
		}
		
		public DataBase_PropertiesVO build() {
			return new DataBase_PropertiesVO(property_Name, property_Value, description);
		}
	}
	
	public static Database_ProperitesVOBuilder getBuilder() {
		return new Database_ProperitesVOBuilder();
	}
	
	
}
