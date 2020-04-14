import java.io.*;
import java.net.Socket;

public class Server implements Runnable {
    private Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void read() throws IOException {
        BufferedReader request = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        BufferedWriter response = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));

        StringBuilder stringBuilder = new StringBuilder();
        String header = "";
        String temp = ".";
        while (!temp.equals("")) {
            temp = request.readLine();
            System.out.println(temp);
            header += temp + "\n";
        }

        String imageName = header.split("\n")[0].split(" ")[1];
        String getDataFromClient = "";
        if (header.split("\n")[0].contains("GET")
                && isImageExists(imageName)) {

            response.write(getFileName(imageName));
            response.flush();
            System.out.println(imageName);

        } else if (header.split("\n")[0].contains("POST")) {
            temp = request.readLine();
            getDataFromClient += temp + "\n";
            responseCode(stringBuilder, 200);
            createNewImageFile(getDataFromClient);
            response.write(stringBuilder.toString());
            response.write(getDataFromClient);
            response.flush();
            stringBuilder.setLength(0);
            System.out.println(getDataFromClient);
        } else {
            responseCode(stringBuilder, 404);
            response.write(stringBuilder.toString());
            response.flush();
        }

        request.close();
        response.close();
        socket.close();
    }

    /**
     * Получаем название файла
     *
     * @param fileName имя файла
     * @return
     */
    private String getFileName(String fileName) {
        File file = new File("/Volumes/MacSklad/" + fileName);
        return file.getName();
    }

    /**
     * Проверяем на существования файла
     *
     * @param imageName имя файла
     * @return
     */
    private boolean isImageExists(String imageName) {
        File file = new File("/Volumes/MacSklad/" + imageName);
        return file.exists() && !file.isDirectory();
    }

    /**
     * Формируем ответ
     *
     * @param stringBuilder
     * @param code
     */
    public void responseCode(StringBuilder stringBuilder, int code) {
        switch (code) {
            case 200:
                stringBuilder.append("HTTP/1.1 200 OK\r\n");
                stringBuilder.append("Date:" + "today" + "\r\n");
                stringBuilder.append("Server:localhost\r\n");
                stringBuilder.append("Content-Type: application/json\r\n");
                stringBuilder.append("Connection: Keep-Alive\r\n\r\n");
                break;
            case 404:
                stringBuilder.append("HTTP/1.1 404 Not Found\r\n");
                stringBuilder.append("Date:" + "today" + "\r\n");
                stringBuilder.append("Server:localhost\r\n");
                stringBuilder.append("\r\n");
        }
    }

    /**
     * Создаем новый файл если такого нет
     *
     * @param fileName
     * @throws IOException
     */
    private void createNewImageFile(String fileName) throws IOException {
        File myFile = new File("/Volumes/MacSklad/" + fileName);
        if (!myFile.exists() && !myFile.isDirectory()) {
            myFile.createNewFile();
        }
    }
}
