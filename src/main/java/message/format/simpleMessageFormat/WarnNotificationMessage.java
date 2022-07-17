package message.format.simpleMessageFormat;

import message.NoticePrefix;

/**
 * 역할
 * notification - warn 메세지(view) 를 생성하는 역할.
 */
public class WarnNotificationMessage extends NotificationMessage{
    protected WarnNotificationMessage(String message) {
        super(NoticePrefix.WARN.with(message));
    }
}
