import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductList
{
    List<Product> list;

    public ProductList(List<Product> list)
    {
        this.list = new ArrayList<>(list); // copy so it stays mutable
    }

    public void add(Product product)
    {
        list.add(product);
    }

    public Product getProduct(int index)
    {
        return list.get(index);
    }

    public int getLength()
    {
        return list.size(); // simpler than list.toArray().length
    }

    @Override
    public String toString()
    {
        return list.stream()
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
    }
}