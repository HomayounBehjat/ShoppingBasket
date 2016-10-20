import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ShoppingBasketTest {

    private static final BigDecimal appleCost = new BigDecimal(0.60);
    private static final BigDecimal orangeCost = new BigDecimal(0.25);
    private ShoppingBasket shoppingBasket;

    @Before
    public void setUp() {
        shoppingBasket = new ShoppingBasket();
    }

    @Test
    public void testDiscountOnApple() {
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Apple.toString(), 3, appleCost));

        shoppingBasket.calculateTotals(new CalculateBasketTotal());

        assertThat(shoppingBasket.getTotalBill().setScale(2, RoundingMode.HALF_UP), is(new BigDecimal(1.2).setScale(2, RoundingMode.HALF_UP)));
    }

    @Test
    public void testDiscountOnAppleAndOrange() {
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Apple.toString(), 4, appleCost));
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Orange.toString(), 8, orangeCost));

        shoppingBasket.calculateTotals(new CalculateBasketTotal());

        assertThat(shoppingBasket.getTotalBill().setScale(2, RoundingMode.HALF_UP), is(new BigDecimal(2.7).setScale(2, RoundingMode.HALF_UP)));
    }

    @Test
    public void testNewAppleItem() {
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Apple.toString(), 4, appleCost));

        shoppingBasket.calculateTotals(new CalculateBasketTotal());

        assertThat(shoppingBasket.getTotalBill().setScale(2, RoundingMode.HALF_UP), is(new BigDecimal(1.20).setScale(2, RoundingMode.HALF_UP)));
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
    public void testShoppingBasketContainsThreeApplesAndTwoOranges() {
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Apple.toString(), 3, appleCost));
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Orange.toString(), 2, orangeCost));

        Collection<org.hamcrest.Matcher<? super ShoppingItem>> matchers = Arrays.asList(
                hasProperty("name", is(ShoppingItems.Apple.toString())),
                hasProperty("name", is(ShoppingItems.Orange.toString())));

        assertThat(shoppingBasket.getShoppingItems(), containsInAnyOrder(matchers));
        // Apple Quantities
        assertThat(shoppingBasket.getShoppingItems(), hasItem(Matchers.<ShoppingItem>hasProperty("quantity", equalTo(3))));
        // Orange quantities
        assertThat(shoppingBasket.getShoppingItems(), hasItem(Matchers.<ShoppingItem>hasProperty("quantity", equalTo(2))));
    }

    @Test
    public void testShoppingBasketContainsThreeApplesOnly() {
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Apple.toString(), 3, appleCost));

        Collection<org.hamcrest.Matcher<? super ShoppingItem>> matchers = Collections.singletonList(hasProperty("name", is(ShoppingItems.Apple.toString())));

        assertThat(shoppingBasket.getShoppingItems(), containsInAnyOrder(matchers));
        assertThat(shoppingBasket.getShoppingItems(), hasItem(Matchers.<ShoppingItem>hasProperty("quantity", equalTo(3))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShoppingBasketDoesNotContainItemsInSet() {
        shoppingBasket.addShoppingItem(new ShoppingItem("Strawberry", 1, BigDecimal.valueOf(0.15)));

        for (ShoppingItem _item : shoppingBasket.getShoppingItems()) {
            assertEquals("Strawberry", ShoppingItems.valueOf(_item.getName()).toString());
        }
    }

    @Test
    public void testShoppingBasketIsEmpty() {
        assertThat(shoppingBasket.getShoppingItems(), is(IsNull.notNullValue()));
    }

    @Test
    public void testTotalShoppingBasketValueApplesAndOranges() {
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Apple.toString(), 3, appleCost));
        shoppingBasket.addShoppingItem(new ShoppingItem(ShoppingItems.Orange.toString(), 4, orangeCost));

        shoppingBasket.calculateTotals(new CalculateBasketTotal());

        assertThat(shoppingBasket.getTotalBill().setScale(2, RoundingMode.HALF_UP), is(new BigDecimal(1.95).setScale(2, RoundingMode.HALF_UP)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnknownItem() {
        shoppingBasket.addShoppingItem(new ShoppingItem("Peaches", 4, appleCost));
        shoppingBasket.calculateTotals(new CalculateBasketTotal());

        assertThat(shoppingBasket.getTotalBill(), is(new BigDecimal(2.7)));
    }
}