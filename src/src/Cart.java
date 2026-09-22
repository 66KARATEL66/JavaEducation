import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Cart {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(Objects.requireNonNull(product, "product must not be null"));
    }

    public boolean removeProductByIndex(int index) {
        if (index < 0 || index >= products.size()) {
            return false;
        }

        products.remove(index);
        return true;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public List<Product> getProducts() {
        return List.copyOf(products);
    }

    public double getTotalPrice() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public void clear() {
        products.clear();
    }

    @Override
    public String toString() {
        if (products.isEmpty()) {
            return "Cart is empty";
        }
        String items = IntStream.range(0, products.size())
                .mapToObj(index -> (index + 1) + ". " + products.get(index))
                .reduce((first, second) -> first + System.lineSeparator() + second)
                .orElseThrow();
        return "Cart has:" + System.lineSeparator() + items + System.lineSeparator()
                + "Total price: " + getTotalPrice();
    }
}
