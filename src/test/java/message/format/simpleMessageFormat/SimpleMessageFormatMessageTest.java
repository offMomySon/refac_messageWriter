package message.format.simpleMessageFormat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

abstract class SimpleMessageFormatMessageTest {
    @DisplayName("blank, null, empty 이면 메세지가 생성 되지 않습니다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void test(String sMessage){
        //given
        //when
        Exception actual = Assertions.catchException(() -> createMessage(sMessage));

        //then
        Assertions.assertThat(actual).isNotNull();
    }

    @DisplayName("올바른 string 값이면 메세지 객체가 생성됩니다.")
    @ParameterizedTest
    @ValueSource(strings = {"test message","not empty message", "  not blank message"})
    void test1(String sMessage){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()->createMessage(sMessage));

        //then
        Assertions.assertThat(actual).isNull();
    }

    @DisplayName("일반 메세지 포멧을 가진 메세지를 생성합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"test message","not empty message", "  not blank message"})
    void test2(String sMessage){
        //given
        SimpleMessageFormatMessage simpleMessage = createMessage(sMessage);

        //when
        String actual = simpleMessage.create();

        //then
        Assertions.assertThat(actual).containsPattern(getCode() + ":");
    }

    protected abstract SimpleMessageFormatMessage createMessage(String sMessage);

    protected abstract String getCode();
}
