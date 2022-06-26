package com.example.restreword.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
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

    private String password;

    private LocalDate birthDate;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
    private List<Setting> settings;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users", cascade = CascadeType.REMOVE)
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @PreRemove
    public void removeUser() {
        for (Setting s : settings) {
            s.getUsers().remove(this);
        }
    }

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
