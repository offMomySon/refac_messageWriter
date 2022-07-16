import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.NonNull;
import message.Message;

/**
 * 역할.
 * message(view) 를 대상에게 출력하는 역할.
 */
public class MessageWriter {
    private final Map<Destination, BufferedWriter> outputStreamMap;

    public MessageWriter(){
        this.outputStreamMap = new HashMap<>();
    }
    private MessageWriter(@NonNull Map<Destination, BufferedWriter> outputStreamMap) {
        this.outputStreamMap = outputStreamMap;
    }

    public static MessageWriter of(@NonNull Map<Destination, OutputStream> outputStreamMap){
        Map<Destination, BufferedWriter> sourceMap = outputStreamMap.entrySet().stream()
            .filter(e -> Objects.nonNull(e.getKey()) && Objects.nonNull(e.getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, e -> new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(e.getValue(), 8192)), 8192)));

        return new MessageWriter(sourceMap);
    }

    public void write(Destination destination, Message message) {
        write(outputStreamMap.get(destination), message);
    }

    public void writeAll(Usage usage, Message message){
        outputStreamMap.keySet().stream()
            .filter(d-> d.getUsage() == usage)
            .forEach(d -> write(d, message));
    }

    private static void write(BufferedWriter writer, Message message){
        try {
            writer.write(message.create());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Fail to write message");
        }
    }
}
