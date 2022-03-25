package hello.itemservice.domain.item;

import lombok.Data;

@Data // 사용 시 주의 - @Getter,@Setter 사용하는 게 더 안전
public class Item {

	private Long id;
	private String itemName;
	private Integer price; //값이 들어가지 않는 경우 대비 -> int 대신 Integer 사용 
	private Integer quantity;
	
	 public Item() {
     }

	 public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	 
	 
}
