package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(50) default 'ADMIN,USER'",length = 20,nullable = false,unique = true)
    private String name;

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return name;
    }

    public void setRole(String name) {
        this.name = name;
    }
}
