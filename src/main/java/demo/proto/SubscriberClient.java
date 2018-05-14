package demo.proto;

import ProjectGrpc.PersonReply;
import ProjectGrpc.PersonRequest;
import ProjectGrpc.TransferGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class SubscriberClient {
    private final ManagedChannel channel;
    private final TransferGrpc.TransferBlockingStub blockingStub;

    public SubscriberClient(String host, int port){
        channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext(true)
                .build();
        blockingStub = TransferGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException{
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void transfer(String name, String need){
        PersonRequest request = PersonRequest.newBuilder().setName(name).setNeed(need).build();
        PersonReply response = blockingStub.informationTo(request);
        System.out.println(response.getMessage());
    }
}
