package com.example.restreword.entity;

import com.example.restreword.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @NotNull
    private String key;
    @NotNull
    private String value;

    @Column(updatable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date updatedAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> users;

    /*
    public void removeUser(User user){
        users.remove(user);
        user.removeSetting(this);
    }

    @PreRemove
    public void removeUser(){
        for (User u: users) {
            u.getSettings().remove(this);
        }
    }
*/




}
