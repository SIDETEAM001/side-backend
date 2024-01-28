package com.sideteam.groupsaver.global.websocket;

import com.sideteam.groupsaver.global.exception.BusinessException;
import com.sideteam.groupsaver.global.exception.websocket.WebSocketErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.Map;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebSocketUtils {

    public static final String IDENTITY = "identity";
    public static final String DEFAULT_CLUB_CHAT_PATH = "/chatroom/clubs/";


    public static <T> T getValue(StompHeaderAccessor accessor, String key) {
        Map<String, Object> sessionAttributes = getSessionAttributes(accessor);
        Object value = sessionAttributes.get(key);

        if (Objects.isNull(value)) {
            throw new BusinessException(WebSocketErrorCode.SESSION_IS_NULL, key + " : not found");
        }

        try {
            return (T) value;
        } catch (Exception exception) {
            throw new BusinessException(WebSocketErrorCode.FAILED_CONVERT, key);
        }
    }

    public static void setValue(StompHeaderAccessor accessor, String key, Object value) {
        Map<String, Object> sessionAttributes = getSessionAttributes(accessor);
        sessionAttributes.put(key, value);
    }


    public static Map<String, Object> getSessionAttributes(StompHeaderAccessor accessor) {
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();

        if (Objects.isNull(sessionAttributes)) {
            throw new BusinessException(WebSocketErrorCode.SESSION_IS_NULL, "session is null");
        }
        return sessionAttributes;
    }


}
