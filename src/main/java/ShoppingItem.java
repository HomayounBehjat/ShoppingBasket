import java.math.BigDecimal;

public class ShoppingItem {
    private String name;
    private int quantity;
    private BigDecimal price;

    private ShoppingBasket shoppingBasket;

    public ShoppingItem(String name, int quantity, BigDecimal price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ShoppingBasket getShoppingCart() {
        return shoppingBasket;
    }

    public void setShoppingCart(ShoppingBasket shoppingCart) {
        this.shoppingBasket = shoppingCart;
    }
}