package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    private Long id;

    private Long userId;

    private Long itemId;

    private String text;

    private LocalDateTime created;

    private String authorName;

}
