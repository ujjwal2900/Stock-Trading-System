import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTable;

import org.json.*;

public class Portfolio implements Portfolio_Interface {
	private String userId;
	private JSONObject jo,jid; 
	public int getStockHolding(Stock s) throws JSONException
	{
		JSONObject jstock=jid.getJSONObject(s.getSymbol());
		return (Integer.parseInt(jstock.getString("CurrentHoldings")));
	}

	public String getLatest(String dt) {
		LocalDate date=LocalDate.parse(dt);
		String d=date.toString();
		String day = date.getDayOfWeek().toString();
		String LatestDay ;
		if(day.equals("SUNDAY")){
			LatestDay = date.minusDays((long) 2.0).toString();
		}
		else if(day.equals("SATURDAY")){
			LatestDay = date.minusDays((long) 1.0).toString();
		}
		else{
			LatestDay = d;
		}
		return LatestDay;
	}

	public double getStockValue(Stock s) throws NumberFormatException, JSONException
	{
		JSONObject jstock=jid.getJSONObject(s.getSymbol());
		int n=Integer.parseInt(jstock.getString("CurrentHoldings"));
		return (n*s.getClosePrice());
	}
	public double getStockInvestment(Stock s) throws JSONException
	{
		JSONObject jstock=jid.getJSONObject(s.getSymbol());
		JSONObject jpurch=jstock.getJSONObject("buys");
		Iterator it =jpurch.keys();
		String date="";
		double invest=0.0;
		while(it.hasNext())
		{
			date=it.next().toString();
			//	date=getLatest(date);
			invest+=(s.getClosePrice(getLatest(date))*Double.parseDouble(jpurch.getString(date)));
		}
		return invest;
	}
	public double getStockSales(Stock s) throws JSONException
	{
		JSONObject jstock=jid.getJSONObject(s.getSymbol());
		JSONObject jsale=jstock.getJSONObject("sells");
		Iterator it =jsale.keys();
		String date="";
		double sale=0.0;
		while(it.hasNext())
		{
			date=it.next().toString();
			//	date=getLatest(date);
			sale+=(s.getClosePrice(getLatest(date))*Double.parseDouble(jsale.getString(date)));
		}
		return sale;
	}
	public double getStockProfit(Stock s) throws NumberFormatException, JSONException
	{
		double profit=this.getStockSales(s)+this.getStockValue(s)-this.getStockInvestment(s);
		return profit;
	}
	public int getOverallHolding() throws JSONException
	{
		Iterator it=jid.keys();
		int tot_hold=0;
		while(it.hasNext())
		{
			String symbol=it.next().toString();
			Stock st=new Stock(symbol);
			tot_hold+=this.getStockHolding(st);
		}
		return tot_hold;
	}
	public Portfolio(String userId) throws Exception
	{
		this.userId=userId;
		FileReader reader = new FileReader("UserAccount_Data.json");
		String s="";
		Scanner scan = new Scanner(reader);
		while(scan.hasNext()){
			s = s + scan.nextLine();
		}
		reader.close();

		jo=new JSONObject(s);
		jid=jo.getJSONObject(userId);
	}
	public double getOverallValue() throws JSONException
	{
		Iterator it=jid.keys();
		double tot_val=0.0;
		while(it.hasNext())
		{
			String symbol=it.next().toString();
			Stock st=new Stock(symbol);
			tot_val+=this.getStockValue(st);
		}
		return tot_val;

	}
	public double getOverallInvestment() throws JSONException
	{
		Iterator it=jid.keys();
		double tot_invest=0.0;
		while(it.hasNext())
		{
			String symbol=it.next().toString();
			Stock st=new Stock(symbol);
			tot_invest+=this.getStockInvestment(st);
		}
		return tot_invest;

	}
	public double getOverallSales() throws JSONException
	{
		Iterator it=jid.keys();
		double tot_sale=0.0;
		while(it.hasNext())
		{
			String symbol=it.next().toString();
			Stock st=new Stock(symbol);
			tot_sale+=this.getStockSales(st);
		}
		return tot_sale;

	}
	public double getOverallProfit() throws NumberFormatException, JSONException
	{
		double tot_profit=this.getOverallSales()+this.getOverallValue()-this.getOverallInvestment();
		return tot_profit;
	}

