package ciai.holidayacc.config

import ciai.holidayacc.data.UserRepository
import ciai.holidayacc.service.UsersService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Service


// check: https://docs.spring.io/spring-security/reference/servlet/getting-started.html

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity, users:UsersService,
                    authenticationManager: AuthenticationManager
    ): SecurityFilterChain {
        http {
            csrf { disable() }
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
            authorizeRequests {
                authorize(HttpMethod.GET, "/api/apartments", permitAll)
                authorize(HttpMethod.GET, "/api/apartments/{apartmentId}", permitAll)
                authorize(HttpMethod.GET, "/api/apartments/{apartmentId}/periods", permitAll)
                authorize(HttpMethod.GET, "/api/apartments/{apartmentId}/reservations", permitAll)
                authorize(HttpMethod.GET, "/api/apartments/{apartmentId}/reviews", permitAll)
                authorize("/swagger-ui/**", permitAll)
                authorize("/v3/**", permitAll)
                authorize(anyRequest, authenticated)

                //authorize(anyRequest, permitAll)
            }
            httpBasic { disable() }
            addFilterBefore<BasicAuthenticationFilter>(UserPasswordAuthenticationFilterToJWT("/login", authenticationManager))
            //addFilterBefore<BasicAuthenticationFilter>(UserPasswordAuthenticationFilterToJWT("/signup", users))
            addFilterBefore<BasicAuthenticationFilter>(JWTAuthenticationFilter())
        }
        return http.build()
    }

    @Bean
    fun authenticationManager(authConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authConfiguration.authenticationManager
    }

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

}

@Service
class MyUserDetailsService(val users: UserRepository, val passwordEncoder: PasswordEncoder) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? =
        username?.let { it ->
            users.findById(it).map {
                if (it.username == "admin"){
                    User.withUsername(it.username)
                        .password(passwordEncoder.encode(it.password))
                        .roles("MANAGER")
                        .build()
                } else {
                    User.withUsername(it.username)
                        .password(passwordEncoder.encode(it.password))
                        .roles("USER")
                        .build()
                }

            }.orElse(null)
        }

}