package ru.otus.protobuf;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ru.otus.protobuf.generated.RangeMessage;
import ru.otus.protobuf.generated.RemoteNumberServiceGrpc;
import ru.otus.protobuf.generated.ValueMessage;

import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final long CLIENT_INTERVAL = 1000;

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        RemoteNumberServiceGrpc.RemoteNumberServiceBlockingStub stub = RemoteNumberServiceGrpc.newBlockingStub(channel);

        RangeMessage rangeMessage = RangeMessage.newBuilder()
                .setFirstValue(0)
                .setLastValue(30)
                .build();

        Deque<ValueMessage> valueMessages = new ConcurrentLinkedDeque<>();
        Iterator<ValueMessage> valueMessageIterator = stub.generateSequence(rangeMessage);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> valueMessageIterator.forEachRemaining(n -> {
            valueMessages.poll();
            valueMessages.addFirst(n);
        }));

        AtomicLong currentValue = new AtomicLong();
        executorService.submit(() -> {
            for (int i = 0; i <= 50; i++) {
                try {
                    Thread.sleep(CLIENT_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ValueMessage valueMessage = valueMessages.poll();
                long numFromServer = valueMessage != null ? valueMessage.getCurrentValue() : 0;
                currentValue.addAndGet(numFromServer + 1);

                System.out.printf("current value: %d %n", currentValue.get());
                System.out.printf("new value: %d %n", numFromServer);
            }
        });
        executorService.shutdown();
    }
}
