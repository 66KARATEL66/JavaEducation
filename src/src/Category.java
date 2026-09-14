import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Category
{
    static final AtomicInteger idGenerator = new AtomicInteger(1);

    int id;
    String name;

    public Category(String name)
    {
        this.id = idGenerator.getAndIncrement();
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}