package ua.epam.mishchenko.ticketbooking.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.mishchenko.ticketbooking.facade.impl.BookingFacadeImpl;
import ua.epam.mishchenko.ticketbooking.dto.UserDto;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UsersControllerTest {

    private UsersController usersController;

    @Mock
    private BookingFacadeImpl bookingFacade;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        usersController = new UsersController(bookingFacade);
    }

//    @Test
//    public void showUserByIdWithExistingUserIdShouldReturnModelAndViewWithUser() {
//        when(bookingFacade.getUserById(anyLong())).thenReturn(new UserDto());
//
//        ModelAndView actualModelAndView = usersController.showUserById(1L);
//
//        verify(bookingFacade, times(1)).getUserById(anyLong());
//
//        assertEquals("user", actualModelAndView.getViewName());
//        assertTrue(actualModelAndView.getModelMap().containsAttribute("user"));
//        assertFalse(actualModelAndView.getModelMap().containsAttribute("message"));
//    }
//
//    @Test
//    public void showUserByIdWithNotExistingUserIdShouldReturnModelAndViewWithMessage() {
//        when(bookingFacade.getUserById(anyLong())).thenReturn(null);
//
//        ModelAndView actualModelAndView = usersController.showUserById(1L);
//
//        verify(bookingFacade, times(1)).getUserById(anyLong());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("user", actualModelAndView.getViewName());
//        assertFalse(actualModelAndView.getModelMap().containsAttribute("user"));
//        assertTrue(actualModelAndView.getModelMap().containsAttribute("message"));
//        assertEquals("Can not to find user by id: 1", actualModelMap.getAttribute("message"));
//    }
//
//    @Test
//    public void showUsersByNameWithExistingUsersNameShouldReturnModelAndViewWithListOfUsers() {
//        when(bookingFacade.getUsersByName(anyString(), anyInt(), anyInt()))
//                .thenReturn(Collections.singletonList(new UserDto()));
//
//        ModelAndView actualModelAndView = usersController.showUsersByName("Not Existing Name", 1, 1);
//
//        verify(bookingFacade, times(1)).getUsersByName(anyString(), anyInt(), anyInt());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("users", actualModelAndView.getViewName());
//        assertTrue(actualModelMap.containsAttribute("users"));
//        assertFalse(actualModelMap.containsAttribute("message"));
//    }
//
//    @Test
//    public void showUsersByNameWithNotExistingUsersNameShouldReturnModelAndViewWithMessage() {
//        when(bookingFacade.getUsersByName(anyString(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
//
//        ModelAndView actualModelAndView = usersController.showUsersByName("Not Existing Name", 1, 1);
//
//        verify(bookingFacade, times(1)).getUsersByName(anyString(), anyInt(), anyInt());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("users", actualModelAndView.getViewName());
//        assertFalse(actualModelMap.containsAttribute("users"));
//        assertTrue(actualModelMap.containsAttribute("message"));
//        assertEquals("Can not to find users by name: Not Existing Name", actualModelMap.getAttribute("message"));
//    }
//
//    @Test
//    public void showUserByEmailWithExistingUserEmailShouldReturnModelAndViewWithListOfUsers() {
//        when(bookingFacade.getUserByEmail(anyString())).thenReturn(new UserDto());
//
//        ModelAndView actualModelAndView = usersController.showUserByEmail("Not Existing Email");
//
//        verify(bookingFacade, times(1)).getUserByEmail(anyString());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("user", actualModelAndView.getViewName());
//        assertTrue(actualModelMap.containsAttribute("user"));
//        assertFalse(actualModelMap.containsAttribute("message"));
//    }
//
//    @Test
//    public void showUserByEmailWithNotExistingUserEmailShouldReturnModelAndViewWithMessage() {
//        when(bookingFacade.getUserByEmail(anyString())).thenReturn(null);
//
//        ModelAndView actualModelAndView = usersController.showUserByEmail("Not Existing Name");
//
//        verify(bookingFacade, times(1)).getUserByEmail(anyString());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("user", actualModelAndView.getViewName());
//        assertFalse(actualModelMap.containsAttribute("users"));
//        assertTrue(actualModelMap.containsAttribute("message"));
//        assertEquals("Can not to find user by email: Not Existing Name", actualModelMap.getAttribute("message"));
//    }
//
//    @Test
//    public void createUserWithCorrectParametersShouldReturnModelAndViewWithUser() {
//        when(bookingFacade.createUser(any())).thenReturn(new UserDto());
//
//        ModelAndView actualModelAndView = usersController.createUser("Test Name", "test@mail.com");
//
//        verify(bookingFacade, times(1)).createUser(any());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("user", actualModelAndView.getViewName());
//        assertTrue(actualModelMap.containsAttribute("user"));
//        assertFalse(actualModelMap.containsAttribute("message"));
//    }
//
//    @Test
//    public void createUserWithWrongParametersShouldReturnModelAndViewWithMessage() {
//        when(bookingFacade.createUser(any())).thenReturn(null);
//
//        ModelAndView actualModelAndView = usersController.createUser("Test Name", "test@mail.com");
//
//        verify(bookingFacade, times(1)).createUser(any());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("user", actualModelAndView.getViewName());
//        assertFalse(actualModelMap.containsAttribute("user"));
//        assertTrue(actualModelMap.containsAttribute("message"));
//        assertEquals("Can not to create user with name - Test Name and email - test@mail.com", actualModelMap.getAttribute("message"));
//    }
//
//    @Test
//    public void updateUserWithCorrectParametersShouldReturnModelAndViewWithUser() {
//        when(bookingFacade.updateUser(any())).thenReturn(new UserDto());
//
//        ModelAndView actualModelAndView = usersController.updateUser(1L, "Test Name", "test@mail.com");
//
//        verify(bookingFacade, times(1)).updateUser(any());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("user", actualModelAndView.getViewName());
//        assertTrue(actualModelMap.containsAttribute("user"));
//        assertFalse(actualModelMap.containsAttribute("message"));
//    }
//
//    @Test
//    public void updateUserWithWrongParametersShouldReturnModelAndViewWithMessage() {
//        when(bookingFacade.updateUser(any())).thenReturn(null);
//
//        ModelAndView actualModelAndView = usersController.updateUser(1L, "Test Name", "test@mail.com");
//
//        verify(bookingFacade, times(1)).updateUser(any());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("user", actualModelAndView.getViewName());
//        assertFalse(actualModelMap.containsAttribute("user"));
//        assertTrue(actualModelMap.containsAttribute("message"));
//        assertEquals("Can not to update user with id: 1", actualModelMap.getAttribute("message"));
//    }
//
//    @Test
//    public void deleteUserWithExistingUserIdShouldReturnModelAndViewWithPositiveMessage() {
//        when(bookingFacade.deleteUser(anyLong())).thenReturn(true);
//
//        ModelAndView actualModelAndView = usersController.deleteUser(1L);
//
//        verify(bookingFacade, times(1)).deleteUser(anyLong());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("user", actualModelAndView.getViewName());
//        assertTrue(actualModelMap.containsAttribute("message"));
//        assertEquals("The user with id: 1 successfully removed", actualModelMap.getAttribute("message"));
//    }
//
//    @Test
//    public void deleteUserWithNotExistingUserIdShouldReturnModelAndViewWithNegativeMessage() {
//        when(bookingFacade.deleteUser(anyLong())).thenReturn(false);
//
//        ModelAndView actualModelAndView = usersController.deleteUser(1L);
//
//        verify(bookingFacade, times(1)).deleteUser(anyLong());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("user", actualModelAndView.getViewName());
//        assertTrue(actualModelMap.containsAttribute("message"));
//        assertEquals("The user with id: 1 not removed", actualModelMap.getAttribute("message"));
//    }
}