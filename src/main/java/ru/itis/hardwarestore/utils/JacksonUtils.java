package ru.itis.hardwarestore.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.hardwarestore.models.CartElement;

public class JacksonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

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
            throw new RuntimeException(e);
        }
    }
}