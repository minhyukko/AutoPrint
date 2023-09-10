package com.remainsoftware.e3.hello;

import javaParser.JavaParserExample;
import javaParser.VariableNameExtractor;
import org.eclipse.jface.action.MenuManager;

//import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.eclipse.swt.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

//import org.eclipse.swt.widgets.*;
//import org.eclipse.swt.widgets.List;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

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
		shell.setSize(800, 800);
		
	    RowLayout rowLayout = new RowLayout();
	    rowLayout.wrap = false;
	    rowLayout.pack = true;
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

		Combo combo = new Combo (shell, SWT.NONE);
//		combo.setText("C:\\Users\\minhy\\Downloads\\cs463-musical-chairs-thread-concurrency-master\\cs463-musical-chairs-thread-concurrency-master\\AppendString");
//		combo.setItems ("C:\\Users\\minhy\\Downloads\\cs463-musical-chairs-thread-concurrency-master\\cs463-musical-chairs-thread-concurrency-master\\AppendString", "C:\\Users\\minhy\\workspaces\\e3");
//		combo.setItems ("Write your file directory location here:", "C:");
		combo.setItems ("Write your file directory location here:");
		combo.select(0);
		
		
		
        // Create a label to display the selected text
//        Label label = new Label(shell, SWT.NONE);
//        label.setText("Selected item: ");
//        label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
        
    	Table table = new Table (shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
    	table.setVisible(false);
//    	for (int i=0; i<12; i++) {
//    		TableItem item = new TableItem (table, SWT.NONE);
//    		item.setText ("Item " + i);
//    	}
    	Rectangle clientArea = shell.getClientArea();
    	table.setBounds (clientArea.x, clientArea.y, 100, 100);
    	table.addListener (SWT.Selection, event -> {
    		String string = event.detail == SWT.CHECK ? "Checked" : "Selected";
    		System.out.println (event.item + " " + string);
    	});
//    	table.setSize(200, 200);
    	
		Tree tree = new Tree(table, SWT.BORDER | SWT.CHECK);
		tree.setSize(400, 90);
		tree.addListener(SWT.Selection, event -> {
			if (event.detail == SWT.CHECK) {
				System.out.println("CHECK");
				TreeItem item = (TreeItem) event.item;
				boolean checked = item.getChecked();
				checkItems(item, checked);
				checkPath(item.getParentItem(), checked, false);
			}
		});
		
		
		shell.setText("AutoPrint");
		final TabFolder tabFolder = new TabFolder (shell, SWT.BORDER);
		tabFolder.setVisible(false);
//		for (int i=0; i<4; i++) {
//	        TabItem tabItem1 = new TabItem(tabFolder, SWT.NONE);
//	        tabItem1.setText("IntListVar" + i + ".java");
//	        
//	        Composite composite1 = new Composite(tabFolder, SWT.NONE);
//	        composite1.setLayout(new GridLayout(2, false));
//	        
//			Button button = new Button (composite1, SWT.PUSH);
//			button.setText ("Line Number Where Print Statement Starts:");
//
//
//	        Spinner spinner1 = new Spinner(composite1, SWT.NONE);
//	        spinner1.setMinimum(0);
//	        spinner1.setMaximum(100);
//	        spinner1.setSelection(0);
//	        
//	        Button button2 = new Button (composite1, SWT.PUSH);
//			button2.setText ("Line Number Where Print Statement Ends:");
//
//	        Spinner spinner21 = new Spinner(composite1, SWT.NONE);
//	        spinner21.setMinimum(0);
//	        spinner21.setMaximum(100);
//	        spinner21.setSelection(50);
//
//	        tabItem1.setControl(composite1);
//		}
		tabFolder.pack ();
		
//		System.out.println(combo);
//		Set<String> set = listFilesUsingJavaIO(combo.getText());
//        Object[] setArr = set.toArray();
        
//        Tree tree1 = new Tree(shell, SWT.BORDER | SWT.CHECK);
//
//        TreeItem root = new TreeItem(tree1, SWT.NONE);
//        root.setText("Root");
//
//        TreeItem item1 = new TreeItem(root, SWT.NONE);
//        item1.setText("Item 1");
//
//        TreeItem item2 = new TreeItem(root, SWT.NONE);
//        item2.setText("Item 2");
//
//        TreeItem item3 = new TreeItem(root, SWT.NONE);
//        item3.setText("Item 3");
//        
//        TreeItem root2 = new TreeItem(tree1, SWT.NONE);
//        root2.setText("Root2");
//
//        TreeItem item11 = new TreeItem(root2, SWT.NONE);
//        item11.setText("Item 11");
//
//        TreeItem item21 = new TreeItem(root2, SWT.NONE);
//        item21.setText("Item 21");
//
//        TreeItem item31 = new TreeItem(root2, SWT.NONE);
//        item31.setText("Item 31");
//
//
//        tree1.addSelectionListener(new SelectionAdapter() {
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                if (e.item instanceof TreeItem) {
//                	System.out.println(item31.getChecked());
//                    tree1.setSize(400, 200);
//                    TreeItem treeItem = (TreeItem) e.item;
//                    boolean checked = treeItem.getChecked();
//                    System.out.println(treeItem.getText() + " checked: " + checked);
//                    
//                }
//            }
//        });
        
        ArrayList<TreeItem> ti = new ArrayList<TreeItem>();
        ArrayList<ArrayList<TreeItem>> tiVar = new ArrayList<>();
        ArrayList<Spinner> start = new ArrayList<Spinner>();
        ArrayList<Spinner> end = new ArrayList<Spinner>();
        ArrayList<Composite> comp = new ArrayList<Composite>();
        ArrayList<TabItem> tabitem = new ArrayList<TabItem>();
		
        combo.addListener(SWT.Modify, new Listener() {
            @Override
            public void handleEvent(Event event) {
                // This event is triggered whenever the content of the Combo changes
            	for (int i = 0; i < ti.size(); i++) {
            		ti.get(i).dispose();
            		start.get(i).dispose();
            		end.get(i).dispose();
            		comp.get(i).dispose();
            		tabitem.get(i).dispose();
            		for (int j = 0; j < tiVar.get(i).size(); j++) {
            			tiVar.get(i).get(j).dispose();
            		}
            	}
            	
            	
                String selectedText = combo.getText();
                System.out.println("User typed: " + selectedText);
                
                File directory = new File(selectedText);
                ArrayList<String> listJavaFiles = findJavaFiles(directory);
                System.out.println("List Java Files:" + listJavaFiles);
//                label.setText(listJavaFiles.toString());
//                tiVar = new ArrayList[listJavaFiles.size()];
//                final TabFolder tabFolder = new TabFolder (shell, SWT.BORDER);
            	for (int i=0; i<listJavaFiles.size(); i++) {
            		table.setVisible(true);
            		tabFolder.setVisible(true);
        	        TabItem tabItem1 = new TabItem(tabFolder, SWT.NONE);
        	        tabItem1.setText(listJavaFiles.get(i));
        	        
        	        Composite composite1 = new Composite(tabFolder, SWT.NONE);
        	        composite1.setLayout(new GridLayout(2, false));
        	        
        			Button button = new Button (composite1, SWT.PUSH);
        			button.setText ("Line Number Where Print Statement Starts:");


        	        Spinner spinner1 = new Spinner(composite1, SWT.NONE);
        	        spinner1.setMinimum(0);
        	        spinner1.setMaximum(100000000);
        	        spinner1.setSelection(0);
        	        
        	        Button button2 = new Button (composite1, SWT.PUSH);
        			button2.setText ("Line Number Where Print Statement Ends:");

        	        Spinner spinner21 = new Spinner(composite1, SWT.NONE);
        	        spinner21.setMinimum(0);
        	        spinner21.setMaximum(100000000);
        	        spinner21.setSelection(500);

        	        tabItem1.setControl(composite1);
        	        start.add(spinner1);
        	        end.add(spinner21);
//            		TableItem item = new TableItem (table, SWT.NONE);
//            		item.setText (listJavaFiles.get(i));
            		ArrayList<TreeItem> tiVarList = new ArrayList<>();
            		//Tree
            		if(listJavaFiles.get(i).toString().substring(listJavaFiles.get(i).toString().length() - 4).equals("java")) {
	        			String setArrVar = combo.getText() + "\\" + listJavaFiles.get(i);
	        			System.out.println(setArrVar);
	        			VariableNameExtractor vne = new VariableNameExtractor();
	        			List<String> variableNames = vne.extractVariableNames(setArrVar);
	       				System.out.println(variableNames);
	       				TreeItem itemI = new TreeItem(tree, SWT.NONE);
	       				itemI.setText(listJavaFiles.get(i).toString());
	       				ti.add(itemI);
	       				for (int j = 0; j < variableNames.size(); j++) {
	       					TreeItem itemJ = new TreeItem(itemI, SWT.NONE);
	       					itemJ.setText(variableNames.get(j));
	       					tiVarList.add(itemJ);
	       				}
	       				tiVar.add(tiVarList);
            		}
            		tabitem.add(tabItem1);
            		comp.add(composite1);
            	}
            	tabFolder.pack ();
            	shell.setSize(700, 600);
            	combo.setSize(600, 30);
            	System.out.println(combo.getSize());
            	table.setSize(400, 90);
            	System.out.println("tree: " + tree);
            	
                if (directory.exists() && directory.isDirectory()) {
                    findJavaFiles(directory);
                } else {
                    System.out.println("Invalid directory path or directory does not exist.");
                }
            }
        });
        
//        // Add a SelectionListener to the combo
//        combo.addSelectionListener(new SelectionAdapter() {
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                // This method will be called when the combo selection changes
//                // You can retrieve the selected item using the getText() method
//                String selectedText = combo.getText();
//                System.out.println("Selected item: " + selectedText);
////                label.setText("Selected item: " + selectedText); // Update label text
//                
//                File directory = new File(selectedText);
//                ArrayList<String> listJavaFiles = findJavaFiles(directory);
//                System.out.println("List Java Files:" + listJavaFiles);
////                label.setText(listJavaFiles.toString());
////                tiVar = new ArrayList[listJavaFiles.size()];
////                final TabFolder tabFolder = new TabFolder (shell, SWT.BORDER);
//            	for (int i=0; i<listJavaFiles.size(); i++) {
//        	        TabItem tabItem1 = new TabItem(tabFolder, SWT.NONE);
//        	        tabItem1.setText(listJavaFiles.get(i));
//        	        
//        	        Composite composite1 = new Composite(tabFolder, SWT.NONE);
//        	        composite1.setLayout(new GridLayout(2, false));
//        	        
//        			Button button = new Button (composite1, SWT.PUSH);
//        			button.setText ("Line Number Where Print Statement Starts:");
//
//
//        	        Spinner spinner1 = new Spinner(composite1, SWT.NONE);
//        	        spinner1.setMinimum(0);
//        	        spinner1.setMaximum(100000000);
//        	        spinner1.setSelection(0);
//        	        
//        	        Button button2 = new Button (composite1, SWT.PUSH);
//        			button2.setText ("Line Number Where Print Statement Ends:");
//
//        	        Spinner spinner21 = new Spinner(composite1, SWT.NONE);
//        	        spinner21.setMinimum(0);
//        	        spinner21.setMaximum(100000000);
//        	        spinner21.setSelection(50);
//
//        	        tabItem1.setControl(composite1);
//        	        start.add(spinner1);
//        	        end.add(spinner21);
////            		TableItem item = new TableItem (table, SWT.NONE);
////            		item.setText (listJavaFiles.get(i));
//            		ArrayList<TreeItem> tiVarList = new ArrayList<>();
//            		//Tree
//            		if(listJavaFiles.get(i).toString().substring(listJavaFiles.get(i).toString().length() - 4).equals("java")) {
//	        			String setArrVar = combo.getText() + "\\" + listJavaFiles.get(i);
//	        			System.out.println(setArrVar);
//	        			VariableNameExtractor vne = new VariableNameExtractor();
//	        			List<String> variableNames = vne.extractVariableNames(setArrVar);
//	       				System.out.println(variableNames);
//	       				TreeItem itemI = new TreeItem(tree, SWT.NONE);
//	       				itemI.setText(listJavaFiles.get(i).toString());
//	       				ti.add(itemI);
//	       				for (int j = 0; j < variableNames.size(); j++) {
//	       					TreeItem itemJ = new TreeItem(itemI, SWT.NONE);
//	       					itemJ.setText(variableNames.get(j));
//	       					tiVarList.add(itemJ);
//	       				}
//	       				tiVar.add(tiVarList);
//            		}
//            		
//            		
//            	}
//            	tabFolder.pack ();
//            	table.setSize(400, 90);
//            	System.out.println("tree: " + tree);
//            	
//                if (directory.exists() && directory.isDirectory()) {
//                    findJavaFiles(directory);
//                } else {
//                    System.out.println("Invalid directory path or directory does not exist.");
//                }
//            }
//        });

//        System.out.println("tree: " + tree);
		
		
//		new Label(shell, SWT.NONE).setText(combo.getText());
		
		Button rm = new Button(shell, SWT.RADIO);
		rm.setText("Remove Print Statements");
		
		
		Button rmAll = new Button(shell, SWT.RADIO);
		rmAll.setText("Remove All Print Statements");
		
		Button none = new Button(shell, SWT.RADIO);
		none.setText("None");
		
		
		
////		new Label().setText("Line Number Where Print Statement Starts:");
//		new Label(shell, SWT.NONE).setText("Line Number Where Print Statement Starts:");
//		Spinner spinner = new Spinner (shell, SWT.BORDER);
//		spinner.setMinimum(0);
//		spinner.setMaximum(2147483647);
//		spinner.setSelection(0);
//		spinner.setIncrement(1);
//		spinner.setPageIncrement(100);
//		
//		System.out.println(spinner.getText());
//		
////		new Label().setText("Line Number Where Print Statement Ends:");
//		new Label(shell, SWT.NONE).setText("Line Number Where Print Statement Ends:");
//		Spinner spinner2 = new Spinner (shell, SWT.BORDER);
//		spinner2.setMinimum(0);
//		spinner2.setMaximum(2147483647);
//		spinner2.setSelection(2147483647);
//		spinner2.setIncrement(1);
//		spinner2.setPageIncrement(100);
		
//		new Label().setText("Variables To Print:");
//		new Label(shell, SWT.NONE).setText("Variables To Print:");
//		final Text t = new Text(shell, SWT.BORDER | SWT.MULTI);
//		t.setText ("here is some text to be selected");
		Menu bar = new Menu (shell, SWT.BAR);
		shell.setMenuBar (bar);
		MenuItem editItem = new MenuItem (bar, SWT.CASCADE);
		editItem.setText ("Edit");
		Menu submenu = new Menu (shell, SWT.DROP_DOWN);
		editItem.setMenu (submenu);

		MenuItem item = new MenuItem (submenu, SWT.PUSH);
//		item.addListener (SWT.Selection, e -> t.selectAll());
		item.setText ("Select &All\tCtrl+A");
		item.setAccelerator (SWT.MOD1 + 'A');
		
//		Button bard = new Button(shell, SWT.CHECK);
//		bard.setText("Use Bard");
		
//        shell.setLayout(new GridLayout(2, false));
//
//        // Load the warning sign image
//        Image warningImage = display.getSystemImage(SWT.ICON_WARNING);
//
//        // Create a label to display the warning sign
//        Label warningLabel = new Label(shell, SWT.NONE);
//        warningLabel.setImage(warningImage);
//        warningLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
//
//        // Create a label to display a message
//        Label messageLabel = new Label(shell, SWT.NONE);
//        messageLabel.setText("Warning: Something went wrong!");
//        messageLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
//
//        shell.pack();
//        shell.open();
//
//        while (!shell.isDisposed()) {
//            if (!display.readAndDispatch()) {
//                display.sleep();
//            }
//        }
//
//        display.dispose();
		
		
		Button b = new Button(shell, SWT.PUSH);
		b.setText("Run!");		
		b.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//		        shell.setLayout(new GridLayout(2, false));
//
//		        // Load the warning sign image
//		        Image warningImage = display.getSystemImage(SWT.ICON_WARNING);
//
//		        // Create a label to display the warning sign
//		        Label warningLabel = new Label(shell, SWT.NONE);
//		        warningLabel.setImage(warningImage);
//		        warningLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
//
//		        // Create a label to display a message
//		        Label messageLabel = new Label(shell, SWT.NONE);
//		        messageLabel.setText("Warning: Something went wrong!");
//		        messageLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
//
//		        shell.pack();
//		        shell.open();
//
//		        while (!shell.isDisposed()) {
//		            if (!display.readAndDispatch()) {
//		                display.sleep();
//		            }
//		        }
//
//		        display.dispose();
		        
				// TODO Auto-generated method stub
				System.out.println("11");
				System.out.println("WOW:" + start);
				if (start.size() == 0) {
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
			        messageBox.setText("Warning");
			        messageBox.setMessage("Invalid directory path or directory does not exist.");
			        
			        int result = messageBox.open();
			        
			        if (result == SWT.OK) {
			            System.out.println("OK button clicked.");
			        }
				}
				System.out.println(end);
				System.out.println(combo.getText());
				System.out.println(rm.getSelection());
				System.out.println(rmAll.getSelection());
				// Print Statement Generator
		        String[] args = {"run"};
		        String[] args2 = {};
		        String[] combinedArray = Arrays.copyOf(args, args.length + args2.length);
		        System.arraycopy(args2, 0, combinedArray, args.length, args2.length);
		        if (rm.getSelection()) {
		        	combinedArray[0] = "remove";
		        }
//				Set<String> set = listFilesUsingJavaIO(combo.getText());
//		        Object[] setArr = set.toArray();
		        if (rmAll.getSelection()) {
		        	combinedArray[0] = "removeAll";
		        }
//		        if (bard.getSelection()) {
//		        	combinedArray[0] = "bard";
//		        }
		        
		        for (int i = 0; i < ti.size(); i++) {
		        	
//		        	if (!ti.get(i).getChecked()) { 
//		        		continue;
//		        	}
//		        	
//		        	ArrayList<String> varList = new ArrayList<String>();
//		        	for (int j = 0; j < tiVar.get(i).size(); j++) {
//		        		if (tiVar.get(i).get(j).getChecked()) {
//		        			varList.add(tiVar.get(i).get(j).getText());
//		        		}
//		        	}
//		        	varList.add(0, combinedArray[1]);
//		        	varList.add(0, combinedArray[0]);
//		        	Object[] objectArray = varList.toArray();
//		            // Cast Object[] array to String[] array
//		        	combinedArray = new String[objectArray.length];
//		            for (int j = 0; j < objectArray.length; j++) {
//		            	combinedArray[j] = (String) objectArray[j];
//		            }
//		        	System.out.println(varList);
		            if(ti.get(i).getText().toString().substring(ti.get(i).getText().toString().length() - 4).equals("java")) { // Checks whether the file ends with .txt extension or not.
		                if (combinedArray[0].equals("removeAll")) {
		                    removePrintStatementsFromFile(combo.getText() + "\\" + ti.get(i).getText().toString());
		                } else if (combinedArray[0].equals("remove")) { // Removes print statements generated by AutoPrint
		                    Path source = Paths.get("C:\\Users\\minhy\\Downloads\\cs463-musical-chairs-thread-concurrency-master\\cs463-musical-chairs-thread-concurrency-master\\AppendString\\copy\\" + ti.get(i).getText().toString());
		                    Path destination = Paths.get("C:\\Users\\minhy\\Downloads\\cs463-musical-chairs-thread-concurrency-master\\cs463-musical-chairs-thread-concurrency-master\\AppendString\\" + ti.get(i).getText().toString());
		                    try {
								Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		                } else if (combinedArray[0].equals("bard")) {
		                	try {
		                	String fileStr = combo.getText() + "\\" + ti.get(i).getText().toString();
		                	System.out.println(fileStr);
		                	bart(fileStr);
		                	} catch (Exception ex) {
		                		System.out.println(ex);
		                	}
		                } else {
		                	try {
	    		        	if (!ti.get(i).getChecked()) { 
	    		        		continue;
	    		        	}
	    		        	
	    		        	boolean notAllVariablesPrinted = false;
	    		        	ArrayList<String> varList = new ArrayList<String>();
	    		        	for (int j = 0; j < tiVar.get(i).size(); j++) {
	    		        		if (tiVar.get(i).get(j).getChecked()) {
	    		        			varList.add(tiVar.get(i).get(j).getText());
	    		        		} else {
	    		        			notAllVariablesPrinted = true;
	    		        		}
	    		        	}
	    		        	varList.add(0, end.get(i).getText());
	    		        	varList.add(0, start.get(i).getText());
	    		        	Object[] objectArray = varList.toArray();
	    		            // Cast Object[] array to String[] array
	    		        	combinedArray = new String[objectArray.length];
	    		            for (int j = 0; j < objectArray.length; j++) {
	    		            	combinedArray[j] = (String) objectArray[j];
	    		            }
		                		
		                	String setArrVar = combo.getText() + "\\" + ti.get(i).getText().toString();
		                	FileWriter fileWriter = new FileWriter(combo.getText() + "\\log.txt", true);
		                	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		                    System.out.println(combinedArray);
		                    bufferedWriter.write(Arrays.toString(combinedArray) + setArrVar.toString() + notAllVariablesPrinted);
		                    bufferedWriter.newLine(); // Add a newline character for readability
		                    bufferedWriter.close();
		                    fileWriter.close();
		                    System.out.println(notAllVariablesPrinted);
		                    run(combinedArray, setArrVar);
		                	} catch (FileNotFoundException fnfe) {
		                		System.out.print(-1);
		                		System.out.println(ti.get(i).getText()); //Java file name that does not have a complete code.
		                        shell.setLayout(new GridLayout(2, false));

		                        // Load the warning sign image
		                        Image warningImage = display.getSystemImage(SWT.ICON_WARNING);

		                        // Create a label to display the warning sign
		                        Label warningLabel = new Label(shell, SWT.NONE);
		                        warningLabel.setImage(warningImage);
		                        warningLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		                        // Create a label to display a message
		                        Label messageLabel = new Label(shell, SWT.NONE);
		                        messageLabel.setText("Warning: There is a parse error in the file " + ti.get(i).getText());
		                        messageLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		                        shell.pack();
		                        shell.open();

		                        while (!shell.isDisposed()) {
		                            if (!display.readAndDispatch()) {
		                                display.sleep();
		                            }
		                        }

		                        display.dispose();
		                	} catch (Exception e1) {
		                		System.out.print(e1);
		                		e1.printStackTrace();
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
		
//		Rectangle clientArea = shell.getClientArea();
//		spinner.setLocation(clientArea.x, clientArea.y);
//		spinner.pack();
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
    
	static void checkPath(TreeItem item, boolean checked, boolean grayed) {
		if (item == null) return;
		if (grayed) {
			checked = true;
		} else {
			int index = 0;
			TreeItem[] items = item.getItems();
			while (index < items.length) {
				TreeItem child = items[index];
				if (child.getGrayed() || checked != child.getChecked()) {
					checked = grayed = true;
					break;
				}
				index++;
			}
		}
		item.setChecked(checked);
		item.setGrayed(grayed);
		checkPath(item.getParentItem(), checked, grayed);
	}

	static void checkItems(TreeItem item, boolean checked) {
		item.setGrayed(false);
		item.setChecked(checked);
		for (TreeItem child : item.getItems()) {
			checkItems(child, checked);
		}
	}
    
    public static String formatArray(Object[] array) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);

            if (i != array.length - 1) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }
    
    public static ArrayList<String> findJavaFiles(File directory) {
        File[] files = directory.listFiles();
        ArrayList<String> javaFileName = new ArrayList<String>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".java")) {
//                    System.out.println(file.getAbsolutePath());
                    String input = file.getAbsolutePath();
                    int lastIndex = input.lastIndexOf("\\");
                    input = input.substring(lastIndex + 1);
                    System.out.println(input);
                    javaFileName.add(input);
                } else if (file.isDirectory()) {
//                    findJavaFiles(file);
                }
            }
        }
        return javaFileName;
    }
    
    public static void pythonRunner(String fileStr, ArrayList<Integer> cut) {
        try {
//            String pythonScriptPath = "C:\\Users\\minhy\\Desktop\\main.py";  // Set the path to your Python script
        	String pythonScriptPath = ".\\src\\com\\remainsoftware\\e3\\hello\\readJava.py";  // Set the path to your Python script
            
            // Run the Python script
            Process process = Runtime.getRuntime().exec("python " + pythonScriptPath + " " + fileStr);
//            Process process = Runtime.getRuntime().exec("python " + pythonScriptPath + " " + fileStr + " " + formatArray(cut.toArray()));

            // Read the output from the Python script
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Check for any errors from the Python script
            InputStream errorStream = process.getErrorStream();
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println(errorLine);
            }

            // Wait for the Python process to complete
            int exitCode = process.waitFor();
            System.out.println("Python script executed with exit code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
    
    public static void bart(String fileStr) throws Exception {
        File a = new File(fileStr);
        File b = new File(fileStr.substring(0, fileStr.lastIndexOf("\\")) + "\\copy" + fileStr.substring(fileStr.lastIndexOf("\\")));
//        File b = new File(fileStr);
        Path copyDirectory = Paths.get(fileStr.substring(0, fileStr.lastIndexOf("\\")) + "\\copy");
        Files.createDirectories(copyDirectory);
        copyFile(a, b);
//        System.out.println("ple");
        
        
        RandomAccessFile file = new RandomAccessFile(fileStr, "r");
        String str;
        Queue<Integer> lineNumQueue = new LinkedList<>();
        Queue<String> varNameQueue = new LinkedList<>();
        Queue<String> spaceQue = new LinkedList<>();
        Queue <String> toStrQue = new LinkedList<>();
        ArrayList<Integer> hierarchy = new ArrayList<Integer>();

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
//                    lineNum--;
                }
                System.out.println(matcher.group());
//                writeAfterNthLine(fileName, "System.out.println();", lineNum);

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

        ArrayList<Integer> ret = bigFunc(arr);
        ArrayList<Integer> cut = new ArrayList<Integer>();
        cut.add(ret.get(0));
        
        int start = ret.get(0);
        for (int i = 1; i < ret.size(); i++) {
	        if ((ret.get(i) * (-1)) > (start + 80)) { // Gives the line number to cut: 90 lines.
	        	cut.add(ret.get(i) * -1);
	        	if ((i + 1) < ret.size()) start = ret.get(i+1);
	        } else {
	        	i++; // This is done intentionally to skip one variable.
	        }
        }
        cut.add(ret.get(ret.size() - 1) * -1);
        
        String outputFile = "C:\\\\Users\\\\minhy\\\\Downloads\\\\cs463-musical-chairs-thread-concurrency-master\\\\cs463-musical-chairs-thread-concurrency-master\\\\AppendString\\\\temp.txt"; // Replace with your output file name

        try {
            extractFileContent(fileStr, outputFile, 25, 107);
            System.out.println("Content extracted and saved to " + outputFile);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        
        pythonRunner(fileStr, cut);
    }
    
    /**
     * Extracts the content from a file starting from the specified start line and ending at the specified end line.
     *
     * @param inputFile  the name or path of the input file
     * @param outputFile the name or path of the output file
     * @param startLine  the line number to start extracting from (inclusive)
     * @param endLine    the line number to end extraction at (inclusive)
     * @throws IOException if an I/O error occurs while reading or writing the files
     */
    public static void extractFileContent(String inputFile, String outputFile, int startLine, int endLine) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            int currentLine = 1;

            // Read the input file until the desired start line
            while ((line = reader.readLine()) != null && currentLine < startLine) {
                currentLine++;
            }

            // Write the lines from startLine to endLine to the output file
            while (line != null && currentLine <= endLine) {
                writer.write(line);
                writer.newLine();
                line = reader.readLine();
                currentLine++;
            }
        }
    }
    
    // Selects only "big" methods
    public static ArrayList<Integer> bigFunc(int[][] arr) {
    	ArrayList<Integer> ret = new ArrayList<Integer>();
    	int minInt = arr[arr.length - 2][0]; //Starts at arr.length - 2 because the last element captures the entire class.
    	ret.add(0, arr[arr.length - 2][1]);
    	ret.add(0, arr[arr.length - 2][0]);
    	for (int i = arr.length - 3; i >= 0; i--) { 
    		if (arr[i][0] > minInt) {
    			continue;
    		} else {
    			minInt = arr[i][0];
    	    	ret.add(0, arr[i][1]);
    	    	ret.add(0, arr[i][0]);
    		}
    	}
    	
    	return ret;
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

        // JavaParserExample.java
        System.out.println("Working Directory = " + System.getProperty("user.dir"));// Get the current directory as a Path object
        // Get the current directory
        String currentDirectory = System.getProperty("user.dir");
        JavaParserExample jpe = new JavaParserExample();
        
        String[] combinedArray = Arrays.copyOf(args, args.length + 1);
        combinedArray[combinedArray.length - 1] = fileStr;
        
        int successful = jpe.main(combinedArray); // 0 if successful, -1 if unsuccessful.
        System.out.println("successful:" + successful);
        if (successful < 0) {
        	throw new FileNotFoundException(); // Not the exact exception, but most similar exception that I can find.
        }
//        JavaParserExample jpe = new JavaParserExample();

        // Create a File object representing the parent directory
        
        

        
//        // Specify the directory where the file is located
//        String directory = "..";
//
//        // Specify the filename
//        String filename = "example.txt";
//
//        // Create a Path object representing the file's location
//        Path filePath = Paths.get(directory, filename);
//
//        // Check if the file exists
//        if (Files.exists(filePath)) {
//            // Read the file contents
//            String fileContents = Files.readString(filePath);
//            System.out.println("File contents: " + fileContents);
//        } else {
//            System.out.println("File not found.");
//        }

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
