//package ua.epam.mishchenko.ticketbooking.web.controller;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.servlet.ModelAndView;
//import ua.epam.mishchenko.ticketbooking.facade.impl.BookingFacadeImpl;
//import ua.epam.mishchenko.ticketbooking.model.Category;
//import ua.epam.mishchenko.ticketbooking.dto.EventDto;
//import ua.epam.mishchenko.ticketbooking.dto.TicketDto;
//import ua.epam.mishchenko.ticketbooking.dto.UserDto;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class TicketsControllerTest {
//
//    private TicketsController ticketsController;
//
//    @Mock
//    private BookingFacadeImpl bookingFacade;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        ticketsController = new TicketsController(bookingFacade);
//    }
//
//    @Test
//    public void bookTicketWithCorrectParametersShouldReturnModelAndViewWithBookedTicket() {
//        TicketDto ticket = new TicketDto();
//
//        when(bookingFacade.bookTicket(anyLong(), anyLong(), anyInt(), any())).thenReturn(ticket);
//
//        ModelAndView actualModelAndView = ticketsController.bookTicket(1L, 1L, 1, Category.BAR);
//
//        verify(bookingFacade, times(1)).bookTicket(anyLong(), anyLong(), anyInt(), any());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("ticket", actualModelAndView.getViewName());
//        assertTrue(actualModelMap.containsAttribute("ticket"));
//        assertFalse(actualModelMap.containsAttribute("message"));
//    }
//
//    @Test
//    public void bookTicketWithCorrectParametersShouldReturnModelAndViewWithMessage() {
//        when(bookingFacade.bookTicket(anyLong(), anyLong(), anyInt(), any())).thenReturn(null);
//
//        ModelAndView actualModelAndView = ticketsController.bookTicket(1L, 1L, 1, Category.BAR);
//
//        verify(bookingFacade, times(1)).bookTicket(anyLong(), anyLong(), anyInt(), any());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("ticket", actualModelAndView.getViewName());
//        assertFalse(actualModelMap.containsAttribute("ticket"));
//        assertTrue(actualModelMap.containsAttribute("message"));
//        assertEquals("Can not to book a ticket", actualModelMap.getAttribute("message"));
//    }
//
//    @Test
//    public void showTicketsByUserWithNotExistingUserIdShouldReturnModelAndViewWithMessage() {
//        when(bookingFacade.getUserById(anyLong())).thenReturn(null);
//
//        ModelAndView actualModelAndView = ticketsController.showTicketsByUser(1L, 1, 1);
//
//        verify(bookingFacade, times(1)).getUserById(anyLong());
//        verify(bookingFacade, times(0)).getBookedTickets(any(UserDto.class), anyInt(), anyInt());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("tickets", actualModelAndView.getViewName());
//        assertFalse(actualModelMap.containsAttribute("tickets"));
//        assertTrue(actualModelMap.containsAttribute("message"));
//        assertEquals("Can not to find a user by id: 1", actualModelMap.getAttribute("message"));
//    }
//
//    @Test
//    public void showTicketsByUserWithExistingUserIdShouldReturnModelAndViewWithMessage() {
//        when(bookingFacade.getUserById(anyLong())).thenReturn(new UserDto());
//        when(bookingFacade.getBookedTickets(any(UserDto.class), anyInt(), anyInt())).thenReturn(new ArrayList<>());
//
//        ModelAndView actualModelAndView = ticketsController.showTicketsByUser(1L, 1, 1);
//
//        verify(bookingFacade, times(1)).getUserById(anyLong());
//        verify(bookingFacade, times(1)).getBookedTickets(any(UserDto.class), anyInt(), anyInt());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("tickets", actualModelAndView.getViewName());
//        assertFalse(actualModelMap.containsAttribute("tickets"));
//        assertTrue(actualModelMap.containsAttribute("message"));
//        assertEquals("Can not to find the tickets by user with id: 1", actualModelMap.getAttribute("message"));
//    }
//
//    @Test
//    public void showTicketsByUserWithExistingUserIdShouldReturnModelAndViewWithListOfBookedTickets() {
//        when(bookingFacade.getUserById(anyLong())).thenReturn(new UserDto());
//        when(bookingFacade.getBookedTickets(any(UserDto.class), anyInt(), anyInt()))
//                .thenReturn(Collections.singletonList(new TicketDto()));
//
//        ModelAndView actualModelAndView = ticketsController.showTicketsByUser(1L, 1, 1);
//
//        verify(bookingFacade, times(1)).getUserById(anyLong());
//        verify(bookingFacade, times(1)).getBookedTickets(any(UserDto.class), anyInt(), anyInt());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("tickets", actualModelAndView.getViewName());
//        assertTrue(actualModelMap.containsAttribute("tickets"));
//        assertFalse(actualModelMap.containsAttribute("message"));
//    }
//
//    @Test
//    public void showTicketsByEventWithNotExistingEventIdShouldReturnModelAndViewWithMessage() {
//        when(bookingFacade.getEventById(anyLong())).thenReturn(null);
//
//        ModelAndView actualModelAndView = ticketsController.showTicketsByEvent(1L, 1, 1);
//
//        verify(bookingFacade, times(1)).getEventById(anyLong());
//        verify(bookingFacade, times(0)).getBookedTickets(any(EventDto.class), anyInt(), anyInt());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("tickets", actualModelAndView.getViewName());
//        assertFalse(actualModelMap.containsAttribute("tickets"));
//        assertTrue(actualModelMap.containsAttribute("message"));
//        assertEquals("Can not to find an event by id: 1", actualModelMap.getAttribute("message"));
//    }
//
//    @Test
//    public void showTicketsByEventWithExistingEventIdShouldReturnModelAndViewWithMessage() {
//        when(bookingFacade.getEventById(anyLong())).thenReturn(new EventDto());
//        when(bookingFacade.getBookedTickets(any(EventDto.class), anyInt(), anyInt())).thenReturn(new ArrayList<>());
//
//        ModelAndView actualModelAndView = ticketsController.showTicketsByEvent(1L, 1, 1);
//
//        verify(bookingFacade, times(1)).getEventById(anyLong());
//        verify(bookingFacade, times(1)).getBookedTickets(any(EventDto.class), anyInt(), anyInt());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("tickets", actualModelAndView.getViewName());
//        assertFalse(actualModelMap.containsAttribute("tickets"));
//        assertTrue(actualModelMap.containsAttribute("message"));
//        assertEquals("Can not to find the tickets by event with id: 1", actualModelMap.getAttribute("message"));
//    }
//
//    @Test
//    public void showTicketsByEventWithExistingEventIdShouldReturnModelAndViewWithListOfBookedTickets() {
//        when(bookingFacade.getEventById(anyLong())).thenReturn(new EventDto());
//        when(bookingFacade.getBookedTickets(any(EventDto.class), anyInt(), anyInt()))
//                .thenReturn(Collections.singletonList(new TicketDto()));
//
//        ModelAndView actualModelAndView = ticketsController.showTicketsByEvent(1L, 1, 1);
//
//        verify(bookingFacade, times(1)).getEventById(anyLong());
//        verify(bookingFacade, times(1)).getBookedTickets(any(EventDto.class), anyInt(), anyInt());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("tickets", actualModelAndView.getViewName());
//        assertTrue(actualModelMap.containsAttribute("tickets"));
//        assertFalse(actualModelMap.containsAttribute("message"));
//    }
//
//    @Test
//    public void cancelTicketWithExistingIdShouldReturnModelAndViewWithPositiveMessage() {
//        when(bookingFacade.cancelTicket(anyLong())).thenReturn(true);
//
//        ModelAndView actualModelAndView = ticketsController.cancelTicket(1L);
//
//        verify(bookingFacade, times(1)).cancelTicket(anyLong());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("ticket", actualModelAndView.getViewName());
//        assertTrue(actualModelMap.containsAttribute("message"));
//        assertEquals("The ticket with id: 1 successfully canceled", actualModelMap.getAttribute("message"));
//    }
//
//    @Test
//    public void cancelTicketWithNotExistingIdShouldReturnModelAndViewWithNegativeMessage() {
//        when(bookingFacade.cancelTicket(anyLong())).thenReturn(false);
//
//        ModelAndView actualModelAndView = ticketsController.cancelTicket(1L);
//
//        verify(bookingFacade, times(1)).cancelTicket(anyLong());
//
//        ModelMap actualModelMap = actualModelAndView.getModelMap();
//
//        assertEquals("ticket", actualModelAndView.getViewName());
//        assertTrue(actualModelMap.containsAttribute("message"));
//        assertEquals("The ticket with id: 1 not canceled", actualModelMap.getAttribute("message"));
//    }
//}