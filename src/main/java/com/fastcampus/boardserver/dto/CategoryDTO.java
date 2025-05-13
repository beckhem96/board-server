package com.fastcampus.boardserver.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    public enum sortStatus {
        CATEGORIES, NEWEST, OLDEST
    }
    private int id;
    private String name;
    private sortStatus sortStatus;
    private int searchCount;
    private int paggingStartOffset;
}
