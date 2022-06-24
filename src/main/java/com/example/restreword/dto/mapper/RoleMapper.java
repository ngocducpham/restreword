package com.example.restreword.dto.mapper;

import com.example.restreword.dto.RoleOutput;
import com.example.restreword.entity.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleOutput toOutputDto(Role role);
    List<RoleOutput> toOutputDtos(List<Role> roles);
}
