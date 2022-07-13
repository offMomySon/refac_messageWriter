import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;


/**
 * 역할일치?
 * address 에서 메세지 출력위치(file, socket) 를 가리키기 위한 vo 객체이다.
 * vo 객체이기 때문에 어떤 역할이 있지 않고 값으로써 다루어 진다.
 *
 * test suit 복잡도?
 * vo 객체는 값으로 다루어지는 객체이기 때문에 기능에 대한 테스트가 없기 때문에 복잡도가 높지않다.
 * 객체간의 연산( equal, hashcode ) 이 올바른지 테스트 하면 된다.
 *
 * 테스트 코드의 이상한점?
 * vo 객체간의 연산에 대한 테스트를 통해
 * 연산을 보장하기 때문에 올바른 테스트입니다.
 */
class DestinationTest {

    @DisplayName("같은 값이라면 eqaul 연산시 true 를 반환합니다.")
    @ParameterizedTest
    @MethodSource("provideAddressAndUsage")
    void test1(Address address, Usage usage){
        //given
        Destination destination1 = new Destination(address, usage);
        Destination destination2 = new Destination(address, usage);

        //when
        boolean actual = Objects.equals(destination1, destination2);

        //then
        Assertions.assertThat(actual).isTrue();
    }

    @DisplayName("다른 값이면 equal 연산시 false 를 반환합니다.")
    @ParameterizedTest
    @CsvSource(value = {
        "1.1.1.1, 1.1.1.1, file, socket",
        "1.1.1.1, 1.1.1.2, file, socket",
        "1.1.1.1, 1.1.1.3, file, socket",
        "1.1.1.1, 1.1.1.4, file, socket",
        "1.1.1.1, 2.1.1.1, file, file",
        "1.1.1.1, 3.1.1.1, file, file",
        "1.1.1.1, 4.1.1.1, file, file",
        "1.1.1.1, 5.1.1.1, file, file",
    })
    void test2(String sAddress1, String sAddress2, String usage1, String usage2){
        //given
        Destination destination1 = new Destination(new Address(sAddress1), Usage.find(usage1).orElseThrow(()-> new RuntimeException("can not find usage")));
        Destination destination2 = new Destination(new Address(sAddress2), Usage.find(usage2).orElseThrow(()-> new RuntimeException("can not find usage")));

        //when
        boolean actual = Objects.equals(destination1, destination2);

        //then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("같은 값이라면 hash code 가 일치합니다.")
    @ParameterizedTest
    @MethodSource("provideAddressAndUsage")
    void test3(Address address, Usage usage){
        //given
        Destination destination1 = new Destination(address, usage);
        Destination destination2 = new Destination(address, usage);

        //when
        boolean actual = Objects.equals(destination1, destination2);

        //then
        Assertions.assertThat(actual).isTrue();
    }

    @DisplayName("다른 값이면 hash code 가 일치하지 않습니다.")
    @ParameterizedTest
    @CsvSource(value = {
        "1.1.1.1, 1.1.1.1, file, socket",
        "1.1.1.1, 1.1.1.2, file, socket",
        "1.1.1.1, 1.1.1.3, file, socket",
        "1.1.1.1, 1.1.1.4, file, socket",
        "1.1.1.1, 2.1.1.1, file, file",
        "1.1.1.1, 3.1.1.1, file, file",
        "1.1.1.1, 4.1.1.1, file, file",
        "1.1.1.1, 5.1.1.1, file, file",
    })
    void test4(String sAddress1, String sAddress2, String usage1, String usage2){
        //given
        Destination destination1 = new Destination(new Address(sAddress1), Usage.find(usage1).orElseThrow(()-> new RuntimeException("can not find usage")));
        Destination destination2 = new Destination(new Address(sAddress2), Usage.find(usage2).orElseThrow(()-> new RuntimeException("can not find usage")));

        //when
        boolean actual = Objects.equals(destination1, destination2);

        //then
        Assertions.assertThat(actual).isFalse();
    }

    private static Stream<Arguments> provideAddressAndUsage() {
        List<Arguments> arguments = new LinkedList<>();
        for(int i =0 ; i< 100; i++){
            arguments.add(Arguments.of(createRandomAddress(), createRandomUsage()));
        }
        return arguments.stream();
    }

    private static Address createRandomAddress(){
        Random random = new Random(System.currentTimeMillis());

        String addressValue = IntStream.range(0, 4)
            .mapToObj(n -> String.valueOf(random.nextInt(255)))
            .collect(Collectors.joining("."));

        return new Address(addressValue);
    }

    private static Usage createRandomUsage(){
        Random random = new Random(System.currentTimeMillis());

        Usage[] values = Usage.values();

        System.out.println(random.nextInt()%values.length);

        return values[random.nextInt(values.length)%values.length];
    }
}