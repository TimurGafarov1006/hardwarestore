package ru.itis.hardwarestore.repositories.queries;

public class OrderQueries {
    public static final String SAVE_SQL = """
            INSERT
            INTO orders (user_id, amount_before_discount, discount_amount, total_amount, created_at, delivered_at, closed_at)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
    public static final String FIND_BY_ID_SQL = """
            SELECT *
            FROM orders
            WHERE id = ?
            """;
    public static final String FIND_ALL_BY_USER_ID_SQL = """
            SELECT *
            FROM orders
            WHERE user_id = ?
            """;
}
