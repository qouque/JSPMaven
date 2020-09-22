package kr.or.ddit.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(of= "zipcode")
public class ZipVO {
	
	private String zipcode;
	private String sido;
	private String gugun;
	private String dong;
	private String bunji;
	
	public String getAddress() {
		StringBuffer address = new StringBuffer();
		address.append(sido);
		address.append(" " + gugun);
		if(dong!=null) address.append(" " + dong);
		if(bunji!=null) address.append(" " + bunji);
		return address.toString();
	}
	
}
