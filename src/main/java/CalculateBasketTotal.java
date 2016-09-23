import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class CalculateBasketTotal {

    private List shoppingItemsList = Arrays.asList(ShoppingItems.values());

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

    public void calculateShoppingItem(ShoppingItem item) {

        BigDecimal itemCost = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

        item.getShoppingCart().setTotalBill(
                item.getShoppingCart().getTotalBill().add(itemCost));

        System.out.println(item.getQuantity() + " x " +
                item.getName().toUpperCase() +
                " @ " + item.getPrice().setScale(2, RoundingMode.HALF_UP) + " - �" + itemCost.setScale(2, RoundingMode.HALF_UP));
    }
}