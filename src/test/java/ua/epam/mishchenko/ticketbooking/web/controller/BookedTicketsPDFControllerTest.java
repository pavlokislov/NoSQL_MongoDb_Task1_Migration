package ua.epam.mishchenko.ticketbooking.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import ua.epam.mishchenko.ticketbooking.facade.impl.BookingFacadeImpl;
import ua.epam.mishchenko.ticketbooking.dto.TicketDto;
import ua.epam.mishchenko.ticketbooking.dto.UserDto;
import ua.epam.mishchenko.ticketbooking.utils.PDFUtils;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookedTicketsPDFControllerTest {

    private BookedTicketsPDFController bookedTicketsPDFController;

    @Mock
    private BookingFacadeImpl bookingFacade;

    @Mock
    private PDFUtils pdfUtils;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        bookedTicketsPDFController = new BookedTicketsPDFController(bookingFacade, pdfUtils);
    }

    @Test
    public void getBookedTicketsByUserPDFWithNotExistingUserIdShouldThrowException() {
        when(bookingFacade.getUserById(anyLong())).thenReturn(null);

        RuntimeException actualException = assertThrows(RuntimeException.class,
                () -> bookedTicketsPDFController.getBookedTicketsByUserPDF(1L, 1, 1));

        verify(bookingFacade, times(1)).getUserById(anyLong());
        verify(bookingFacade, times(0)).getBookedTickets(any(UserDto.class), anyInt(), anyInt());
        verify(pdfUtils, times(0)).setTickets(any());
        verify(pdfUtils, times(0)).createPDFFileOfBookedTicketsByUser();

        assertEquals("Can not to find a user by id: 1", actualException.getMessage());
    }

    @Test
    public void getBookedTicketsByUserPDFExistingUserIdAndEmptyListShouldThrowException() {
        when(bookingFacade.getUserById(anyLong())).thenReturn(new UserDto());
        when(bookingFacade.getBookedTickets(any(UserDto.class), anyInt(), anyInt())).thenReturn(new ArrayList<>());

        RuntimeException actualException = assertThrows(RuntimeException.class,
                () -> bookedTicketsPDFController.getBookedTicketsByUserPDF(1L, 1, 1));

        verify(bookingFacade, times(1)).getUserById(anyLong());
        verify(bookingFacade, times(1)).getBookedTickets(any(UserDto.class), anyInt(), anyInt());
        verify(pdfUtils, times(0)).setTickets(any());
        verify(pdfUtils, times(0)).createPDFFileOfBookedTicketsByUser();

        assertEquals("Can not to find the tickets by user with id: 1", actualException.getMessage());
    }

    @Test
    public void getBookedTicketsByUserPDFExistingUserIdAndNotExistingDocumentShouldThrowException() {
        when(bookingFacade.getUserById(anyLong())).thenReturn(new UserDto());
        when(bookingFacade.getBookedTickets(any(UserDto.class), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(new TicketDto()));
        when(pdfUtils.getPDFDocument()).thenThrow(new RuntimeException());

        RuntimeException actualException = assertThrows(RuntimeException.class,
                () -> bookedTicketsPDFController.getBookedTicketsByUserPDF(1L, 1, 1));

        verify(bookingFacade, times(1)).getUserById(anyLong());
        verify(bookingFacade, times(1)).getBookedTickets(any(UserDto.class), anyInt(), anyInt());
        verify(pdfUtils, times(1)).setTickets(any());
        verify(pdfUtils, times(1)).createPDFFileOfBookedTicketsByUser();

        assertEquals(RuntimeException.class, actualException.getClass());
    }

    @Test
    public void getBookedTicketsByUserPDFExistingUserIdShouldReturnDocument() {
        when(bookingFacade.getUserById(anyLong())).thenReturn(new UserDto());
        when(bookingFacade.getBookedTickets(any(UserDto.class), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(new TicketDto()));
        when(pdfUtils.getPDFDocument()).thenReturn(any(InputStreamResource.class));

        ResponseEntity<Object> actualResponseEntity = bookedTicketsPDFController.getBookedTicketsByUserPDF(1L, 1, 1);

        verify(bookingFacade, times(1)).getUserById(anyLong());
        verify(bookingFacade, times(1)).getBookedTickets(any(UserDto.class), anyInt(), anyInt());
        verify(pdfUtils, times(1)).setTickets(any());
        verify(pdfUtils, times(1)).createPDFFileOfBookedTicketsByUser();
    }
}