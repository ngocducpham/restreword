package com.example.restreword.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOutput {
    private Integer id;

    private String name;

    //@JsonFormat(pattern = "dd-MM-yyyy")
    //private Date birthDate;
    private Integer age;

    @JsonIgnore
    private List<SettingOutput> settings;
    private List<PostOutput> posts;
}
