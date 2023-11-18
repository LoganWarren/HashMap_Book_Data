package ass3;
/** 
 * This creates a dataset of books by reading the book data from 
 * GoodReadsData.txt
 */
import java.util.*;
//import ass3sol.BookData; commenting out because its in the same folder as bookdata
import java.io.*;
/*
 * A BookFinder class holds mappings between ISBNs, titles, authors, 
 * publishers, and ratings to books. each book is an object of type {@link BookData}.
 * 
 * @version Fall 2022
 * 
 */
public class BookFinder {
	
	MyHashMap<String, BookData> isbnToData;
	MyHashMap<String, ArrayList<BookData>> titleToData;
	MyHashMap<String, ArrayList<BookData>> authorToData;
	MyHashMap<String, ArrayList<BookData>> publisherToData;
	MyHashMap<Float, ArrayList<BookData>> ratingToData;
   
	
	/**
	 * Default constructor: used for tests
	 */
	public BookFinder() {
    	isbnToData = new MyHashMap<String, BookData>();
    	titleToData = new MyHashMap<String, ArrayList<BookData>>();
    	authorToData = new MyHashMap<String, ArrayList<BookData>>();
    	publisherToData = new MyHashMap<String, ArrayList<BookData>>();
    	ratingToData = new MyHashMap<Float, ArrayList<BookData>>();       
    }
	
	/**
     * Creates a BookFinder object by reading the data file at path.
     * 
     * The input file is a comma-separated text file with 5 fields per line:
     * isbn,authors,title,publisher,rating
     * 
     * Multiple authors are separated by '/' characters: 
     * for example: Frank Herbert/Domingo Santos
     * 
     * @param path The file path for the input data file.
     */
    public BookFinder (String path) {
    	this();
    	
    	fillDataFromFile(path);
    }   
    
    
    /*
     * You need to open the data file with a "UTF-8" flag, as in
     * 
     * Scanner scan = new Scanner( new File(s), "UTF-8");
     *
     * Parse each line of the file and create a new BookData object 
     * with the relevant fields. 
     * 
     * Put the newly created BookData object into isbnToData map with the isbn as the key.
     * 
     * For the other maps, add the BookData object to the ArrayList stored in the map with
     * the appropriate key (title, author, publisher, or rating). 
     * If a book has multiple authors, then each author's list should contain the BookData object.
     */
    private void fillDataFromFile(String path) {
        Scanner scan = null;
        try {
            scan = new Scanner(new File(path), "UTF-8");
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            return;
        }

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] fields = line.split(",");

            String isbn = fields[0];
            String[] authors = fields[1].split("/");
            for (int i = 0; i < authors.length; i++) {
                authors[i] = authors[i].trim();
            }  //Helps with any spacing issues
            String title = fields[2];
            String publisher = fields[3];
            Float rating = Float.parseFloat(fields[4]);

            BookData bd = new BookData(isbn, authors, title, publisher, rating);

            addBookByISBN(isbn, bd);
            for (String author : authors) {
                addBookByAuthor(author, bd);
            }
            addBookByTitle(title, bd);
            addBookByPublisher(publisher, bd);
            addBookByRating(rating, bd);
        }
        scan.close();
    }
    
    /** 
     * Adds the isbn as a key and the BookData object as a value into the isbnToData map
     * 
     * @param isbn - book ISBN
     * @param bd - the BookData object
     */
    public void addBookByISBN(String isbn, BookData bd) {
        isbnToData.put(isbn, bd);
    }
    
    /** 
     * Adds the title as a key and the BookData object as a value into the titleToData map
     * Note that a title is not guaranteed to be unique, that is why 
     * you should store a list of BookData objects for each title key.
     * 
     * @param title - book title
     * @param bd - the BookData object
     */
    public void addBookByTitle (String title, BookData bd) {
        addBookToList(titleToData, title, bd);
    }
    
    /** 
     * Adds the author as a key and the BookData object as a value into the authorToData map
     * There are of course many books for the same author, that is why 
     * you should store a list of BookData objects for each author key.
     * 
     * @param author - an author
     * @param bd - the BookData object
     */
    public void addBookByAuthor (String author, BookData bd) {
        addBookToList(authorToData, author, bd);
    }
    
    /** 
     * Adds the publisher as a key and the BookData object as a value into the authorToData map
     * There are many books from the same publisher, that is why 
     * you should store a list of BookData objects for each key.
     * 
     * @param publisher - a publisher
     * @param bd - the BookData object
     */
    public void addBookByPublisher (String publisher, BookData bd) {
        addBookToList(publisherToData, publisher, bd);
    }
    
    /** 
     * Groups all the books with the same rating under the same map key
     * 
     * There are many books with the same rating, that is why 
     * you should store a list of BookData objects for each key.
     * 
     * @param rating - a book rating
     * @param bd - the BookData object
     */
    public void addBookByRating(Float rating, BookData bd) {
        ArrayList<BookData> books = ratingToData.get(rating);
        if (books == null) {
            books = new ArrayList<>();
            ratingToData.put(rating, books);
        }
        books.add(bd);
    }
    
    //possible helper method to which you can pass the map of interest, the key, and the value
    //and it will add the value to the list of values
    //you can then reuse this for addBookByTitle, addBookByAuthor, addBookByPublisher
    // you DO NOT have to implement this helper - feel free to delete it.
    private static void addBookToList(MyHashMap<String, ArrayList<BookData>> map,String key, BookData bd) {
        ArrayList<BookData> books = map.get(key);
        if (books == null) {
            books = new ArrayList<>();
            map.put(key, books);
        }
        books.add(bd);
    }
    
	/**
	 * Returns a list of books written by the author.
	 * 
	 * @param author The author to search for.
	 * @return A list of {@link BookData} objects written by author.
	 */
    public List<BookData> searchByAuthor(String author) {
        return authorToData.get(author);  // Simply return the result from the map
    }
    
// THIS ONE IS DIFFERENT ^^^^ currently working on it 
	/**
	 * Returns a list of books with the exact title.
	 * 
	 * @param title The title to search for.
	 * @return A list of {@link BookData} objects with the given title.
	 */
    public List<BookData> searchByTitle(String title) {
        return titleToData.get(title);  // Return null if no title is found
    }

	/**
	 * Returns a list of books published by publisher.
	 * 
	 * @param publisher The publisher to search for.
	 * @return A list of {@link BookData} published by the publisher.
	 */
    public List<BookData> searchByPublisher(String publisher) {
        return publisherToData.get(publisher);  // Return null if no publisher is found
    }
    

	/**
	 * Returns a book corresponding to an ISBN, or null if no such book is in the
	 * database.
	 * 
	 * @param isbn The ISBN to search for.
	 * @return A (unique) {@link BookData} corresponding to the isbn, or null.
	 */
     public BookData searchByIsbn(String isbn) {
        return isbnToData.get(isbn);
    }
	/**
	 * Returns a list of books with the same rating
	 * 
	 * @param rating The value of book rating.
	 * @return A list of {@link BookData} with this rating.
	 */
    public List<BookData> searchByRating(Float rating) {
        return ratingToData.get(rating);  // Return null if no rating is found
    }
    
}
