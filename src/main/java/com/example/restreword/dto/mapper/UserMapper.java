package com.example.restreword.dto.mapper;

import com.example.restreword.dto.UserInput;
import com.example.restreword.dto.UserOutput;
import com.example.restreword.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Mapper(uses = {PostMapper.class, SettingMapper.class, RoleMapper.class}, componentModel = "spring")
public abstract class UserMapper {
    public abstract UserInput toInputDto(User user);

    @Mapping(target = "age", source = "birthDate", qualifiedByName = "ageCalculator")
    public abstract UserOutput toOutputDto(User user);

    public abstract User inputToEntity(UserInput user);

    public abstract List<UserOutput> toOutputDtos(List<User> users);

    @Named("ageCalculator")
    protected Integer ageCalculator(LocalDate birthDate) {
        LocalDate now = LocalDate.now();
        return now.getYear() - birthDate.getYear();
    }
}
