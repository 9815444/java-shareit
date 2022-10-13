package ru.practicum.shareit.item;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import javax.transaction.Transactional;

@SpringBootTest
class ItemServiceImplTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository1 itemRepository1;

//
//    ItemServiceImplTest(ItemService itemService) {
//        this.itemService = itemService;
//    }

    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Transactional
    @Test
    void postComment() {
        CommentDto commentDto = new CommentDto("text");
        CommentDto comment = itemService.addComment(Long.valueOf(1), Long.valueOf(3), commentDto);
    }

    @Transactional
    @Test
    void postCommentWrongUser() {
        CommentDto commentDto = new CommentDto("text");
        CommentDto comment = itemService.addComment(Long.valueOf(100), Long.valueOf(3), commentDto);
    }

    @Transactional
    @Test
    void getItemWithComments() {
        CommentDto commentDto = new CommentDto("text");
        CommentDto comment = itemService.addComment(Long.valueOf(1), Long.valueOf(2), commentDto);

        CommentDto commentDto1 = new CommentDto("text2");
        CommentDto comment1 = itemService.addComment(Long.valueOf(1), Long.valueOf(2), commentDto1);

        Item item = itemService.findByUser(Long.valueOf(2), Long.valueOf(1));

    }
}