package ru.itis.hardwarestore.repositories.queries;

public class CartElementsQueries {
    public static final String SAVE_SQL = """
            INSERT
            INTO cart_elements (user_id, product_id, quantity)
            VALUES (?, ?, ?)
            """;
    public static final String FIND_ALL_BY_USER_ID_SQL = """
            SELECT *
            FROM cart_elements
            WHERE user_id = ?
            """;
    public static final String UPDATE_SQL = """
            UPDATE cart_elements
            SET quantity = ?
            WHERE id = ?
            """;
    public static final String DELETE_SQL = """
            DELETE FROM cart_elements
            WHERE id = ?
            """;
    public static final String FIND_BY_ID_SQL = """
            SELECT *
            FROM cart_elements
            WHERE id = ?
            """;
}
