package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = {"lprod_id"})
@NoArgsConstructor
@AllArgsConstructor
public class LProdVO implements Serializable {
	
	private String lprod_id;
	private String lprod_gu;
	private String lprod_nm;
	
	
	
}
