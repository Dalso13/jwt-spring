package com.almond.jwt_spring.config.auth;

import com.almond.jwt_spring.dto.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class PrincipalDetails implements UserDetails {

    private User user;

    // 일반 로그인 사용자
    public PrincipalDetails(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();

        user.getRoleList().forEach(r -> {
            collect.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return r;
                }
            });
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
