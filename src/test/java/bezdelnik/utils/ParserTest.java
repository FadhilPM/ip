package bezdelnik.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ParserTest {

    @Test
    void parse_CreateDeadlineTask() {
        Taskman initialTaskman = new Taskman();
        String validInput = "deadline Submit report /by 25/12/2024 1430";

        // Test valid deadline creation
        Command result = assertDoesNotThrow(() -> Parser.parse(validInput, initialTaskman));

        Pair<String, Taskman> execOutput = assertDoesNotThrow(() -> result.execute());

        Taskman updatedTaskman = execOutput.second();
        assertEquals(1, updatedTaskman.size());
        assertTrue(execOutput.first().contains("added"));

        // Test invalid deadline format
        String invalidInput = "deadline Submit report";
        BezdelnikException be = assertThrows(BezdelnikException.class, () -> Parser.parse(invalidInput, initialTaskman));
        assertTrue(be.getMessage().contains("deadline"));
    }

    @Test
    void parse_CreateEventTask() {
        Taskman initialTaskman = new Taskman();
        String validInput = "event Team meeting /from 25/12/2024 1430 /to 25/12/2024 1530";

        // Test valid event creation
        Command result = assertDoesNotThrow(() -> Parser.parse(validInput, initialTaskman));

        Pair<String, Taskman> execOutput = assertDoesNotThrow(() -> result.execute());

        Taskman updatedTaskman = execOutput.second();

        assertEquals(1, updatedTaskman.size());
        assertTrue(execOutput.first().contains("added"));

        // Test invalid event format (missing /to)
        String invalidInput = "event Team meeting /from 25/12/2024 1430";
        BezdelnikException be = assertThrows(BezdelnikException.class, () -> Parser.parse(invalidInput, initialTaskman));
        assertTrue(be.getMessage().contains("event"));
    }
}
