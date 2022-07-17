package message;

import java.text.MessageFormat;
import lombok.Getter;

/**
 * 역할
 * noticeType 의 type 정의와 prefix 값 정의.
 */
@Getter
public enum NoticePrefix {
    INFO("\\u001B[33m[INFO]\\u001B[0m"), WARN("\\u001B[31m[WARN]\\u001B[0m");

    private static final String NOTICE_MESSAGE_FORMAT = "{0} {1}";
    private final String value;

    NoticePrefix(String value) {
        this.value = value;
    }

    public String with(String message){
        return MessageFormat.format(NOTICE_MESSAGE_FORMAT, value, message);
    }
}
