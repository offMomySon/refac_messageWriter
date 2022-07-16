import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import message.Message;
import message.format.simpleMessageFormat.GenericMessage;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 역할 일치?
 * 목적지(=destinatnion) 에 메세지(=view) 를 출력한다. 라는 역할을 수행하고있다.
 *
 * 테스트 suit 복잡성?
 * 메세지가 가지고있어야하는 destination 에 대한 suit 만 존재하기 때문에
 * 복잡하지 않다.
 * 다만 실제 테스트 해야하는 객체를 구성하기 위해 destination 으로 부터 outpustream 과 연결된 map 을 구성해야하고,
 * 출력할 메세지 객체를 생성해야한다.
 *
 * 테스트의 정당성.
 * 오직 객체의 역할인 목적지에 메세지를 전달하는 역할을 검증하기 위한 테스트를 구성했다.
 * 목적지에 따라 메세지가 출력되는지 확인하기 위해 test suit 로 랜덤한 목적지를 가져오고, 목적지중 하나를 선택해서 출력한다.
 * 이때 하나의 목적지에만 메세지가 출력되고, 나머지의 목적지에는 메세지가 출력되지 않아야한다. - 이결과를 테스트 하기위해 테스트 객체를 구성하였고.
 * 테스트가 올바르게 동작함에 따라 객체가 올바르게 동작함을 확인할 수 있다.
 */
class MessageWriterTest {

    @DisplayName("지정한 목적지에 메세지를 출력합니다.")
    @ParameterizedTest
    @MethodSource("provideRandomDestination")
    void test1(Set<Destination> sources){
        //given
        Map<Destination, OutputStream> sourceMap = sources.stream()
            .map(destination -> Map.entry(destination, new ByteArrayOutputStream()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (p1, p2) -> p1));
        Destination target = sources.stream().findAny().orElseThrow(()-> new RuntimeException("not exist Destination"));

        Message message = new GenericMessage("test message.");
        MessageWriter messageWriter = MessageWriter.of(sourceMap);

        messageWriter.write(target, message);

        //when
        List<String> actuals = sources.stream()
            .filter(d-> sourceMap.containsKey(target))
            .map(d -> sourceMap.get(target).toString())
            .collect(Collectors.toUnmodifiableList());

        //then
        Assertions.assertThat(actuals)
            .allMatch(a-> StringUtils.equals(a, message.create()));
    }

    @DisplayName("지정하지 않은 목적지에는 메세지를 출력하지 않습니다.")
    @ParameterizedTest
    @MethodSource("provideRandomDestination")
    void test2(Set<Destination> sources){
        //given
        Map<Destination, OutputStream> sourceMap = sources.stream()
            .map(destination -> Map.entry(destination, new ByteArrayOutputStream()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (p1, p2) -> p1));
        Destination target = sources.stream().findAny().orElseThrow(()-> new RuntimeException("not exist Destination"));

        Message message = new GenericMessage("test message.");
        MessageWriter messageWriter = MessageWriter.of(sourceMap);

        messageWriter.write(target, message);

        //when
        List<String> actuals = sources.stream()
            .filter(s -> ObjectUtils.notEqual(s, target))
            .filter(sourceMap::containsKey)
            .map(d -> sourceMap.get(d).toString())
            .collect(Collectors.toUnmodifiableList());


        //then
        Assertions.assertThat(actuals)
            .allMatch(a-> StringUtils.equals(a, ""));
    }

    @DisplayName("모든 동일한 타입의 목적지에 메세지를 출력합니다.")
    @ParameterizedTest
    @MethodSource("provideRandomDestination")
    void test3(Set<Destination> sources){
        //given
        Map<Destination, OutputStream> sourceMap = sources.stream()
            .map(destination -> Map.entry(destination, new ByteArrayOutputStream()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (p1, p2) -> p2));

        Usage usage = createRandomUsage();
        Message message = new GenericMessage("test message.");
        MessageWriter messageWriter = MessageWriter.of(sourceMap);

        messageWriter.writeAll(usage, message);

        //when
        List<String> actuals = sources.stream()
            .filter(d -> d.getUsage() == usage)
            .filter(sourceMap::containsKey)
            .map(d -> sourceMap.get(d).toString())
            .collect(Collectors.toUnmodifiableList());

        //then
        Assertions.assertThat(actuals)
            .allMatch(a-> StringUtils.equals(a, message.create()));
    }

    @DisplayName("동일한 타입이 아닌 모든 목적지에 메세지를 출력하지 않습니다.")
    @ParameterizedTest
    @MethodSource("provideRandomDestination")
    void test4(Set<Destination> sources){
        //given
        Map<Destination, OutputStream> sourceMap = sources.stream()
            .map(destination -> Map.entry(destination, new ByteArrayOutputStream()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (p1, p2) -> p2));

        Usage usage = createRandomUsage();
        Message message = new GenericMessage("test message.");
        MessageWriter messageWriter = MessageWriter.of(sourceMap);

        messageWriter.writeAll(usage, message);

        //when
        List<String> actuals = sources.stream()
            .filter(d -> d.getUsage() != usage)
            .filter(sourceMap::containsKey)
            .map(d -> sourceMap.get(d).toString())
            .collect(Collectors.toUnmodifiableList());

        //then
        Assertions.assertThat(actuals)
            .allMatch(a-> StringUtils.equals(a, message.create()));
    }

    private static Stream<Arguments> provideRandomDestination() {
        List<Arguments> arguments = new LinkedList<>();

        for(int caseNum =0 ; caseNum< 10; caseNum++){
            Set<Destination> sourceDestination = new HashSet<>();
            for(int testNum = 0; testNum< 200; testNum++){
                sourceDestination.add(new Destination(createRandomAddress(), createRandomUsage()));
            }

            arguments.add(Arguments.of(sourceDestination));
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

        return values[random.nextInt(values.length)%values.length];
    }
}