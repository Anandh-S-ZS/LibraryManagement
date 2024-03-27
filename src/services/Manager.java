package services;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import database.LibraryDB;
import model.Book;
import model.User;
import model.UserRecord;

public class Manager {
	LibraryDB db = LibraryDB.getInstance();
	
	Map<Integer, Book> b1 = new HashMap<>();
	Map<Integer, User> c1 = new HashMap<>();
	
	public int createUser(String userName, String password,String mailId) {
		User u = new User(userName,password,mailId);
		db.getCustomer().put(u.getUserId(), u);
		db.setCustomer();
		return u.getUserId();
	}	

	public boolean isValidUser(int userId, String password) {
		if(db.getCustomer().containsKey(userId))
		{
			System.out.println("User id Matched");
			if(password.equals(db.getCustomer().get(userId).getPassword()))
			{
				System.out.println("Password Matched");
				return true;
			}
		}
		return false;
	}

	public boolean isValidAdmin(int userId, String password) {
		if(userId == 777)
		{
			if(password.equals("Godzilla"))
			{
				System.out.println("Welcome Admin");
				return true;
			}
		}
		return false;
	}

	public User getUser(int userId) {
		return db.getCustomer().get(userId);
	}
	
	public String getUserName(int userId) {
		return db.getCustomer().get(userId).getUserName();
	}

	public void changePassword(int userId, String newPassword) {
		db.getCustomer().get(userId).setPassword(newPassword);
		db.setCustomer();
		System.out.println("Password Changed Successfully");
		
	}

	public int getDue(int userId, String date) {
		if(db.getBooks().get(db.getCustomer().get(userId).getCurrentBook()) != null)
		{
			LocalDate borrowedDate =dateConverter(getBorrowedDate(db.getBooks().get(db.getCustomer().get(userId).getCurrentBook())));
			
			LocalDate currentDate = dateConverter(date);
			int due = calculateDue(userId,borrowedDate , currentDate);
		}
		
		return db.getCustomer().get(userId).getMyDue();
	}

	public void addBook(String bookName, String author) {
		System.out.println(db.getBooks().size());
		Book b = new Book(bookName, author,db.getBooks().size()+1000);
		System.out.println("Testing");
		db.getBooks().put(b.getBookId(), b);
		db.setBooks();
		System.out.println("Book Added!");
	}

	public void removeBook(int bookId) {
		db.getBooks().remove(bookId);
		db.setBooks();
		System.out.println("Book Removed!");
	}

	public void getAvailableBooks() {
		if(!db.getBooks().isEmpty())
		{
			System.out.println("\n-------------------------------");
			for(int i : db.getBooks().keySet())
			{
				if(db.getBooks().get(i).getAvailable())
				{
					System.out.println("Book Id		:"+db.getBooks().get(i).getBookId()+"\nBook Name	:"+db.getBooks().get(i).getBookName()+"\nBook Author	:"+db.getBooks().get(i).getAuthor());
					System.out.println("-------------------------------");
				}
			}
			System.out.println();
		}
		else 
		{
			System.out.println("---------------------Book Not Added Yet---------------------");
		}
	}

	public void getBook(int bookId) {
		System.out.println(db.getBooks().get(bookId).getBookId()+" \t|| "+db.getBooks().get(bookId).getBookName()+" \t || "+db.getBooks().get(bookId).getAuthor());
	}

	public void changeBookName(int bookId, String bookName) {
		db.getBooks().get(bookId).setBookName(bookName);
		db.setBooks();
		System.out.println("Book Name Updated");
	}

	public void changeAuthorName(int bookId, String author) {
		
		db.getBooks().get(bookId).setAuthor(author);
		db.setBooks();
		System.out.println("Author Name Updated");
	}

	public void getAllUsers() {
//		System.out.println("User Id\t\t|| User Name\t\t|| Password\t\t || Due");
//		for(int i:customer.keySet())
//		{
//			System.out.println(customer.get(i).getUserId()+"\t\t|| "+customer.get(i).getUserName()+"\t\t\t|| "+customer.get(i).getPassword()+" \t\t || "+customer.get(i).getMyDue());
//		}
		if(!db.getCustomer().isEmpty())
		{
			System.out.println("\n-------------------------------------");
			for(int i:db.getCustomer().keySet())
			{
				System.out.println("Customer Id		:"+db.getCustomer().get(i).getUserId()+"\nCustomer Name	:"+db.getCustomer().get(i).getUserName()+"\nPassword		:"+db.getCustomer().get(i).getPassword()+"Due			:"+db.getCustomer().get(i).getMyDue());
				System.out.println("-------------------------------------");
			}
		}
		else
		{
			System.out.println("----------------User Not Added Yet----------------\n");
		}
	}

