package com.example.restreword.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class UserDTO {
    private Integer id;

    @Size(min=2, message="Name should have at least 2 characters")
    @NotNull
    private String name;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-YYYY")
    private Date birthDay;

    private List<UserSettingDTO> userSettings;
}
