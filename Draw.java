import java.awt.*;
import javax.swing.Timer; //for timer
import java.text.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.IOException;


public class Draw extends JPanel implements ActionListener{
AuctionServer server;
Timer timer ;
private static JPanel table= new JPanel(new GridLayout(9,3));
private static JLabel[][] labels = new JLabel[8][3];
private static JButton [] button=new JButton[8];
JPanel text;
JTextArea textArea;
public Draw(AuctionServer server){
super(new BorderLayout());
         this.server=server;
         JLabel symbol= new JLabel("Symbol");
         symbol.setBorder(BorderFactory.createLineBorder(Color.BLACK));
         JLabel name= new JLabel("Name");
         name.setBorder(BorderFactory.createLineBorder(Color.BLACK));
         JLabel price= new JLabel("Price");
         price.setBorder(BorderFactory.createLineBorder(Color.BLACK));
         table.add(symbol);
         table.add(name);
         table.add(price);
        
      for(int i=0;i<8;i++){
        for (int j=0;j<3 ;j++ ){
       
       labels[i][j]= new JLabel("");
       labels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
       table.add(labels[i][j]);

       }

       text = new JPanel();
       text.setLayout(new BorderLayout());
       textArea = new JTextArea(10, 20);   //create text area to display all bids
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        text.add(scrollPane);

      setPreferredSize(new Dimension(500,500));
	     add(table,BorderLayout.CENTER); 
       add(text, BorderLayout.SOUTH);
} 
      
	 labels[0][0].setText("FB");
     labels[1][0].setText("VRTU");
     labels[2][0].setText("MSFT");
     labels[3][0].setText("GOOGL");
     labels[4][0].setText("YHOO");
     labels[5][0].setText("XLNX");
     labels[6][0].setText("TSLA");
     labels[7][0].setText("TXN");


     labels[0][1].setText("Facebook");
     labels[1][1].setText("Virtusa Corporation - common stock");
     labels[2][1].setText("Microsoft Corporation - Common Stock");
     labels[3][1].setText("Google Inc. - Class C Capital Stock");
     labels[4][1].setText("Yahoo! Inc. - Common Stock");
     labels[5][1].setText("Xilinx");
     labels[6][1].setText("Tesla Motors");
     labels[7][1].setText("Texas Instruments Incorporated - Common Stock");
     

   

	 labels[0][2].setText(server.details.findPrice("FB"));
	 labels[1][2].setText(server.details.findPrice("VRTU"));
	 labels[2][2].setText(server.details.findPrice("MSFT"));
	 labels[3][2].setText(server.details.findPrice("GOOGL"));
	 labels[4][2].setText(server.details.findPrice("YHOO"));
	 labels[5][2].setText(server.details.findPrice("XLNX"));
	 labels[6][2].setText(server.details.findPrice("TSLA"));
	 labels[7][2].setText(server.details.findPrice("TXN"));

   timer = new Timer(500, this); 
   timer.start(); 
     }


     public void actionPerformed(ActionEvent e) {
     String time;
      for(AuctionServer s: server.clients){
         time = new SimpleDateFormat("EEE, MMM d, ''yy 'at' h:mm a").format(Calendar.getInstance().getTime()); //get system time and date
          if(s.newbid){ //if it is a new bid
          textArea.append(time+ " , Client : " + s.clientName + " , Item : "+s.symbol + " , Bid :"+s.bids.get(s.no_of_bids-1) +".\n");  //display details of bid
          textArea.setCaretPosition(textArea.getDocument().getLength());
          s.newbid=false;
  }

        for(int j=0;j<=7;j++){

      if(s.symbol != null && s.symbol.equals(labels[j][0].getText()) && (s.no_of_bids-1)>=0){ //check the symbol with the label text 

        if(Double.compare(Double.parseDouble(labels[j][2].getText()), s.bids.get(s.no_of_bids-1))<0){   //update bid if bid is greater than current price
        labels[j][2].setText(s.bids.get(s.no_of_bids-1)+"");
      
      }
    } 


    }

      }



     }
	
}


      
       
    



