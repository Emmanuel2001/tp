package powerbake.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import powerbake.address.commons.core.GuiSettings;
import powerbake.address.logic.commands.CommandResult;
import powerbake.address.logic.commands.exceptions.CommandException;
import powerbake.address.logic.parser.exceptions.ParseException;
import powerbake.address.model.ReadOnlyAddressBook;
import powerbake.address.model.order.Order;
import powerbake.address.model.pastry.Pastry;
import powerbake.address.model.person.Person;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see powerbake.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of pastries */
    ObservableList<Pastry> getFilteredPastryList();

    /** Returns an unmodifiable view of the filtered list of pastries */
    ObservableList<Order> getFilteredOrderList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
