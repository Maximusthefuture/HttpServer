
import java.io.IOException;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 8080;
        String string = "{\"id\":1,\"method\":\"object.deleteAll\",\"params\":[\"subscriber\"]}";

        Scanner scanner = new Scanner(System.in);

        String command = "";
        while (true) {
            if (scanner.hasNextLine()) {
                command = scanner.nextLine();
                if (command.startsWith("/")) {
                    Client.getMethod(host, port, command.substring(1));
                } else {
                    Client.postMethod(host, port, command);
                }
                if (command.equals("exit")) {
                    System.exit(0);
                }
            }
        }
    }
}
