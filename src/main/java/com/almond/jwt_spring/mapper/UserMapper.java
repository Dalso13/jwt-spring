package com.almond.jwt_spring.mapper;

import com.almond.jwt_spring.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int join(User user);
    User findByUsername(String username);
}
