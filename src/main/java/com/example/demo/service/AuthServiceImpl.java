package com.example.demo.service;

import com.example.demo.entity.Users;
import com.example.demo.enums.Status;
import com.example.demo.repository.UsersRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UsersRepository usersRepository;

    private final Logger log = Logger.getLogger(UserServiceImpl.class);

    @Override
    public Status authenticate(Users user) {
        List<Users> users = (List<Users>) usersRepository.findAll();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        log.info("Get all users : " + users );

        for (Users other : users) {
            boolean isPasswordMatch = encoder.matches(user.getPassword(), other.getPassword());
            log.info("isPasswordMatch : " + isPasswordMatch );

            if( other.equals(user) && isPasswordMatch ) {
                other.setActive(true);
                usersRepository.save(other);
                log.info("Message : User is "+ Status.SUCCESS +" logged ");
                return Status.SUCCESS;
            }
        }
        log.warn("Message : "+ Status.USER_DOES_NOT_EXISTS);
        return Status.USER_DOES_NOT_EXISTS;
    }

    @Override
    public Status logout(Users user) {
        List<Users> users = (List<Users>) usersRepository.findAll();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        log.info("Get all users : " + users );

        for (Users other : users) {
            boolean isPasswordMatch = encoder.matches(user.getPassword(), other.getPassword());
            log.info("isPasswordMatch : " + isPasswordMatch );

            if (other.equals(user) && isPasswordMatch) {
                other.setActive(false);
                usersRepository.save(other);
                log.info("Message : User is "+ Status.SUCCESS +" logged out ");

                return Status.SUCCESS;
            }
        }

        log.warn("Message : "+ Status.USER_DOES_NOT_EXISTS+" {LOGOUT} ");
        return Status.USER_DOES_NOT_EXISTS;
    }
}
