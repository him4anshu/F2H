package com.training.project.vegmarket.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.training.project.vegmarket.entity.User;
import com.training.project.vegmarket.service.UserService;

@RestController
public class LoginController {
	
	@Autowired
	UserService userService;

	@GetMapping(value = { "/", "/login" })
	public ModelAndView login() {
		System.out.println("=========================ShoppingCartController.login()======================");
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("user", new User());
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@GetMapping(value = "/registration")
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        
        System.out.println("USer enter data "+user.getName());
        System.out.println("USer enter data "+user.getLastName());
        
        User userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
       
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }
	
	/* @GetMapping(value="/home")
	    public ModelAndView home(){
	        ModelAndView modelAndView = new ModelAndView();
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        User user = userService.findUserByUserName(auth.getName());
	        List<ProductInfo> products = userService.getAllProduct();
			modelAndView.addObject("products", products);
	        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
	        modelAndView.setViewName("home");
	        return modelAndView;
	  }*/
	
	
}
