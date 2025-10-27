package ru.itis.hardwarestore.repositories.queries;

public class DiscountCardQueries {
    public static final String SAVE_SQL = """
            INSERT
            INTO discount_cards (id, user_id, card_no, card_type_id, status, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
    public static final String FIND_BY_USER_ID_SQL = """
            SELECT *
            FROM discount_cards
            WHERE user_id = ?
            """;
    public static final String FIND_BY_CARD_NO_SQL = """
            SELECT *
            FROM discount_cards
            WHERE card_no = ?
            """;
    public static final String UPDATE_SQL = """
            UPDATE discount_cards
            SET card_type_id = ?,
                status = ?,
                updated_at = ?
            WHERE id = ?
            """;
    public static final String DELETE_SQL = """
            DELETE FROM discount_cards
            WHERE id = ?
            """;
}
