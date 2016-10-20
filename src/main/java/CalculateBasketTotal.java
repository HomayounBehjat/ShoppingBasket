import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class CalculateBasketTotal {

    private List<ShoppingItems> shoppingItemsList = Arrays.asList(ShoppingItems.values());

    public void calculateBasketTotal(ShoppingBasket shoppingBasket) {

        for (ShoppingItem shoppingItem : shoppingBasket.getShoppingItems()) {
            boolean itemFound = shoppingItemsList.stream().anyMatch(item -> item.toString().equals(shoppingItem.getName()));
            if (itemFound) {
                calculateShoppingItem(shoppingItem);
            } else {
                throw new IllegalArgumentException("No such Item in Stock!");
            }
        }

        System.out.println("\nAmount to pay : �" + shoppingBasket.getTotalBill().setScale(2, RoundingMode.HALF_UP));
    }

    /*
    * Calculates a discount according to the following:
    * For Apples its buy one get one free.
    * For Oranges its buy 3 pay for 2.
    *
    * Returns the adjusted quantity for the related discount.
    * if no discount is applicable then returns the original quantity.
    */
    public int calculateDiscount(ShoppingItem item) {
        int discountedQuantity = item.getQuantity();

        switch (ShoppingItems.valueOf(item.getName())) {
            case Apple:
                //Specifically checking buy 1 get 1 offer
                //Checking if there are odd apples, then 1 will be need to added explicitly
                if (item.getQuantity() > 1) {
                    discountedQuantity = (item.getQuantity() / 2) + (item.getQuantity() % 2);
                }
                break;

            case Orange:
                //Specifically checking 3 for the price of 2 for Oranges
                if (item.getQuantity() > 2) {
                    discountedQuantity = item.getQuantity() - (item.getQuantity() / 3);
                }
                break;

            default:
                break;
        }

        return discountedQuantity;
    }

    public void calculateShoppingItem(ShoppingItem item) {

        BigDecimal itemCost = item.getPrice().multiply(BigDecimal.valueOf(calculateDiscount(item)));

        item.getShoppingCart().setTotalBill(
                item.getShoppingCart().getTotalBill().add(itemCost));

        System.out.println(item.getQuantity() + " x " +
                item.getName().toUpperCase() +
                " @ " + item.getPrice().setScale(2, RoundingMode.HALF_UP) + " - �" + itemCost.setScale(2, RoundingMode.HALF_UP));
    }
}