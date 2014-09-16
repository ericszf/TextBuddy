// This program saves the text file when the user exit the program.

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TextBuddy {

	static Path _fileUrl;
	static String fileUrlString;
	
	private static final String MESSAGE_INVALID_FORMAT = "invalid command format :%1$s";
	private static final String MESSAGE_ADDED = "added to mytextfile.txt: \"%1$s\"";
	private static final String MESSAGE_DELETED = "deleted from mytextfile.txt: \"%1$s\"";
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %s is now ready for use\n";
	private static final String MESSAGE_SORTED = "Text file contents have been sorted.";
	
	enum COMMAND_TYPE {
		ADD, DISPLAY, DELETE, CLEAR, SORT, EXIT, INVALID
	};
	
	static void argumentError() {
		System.out.println("Argument Error!");
		System.exit(1);
		
	}
	
	static void printWelcomeMessage(String fileName) {
		System.out.printf(MESSAGE_WELCOME, fileName);
	}
	
	private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}
	
	private static String removeFirstWord(String userCommand) {
		return userCommand.replace(getFirstWord(userCommand), "").trim();
	}	
	
	/**
	 * This operation determines which of the supported command types the user
	 * wants to perform
	 * 
	 * @param commandTypeString
	 *            is the first word of the user command
	 */
	private static COMMAND_TYPE determineCommandType(String commandTypeString) {
		if (commandTypeString == null)
			throw new Error("command type string cannot be null!");

		if (commandTypeString.equalsIgnoreCase("add")) {
			return COMMAND_TYPE.ADD;
		} else if (commandTypeString.equalsIgnoreCase("display")) {
			return COMMAND_TYPE.DISPLAY;
		} else if (commandTypeString.equalsIgnoreCase("delete")) {
		 	return COMMAND_TYPE.DELETE;
		} else if (commandTypeString.equalsIgnoreCase("clear")) {
			return COMMAND_TYPE.CLEAR;
		} else if (commandTypeString.equalsIgnoreCase("sort")) {
			return COMMAND_TYPE.SORT;
		} else if (commandTypeString.equalsIgnoreCase("exit")) {
			return COMMAND_TYPE.EXIT;
		} else {
			return COMMAND_TYPE.INVALID;
		}
	}
	
	private static String sortText() {
		
		String tempFileUrl = "";
		
		try {
			ReadFile file = new ReadFile(fileUrlString);
			String[] aryLines = file.removeNumberings();
			Arrays.sort(aryLines);
			
			//Construct the new file that will later be renamed to the original filename.
			tempFileUrl = fileUrlString.substring(0, fileUrlString.length()-4) + ".tmp";
		    File tempFile = new File(tempFileUrl);
		      
		    FileWriter write = new FileWriter(tempFile);
			PrintWriter print_line = new PrintWriter(write);
			
			int countLines = file.readLines();
			
			for(int i = 0; i < countLines; i++) {
				print_line.printf("%d. ", i+1);
	    		print_line.printf("%s" + "%n", aryLines[i]);
			}
			
			print_line.close();

		    File textFile = new File(fileUrlString);
		    
		      //Delete the original file
		      if (!textFile.delete()) {
		        System.out.println("Could not delete file");
		      }

		      //Rename the new file to the filename the original file had.
		      if (!tempFile.renameTo(textFile))
		        System.out.println("Could not rename file");
		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
		    ex.printStackTrace();
		}
		
		return String.format(MESSAGE_SORTED);
	}
	
	private static String displayText() {
		
		try {
			ReadFile file = new ReadFile(fileUrlString);
			String[] aryLines = file.OpenFile();
			
			if(aryLines.length == 0) {
				String text = "mytextfile.txt is empty";
				return text;
			}
			else {
				for(int i = 0; i < aryLines.length; i++) {
					System.out.println(aryLines[i]);
				}
				String text2 = "displayed";
				return text2;
			}
		}
		catch (IOException e) {
			return ( e.getMessage() );
		}
		
	}
	
	private static String clearText() throws FileNotFoundException {
		
		PrintWriter pw = new PrintWriter(fileUrlString);
		pw.close();
		
		String text = "all content deleted from mytextfile.txt";
		return text;
		
		
	}
	private static String addText(String command) {
		
		String extractedText = removeFirstWord(command);
		
		try {
			WriteFile data = new WriteFile(fileUrlString, true);
			data.writeToFile(extractedText);
		}
		catch (IOException e) {
			System.out.println( e.getMessage() );
		}
		
		return String.format(MESSAGE_ADDED, extractedText);
		
	}
	
	private static void showToUser(String text) {
		if(text.equals("displayed")) {
		}
		else {
			System.out.println(text);
		}
	}
	
	private static String deleteText(String command) {
		
		String lineNumber = removeFirstWord(command);
		int lineToRemove = 0;
		String deletedText = "";
		String extractedText = "";
		String tempFileUrl = "";
		
		try {
			lineToRemove = Integer.parseInt(lineNumber);
		}
		catch (NumberFormatException e) {
		}
		
		try {
			ReadFile file = new ReadFile(fileUrlString);
			String[] aryLines = file.OpenFile();
		      
		    //Construct the new file that will later be renamed to the original filename.
			tempFileUrl = fileUrlString.substring(0, fileUrlString.length()-4) + ".tmp";
		    File tempFile = new File(tempFileUrl);
		      
		    FileWriter write = new FileWriter(tempFile);
			PrintWriter print_line = new PrintWriter(write);
				
		    //Read from the original file and write to the new
		    //unless line matches data to be removed.
			int numOfLines = file.readLines();
			
			if(lineToRemove == 0 || lineToRemove > numOfLines) {
				String errorText = "No such line exist!";
				print_line.close();
				return errorText;
			}
			
		    for(int i = 0, j = 0; i < numOfLines; i++) { // i is for old file iteration, j is for new file iteration
		    	if(i == lineToRemove - 1) {
		    		deletedText = removeFirstWord(aryLines[i]);
		    	}
		    	else {
		    		extractedText = removeFirstWord(aryLines[i]);
		    		
		    		print_line.printf("%d. ", j+1);
		    		print_line.printf("%s" + "%n", extractedText);
		    		j++;
		    	}
		    }
		 
		    print_line.close();

		    File textFile = new File(fileUrlString);
		    
		      //Delete the original file
		      if (!textFile.delete()) {
		        System.out.println("Could not delete file");
		      }

		      //Rename the new file to the filename the original file had.
		      if (!tempFile.renameTo(textFile))
		        System.out.println("Could not rename file");

		    }
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
		    ex.printStackTrace();
		}
		
		return String.format(MESSAGE_DELETED, deletedText);
	}
	
	public static String executeCommand(String userCommand) throws FileNotFoundException {
		if (userCommand.trim().equals(""))
			return String.format(MESSAGE_INVALID_FORMAT, userCommand);

		String commandTypeString = getFirstWord(userCommand);

		COMMAND_TYPE commandType = determineCommandType(commandTypeString);

		switch (commandType) {
		case ADD:
			return addText(userCommand);
		case DISPLAY:
			return displayText();
		case DELETE:
			return deleteText(userCommand);
		case CLEAR:
			return clearText();
		case SORT:
			return sortText();
		case INVALID:
			return String.format(MESSAGE_INVALID_FORMAT, userCommand);
		case EXIT:
			System.exit(0);
		default:
			//throw an error if the command is not recognized
			throw new Error("Unrecognized command type");
		}
	}
	
	private static String readUserCommand() {
		System.out.printf("command: ");
		String command = scanner.nextLine();
		return command;
	}
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) throws FileNotFoundException {
		
		
		if(args.length <=0){
			argumentError();
		}
		
		String fileName = args[0];
		_fileUrl = Paths.get(fileName);
		fileUrlString = _fileUrl.toString();
		
		printWelcomeMessage(fileName);
		while (true) {
			String userCommand = readUserCommand();
			String feedback = executeCommand(userCommand);
			showToUser(feedback);
		}
	}
}
