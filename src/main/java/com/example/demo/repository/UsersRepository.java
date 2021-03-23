package com.example.demo.repository;

import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<Users,Long> {
//    Optional<Users> findByUsername(String username);

    Users findByUsername(String username);

    Boolean existsByUsername(String username);

}
