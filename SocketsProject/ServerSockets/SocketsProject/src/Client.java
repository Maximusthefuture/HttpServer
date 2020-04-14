
//import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    /**
     * Метод для GET запроса(получения картинки)
     *
     * @param host по умолчанию localhost
     * @param port номер порта
     * @param file имя картинки которую ищем
     * @throws IOException
     */
    public static void getMethod(String host, int port, String file)
            throws IOException {

        Socket clientSocket = new Socket(host, port);

        PrintWriter request = new PrintWriter(clientSocket.getOutputStream(),
                true);

        BufferedReader response = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));

        request.print("GET /" + file + " HTTP/1.1\r\n"); // "+path+"
        request.print("Host: " + host + "\r\n");
        request.print("Connection: close\r\n");
        request.print("Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n");
        request.print("\r\n");
        request.flush();

        String responseLine;
        while ((responseLine = response.readLine()) != null) {
            System.out.println(responseLine);
        }

        response.close();
        request.close();
        clientSocket.close();
    }

    /**
     * Метод для POST запроса
     *
     * @param host по умолчанию localhost
     * @param port номер порта
     * @param file имя картинки которую ищем
     * @throws UnknownHostException
     * @throws IOException
     */
    public static void postMethod(String host, int port, String file)
            throws UnknownHostException, IOException {

        Socket clientSocket  = new Socket(host, port);

        PrintWriter request = new PrintWriter(clientSocket.getOutputStream(),
                true);
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        BufferedReader response = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));

        wr.write("POST " + file + " HTTP/1.1\r\n"); // "+path+"
        wr.write("Host: " + host + "\r\n");
        wr.write("Accept-Language: en-us\r\n");
        wr.write("Connection: Keep-Alive\r\n");
        wr.write("Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n");
        wr.write("Content-type: image/jpeg\r\n");//application/json
        wr.write("Content-Length: " + file.length() + "\r\n");
        wr.write("\r\n");

//        wr.write(String.valueOf(jsonObject));
        wr.flush();

        request.println(file);
        request.flush();

        String responseLine;
        while ((responseLine = response.readLine()) != null) {
            System.out.println(responseLine);
        }

        request.close();
        response.close();
        clientSocket.close();
    }
}
