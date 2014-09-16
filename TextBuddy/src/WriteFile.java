import java.io.*;

public class WriteFile {
	private String path;
	private boolean append_to_file = false;
	
	public WriteFile(String file_path) {
		path = file_path;
	}
	
	public WriteFile(String file_path, boolean append_value) {
		path = file_path;
		append_to_file = append_value;
	}
	
	public void writeToFile(String textLine) throws IOException {
		
		FileWriter write = new FileWriter(path, append_to_file);
		PrintWriter print_line = new PrintWriter(write);
		
		ReadFile data = new ReadFile(path);
		int numOfLines = data.readLines();
		
		print_line.printf("%d. ", numOfLines + 1);
		print_line.printf("%s" + "%n", textLine);
		
		print_line.close();
	}
}
