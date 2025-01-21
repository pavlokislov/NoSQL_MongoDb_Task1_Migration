package ua.epam.mishchenko.ticketbooking.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import ua.epam.mishchenko.ticketbooking.dto.UserDto;
import ua.epam.mishchenko.ticketbooking.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

//    @Autowired
//    private UserServiceImpl userService;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @Test
//    public void getUserByIdWithExistsIdShouldBeOk() {
//        UserDto expectedUser = new UserDto(3L, "Max", "max@gmail.com");
//
//        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expectedUser));
//
//        UserDto actualUser = userService.getUserById(3L);
//
//        assertEquals(expectedUser, actualUser);
//    }
//
//    @Test
//    public void getUserByIdWithExceptionShouldReturnNull() {
//        when(userRepository.findById(anyLong())).thenThrow(RuntimeException.class);
//
//        UserDto actualUser = userService.getUserById(10L);
//
//        assertNull(actualUser);
//    }
//
//    @Test
//    public void getUserByIdWithNotExistIdShouldReturnNull() {
//        when(userRepository.findById(anyLong())).thenReturn(null);
//
//        UserDto actualUser = userService.getUserById(10L);
//
//        assertNull(actualUser);
//    }
//
//    @Test
//    public void getUserByEmailWithExistsEmailShouldBeOk() {
//        UserDto expectedUser = new UserDto(3L, "Max", "max@gmail.com");
//
//        when(userRepository.getByEmail(anyString())).thenReturn(Optional.of(expectedUser));
//
//        UserDto actualUser = userService.getUserByEmail("max@gmail.com");
//
//        assertEquals(expectedUser, actualUser);
//    }
//
//    @Test
//    public void getUserByEmailWithNotExistEmailShouldBeOk() {
//        when(userRepository.getByEmail(anyString())).thenReturn(null);
//
//        UserDto actualUser = userService.getUserByEmail("max@gmail.com");
//
//        assertNull(actualUser);
//    }
//
//    @Test
//    public void getUserByEmailWithExceptionShouldReturnNull() {
//        when(userRepository.getByEmail(anyString())).thenThrow(RuntimeException.class);
//
//        UserDto actualUser = userService.getUserByEmail("notexists@gmail.com");
//
//        assertNull(actualUser);
//    }
//
//    @Test
//    public void getUserByEmailWithEmptyEmailShouldReturnNull() {
//        UserDto actualUserByEmail = userService.getUserByEmail("");
//
//        assertNull(actualUserByEmail);
//    }
//
//    @Test
//    public void getUserByNameWithExistsNameShouldBeOk() {
//        List<UserDto> content = Arrays.asList(
//                new UserDto(3L, "Max", "max@gmail.com"),
//                new UserDto(4L, "Max", "max123@gmail.com")
//        );
//        Page<UserDto> page = new PageImpl<>(content);
//
//        when(userRepository.getAllByName(any(Pageable.class), anyString())).thenReturn(page);
//
//        List<UserDto> actualUsersByName = userService.getUsersByName("Max", 2, 1);
//
//        assertTrue(content.containsAll(actualUsersByName));
//    }
//
//    @Test
//    public void getUserByNameWithExceptionShouldReturnEmptyList() {
//        when(userRepository.getAllByName(any(Pageable.class), anyString())).thenThrow(RuntimeException.class);
//
//        List<UserDto> actualListOfUsers = userService.getUsersByName("Not exists", 1, 1);
//
//        assertTrue(actualListOfUsers.isEmpty());
//    }
//
//    @Test
//    public void getUserByNameWithEmptyNameShouldReturnEmptyList() {
//        List<UserDto> actualUsersByName = userService.getUsersByName("", 1, 1);
//
//        assertTrue(actualUsersByName.isEmpty());
//    }
//
//    @Test
//    public void createUserWithUserShouldBeOk() {
//        UserDto expectedUser = new UserDto(1L, "Test User", "testuser@gmail.com");
//
//        when(userRepository.save(any())).thenReturn(expectedUser);
//
//        UserDto actualUser = userService.createUser(expectedUser);
//
//        assertEquals(expectedUser, actualUser);
//    }
//
//    @Test
//    public void createUserWithExceptionShouldReturnNull() {
//        when(userRepository.save(any())).thenThrow(RuntimeException.class);
//
//        UserDto actualUser = userService.createUser(new UserDto("Max", "max@gmail.com"));
//
//        assertNull(actualUser);
//    }
//
//    @Test
//    public void updateUserWithExistsUserShouldBeOk() {
//        UserDto expectedUser = new UserDto(1L, "Test User", "testuser@gmail.com");
//
//        when(userRepository.save(any())).thenReturn(expectedUser);
//
//        UserDto actualUser = userService.updateUser(expectedUser);
//
//        assertEquals(expectedUser, actualUser);
//    }
//
//    @Test
//    public void updateUserWithExceptionShouldThrowException() {
//        when(userRepository.save(any())).thenThrow(RuntimeException.class);
//
//        UserDto actualUser = userService.updateUser(new UserDto());
//
//        assertNull(actualUser);
//    }
//
//    @Test
//    public void deleteUserExistsUserShouldReturnTrue() {
//        boolean actualIsDeleted = userService.deleteUser(2);
//
//        assertTrue(actualIsDeleted);
//    }
//
//    @Test
//    public void deleteUserWhichNotExistsShouldReturnFalse() {
//        doThrow(new RuntimeException()).when(userRepository).deleteById(anyLong());
//
//        boolean isRemoved = userService.deleteUser(10L);
//
//        assertFalse(isRemoved);
//    }

}