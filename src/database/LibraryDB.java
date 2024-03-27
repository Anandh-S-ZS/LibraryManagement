package database;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;


import model.Book;
import model.User;

public class LibraryDB {
	Map<Integer, User> customers = new HashMap<>();
	//userId, User Object0
	Map<Integer, Book> books = new HashMap<>();
	//BookId, Book object
	
	ObjectMapper obj = new ObjectMapper();
	
	static LibraryDB l1;
		
	File customerFile = new File("C:\\Users\\abian\\eclipse-workspace\\LibraryManagement\\src\\database\\customerData.json");
	File booksFile = new File("C:\\Users\\abian\\eclipse-workspace\\LibraryManagement\\src\\database\\booksData.json");

	public static LibraryDB getInstance() {
		if(l1==null)
			l1 = new LibraryDB();
		return l1;
	}
	
	
	public LibraryDB(HashMap<Integer, User> customer, HashMap<Integer, Book> book){
		this.customers = customer;
		this.books = book;
	}
	
	public LibraryDB()
	{
		try {
			books = obj.readValue(booksFile, new TypeReference<Map<Integer, Book>>() {});
			customers = obj.readValue(customerFile, new TypeReference<HashMap<Integer,User>>(){});
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
	}

	
	public Map<Integer, Book> getBooks() {
		try {
			books = obj.readValue(booksFile, new TypeReference<Map<Integer,Book>>(){});
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return  books;
	}
	public void setBooks(Map<Integer, Book> books) {
		try {
			obj.writeValue(booksFile,books);
		} catch (StreamWriteException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("Book data pushed successfully");
		this.books = books;
	}
	public Map<Integer, User> getCustomer() {
		try {
			customers = obj.readValue(customerFile, new TypeReference<HashMap<Integer,User>>(){});
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return customers;
	}
	public void setCustomer(Map<Integer, User> customer) {
		try {
			obj.writeValue(customerFile , customer);
		} catch (StreamWriteException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("Customer data pushed successfully");
		this.customers = customer;
	}
	
	public void setCustomer() {
		try {
			obj.writeValue(customerFile , customers);
		} catch (StreamWriteException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("Customer data pushed successfully");
	}
	
	public void setBooks() {
		try {
			obj.writeValue(booksFile,books);
		} catch (StreamWriteException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("Book data pushed successfully");
	}
}