	public void getBorrowedHistory(int bookId) {
		if(db.getBooks().containsKey(bookId))
		{
			for(UserRecord i : db.getBooks().get(bookId).getBorrowedRecord())
			{
				System.out.println(db.getCustomer().get(i.getUserId()).getUserName()+" \t\t|| "+i.getBorrowedDate()+" \t\t|| "+i.getReturnDate());
			}
		}
		else
		{
			System.out.println("Book is not taken by anyone yet...!");
		}
	}

	public void getMyBooks(int userId) {
		if(!db.getCustomer().get(userId).getBookHistory().isEmpty())
		{
			System.out.println("-------------------------------------");
			
			for(int i: db.getCustomer().get(userId).getBookHistory())
			{
				System.out.println("Book Id 	:"+i+
								 "\nBook Name	:"+ db.getBooks().get(i).getBookName()+
								 "\nAuthor		:"+db.getBooks().get(i).getAuthor()
								 );
//				"\nBought Date	:"+db.getBooks().get(i).getBorrowedRecord().get(j).getBorrowedDate()+
//				 "\nReturn Date	:"+db.getBooks().get(i).getBorrowedRecord().get(j).getReturnDate()

				System.out.println("-------------------------------------");
			}
		}
		else
		{
			System.out.println("You never took a book yet...!");
		}
	}

	public int checkBookAvailablityByName(String bookName) {
		if(!db.getBooks().isEmpty())
		{
			for(int i: db.getBooks().keySet())
			{
				if(db.getBooks().get(i).getBookName().equals(bookName) && db.getBooks().get(i).getAvailable())
				{
					System.out.println("Book Available");
					System.out.println("-------------------------------");
					System.out.println("Book Id		:"+db.getBooks().get(i).getBookId()+
									 "\nBook Name	:"+db.getBooks().get(i).getBookName()+
									 "\nBook Author	:"+db.getBooks().get(i).getAuthor());
					System.out.println("-------------------------------");
					return i;
				}
				else
				{
					System.out.println("Book Not Available");
					return -1;
				}
			}
		}
		System.out.println("Book not available right now\nVisit later");
		return -1;
	}
	
	private LocalDate dateConverter(String date)
	{
		//Thread.dumpStack();
		String []temp = date.split("/");
		int day = Integer.parseInt(temp[0]);
		int month = Integer.parseInt(temp[1]);
		int year = Integer.parseInt(temp[2]);
		return LocalDate.of(year, month, day);
	}

	public void giveBook(int bookId, int userId,String date) {
		if(db.getCustomer().get(userId).getMyDue() == 0 && db.getCustomer().get(userId).getCurrentBook()==0)
		{
			db.getCustomer().get(userId).setCurrentBook(bookId);
			db.setCustomer();
			db.getCustomer().get(userId).addBookHistory(bookId);
			db.setCustomer();
			
			UserRecord user = new UserRecord(userId,date);
			db.getBooks().get(bookId).getBorrowedRecord().add(user);
			db.setBooks();
			System.out.println(db.getBooks().get(bookId).getBorrowedRecord().contains(user)+"uiii..");
			db.getBooks().get(bookId).setAvailable(false);
			
			db.setBooks();
			db.setCustomer();
			System.out.println("Enjoy Reading\n");
		}
		else
		{
			System.out.println("You have Due to pay or You aleady have a book to return\nPlease pay or Return the Book and Borrow Next book");
		}
	}

	public void checkBookAvailablityByAuthor(String author) {
		if(!db.getBooks().isEmpty())
		{
			boolean available = true;
			for(int i:db.getBooks().keySet())
			{
				if(db.getBooks().get(i).getAuthor().equals(author))
				{
					available = false;
					System.out.println("Book Available");
					System.out.println("--------------------------------------");
					System.out.println("Book Id		:"+db.getBooks().get(i).getBookId()+"\nBook Name	:"+db.getBooks().get(i).getBookName()+"\nBook Author	:"+db.getBooks().get(i).getAuthor());
				}
			}
			System.out.println("--------------------------------------");
			if(available)
			{
				System.out.println("Book of "+author+" is not available");
			}
		}
	}

