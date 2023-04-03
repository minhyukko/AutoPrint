package com.remainsoftware.e3.hello;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class HelloHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		
//		HandlerUtil.getActiveWorkbenchWindow(event).close();
		MessageDialog.openInformation(HandlerUtil.getActiveWorkbenchWindow(
                event).getShell(), "Info", "Info for you");
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		MessageDialog.openInformation(shell, "Hello Handler", "Eclipse says hello through a handler");
		return null;
	}

}

