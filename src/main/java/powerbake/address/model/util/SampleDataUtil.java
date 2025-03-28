package powerbake.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import powerbake.address.model.AddressBook;
import powerbake.address.model.ReadOnlyAddressBook;
import powerbake.address.model.order.Order;
import powerbake.address.model.order.OrderItem;
import powerbake.address.model.order.OrderStatus;
import powerbake.address.model.pastry.Pastry;
import powerbake.address.model.pastry.Price;
import powerbake.address.model.person.Address;
import powerbake.address.model.person.Email;
import powerbake.address.model.person.Name;
import powerbake.address.model.person.Person;
import powerbake.address.model.person.Phone;
import powerbake.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Person PERSON_1 = new Person(
        new Name("Alex Yeoh"),
        new Phone("87438807"),
        new Email("alexyeoh@example.com"),
        new Address("Blk 30 Geylang Street 29, #06-40"),
        getTagSet("friends"));

    public static final Person PERSON_2 = new Person(
            new Name("Bernice Yu"),
            new Phone("99272758"),
            new Email("berniceyu@example.com"),
            new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
            getTagSet("colleagues", "friends"));

    public static final Person PERSON_3 = new Person(
            new Name("Charlotte Oliveiro"),
            new Phone("93210283"),
            new Email("charlotte@example.com"),
            new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
            getTagSet("neighbours"));

    public static final Person PERSON_4 = new Person(
            new Name("David Li"),
            new Phone("91031282"),
            new Email("lidavid@example.com"),
            new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
            getTagSet("family"));

    public static final Person PERSON_5 = new Person(
            new Name("Irfan Ibrahim"),
            new Phone("92492021"),
            new Email("irfan@example.com"),
            new Address("Blk 47 Tampines Street 20, #17-35"),
            getTagSet("classmates"));

    public static final Person PERSON_6 = new Person(
            new Name("Roy Balakrishnan"),
            new Phone("92624417"),
            new Email("royb@example.com"),
            new Address("Blk 45 Aljunied Street 85, #11-31"),
            getTagSet("colleagues"));


    public static final Pastry PASTRY_1 = new Pastry(
            new powerbake.address.model.pastry.Name("Apple Pie"), new Price("3.50"));
    public static final Pastry PASTRY_2 = new Pastry(
            new powerbake.address.model.pastry.Name("Brownie"), new Price("2.50"));
    public static final Pastry PASTRY_3 = new Pastry(
            new powerbake.address.model.pastry.Name("Cheesecake"), new Price("4.00"));

    public static Person[] getSamplePersons() {
        return new Person[] {
            PERSON_1,
            PERSON_2,
            PERSON_3,
            PERSON_4,
            PERSON_5,
            PERSON_6
        };
    }

    public static Pastry[] getSamplePastries() {
        return new Pastry[] {
            PASTRY_1,
            PASTRY_2,
            PASTRY_3
        };
    }

    public static Order[] getSampleOrders() {
        return new Order[] {
            new Order(PERSON_1, Arrays.asList(
                new OrderItem(PASTRY_1, 1),
                new OrderItem(PASTRY_2, 2)
            )).withStatus(OrderStatus.PENDING),
            new Order(PERSON_2, Arrays.asList(
                new OrderItem(PASTRY_3, 1)
            )).withStatus(OrderStatus.PENDING),
            new Order(PERSON_3, Arrays.asList(
                new OrderItem(PASTRY_2, 3),
                new OrderItem(PASTRY_1, 1)
            )).withStatus(OrderStatus.PROCESSING),
            new Order(PERSON_4, Arrays.asList(
                new OrderItem(PASTRY_3, 2)
            )).withStatus(OrderStatus.CANCELLED),
            new Order(PERSON_5, Arrays.asList(
                new OrderItem(PASTRY_1, 1),
                new OrderItem(PASTRY_2, 2),
                new OrderItem(PASTRY_3, 1)
            )).withStatus(OrderStatus.DELIVERED),
            new Order(PERSON_6, Arrays.asList(
                new OrderItem(PASTRY_1, 3)
            )).withStatus(OrderStatus.DELIVERED)
        };
    }
    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Pastry samplePastry : getSamplePastries()) {
            sampleAb.addPastry(samplePastry);
        }
        for (Order sampleOrder : getSampleOrders()) {
            sampleAb.addOrder(sampleOrder);
        }

        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
