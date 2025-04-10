package powerbake.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));

        // different showClient value -> returns True (since showClient is static)
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, false, false, false)));

        // different showPastry value -> returns True (since showPastry is static)
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, false, true, false)));

        // different showOrder value -> returns True (since showOrder is static)
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, false, false, true)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());

        commandResult = new CommandResult("feedback", false, false, true, false, false);

        // different showClient value -> returns different hashcode
        // Hashcode is different because hashcode is calculated before static variable is set
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, false, false, false, false)
                .hashCode());

        commandResult = new CommandResult("feedback", false, false, true, false, false);

        // different showPastry value -> returns different hashcode
        // Hashcode is different because hashcode is calculated before static variable is set
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, false, false, true, false)
                .hashCode());

        commandResult = new CommandResult("feedback", false, false, true, false, false);

        // different showOrder value -> returns different hashcode
        // Hashcode is different because hashcode is calculated before static variable is set
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, false, false, false, true)
                .hashCode());

        commandResult = new CommandResult("feedback", false, false, true, false, false);

        // Same showOrder value -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback", false, false, true, false, false, 0)
                .hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit()
                + ", showClient=" + commandResult.isShowClient()
                + ", showPastry=" + commandResult.isShowPastry()
                + ", showOrder=" + commandResult.isShowOrder()
                + ", orderIndex=" + commandResult.getOrderIndex() + "}";
        assertEquals(expected, commandResult.toString());
    }
}
