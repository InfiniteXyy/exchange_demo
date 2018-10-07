package demo.proto;

import ProjectGrpc.PersonReply;
import ProjectGrpc.PersonRequest;
import ProjectGrpc.TransferGrpc;
import demo.PersonSubs;
import demo.SQLconnecter;
import demo.SocketServer;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class SubscriberServer {
    private int  port = 50051;
    private Server server;

    private void start() throws IOException{
        server = ServerBuilder.forPort(port)
                .addService(new TransferImpl())
                .build()
                .start();
        System.out.println("service start...");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            SubscriberServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop(){
        if(server != null){
            server.shutdown();
        }
    }

    private void blockUtillShutdown() throws InterruptedException{
        if(server != null){
            server.awaitTermination();
        }
    }

    public static void main(String[] a)throws IOException,InterruptedException{
        final SubscriberServer server = new SubscriberServer();
        server.start();
        server.blockUtillShutdown();
    }

    private class TransferImpl extends TransferGrpc.TransferImplBase{
        public void informationTo (PersonRequest req , StreamObserver<PersonReply> responseObserver){
            // 在这里把数据上传到数据库（MySQL/SQL server）
            SQLconnecter.addToDB(new PersonSubs(req.getName(), req.getNeed()));
            SocketServer.shops.add(req.getNeed());
            PersonReply reply =  PersonReply.newBuilder().setMessage("数据成功已接受").build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}
