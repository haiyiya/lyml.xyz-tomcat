package com.lyml.demo1.config;


import com.lyml.demo1.TestUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    TestUserDetailsService userDetailService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //未经登录允许访问，如未登录时候
                .antMatchers("/index", "/medicine", "/medicine/searchList", "/user/test1",
                        "/**/*.html",
                        "/**/*.js",
                        "/**/*.css",
                        "/**/*.jpg",
                        "/**/*.eot",
                        "/**/*.svg",
                        "/**/*.ttf",
                        "/**/*.woff",
                        "/**/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()//通过formLogin()定义当需要用户登录时候，转到的登录页面。
                .loginPage("/login")//登录页
                .loginProcessingUrl("/login")//需要验证登录的地址
                .defaultSuccessUrl("/login", true)//登录成功跳转地址
                .failureForwardUrl("/login?error=true")//密码错误跳转地址
                .permitAll()//未登录状态允许访问
                .and()
                .logout()//注销
                .logoutUrl("/logout")//注销地址
                .logoutSuccessUrl("/login?logout=true")//注销成功跳转地址
                .permitAll()//未登录状态允许访问
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())//设置操作表的Repository
                .tokenValiditySeconds(60 * 60 * 24 * 7)//设置记住我的时间为7天
                .userDetailsService(userDetailService)
                .and()
                .csrf().disable()//关闭csrf 防止循环定向
                .headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)//配置自定义userDetailService，密码验证
                .passwordEncoder(new BCryptPasswordEncoder());//密码加密方式，不可逆强加密
    }

    @Bean
    @ConditionalOnMissingBean(ClassPathTldsLoader.class)
    public ClassPathTldsLoader classPathTldsLoader(){
        return new ClassPathTldsLoader();
    }
}