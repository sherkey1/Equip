/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equip;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

//    public static void main(String[] args) throws Exception
//    {
//        new Listener().start();
//    }
/**
 *
 * @author 3
 */
public class Listener extends Thread {

    String data = null;
    PrintStream out;
    BufferedReader reader;
    Socket client;

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(8888);
            while (true) {
                client = server.accept();
                out = new PrintStream(client.getOutputStream());
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));


                data = reader.readLine();


                if (isConfirmed()) {
                    out.println("allow");
                } else {
                    out.println("deny");
                }
                close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isConfirmed() {
        String info = data;
        int optionType = JOptionPane.OK_CANCEL_OPTION;
        int messageType = JOptionPane.WARNING_MESSAGE;
        int result = JOptionPane.showConfirmDialog(null, info, "增加数据请求！！", optionType);
        return result == JOptionPane.OK_OPTION;

    }

    private void close() throws IOException {
        out.close();
        reader.close();
        client.close();
    }
}
