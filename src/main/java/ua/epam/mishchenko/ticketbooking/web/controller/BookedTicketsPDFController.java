package ua.epam.mishchenko.ticketbooking.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.epam.mishchenko.ticketbooking.dto.TicketDto;
import ua.epam.mishchenko.ticketbooking.dto.UserDto;
import ua.epam.mishchenko.ticketbooking.facade.impl.BookingFacadeImpl;
import ua.epam.mishchenko.ticketbooking.utils.PDFUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

/**
 * The type Booked tickets pdf controller.
 */
@RestController
@RequestMapping(value = "/tickets/user", consumes = APPLICATION_PDF_VALUE, produces = APPLICATION_PDF_VALUE)
public class BookedTicketsPDFController {

    /**
     * The constant log.
     */
    private static final Logger log = LoggerFactory.getLogger(BookedTicketsPDFController.class);

    /**
     * The Booking facade.
     */
    private final BookingFacadeImpl bookingFacade;

    /**
     * The Pdf utils.
     */
    private final PDFUtils pdfUtils;

    /**
     * Instantiates a new Booked tickets pdf controller.
     *
     * @param bookingFacade the booking facade
     * @param pdfUtils      the pdf utils
     */
    public BookedTicketsPDFController(BookingFacadeImpl bookingFacade, PDFUtils pdfUtils) {
        this.bookingFacade = bookingFacade;
        this.pdfUtils = pdfUtils;
    }

    /**
     * Gets booked tickets by user pdf.
     *
     * @param userId   the user id
     * @param pageSize the page size
     * @param pageNum  the page num
     * @return the booked tickets by user pdf
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getBookedTicketsByUserPDF(@PathVariable long userId,
                                                            @RequestParam int pageSize,
                                                            @RequestParam int pageNum) {
        log.info("Showing the tickets by user with id: {}", userId);

        UserDto userById = getUserById(userId);
        List<TicketDto> bookedTickets = getBookedTickets(userId, pageSize, pageNum, userById);

        log.info("The tickets successfully found");

        return createResponseEntityWithPDFDocument(bookedTickets);
    }

    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     */
    private UserDto getUserById(long userId) {
        UserDto userById = bookingFacade.getUserById(userId);
        if (isNull(userById)) {
            log.info("Can not to find a user by id: {}", userId);
            throw new RuntimeException("Can not to find a user by id: " + userId);
        }
        return userById;
    }

    /**
     * Gets booked tickets.
     *
     * @param userId   the user id
     * @param pageSize the page size
     * @param pageNum  the page num
     * @param userById the user by id
     * @return the booked tickets
     */
    private List<TicketDto> getBookedTickets(long userId, int pageSize, int pageNum, UserDto userById) {
        List<TicketDto> bookedTickets = bookingFacade.getBookedTickets(userById, pageSize, pageNum);
        if (bookedTickets.isEmpty()) {
            log.info("Can not to find the tickets by user with id: {}", userId);
            throw new RuntimeException("Can not to find the tickets by user with id: " + userId);
        }
        return bookedTickets;
    }

    /**
     * Create response entity with pdf document response entity.
     *
     * @param bookedTickets the booked tickets
     * @return the response entity
     */
    private ResponseEntity<Object> createResponseEntityWithPDFDocument(List<TicketDto> bookedTickets) {
        createPDFDocument(bookedTickets);
        InputStreamResource pdfDocument = pdfUtils.getPDFDocument();
        pdfUtils.deletePDFDocument();
        return new ResponseEntity<>(pdfDocument, HttpStatus.OK);
    }

    /**
     * Create pdf document.
     *
     * @param bookedTickets the booked tickets
     */
    private void createPDFDocument(List<TicketDto> bookedTickets) {
        Path path = Paths.get("Booked Tickets.pdf");
        pdfUtils.setTickets(bookedTickets);
        pdfUtils.setPath(path);
        pdfUtils.createPDFFileOfBookedTicketsByUser();
    }

    /**
     * Is null boolean.
     *
     * @param object the object
     * @return the boolean
     */
    private boolean isNull(Object object) {
        return object == null;
    }
}
