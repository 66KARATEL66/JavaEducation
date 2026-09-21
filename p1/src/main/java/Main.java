import java.util.List;

public class Main {
    public static void main(String[] args) {
        Category phones = new Category("Phones");
        Category laptops = new Category("Laptops");
        Category fruits = new Category("Fruits");

        ProductList catalogue = new ProductList(List.of(
                new Product("Laptop", 60_000, "PC", laptops),
                new Product("Phone", 30_000, "S23", phones),
                new Product("Apple", 30, "Fresh apple", fruits),
                new Product("Banana", 70, "Fresh banana", fruits)
        ));
        new ConsoleMenu(catalogue, List.of(phones, laptops, fruits)).run();
    }
}
