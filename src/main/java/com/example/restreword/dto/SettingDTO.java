package com.example.restreword.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingDTO {
    @JsonIgnore
    private Integer id;

    @NotNull
    private String key;

    @NotNull
    private String value;

    @JsonIgnore
    private List<UserSettingDTO> userSettings;
}
