import org.json.JSONException;

public interface Portfolio_Interface {
	public int getStockHolding(Stock s) throws JSONException;
	public double getStockValue(Stock s) throws NumberFormatException, JSONException;
	public double getStockInvestment(Stock s) throws JSONException;
	public double getStockSales(Stock s) throws JSONException;
	public double getStockProfit(Stock s) throws NumberFormatException, JSONException;
	public int getOverallHolding() throws JSONException;

public double	getOverallValue() throws JSONException;
	public double getOverallInvestment() throws JSONException;
	public double getOverallSales() throws JSONException;
	public double getOverallProfit() throws NumberFormatException, JSONException;
	
}
