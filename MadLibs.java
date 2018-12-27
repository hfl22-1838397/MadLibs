import java.util.*;
import java.io.*;

// Phil Lin
// 11/13/2018
// CSE142
// TA: Oliver Eriksen
// Assignment #6
//
// This program asks the user to choose a mad lib file and provide
// various words to complete a funny story. The result will be written
// in an output file and can be read depending on user's command.

public class MadLibs {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);

        intro();
        System.out.println();

        //prompt the user to input a choice
        System.out.print("(C)reate mad-lib, (V)iew mad-lib, (Q)uit? ");
        String choice = console.nextLine();
        while (!(choice.equalsIgnoreCase("q"))) {
            if (choice.equalsIgnoreCase("v")) {
                // call the view method when the user types "v", case-insensitive.
                view(console);
            } else if (choice.equalsIgnoreCase("c")) {
                // call the create method when he types "c", case-insensitive.
                create(console);
            }
            System.out.print("(C)reate mad-lib, (V)iew mad-lib, (Q)uit? ");
            choice = console.nextLine();//update String choice so the while loop could go on
        }
    }

    // give the instruction of the program to the user.
    public static void intro(){
        System.out.println("Welcome to the game of Mad Libs.");
        System.out.println("I will ask you to provide various words");
        System.out.println("and phrases to fill in a story.");
        System.out.println("The result will be written to an output file.");
    }

    // check whether the input file exists and guide the user to input a valid name
    // pass the Scanner console to get the information from the user
    // return the scanner of the existed file
    public static Scanner getFile(Scanner console) throws FileNotFoundException{
        System.out.print("Input file name: ");
        String fileName = console.nextLine();
        //the while loop ends unless the file with the input name exists
        while(!(new File(fileName).exists())){
            System.out.print("File not found. Try again: ");
            fileName = console.nextLine();
        }
        File correctFile = new File(fileName);
        Scanner input = new Scanner(correctFile);

        return input;
    }

    // View - read and print the content of file that the user selects
    // pass the Scanner console to get teh information from user
    public static void view(Scanner console) throws FileNotFoundException{
        Scanner file = getFile(console);
        System.out.println();

        while(file.hasNextLine()){
            System.out.println(file.nextLine());
        }
        System.out.println();
    }

    // check if the placeholder starts with a vowel
    // pass String placeholder so it can be checked
    // return a corresponding string "a " or "an "
    public static String aOrAn(String placeholder){
        placeholder = placeholder.toLowerCase();
        if(placeholder.startsWith("a") || placeholder.startsWith("e") ||
           placeholder.startsWith("i") || placeholder.startsWith("o") ||
           placeholder.startsWith("u")){
            return "an ";
        }
        return "a ";
    }

    // Create - create a mad lib
    // pass Scanner console to read the user's input
    public static void create(Scanner console) throws FileNotFoundException{
        Scanner file = getFile(console);

        // create anm output file so we can save file into it
        System.out.print("Output file name: ");
        String outputName = console.nextLine();
        PrintStream output = new PrintStream(new File(outputName));
        System.out.println();

        // scan through the file and replace every placeholder
        while(file.hasNextLine()){
            Scanner lineScanner = new Scanner(file.nextLine());
            while(lineScanner.hasNext()){
                String word = lineScanner.next();
                if(word.startsWith("<")){
                    String type = word.substring(1, word.length()-1)
                            .replace("-", " ");
                    System.out.print("Please type " + aOrAn(type) + type + ": ");
                    output.print(console.nextLine() + " "); //save the words into the output file
                }else{
                    output.print(word + " "); //save words into the output file
                }
            }
            output.println();
        }
        System.out.println("Your mad-lib has been created!");
        System.out.println();
    }
}
