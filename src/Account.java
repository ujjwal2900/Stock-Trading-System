import org.json.JSONException;

import java.io.IOException;

public class Account {
	
	private final String userId;
	private String Name;
	private String userName;
	private String password;
	private double balance;
	public String generateId() {
		/*
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		String s="";
		for(int i=0;i<4;i++) {
			int num=(int)(Math.random()*62);
			char ch=AB.charAt(num);
			s+=ch;
		}
		return  s;
		*/
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public Account(String userName,String password,double balance, String Name) {
		//this.userId=generateId();
		this.Name = Name;
		this.userId = userName;
		this.userName=userName;
		this.password=password;
		this.balance=balance;//For sign-up
	}
	public Account(String userId,String password) {
		//this.userId=userId;
		this.userId = userId;
		this.userName="";
		this.password=password;
		this.balance=0;//For login
	}
	public Account(String userId,String userName,String password,double balance) {
		//this.userId=userId;
		this.userId = userId;
		this.userName=userName;
		this.password=password;
		this.balance=balance;
	}
	public Account(String userId){
		this.userId = userId;
	}
	public String getUserId() {
		return this.userId;
	}
	public String getUserName() {
		return this.userName;
	}
	public double getBalance() throws JSONException {
		return this.balance;
	}
	public String getName()
	{
		return this.Name;
	}
}
