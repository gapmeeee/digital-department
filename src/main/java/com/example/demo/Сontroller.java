package com.example.demo;

import com.example.demo.moduls.enums.Product;
import com.example.demo.moduls.User;
import com.example.demo.moduls.enums.ProductService;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/employee")
public class Сontroller {
    private final ProductService productService;

    @GetMapping("/")
    public String products(@RequestParam(name="title", required = false) String title, Principal principal, Model model){
        model.addAttribute("products", productService.listProducts(title));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        return "main";
    }



}
