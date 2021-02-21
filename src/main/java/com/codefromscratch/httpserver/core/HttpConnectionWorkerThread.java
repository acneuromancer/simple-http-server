package com.codefromscratch.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private Socket socket;

    HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            int _byte;
            while ((_byte = inputStream.read()) >= 0) {
                System.out.print((char) _byte);
            }

            String html = "<html><head><title>Simple Java Http Server</title></head><body><h1>This page was served using my Simple Java HTTP Server</h1></body></html>";
            final String CRLF = "\n\r"; // ACII 13, 10

            String response= "HTTP/1.1 200 OK" + CRLF // Status line: HTTP version response_code response_message
                    + "Content-Length: " + html.getBytes().length + CRLF // HEADER
                    + CRLF
                    + html
                    + CRLF + CRLF;
            outputStream.write(response.getBytes());

           /* try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            LOGGER.info("Connection processing finished.");
        } catch (IOException e) {
            LOGGER.error("Problem with communication" ,e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
