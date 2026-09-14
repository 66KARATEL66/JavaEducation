import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class Cart
{
    List<Product> products;

    public Cart()
    {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product)
    {
        products.add(product);
    }
    public void deleteProduct(int id) {products.remove(id);}

    public void removeProduct(Product product)
    {
        products.remove(product);
    }

    public double getTotalPrice()
    {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public void clear()
    {
        products = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        if (products.isEmpty())
        {
            return "Cart is empty";
        }

        String items = IntStream.range(0, products.size())
                .mapToObj(i -> (i + 1) + ". " + products.get(i))
                .collect(Collectors.joining("\n"));

        return "Cart has:\n" + items + "\nTotal Price: " + getTotalPrice();
    }
}