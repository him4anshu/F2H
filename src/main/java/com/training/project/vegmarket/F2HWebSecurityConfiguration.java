package com.training.project.vegmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.training.project.vegmarket.service.F2HUserDetailService;

@Configuration
@EnableWebSecurity
public class F2HWebSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private F2HUserDetailService f2hUserDetailService;
	
	 @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(f2hUserDetailService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.
	        authorizeRequests()
	        .antMatchers("/").permitAll()
	        .antMatchers("/login").permitAll()
	        .antMatchers("/registration").permitAll()
	        .antMatchers("/admin/**").hasAuthority("ADMIN")
	        .anyRequest()
	        .authenticated()
	        .and().csrf().disable()
	        
	        .formLogin()
	        .loginPage("/login")
	        .loginPage("/")
	        .failureUrl("/login?error=true")
	        .successHandler(new MySimpleUrlAuthenticationSuccessHandler())
	        
	        .and().logout()
	        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	        .logoutSuccessUrl("/login").
	        
	        and().exceptionHandling();
	}
	
	 @Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
}
