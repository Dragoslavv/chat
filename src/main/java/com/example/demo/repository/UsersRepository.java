package com.example.demo.repository;

import com.example.demo.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends CrudRepository<Users,Long> {
//    Optional<Users> findByUsername(String username);

    Users findByUsername(String username);

    Boolean existsByUsername(String username);

}
