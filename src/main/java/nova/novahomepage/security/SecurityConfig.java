package nova.novahomepage.security;

import lombok.RequiredArgsConstructor;
import nova.novahomepage.security.handler.JwtAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryFilter jwtAuthenticationEntryFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/h2/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                        .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
//                        .addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class)
                        .exceptionHandling()
                        .authenticationEntryPoint(jwtAuthenticationEntryFilter)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        ;
        http.authorizeRequests()
                        .antMatchers("/login").permitAll()
                        .antMatchers("/users").permitAll()
                .and()
                .apply(new JwtSecurityConfig(tokenProvider))
                ;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//   @Bean
//    public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
//        FilterSecurityInterceptor permitAllFilter = new FilterSecurityInterceptor();
//        permitAllFilter.setAuthenticationManager(authenticationManagerBean());
//        permitAllFilter.setAccessDecisionManager(affirmativeBased());
//        return permitAllFilter;
//    }
//
//
//    private AccessDecisionManager affirmativeBased() {
//        AffirmativeBased affirmativeBased = new AffirmativeBased(getAccessDecisionVoters());
//        return affirmativeBased;
//    }
//
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    public List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
//        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
//        accessDecisionVoters.add(roleVoter());
//        return accessDecisionVoters;
//    }
//
//    @Bean
//    public AccessDecisionVoter<? extends Object> roleVoter() {
//        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierachy());
//        return roleHierarchyVoter;
//    }
//
//    @Bean
//    public RoleHierarchyImpl roleHierachy() {
//        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//        return roleHierarchy;
//    }

}
