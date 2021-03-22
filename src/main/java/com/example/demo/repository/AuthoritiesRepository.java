package com.example.demo.repository;

import com.example.demo.entity.Authorities;
import com.example.demo.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities,Long> {
    Authorities findByRole(ERole role);
}
