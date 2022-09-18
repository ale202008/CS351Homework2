// This is an assignment for students to complete after reading Chapter 3 of
// "Data Structures and Other Objects Using Java" by Michael Main.

package edu.uwm.cs351;

import java.util.spi.CalendarDataProvider;

import junit.framework.TestCase;

//Andrew Le, Homework #2, CS 351

/******************************************************************************
 * This class is a homework assignment;
 * An ApptBook ("book" for short) is a sequence of Appointment objects in sorted order.
 * The book can have a special "current element," which is specified and 
 * accessed through four methods that are available in the this class 
 * (start, getCurrent, advance and isCurrent).
 * <p>
 * Notes:
 * <ol>
 * <li> The capacity of a book can change after it's created, but
 *   the maximum capacity is limited by the amount of free memory on the 
 *   machine. The constructor, insert, insertAll, and clone
 *   methods will result in an
 *   {@link java.lang.OutOfMemoryError} when free memory is exhausted.
 * <li>
 *   A book's capacity cannot exceed the maximum integer 2,147,483,647
 *   ({@link Integer#MAX_VALUE}). Any attempt to create a larger capacity
 *   results in a failure due to an arithmetic overflow. 
 * </ol>
 * <b>NB</b>: Neither of these conditions require any work for the implementors (students).
 *
 *
 ******************************************************************************/
public class ApptBook implements Cloneable {

	/** Static Constants */
	private static final int INITIAL_CAPACITY = 1;

	/** Fields */
	
	// TODO: You need 'data', 'manyItems' and 'currentIndex' fields.
	// Don't initialize them here, but rather in the constructor(s).
	
	private Appointment[] data;													//Removed the final modifier from the fields.
	private int manyItems;
	private int currentIndex;
	
	// Invariant of the ApptBook class:
	//   1. The number of elements in the books is in the instance variable 
	//      manyItems.
	//   2. For an empty book (with no elements), we do not care what is 
	//      stored in any of data; for a non-empty book, the elements of the
	//      book are stored in data[0] through data[manyItems-1] and we
	//      don't care what's in the rest of data.
	//   3. None of the elements are null and they are ordered according to their
	//      natural ordering (Comparable); duplicates *are* allowed.
	//   4. If there is a current element, then it lies in data[currentIndex];
	//      if there is no current element, then currentIndex equals manyItems.

	private static boolean doReport = true; // change only in invariant tests
	private boolean report(String error) {
		if (doReport) {
			System.out.println("Invariant error: " + error);
		}
		return false;
	}

	private boolean wellFormed() {
		// Check the invariant.
		
		// 1. data array is never null
		// TODO
		
		if (data == null) {														//Returns false if the array data null.
			return false;
		}

		// 2. The data array has at least as many items in it as manyItems
		//    claims the book has
		// TODO
		
		int count = 0;
		
		for (int i = 0; i < data.length; ++i) {									//iterates through the array keeping a count
																				//field to keep count of how many elements
																				//exist within the array to compar manyItems
			if (data[i] != null) {
				count++;
			}
		}
		
		if (count < manyItems) {												//Returns false if the length of the array
																				//does not equal to the manyItems.
																				//Might change depending if length and the
																				//amount of elements are the same or not.
			return false;
		}
		

		// 3. None of the elements are null and all are in natural order
		
		for (int i = 0; i < count; ++i) {										//Currently, only checks if elements are null
																				//and will include the compareTo method once
																				//I figure it out.
			if ((data[i] == null && i > 0)) {
				return false;
			}
////			else if (data.length > 1) {
////				if (data[i+1] != null && data[i].compareTo(data[i+1]) > 0) {
////					return false;
////				}
//			}
		}
		
		for (int i = 0; i < count - 1; ++i) {									//Currently trying to use the compareTo method
																				//to check for natural ordering.
			if (data[i+1] != null) {
//				for (int y = 0; y < count - 1; ++y) {
					if (data[i].compareTo(data[i+1]) > 0) {
						return false;
//					}
				}
			}
		}
		
		// 4. currentIndex is never negative and never more than the number of
		//    items in the book.
		// TODO	
		
		if (this.currentIndex < 0 || this.currentIndex > manyItems) {			//Current statement says that if currentIndex is 
																				//lesser than 0 or greater than manyItems, return
																				//false.
			return false;
		}

		// If no problems discovered, return true
		return true;
	}

