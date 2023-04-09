package com.remainsoftware.e3.hello;

import org.eclipse.jface.action.MenuManager;

//import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.*;
//import org.eclipse.swt.widgets.*;
//import org.eclipse.swt.widgets.List;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class HelloSWT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Window");
		shell.setSize(3000, 2000);
		
	    RowLayout rowLayout = new RowLayout();
	    rowLayout.wrap = false;
	    rowLayout.pack = false;
	    rowLayout.justify = true;
	    rowLayout.marginLeft = 10;
	    rowLayout.marginTop = 10;
	    rowLayout.marginRight = 10;
	    rowLayout.marginBottom = 10;
	    rowLayout.spacing = 10;
	    rowLayout.type = SWT.VERTICAL;
	    shell.setLayout(rowLayout);
		
		
//		new Label().setText("Directory Location:");
		new Label(shell, SWT.NONE).setText("Directory Location:");
		Combo combo = new Combo (shell, 0);
//		combo.setText("C:\\Users\\minhy\\Downloads\\cs463-musical-chairs-thread-concurrency-master\\cs463-musical-chairs-thread-concurrency-master\\AppendString");
		combo.setItems ("C:\\Users\\minhy\\Downloads\\cs463-musical-chairs-thread-concurrency-master\\cs463-musical-chairs-thread-concurrency-master\\AppendString", "C:\\Users\\minhy\\workspaces\\e3");
		combo.select(0);
		
		Button rm = new Button(shell, SWT.CHECK);
		rm.setText("Remove Print Statements");
		
		
		Button rmAll = new Button(shell, SWT.CHECK);
		rmAll.setText("Remove All Print Statements");
		
		
		
//		new Label().setText("Line Number Where Print Statement Starts:");
		new Label(shell, SWT.NONE).setText("Line Number Where Print Statement Starts:");
		Spinner spinner = new Spinner (shell, SWT.BORDER);
		spinner.setMinimum(0);
		spinner.setMaximum(2147483647);
		spinner.setSelection(0);
		spinner.setIncrement(1);
		spinner.setPageIncrement(100);
		
		System.out.println(spinner.getText());
		
//		new Label().setText("Line Number Where Print Statement Ends:");
		new Label(shell, SWT.NONE).setText("Line Number Where Print Statement Ends:");
		Spinner spinner2 = new Spinner (shell, SWT.BORDER);
		spinner2.setMinimum(0);
		spinner2.setMaximum(2147483647);
		spinner2.setSelection(2147483647);
		spinner2.setIncrement(1);
		spinner2.setPageIncrement(100);
		
//		new Label().setText("Variables To Print:");
		new Label(shell, SWT.NONE).setText("Variables To Print:");
		final Text t = new Text(shell, SWT.BORDER | SWT.MULTI);
//		t.setText ("here is some text to be selected");
		Menu bar = new Menu (shell, SWT.BAR);
		shell.setMenuBar (bar);
		MenuItem editItem = new MenuItem (bar, SWT.CASCADE);
		editItem.setText ("Edit");
		Menu submenu = new Menu (shell, SWT.DROP_DOWN);
		editItem.setMenu (submenu);

		MenuItem item = new MenuItem (submenu, SWT.PUSH);
		item.addListener (SWT.Selection, e -> t.selectAll());
		item.setText ("Select &All\tCtrl+A");
		item.setAccelerator (SWT.MOD1 + 'A');
		
		
		Button b = new Button(shell, SWT.PUSH);
		b.setText("Run!");		
		b.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("11");
				System.out.println(combo.getText());
				System.out.println(rm.getSelection());
				System.out.println(rmAll.getSelection());
				System.out.println(spinner.getText());
				System.out.println(spinner2.getText());
				System.out.println(t.getText());
				// Print Statement Generator
		        String[] args = {spinner.getText(), spinner2.getText()};
		        String[] args2 = t.getText().split("\\s+");
		        String[] combinedArray = Arrays.copyOf(args, args.length + args2.length);
		        System.arraycopy(args2, 0, combinedArray, args.length, args2.length);
		        if (rm.getSelection()) {
		        	combinedArray[0] = "remove";
		        }
				Set<String> set = listFilesUsingJavaIO(combo.getText());
		        Object[] setArr = set.toArray();
		        if (rmAll.getSelection()) {
		        	combinedArray[0] = "removeAll";
		        }
		        
		        for (int i = 0; i < setArr.length; i++) {
		            if(setArr[i].toString().substring(setArr[i].toString().length() - 4).equals(".txt")) { // Checks whether the file ends with .txt extension or not.
		                if (combinedArray[0].equals("removeAll")) {
		                    removePrintStatementsFromFile(combo.getText() + "\\" + setArr[i].toString());
		                } else if (combinedArray[0].equals("remove")) { // Removes print statements generated by AutoPrint
		                    Path source = Paths.get("C:\\Users\\minhy\\Downloads\\cs463-musical-chairs-thread-concurrency-master\\cs463-musical-chairs-thread-concurrency-master\\AppendString\\copy\\" + setArr[i].toString());
		                    Path destination = Paths.get("C:\\Users\\minhy\\Downloads\\cs463-musical-chairs-thread-concurrency-master\\cs463-musical-chairs-thread-concurrency-master\\AppendString\\" + setArr[i].toString());
		                    try {
								Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		                } else {
		                	try {
		                	String setArrVar = combo.getText() + "\\" + setArr[i].toString();
		                    run(combinedArray, setArrVar);
		                	} catch (Exception e1) {
		                		System.out.print(e1);
		                	}
		                }
		            }
		        }
		        System.out.println(combo.getText());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("22");
			}
		});
		
		Rectangle clientArea = shell.getClientArea();
		spinner.setLocation(clientArea.x, clientArea.y);
		spinner.pack();
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		
	}
	
	
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
        File a = new File(fileStr);
        File b = new File(fileStr.substring(0, fileStr.lastIndexOf("\\")) + "\\copy" + fileStr.substring(fileStr.lastIndexOf("\\")));
//        File b = new File(fileStr);
        Path copyDirectory = Paths.get(fileStr.substring(0, fileStr.lastIndexOf("\\")) + "\\copy");
        Files.createDirectories(copyDirectory);
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
