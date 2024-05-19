package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static jpabook.jpashop.service.ItemService.extracted;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String newItem(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String newItem(@ModelAttribute("form") BookForm form) {
        Book book = new Book();
        extracted(form, book);

        itemService.save(book);
        return "redirect:/items";
    }



    @GetMapping("/items")
    public String items(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, Model model) {
        Book one = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        extracted(form, one);
        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemID}/edit")
    public String editItem(@ModelAttribute("form") BookForm form) {
        Book book = (Book) itemService.findOne(form.getId());
        extracted(form, book);
        itemService.save(book);
        return "redirect:/items";
    }
}