	public int checkBookAvailablityById(int bookId) {
		if(!db.getBooks().isEmpty())
		{
			if(db.getBooks().containsKey(bookId) && db.getBooks().get(bookId).getAvailable())
			{
				System.out.println("Book Available");
				System.out.println("--------------------------------------");
				System.out.println("Book Id		:"+db.getBooks().get(bookId).getBookId()+"\nBook Name	:"+db.getBooks().get(bookId).getBookName()+"\nBook Author	:"+db.getBooks().get(bookId).getAuthor());
				return bookId;
			}
			else
			{
				System.out.println("Book Not available");
			}
		}
		return -1;
	}

	public boolean checkForBook(int userId) {
		if(db.getCustomer().get(userId).getCurrentBook()!=0)
		{
			return true;
		}
		return false;
	}

	private int calculateDue(int userId ,LocalDate borrowedDate, LocalDate returnDate) {
		
		Period difference = Period.between(borrowedDate, returnDate);
		int pendingDays = difference.getDays()+(difference.getMonths()*30)+(difference.getYears()*365);

		if(pendingDays>7)
		{
			int due = (pendingDays-7)*2;
			db.getCustomer().get(userId).setMyDue(due);
			db.setCustomer();
			return due;
		}
		return 0;
	}
	
	private int sendDate(int userId)
	{
		LocalDate borrowedDate = dateConverter(getBorrowedDate(db.getBooks().get(db.getCustomer().get(userId).getCurrentBook())));
		LocalDate returnDate = dateConverter(db.getBooks().get(db.getCustomer().get(userId).getCurrentBook()).getBorrowedRecord().get(db.getBooks().get(db.getCustomer().get(userId).getCurrentBook()).getBorrowedRecord().size()-1).getReturnDate());
		db.getCustomer().get(userId).setCurrentBook(0);
		db.setCustomer();
		return calculateDue(userId , borrowedDate, returnDate);
	}

	public int returnBook(int userId, String date) {
		int currentBook = db.getCustomer().get(userId).getCurrentBook();
		List<UserRecord> u = db.getBooks().get(currentBook).getBorrowedRecord();
		u.get(u.size()-1).setReturnDate(date);
		db.setBooks();
		db.getBooks().get(currentBook).setAvailable(true);
		db.setBooks();
		return sendDate(userId);
	}

	public void clearDue(int userId) {
		db.getCustomer().get(userId).setMyDue(0);
		db.setCustomer();
	}

	public void addDue(int userId, int due) {
		if(db.getCustomer().containsKey(userId))
		{
			db.getCustomer().get(userId).setMyDue(due);
			db.setCustomer();
			System.out.println("Due Added Successfully..!");
		}
		else 
		{
			System.out.println("User Not Found..!");
		}
	}

	public void getOverDueBooks(String date) {
		// TODO Auto-generated method stub
		//System.out.println("This Feature is not added yet...!");
		boolean flag = true;
		if(!db.getCustomer().isEmpty())
		{	
			for(int i : db.getCustomer().keySet())
			{
				if(db.getCustomer().get(i).getCurrentBook()!=0)
				{
					LocalDate borrowedDate =dateConverter(getBorrowedDate(db.getBooks().get(db.getCustomer().get(i).getCurrentBook())));
					
					LocalDate currentDate = dateConverter(date);
						int due = calculateDue(i, borrowedDate, currentDate);
						if(due >0)
						{
							flag = false;
							System.out.println(db.getCustomer().get(i).getUserName()+" \t|| "+db.getCustomer().get(i).getMyDue()+" \t|| "+db.getBooks().get(db.getCustomer().get(i).getCurrentBook()).getBookName());
						}
				}
			}
		}
		if(flag)
		{
			System.out.println("----------------No Due For Any Book---------------\n");
		}
	}

	private String getBorrowedDate(Book book) {
		return book.getBorrowedRecord().get(book.getBorrowedRecord().size()-1).getBorrowedDate();
	}

}
