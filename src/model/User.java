package model;

import java.util.ArrayList;
import java.util.List;

public class User {
	private static int idCreater = 1001;

	private int userId;
	private String userName;
	private String password;
	private String mailId;
	private int myDue;
	private int currentBookId;
	// private List<Book> bookHistory = new ArrayList<>();
	private List<Integer> bookHistory = new ArrayList<>();
	
	public User(int id, String name, String password, String mailId, int due, int currentBookId) {
		this.userId = id;
		this.userName = name;
		this.password = password;
		this.mailId = mailId;
		this.myDue = due;
		this.currentBookId = currentBookId;
	}

	public User(String userName, String password, String mailId) {
		this.userName = userName;
		this.password = password;
		this.mailId = mailId;
		this.userId = idCreater++;
	}

	public User() {
		
	}
	
	public int getCurrentBookId() {
		return currentBookId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public void setCurrentBookId(int currentBookId) {
		this.currentBookId = currentBookId;
	}

	public void setBookHistory(List<Integer> bookHistory) {
		this.bookHistory = bookHistory;
	}

	public String getMailId() {
		return mailId;
	}

	public int getCurrentBook() {
		return currentBookId;
	}

	public void setCurrentBook(int currentBookId) {
		this.currentBookId = currentBookId;
	}

	public int getMyDue() {
		return myDue;
	}

	public void setMyDue(int myDue) {
		this.myDue = myDue;
	}

	public int getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public List<Integer> getBookHistory() {
		return bookHistory;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void addBookHistory(int bookId) {
		this.bookHistory.add(bookId);
	}
}
