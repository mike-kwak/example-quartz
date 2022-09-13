package page.hellomike.example.quartz.model;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public class ImmutableCounter implements Counter {

    private final UUID id;

    private final long value;

    private final ZonedDateTime createdAt;

    public ImmutableCounter() {
        this.id = UUID.randomUUID();
        this.value = 0;
        this.createdAt = ZonedDateTime.now();
    }

    public ImmutableCounter(UUID id, long value, ZonedDateTime createdAt) {
        this.id = id;
        this.value = value;
        this.createdAt = createdAt;
    }

    public Counter withValueIncrease() {
        return new ImmutableCounter(this.id, value + 1, createdAt);
    }

    public Counter withClear() {
        return new ImmutableCounter(this.id, 0, createdAt);
    }

    public UUID getId() {
        return id;
    }

    public long getValue() {
        return value;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmutableCounter that = (ImmutableCounter) o;
        return id.equals(that.id) && createdAt.equals(that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt);
    }

    @Override
    public String toString() {
        return "Counter{" +
                "id=" + id +
                ", value=" + value +
                ", createdAt=" + createdAt +
                '}';
    }

}
