import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@ToString
public class Product {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    @Setter(AccessLevel.NONE)
    private final int id;
    String name;
    double price;
    String description;
    Category category;

    public Product(String name, double price, String description, Category category) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.price = price;
        this.name = name;
        this.description = description;
        this.category = category;
    }
}
