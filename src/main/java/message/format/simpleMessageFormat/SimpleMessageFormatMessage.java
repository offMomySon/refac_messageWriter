package message.format.simpleMessageFormat;

import message.Message;

/**
 * 요구사항 분석.
 * 공지 명령어에 따른 메세지 포멧이 필요함. 일반/공지
 *
 * 역할.
 *
 */
public interface SimpleMessageFormatMessage extends Message {
    String create();
}

