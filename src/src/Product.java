import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@ToString
public class Product
{
    static final AtomicInteger idGenerator = new AtomicInteger(1);

    final int id;
    String name;
    double price;
    String description;
    Category category;

    public Product(String name, double price, String description, Category category)
    {
        this.id = idGenerator.getAndIncrement();
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }
}