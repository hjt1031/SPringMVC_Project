package hello.itemservice.domain.item;

import lombok.Data;

//Data 는 편리하긴하기만 메인도메인 에서 사용하기 위험하다
@Data
public class Item {

    private Long id;
    private String itemName;
    // Integer 쓴 이유는 price 가 null 일 수도있어서.
    private Integer price;
    private Integer quantity; //수량

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }


}
