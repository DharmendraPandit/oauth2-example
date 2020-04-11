package com.example.auth;

import com.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repository.findByUsername(username);
        if (user == null)
            return null;
		/*List<GrantedAuthority> auth = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_" + user.getUserType());*/
		/*if (user.getUserType().equals(UserType.ADMIN.toString())) {
			auth = AuthorityUtils.createAuthorityList( "ROLE_ADMIN");
		} else if (user.getUserType().equals(UserType.TRAINER.toString())) {
			auth = AuthorityUtils.createAuthorityList("ROLE_TRAINER");
		} else if(user.getUserType().equals(UserType.TECHOCAMP_ADMIN.toString())) {
			auth = AuthorityUtils.createAuthorityList("ROLE_TECHOCAMP_ADMIN");
		}*/
        //user.setAuthorities(auth);

        /*User user1 = new User();
        user1.setPassword("123456");
        user1.setUsername("dharmendra.pandit@techolution.com");
        user1.setFirstName("Dharmendra");
        user1.setLastName("Pandit");
        user1.setUserType("ROLE_ADMIN");
        createuser(user1);*/
        return user;
    }


    public User createuser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEmail(user.getUsername());
        return repository.save(user);
    }

    public User createUser(User user) {
        if (repository.findByUsername(user.getUsername()) == null) {
            user.setId(UUID.randomUUID().toString());
            return repository.save(user);
        } else
            throw new UserException(HttpStatus.BAD_REQUEST.value(), UserErrorMessages.DUPLICATE_USER.name());
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }


    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public Optional<User> getUserByUserId(String id) {
        return repository.findById(id);
    }


    public void updateUser(User currentUser) {
        createUser(currentUser);
    }
}
