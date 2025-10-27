package ru.itis.hardwarestore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
public class Category {
    private int id;
    private String name;
    private Integer parentId;
    private String slug;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
