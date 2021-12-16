package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(length = 50, nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    @NotBlank
    private String password;
    @Column(length = 150,nullable = false)
    @NotBlank
    private String firstName;
    @Column(length = 150,nullable = false)
    @NotBlank
    private String lastName;
    @Column(nullable = false)
    @Email
    @NotBlank
    private String email;
    @Column(length = 100,nullable = false)
    @NotBlank
    private String number;
    @Column(nullable = false)
    @NotBlank
    private String address;
    @Column(nullable = false)
    @NotBlank
    private String city;
    @Column(nullable = false)
    private boolean active;
    @Column(updatable = false,nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id",nullable = false))
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Set<Role> roles = new HashSet<>();
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "users")
    private UploadFile uploadFile;


    public Users(Users user){
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
        this.createdAt = user.getCreatedAt();
        this.active = user.active;
        this.roles = user.roles;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.number = user.number;
        this.address = user.address;
        this.city = user.city;

    }

    public Users(@NotBlank String username, @NotBlank String password) {
        this.username = username;
        this.password = password;
        this.active = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        Users user = (Users) o;
        return Objects.equals(username, user.username);
    }

}
