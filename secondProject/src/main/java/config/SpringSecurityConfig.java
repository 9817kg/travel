package second.project.config;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import second.project.oauth2.OAuth2Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;








@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
    @Autowired
    private final OAuth2Service oAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                                .requestMatchers("/status", "/view/join", "/auth/join", "/my/", "/my/main").permitAll()
                                .requestMatchers("/findEmail", "/resultFindEmail", "/findPw").permitAll()
                                .requestMatchers("/**").permitAll()
                                .requestMatchers("/info/teacherInfo").permitAll()
                                .requestMatchers("/view/admin").hasRole("ADMIN")


                                .anyRequest().authenticated()
                )
                .formLogin(login -> login
                                .loginPage("/view/login")
                                .loginProcessingUrl("/login-process")
                                .usernameParameter("email")
                                .passwordParameter("password")
                               
                                .defaultSuccessUrl("/", false) 
                                .successHandler((request, response, authentication) -> {
                                   
                                    if (authentication != null && authentication.getAuthorities().stream()
                                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                                        response.sendRedirect("/admin/admin");
                                    } else {
                                        response.sendRedirect("/view/mainLog");
                                    }
                                })


                )
                .oauth2Login(oauth2Configurer -> oauth2Configurer
                                .loginPage("/view/login")

                                .defaultSuccessUrl("/view/mainLog2", true) // ·Î±×ÀÎ ¼º°ø½Ã ÀÌµ¿ÇÒ URL
                                .userInfoEndpoint()// »ç¿ëÀÚ°¡ ·Î±×ÀÎ¿¡ ¼º°øÇÏ¿´À» °æ¿ì,
                       
                                .userService(oAuth2UserService)// ÇØ´ç ¼­ºñ½º ·ÎÁ÷À» Å¸µµ·Ï ¼³Á¤
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true));
     

        return http.build();
    }
   
    
}