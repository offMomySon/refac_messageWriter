package message.format.simpleMessageFormat;

import lombok.NonNull;
import message.NoticeType;

/**
 * 역할
 * notification - warn 메세지(view) 를 생성하는 역할.
 */
public class WarnMessage extends NotificationMessage{
    protected WarnMessage(String message) {
        super(PrefixMessage.of(NoticeType.WARN), message);
    }
}
