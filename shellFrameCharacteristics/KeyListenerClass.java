package shellFrameCharacteristics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerClass implements KeyListener
{
	@Override
	public void keyPressed(KeyEvent e) 
	{
		// when ENTER button is pressed on the keyboard
		// send the input command to the static method ShellFrame.solve(String) to get an output
		char key = e.getKeyChar();
		if(key == KeyEvent.VK_ENTER)
		{
			if(ShellFrame.command.length() > 0)
			{
				try
				{					
					ShellFrame.solve();
					ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + "\n\n" + ShellFrame.currentPath + " > ");
				}
				catch(Exception ex){
					ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n' + ex.getMessage() + "\n\n" + ShellFrame.currentPath + " > ");
				}
				finally
				{
					ShellFrame.argc = 0;
					ShellFrame.command.delete(0, ShellFrame.command.length());
				}
			}	
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		char key = e.getKeyChar();
		if(key == KeyEvent.VK_SPACE)
			++ShellFrame.argc;
		if(key == KeyEvent.VK_BACK_SPACE)
		{
			if(ShellFrame.command.length() != 0)
			{
				if(ShellFrame.command.charAt(ShellFrame.command.length() - 1) == ' ')
					--ShellFrame.argc;
				ShellFrame.command.deleteCharAt(ShellFrame.command.length() - 1);
				String text = ShellFrame.commandArea.getText();
				ShellFrame.commandArea.setText(text.substring(0, text.length() - 1));
			}
		}
		else if(key != KeyEvent.VK_ENTER)
		{
			ShellFrame.command.append(key);
			ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + key);
		}
	}
}