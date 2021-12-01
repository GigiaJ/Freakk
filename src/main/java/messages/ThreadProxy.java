/**
 * @author		GigiaJ
 * @filename	ThreadProxy.java
 * @date		Mar 27, 2020
 * @description 
 */
package messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

class ThreadProxy extends Thread {
    private Socket sClient;
    private final String SERVER_URL;
    private final int SERVER_PORT;

    ThreadProxy(Socket sClient, String ServerUrl, int ServerPort) {
        this.SERVER_URL = ServerUrl;
        this.SERVER_PORT = ServerPort;
        this.sClient = sClient;
        this.start();
    }

    public void run() {
        try {
            final byte[] request = new byte[1024];
            byte[] reply = new byte[4096];
            final InputStream inFromClient = this.sClient.getInputStream();
            OutputStream outToClient = this.sClient.getOutputStream();
            Socket client = null;
            Socket server = null;

            try {
                server = new Socket(this.SERVER_URL, this.SERVER_PORT);
            } catch (IOException var21) {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(outToClient));
                out.flush();
                throw new RuntimeException(var21);
            }

            InputStream inFromServer = server.getInputStream();
            final OutputStream outToServer = server.getOutputStream();
            (new Thread() {
                public void run() {
                    while(true) {
                        try {
                            int bytes_read;
                            if ((bytes_read = inFromClient.read(request)) != -1) {
                                outToServer.write(request, 0, bytes_read);
                                outToServer.flush();
                                continue;
                            }
                        } catch (IOException var4) {
                        }

                        try {
                            outToServer.close();
                        } catch (IOException var3) {
                            var3.printStackTrace();
                        }

                        return;
                    }
                }
            }).start();

            try {
                int bytes_read;
                try {
                    while((bytes_read = inFromServer.read(reply)) != -1) {
                        if (!(new String(request, "UTF-8")).contains("\u0003")) {
                            System.out.println(new String(request, "UTF-8"));
                        }

                        outToClient.write(reply, 0, bytes_read);
                        outToClient.flush();
                    }
                } catch (IOException var22) {
                    var22.printStackTrace();
                }
            } finally {
                try {
                    if (server != null) {
                        server.close();
                    }

                    if (client != null) {
                        ((Socket)client).close();
                    }
                } catch (IOException var20) {
                    var20.printStackTrace();
                }

            }

            outToClient.close();
            this.sClient.close();
        } catch (IOException var24) {
            var24.printStackTrace();
        }

    }

    String byteArrayToString(byte[] in) {
        char[] out = new char[in.length * 2];

        for(int i = 0; i < in.length; ++i) {
            out[i * 2] = "0123456789ABCDEF".charAt(in[i] >> 4 & 15);
            out[i * 2 + 1] = "0123456789ABCDEF".charAt(in[i] & 15);
        }

        return new String(out);
    }
}