package com.almond.jwt_spring.config.auth;


import com.almond.jwt_spring.dto.User;
import com.almond.jwt_spring.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = mapper.findByUsername(username);
        log.info("user : {}", user);
        if (user != null) {
            return new PrincipalDetails(user);
        }
        return null;
    }
}
