package springtest;

import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

@Controller
public class WebController {
    @Autowired
    AddressRepository addressRepository;
    @GetMapping("/books")
    public String address(Model model) {
        ArrayList<AddressBook> books = (ArrayList<AddressBook>) addressRepository.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/bookbuddies")
    public String bookbuddies(Model model, @RequestParam(name="bookid", required=true) Long id) {
        ArrayList<AddressBook> books = (ArrayList<AddressBook>) addressRepository.findAllById(Collections.singleton(id));

        for(BuddyInfo a : books.get(0).getBuddies()){System.out.println(a);}

        model.addAttribute("buddies", books.get(0).getBuddies());
        return "bookbuddies";
    }

    @GetMapping("/result")
    public String greetingSubmit(Model model) {
        return "result";
    }

    @GetMapping("/createbooks")
    public String greetingForm(Model model) {
        model.addAttribute("book", new AddressBook());
        return "createbooks";
    }

    @PostMapping("/addContact")
    public String addContact(AddressBook book) {
        book.addBuddy(new BuddyInfo());
        return "createbooks :: contacts"; // returning the updated section
    }
}