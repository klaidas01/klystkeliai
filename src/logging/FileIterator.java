package logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileIterator implements Iterator {
    private int offset;
    private String fileName;

    public FileIterator(String fileName) {
        offset = 1;
        this.fileName = fileName;
    }

    @Override
    public boolean hasNext() {
        boolean result = false;
        try {
            File myObj = new File(fileName);
            myObj.createNewFile();
            Scanner myReader = new Scanner(myObj);
            for(int i = 0; i < offset - 1; i++) myReader.nextLine();
            result = myReader.hasNextLine();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            return false;
        } catch (IOException e) {
        	System.out.println("An error occurred.");
			e.printStackTrace();
		}
        return result;
    }

    @Override
    public String next() {
        String result = "CHECK CONSOLE FOR ERRORS";
        try {
            File myObj = new File(fileName);
            myObj.createNewFile();
            Scanner myReader = new Scanner(myObj);
            for(int i = 0; i < offset - 1; i++) myReader.nextLine();
            result = myReader.nextLine();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            System.out.println("No such element in file");
        } catch (IOException e) {
        	System.out.println("An error occurred.");
			e.printStackTrace();
		}
        offset++;
        return result;
    }
}
