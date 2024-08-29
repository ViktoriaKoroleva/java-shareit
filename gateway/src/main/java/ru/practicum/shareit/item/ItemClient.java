package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentAddDto;
import ru.practicum.shareit.item.dto.ItemAddDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> addItem(long userId, ItemAddDto itemDto) {
        return sendPostRequest("", userId, itemDto);
    }

    public ResponseEntity<Object> updateItem(long userId, int itemId, ItemUpdateDto patchDto) {
        return sendPatchRequest("/" + itemId, userId, patchDto);
    }

    public ResponseEntity<Object> getAllItems(long userId) {
        return sendGetRequest("", userId);
    }

    public ResponseEntity<Object> getItemById(long itemId, long userId) {
        return sendGetRequest("/" + itemId, userId);
    }

    public ResponseEntity<Object> getItemsByText(String text) {
        return sendGetRequest("/?text={text}", null, Map.of("text", text));
    }

    public ResponseEntity<Object> postCommentOnItem(long userId, long itemId, CommentAddDto commentAddDto) {
        return sendPostRequest("/" + itemId + "/comment", userId, commentAddDto);
    }
}
