package com.example.demo.repository;

import com.example.demo.entity.Authorities;
import com.example.demo.enums.ERole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends CrudRepository<Authorities,Long> {
    Authorities findByRole(ERole role);
}
