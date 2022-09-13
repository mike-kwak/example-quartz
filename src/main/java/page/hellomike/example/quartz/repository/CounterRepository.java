package page.hellomike.example.quartz.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import page.hellomike.example.quartz.model.Counter;
import page.hellomike.example.quartz.model.ImmutableCounter;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("database")
public class CounterRepository {

    private final JdbcTemplate jdbcTemplate;

    public CounterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Counter> findCounterById(UUID id) {
        String query = """
                SELECT ID, COUNT_VALUE, CREATED_AT
                FROM T_COUNTER
                WHERE ID = ?
                """;

        Counter counter = jdbcTemplate.queryForObject(query, this.createCounterRowMapper(), id.toString());

        return Optional.ofNullable(counter);
    }

    public List<Counter> findCounters() {
        String query = """
                SELECT ID, COUNT_VALUE, CREATED_AT
                FROM T_COUNTER
                """;

        return jdbcTemplate.query(query, this.createCounterRowMapper());
    }

    public Counter createCounter() {
        Counter counter = new ImmutableCounter();

        String query = """
                INSERT INTO T_COUNTER (ID, COUNT_VALUE, CREATED_AT)
                VALUES (?, 0, ?)
                """;

        jdbcTemplate.update(query, counter.getId().toString(), counter.getCreatedAt());

        return counter;
    }

    public int updateCounterAsIncrease(UUID counterId) {
        String query = """
                UPDATE T_COUNTER
                SET
                    COUNT_VALUE = COUNT_VALUE + 1
                WHERE ID = ?
                """;

        return jdbcTemplate.update(query, counterId.toString());
    }

    public int updateCounterAsClear(UUID counterId) {
        String updateQuery = """
                UPDATE T_COUNTER
                SET
                    COUNT_VALUE = 0
                WHERE ID = ?
                """;

        return jdbcTemplate.update(updateQuery, counterId.toString());
    }

    public int deleteCounter(UUID counterId) {
        String query = """
                DELETE FROM T_COUNTER
                WHERE ID = ?
                """;

        return jdbcTemplate.update(query, counterId.toString());
    }
    
    private RowMapper<Counter> createCounterRowMapper() {
        return (rs, rowNum) -> {
            String fetchId = rs.getString("ID");
            long fetchCount = rs.getLong("COUNT_VALUE");
            ZonedDateTime createdAt = null;
            Timestamp rawCreatedAt = rs.getTimestamp("CREATED_AT");
            if (rawCreatedAt != null) {
                createdAt = rawCreatedAt.toInstant().atZone(ZoneOffset.UTC);
            }

            return new ImmutableCounter(
                    UUID.fromString(fetchId),
                    fetchCount,
                    createdAt
            );
        };
    }

}
