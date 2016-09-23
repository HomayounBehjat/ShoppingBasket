import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ShoppingBasketTest {

    static BigDecimal appleCost = new BigDecimal(0.60);
    static BigDecimal orangeCost = new BigDecimal(0.25);
    ShoppingBasket shoppingBasket;

    @Before
    public void setUp() {
        shoppingBasket = new ShoppingBasket();
    }

    @Test
    public void testNewAppleItem() {
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Apple.toString(), 4, appleCost));

        shoppingBasket.calculateTotals(new CalculateBasketTotal());

        assertThat(shoppingBasket.getTotalBill().setScale(2, RoundingMode.HALF_UP), is(new BigDecimal(2.40).setScale(2, RoundingMode.HALF_UP)));
    }

    @Test
    public void testOnlyOneItemInCart() {
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Apple.toString(), 1, appleCost));

        int numberOfItems = shoppingBasket.getShoppingItems().size();

        assertThat(numberOfItems, is(1));
    }

    @Test
    public void testOnlyTwoItemsInCart() {
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Apple.toString(), 1, appleCost));
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Orange.toString(), 1, orangeCost));

        int numberOfItems = shoppingBasket.getShoppingItems().size();

        assertThat(numberOfItems, is(2));
    }

    @Test
    public void testshoppingBasketContainsThreeApplesAndTwoOranges() {
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Apple.toString(), 3, appleCost));
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Orange.toString(), 2, orangeCost));

        Collection matchers = Arrays.asList(
                hasProperty("name", is(ShoppingItems.Apple.toString())),
                hasProperty("name", is(ShoppingItems.Orange.toString())));

        assertThat(shoppingBasket.getShoppingItems(), containsInAnyOrder(matchers));
        // Apple Quantities
        assertThat(shoppingBasket.getShoppingItems(), hasItem(Matchers.<ShoppingItem>hasProperty("quantity", equalTo(3))));
        // Orange quantities
        assertThat(shoppingBasket.getShoppingItems(), hasItem(Matchers.<ShoppingItem>hasProperty("quantity", equalTo(2))));
    }

    @Test
    public void testshoppingBasketContainsThreeApplesOnly() {
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Apple.toString(), 3, appleCost));

        Collection matchers = Arrays.asList(
                hasProperty("name", is(ShoppingItems.Apple.toString())));

        assertThat(shoppingBasket.getShoppingItems(), containsInAnyOrder(matchers));
        assertThat(shoppingBasket.getShoppingItems(), hasItem(Matchers.<ShoppingItem>hasProperty("quantity", equalTo(3))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testshoppingBasketDoesNotContainItemsInSet() {
        shoppingBasket.addShoppingItem(new ShoppingItem("Strawberry", 1, BigDecimal.valueOf(0.15)));

        for (ShoppingItem _item : shoppingBasket.getShoppingItems()) {
            assertEquals(false, ShoppingItems.valueOf(_item.getName()));
        }
    }

    @Test
    public void testshoppingBasketIsEmpty() {
        assertThat(shoppingBasket.getShoppingItems(), is(IsNull.notNullValue()));
    }

    @Test
    public void testTotalshoppingBasketValueApplesAndOranges() {
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Apple.toString(), 3, appleCost));
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Orange.toString(), 2, orangeCost));

        shoppingBasket.calculateTotals(new CalculateBasketTotal());

        assertThat(shoppingBasket.getTotalBill().setScale(2, RoundingMode.HALF_UP), is(new BigDecimal(2.3).setScale(2, RoundingMode.HALF_UP)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnknowItem() {
        shoppingBasket.addShoppingItem(new ShoppingItem("Peaches", 4, appleCost));
        shoppingBasket.calculateTotals(new CalculateBasketTotal());

        assertThat(shoppingBasket.getTotalBill(), is(new BigDecimal(2.7)));
    }
}