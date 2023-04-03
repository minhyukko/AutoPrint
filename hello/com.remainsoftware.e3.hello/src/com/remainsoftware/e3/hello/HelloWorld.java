package com.remainsoftware.e3.hello;

import org.eclipse.jface.action.MenuManager;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Menu;

public class HelloWorld extends ViewPart {

	public HelloWorld() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		Button button = new Button(parent, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		button.setText("Code world, no blanket!!!");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageDialog.openInformation(parent.getShell(), "Code world, no blanket!!!", "Hello Research!");
				System.out.println(1);
				// Print Statement Generator
		        Set<String> set = listFilesUsingJavaIO("C:\\Users\\minhy\\Downloads\\cs463-musical-chairs-thread-concurrency-master\\cs463-musical-chairs-thread-concurrency-master\\AppendString");
		        Object[] setArr = set.toArray();
		        String[] args = {""};
		        for (int i = 0; i < setArr.length; i++) {
		            if(setArr[i].toString().substring(setArr[i].toString().length() - 4).equals(".txt")) { // Checks whether the file ends with .txt extension or not.
		                if (args[0].equals("remove")) {
		                    removePrintStatementsFromFile(setArr[i].toString());
		                } else {
		                	try {
		                	String setArrVar = "C:\\Users\\minhy\\Downloads\\cs463-musical-chairs-thread-concurrency-master\\cs463-musical-chairs-thread-concurrency-master\\AppendString\\" + setArr[i].toString();
		                    run(args, setArrVar);
		                	} catch (Exception e1) {
		                		System.out.print(e1);
		                	}
		                }
//		                System.out.println(setArr[i].toString().substring(setArr[i].toString().length() - 4));
		            }
		        }
			}
		});
		
		registerContextMenu(parent);

	}
	
	private void registerContextMenu(Composite parent) {
		MenuManager manager = new MenuManager();
		Menu contextMenu = manager.createContextMenu(parent);
		getSite().registerContextMenu(manager, null);
		parent.setMenu(contextMenu);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
    //A Node structure to store the hierarchy information of the code.
    //https://www.geeksforgeeks.org/generic-treesn-array-trees/
    public class Node {
        int data;
        ArrayList<Node> children;
        Queue<Integer> lineNumQueue = new LinkedList<>();
        Queue<String> varNameQueue = new LinkedList<>();
        Queue<String> spaceQue = new LinkedList<>();

        Node(int data)
        {
            this.data = data;
            this.children = new ArrayList<Node>();
        }
    }

    // Method 1
    // TO append string into a file
    public static void appendStrToFile(String fileName,
                                       String str)
    {
        // Try block to check for exceptions
        try {

            // Open given file in append mode by creating an
            // object of BufferedWriter class
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(fileName, true));

            // Writing on output stream
            out.write(str);
            // Closing the connection
            out.close();
        }

        // Catch block to handle the exceptions
        catch (IOException e) {

            // Display message when exception occurs
            System.out.println("exception occurred" + e);
        }
    }

    public static void writeAfterNthLine(String filename, String text, int lineno) throws IOException{
        File file = new File(filename);
        File temp = File.createTempFile("temp-file-name", ".tmp");
        BufferedReader br = new BufferedReader(new FileReader( file ));
        PrintWriter pw =  new PrintWriter(new FileWriter( temp ));
        String line;
        int lineCount = 0;
        while ((line = br.readLine()) != null) {
            pw.println(line);
            if(lineCount==lineno){
                pw.println(text);
            }
            lineCount++;
        }
        br.close();
        pw.close();
        file.delete();
        temp.renameTo(file);
    }

    // https://stackoverflow.com/questions/106770/standard-concise-way-to-copy-a-file-in-java/16600787#16600787
    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if(!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        }
        finally {
            if(source != null) {
                source.close();
            }
            if(destination != null) {
                destination.close();
            }
        }
    }

    // Creates a string with spaces needed for print statement indention.
    public static String spaceStr(String inputStr) {
        String str = inputStr;
        String space = "";
        for (int i = 0; i < str.length(); i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                space += str.charAt(i);
            } else {
                break;
            }
        }
        return space;
    }

    public static void run(String[] args, String fileStr) throws Exception
    {
        // Creating a sample file with some random text
        String fileName = fileStr;
        File a = new File("C:\\Users\\minhy\\Downloads\\cs463-musical-chairs-thread-concurrency-master\\cs463-musical-chairs-thread-concurrency-master\\AppendString\\Geek.txt");
        File b = new File("C:\\Users\\minhy\\Downloads\\cs463-musical-chairs-thread-concurrency-master\\cs463-musical-chairs-thread-concurrency-master\\AppendString\\Geek2.txt");
        copyFile(a, b);

        // Line numbers to limit print
//        int startNum = 0;
//        int finishNum = 700;
        int startNum = 0;
        int finishNum = 2147483647; // Default is maximum value of integer

        //If the maximum value is given through command line
        if (args.length >= 2) {
            startNum = Integer.parseInt(args[0]);
            finishNum = Integer.parseInt(args[1]);
            if (startNum > finishNum) {
                System.out.println("Starting line number cannot be bigger then finishing line number");
                System.exit(-1);
            }
        }

        //Variables that

        System.out.println(startNum);
        System.out.println(finishNum);

        //A Stack structure to store the hierarchy information of the code.
        ArrayList<Integer> hierarchy = new ArrayList<Integer>();

        // Try block to check for exceptions
        try {

            // Again operating same operations by passing
            // file as
            // parameter to read it
//            BufferedWriter out = new BufferedWriter(
//                    new FileWriter(fileName));
//
//            // Writing on. file
//            out.write("Hello World:\n");
//
//            out.write("hfuerfr", 1, 2);

//            writeAfterNthLine(fileName, "pineapple", 0);

            // Prints a print statement in every line.
            long count = Files.lines(Paths.get(fileName)).count();
            System.out.println("Total Lines: " + count);
            for (int i = 0; i < count * 2; i = i+2) {
//                writeAfterNthLine(fileName, "System.out.println();", i);
            }


            RandomAccessFile file = new RandomAccessFile(fileName, "r");
            String str;
            Queue<Integer> lineNumQueue = new LinkedList<>();
            Queue<String> varNameQueue = new LinkedList<>();
            Queue<String> spaceQue = new LinkedList<>();
            Queue <String> toStrQue = new LinkedList<>();

            // Queue of Class Name and Number
            List<String> className = new LinkedList<>(); //name of class
            Queue<Integer> classNum = new LinkedList<>(); //lineNum of where the class starts

            int lineNum = 0;
            while ((str = file.readLine()) != null) {
                System.out.println(str);
                Matcher matcher = Pattern
                        .compile("(?<=int |float |String |double |[a-zA-Z_\\[\\]]\\[] )([a-zA-Z_]\\w*)(?=,|\\)|;|\\s)|([a-zA-Z_]\\w*)(?=,|;|\\s*=)")
                        .matcher(str);

                matching:
                while (matcher.find()) {
                    if (str.contains("return")) {
                        continue;
//                        lineNum--;
                    }
                    System.out.println(matcher.group());
//                    writeAfterNthLine(fileName, "System.out.println();", lineNum);

                    // Checks if there are specific variables that the user wants to print is indicated starting at args[2]
                    // Only adds the variables to the queue that are passed through the argument starting at args[2]
                    if (args.length >= 3) {
                        int index = 2;
                        while (true) {
                            if (index == args.length) { // Skips to next iteration in matching if a variable is not found in the arguments.
                                continue matching;
                            }
                            if(args[index].equals(matcher.group())) {
                                break;
                            }
                            index++;
                        }
                    }
                    lineNumQueue.add(lineNum);
                    varNameQueue.add(matcher.group());
                    spaceQue.add(spaceStr(str));

                    if (!str.contains("int") && !str.contains("float") && !str.contains("String") && !str.contains("double")) {
                        String arr[] = str.split(" ");
                        int i = 0;
                        while (arr[i].equals("")) {
                            i++;
                        }
                        toStrQue.add(arr[i]);
                    }

                }

                Matcher matcher1 = Pattern
                        .compile("class")
                        .matcher(str);
                while (matcher1.find()) {
                    String[] words = str.split("\\s+");
                    int i = 0;
                    while (i < words.length) {
                        if (words[i].equals("class")) {
                            i++;
                            break;
                        }
                        i++;
                    }
                    System.out.println(words[i]);
                    className.add(words[i]);
                    classNum.add(lineNum);
                }

                Matcher matcher2 = Pattern
                        .compile("\\{")
                        .matcher(str);
                while (matcher2.find()) {
                    hierarchy.add(lineNum);
                    System.out.println(matcher2);
                }

                Matcher matcher3 = Pattern
                        .compile("\\}")
                        .matcher(str);
                while (matcher3.find()) {
                    hierarchy.add(lineNum * (-1));
                    System.out.println(matcher3);
                }
                lineNum++;
            }

            file.close();

            // Saves the hierarchy information in the 2D array (first column - start; second column - end)
            int arr[][] = new int[hierarchy.size() / 2][2];
            int arrNum = 0;
            for(int i = 0; i < hierarchy.size(); i++) {
                if (hierarchy.get(i) < 0) {
                    arr[arrNum][1] = hierarchy.remove(i--);
                    arr[arrNum++][0] = hierarchy.remove(i--);
                }
            }

            // Saves variables that are accessible in the scope.
            List<Integer> lineNumQueue1 = new LinkedList<>(lineNumQueue); // Queues are duplicated so that I can pop it twice.
            List<String> varNameQueue1 = new LinkedList<>(varNameQueue);
            List<String> spaceQue1 = new LinkedList<>(spaceQue);
            List<String>[] arrayOfLists = new ArrayList[arr.length];
            for (int i = 0; i < arrayOfLists.length; i++) {
                arrayOfLists[i] = new ArrayList<String>();
            }
            for (int i = 0; i < lineNumQueue.size(); i++) {
                for (int j = 0; j < arr.length; j++) {
                    if (lineNumQueue1.get(i) > arr[j][0] && lineNumQueue1.get(i) < (arr[j][1] * (-1))) {
                        arrayOfLists[j].add(varNameQueue1.get(i));
                    }
                }
            }
            // Remove Duplicates in the variable list
            for (int i = 0; i < arrayOfLists.length; i++) {
                arrayOfLists[i] = (arrayOfLists[i]).stream().distinct().collect(Collectors.toList());
            }

//            writeAfterNthLine(fileName, "System.out.println();", 1);
            System.out.println(arrayOfLists[0]);

            int lineCount = 0; // Tracks number of lines added due to the addition of the print statements.

            for(int i = 0; lineNumQueue.size() > 0 ; i++) {
                if (startNum < (lineNumQueue.peek() + lineCount) && finishNum > (lineNumQueue.peek() + lineCount)) {
                    writeAfterNthLine(fileName, spaceQue.poll() + "System.out.println(" + "\"" + varNameQueue.peek() + ": " + "\" + " + varNameQueue.poll() + ");", lineNumQueue.poll() + lineCount++);
                } else {
                    spaceQue.poll();
                    varNameQueue.poll();
                    lineNumQueue.poll();
                }
            }

            // Extracts variable contained in the class to generate a toString() method
//            List<String>[] variables = new ArrayList[classNum.size()];
//            int classNumI = 0;
//            while (classNum.size() > 0) {
//                int num = classNum.poll();
////                String strName = className.poll();
//                while (true) {
//                    for (int j = 0; j < arr.length; j++) {
//                        if (arr[j][0] == num) {
//                            variables[classNumI] = arrayOfLists[j];
//                            String s = "";
//                            for (int k = 0; k < variables[classNumI].size(); k++) {
//                                s += " \"" + variables[classNumI].get(k) + ": \" + " + variables[classNumI].get(k);
//                                if (k != variables[classNumI].size() - 1) {
//                                    s += " + ";
//                                }
//                            }
//                            writeAfterNthLine(fileName, "public String toString() {\n" +
//                                    "        return " + s + ";\n" +
//                                    "    }", 0); // Line number needs to be fixed later.
//                            lineCount += 3;
//                            break;
//                        }
//                    }
//                    if (variables[classNumI] != null) {
//                        break;
//                    } else {
//                        num++;
//                    }
//                }
//                classNumI++;
//            }


            //Detects variable through a string https://stackoverflow.com/questions/31731479/detecting-variables-through-a-string
//            String javaCode = "int a = 100;\n" +
//                    "float bweg = 110;\n" +
//                    "String c4rwg = \"Hello World\";" +
//                    "double d, egrwe311, f, g = 1.0, h;";
//
//            Matcher matcher = Pattern
//                    .compile("(?<=int|float|String|double|char|long)([a-zA-Z_]\\w*)(?=,|;|\\s)|([a-zA-Z_]\\w*)(?=,|;|\\s*=)")
//                    .matcher(javaCode);
//            while (matcher.find()) {
//                System.out.println(matcher.group());
//            }


            // Closing file connections
//            out.close();
        }

        // Catch block to handle exceptions
        catch (Exception e) { //IOException

            // Display message when error occurs
            System.out.println("Exception Occurred" + e);
        }

        // Now appendinggiven str to above
        // created file
        String str = "This is Min";

        // Calling the above method
        appendStrToFile(fileName, str);

        /*
        public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .toString();
        }
        */
        String toStr = "public String toString() {\n" +
                "return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)\n";


    }

    // https://www.baeldung.com/java-list-directory-files
    public static Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    // Only remove what was added?
    public static void removePrintStatementsFromFile(String filePath) {

        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            String output = "";
            while ((line = reader.readLine()) != null) {
                if (!line.contains("System.out.print")) {
                    output += line + "\n";
                }
            }
            reader.close();
            FileWriter writer = new FileWriter(file);
            writer.write(output);
            writer.close();
            System.out.println("Print statements removed successfully!");
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }

    }

}
