package model;

public class UserRecord {
	//final User user;		//userID
	int userId;
	String borrowedDate;
	String returnDate;
	
	
//	public UserRecord(User user,LocalDate borrowedDate){
//		this.user = user;
//		this.borrowedDate = borrowedDate;
//	}
	
	public UserRecord() {
		
	}
	
	public UserRecord(int userId, String borrowedDate, String returnDate) {
		this.userId = userId;
		this.borrowedDate = borrowedDate;
		this.returnDate = returnDate;
	}
	
	public UserRecord(int userId, String borrowedDate)
	{
		this.userId = userId;
		this.borrowedDate = borrowedDate;
	}
	
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	

	public String getBorrowedDate() {
		return borrowedDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setBorrowedDate(String borrowedDate) {
		this.borrowedDate = borrowedDate;
	}

	public String getReturnDate() {
		return returnDate;
	}
}
