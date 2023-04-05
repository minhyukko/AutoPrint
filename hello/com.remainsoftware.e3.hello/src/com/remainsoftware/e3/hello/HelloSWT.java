package com.remainsoftware.e3.hello;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class HelloSWT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Window");
		shell.setLayout(new FillLayout());
		shell.setSize(1200, 800);
		
		Combo combo = new Combo (shell, 0);
		combo.setItems ("C:\\Users\\minhy\\Downloads\\cs463-musical-chairs-thread-concurrency-master\\cs463-musical-chairs-thread-concurrency-master\\AppendString", "C:\\Users\\minhy\\workspaces\\e3");
		
		new Button(shell, SWT.CHECK).setText("Remove Print Statements");
		new Button(shell, SWT.CHECK).setText("Remove All Print Statements");
		
		Spinner spinner = new Spinner (shell, SWT.BORDER);
		spinner.setMinimum(0);
		spinner.setMaximum(1000);
		spinner.setSelection(500);
		spinner.setIncrement(1);
		spinner.setPageIncrement(100);
		
		Spinner spinner2 = new Spinner (shell, SWT.BORDER);
		spinner2.setMinimum(0);
		spinner2.setMaximum(1000);
		spinner2.setSelection(500);
		spinner2.setIncrement(1);
		spinner2.setPageIncrement(100);
		
		final Text t = new Text(shell, SWT.BORDER | SWT.MULTI);
		t.setText ("here is some text to be selected");
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

}
