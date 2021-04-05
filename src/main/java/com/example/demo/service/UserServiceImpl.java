package com.example.demo.service;

import com.example.demo.entity.Authorities;
import com.example.demo.entity.Users;
import com.example.demo.enums.ERole;
import com.example.demo.enums.Status;
import com.example.demo.errormsg.EntityNotFoundException;
import com.example.demo.errormsg.ErrorMessage;
import com.example.demo.repository.AuthoritiesRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.security.jwt.JwtTokenUtil;
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final Logger log = Logger.getLogger(UserServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        Users user = usersRepository.findByUsername(username);
        List<Users> users = (List<Users>) usersRepository.findAll();

        for (Users other : users) {

            if( other.getUsername().equals(username)) {
                other.setActive(true);
                usersRepository.save(other);
                log.info("Message : User is "+ Status.SUCCESS +" logged ");
            }
        }

        if (user == null) {
            log.warn("User not found with username:( " + username + ")");

            throw new EntityNotFoundException(Users.class, "username", username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    @Override
    public Status saveUser(Users newUsers) {

        List<Users> users = (List<Users>) usersRepository.findAll();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        newUsers.setPassword(bCryptPasswordEncoder.encode(newUsers.getPassword()));
        Set<Authorities> roles = new HashSet<>();
        Set<Authorities> strRoles = newUsers.getAuthorities();

        Set<ERole> eRoleReq = new HashSet<>();
        Set<ERole> eRoleDb = new HashSet<>();

        strRoles.forEach( res -> {
            eRoleReq.add(res.getRole());
        });

        for (Users user : users) {
            if(user.equals(newUsers)){
                log.warn("ErrorMessage:" + ErrorMessage.USER_ALREADY_EXISTS + "(" + user.getUsername() + ")");
                return Status.USER_ALREADY_EXISTS;
            }
        }

        newUsers.getAuthorities().forEach( result -> {
            Authorities getRoles = authoritiesRepository.findByRole(result.getRole());

            if(getRoles != null) {
                roles.add(getRoles);
                eRoleDb.add(getRoles.getRole());
            }
        });

        if(!roles.isEmpty()) {
            newUsers.setAuthorities(roles);
        }

        usersRepository.save(newUsers);
        log.info("Message : User is "+ Status.SUCCESS + " created");
        return Status.SUCCESS;
    }

    @Override
    public Status update(Users existUser) {
        List<Users> users = (List<Users>) usersRepository.findAll();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        existUser.setPassword(bCryptPasswordEncoder.encode(existUser.getPassword()));

        for(Users user : users) {
            if (Objects.equals(user.getId(), existUser.getId()) && !Objects.equals(user.getUsername(),existUser.getUsername()) ) {
                usersRepository.save(existUser);
                log.info("Message : User is "+ Status.SUCCESS + "updated");
                return Status.SUCCESS;
            }
        }

        log.warn("ErrorMessage:" + Status.FAILURE + "(" + existUser.getUsername() + ")");

        return Status.FAILURE;
    }

    @Override
    public Status deleteUser(Long userId) {
        if(usersRepository.existsById(userId)) {
            log.info(userId);
            usersRepository.deleteById(userId);
            log.info("User is " + Status.SUCCESS + " deleted (" + userId + ")");
            return Status.SUCCESS;
        }

        log.warn("ErrorMessage: " + Status.FAILURE + "(" + userId + "), NOT DELETE");
        return Status.FAILURE;
    }

    @Override
    public List<Users> getAllUsers(){
        return (List<Users>) usersRepository.findAll();
    }

    @Override
    public Optional<Users> getUserById(Long userId) {
        Optional<Users> optionalUsers = usersRepository.findById(userId);

        if (optionalUsers.isEmpty()) {
             throw new EntityNotFoundException(Users.class, "id", userId.toString());
        }
        return optionalUsers;
    }

}
