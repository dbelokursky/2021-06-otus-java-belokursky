package ru.otus.protobuf;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import ru.otus.protobuf.service.NumberService;
import ru.otus.protobuf.service.NumberServiceImpl;
import ru.otus.protobuf.service.RemoteNumberServiceImpl;

import java.io.IOException;

public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {
        NumberService numberService = new NumberServiceImpl();
        RemoteNumberServiceImpl remoteNumberService = new RemoteNumberServiceImpl(numberService);

        Server server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(remoteNumberService)
                .build()
                .start();

        System.out.println("Sever wait for connection");
        server.awaitTermination();
    }
}
