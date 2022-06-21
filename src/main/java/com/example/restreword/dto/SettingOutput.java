package com.example.restreword.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingOutput {
    private String key;
    private String value;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date updateTime;
}
