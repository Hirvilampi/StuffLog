package kevat25.stufflog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import kevat25.stufflog.model.UserAccount;


import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private UserDetailsService userDetailsService;

    private UserAccount userAccount;

    public WebSecurityConfig(
        UserDetailsService userDetailsService 
   //     CustomAuthenticationSuccessHandler successHandler
        ) {
        this.userDetailsService = userDetailsService;
 //       this.successHandler = successHandler;
    } 



    // näitä voi sii lisätä myös tällaiseen white_list_url:n valmiiksi, jos haluaa
    // beanista lyhyemmän
    private static final AntPathRequestMatcher[] WHITE_LIST_URLS = {
            new AntPathRequestMatcher("/api/booklist**"),
            new AntPathRequestMatcher("/h2-console/**")
    };

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers(antMatcher("/css/**")).permitAll()
                        .requestMatchers(antMatcher("/signup")).permitAll()
                        .requestMatchers(antMatcher("/saveuser")).permitAll()
                        .requestMatchers(WHITE_LIST_URLS).permitAll()
                        .anyRequest().authenticated())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions
                        .disable())) // for h2console
                .formLogin(formlogin -> formlogin
                         .loginPage("/login")
                        .defaultSuccessUrl("/index", true)
                        .permitAll())
                .logout(logout -> logout.permitAll())
                .csrf(csrf -> csrf.disable()); // not for production, just for development



        return http.build();
    }

    /*
     * @Bean
     * public BCryptPasswordEncoder passwordEncoder() {
     * return new BCryptPasswordEncoder();
     * }
     * 
     * @Autowired
     * public void configureGlobal(AuthenticationManagerBuilder auth,
     * BCryptPasswordEncoder passwordEncoder) throws Exception {
     * auth.userDetailsService(userDetailsService).passwordEncoder( new
     * BCryptPasswordEncoder());
     * }
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

}
