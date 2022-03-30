package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;
    // 생성자하가 하나면 Autowired 생략 가능, @RequiredArgsConstructor 사용하면 final 이 붙은 생성자를 만들어준다.
//    @Autowired
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }


    @GetMapping
    public String item(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item",item);
        return "basic/item";
    }


//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item,
                       Model model){

        itemRepository.save(item);

//        model.addAttribute("item",item); // 자동 추가, 생략 가능
        return "basic/item";
    }

//    @PostMapping("/add")
    //ModelAttribute 명명 을 안정해주면 클래스 명 첫글자를 소문자로 바꿔서 넣어준다 ex) Item => item
    public String addItemV3(@ModelAttribute Item item){
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    // ModelAttribute 생략 가능
    public String addItemV4(Item item){
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * 새로고집(마지막 요청을 다시 요청한다) 하면 POST 요청을 다시 요청을 하기 떄문에 계속 상품이 등록이 된다.
     */
    // 리다이렉트
//    @PostMapping("/add")
    public String addItemV5(Item item){
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    // 리다이렉트에트리뷰트 (메시지 추가)
    //redirectAttributes 를 사용하면 URL 인코딩도 해주고, pathVarible 쿼리 파라미터까지 처리해준다.
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/basic/items/{itemId}";
    }

    //상품 수정 폼
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }

    //상품 수정 처리
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        //리다이렉트
        return "redirect:/basic/items/{itemId}";
    }

    // 테스트용 데이터 추가.
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }

}