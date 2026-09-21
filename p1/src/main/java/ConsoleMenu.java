import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/** Console user interface for the catalogue, cart, and order history. */
public class ConsoleMenu {
    private final ProductList catalogue;
    private final List<Category> categories;
    private final Cart cart = new Cart();
    private final List<Order> orders = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private final List<MenuItem> menuItems;
    private boolean running = true;

    public ConsoleMenu(ProductList catalogue, List<Category> categories) {
        this.catalogue = catalogue;
        this.categories = List.copyOf(categories);
        this.menuItems = List.of(
                new MenuItem("Show products list", () -> System.out.println(catalogue)),
                new MenuItem("Search", this::search),
                new MenuItem("Update product", this::updateProduct),
                new MenuItem("Add product to cart", this::addProductToCart),
                new MenuItem("Check cart", () -> System.out.println(cart)),
                new MenuItem("Delete product from cart", this::deleteProductFromCart),
                new MenuItem("Make order", this::makeOrder),
                new MenuItem("Check order history", this::showOrderHistory),
                new MenuItem("Exit", this::exit)
        );
    }

    public void run() {
        while (running) {
            printMenu();
            int choice = readInt("Enter a choice: ");
            if (choice < 1 || choice > menuItems.size()) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }
            menuItems.get(choice - 1).action().run();
        }
    }

    private void addProductToCart() {
        int productId = readInt("Enter product ID to add (0 to stop): ");
        if (productId == 0) {
            return;
        }
        catalogue.findById(productId).ifPresentOrElse(cart::addProduct,
                () -> System.out.println("Product was not found."));
    }

    private void deleteProductFromCart() {
        int productId = readInt("Enter product ID to delete (0 to stop): ");
        if (productId != 0 && !cart.removeProductById(productId)) {
            System.out.println("Product was not found in the cart.");
        }
    }

    private void updateProduct() {
        int productId = readInt("Enter product ID to update (0 to stop): ");
        if (productId == 0) {
            return;
        }
        catalogue.findById(productId).ifPresentOrElse(this::readProductUpdate,
                () -> System.out.println("Product was not found."));
    }

    private void readProductUpdate(Product product) {
        product.name = readText("Enter name: ");
        product.price = readPrice("Enter price: ");
        product.description = readText("Enter description: ");
        product.category = readCategory();
        System.out.println("Product updated.");
    }

    private void search() {
        String query = readText("Enter product name or category: ");
        List<Product> results = catalogue.search(query);
        System.out.println(results.isEmpty() ? "No products found." : joinProducts(results));
    }

    private void makeOrder() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        Order order = new Order(cart);
        orders.add(order);
        cart.clear();
        System.out.println("Order " + order.getId() + " is completed.");
    }

    private void showOrderHistory() {
        if (orders.isEmpty()) {
            System.out.println("Order history is empty.");
            return;
        }
        System.out.println(orders.stream()
                .map(order -> "Order " + order.getId() + ":" + System.lineSeparator() + order)
                .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator())));
    }

    private Category readCategory() {
        System.out.println("Choose category:");
        for (int index = 0; index < categories.size(); index++) {
            System.out.println((index + 1) + ". " + categories.get(index).getName());
        }
        while (true) {
            int choice = readInt("Enter category number: ");
            if (choice >= 1 && choice <= categories.size()) {
                return categories.get(choice - 1);
            }
            System.out.println("Invalid category. Try again.");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readText(prompt));
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private double readPrice(String prompt) {
        while (true) {
            try {
                double price = Double.parseDouble(readText(prompt));
                if (price >= 0) {
                    return price;
                }
            } catch (NumberFormatException ignored) {
                // A uniform message is printed below for all invalid input.
            }
            System.out.println("Please enter a valid non-negative price.");
        }
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        while (true) {
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.print("Value must not be blank. Try again: ");
        }
    }

    private void printMenu() {
        System.out.println();
        for (int index = 0; index < menuItems.size(); index++) {
            System.out.println((index + 1) + ". " + menuItems.get(index).title());
        }
    }

    private void exit() {
        System.out.println("Thanks for using the shop.");
        running = false;
    }

    private String joinProducts(List<Product> products) {
        return products.stream().map(Product::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    private record MenuItem(String title, Runnable action) {
    }
}
