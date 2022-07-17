package strategy;

import common.Destinations;
import common.Usage;
import lombok.NonNull;
import message.Message;
import writer.MessageWriter;

/**
 * 모든 메세지
 */
public class AllSendStrategy implements SendStrategy{
    private final MessageWriter messageWriter;
    private final Usage usage;
    private final Message message;

    public AllSendStrategy(@NonNull MessageWriter messageWriter, @NonNull Usage usage, @NonNull Message message) {
        this.messageWriter = messageWriter;
        this.usage = usage;
        this.message = message;
    }

    @Override
    public void send() {
        messageWriter.writeAll(d-> d.getUsage() == usage, message);
        messageWriter.writeAll2(destinations -> destinations.filtered(d-> d.getUsage()== usage), message);
        messageWriter.writeAll3(Destinations.build().filtered(d-> d.getUsage() == usage), message);
        messageWriter.writeAll4(new Destinations.Builder().filtered2(d->d.getUsage() == usage).build(), message);
    }
}
