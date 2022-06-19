package com.example.restreword.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Integer id;

    @NotNull
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;

}

