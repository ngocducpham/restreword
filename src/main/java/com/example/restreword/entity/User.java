package com.example.restreword.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    private String name;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-YYYY")
    private Date birthDate;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Setting> settings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    /*
    public void removeSetting(Setting setting){
        settings.remove(setting);
        setting.removeUser(this);
    }


    @PreRemove
    public void removeUser(){
        for (Setting s: settings) {
            s.getUsers().remove(this);
        }
    }
    */



}