	// This is only for testing the invariant.  Do not change!
	private ApptBook(boolean testInvariant) { }

	/**
	 * Initialize an empty book with 
	 * an initial capacity of INITIAL_CAPACITY. The {@link #insert(Appointment)} method works
	 * efficiently (without needing more memory) until this capacity is reached.
	 * @postcondition
	 *   This book  is empty and has an initial capacity of INITIAL_CAPACITY
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for initial array.
	 **/   
	public ApptBook( )
	{
		// TODO: Implemented by student.
		this.data = new Appointment[INITIAL_CAPACITY];						//Will initialize the data array to be of size
																			//INITIAL_CAPACITY.
																			//Initialized the fields manyItems and currentIndex
		this.manyItems = 0;
		this.currentIndex = 0;
		assert wellFormed() : "invariant failed at end of constructor";
	}

	/**
	 * Initialize an empty book with a specified initial capacity.
	 * The {@link #insert(Appointment)} method works
	 * efficiently (without needing more memory) until this capacity is reached.
	 * @param initialCapacity
	 *   the initial capacity of this book, must not be negative
	 * @exception IllegalArgumentException
	 *   Indicates that initialCapacity is invalid
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for an array with this many elements.
	 *   new Appointment[initialCapacity].
	 **/   
	public ApptBook(int initialCapacity)
	{
		
		// TODO: Implemented by student.
		this.data = new Appointment[initialCapacity];						//Will initialize the data array to be of size
																			//initialCapacity.
																			//Initialized the fields manyItems and currentIndex
		this.manyItems = 0;
		this.currentIndex = 0;
		assert wellFormed() : "invariant failed at end of constructor";
	}

