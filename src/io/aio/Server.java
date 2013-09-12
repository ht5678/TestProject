package io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * jdk7----aio
 * @author 岳志华
 *
 */
public class Server {

//	private AsynchronousServerSocketChannel server;  
//    
//    public Server()throws IOException{  
//        server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8888));  
//    }  
//      
//    public void start() throws InterruptedException, ExecutionException, TimeoutException{  
//        Future<AsynchronousSocketChannel> future = server.accept();  
//        AsynchronousSocketChannel socket = future.get();  
//         
//        ByteBuffer readBuf = ByteBuffer.allocate(1024);  
//        socket.read(readBuf).get(100, TimeUnit.SECONDS);  
//          
//        System.out.printf("Receiver:%s%n",new String(readBuf.array()));  
//    }  
//      
//    public static void main(String args[]) throws Exception{  
//        new Server().start();  
//    }  
}
