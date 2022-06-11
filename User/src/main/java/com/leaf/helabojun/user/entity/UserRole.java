package com.leaf.helabojun.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_role")
public class UserRole extends Common{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "status", nullable = false)
    private String status;

}
