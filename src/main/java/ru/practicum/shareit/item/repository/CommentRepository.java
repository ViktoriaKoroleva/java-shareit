package ru.practicum.shareit.item.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByItem(Item item);

    @Query("SELECT c FROM Comment c WHERE c.item IN :items")
    List<Comment> findAllByItems(@Param("items") List<Item> items);

    List<Comment> findByItemIn(List<Item> items, Sort sort);
}
