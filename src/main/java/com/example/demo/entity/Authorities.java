package com.example.demo.entity;

import com.example.demo.enums.ERole;

import javax.persistence.*;

@Entity
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50) default 'ROLE_ADMIN,ROLE_USER'",length = 20,nullable = false,unique = true)
    private ERole role;

    public Authorities(){}

    public Authorities(ERole role){
        this.role = role;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Authorities{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }

}
