package ru.gb.homework11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.homework11.entities.User;
import ru.gb.homework11.service.ProductService;
import ru.gb.homework11.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setProductService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/showall")
    public String showListUsers(Model model){
        List<User> userList = userService.findAllUsers();
        model.addAttribute("listusers", userList);
        return "showlistusers";
    }
}
