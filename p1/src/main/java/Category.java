import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Category {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    @Setter(AccessLevel.NONE)
    private final int id;
    private final String name;

    public Category(String name) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.name = requireName(name);
    }

    @Override
    public String toString() {
        return name;
    }

    private String requireName(String value) {
        String trimmedValue = Objects.requireNonNull(value, "name must not be null").trim();
        if (trimmedValue.isEmpty()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        return trimmedValue;
    }
}
