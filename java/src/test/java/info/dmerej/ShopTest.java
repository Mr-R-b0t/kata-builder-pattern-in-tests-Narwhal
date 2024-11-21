package info.dmerej;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShopTest {

    public static class UserBuilder {
        private final static String name = "Pablo";
        private final static String email = "pablo.escobar@cartel.com";
        private int age = 25;
        private boolean verified = false;
        private Address address = new Address(
                "33 quai d'Orsay",
                "",
                "Paris",
                "75007",
                "France"
        );;

        public UserBuilder() {
        }

        public UserBuilder minor() {
            this.age = 16;
            return this;
        }

        public UserBuilder verified() {
            this.verified = true;
            return this;
        }

        public UserBuilder american() {
            this.address = new Address(
                    "51 Franklin Street",
                    "Fifth Floor",
                    "Boston",
                    "02110",
                    "USA"
            );
            return this;
        }

        public User build() {
            return new User(name, email, age, verified, address);
        }
    }

    private final UserBuilder userBuilder = new UserBuilder();


    @Test
    public void happy_path() {
        User user = userBuilder.american().verified()
                                .build();

        assertTrue(Shop.canOrder(user));
        assertFalse(Shop.mustPayForeignFee(user));
    }

    @Test
    public void minors_cannot_order_from_shop() {

        User user = userBuilder.minor().build();

        assertFalse(Shop.canOrder(user));
    }

    @Test
    public void must_be_verified_to_order_from_shop() {
        User user = userBuilder.build();

        assertFalse(Shop.canOrder(user));
    }

    @Test
    public void must_be_verified_and_not_minor_to_order_from_shop() {

        User user = userBuilder.verified().build();

        assertTrue(Shop.canOrder(user));
    }

    @Test
    public void americans_dont_pay_foreign_fee() {
        final User user = userBuilder.american().build();
        assertFalse(Shop.mustPayForeignFee(user));
    }


    @Test
    public void foreigners_must_pay_foreign_fee() {

        User user = userBuilder.verified()
                               .build();

        assertTrue(Shop.mustPayForeignFee(user));
    }

}
