import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;
import java.util.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class AuctionServer implements Runnable {

    public static final int BASE_PORT = 2000;

    private static ServerSocket serverSocket;
    private static int socketNumber;
    public StockItemsDB details = null;
    private Socket connectionSocket;
    private StockItemsDB stocks;
    public ArrayList<Double> bids;
    public int no_of_bids;
    public String clientName, symbol;
    public boolean newbid = false;


    public static ArrayList<AuctionServer> clients = new ArrayList<>();

    public AuctionServer(StockItemsDB newStock) throws IOException {
        serverSocket = new ServerSocket(BASE_PORT);
        this.details = newStock;
    }

    public AuctionServer(Socket socket, StockItemsDB stocks) throws IOException {
        this.connectionSocket = socket;
        bids = new ArrayList<>();
        this.stocks = stocks;
    }

    public void server_loop() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            AuctionServer temp = new AuctionServer(socket, details);
            clients.add(temp);
            Thread worker = new Thread(temp);
            worker.start();
        }
    }

    public void run() {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream())); //input stream
            PrintWriter out = new PrintWriter(new OutputStreamWriter(this.connectionSocket.getOutputStream()));      //output stream
            String line;
            int count = 1;
            no_of_bids = 0;

            for (line = in.readLine(); line != null && !line.equals("quit"); line = in.readLine()) {
                if (count == 1) {  //second line is client name
                    clientName = line;

                }
                if (count == 2) {
                    symbol = line;
                    out.print("\ncurrent price is " + stocks.findPrice(symbol) + ". New Bid : ");
                }
                if (count > 2) {
                    if (count == 3) {
                        if (Double.compare(Double.parseDouble(stocks.findPrice(symbol)), Double.parseDouble(line)) < 0) {  //if client a bid greater than the current price
                            bids.add(Double.parseDouble(line));
                            bids.add(Double.parseDouble(line));
                            stocks.setPrice(symbol, line);
                            no_of_bids++;
                            out.print("\nYou set a bid of " + line + " on " + symbol + ". New Bid : ");
                            newbid = true;

                        } else {
                            out.print("\nYour Bid is not valid, current price is " + stocks.findPrice(symbol) + ". New Bid : ");
                            count--;
                        }
                    }

                    if (count > 3) {
                        if (Double.compare(bids.get(no_of_bids - 1), Double.parseDouble(line)) < 0) { //if client a bid greater than the current price
                            bids.add(Double.parseDouble(line));
                            stocks.setPrice(symbol, line);
                            no_of_bids++;
                            out.print("\nYou set a bid of " + line + " on " + symbol + ". New Bid : ");
                            newbid = true;
                        } else {
                            out.print("\nYour Bid is not valid, current price is " + stocks.findPrice(symbol) + ". New Bid : ");
                            count--;
                        }
                    }


                }
                out.flush();
                count++;
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                this.connectionSocket.close();
            } catch (Exception e) {
            }
        }
    }


    public static void main(String[] args) throws IOException {
        StockItemsDB details = new StockItemsDB("stocks.csv", "Symbol", "Security Name", "Price ");
        AuctionServer server = new AuctionServer(details);

        JFrame frame = new JFrame("Stock Market");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Draw(server));
        frame.pack();
        frame.setVisible(true);

        server.server_loop();
    }
}

