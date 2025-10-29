package ru.itis.hardwarestore.repositories.queries;

public class OrderListQueries {
    public static final String SAVE_SQL = """
            INSERT
            INTO order_lists (order_id, product_id, quantity)
            VALUES (?, ?, ?)
            """;
    public static final String FIND_ALL_BY_ORDER_ID_SQL = """
            SELECT *
            FROM order_lists
            WHERE order_id = ?
            """;
}
