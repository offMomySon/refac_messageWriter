package strategy;

import common.Destination;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.NonNull;
import message.Message;
import writer.MessageWriter;

public class DestinationSendStrategy implements SendStrategy{
    private final MessageWriter messageWriter;
    private final List<Destination> destinations;
    private final Message message;

    public DestinationSendStrategy(@NonNull MessageWriter messageWriter, @NonNull List<Destination> destinations, @NonNull Message message) {
        this.messageWriter = messageWriter;
        this.destinations = destinations.stream().filter(Objects::nonNull).collect(Collectors.toUnmodifiableList());
        this.message = message;
    }

    @Override
    public void send() {
        destinations
            .forEach(destination -> messageWriter.write(destination, message));
    }
}
