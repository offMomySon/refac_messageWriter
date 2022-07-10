package message.format.simpleMessageFormat;

import lombok.NonNull;
import message.NoticeType;
import static message.NoticeType.INFO;

/**
 * 역할.
 * notification - info 메세지(view) 를 생성하는 역할.
 */
public class InfoMessage extends NotificationMessage {
    protected InfoMessage(String message) {
        super(PrefixMessage.of(INFO), message);
    }
}
