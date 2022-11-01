package ru.gb.homework11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.homework11.entities.Product;
import ru.gb.homework11.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/findbyid/{id}")
    public String showFindById(Model model, @PathVariable(value="id") Long id) {
        Product product = null;
        product = productService.findById(id).get();
        model.addAttribute("result", product);
        return "byid";
    }

    @GetMapping("/findall/{page}/{edit_id}")
    public String showFindAll(Model model, @PathVariable(value="page") int page, @PathVariable(value = "edit_id") Long editId) {
        List<Product> list = null;
        list = productService.findAll(page);
        ArrayList<Integer> pages=new ArrayList<>();
        for (int i=1;i<=productService.getAmountPages();i++){
            pages.add(i);
        }
        Product product;
        if (editId==0){
            product=new Product();
            product.setTitle("");
            product.setPrice(0);
        }else {
            product=productService.findById(editId).get();
            System.out.println(product);
        }
        model.addAttribute("products", list);
        model.addAttribute("pages",pages);
        model.addAttribute("product",product);
        model.addAttribute("currentpage",page);
        return "products";
    }

    @GetMapping("/removebyid/{id}")
    public String deleteById(Model model, @PathVariable(value = "id") Long id) {
        Product product = null;
        productService.deleteProduct(id);
        return "redirect:/product/findall/1/0";
    }

    @GetMapping("/showForm")
    public String showSimpleForm(Model model) {
        Product product = new Product();
        product.setTitle("");
        product.setPrice(0);
        model.addAttribute("product", product);
        return "productform";
    }

    @PostMapping("/processForm")
    public String processForm(@ModelAttribute Product product, Model model) {
        productService.addProduct(product);
        return "redirect:/product/findall/1/0";
    }
}
