package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book {
	
	private int bookId;
	private String bookName;
	private String author;
	private List<UserRecord> borrowedRecord = new ArrayList<>();
	private boolean available;
	
	public Book(int id, String name, String author, List<UserRecord> borrowedRecord, boolean available)
	{
		this.bookId = id;
		this.bookName = name;
		this.author = author;
		this.borrowedRecord = borrowedRecord;
		this.available = available;
	}

	public Book(String bookName, String author, int id) {
		this.bookName = bookName;
		this.author = author;
		this.bookId = id;
		this.available = true;
	}
	
	public Book() {
		
	}
	
	public int getBookId() {
		return bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public String getAuthor() {
		return author;
	}
	public List<UserRecord> getBorrowedRecord() {
		return borrowedRecord;
	}
//	public UserRecord getLastBorrowedRecord() {
//		return borrowedRecord.get(borrowedRecord.size()-1);
//	}
//	public String getBorrowedDate()
//	{
//		return borrowedRecord.get(borrowedRecord.size()-1).getBorrowedDate();
//	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void addBorrowedRecord(UserRecord userRecord) {
		borrowedRecord.add(userRecord);
	}
	public boolean getAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public void setBorrowedRecord(List<UserRecord> borrowedRecord) {
		this.borrowedRecord = borrowedRecord;
	}
}