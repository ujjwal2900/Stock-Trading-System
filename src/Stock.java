import org.json.JSONException;
import org.json.JSONObject;
import java.time.LocalDateTime;

public class Stock {
    JSONObject js;
    private String openPrice;
    private String closePrice;
    private String volume;
    private String Symbol;
    Stock(String Symbol) throws JSONException {
        this.Symbol = Symbol;
        String link = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="+Symbol+"&apikey=J60K315T8WXLABLH";
        //String link = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&apikey=J60K315T8WXLABLH";
        ParsingJSON ps = new ParsingJSON(link);
        String thisStockData = ps.giveString();
        try {
            js = new JSONObject(thisStockData);
        } catch (JSONException e) {
            js = new JSONObject("GetInfo");
        }

        LocalDateTime date = LocalDateTime.now().minusDays((long)1.0);
        String d = date.toString().substring(0,10);
        String day = date.getDayOfWeek().toString();
        String LatestDay ;
        if(day.equals("SUNDAY")){
            LatestDay = date.minusDays((long) 2.0).toString().substring(0,10);
        }
        else if(day.equals("SATURDAY")){
            LatestDay = date.minusDays((long) 1.0).toString().substring(0,10);
        }
        else{
            LatestDay = d;
        }
        this.openPrice = js.getJSONObject("Time Series (Daily)").getJSONObject(LatestDay).getString("1. open");
        this.closePrice = js.getJSONObject("Time Series (Daily)").getJSONObject(LatestDay).getString("4. close");
        this.volume = js.getJSONObject("Time Series (Daily)").getJSONObject(LatestDay).getString("5. volume");

    }

    public double getOpenPrice() {
        return Double.parseDouble(openPrice);
    }
    public double getOpenPrice(String date) throws JSONException {
        return Double.parseDouble(js.getJSONObject("Time Series (Daily)").getJSONObject(date).getString("1. open"));
    }
    public double getClosePrice(){
        return Double.parseDouble(closePrice);
    }
    public double getClosePrice(String date) throws JSONException {
        return Double.parseDouble(js.getJSONObject("Time Series (Daily)").getJSONObject(date).getString("4. close"));
    }
    public double getVolume() throws JSONException {
        return Double.parseDouble(this.volume);
    }
    public double getVolume(String date) throws JSONException {
        return Double.parseDouble(js.getJSONObject("Time Series (Daily)").getJSONObject(date).getString("5. volume"));
    }
    public String getSymbol() {
		return Symbol;
	}
//     StockTrend(String date1,String date2)->String , returns how much the stock has increased/decreased in the given time period
    public String StockTrend(String date1,String date2) throws JSONException {
        double price1 = this.getClosePrice(date1);
        double price2 = this.getClosePrice(date2);
        if (price1>price2){
            return String.format("Stock Increased by %.4f ",(price1 - price2)*100/price2)+"%";
        }
        return String.format("Stock Decreased by %.4f ",Math.abs((price1 - price2)*100/price2))+"%";

    }
}
