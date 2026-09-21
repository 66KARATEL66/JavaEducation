import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Getter
public class Order {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private final int id;
    private final List<Product> products;
    private final double totalPrice;
    String status;

    public Order(Cart cart) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.products = List.copyOf(cart.getProducts());
        this.totalPrice = cart.getTotalPrice();
        this.status = "New";
    }

    @Override
    public String toString() {
        String productLines = products.stream().map(Product::toString).collect(Collectors.joining(System.lineSeparator()));
        return productLines + System.lineSeparator() + "Total price: " + totalPrice
                + System.lineSeparator() + "Status: " + status;
    }
}
