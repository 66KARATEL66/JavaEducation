import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/** In-memory product catalogue. */
public class ProductList {
    private final List<Product> products;

    public ProductList(List<Product> products) {
        this.products = new ArrayList<>(Objects.requireNonNull(products, "products must not be null"));
        this.products.forEach(product -> Objects.requireNonNull(product, "products must not contain null"));
    }

    public void add(Product product) {
        products.add(Objects.requireNonNull(product, "product must not be null"));
    }

    public Optional<Product> findById(int id) {
        return products.stream().filter(product -> product.getId() == id).findFirst();
    }

    public List<Product> search(String query) {
        String normalizedQuery = Objects.requireNonNull(query, "query must not be null").trim().toLowerCase(Locale.ROOT);
        return products.stream()
                .filter(product -> product.getName().toLowerCase(Locale.ROOT).contains(normalizedQuery)
                        || product.getCategory().getName().toLowerCase(Locale.ROOT).contains(normalizedQuery))
                .toList();
    }

    public List<Product> getProducts() {
        return List.copyOf(products);
    }

    @Override
    public String toString() {
        return products.stream().map(Product::toString).collect(Collectors.joining(System.lineSeparator()));
    }
}
