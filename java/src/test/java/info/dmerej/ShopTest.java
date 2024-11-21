package info.dmerej;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShopTest {

    private final Address fsfAddress = new Address(
        "51 Franklin Street",
        "Fifth Floor",
        "Boston",
        "02110",
        "USA"
    );

    private final Address parisAddress = new Address(
        "33 quai d'Orsay",
        "",
        "Paris",
        "75007",
        "France"
    );

    public static class UserBuilder {
        private String name = "Pablo";
        private String email = "pablo.escobar@cartel.com";
        private int age = 0;
        private boolean verified = true;
        private Address address = null;

        public UserBuilder() {
        }

        public UserBuilder withAge(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder withVerified(boolean verified) {
            this.verified = verified;
            return this;
        }

        public UserBuilder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(name, email, age, verified, address);
        }
    }

    private UserBuilder userBuilder = new UserBuilder();


    @Test
    public void happy_path() {
        User user = userBuilder.withAddress(    )

        assertTrue(Shop.canOrder(user));
        assertFalse(Shop.mustPayForeignFee(user));
    }

    @Test
    public void minors_cannot_order_from_shop() {

        final User user =

        assertFalse(Shop.canOrder(user));
    }

    @Test
    public void must_be_verified_to_order_from_shop() {
        final User user = new User("Bob", "bob@domain.tld", 25, false, fsfAddress);

        assertFalse(Shop.canOrder(user));
    }

    @Test
    public void must_be_verified_and_not_minor_to_order_from_shop() {



        assertFalse(Shop.canOrder(user));
    }

    @Test
    public void americans_dont_pay_foreign_fee() {
        final User user = new User("Bob", "bob@domain.tld", 25, true, fsfAddress);
        assertFalse(Shop.mustPayForeignFee(user));
    }


    @Test
    public void foreigners_must_pay_foreign_fee() {
        final User user = new User("Bob", "bob@domain.tld", 25, false, parisAddress);

        assertTrue(Shop.mustPayForeignFee(user));
    }

}
