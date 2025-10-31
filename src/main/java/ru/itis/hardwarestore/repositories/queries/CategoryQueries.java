package ru.itis.hardwarestore.repositories.queries;

public class CategoryQueries {
    public static final String SAVE_SQL = """
            INSERT
            INTO categories (name, parent_id, slug, image_url, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
    public static final String FIND_ALL_SQL = """
            SELECT *
            FROM categories
            """;
    public static final String FIND_BY_PARENT_ID_SQL = """
            SELECT *
            FROM categories
            WHERE parent_id = ?
            """;
    public static final String FIND_BY_SLUG_SQL = """
            SELECT *
            FROM categories
            WHERE slug = ?
            """;
    public static final String FIND_BY_PARENT_ID_IS_NULL_SQL = """
            SELECT *
            FROM categories
            WHERE parent_id IS NULL
            """;
    public static final String UPDATE_SQL = """
            UPDATE categories
            SET name = ?,
                parent_id = ?,
                slug = ?,
                image_url = ?,
                updated_at = ?
            WHERE id = ?
            """;
    public static final String DELETE_SQL = """
            DELETE FROM categories
            WHERE id = ?
            """;
    public static final String FIND_BY_ID_SQL = """
            SELECT *
            FROM categories
            WHERE id = ?
            """;
}
