package ru.itis.hardwarestore.repositories.queries;

public class SessionQueries {
    public static final String SAVE_SQL = """
            INSERT
            INTO sessions (session_id, user_id, expire_at)
            VALUES (?, ?, ?)
            """;
    public static final String FIND_BY_ID_SQL = """
            SELECT *
            FROM sessions
            WHERE session_id = ?
            """;
    public static final String DELETE_SQL = """
            DELETE FROM sessions
            WHERE session_id = ?
            """;
}
