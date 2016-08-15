package shellFrameCharacteristics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ManPageWindowListener implements WindowListener
{

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {
		ShellFrame.commandArea.requestFocus();
	}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		ShellFrame.thisShellFrame.setVisible(true);
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
		ManFrame myManFrame = (ManFrame)(e.getSource());
		myManFrame.manArea.requestFocusInWindow();
	}
}