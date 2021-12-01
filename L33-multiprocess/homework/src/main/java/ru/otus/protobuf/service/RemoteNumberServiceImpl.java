package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.RangeMessage;
import ru.otus.protobuf.generated.RemoteNumberServiceGrpc;
import ru.otus.protobuf.generated.ValueMessage;
import ru.otus.protobuf.model.Range;
import ru.otus.protobuf.model.Value;

import java.util.List;

public class RemoteNumberServiceImpl extends RemoteNumberServiceGrpc.RemoteNumberServiceImplBase {
    private static final long INTERVAL = 2000;
    private final NumberService numberService;

    public RemoteNumberServiceImpl(NumberService numberService) {
        this.numberService = numberService;
    }

    @Override
    public void generateSequence(RangeMessage request, StreamObserver<ValueMessage> responseObserver) {
        Range range = rangeMessageToRange(request);
        List<Value> values = numberService.getSequence(range);

        values.forEach(n -> {
            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(ValueMessage.newBuilder().setCurrentValue(n.getCurrentValue()).build());
        });
        responseObserver.onCompleted();
    }

    private Range rangeMessageToRange(RangeMessage rangeMessage) {
        return new Range(rangeMessage.getFirstValue(), rangeMessage.getLastValue());
    }

}
