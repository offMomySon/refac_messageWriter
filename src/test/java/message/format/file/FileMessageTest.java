package message.format.file;

import java.time.LocalDateTime;
import java.util.regex.Pattern;
import message.Subject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * 역할일치? - no.
 * String actual = fileMessage.create(LocalDateTime.now());
 *
 * fileMessage 객체가 가 메세지를 생성한다.
 * create() 시점마다 datetime 을 인자로 받기 때문에 view 가 변하게 된다.
 *
 * 이것은 객체가 역할을 수행하기 위해, 내부 instance 의 조합을 통해 일관적인 view 를 생성하는 것이 아니기 때문에
 * 생성 시점마다 view 가 달라지는 가변적인 성질을 가진다.
 *
 * 이것은 사용성 측면에서 좋을 수 있지만,
 * 객체로 보기 보다는 범용적 기능을 가진 구현체로 볼 수 있다.
 *
 */
class FileMessageTest {
    @DisplayName("blank, null, empty 이면 메세지가 생성 되지 않습니다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void test(String sMessage){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()-> new FileMessage(Subject.CLIENT, sMessage));

        //then
        Assertions.assertThat(actual).isNotNull();
    }

    @DisplayName("올바른 message 이면 객채가 생성됩니다.")
    @ParameterizedTest
    @ValueSource(strings = {"test message","not empty message", "  not blank message"})
    void test1(String sMessage){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()-> new FileMessage(Subject.CLIENT, sMessage));

        //then
        Assertions.assertThat(actual).isNull();
    }

    @DisplayName("file message 포멧을 가진 메세지를 생성합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"test message","not empty message", "  not blank message"})
    void test2(String sMessage){
        //given
        FileMessage fileMessage = new FileMessage(Subject.CLIENT, sMessage);

        //when
        String actual = fileMessage.create(LocalDateTime.now());

        //then
        Assertions.assertThat(actual).containsPattern(Pattern.compile("\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) \\S* \\s*"));
    }
}