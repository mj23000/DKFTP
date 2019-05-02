package FTP;

import java.io.*;
import java.net.*;
import java.util.*;

public class FTP
{

    Socket control;
    PrintStream output;
    BufferedReader input;
    
    // Read and print reply from server
    private String readReply() throws IOException
    {
        while (true)
        {
            String s = input.readLine();
            System.out.println("RECEIVED: " + s);
            if (s.length() >= 3 && s.charAt(3) != '-' && Character.isDigit(s.charAt(0))
                    && Character.isDigit(s.charAt(1)) && Character.isDigit(s.charAt(2)))
            {
                return s;
            }
        }
    }

    // Send command to server and print reply
    public String sendCommand(String command) throws IOException
    {
        System.out.println("SENT: " + command);
        output.println(command);
        output.flush();
        return readReply();
    }
    
    // Connect to server with host IP, username and password
    public void connect(String host, String username, String password) throws IOException
    {
        control = new Socket(host, 21);
        output = new PrintStream(control.getOutputStream());
        input = new BufferedReader(new InputStreamReader(control.getInputStream()));
        readReply();
        sendCommand("USER " + username);
        sendCommand("PASS " + password);
    }
    
    // Initialize data connection
    private Socket getDataconnection() throws IOException
    {
        String IPadressAndPort = sendCommand("PASV");
        StringTokenizer st = new StringTokenizer(IPadressAndPort, "(,)");
        if (st.countTokens() < 7)
        {
            throw new IOException("Not logged in!");
        }
        for (int i = 0; i < 5; i++)
        {
            st.nextToken();
        }
        int port = 256 * Integer.parseInt(st.nextToken()) + Integer.parseInt(st.nextToken());
        return new Socket(control.getInetAddress(), port);
    }

    // Upload text file to server
    public void sendText(String command, String data) throws IOException
    {
        Socket df = getDataconnection();
        PrintStream dataOutput = new PrintStream(df.getOutputStream());
        sendCommand(command);
        dataOutput.print(data);
        dataOutput.close();
        df.close();
        readReply();
    }

    // Download text file from server and print first 1 KB received
    public String receiveText(String command) throws IOException
    {
        Socket df = getDataconnection();
        BufferedReader dataInput = new BufferedReader(new InputStreamReader(df.getInputStream()));
        sendCommand(command);
        StringBuilder sb = new StringBuilder();
        String s = dataInput.readLine();
        while (s != null)
        {
            System.out.println("DATA: " + s);
            sb.append(s + "\n");
            s = dataInput.readLine();
        }
        dataInput.close();
        df.close();
        readReply();

        s = sb.toString();

        if (s.length() > 1024)
        {
            return s.substring(0, 1024);
        } else
        {
            return s;
        }
    }
    
    // Download text file from server and save as a local file
    public void receiveText(String command, String filename) throws Exception
    {
        Socket df = getDataconnection();
        BufferedReader dataInput = new BufferedReader(new InputStreamReader(df.getInputStream()));
        sendCommand(command);
        StringBuilder sb = new StringBuilder();
        String s = dataInput.readLine();
        while (s != null)
        {
            sb.append(s + "\n");
            s = dataInput.readLine();
        }
        dataInput.close();
        df.close();
        readReply();

        try (PrintStream out = new PrintStream(new FileOutputStream(filename)))
        {
            out.print(sb.toString());
        }
    }

}
