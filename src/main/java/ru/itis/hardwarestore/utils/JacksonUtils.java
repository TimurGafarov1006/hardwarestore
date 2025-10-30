package ru.itis.hardwarestore.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.itis.hardwarestore.models.CartElement;
import ru.itis.hardwarestore.models.Product;

import java.util.List;

public class JacksonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static String cartElementToJson(CartElement cartElement) {
        try {
            return objectMapper.writeValueAsString(cartElement);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static CartElement jsonToCartElement(String json) {
        try {
            return objectMapper.readValue(json, CartElement.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка десериализации JSON в CartElement. JSON: " + json, e);
        }
    }

    public static String ProductsListToJson(List<Product> products) {
        try {
            return objectMapper.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка сериализации списка ProductsList в JSON", e);
        }
    }

}