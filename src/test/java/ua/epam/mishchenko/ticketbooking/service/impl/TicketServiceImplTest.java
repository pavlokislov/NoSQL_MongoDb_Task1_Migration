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
import ua.epam.mishchenko.ticketbooking.model.*;
import ua.epam.mishchenko.ticketbooking.repository.EventRepository;
import ua.epam.mishchenko.ticketbooking.repository.TicketRepository;
import ua.epam.mishchenko.ticketbooking.repository.UserAccountRepository;
import ua.epam.mishchenko.ticketbooking.repository.UserRepository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static ua.epam.mishchenko.ticketbooking.utils.Constants.DATE_FORMATTER;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketServiceImplTest {

    @Autowired
    private TicketServiceImpl ticketService;

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private UserAccountRepository userAccountRepository;

//    @Test
//    public void bookTicketIfUserNotExistShouldReturnNull() {
//        when(userRepository.existsById(anyLong())).thenReturn(false);
//
//        TicketDto ticket = ticketService.bookTicket(1L, 1L, 1, Category.BAR);
//
//        assertNull(ticket);
//    }
//
//    @Test
//    public void bookTicketIfEventNotExistShouldReturnNull() {
//        when(userRepository.existsById(anyLong())).thenReturn(true);
//        when(eventRepository.existsById(anyLong())).thenReturn(false);
//
//        TicketDto ticket = ticketService.bookTicket(1L, 1L, 1, Category.BAR);
//
//        assertNull(ticket);
//    }
//
//    @Test
//    public void bookTicketIfTicketAlreadyBookedShouldReturnNull() {
//        when(userRepository.existsById(anyLong())).thenReturn(true);
//        when(eventRepository.existsById(anyLong())).thenReturn(true);
//        when(ticketRepository.existsByEventIdAndPlaceAndCategory(anyLong(), anyInt(), any(Category.class)))
//                .thenReturn(true);
//
//        TicketDto ticket = ticketService.bookTicket(1L, 1L, 1, Category.BAR);
//
//        assertNull(ticket);
//    }
//
//    @Test
//    public void bookTicketIfUserNotHaveAccountShouldReturnNull() {
//        when(userRepository.existsById(anyLong())).thenReturn(true);
//        when(eventRepository.existsById(anyLong())).thenReturn(true);
//        when(ticketRepository.existsByEventIdAndPlaceAndCategory(anyLong(), anyInt(), any(Category.class)))
//                .thenReturn(false);
//        when(userAccountRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        TicketDto ticket = ticketService.bookTicket(1L, 1L, 1, Category.BAR);
//
//        assertNull(ticket);
//    }
//
//    @Test
//    public void bookTicketIfUserNotHaveMoneyShouldReturnNull() {
//        when(userRepository.existsById(anyLong())).thenReturn(true);
//        when(eventRepository.existsById(anyLong())).thenReturn(true);
//        when(ticketRepository.existsByEventIdAndPlaceAndCategory(anyLong(), anyInt(), any(Category.class)))
//                .thenReturn(false);
//        when(userAccountRepository.findById(anyLong()))
//                .thenReturn(Optional.of(new UserAccount(new UserDto(), BigDecimal.ONE)));
//        when(eventRepository.findById(anyLong()))
//                .thenReturn(Optional.of(new EventDto("Title", new Date(System.currentTimeMillis()), BigDecimal.TEN)));
//
//        TicketDto ticket = ticketService.bookTicket(1L, 1L, 1, Category.BAR);
//
//        assertNull(ticket);
//    }
//
//    @Test
//    public void bookTicketIfEverythingFineShouldReturnBookedTicket() {
//        when(userRepository.existsById(anyLong())).thenReturn(true);
//        when(eventRepository.existsById(anyLong())).thenReturn(true);
//        when(ticketRepository.existsByEventIdAndPlaceAndCategory(anyLong(), anyInt(), any(Category.class)))
//                .thenReturn(false);
//        when(userAccountRepository.findById(anyLong()))
//                .thenReturn(Optional.of(new UserAccount(new UserDto(), BigDecimal.TEN)));
//        when(eventRepository.findById(anyLong()))
//                .thenReturn(Optional.of(new EventDto("Title", new Date(System.currentTimeMillis()), BigDecimal.ONE)));
//
//        TicketDto ticket = ticketService.bookTicket(1L, 1L, 1, Category.BAR);
//
//        assertNull(ticket);
//    }
//
//    @Test
//    public void getBookedTicketsWithNotNullUserAndProperPageSizeAndPageNumShouldBeOk() {
//        UserDto user = new UserDto(1L, "Alan", "alan@gmail.com");
//        List<TicketDto> content = Arrays.asList(
//                new TicketDto(1L, new UserDto(), new EventDto(), 10, Category.BAR),
//                new TicketDto(4L, new UserDto(), new EventDto(), 20, Category.BAR)
//        );
//        Page<TicketDto> page = new PageImpl<>(content);
//
//        when(ticketRepository.getAllByUserId(any(Pageable.class), anyLong())).thenReturn(page);
//
//        List<TicketDto> actualListOfTicketsByUser = ticketService.getBookedTickets(user, 2, 1);
//
//        assertEquals(content, actualListOfTicketsByUser);
//    }
//
//    @Test
//    public void getBookedTicketsByUserWithExceptionShouldReturnEmptyList() {
//        when(ticketRepository.getAllByUserId(any(Pageable.class), anyLong())).thenThrow(RuntimeException.class);
//
//        List<TicketDto> actualListOfTicketsByUser = ticketService.getBookedTickets(new UserDto(), 2, 1);
//
//        assertTrue(actualListOfTicketsByUser.isEmpty());
//    }
//
//    @Test
//    public void getBookedTicketsByUserWithNullUserShouldReturnEmptyList() {
//        List<TicketDto> actualTicketsByUser = ticketService.getBookedTickets((UserDto) null, 1, 2);
//
//        assertTrue(actualTicketsByUser.isEmpty());
//    }
//
//    @Test
//    public void getBookedTicketsWithNotNullEventAndProperPageSizeAndPageNumShouldBeOk() throws ParseException {
//        EventDto event = new EventDto(4L, "Fourth event", DATE_FORMATTER.parse("15-05-2022 21:00"), BigDecimal.ONE);
//        List<TicketDto> content = Arrays.asList(
//                new TicketDto(4L, new UserDto(), new EventDto(), 20, Category.BAR),
//                new TicketDto(2L, new UserDto(), new EventDto(), 10, Category.PREMIUM)
//        );
//        Page<TicketDto> page = new PageImpl<>(content);
//
//        when(ticketRepository.getAllByEventId(any(Pageable.class), anyLong())).thenReturn(page);
//
//        List<TicketDto> actualListOfTicketsByEvent = ticketService.getBookedTickets(event, 2, 1);
//
//        assertTrue(content.containsAll(actualListOfTicketsByEvent));
//    }
//
//    @Test
//    public void getBookedTicketsByEventWithExceptionShouldReturnEmptyList() {
//        when(ticketRepository.getAllByEventId(any(Pageable.class), anyLong())).thenThrow(RuntimeException.class);
//
//        List<TicketDto> actualListOfTicketsByEvent = ticketService.getBookedTickets(new EventDto(), 2, 1);
//
//        assertTrue(actualListOfTicketsByEvent.isEmpty());
//    }
//
//    @Test
//    public void getBookedTicketsWithNullEventShouldReturnEmptyList() {
//        List<TicketDto> actualTicketsByEvent = ticketService.getBookedTickets((EventDto) null, 1, 2);
//
//        assertTrue(actualTicketsByEvent.isEmpty());
//    }
//
//    @Test
//    public void cancelTicketExistsTicketShouldReturnTrue() {
//        boolean actualIsDeleted = ticketService.cancelTicket(6L);
//
//        assertTrue(actualIsDeleted);
//    }
//
//    @Test
//    public void cancelTicketWithExceptionShouldReturnFalse() {
//        doThrow(new RuntimeException()).when(ticketRepository).deleteById(anyLong());
//
//        boolean isRemoved = ticketService.cancelTicket(10L);
//
//        assertFalse(isRemoved);
//    }
}