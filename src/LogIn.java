import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class LogIn {
    JSONObject userInfo;
    LogIn() throws JSONException, FileNotFoundException 
	{
        String s = "";
        FileReader reader = new FileReader("USERDATA.json");
        Scanner scan = new Scanner(reader);
        while(scan.hasNext()){
            s = s + scan.nextLine();
        }
        userInfo = new JSONObject(s);
    }
    public boolean accountExists(String enteredId) throws FileNotFoundException 
	{
        Iterator it = userInfo.keys();
        while(it.hasNext()){
            String userID = it.next().toString();
            if(enteredId.equals(userID)){
                return true;
            }
        }
        return false;
    }
    public boolean SignIN(Account acc) throws JSONException {
        Iterator it = userInfo.keys();
        while(it.hasNext()){
            String userID = it.next().toString();
            if(acc.getUserId().equals(userID) && acc.getPassword().equals(userInfo.getJSONObject(userID).get("password"))){
                return  true;
            }
        }
        return false;
    }	
    
    public void SignUp(Account acc) throws JSONException 
	{
    	JSONObject ID1 = new JSONObject();
	      //Inserting key-value pairs into the json object
	      ID1.put("userName", acc.getUserName());
		  ID1.put("Name",acc.getName());
	      ID1.put("password", acc.getPassword());
		  ID1.put("balance", String.valueOf(acc.getBalance()));
		  userInfo.accumulate(acc.getUserId(), ID1);
		  try {
		         FileWriter file = new FileWriter("USERDATA.json");
//		         file.write(jsonObject.toJSONString());
		         file.write(userInfo.toString());
		         file.close();
		      } catch (IOException e) {
		         e.printStackTrace();
		      }
		  
		  FileReader reader=null;
		try {
			reader = new FileReader("UserAccount_Data.json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	        Scanner scan = new Scanner(reader);
	        JSONObject dataUAD;
	        String s="";
	        while(scan.hasNext()){
	            s = s + scan.nextLine();
	        }
	        try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        dataUAD = new JSONObject(s);
	        JSONObject UserAccount_Data; 
	        UserAccount_Data=new JSONObject();
        	dataUAD.put(acc.getUserId(),UserAccount_Data);
        	FileWriter file2;
			try {
				file2 = new FileWriter("UserAccount_Data.json");
				file2.write(dataUAD.toString());
				file2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

    public static void main(String[] args) {
    	
    }
}
