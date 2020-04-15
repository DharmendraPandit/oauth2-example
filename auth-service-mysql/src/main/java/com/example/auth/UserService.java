package com.example.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //User dbUser = createuser();
        User user = repository.findByUsername(username);
        if (user == null)
            return null;
        return user;
    }


    private User createuser() {
        User user = new User();
        user.setFirstName("Dharmendra");
        user.setLastName("Pandit");
        user.setEmail("dkpandit.08@gmail.com");
        user.setUsername("dkpandit.08@gmail.com");
        user.setPassword("123456");
        user.setUserType("ADMIN");
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return repository.save(user);
    }

   /* public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }


    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }*/
}
