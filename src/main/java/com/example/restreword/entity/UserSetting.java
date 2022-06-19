package com.example.restreword.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
public class UserSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;

    @NotNull
    private String value;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createDateTime;

    private Date updateDateTime;
}
