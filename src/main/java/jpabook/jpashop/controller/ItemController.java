package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) { // 상품 목록
        List<Item> items = itemService.findItems(); // 상품 목록을 조회해서
        model.addAttribute("items", items); // 모델에 담아서
        return "items/itemList"; // 상품 목록 화면으로 이동
    }

    @GetMapping("items/{itemId}/edit")// {itemId}는 @PathVariable로 받아온다. 변경될 수 있기 때문.
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId); // 상품을 찾아서

        BookForm form = new BookForm(); // BookForm을 생성하고 //merge를 사용하면 엔티티를 수정할 때 모든 필드를 수정해야 하지만 DTO를 사용하면 일부만 수정해도 된다.
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());


        model.addAttribute("form", form); // 모델에 담아서
        return "items/updateItemForm"; // 상품 수정 화면으로 이동
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) { // @ModelAttribute("form")으로 넘어오는 모델을 받아온다.

        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity()); // 상품을 업데이트하고

        return "redirect:/items"; // 상품 목록 화면으로 리다이렉트
    }
}





