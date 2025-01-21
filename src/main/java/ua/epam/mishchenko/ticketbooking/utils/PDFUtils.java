package ua.epam.mishchenko.ticketbooking.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;
import ua.epam.mishchenko.ticketbooking.dto.TicketDto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * The type Pdf utils.
 */
@Component
public class PDFUtils {

    /**
     * The constant log.
     */
    private static final Logger log = LoggerFactory.getLogger(PDFUtils.class);

    /**
     * The Tickets.
     */
    private List<TicketDto> tickets;

    /**
     * The Path.
     */
    private Path path;

    /**
     * Gets pdf document.
     *
     * @return the pdf document
     */
    public InputStreamResource getPDFDocument() {
        try {
            InputStream inputStream = Files.newInputStream(path);
            return new InputStreamResource(inputStream);
        } catch (IOException e) {
            log.warn("Can not to download pdf document of booked tickets", e);
            throw new RuntimeException("Can not to download pdf document of booked tickets", e);
        }
    }

    /**
     * Create pdf file of booked tickets by user.
     */
    public void createPDFFileOfBookedTicketsByUser() {
        log.info("Creating a pdf file of booked tickets by user");
        Document doc = new Document();
        try {
            Path path = Paths.get("Booked Tickets.pdf");
            PdfWriter writer = PdfWriter.getInstance(doc, createFile());
            doc.open();
            doc.add(createTableAndInsertDate());
            doc.close();
            writer.close();
            log.info("The pdf file of booked tickets by user successfully created");
        } catch (DocumentException e) {
            log.info("Can not to create a pdf file of booked tickets by user", e);
            throw new RuntimeException("Can not to create a pdf fle of booked tickets by user", e);
        }
    }

    /**
     * Create file output stream.
     *
     * @return the output stream
     */
    private OutputStream createFile() {
        log.info("Creating pdf file with path: {}", path);
        try {
            OutputStream outputStream = Files.newOutputStream(path);
            log.info("The pdf file with path: '{}' successfully created", path);
            return outputStream;
        } catch (IOException e) {
            log.info("Can not to create a pdf file with path: {}", path, e);
            throw new RuntimeException("Can not to create a pdf file with path: " + path, e);
        }
    }

    /**
     * Create table and insert date pdf p table.
     *
     * @return the pdf p table
     */
    private PdfPTable createTableAndInsertDate() {
        PdfPTable table = createTable();
        insertDataInTable(table);
        return table;
    }

    /**
     * Create table pdf p table.
     *
     * @return the pdf p table
     */
    private PdfPTable createTable() {
        PdfPTable table = new PdfPTable(5);
        createAndAddCells(table, "ID", "User ID", "Event ID", "Place", "Category");
        table.setHeaderRows(1);
        return table;
    }

    /**
     * Create and add cells.
     *
     * @param table  the table
     * @param values the values
     */
    private void createAndAddCells(PdfPTable table, String... values) {
        for (String value : values) {
            PdfPCell cell = new PdfPCell(new Phrase(value));
            table.addCell(cell);
        }
    }

    /**
     * Insert data in table.
     *
     * @param table the table
     */
    private void insertDataInTable(PdfPTable table) {
        for (TicketDto ticket : tickets) {
            createAndAddCells(table,
                    String.valueOf(ticket.getId()),
                    String.valueOf(ticket.getUser().getId()),
                    String.valueOf(ticket.getEvent().getId()),
                    String.valueOf(ticket.getPlace()),
                    String.valueOf(ticket.getCategory()));
        }
    }

    /**
     * Delete pdf document.
     */
    public void deletePDFDocument() {
        log.info("Removing pdf file with path: {}", path);
        try {
            Files.delete(path);
            log.info("The pdf file with path: '{}' successfully removed", path);
        } catch (IOException e) {
            log.info("Can not to remove a pdf file with path: {}", path, e);
            throw new RuntimeException("Can not to remove a pdf file with path: " + path, e);
        }
    }

    /**
     * Sets tickets.
     *
     * @param tickets the tickets
     */
    public void setTickets(List<TicketDto> tickets) {
        this.tickets = tickets;
    }

    /**
     * Sets path.
     *
     * @param path the path
     */
    public void setPath(Path path) {
        this.path = path;
    }
}
