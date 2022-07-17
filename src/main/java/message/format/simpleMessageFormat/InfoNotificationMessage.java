package message.format.simpleMessageFormat;

import static message.NoticePrefix.INFO;

/**
 * 역할.
 * notification - info 메세지(view) 를 생성하는 역할.
 */
public class InfoNotificationMessage extends NotificationMessage {
    protected InfoNotificationMessage(String message) {
        super(INFO.with(message));

    }
}
