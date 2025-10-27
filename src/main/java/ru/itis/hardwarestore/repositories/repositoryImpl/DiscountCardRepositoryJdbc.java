package ru.itis.hardwarestore.repositories.repositoryImpl;

import ru.itis.hardwarestore.models.DiscountCard;
import ru.itis.hardwarestore.models.enums.CardStatus;
import ru.itis.hardwarestore.models.enums.CardType;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.DiscountCardRepository;
import ru.itis.hardwarestore.utils.PropertiesUtil;

import java.sql.*;
import java.util.Optional;
import java.util.Properties;

import static ru.itis.hardwarestore.repositories.queries.DiscountCardQueries.*;

public class DiscountCardRepositoryJdbc implements DiscountCardRepository {
    private final Properties properties;
    private final String url;

    public DiscountCardRepositoryJdbc() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        properties = PropertiesUtil.getProperties();
        url = properties.getProperty("url");
    }

    @Override
    public void save(DiscountCard discountCard) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL))
        {
            statement.setString(1, discountCard.getId());
            statement.setString(2, discountCard.getUserId());
            statement.setString(3, discountCard.getCardNo());
            statement.setInt(4, discountCard.getCardType().getId());
            statement.setString(5, discountCard.getCardStatus().getStatus());
            statement.setObject(6, discountCard.getCreatedAt(), Types.TIMESTAMP);
            statement.setObject(7, discountCard.getUpdatedAt(), Types.TIMESTAMP);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(DiscountCard discountCard) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL))
        {
            statement.setInt(1, discountCard.getCardType().getId());
            statement.setString(2, discountCard.getCardStatus().getStatus());
            statement.setObject(3, discountCard.getUpdatedAt(), Types.TIMESTAMP);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String id) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL))
        {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<DiscountCard> findByCardNo(String cardNo) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CARD_NO_SQL))
        {
            statement.setString(1, cardNo);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                DiscountCard discountCard = new DiscountCard(
                        resultSet.getString("id"),
                        resultSet.getString("user_id"),
                        resultSet.getString("card_no"),
                        CardType.fromId(resultSet.getInt("card_type_id")),
                        CardStatus.valueOf(resultSet.getString("status")),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at").toLocalDateTime()
                );

                return Optional.ofNullable(discountCard);
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<DiscountCard> findByUserId(String userId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_BY_USER_ID_SQL))
        {
            statement.setString(1, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                DiscountCard discountCard = new DiscountCard(
                        resultSet.getString("id"),
                        resultSet.getString("user_id"),
                        resultSet.getString("card_no"),
                        CardType.fromId(resultSet.getInt("card_type_id")),
                        CardStatus.valueOf(resultSet.getString("status")),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at").toLocalDateTime()
                );

                return Optional.ofNullable(discountCard);
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}
