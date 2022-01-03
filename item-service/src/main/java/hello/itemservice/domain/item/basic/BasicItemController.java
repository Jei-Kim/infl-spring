package hello.itemservice.domain.item.basic;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class BasicItemController {

	private final ItemRepository itemRepository;

	// 롬복 에러때문에 추가
//	public BasicItemController(ItemRepository itemRepository) {
//		this.itemRepository = itemRepository;
//	}

	@GetMapping
	public String items(Model model) {
		List<Item> items = itemRepository.findAll();
		model.addAttribute("items", items);
		return "basic/items";
	}

	// PathVariable 로 넘어온 상품ID로 상품을 조회하고, 모델에 담아둔다.
	// 그리고 뷰 템플릿을 호출한다.
	@GetMapping
	public String item(@PathVariable Long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "basic/item";
	}

	// 단순히 뷰 템플릿만 호출
	@GetMapping("/add")
	public String addForm() {
		return "basic/addForm";
	}

	/*
	 * 
	  @PostMapping("/add") public String addItemV1(@RequestParam String itemName,
	  
	  @RequestParam int price,
	  
	  @RequestParam Integer quantity, Model model) { Item item = new Item();
	  item.setItemName(itemName); item.setPrice(price); item.setQuantity(quantity);
	  itemRepository.save(item); model.addAttribute("item", item); return
	  "basic/item"; }
	 */
	
	/**
	* @ModelAttribute("item") Item item
	* model.addAttribute("item", item); 자동 추가 
	  @PostMapping("/add")
	  public String addItemV2(@ModelAttribute("item") Item item, Model model) {
	itemRepository.save(item); //model.addAttribute("item", item); //자동 추가, 생략 가능
	      return "basic/item";
	  } */
	  
	  /**
	  * @ModelAttribute name 생략 가능
	  * model.addAttribute(item); 자동 추가, 생략 가능
	  * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item 
	    @PostMapping("/add")
	    public String addItemV3(@ModelAttribute Item item) {
	        itemRepository.save(item);
	        return "basic/item";
	    }*/
	    
	    /**
	    * @ModelAttribute 자체 생략 가능
	    * model.addAttribute(item) 자동 추가 
	      @PostMapping("/add")
	      public String addItemV4(Item item) {
	          itemRepository.save(item);
	          return "basic/item";
	      }*/
	

	  /**
	   * PRG - Post/Redirect/Get
	  
	  @PostMapping("/add")
	  public String addItemV5(Item item) {
	      itemRepository.save(item);
	      return "redirect:/basic/items/" + item.getId();
	  }*/
	      
	      @GetMapping("/{itemId}/edit")
	    public String editForm(@PathVariable Long itemId, Model model) {
	    	Item item = itemRepository.findById(itemId);
	    	model.addAttribute("item", item);
	    	return "basic/editForm";
	    } 
	      
	      /**
	       * RedirectAttributes
	       */
	      @PostMapping("/add")
	      public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
	          Item savedItem = itemRepository.save(item);
	          redirectAttributes.addAttribute("itemId", savedItem.getId());
	          redirectAttributes.addAttribute("status", true);
	          return "redirect:/basic/items/{itemId}";
	      }
	      
	/**
	 * 테스트용 데이터 추가
	 */
	@PostConstruct
	public void init() {
		itemRepository.save(new Item("testA", 10000, 10));
		itemRepository.save(new Item("testB", 20000, 20));
	}
}
