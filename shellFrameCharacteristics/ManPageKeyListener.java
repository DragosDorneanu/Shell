package shellFrameCharacteristics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ManPageKeyListener implements KeyListener 
{
	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		char key = e.getKeyChar();
		if(key == KeyEvent.VK_Q || key == (char)(KeyEvent.VK_Q + 32))
		{
			ShellFrame.thisShellFrame.setVisible(true);
			ManFrame.thisManPage.dispose();
		}
	}
}
