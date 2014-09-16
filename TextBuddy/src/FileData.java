import java.io.*;

public class FileData {
	
	public static void main(String[] args) throws IOException {
		
		String file_name = "C:/Users/Eric/workspace/TextBuddy/src/mytextfile.txt";
		
		try {
			ReadFile file = new ReadFile(file_name);
			String[] aryLines = file.OpenFile();
			
			for(int i = 0; i < aryLines.length; i++) {
				System.out.println(aryLines[i]);
			}
		}
		catch (IOException e) {
			System.out.println( e.getMessage() );
		}
	}
}
