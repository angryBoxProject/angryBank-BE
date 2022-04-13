package com.teamY.angryBox.config.security;

import com.teamY.angryBox.config.properties.AppProperties;
import com.teamY.angryBox.config.properties.CorsProperties;
import com.teamY.angryBox.config.security.oauth.*;
import com.teamY.angryBox.config.security.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.teamY.angryBox.config.security.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.teamY.angryBox.config.security.oauth.handler.TestHandler;
import com.teamY.angryBox.config.security.oauth.handler.TokenDeniedHandler;
import com.teamY.angryBox.config.security.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.teamY.angryBox.config.security.service.CustomUserDetailsService;
import com.teamY.angryBox.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AuthTokenProvider authTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final TokenDeniedHandler tokenDeniedHandler;
    private final CustomOAuth2UserService oAuth2UserService;
    private final MemberService memberService;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //토큰 필터 설정
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(authTokenProvider, memberService);
    }

    //UserDetailsService 설정
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    //auth 매니저 설정
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /*
     * 쿠키 기반 인가 Repository
     * 인가 응답을 연계 하고 검증할 때 사용.
     * */
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    /*
     * Oauth 인증 성공 핸들러
     * */
    @Bean
    public TestHandler oAuth2AuthenticationSuccessHandler() {
//        return new OAuth2AuthenticationSuccessHandler(
//                authTokenProvider,
//                appProperties,
//                /*userRefreshTokenRepository,*/
//                oAuth2AuthorizationRequestBasedOnCookieRepository()
//        );
        return new TestHandler();
    }

    /*
     * Oauth 인증 실패 핸들러
     * */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable();
        http
                .authorizeRequests()
                .antMatchers("/hello").authenticated()
                .anyRequest().permitAll();

        //.antMatchers("/api/**").hasAnyAuthority(RoleType.USER.getCode())
        //.anyRequest().authenticated()

        http.
                exceptionHandling().
                authenticationEntryPoint(new JwtAuthenticationEntryPoint()).
                accessDeniedHandler(tokenDeniedHandler);

//        http.
//                oauth2Login()
//                .authorizationEndpoint()
//                .baseUri("/oauth2/authorize")
//                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
//                .and()
//                .redirectionEndpoint()
//                .baseUri("/*/oauth2/code/*")
//                .and()
//                .userInfoEndpoint()
//                .userService(oAuth2UserService)
//                .and()
//                .successHandler(oAuth2AuthenticationSuccessHandler())
//                .failureHandler(oAuth2AuthenticationFailureHandler());
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); //프론트와 연결 테스트 실패 : Spring Boot에서 CORS 설정 시 .allowCredentials(true)와 .allowedOrigins("*")를 동시에 사용할 수 없도록 업데이트 됨 > .allowedOrigins("*") -> .allowed/originPatterns("*")로 변경하여 해결
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        //configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
