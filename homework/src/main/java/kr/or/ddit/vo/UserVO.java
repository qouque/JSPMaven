package kr.or.ddit.vo;

import java.io.Serializable;

public class UserVO implements Serializable {
	
	private String userid       ;
	private String usernm       ;
	private String pass         ;
	private String reg_dt       ;
	private String alias        ;
	private String addr1        ;
	private String addr2        ;
	private String zipcode      ;
	private String filename     ;
	private String realfilename ;
	
	
	private UserVO(String userid, String usernm, String pass, String reg_dt, String alias, String addr1, String addr2,
			String zipcode, String filename, String realfilename) {
		super();
		this.userid = userid;
		this.usernm = usernm;
		this.pass = pass;
		this.reg_dt = reg_dt;
		this.alias = alias;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.zipcode = zipcode;
		this.filename = filename;
		this.realfilename = realfilename;
	}
	
	@Override
	public String toString() {
		return "UserVO [userid=" + userid + ", usernm=" + usernm + ", pass=" + pass + ", reg_dt=" + reg_dt + ", alias="
				+ alias + ", addr1=" + addr1 + ", addr2=" + addr2 + ", zipcode=" + zipcode + ", filename=" + filename
				+ ", realfilename=" + realfilename + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
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
		UserVO other = (UserVO) obj;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getUsernm() {
		return usernm;
	}
	public void setUsernm(String usernm) {
		this.usernm = usernm;
	}
	
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getRealfilename() {
		return realfilename;
	}
	public void setRealfilename(String realfilename) {
		this.realfilename = realfilename;
	}
	
	public static class UserVOBuilder{
		
		private String userid       ;
		private String usernm       ;
		private String pass         ;
		private String reg_dt       ;
		private String alias        ;
		private String addr1        ;
		private String addr2        ;
		private String zipcode      ;
		private String filename     ;
		private String realfilename ;
		
		public UserVOBuilder uesrid(String userid) {
			this.userid = userid;
			return this;
		}
		public UserVOBuilder usernm(String usernm) {
			this.usernm = usernm;
			return this;
		}
		public UserVOBuilder pass(String pass) {
			this.pass = pass;
			return this;
		}
		public UserVOBuilder reg_dt(String reg_dt) {
			this.reg_dt = reg_dt;
			return this;
		}
		public UserVOBuilder alias(String alias) {
			this.alias = alias;
			return this;
		}
		public UserVOBuilder addr1(String addr1) {
			this.addr1 = addr1;
			return this;
		}
		public UserVOBuilder addr2(String addr2) {
			this.addr2 = addr2;
			return this;
		}
		public UserVOBuilder zipcode(String zipcode) {
			this.zipcode = zipcode;
			return this;
		}
		public UserVOBuilder filename(String filename) {
			this.filename = filename;
			return this;
		}
		public UserVOBuilder realfilename(String realfilename) {
			this.realfilename = realfilename;
			return this;
		}
		
		public UserVO build() {
			return new UserVO(userid, usernm, pass, reg_dt, alias, addr1, addr2, zipcode, filename, realfilename);
		}
	}
	
	public static UserVOBuilder getBuilder() {
		return new UserVOBuilder();
	}
}
