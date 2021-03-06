import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class ReadFile {

	private String path;
	
	public ReadFile(String file_path) {
		path = file_path;
	}
	
	public int readLines() throws IOException {
		
		FileReader file_to_read = new FileReader(path);
		BufferedReader bf = new BufferedReader(file_to_read);
		
		String aLine;
		int numberOfLines = 0;
		
		while ((aLine = bf.readLine()) != null) {
			numberOfLines++;
		}
		bf.close();
		
		return numberOfLines;
	
	}
	
	public String[] OpenFile() throws IOException {
		
		FileReader fr = new FileReader(path);
		BufferedReader textReader = new BufferedReader(fr);
		
		int numberOfLines = readLines();
		String[]textData = new String[numberOfLines];
		
		for(int i = 0; i < numberOfLines; i++) {
			textData[i] = textReader.readLine();
		}
		
		textReader.close();
		return textData;	
	}
	
	public String[] removeNumberings() throws IOException {
		
		FileReader fr = new FileReader(path);
		BufferedReader textReader = new BufferedReader(fr);
		
		int numberOfLines = readLines();
		String[]numberedTextData = new String[numberOfLines];
		
		for(int i = 0; i < numberOfLines; i++) {
			numberedTextData[i] = textReader.readLine();
		}
		
		String[]textData = new String[numberOfLines];
		
		for(int i = 0; i < numberOfLines; i++) {
			textData[i] = numberedTextData[i].replace((numberedTextData[i].trim().split("\\s+")[0]), "").trim();
		}
		
		textReader.close();
		return textData;
	}
}
