package rose_atelier;

public class CartItem {

    private Rose rose;
    private int quantity;

    public CartItem(Rose rose, int quantity) {
        this.rose = rose;
        this.quantity = quantity;
    }

    public Rose getRose() {
        return rose;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSubtotal() {
        return rose.getPrice() * quantity;
    }

    public void addQuantity(int amount) {
        this.quantity += amount;
    }
}