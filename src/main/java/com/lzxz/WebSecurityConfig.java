package com.lzxz;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
 * SpringSecurity简单配置
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
        	.antMatchers("/","/login").permitAll()//设置SpringSecurity对/和/login路径不拦截
        	.anyRequest().authenticated()
         	.and()
           	.formLogin()
        	.loginPage("/login") //设置SpringSecurity的登录访问页面的路径是/login
        	.defaultSuccessUrl("/chat") //登陆成功转向/chat页面
          	.permitAll()
			.and()
		 	.logout()
			.permitAll();
    }
    
    //在内存中分别配置2个User角色：tangyd和zhaoxz
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth
        	.inMemoryAuthentication()
        	.withUser("tangyd").password("tangyd").roles("USER")
        	.and()
           	.withUser("zhaoxz").password("zhaoxz").roles("USER");
    }
    
    //SpringSecurity不拦截/resources/static目录下的静态资源
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/static/**");
    }

}