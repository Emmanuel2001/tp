package powerbake.address.logic.parser;

import static powerbake.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static powerbake.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static powerbake.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static powerbake.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static powerbake.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static powerbake.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static powerbake.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static powerbake.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static powerbake.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static powerbake.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static powerbake.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static powerbake.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static powerbake.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static powerbake.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static powerbake.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static powerbake.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static powerbake.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static powerbake.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static powerbake.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static powerbake.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static powerbake.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static powerbake.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static powerbake.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static powerbake.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static powerbake.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static powerbake.address.logic.parser.CliSyntax.PREFIX_CLIENT;
import static powerbake.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static powerbake.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static powerbake.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static powerbake.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static powerbake.address.testutil.TypicalPersons.AMY;
import static powerbake.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import powerbake.address.logic.Messages;
import powerbake.address.logic.commands.AddClientCommand;
import powerbake.address.logic.commands.AddCommand;
import powerbake.address.model.person.Address;
import powerbake.address.model.person.Email;
import powerbake.address.model.person.Name;
import powerbake.address.model.person.Person;
import powerbake.address.model.person.Phone;
import powerbake.address.model.tag.Tag;
import powerbake.address.testutil.PersonBuilder;

public class AddClientCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddClientCommand(expectedPerson));


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddClientCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_clientRepeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, validExpectedPersonString + NAME_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLIENT));

        // multiple phones
        assertParseFailure(parser, validExpectedPersonString + PHONE_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, validExpectedPersonString + EMAIL_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, validExpectedPersonString + ADDRESS_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS, PREFIX_EMAIL,
                PREFIX_PHONE, PREFIX_CLIENT));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLIENT));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_clientOptionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddClientCommand(expectedPerson));
    }

    @Test
    public void parse_clientCompulsoryFieldMissing_failure() {
        String addClientExpectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClientCommand.MESSAGE_USAGE);
        String addExpectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                addExpectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                addClientExpectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                addClientExpectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                addClientExpectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                addExpectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
