package bezdelnik.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    void parse_CreateDeadlineTask() {
        Taskman initialTaskman = new Taskman();
        String validInput = "deadline Submit report /by 25/12/2024 1430";

        // Test valid deadline creation
        Pair<String, Taskman> result = Parser.parse(validInput, initialTaskman);
        Taskman updatedTaskman = result.second();

        assertEquals(1, updatedTaskman.size());
        assertTrue(result.first().contains("added"));

        // Test invalid deadline format
        String invalidInput = "deadline Submit report";
        Pair<String, Taskman> invalidResult = Parser.parse(invalidInput, initialTaskman);
        System.out.println(invalidResult.first());
        //assertTrue(invalidResult.first().contains("Invalid"));
        assertEquals(initialTaskman.size(), invalidResult.second().size());
    }

    @Test
    void parse_CreateEventTask() {
        Taskman initialTaskman = new Taskman();
        String validInput = "event Team meeting /from 25/12/2024 1430 /to 25/12/2024 1530";

        // Test valid event creation
        Pair<String, Taskman> result = Parser.parse(validInput, initialTaskman);
        Taskman updatedTaskman = result.second();

        assertEquals(1, updatedTaskman.size());
        assertTrue(result.first().contains("added"));

        // Test invalid event format (missing /to)
        String invalidInput = "event Team meeting /from 25/12/2024 1430";
        Pair<String, Taskman> invalidResult = Parser.parse(invalidInput, initialTaskman);
        //assertTrue(invalidResult.first().contains("Invalid"));
        assertEquals(initialTaskman.size(), invalidResult.second().size());
    }
}
