import lombok.Getter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Order {
    // private
    static final AtomicInteger idGenerator = new AtomicInteger(1);

    int id;
    List<Product> products;
    double totalPrice;
    String status;

    public Order(Cart cart)
    {
        this.id = idGenerator.getAndIncrement();
        this.products = cart.products;
        this.totalPrice = cart.getTotalPrice();
        this.status = "New";
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(Product product : products)
        {
            sb.append(product.toString()).append("\n");
        }
        sb.append("Total price: ").append(totalPrice).append("\n");
        sb.append("Status: ").append(status);
        return sb.toString();
    }
}
