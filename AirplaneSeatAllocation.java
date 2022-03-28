import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class AirplaneSeatAllocation
{
	
    public static int[] calculateSeatDistrubution(int matrix[][]) // Calculate the total number of aisle, window, middle seats
		{
		int n = matrix.length; 
		int window_seats = matrix[0][1]+matrix[n-1][1];/*window seats will be there only in first and last matrix. Adding the number of rows in first and last matrix will be total number of window seats.*/
		int middle_seats = 0, aisle_seats = 0;
		for(int i=0;i<n;i++) //Iterating matrix to calculate the total number of aisle and middle seats
		{
		    for(int j=0;j<2;j++)
		    {
		        if(i!=0 && i!=n-1)//Aisle and Middle seat calculation for non window seat containing matrix 
		        {
		        if(j==0 && matrix[i][j]>2)//If there is greater than 2 columns then middle seats will be there else not
		        {
		            middle_seats+= (matrix[i][j]-2)*matrix[i][j+1];//Middle Seats - Eliminating Aisle seats*number of rows
		            aisle_seats+= 2*matrix[i][j+1];//Aisle seats - 2*number of rows on non window matrix
		        }
		        else if(j==0){
		            aisle_seats+= matrix[i][j]*matrix[i][j+1];/*Aisle seats - If less or equal 2 columns in a matrix then just multiply it with number of rows*/  
		        }
		        }
		        else//Aisle and Middle seat calculation for window seat containing matrix 
		        {
		           if(j==0 && matrix[i][j]>2)//If there is greater than 2 columns then middle seats will be there else not
		        {
		            middle_seats+= (matrix[i][j]-2)*matrix[i][j+1];//Middle Seats - Eliminating Aisle seats*number of rows
		            aisle_seats+= 1*matrix[i][j+1];//Aisle seats - 1*number of rows on non window matrix eliminating window seats
		        }
		        else if(j==0){
		            aisle_seats+= matrix[i][j]*matrix[i][j+1];/*Aisle seats - If less or equal 2 columns in a matrix then just multiply it with number of rows*/
		        } 
		        }
		    }
		}
		int seats[] = new int[3];
		seats[0] = aisle_seats;
		seats[1] = window_seats;
		seats[2] = middle_seats;
		return seats;// Store and return total number of seat distrubution
    }
		
		
		
		public static String[][][] seatDistrubution(int matrix[][],int aisle_seat_counter, int window_seat_counter, int middle_seat_counter, int maxcol, int maxrow) /* Algorithm/Logic to distrubute seating in such a way only one time whole row and column of every matrix input traversed for distrubuting all the seats*/
		{
		String airline_seats[][][]= new String[maxrow][maxrow][maxcol];//3D Array for storing the seat distrubution of entire matrix
		for(int i=0;i<maxrow;i++)//Iterate/loop only one time for calculating the seat distrubution for all seat types
		{
		    for(int j=0;j<matrix[0][0];j++)//Iterate the first 2D matrix
		    {
		        if(i>matrix[0][1]-1){//If greater than rows of first matrix
		            break;
		        }
		        if(j==0)//First column of first matrix then it is window seat
		        {
		            airline_seats[i][0][j] = "W-"+window_seat_counter;//Marking and Assigning window seat
		            window_seat_counter++;//Increase window seat counter
		        }
		        else if(j==(matrix[0][0]-1))//If it is window matrix then only the last column of first matrix is aisle
		        {
		            airline_seats[i][0][j] = "A-"+aisle_seat_counter;//Marking and Assigning Aisle seat
		            aisle_seat_counter++;//Increase aisle seat counter
		        }
		        else//If not window and aisle then it is middle seat
		        {
		            airline_seats[i][0][j] = "M-"+middle_seat_counter;//Marking and Assigning Middle seat
		            middle_seat_counter++;//Increase middle seat counter
		        }
		    }
		    for(int j=0;j<matrix[1][0];j++)//Iterate the second 2D matrix
		    {
		        if(i>matrix[1][1]-1){//If greater than rows of second matrix
		            break;
		        }
		        if(j==0 || j==matrix[1][0]-1)// On non window matrix first and last column are aisle seats
		        {
		            airline_seats[i][1][j] = "A-"+aisle_seat_counter;
		            aisle_seat_counter++;
		        }
		        else// On non window matrix except first and last column all other are middle seats
		        {
		            airline_seats[i][1][j] = "M-"+middle_seat_counter;
		            middle_seat_counter++;
		        }
		    }
		    for(int j=0;j<matrix[2][0];j++)//Iterate the third 2D matrix
		    {
		        if(i>matrix[2][1]-1){//If greater than rows of third matrix
		            break;
		        }
		        if(j==0 || j==matrix[2][0]-1)// On non window matrix first and last column are aisle seats
		        {
		            airline_seats[i][2][j] = "A-"+aisle_seat_counter;
		            aisle_seat_counter++;
		        }
		        else// On non window matrix except first and last column all other are middle seats
		        {
		            airline_seats[i][2][j] = "M-"+middle_seat_counter;
		            middle_seat_counter++;
		        }
		    }
		    for(int j=0;j<matrix[3][0];j++)//Iterate the fourth 2D matrix
		    {
		        if(i>matrix[3][1]-1){//If greater than rows of fourth matrix
		            break;
		        }
		        if(j==matrix[3][0]-1)//Last column of Last matrix then it is window seat
		        {
		            airline_seats[i][3][j] = "W-"+window_seat_counter;
		            window_seat_counter++;
		        }
		        else if(j==0)//If it is window matrix then only the first column of last matrix is aisle
		        {
		            airline_seats[i][3][j] = "A-"+aisle_seat_counter;
		            aisle_seat_counter++;
		        }
		        else//If not window and aisle then it is middle seat
		        {
		            airline_seats[i][3][j] = "M-"+middle_seat_counter;
		            middle_seat_counter++;
		        }
		    }
		}
		return airline_seats;//Return the 3d array of containing all seat distrubution with seat numbers of a matrix
		}
		
		
		
		public static void print(String airline_seats[][][],int maxrow,int maxcol, int passengers, int total_seats, int matrix[][]) throws Exception // Method to print airplane seating to a file
		{
			FileWriter fw
                = new FileWriter("airplane_seating.txt");// Output file containing the seating plan
		    for(int i=0;i<maxrow;i++)//Iterate over 3D array with max number of rows
		{
		    System.out.println();
			fw.write("\n");
		    for(int j=0;j<maxrow;j++)//Iterate over 3D array with max number of rows
		    {
		        for(int z=0;z<maxcol;z++)//Iterate over 3D array with max number of columns
		        {
		            if(airline_seats[i][j][z]!=null)//If the seats are null
					{
					if(Integer.parseInt(airline_seats[i][j][z].substring(2))<=passengers)/*If passenger waiting in queue and seat number is less then allocate the seat*/ 
					{
		            System.out.print(airline_seats[i][j][z]+" ");
					fw.write(airline_seats[i][j][z]+" ");
					}
					else{
					System.out.print("__ ");
					fw.write("__ ");//free/empty seats without allocation 
					}
					}
					else{
						System.out.print(" "+"   ");
						fw.write(" "+"   ");
					}
		        }
				System.out.print("        ");
				fw.write("        ");// space between every matrix block
		    }
		}
		fw.write("\n");
		fw.write("\n");
		fw.write("A - Aisle Seats");//Indication and explanation of seats on output
		fw.write("\n");
		fw.write("W - Window Seats");
		fw.write("\n");
		fw.write("M - Middle Seats");
		fw.write("\n");
		fw.write("__ - Empty Seats");
		fw.write("\n");
		fw.write("\n");
		if(passengers>total_seats)//If passenger waiting in queue greater than total number of seats
		{
		fw.write("Passengers Still waiting for seat : "+(passengers-total_seats));
		}
		else{
			fw.write("All passengers in queue seated successfully");
		}
		fw.close();
	}
		
		
		public static boolean validateInput(String input[]) throws Exception// Method to validate matrix input
		{
			if(input.length!=4)
			{
				throw new Exception("Please provide proper input matrix");
			}
			for(int i=0;i<input.length;i++)
			{
				if(input[i].replace("]","").split(",").length!=2)
				{
					throw new Exception("Please provide proper input matrix");
				}
			}
			return true;
		}
		
		
		public static boolean isNonNegativeInteger(int input) throws Exception// Method to validate input values
		{
			if(input<0)
			{
				throw new Exception("Please provide non negative integer input");
			}
			return true;
		}
			
		
		
	public static void main(String[] args) throws Exception {
	  Scanner sc = new Scanner(new BufferedReader(new FileReader(args[0]))); // Reading input file with scanner
	  
	  int window_seats = 0, middle_seats = 0, aisle_seats = 0, maxrow = 0, maxcol = 0, total_seats = 0, 
	  aisle_seat_counter=0, window_seat_counter = 0, middle_seat_counter = 0, passengers=0; /* Variables to hold seat capacity and it's counter*/
	  
	  int matrix[][] = new int[4][2];//Input matrix
	  
	  String input = sc.nextLine();// Reading the Input file and parsing
	  String array_input[] = input.substring(1, input.length() - 1).replace("[","").split("],");
	  validateInput(array_input);//Validate input matrix
	  for (int i=0; i<array_input.length; i++)
	  {
		  String temp[] = array_input[i].replace("]","").split(",");
		  matrix[i][0] = Integer.parseInt(temp[0].trim());
		  isNonNegativeInteger(matrix[i][0]);//validate matrix values
		  matrix[i][1] = Integer.parseInt(temp[1].trim());
		  isNonNegativeInteger(matrix[i][1]);//validate matrix values
		  if(matrix[i][0]>maxrow)
		  maxrow = matrix[i][0];//maxrow to traverse
		  if(matrix[i][1]>maxcol)
		  maxcol = matrix[i][1];//maxcol to traverse
	  }
	  passengers = Integer.parseInt(sc.nextLine().trim());//Number of passenger in queue
	  isNonNegativeInteger(passengers);//validate passenger value
	  
	  int seat_distrubution[] = calculateSeatDistrubution(matrix);//calculate seat distrubution - Total number of aisle seats, window seats and middle seats for given matrix input
	  aisle_seats = seat_distrubution[0];//Total number of aisle seats
	  window_seats = seat_distrubution[1];//Total number of window seats
	  middle_seats = seat_distrubution[2];//Total number of middle seats
	  
	  System.out.println("window_seats: "+window_seats);
	  System.out.println("middle_seats: "+middle_seats);
	  System.out.println("aisle_seats: "+aisle_seats);
	  
	  aisle_seat_counter = 1; window_seat_counter = aisle_seats+1; middle_seat_counter = aisle_seats+window_seats+1;/*aisle_seat_counter will start from 1 and window_seat_counter will start from total number aisle_seats+1(after aisle seats filled) and middle_seat_counter start from aisle_seats+window_seats+1(after aisle and window seats filled)*/
	  
	  total_seats = aisle_seats+window_seats+middle_seats;//Total seats available for given matrix in a plane
		
	  String airline_seats[][][]= seatDistrubution(matrix,aisle_seat_counter,window_seat_counter,middle_seat_counter,maxcol,maxrow);//Seat distrubuted using all the counter and matrix values
	  
	  print(airline_seats,maxrow,maxcol,passengers,total_seats,matrix);//Printing the seat distrubution to a file - airplane_seating.txt
	  
	}
}