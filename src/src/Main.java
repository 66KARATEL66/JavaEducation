import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main
{
    public static void main(String[] args)
    {
        record MenuItem(String title, Runnable action) {}

        Cart cart = new Cart();
        List<Order> orderList = new ArrayList<>();

        Category phones = new Category("Phones");
        Category laptops = new Category("Laptops");
        Category fruits = new Category("Fruits");

        ProductList list = new ProductList(List.of(
                new Product("laptop", 60000, "pc", laptops),
                new Product("phone", 30000, "s23", phones),
                new Product("apple", 30, "lorem ipsum", fruits),
                new Product("banana", 70, "lorem ipsum", fruits)
        ));

        class Menu
        {
            Scanner scanner = new Scanner(System.in);
            boolean running = true; // field, not local var, so lambdas can change it

            List<MenuItem> itemList = List.of(
                    new MenuItem("Show products list", () -> System.out.println(list)),
                    new MenuItem("Search", this::search),
                    new MenuItem("Add product to Cart", this::addProductToCart),
                    new MenuItem("Check Cart", () -> System.out.println(cart)),
                    new MenuItem("Delete product from Cart", this::deleteProductFromCart),
                    new MenuItem("Make order", () -> {
                        if(cart.products.isEmpty()) {System.out.print("Cart is empty \n"); return;}

                        orderList.add(new Order(cart));
                        System.out.println("Order is completed");
                        System.out.print(orderList.getLast());
                        cart.clear();
                    }),
                    new MenuItem("Check order history", () -> {
                        System.out.print(orderList.stream()
                                .map(order -> "Order " + order.id + ":\n" + order)
                                .collect(Collectors.joining("\n\n"))
                        );
                    }),
                    new MenuItem("Exit", () -> {
                        System.out.println("Thanks for using");
                        running = false;
                    })
            );

            private int getChoice(String text)
            {
                System.out.print(text);
                while (true)
                {
                    String input = scanner.nextLine().trim();
                    try
                    {
                        return Integer.parseInt(input);
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.print("Please enter a valid number: ");
                    }
                }
            }

            private void addProductToCart()
            {
                while (true)
                {
                    int id = getChoice("Enter the id to add product to Cart (0 to stop): ");
                    if (id == 0)
                    {
                        break;
                    }
                    if (id < 1 || id > list.getLength())
                    {
                        System.out.println("Invalid id; Try again!");
                        continue;
                    }
                    cart.addProduct(list.getProduct(id - 1));
                    System.out.println("Added to cart!");
                }
            }

            private void deleteProductFromCart()
            {
                while (true)
                {
                    int id = getChoice("Enter the id to delete product from Cart (0 to stop): ");
                    if (id == 0)
                    {
                        break;
                    }
                    if (id < 1 || id > list.getLength())
                    {
                        System.out.println("Invalid id; Try again!");
                        continue;
                    }
                    cart.deleteProduct(id - 1);
                    System.out.println("Deleted from cart!");
                }
            }

            private void search()
            {
                System.out.print("Enter name or category: ");
                String text = scanner.nextLine().trim().toLowerCase();

                if (text.isEmpty())
                {
                    System.out.println("Search text cannot be empty!");
                    return;
                }

                List<Product> found = list.list.stream()
                        .filter(p -> p.getName().toLowerCase().contains(text)
                                || p.getCategory().getName().toLowerCase().contains(text))
                        .collect(Collectors.toList());

                if (found.isEmpty())
                {
                    System.out.println("No products found.");
                }
                else
                {
                    String result = found.stream()
                            .map(Product::toString)
                            .collect(Collectors.joining("\n"));
                    System.out.println(result);
                }
            }

            private void run()
            {
                while (running)
                {
                    System.out.println(this);
                    int choice = getChoice("Enter a choice: ");

                    if (choice < 1 || choice > itemList.size())
                    {
                        System.out.println("Invalid choice; Try again");
                        continue;
                    }

                    itemList.get(choice - 1).action().run();
                }
            }

            @Override
            public String toString()
            {
                System.out.print('\n');
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < itemList.size(); i++)
                {
                    sb.append(i + 1).append(". ").append(itemList.get(i).title()).append("\n");
                }
                return sb.toString();
            }
        }

        new Menu().run();
    }
}