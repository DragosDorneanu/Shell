package shellFrameCharacteristics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowListenerClass implements WindowListener
{

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) 
	{
		// clear the system's memory
		System.gc();
	}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		// destroy the Frame when CLOSE button is pressed
		e.getWindow().dispose();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) 
	{
		// when a Frame is opened, focus on the commandArea to insert commands 
		ShellFrame.commandArea.requestFocus();
	}
}