	/**
	 * Determine the number of elements in this book.
	 * @return
	 *   the number of elements in this book
	 **/ 
	public int size( )
	{
		assert wellFormed() : "invariant failed at start of size";
		// TODO: Implemented by student.
		int count = 0;
		for (int i = 0; i < data.length; ++i) {								//A for-loop that iterates through the array to
																			//its set length and keeps count of the elements
																			//that are not equal to null and returns the number.
			if (data[i] != null) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Set the current element at the front of this book.
	 * @postcondition
	 *   The front element of this book is now the current element (but 
	 *   if this book has no elements at all, then there is no current 
	 *   element).
	 **/ 
	public void start( )
	{
		assert wellFormed() : "invariant failed at start of start";
		// TODO: Implemented by student.
		this.currentIndex = 0;

		assert wellFormed() : "invariant failed at end of start";
	}

	/**
	 * Accessor method to determine whether this book has a specified 
	 * current element that can be retrieved with the 
	 * getCurrent method. 
	 * @return
	 *   true (there is a current element) or false (there is no current element at the moment)
	 **/
	public boolean isCurrent( )
	{
		assert wellFormed() : "invariant failed at start of isCurrent";
		// TODO: Implemented by student.
		if (currentIndex == manyItems) {										//Checks if currentIndex is equal to manyItems
																				//and if so that means that there is not a
																				//current element, so its returns false and 
																				//returns true if that is not the case.
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Accessor method to get the current element of this book. 
	 * @precondition
	 *   isCurrent() returns true.
	 * @return
	 *   the current element of this book
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so 
	 *   getCurrent may not be called.
	 **/
	public Appointment getCurrent( )
	{
		assert wellFormed() : "invariant failed at start of getCurrent";
		// TODO: Implemented by student.
		// Don't change "this" object!
		
		if (isCurrent()) {														//If statement that runs the isCurrent method
																				//to check if there exists a current element
																				//and if true to return the Appointment object
																				//at the currrentIndex position within the
																				//data array.
			return data[currentIndex];
		}
		else {
			throw new IllegalStateException();
		}

	}

	/**
	 * Move forward, so that the current element will be the next element in
	 * this book.
	 * @precondition
	 *   isCurrent() returns true. 
	 * @postcondition
	 *   If the current element was already the end element of this book 
	 *   (with nothing after it), then there is no longer any current element. 
	 *   Otherwise, the new element is the element immediately after the 
	 *   original current element.
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so 
	 *   advance may not be called.
	 **/
	public void advance( )
	{
		assert wellFormed() : "invariant failed at start of advance";
		// TODO: Implemented by student.
		
		if (isCurrent()) {														//Checks if isCurrent() method returns true,
																				//and if so, adds 1 to the currentIndex to
																				//advance.
			currentIndex++;
		}
		else {
			throw new IllegalStateException();
		}
		
		assert wellFormed() : "invariant failed at end of advance";
	}

	/**
	 * Remove the current element from this book.
	 * @precondition
	 *   isCurrent() returns true.
	 * @postcondition
	 *   The current element has been removed from this book, and the 
	 *   following element (if there is one) is now the new current element. 
	 *   If there was no following element, then there is now no current 
	 *   element.
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so 
	 *   removeCurrent may not be called. 
	 **/
	public void removeCurrent( )
	{
		assert wellFormed() : "invariant failed at start of removeCurrent";
		// TODO: Implemented by student.
		
		if (isCurrent()) {														//Checks if isCurrent() method returns true,
																				//and if so, turns the current element into null
																				//so that in the for-loop we can make a new array
																				//that is of size data.length - 1 and only consists
																				//of the remaining elements.
																				//Wherever the element that is made into null is in 
																				//the data array, the next element after or the element at
																				//currentIndex + 1 is now where currentIndex points to.
																				
			data[currentIndex] = null;
			Appointment[] temp = new Appointment[data.length-1];
			int tempIndex = 0;
			
			for(int i = 0; i < data.length; ++i) {
				if (data[i] != null) {
					temp[tempIndex] = data[i];
					tempIndex++;
				}
			}
			
			data = temp;
			
		}
		else {
			throw new IllegalStateException();
		}
		
		assert wellFormed() : "invariant failed at end of removeCurrent";
	}
	
	/**
	 * Set the current element to the first element that is equal
	 * or greater than the guide.
	 * @param guide element to compare against, must not be null.
	 */
	public void setCurrent(Appointment guide) {
		// TODO: use other methods.
		// (Binary search would be much faster for a large book.
		// but don't worry about efficiency for this method yet.)
		
		for (int i = 0; i < data.length; ++i) {									//Checks equality between objects as the for-loop
																				//iterates through the data array to check which
																				//Appointment object elements equal to guide and set the
																				//current index to that position represented by int i.
																				//I don't know how to do the greater case of this statement
																				//so for now just equal.
																				//If I am understanding the compareTo method in Appointment.java
																				//then the if statement will check if the data object at int i will
																				//equal guide or if the returning int value from the compareTo method
																				//is not 0.
			if (data[i].equals(guide) || data[i].compareTo(guide) != 0) {
				currentIndex = i;
			}
		}
		
	}

	/**
	 * Change the current capacity of this book as needed so that
	 * the capacity is at least as big as the parameter.
	 * This code must work correctly and efficiently if the minimum
	 * capacity is (1) smaller or equal to the current capacity (do nothing)
	 * (2) at most double the current capacity (double the capacity)
	 * or (3) more than double the current capacity (new capacity is the
	 * minimum passed).
	 * @param minimumCapacity
	 *   the new capacity for this book
	 * @postcondition
	 *   This book's capacity has been changed to at least minimumCapacity.
	 *   If the capacity was already at or greater than minimumCapacity,
	 *   then the capacity is left unchanged.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for: new array of minimumCapacity elements.
	 **/
	private void ensureCapacity(int minimumCapacity)
	{
		// TODO: Implemented by student.
		// NB: do not check invariant
		
		if (minimumCapacity >= Integer.MAX_VALUE) {
			throw new OutOfMemoryError();
		}
		
		if (data.length < minimumCapacity && minimumCapacity/data.length == 2) {
			Appointment[] temp = new Appointment[2*data.length];
			for (int i = 0; i < 0; ++i) {
				temp[i] = data[i];
			}
			data = temp;
		}
		else if (minimumCapacity/data.length > 2) {
			Appointment[] temp = new Appointment[minimumCapacity];
			for (int i = 0; i < 0; ++i) {
				temp[i] = data[i];
			}
			data = temp;
		}
		
		if (data.length == minimumCapacity) {
			boolean y = true;
		}

		
	}

	/**
	 * Add a new element to this book, in order.  If an equal appointment is already
	 * in the book, it is inserted after the last of these. 
	 * If the new element would take this book beyond its current capacity,
	 * then the capacity is increased before adding the new element.
	 * The current element (if any) is not affected.
	 * @param element
	 *   the new element that is being added, must not be null
	 * @postcondition
	 *   A new copy of the element has been added to this book. The current
	 *   element (whether or not is exists) is not changed.
	 * @exception IllegalArgumentException
	 *   indicates the parameter is null
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for increasing the book's capacity.
	 **/
	public void insert(Appointment element)
	{
		assert wellFormed() : "invariant failed at start of insert";
		// TODO: Implemented by student.														//Basically, I set up three fields to temporarily
																								//hold things: A temporary array, and int field, and
																								//a boolean
																								//The int elementPosition serves to remember where the
																								//element should be placed after checking the cases
																								//of equality
																								//Two conditions must occure for the for loop to break
																								//and that is if there exists and element that is equal
																								//to the given element or if there exists an element
																								//greater than the given element.
		
		Appointment[] temp = new Appointment[data.length + 1];
		int elementPosition = 0;
		
		for (int i = 0; i < data.length; ++i) {
			if (data[i].equals(element)) {
				elementPosition = i + 1;														//The elementPosition is after the equal element
				temp[elementPosition] = element;
				break;
			}
			if (element.compareTo(data[i]) > 0) {
				elementPosition = i - 1;														//The elementPosition is right before the element
																								//greater than it
				break;
			}
		}
		
																								//After checking for the cases, process to construct
																								//the new array after inserting the new one with 
																								//the if statement running for-loops to fill in elements
																								//before the elementPosition and the elements after it
																								//as well in the case of there being an element that is equal
																								//to the given element.
																								//Otherwise, simply copy of the elements of data array and
																								//insert the element wherever it needs to be,
		
		for (int i = 0; i < elementPosition; ++i) {
			temp[i] = data[i];
		}
		for (int i = elementPosition; i < data.length; ++i) {
			temp[i + 1] = data[i];
		}

		
		data = temp;
		
		assert wellFormed() : "invariant failed at end of insert";
	}

	/**
	 * Place all the appointments of another book (which may be the
	 * same book as this!) into this book in order as in {@link #insert}.
	 * The elements should added one by one from the start.
	 * The elements are probably not going to be placed in a single block.
	 * @param addend
	 *   a book whose contents will be placed into this book
	 * @precondition
	 *   The parameter, addend, is not null. 
	 * @postcondition
	 *   The elements from addend have been placed into
	 *   this book. The current el;ement (if any) is
	 *   unchanged.
	 * @exception NullPointerException
	 *   Indicates that addend is null. 
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory to increase the size of this book.
	 **/
	public void insertAll(ApptBook addend)
	{
		assert wellFormed() : "invariant failed at start of insertAll";
		// TODO: Implemented by student.
		// Watch out for the this==addend case!
		// Cloning the addend is an easy way to avoid problems.
		assert wellFormed() : "invariant failed at end of insertAll";
		assert addend.wellFormed() : "invariant of addend broken in insertAll";
	}

	/**
	 * Generate a copy of this book.
	 * @return
	 *   The return value is a copy of this book. Subsequent changes to the
	 *   copy will not affect the original, nor vice versa.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for creating the clone.
	 **/ 
	public ApptBook clone( ) { 
		assert wellFormed() : "invariant failed at start of clone";
		ApptBook answer;
	
		try
		{
			answer = (ApptBook) super.clone( );
		}
		catch (CloneNotSupportedException e)
		{  // This exception should not occur. But if it does, it would probably
			// indicate a programming error that made super.clone unavailable.
			// The most common error would be forgetting the "Implements Cloneable"
			// clause at the start of this class.
			throw new RuntimeException
			("This class does not implement Cloneable");
		}
	
		// all that is needed is to clone the data array.
		// (Exercise: Why is this needed?)
		answer.data = data.clone( );
	
		assert wellFormed() : "invariant failed at end of clone";
		assert answer.wellFormed() : "invariant on answer failed at end of clone";
		return answer;
	}

	// don't change this nested class:
	public static class TestInvariantChecker extends TestCase {
		Time now = new Time();
		Appointment e1 = new Appointment(new Period(now,Duration.HOUR),"1: think");											//An hour	//e1
		Appointment e2 = new Appointment(new Period(now,Duration.DAY),"2: current");										//24 hours	//e3
		Appointment e3 = new Appointment(new Period(now.add(Duration.HOUR),Duration.HOUR),"3: eat");						//2 hours	//e4
		Appointment e4 = new Appointment(new Period(now.add(Duration.HOUR.scale(2)),Duration.HOUR.scale(8)),"4: sleep");	//10 hours	//e2
		Appointment e5 = new Appointment(new Period(now.add(Duration.DAY),Duration.DAY),"5: tomorrow");						//48 hours	//e5
		ApptBook hs;

		protected void setUp() {
			hs = new ApptBook(false);
			ApptBook.doReport = false;
		}

		public void test0() {
			assertFalse(hs.wellFormed());
		}
		
		public void test1() {
			hs.data = new Appointment[0];
			hs.manyItems = -1;
			assertFalse(hs.wellFormed());
			hs.manyItems = 1;
			assertFalse(hs.wellFormed());
			
			doReport = true;
			hs.manyItems = 0;
			assertTrue(hs.wellFormed());
		}
		
		public void test2() {
			hs.data = new Appointment[1];
			hs.manyItems = 1;
			assertFalse(hs.wellFormed());
			hs.manyItems = 2;
			assertFalse(hs.wellFormed());
			hs.data[0] = e1;
			
			doReport = true;
			hs.manyItems = 0;
			assertTrue(hs.wellFormed());
			hs.manyItems = 1;
			hs.data[0] = e1;
			assertTrue(hs.wellFormed());
		}

		public void test3() {
			hs.data = new Appointment[3];
			hs.manyItems = 2;
			hs.data[0] = e2;
			hs.data[1] = e1;
			assertFalse(hs.wellFormed());
			
			doReport = true;
			hs.data[0] = e1;
			assertTrue(hs.wellFormed());
			hs.data[1] = e2;
			assertTrue(hs.wellFormed());
		}
		
		public void test4() {
			hs.data = new Appointment[10];
			hs.manyItems = 3;
			hs.data[0] = e2;
			hs.data[1] = e4;
			hs.data[2] = e3;
			assertFalse(hs.wellFormed());
			
			doReport = true;
			hs.data[2] = e5;
			assertTrue(hs.wellFormed());
			hs.data[0] = e4;
			assertTrue(hs.wellFormed());
			hs.data[1] = e5;
			assertTrue(hs.wellFormed());
		}
		
		public void test5() {
			hs.data = new Appointment[10];
			hs.manyItems = 4;
			hs.data[0] = e1;
			hs.data[1] = e3;
			hs.data[2] = e2;
			hs.data[3] = e5;
			assertFalse(hs.wellFormed());
			
			doReport = true;
			hs.data[2] = e4;
			assertTrue(hs.wellFormed());
			hs.data[2] = e5;
			assertTrue(hs.wellFormed());
			hs.data[2] = e3;
			assertTrue(hs.wellFormed());
		}
		
		public void test6() {
			hs.data = new Appointment[3];
			hs.manyItems = -2;
			assertFalse(hs.wellFormed());
			hs.manyItems = -1;
			assertFalse(hs.wellFormed());
			hs.manyItems = 1;
			assertFalse(hs.wellFormed());
			
			doReport = true;
			hs.manyItems = 0;
			assertTrue(hs.wellFormed());
		}

		public void test7() {
			hs.data = new Appointment[3];
			hs.data[0] = e1;
			hs.data[1] = e2;
			hs.data[2] = e4;
			hs.manyItems = 4;
			assertFalse(hs.wellFormed());
			
			doReport = true;
			hs.manyItems = 3;
			assertTrue(hs.wellFormed());
		}

		public void test8() {
			hs.data = new Appointment[3];
			hs.data[0] = e2;
			hs.data[1] = e3;
			hs.data[2] = e1;
			hs.manyItems = 2;
			hs.currentIndex = -1;
			assertFalse(hs.wellFormed());
			hs.currentIndex = 3;
			assertFalse(hs.wellFormed());
			
			doReport = true;
			hs.currentIndex = 0;
			assertTrue(hs.wellFormed());
			hs.currentIndex = 1;
			assertTrue(hs.wellFormed());
			hs.currentIndex = 2;
			assertTrue(hs.wellFormed());
		}
	}
}

