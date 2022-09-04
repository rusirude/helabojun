package com.leaf.helabojun.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "cubis_user")
public class User extends Common{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;
    @Column(name = "username", nullable = false)
    private String username;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title", nullable = false)
    private Title title;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "password", nullable = false)
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", nullable = false)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_role", nullable = false)
    private UserRole userRole;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location", nullable = false)
    private Location location;
    @Column(name = "first_login", nullable = false)
    private Boolean firstLogin;
    @Column(name = "system_user", nullable = false)
    private Boolean systemUser;


}
