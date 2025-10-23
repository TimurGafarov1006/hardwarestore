package ru.itis.hardwarestore.repositories.queries;

//language=sql
public class UserQueries {

    public static final String SAVE_SQL = """
            INSERT
            INTO users (id, role, first_name, last_name, password_hash, salt, phone, email, birthday, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
    public static final String FIND_ALL_SQL = """
            SELECT *
            FROM users
            """;
    public static final String FIND_BY_ID_SQL = """
            SELECT *
            FROM users
            WHERE id = ?
            """;
    public static final String FIND_BY_EMAIL_SQL = """
            SELECT *
            FROM users
            WHERE email = ?
            """;
    public static final String UPDATE_SQL = """
            UPDATE users
            SET first_name = ?,
                last_name = ?,
                password_hash = ?,
                salt = ?,
                phone = ?,
                email = ?,
                updated_at = ?
            WHERE id = ?
            """;
    public static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;
    public static final String FIND_BY_PHONE_SQL = """
            SELECT *
            FROM users
            WHERE phone = ?
            """;
}
