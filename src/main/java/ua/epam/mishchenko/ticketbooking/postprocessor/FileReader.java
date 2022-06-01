package ua.epam.mishchenko.ticketbooking.postprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The type File reader.
 */
public class FileReader {

    /**
     * The constant log.
     */
    private static final Logger log = LoggerFactory.getLogger(FileReader.class);

    /**
     * The File.
     */
    private File file;

    /**
     * Read prepared data from file map.
     *
     * @return the map
     */
    public Map<String, String> readPreparedDataFromFile() {
        log.info("Trying to read prepared data from file: {}", file);

        String delimiter = "=";
        Map<String, String> map = new HashMap<>();
        try (Stream<String> lines = Files.lines(file.toPath())) {
            lines.filter(line -> line.contains(delimiter)).forEach(
                    line -> map.putIfAbsent(line.split(delimiter)[0], line.split(delimiter)[1])
            );
        } catch (IOException e) {
            log.warn("Can not to retrieve prepared data from file: {}", file);
            throw new RuntimeException("Can not to retrieve prepared data from file", e);
        }

        log.info("Data read successfully");
        return map;
    }

    /**
     * Sets file.
     *
     * @param file the file
     */
    public void setFile(File file) {
        this.file = file;
    }
}
