package ua.epam.mishchenko.ticketbooking.facade.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.epam.mishchenko.ticketbooking.dto.EventDto;
import ua.epam.mishchenko.ticketbooking.dto.UserDto;
import ua.epam.mishchenko.ticketbooking.model.Category;
import ua.epam.mishchenko.ticketbooking.model.UserAccount;
import ua.epam.mishchenko.ticketbooking.repository.UserAccountRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@Sql(value = {"classpath:sql/clear-database.sql", "classpath:sql/insert-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:sql/clear-database.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookingFacadeImplTest {

//    @Autowired
//    private BookingFacadeImpl bookingFacade;
//
//    @Autowired
//    private UserAccountRepository userAccountRepository;
//
//    @Test
//    public void createUserThenCreateEventThenBookTicketForThisEventForUserAndThenCancelItShouldBeOk() {
//        UserDto user = new UserDto("Andrii", "andrii@gmail.com");
//        EventDto event = new EventDto("Integration Event", new Date(System.currentTimeMillis()), BigDecimal.valueOf(250));
//        int place = 10;
//
//        user = bookingFacade.createUser(user);
//
//        assertNotNull(bookingFacade.getUserById(user.getId()));
//
//        event = bookingFacade.createEvent(event);
//
//        assertNotNull(bookingFacade.getEventById(event.getId()));
//
//        UserAccount userAccount = bookingFacade.refillUserAccount(user.getId(), BigDecimal.valueOf(500));
//
//        assertEquals(BigDecimal.valueOf(500), userAccount.getMoney());
//
//        TicketDto ticket = bookingFacade.bookTicket(user.getId(), event.getId(), place, Category.STANDARD);
//
//        assertEquals(250, userAccountRepository.findById(user.getId()).get().getMoney().intValue());
//
//        List<TicketDto> bookedTicketsByUserBeforeCanceling = bookingFacade.getBookedTickets(user, 1, 1);
//        List<TicketDto> bookedTicketsByEventBeforeCanceling = bookingFacade.getBookedTickets(event, 1, 1);
//
//        assertTrue(bookedTicketsByUserBeforeCanceling.contains(ticket));
//        assertTrue(bookedTicketsByEventBeforeCanceling.contains(ticket));
//
//        bookingFacade.cancelTicket(ticket.getId());
//
//        List<TicketDto> bookedTicketsByUserAfterCanceling = bookingFacade.getBookedTickets(user, 1, 1);
//        List<TicketDto> bookedTicketsByEventAfterCanceling = bookingFacade.getBookedTickets(event, 1, 1);
//
//        assertTrue(bookedTicketsByUserAfterCanceling.isEmpty());
//        assertTrue(bookedTicketsByEventAfterCanceling.isEmpty());
//    }
//
//    @Test
//    public void refillUserAccountAndBookTicketWithNotExistingUserAccountShouldBeOk() {
//        long userId = 5;
//        long eventId = 1;
//        int place = 5;
//        Category category = Category.BAR;
//        BigDecimal money = BigDecimal.valueOf(5000);
//
//        UserAccount userAccount = bookingFacade.refillUserAccount(userId, money);
//
//        assertEquals(Long.valueOf(userId), userAccount.getUser().getId());
//        assertEquals(money, userAccount.getMoney());
//
//        TicketDto ticket = bookingFacade.bookTicket(userId, eventId, place, category);
//
//        assertNotNull(ticket);
//        assertEquals(Long.valueOf(userId), ticket.getUser().getId());
//
//        UserDto userById = bookingFacade.getUserById(userId);
//
//        assertEquals(userAccount.getMoney().subtract(ticket.getEvent().getTicketPrice()),
//                userById.getUserAccount().getMoney());
//    }

}