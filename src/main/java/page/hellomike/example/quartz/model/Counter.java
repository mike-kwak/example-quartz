package page.hellomike.example.quartz.model;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface Counter {

    Counter withValueIncrease();

    Counter withClear();

    UUID getId();

    long getValue();

    ZonedDateTime getCreatedAt();

}
