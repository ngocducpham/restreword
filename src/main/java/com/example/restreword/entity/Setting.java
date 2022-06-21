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

    private String key;

    private String value;

    @CreationTimestamp
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "setting_user",
            joinColumns = @JoinColumn(name = "setting_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @PreRemove
    public void removeUser(){
        for (User u: users) {
            u.getSettings().remove(this);
        }
    }


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
