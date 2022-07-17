package message.format.file;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import message.Subject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * v1
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

/**
 * v2
 *
 * 역할일치?
 * 객체의 create 메서드를 통해 일관성을 가진, file message 포멧의 메세지(view) 를 생성한다.
 *
 * test suit 복잡도?
 * fileMessage 생성에 필요한 subject, message 만을 전달하고 있어 복잡도가 높지 않다.
 *
 * 테스트 코드의 이상한점?
 * 의도한 메세지 view 가 생성되는것을 확인하기 위해, DateTimeFormatter 를 테스트 코드에 작성하였다.
 * DateTimeFormatter MESSAGE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
 *
 * 객체가 생성하는 message(=view) 와 동일한 방식으로, 테스트 코드에서 비교용 view 데이터를 생성한다.
 * 객체의 역할이 view 생성 이기 때문에, view 결과물을 비교할 다른 방법은 없을것 같다.
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
        Throwable actual = Assertions.catchThrowable(()-> new FileMessage(LocalDateTime.now(), Subject.CLIENT, sMessage));

        //then
        Assertions.assertThat(actual).isNotNull();
    }

    @DisplayName("올바른 message 이면 객채가 생성됩니다.")
    @ParameterizedTest
    @ValueSource(strings = {"test message","not empty message", "  not blank message"})
    void test1(String sMessage){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()-> new FileMessage(LocalDateTime.now(), Subject.CLIENT, sMessage));

        //then
        Assertions.assertThat(actual).doesNotThrowAnyException();
    }

    @DisplayName("file message 포멧을 가진 메세지를 생성합니다.")
    @ParameterizedTest
    @MethodSource("provideMessageFormatTestSuite")
    void test2(Subject subject, String sMessage){
        //given
        LocalDateTime now = LocalDateTime.now();
//        Subject subject = Subject.find(sSubject).orElseThrow(()-> new RuntimeException("not exist"));

        FileMessage fileMessage = new FileMessage(now, subject, sMessage);

        //when
        String actual = fileMessage.create();

        //then
        DateTimeFormatter MESSAGE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        Assertions.assertThat(actual)
            .contains(MESSAGE_TIME_FORMAT.format(now))
            .contains(subject.getValue())
            .contains(sMessage);
    }

    private static Stream<Arguments> provideMessageFormatTestSuite(){
        return Stream.of(Subject.values())
            .flatMap(s-> Stream.of("test mssage", "not blank message")
                .map(m-> Arguments.of(s,m)) );
    }

}
