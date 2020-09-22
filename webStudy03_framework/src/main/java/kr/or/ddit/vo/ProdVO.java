package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import kr.or.ddit.validate.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(of = {"prod_id"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdVO implements Serializable{
   
   @NotBlank(groups=UpdateGroup.class)
   @Size(max=10,groups=UpdateGroup.class)
   private String prod_id;
   @NotBlank
   @Size(max=40)
   private String prod_name;
   @NotBlank
   @Size(max=4)
   private String prod_lgu;
   @NotBlank
   @Size(max=6)
   private String prod_buyer;
   @Min(value=0)
   private Integer prod_cost;
   @Min(value=0)
   private Integer prod_price;
   @Min(value=0)
   private Integer prod_sale;
   @NotBlank 
   private String prod_outline;
   @Size(max=4000) 
   private String prod_detail;
   @NotBlank 
   @Size(max=40) 
   private String prod_img;
   @Min(value=0)
   private Integer prod_totalstock;
   @Size(max=7)
   private String prod_insdate;
   @Min(value=0)
   private Integer prod_properstock;
   @Size(max=20)
   private String prod_size;
   @Size(max=20) 
   private String prod_color;
   @Size(max=255) 
   private String prod_delivery;
   @Size(max=6) 
   private String prod_unit;
   private Integer prod_qtyin;
   private Integer prod_qtysale;
   private Integer prod_mileage;
   
   
   private BuyerVO buyer; // has a
   private List<MemberVO> memList; // has many
   private LProdVO lprodVO;

}