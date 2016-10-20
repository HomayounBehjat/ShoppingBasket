import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket {

    private BigDecimal totalBill = new BigDecimal(0.0);

    private List<ShoppingItem> shoppingItems = new ArrayList<>();

    public void addShoppingItem(ShoppingItem shoppingCartItem) {
        shoppingCartItem.setShoppingCart(this);
        shoppingItems.add(shoppingCartItem);
    }

    public void calculateTotals(CalculateBasketTotal calculateBasketTotal) {
        calculateBasketTotal.calculateBasketTotal(this);
    }

    public List<ShoppingItem> getShoppingItems() {
        return shoppingItems;
    }

    public void setShoppingItems(List<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
    }

    public BigDecimal getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(BigDecimal totalBill) {
        this.totalBill = totalBill;
    }
}