package lib.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Finn Frankis
 * @version Aug 14, 2018
 */
public class SocketReader {
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static BufferedReader socketStream;

    public static void makeConnection () {
        try {
            System.out.println("INSTANTIATING SERVER SOCKETO");
            serverSocket = new ServerSocket(Constants.SOCKET_PORT);
            System.out.println("FORMING SOCKET CONNECTION");
            SocketReader.socket = serverSocket.accept();
            System.out.println("SOCKET CONNECTION FORMED");
            socketStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isConnected () {
        return socket != null && socket.isBound();
    }

    public static String getValue ()
    {
        System.out.println("CHECKING FOR VAL");
        try {
            if (socketStream != null)
                return socketStream.ready() ? socketStream.readLine() : "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("null stream");
        return "";
    }

}
