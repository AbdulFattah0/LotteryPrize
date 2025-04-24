import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Program Name: AM_ProjectMethods.java
 * Purpose: Methods used in the Project
 * Coder: Abdulfatiah Marouf 1144451
 * Date: Nov 23, 2023
 */

public class AM_ProjectMethods
{
	/**
	 * Method Name: getNextSeries <br>
	 * Purpose: Reads the next line from a file Scanner and parses it into an integer array .   <br>
	 * Accepts: file Scanner to read the next line. <br>
	 * Returns:An integer array containing the parsed numbers .<br>
	 * Coder: AM<br>
	 * Date: Nov. 25, 2023  <br>
	 */
    public static int[] getNextSeries(Scanner fileReader)
    {
    	// Read the next line from the file using the provided Scanner
	    String line = fileReader.nextLine();
	    // Split the line by commas or newlines to obtain individual tokens
	    String[] tokens = line.split(",|\\R");
	    // Create an array to store the parsed integers
	    int[] intArray = new int[tokens.length];
	    // Parse each token into an integer and store it in the array
	    for (int i = 0; i < tokens.length; i++) 
	    {

	    	intArray[i] = Integer.parseInt(tokens[i].trim());

	    }
	     // Return the array containing the parsed integers from the file line
	    return intArray;
    }// end method 
    
    /**
	 * Method Name: countMatchingNumbers <br>
	 * Purpose: counts the number of matching elements between two integers  <br>
	 * Accepts: Two arrays of type int. <br>
	 * Returns: An integer representing the count of matching numbers between the two arrays .<br>
	 * Coder: AM<br>
	 * Date: Nov. 25, 2023  <br>
	 */
    
 // write another class method called countMatchingNumbers()
    public static int countMatchingNumbers(int[]num1TicketArray, int[]winNumArray) 
    {
    	int index1 = 0; // Pointer for num1TicketArray
        int index2 = 0; // Pointer for winNumArray
        int counter = 0; // Counter to track matching numbers
    	
    	// Loop through both arrays to find matching elements
    	while (index1 < num1TicketArray.length && index2 < winNumArray.length)
    		if  (num1TicketArray[index1] == winNumArray[index2])
    		{
	            // Found a match, increment count and move both pointers
	        	counter++;
	            index1++;
	            index2++;

	        } 
    		else if (num1TicketArray[index1] < winNumArray[index2])
    		{
	            // If element in array1 is smaller, move index1
	            index1++;

	        }
    		else 
    		{
	            // If element in array2 is smaller, move index2
	            index2++;
	        }
    	return counter;
    }// end method 
    
    /**
	 * Method Name: formatTicketNumbers <br>
	 * Purpose: Formats an array of ticket numbers into a string representation.  <br>
	 * Accepts: An array of type int . <br>
	 * Returns: A string containing the formatted ticket numbers separated by commas..<br>
	 * Coder: AM<br>
	 * Date: Nov. 25, 2023  <br>
	 */
    
    public static String formatTicketNumbers(int[] ticketNumbers)
    {
        StringBuilder formattedNumbers = new StringBuilder();
        // Append each ticket number to the string builder with comma separation.
        for (int i = 0; i < ticketNumbers.length; i++)
        {
            formattedNumbers.append(ticketNumbers[i]);
            if (i != ticketNumbers.length - 1)
            {
            	// Add comma if not the last number.
                formattedNumbers.append(", ");
            }
        }
        return formattedNumbers.toString();
    }// end method
    
    /**
     * THIS METHOD FOR THE BONUS OPTION 
	 * Method Name:  identifyAndCountDuplicates<br>
	 * Purpose:  Identifies duplicate tickets in a list and counts their occurrences. <br>
	 * Accepts:  List of strings containing tickets<br>
	 * Returns: List of strings with unique tickets and their counts .<br>
	 * Coder: AM<br>
	 * Date: Nov. 25, 2023  <br>
	 */
    public static List<String> identifyAndCountDuplicates(List<String> winners) {
        List<String> winnersWithCounts = new ArrayList<>();
        
          // Iterate through the list of tickets
        for (int i = 0; i < winners.size(); i++)
        {
            String currentTicket = winners.get(i);

            // Check if current ticket is already counted
            if (!winnersWithCounts.contains(currentTicket)) 
            {
                int count = 1;
                
                // Count occurrences of the current ticket in the remaining list.
                for (int j = i + 1; j < winners.size(); j++)
                {
                    if (currentTicket.equals(winners.get(j)))
                    {
                        count++;
                    }
                }// end inner for 
                
             // If the ticket has duplicates, add count to the ticket
                if (count > 1) 
                {
                    winnersWithCounts.add(currentTicket + " (" + count + ")");
                } 
                else 
                {
                	// If the ticket is unique, add it to the list
                    winnersWithCounts.add(currentTicket);
                }
            }// end outer if 
        }// end for loop

        return winnersWithCounts;
    }// end method
}
//End Class