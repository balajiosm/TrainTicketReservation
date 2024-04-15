package com.ticket.reserve.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ticket.reserve.entity.User;
import com.ticket.reserve.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    
    @BeforeEach
    public void setup(){
    	user = new User();
    	user.setUserId(10L);
        user.setFirstName("Balaji");
        user.setLastName("OSM");
        user.setEmailAddress("balajiosm@gmail.com");
    }

    // JUnit test for saveUser method
    @DisplayName("JUnit test for saveUser method")
    @Test
    public void givenUserObject_whenSaveUser_thenReturnUserObject(){
    	//given
        when(userRepository.save(user)).thenReturn(user);

        System.out.println(userRepository);
        System.out.println(userService);

        // when -  action or the behavior that we are going test
        User savedUser = userService.saveUser(user);

        System.out.println(savedUser);
        // then - verify the output
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getFirstName()).isNotEmpty();
    }
}
