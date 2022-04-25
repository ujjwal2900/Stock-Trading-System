import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.JSONException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class MarketWatch extends JFrame implements ActionListener {

    JButton button1,popStockButton,marketIndicesButton;
    JTextField stockname;
    public void displayMessage(String message) {
		JFrame f=new JFrame();
		JOptionPane.showMessageDialog(f,message);
	}
    public MarketWatch(){

        JLabel stocknameLabel = new JLabel("Stock Name");
        stocknameLabel.setBounds(100,100 - 50,140,25);
        stocknameLabel.setFont(new Font("Comic Sans",Font.ITALIC,15));
        stocknameLabel.setForeground(Color.WHITE);

        stockname = new JTextField();
        stockname.setBounds(100+90,100- 50,140,25);
        stockname.setPreferredSize(new Dimension(120,25));

        button1 = new JButton("Get Data");
        button1.setBounds(246+90,100- 50,120,25);
        button1.addActionListener(this);
        button1.setBorder(BorderFactory.createEtchedBorder());
        button1.setBackground(new Color(23,23,23));
        button1.setFont(new Font("Comic Sans",Font.ITALIC,15));
        button1.setForeground(Color.WHITE);
        button1.setFocusable(false);

        popStockButton = new JButton("Popular Stocks");
        popStockButton.setBounds(70+90,230 - 120,120,25);
        popStockButton.addActionListener(this);
        popStockButton.setBorder(BorderFactory.createEtchedBorder());
        popStockButton.setBackground(new Color(23,23,23));
        popStockButton.setFont(new Font("Comic Sans",Font.ITALIC,15));
        popStockButton.setForeground(Color.WHITE);
        popStockButton.setFocusable(false);

        marketIndicesButton = new JButton("Market Indices");
        marketIndicesButton.setBounds(210+90,230 - 120,120,25);
        marketIndicesButton.addActionListener(this);
        marketIndicesButton.setBorder(BorderFactory.createEtchedBorder());
        marketIndicesButton.setBackground(new Color(23,23,23));
        marketIndicesButton.setFont(new Font("Comic Sans",Font.ITALIC,15));
        marketIndicesButton.setForeground(Color.WHITE);
        marketIndicesButton.setFocusable(false);


        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,400);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(133,205,202));
        this.setVisible(true);

        this.add(button1);
        this.add(stocknameLabel);
        this.add(stockname);
        this.add(popStockButton);
        this.add(marketIndicesButton);


    }
    public void forEachStock(Stock s) throws JSONException {

        LocalDateTime date = LocalDateTime.now().minusDays((long)1.0);
        String d = date.toString().substring(0,10);
        String day = date.getDayOfWeek().toString();
        String LatestDay ;
        String earlierDay =  date.minusDays((long) 7.0).toString().substring(0,10);
        if(day.equals("SUNDAY")){
            LatestDay = date.minusDays((long) 2.0).toString().substring(0,10);
            earlierDay = date.minusDays((long) 9.0).toString().substring(0,10);
        }
        else if(day.equals("SATURDAY")){
            LatestDay = date.minusDays((long) 1.0).toString().substring(0,10);
            earlierDay = date.minusDays((long) 8.0).toString().substring(0,10);
        }
        else{
            LatestDay = d;

        }

        System.out.println(String.format("%s | %.3f | %s",s.getSymbol(),s.getClosePrice(),s.StockTrend(LatestDay,earlierDay)));

    }
    public void popularTechStocks() throws JSONException {

        System.out.println("*".repeat(50));
        System.out.println("Stock : Current price : Performance");
        Stock[] StockArr = {new Stock("GOOGL"), new Stock("TXN"),new Stock("AAPL") };
        for(Stock A : StockArr){
            this.forEachStock(A);
        }
        System.out.println("*".repeat(50));

    }
    public void marketIndices(){
    	String s="";
    	s+="\t\tMarket Indices";
    	s+="\n"+"-".repeat(50);
    	s+="\n"+"NYSE Composite index: $16,868.11";
    	s+="\n"+"Dow Jones Industrial Average: $35,515.38";
    	s+="\n"+"S&P 500 Index: $14,822.55";
    	s+="\n"+"Russell 2000 Index: $2,223.11";
    	s+="\n"+"Global Dow Realtime USD: $4,097.02";
    	s+="\n"+"Dow Jones U.S. Total Stock Market Index: $46,820.65";
    	s+="\n"+"NASDAQ 100 Index (NASDAQ Calculation): $15,136.68";
    	s+="\n"+"NYSE Composite Index: $16,868.11";
    	s+="\n"+"-".repeat(50);
    	System.out.println(s);
    	displayMessage(s);
//        System.out.println("\t\tMarket Indices \n"+"-".repeat(50));
//        System.out.println("NYSE Composite index: $16,868.11");
//        System.out.println("Dow Jones Industrial Average: $35,515.38");
//        System.out.println("S&P 500 Index: $14,822.55");
//        System.out.println("Russell 2000 Index: $2,223.11");
//        System.out.println("Global Dow Realtime USD: $4,097.02");
//        System.out.println("Dow Jones U.S. Total Stock Market Index: $46,820.65");
//        System.out.println("NASDAQ 100 Index (NASDAQ Calculation): $15,136.68");
//        System.out.println("NYSE Composite Index: $16,868.11"+"-".repeat(50));
    }
    public void completeStockData(String symbol) throws JSONException {
        System.out.println("*".repeat(50));
        Stock stock = new Stock(symbol);
        forEachStock(stock);
        System.out.println("*".repeat(50));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button1){
            try {
                System.out.println("*".repeat(50));
                System.out.println("Stock : Current price : Performance");
                forEachStock(new Stock(stockname.getText().toString()));
                System.out.println("*".repeat(50));
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            displayMessage("Please check the console for the details");
        }
        if(e.getSource()==marketIndicesButton){
            this.marketIndices();
        }
        if(e.getSource()==popStockButton){
            try {
                this.popularTechStocks();
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            displayMessage("Please check the console for the details");
        }
    }

    public static void main(String[] args) throws JSONException {
//         MarketWatch m = new MarketWatch();
    }
}