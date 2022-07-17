package message.format.simpleMessageFormat;

import message.NoticePrefix;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

abstract class NotificationMessageTest extends SimpleMessageFormatMessageTest{

    @DisplayName("notice prefix 포멧의 메세지를 생성합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"test message","not empty message", "  not blank message"})
    void test3(String sMessage){
        //given
        SimpleMessageFormatMessage message = createMessage(sMessage);

        //when
        String actual = message.create();

        //then
        Assertions.assertThat(actual)
            .contains(getNoticePrefix().with(sMessage));
    }

    @Override
    protected String getCode() {
        return "1";
    }

    protected abstract NoticePrefix getNoticePrefix();
}
