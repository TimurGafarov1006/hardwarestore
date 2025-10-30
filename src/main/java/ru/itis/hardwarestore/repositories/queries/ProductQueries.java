package ru.itis.hardwarestore.repositories.queries;

public class ProductQueries {
    public static final String SAVE_SQL = """
            INSERT
            INTO products (name, slug, description, category_id, price_per_unit, quantity, image_url, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
    public static final String FIND_ALL_SQL = """
            SELECT *
            FROM products
            """;
    public static final String FIND_BY_ID_SQL = """
            SELECT *
            FROM products
            WHERE id = ?
            """;
    public static final String FIND_BY_CATEGORY_ID_SQL = """
            SELECT *
            FROM products
            WHERE category_id = ?
            """;
    public static final String FIND_BY_SLUG_SQL = """
            SELECT *
            FROM products
            WHERE slug = ?
            """;
    public static final String FIND_ALL_LIKE_NAME_SQL = """
            SELECT *
            FROM products
            WHERE name ILIKE ?
            """;
    public static final String UPDATE_SQL = """
            UPDATE products
            SET name = ?,
                slug = ?,
                description = ?,
                category_id = ?,
                price_per_unit = ?,
                quantity = ?,
                image_url = ?,
                updated_at = ?
            WHERE id = ?
            """;
    public static final String DELETE_SQL = """
            DELETE FROM products
            WHERE id = ?
            """;
}
