package shellFrameCharacteristics;
import java.awt.Frame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;

public class ManFrame extends Frame
{
	public static final long serialVersionUID = 0L;
	JTextArea manArea;
	JScrollPane manScroll; 
	static Frame thisManPage;
	
	public ManFrame(String text)
	{
		this.setBounds(ShellFrame.thisShellFrame.getBounds());
		this.setResizable(false);
		this.setTitle("My Shell Man Page");
		this.addWindowListener(new ManPageWindowListener());
		this.setFont(ShellFrame.textFont);
		manArea = new JTextArea(this.getHeight(), this.getWidth());
		manArea.setText(text);
		manArea.setEditable(false);
		manArea.addKeyListener(new ManPageKeyListener());
		manArea.setFont(ShellFrame.textFont);
		manArea.setBackground(Color.black);
		manArea.setForeground(Color.white);
		manScroll = new JScrollPane(manArea);
		this.add(manScroll);
		thisManPage = this;
	}
}