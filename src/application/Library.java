package application;

import java.io.IOException;
import java.util.Scanner;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import services.Manager;

public class Library {
	public static void main(String[] args) throws StreamWriteException, DatabindException, IOException {
		Scanner sc = new Scanner(System.in);
		
		Manager manager = new Manager();
		String currentDate;
		
		
		while(true) 
		{	
			System.out.println("Enter Current Date(DD/MM/YYYY):");
			currentDate = sc.next();
			System.out.println("1.Signup || 2.Login || 0.ShutDown");
			int choice = sc.nextInt();
			
			
			int userId;
			int bookId;
			int due;
			String userName;
			String password;
			String mailId;
			String bookName;
			String author;
			
			
			if(choice == 1) 
			{
				sc.nextLine();
				System.out.println("Enter User Name:");
				userName = sc.nextLine();
				System.out.println("Enter Email Id:");
				mailId = sc.nextLine();
				System.out.println("Enter Password: ");
				password = sc.nextLine();
				System.out.println("\nYour User Name:"+userName+"\nPassword: "+password);
				userId = manager.createUser(userName,password,mailId);
				System.out.println("\nYour User Id is:"+userId);
			}
			else if(choice == 2)
			{
				System.out.println("Enter User Id:");
				userId = sc.nextInt();
				sc.nextLine();
				System.out.println("Enter Password: ");
				password = sc.nextLine();
				
			}
			else if(choice == 0)
			{
				System.out.println("Bye...!");
				break;
			}
			else
			{
				System.out.println("Invalid Input");
				continue;
			}
			
			
			if(manager.isValidUser(userId,password)) 
			{
				userName = manager.getUserName(userId);
				while(true) {
					System.out.println("1.Change Password || 2.Show Due || 3.Search & Get Books || 4.My Book History || 5.List Books || 6.Return Book & Pay Due || 0.Log out");
					choice = sc.nextInt();
					if(choice == 1)
					{
						System.out.println("New password must be atleat 6 characters, 1 special character, 1 numerical Value");
						System.out.println("Enter New Password");
						String newPassword = sc.next();
						manager.changePassword(userId,newPassword);
					}
					else if(choice == 2)
					{
						due = manager.getDue(userId,currentDate);
						if(due==0)
						{
							System.out.println("Don't worry Dude, No due for you");
						}
						else 
						{
							System.out.println("Dear "+userName+", U have due amount of RS:"+due+"\nPlease pay your due to continue your services");
						}
					}
					else if(choice == 3)
					{
						while(true)
						{
							System.out.println("Search By \n1.Book Name\n2.Author Name\n3.Book Id\n4.Exit");
							choice = sc.nextInt();
							if(choice == 1)
							{
								sc.nextLine();
								System.out.println("Enter Book Name:");
								bookName = sc.nextLine();
								bookId = manager.checkBookAvailablityByName(bookName);
								if(bookId>0)
								{

									System.out.println("Are you taking this book?\n1.Yes\n2.No");
									choice = sc.nextInt();
									if(choice == 1)
									{
										manager.giveBook(bookId,userId,currentDate);
									}
									else
									{
										System.out.println();
										continue;
									}
								}
							}
							else if(choice == 2)
							{
								sc.nextLine();
								System.out.println("Enter Author Name:");
								author = sc.nextLine();
								manager.checkBookAvailablityByAuthor(author);
							}
							else if(choice == 3)
							{
								System.out.println("Enter Book Id:");
								bookId = sc.nextInt();
								bookId = manager.checkBookAvailablityById(bookId);
								if(bookId>0)
								{
									System.out.println("Are you taking this book?\n1.Yes\n2.No");
									choice = sc.nextInt();
									if(choice == 1)
									{
										manager.giveBook(bookId,userId,currentDate);
									}
									else
									{
										System.out.println();
										continue;
									}
								}
							}
							else if(choice == 4)
							{
								break;
							}
							else 
							{
								System.out.println("Invalid Input...!");
							}
						}
					}
					else if(choice == 4)
					{
							manager.getMyBooks(userId);
					}
					else if(choice == 5)
					{
						manager.getAvailableBooks();
					}
					else if(choice == 6)
					{
						while(true)
						{
							System.out.println("1.Return Book || 2.Pay Bill || 3.Exit");
							choice = sc.nextInt();
							if(choice == 1)
							{
								if(manager.checkForBook(userId))
								{
									
									due = manager.returnBook(userId,currentDate);
									if(due == 0)
									{
										System.out.println("Chill Dude!, You Returned The Book On Time!");
									}
									else
									{
										System.out.println("You got the Due of RS:"+due);
									}
								}
								else
								{
									System.out.println("You don't have any book to return!");
								}
							}
							else if(choice == 2)
							{
								due = manager.getDue(userId,currentDate);
								if(due==0)
								{
									System.out.println("Don't worry Dude, No due for you");
								}
								else 
								{
									System.out.println("Your due of RS:"+due+" is Paid Successfully");
									manager.clearDue(userId);
								}
							}
							else if(choice == 3)
							{
								break;
							}
							else
							{
								System.out.println("Invalid Input");
							}
						}
						
					}
					else if(choice == 0)
					{
						break;
					}
					else
					{
						System.out.println("Invalid Input");
					}
				}
			}
			else if(manager.isValidAdmin(userId,password))
			{
				
				while(true)
				{
					System.out.println("1.Edit Book Records || 2.List All Books || 3.List All Users || 4.OverDue Books list || 5.Single Book History || 6.Add Due to User || 0.Log Out");
					choice = sc.nextInt();
					if(choice == 1) 
					{
						
						while(true) {
							System.out.println("1.Add Book || 2.Remove Book || 3.Edit Book Data || 4.Exit");
							choice = sc.nextInt();
							if(choice == 1)
							{
								sc.nextLine();
								System.out.println("Enter Book Name:");
								bookName = sc.nextLine();
								System.out.println("Enter Author Name:");
								author = sc.nextLine();
								manager.addBook(bookName,author);
							}
							else if(choice == 2)
							{
								sc.nextLine();
								System.out.println("Provide Book Id to Remove: ");
								bookId = sc.nextInt();
								manager.removeBook(bookId);
							}
							else if(choice == 3)
							{
								System.out.println("Enter Book Id to Edit:");
								bookId = sc.nextInt();
								manager.getBook(bookId);
								while(true)
								{
									System.out.println("What you want to edit...?");
									System.out.println("1.Book Name || 2.Author Name || 3.Completed");
									choice = sc.nextInt();
									if(choice==1)
									{
										sc.nextLine();
										System.out.println("Enter Book Name:");
										bookName = sc.nextLine();
										manager.changeBookName(bookId,bookName);
									}
									else if(choice==2)
									{
										sc.nextLine();
										System.out.println("Enter Author Name:");
										author = sc.nextLine();
										manager.changeAuthorName(bookId,author);
									}
									else if(choice==3)
									{
										System.out.println("Everything is Updated in DataBase..!");
										break;
									}
									else
									{
										System.out.println("Invalid Input");
									}
								}
							}
							else if(choice == 4)
							{
								break;
							}
							else 
							{
								System.out.println("Invalid Input");
								continue;
							}
						}
					}
					else if(choice == 2)
					{
						manager.getAvailableBooks();
					}
					else if(choice == 3)
					{
						manager.getAllUsers();
					}
					else if(choice == 4)
					{
						//todo: overdue book list
						manager.getOverDueBooks(currentDate);
					}
					else if(choice == 5)
					{
						System.out.println("Enter Book Id:");
						bookId = sc.nextInt();
						manager.getBorrowedHistory(bookId);
					}
					else if(choice == 6)
					{
						System.out.println("Enter User Id:");
						userId = sc.nextInt();
						System.out.println("Enter Due Amount:");
						due = sc.nextInt();
						manager.addDue(userId,due);
						
					}
					else if(choice == 0)
					{
						break;
					}
					else 
					{
						System.out.println("Invalid Input");
					}
				}
			}
			
			else
			{
				System.out.println("Invalid UserId or Password");
				continue;
			}
		}
	}
}
