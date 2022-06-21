package com.example.restreword.dto.mapper;

import com.example.restreword.dto.SettingInput;
import com.example.restreword.dto.SettingOutput;
import com.example.restreword.entity.Setting;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SettingMapper {
    SettingInput toInputDto(Setting setting);
    SettingOutput toOutputDto(Setting setting);
    Setting inputToEntity(SettingInput setting);
    List<SettingOutput> toOutputDtos(List<Setting> settings);
}