	public LinkedList<String> displayStockList()
	{
		Iterator it=jid.keys();
		int i=1;
		LinkedList<String> list=new LinkedList<>();
		System.out.println("Your stock list :");
		while(it.hasNext())
		{
			String s=it.next().toString();
			System.out.println(i+". "+s);
			list.add(s);
			i++;
		}
//		System.out.println(list);
		return list;

	}
	//this method displays portfoio of an input stock
	public  void displayStockPortfolio(Stock s) throws JSONException
	{
		if(!jid.has(s.getSymbol()))
		{
			System.out.println("This stock does not exist in portfolio..");
			return ;
		}
		System.out.println("DETAILS OF "+s.getSymbol()+":");
		String data[][]=new String[5][2];
		data[0][0]="Current Holding";
		data[1][0]="Current Value";
		data[2][0]="Total Investment (till date)";
		data[3][0]="Total Sales(till date)";
		data[4][0]="Total Profit(as of now)";
		data[0][1]=String.format("%d", this.getStockHolding(s));
		data[1][1]=String.format("%.2f", this.getStockValue(s));
		data[2][1]=String.format("%.2f", this.getStockInvestment(s));
		data[3][1]=String.format("%.2f", this.getStockSales(s));
		data[4][1]=String.format("%.2f", this.getStockProfit(s));
		String col[]= {"a","b"};



		JFrame pop=new JFrame();
		JTable table =new JTable(data,col);
		table.setRowHeight(20);
		pop.add(table);
		pop.setSize(300,400);
		pop.setVisible(true);


	}
	//This method displays complete stock details
	public void displayFullPortfolio() throws JSONException
	{
		System.out.println("COMPLETE STOCK DETAILS:");
		Iterator it=jid.keys();
		int l=jid.length();
		String data[][]=new String[l+2][6];
		data[0][0]="Stock Name";
		data[0][1]="Current Holding";
		data[0][2]="Current Value";
		data[0][3]="Total Investment (till date)";
		data[0][4]="Total Sales(till date)";
		data[0][5]="Total Profit(as of now)";
		int i=1,holding=0;
		float invest=0,sale=0,value=0,profit=0;
		for(;it.hasNext();i++)
		{
			String name=it.next().toString();
			Stock st=new Stock(name);
			data[i][0]=name;
			data[i][1]=String.format("%d", this.getStockHolding(st));
			holding+=Integer.parseInt(data[i][1]);
			data[i][2]=String.format("%.2f", this.getStockValue(st));
			value+=Float.parseFloat(data[i][2]);
			data[i][3]=String.format("%.2f", this.getStockInvestment(st));
			invest+=Float.parseFloat(data[i][3]);
			data[i][4]=String.format("%.2f", this.getStockSales(st));
			sale+=Float.parseFloat(data[i][4]);
			data[i][5]=String.format("%.2f", (Float.parseFloat(data[i][2])+Float.parseFloat(data[i][4])-Float.parseFloat(data[i][3])));
			profit+=Float.parseFloat(data[i][5]);
		}

		data[i][0]="TOTAL";
		data[i][1]=String.format("%d", holding);
		data[i][2]=String.format("%.2f", value);
		data[i][3]=String.format("%.2f", invest);
		data[i][4]=String.format("%.2f", sale);
		data[i][5]=String.format("%.2f", profit);
		String col[]= {"a","b","c","d","e","f"};



		JFrame pop=new JFrame();
		JTable table =new JTable(data,col);
		table.setRowHeight(20);
		pop.add(table);
		pop.setSize(300,400);
		pop.setVisible(true);

	}
	@SuppressWarnings("deprecation")
	//Following methods display purchase and sale history respectively
	public void displayPurchaseHistory() throws JSONException
	{
		System.out.println("Purchase History");
		String data[][]=new String[20][4];
		data[0][0]="Purchase Date";
		data[0][1]="Stock Purchased";
		data[0][2]="Quantity Purchased";
		data[0][3]="Amount invested";
		int i=1;
		Iterator it=jid.keys();
		while(it.hasNext())
		{
			String name=it.next().toString();
			JSONObject jstock=jid.getJSONObject(name);
			JSONObject jpurch=jstock.getJSONObject("buys");
			Iterator jt=jpurch.keys();
			while(jt.hasNext())
			{
				data[i][0]=jt.next().toString();
				data[i][1]=name;
				Stock st=new Stock(name);
				data[i][2]=jpurch.getString(data[i][0]);
				data[i][3]=String.format("%.2f", st.getClosePrice(getLatest(data[i][0]))*Double.parseDouble(data[i][2]));
				i++;
			}}

		for(int j=1;data[j+1][0]!=null;j++)
		{
			String temp[]=new String[4];
			for(int k=1;data[k+1][0]!=null;k++)
			{
				String date1[]=data[k][0].split("-");
				String date2[]=data[k+1][0].split("-");
				Date d1=new Date(Integer.parseInt(date1[0]),Integer.parseInt(date1[1]),Integer.parseInt(date1[2]));
				Date d2=new Date(Integer.parseInt(date2[0]),Integer.parseInt(date2[1]),Integer.parseInt(date2[2]));
				if(d2.after(d1))
				{
					temp=data[k];
					data[k]=data[k+1];
					data[k+1]=temp;
				}


			}
		}
		String col[]= {"a","b","c","d"};



		JFrame pop=new JFrame();
		JTable table =new JTable(data,col);
		table.setRowHeight(20);
		pop.add(table);
		pop.setSize(300,400);
		pop.setVisible(true);



	}
	public void displaySaleHistory() throws JSONException
	{
		System.out.println("Sale History");
		String data[][]=new String[20][4];
		data[0][0]="Sale Date";
		data[0][1]="Stock sold";
		data[0][2]="Quantity Sold";
		data[0][3]="Amount received";
		int i=1;
		Iterator it=jid.keys();
		while(it.hasNext())
		{
			String name=it.next().toString();
			JSONObject jstock=jid.getJSONObject(name);
			JSONObject jsale=jstock.getJSONObject("sells");
			Iterator jt=jsale.keys();
			while(jt.hasNext())
			{
				data[i][0]=jt.next().toString();
				data[i][1]=name;
				Stock st=new Stock(name);
				data[i][2]=jsale.getString(data[i][0]);
				data[i][3]=String.format("%.2f", st.getClosePrice(getLatest(data[i][0]))*Double.parseDouble(data[i][2]));
				i++;
			}}

		for(int j=1;data[j+1][0]!=null;j++)
		{
			String temp[]=new String[4];
			for(int k=1;data[k+1][0]!=null;k++)
			{
				String date1[]=data[k][0].split("-");
				String date2[]=data[k+1][0].split("-");
				Date d1=new Date(Integer.parseInt(date1[0]),Integer.parseInt(date1[1]),Integer.parseInt(date1[2]));
				Date d2=new Date(Integer.parseInt(date2[0]),Integer.parseInt(date2[1]),Integer.parseInt(date2[2]));
				if(d2.after(d1))
				{
					temp=data[k];
					data[k]=data[k+1];
					data[k+1]=temp;
				}


			}
		}
		String col[]= {"a","b","c","d"};

		JFrame pop=new JFrame();
		JTable table =new JTable(data,col);
		table.setRowHeight(20);
		pop.add(table);
		pop.setSize(300,400);
		pop.setVisible(true);

	}


	public static void main(String[] args) throws Exception {

	}

}


