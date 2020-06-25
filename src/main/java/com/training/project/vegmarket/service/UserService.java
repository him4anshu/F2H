package com.training.project.vegmarket.service;

import java.util.Arrays;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.training.project.vegmarket.entity.Role;
import com.training.project.vegmarket.entity.User;
import com.training.project.vegmarket.repository.RoleRepository;
import com.training.project.vegmarket.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public User findUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public User saveUser(@Valid User user) {
		 user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	        user.setActive(true);
	        Role userRole = roleRepository.findByRole("USER");
	        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
	        return userRepository.save(user);
		
	}

}
