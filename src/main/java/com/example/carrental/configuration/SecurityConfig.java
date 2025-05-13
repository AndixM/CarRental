package com.example.carrental.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/api/**").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/api/**").permitAll()
//                            .requestMatchers("/api/register").permitAll()
//                            .requestMatchers("/api/signin").permitAll()
//                            .requestMatchers("/api/signout").permitAll()
//                            .requestMatchers(HttpMethod.PUT, "/api/addCar").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/reservations").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/products").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/product").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/populateDB").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/branch/{id}").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/api/car/{id}").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/api/rental/{id}").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/api/reservation/{id}").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/api/roles/{id}").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/api/user/{id}").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/api/branch").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/statuses").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/car").permitAll()//.hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/api/rentals").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.GET, "/api/roles").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/users").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.PUT, "/api/addBranch").permitAll()
//                    .requestMatchers(HttpMethod.PUT, "/api/addRental").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.PUT, "/api/addReservation").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.PUT, "/api/addRole").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.PUT, "/api/addUser").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.DELETE, "/api/deleteBranch/{id}").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.DELETE, "/api/deleteCarWithReservation/{idCar}/{idReservation}").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.DELETE, "/api/deleteCar/{id}").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.DELETE, "/api/deleteRental/{id}").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.DELETE, "/api/deleteReservation/{id}").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.DELETE, "/api/deleteRole/{id}").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.DELETE, "/api/deleteUser/{id}").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.DELETE, "/api/deleteUserWithReservation/{idUser}/{idReservation}").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.DELETE, "/api/deleteUserFromReservation/{idUser}/{idReservation}").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/api/updateBranch").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/api/updateCar").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/api/updateRental").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/api/updateReservation").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/api/updateRole").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/api/updateUser").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/api/addBranchToRental").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/api/addCarToBranch/{carId}/{branchId}").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/api/addReservationToUser/{userId}/{reservationId}").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/api/addRoleToUser/{roleId}/{userId}").hasAnyRole("USER", "ADMIN")
                            .anyRequest().authenticated();
                })
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
