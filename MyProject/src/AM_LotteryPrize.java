/*
 * Program Name: AM_LotteryPrize.java
 * Purpose: This Program will analyze the ticket numbers data contained in the selected data file
 * to determine which ticket holders have won a prize,
 * how many ticket holders will have to share a prize and how much money they’ll each receive.
 * Coder: Abdulfatiah Marouf 1144451
 * Date: Nov 30, 2023
 */
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class AM_LotteryPrize{

    public static void main(String[] args) {
    	
    	// Create the Scanner 
        Scanner input = new Scanner(System.in);
        
        // Declare variables 
        DecimalFormat df = new DecimalFormat("$#,##0.00");
        String lotteryName ;
        String filePath;
        int prizePoolAmount ;
        boolean badData = true;// loopFlag
        
        System.out.println("Lottery prizes analyzer\n");
        // prompt the user to enter the name of the lottery
        System.out.print("Enter the name of the lottery: ");
        lotteryName = input.nextLine();
        
        // prompt the user to enter the total amount of money in the prizePool
        // with data validation to make sure the amount at least $1000
        do 
        {
        	System.out.print("Enter the amount of money in the prize pool: $");
        	
        	prizePoolAmount = input.nextInt();
        	
        	if (prizePoolAmount < 1000)
        	{
        		System.out.println("The prize pool amount must be at least $1000 plese try again.");
        		badData = false;
        		
        	}// end if 
        	else 
        	{
        		badData = true;
        	}
        	
        }while (!badData);
        
        input.nextLine();// flush the buffer
        
        
        // prompt the user to enter the path and name of the file with data validation
        do 
        {
        System.out.print("Enter the path for the data file: ");
        filePath = input.nextLine();
        
        File myFile = new File(filePath);
        if (!myFile.exists() || myFile.isDirectory())
        {
        	System.out.println("File does not exists or it's a Directory please try again.");
        }
        else 
        {
        	break ;
        }
        
        }while (true);
        
       /// Create a File object using the provided filePath
       
        // List to store ticket numbers as arrays
        List<int[]> tickets = new ArrayList<>();
        // Array to hold the winning numbers
        int[] winningNumbersArray = null;

        try (Scanner fileReader = new Scanner(new File(filePath)))
        {
        	// Check if the file has the first line containing winning numbers
            if (fileReader.hasNextLine()) 
            {
            	// read the winning numbers, split by commas, and store them as integers in an array
                String[] winningNumbersString = fileReader.nextLine().split(",");
                winningNumbersArray = new int[winningNumbersString.length];
                
                // Parse each element from string to integer and store it in the winningNumbersArray
                for (int i = 0; i < winningNumbersString.length; i++)
                {
                    winningNumbersArray[i] = Integer.parseInt(winningNumbersString[i].trim());
                }
            }// end if 
           
         // Read each subsequent line from the file as a ticket entry and store them in the 'tickets' list
            while (fileReader.hasNextLine())
            {
            	// Extract the ticket numbers and add them to the tickets list
                int[] ticketArray = AM_ProjectMethods.getNextSeries(fileReader);
                tickets.add(ticketArray);
            }
        } 
        catch (FileNotFoundException ex)
        {
        	// Handle this situation by displaying the error message
            System.out.println("Error reading the file: " + ex.getMessage());
            
        }

     // Calculate winners
        
        //  Initialize variables to keep track of different prize winners
        int grandPrizeWinners = 0;
        List<String> secondPrizeWinners = new ArrayList<>();
        List<String> thirdPrizeWinners = new ArrayList<>();
     // Iterate through each ticket in the 'tickets' list
        for (int[] ticketArray : tickets)
        {
        	// Calculate the number of matches on the ticket with winning numbers
            int matches = AM_ProjectMethods.countMatchingNumbers(winningNumbersArray, ticketArray);
            if (matches == winningNumbersArray.length)
            {
            	// Ticket matches all winning numbers - Grand Prize winner
                grandPrizeWinners++;
            } 
            else if (matches == winningNumbersArray.length - 1)
            {
            	// Ticket matches all numbers except one - Second Prize winner
                secondPrizeWinners.add(AM_ProjectMethods.formatTicketNumbers(ticketArray));
            } 
            else if (matches == winningNumbersArray.length - 2)
            {
            	// Ticket matches all numbers except two - Third Prize winner
                thirdPrizeWinners.add(AM_ProjectMethods.formatTicketNumbers(ticketArray));
            }// end if
        }// end for loop
        
        // Calculate prize distribution
        double grandPrizeTotal = prizePoolAmount * 0.85;
        double secondPrizeTotal = prizePoolAmount * 0.07;
        double thirdPrizeTotal = prizePoolAmount * 0.08;
        // Calculate the average third prize amount per winning ticket
        double thirdPrizePerTicket = thirdPrizeWinners.size() > 0 ? thirdPrizeTotal / thirdPrizeWinners.size() : 0;
        
        // Declare the final percentage of the prize pool
        final double GRAND_PRIZE_PERCENTAGE = 85.0;
        final double SECOND_PRIZE_PERCENTAGE = 7.0;
        final double THIRD_PRIZE_PERCENTAGE = 8.0;

        // Print report
        System.out.println("\nLottery Prizes Report");
        System.out.println("----------------------\n");
        System.out.println("Lottery Name:        " + lotteryName);
        System.out.printf("Total prize pool:    $%,d%n", prizePoolAmount);
        System.out.printf("Number of tickets:   %,d%n", tickets.size());
        System.out.println("Winning numbers:     " + AM_ProjectMethods.formatTicketNumbers(winningNumbersArray));

        System.out.println("\nGrand prize winners (all numbers match)...");
        System.out.println("  Number of winners: " + grandPrizeWinners);
        System.out.println("  % of prize pool:   " + GRAND_PRIZE_PERCENTAGE);
        System.out.println("  Total prize value: " + df.format(grandPrizeTotal));
        System.out.println("  Prize per ticket:  " + df.format(grandPrizeTotal / grandPrizeWinners));

        // print second prize winners
        System.out.println("\nSecond prize winners (5 numbers match)...");
        System.out.println("  Number of winners: " + secondPrizeWinners.size());
        System.out.println("  % of prize pool:   " + SECOND_PRIZE_PERCENTAGE);
        System.out.println("  Total prize value: " + df.format(secondPrizeTotal));
        System.out.println("  Prize per ticket:  " + df.format(secondPrizeWinners.size() > 0 ? secondPrizeTotal / secondPrizeWinners.size() : 0));
        System.out.print("  Ticket Numbers:    ");
        
     // Iterate through the winners to format the output correctly
        for (int i = 0; i < secondPrizeWinners.size(); i++)
        {
            if (i > 0)
            { // If it's not the first ticket, add spacing
                System.out.print("     "); // Add the desired amount of spaces between tickets
            }// end if
            // Print the ticket, removing brackets and replacing commas correctly
            System.out.print(secondPrizeWinners.get(i).replaceAll("[\\[\\]]", "").replaceAll(", ", ","));
        }// end for Loop
        System.out.println(); // Add a newline at the end
        
        // print the third winners
        System.out.println("\nThird prize winners (4 numbers match)...");
        System.out.println("  Number of winners: " + thirdPrizeWinners.size());
        System.out.println("  % of prize pool:   " + THIRD_PRIZE_PERCENTAGE);
        System.out.println("  Total prize value: " + df.format(thirdPrizeTotal));
        System.out.println("  Prize per ticket:  " + df.format(thirdPrizePerTicket));
        System.out.print("  Ticket Numbers:    ");

        // Keep track of the line length
        int lineLength = 0;
        for (int i = 0; i < thirdPrizeWinners.size(); i++)
        {
        	// Format the ticket numbers: Remove square brackets and adjust comma spacing
            String ticket = thirdPrizeWinners.get(i).replaceAll("[\\[\\]]", "").replaceAll(", ", ", ");

            // Add a tab if not the first ticket, and after every two tickets start a new line
            if (i > 0)
            {
                if (i % 2 == 0)
                {
                    System.out.print("\n                     ");
                    lineLength = 0;
                } // end inner if
                else
                {
                    System.out.print("\t");
                    lineLength += 4; //
                }
            }// end outer if

            System.out.print(ticket);
            lineLength += ticket.length();

            // Check the length of the line to avoid cutting off tickets
            if (lineLength >= 40)
            {
                System.out.print("\n                     ");
                lineLength = 0;
            }
        }// end for loop
        System.out.println(); // Add a newline at the end
        // close the Scanner
        input.close();
        
        System.out.println();// for Spacing only 
        
        System.out.println("\n           ***************BONUS OPTION********************** ");
        // Modify the lists to store the ticket numbers and their occurrences
        List<String> secondPrizeWinnersWithCounts = AM_ProjectMethods.identifyAndCountDuplicates(secondPrizeWinners);
        List<String> thirdPrizeWinnersWithCounts = AM_ProjectMethods.identifyAndCountDuplicates(thirdPrizeWinners);

        // Print the updated lists of second and third prize winners with counts
        System.out.println("\nSecond prize winners: ");
        for (String ticket : secondPrizeWinnersWithCounts)
        {
            System.out.println("  " + ticket);
        }

        System.out.println("\nThird prize winners: ");
        for (String ticket : thirdPrizeWinnersWithCounts)
        {
            System.out.println("  " + ticket);
            
            
        }
    
        
    }// end main 
  
}// end class

