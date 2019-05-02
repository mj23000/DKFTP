package FTP;

public class Main
{

    public static void main(String[] args) throws Exception
    {
	// Initialization of FTP class
        FTP ftp = new FTP();
        
        // Initialization of connection to server with specified host adress, user and password
	ftp.connect("ftp.dlptest.com", "dlpuser@dlptest.com", "puTeT3Yei1IJ4UYT7q0r");
        
        // Test of HELP command
	ftp.sendCommand("HELP");
        // Test of LIST command
	ftp.receiveText("LIST");
        
        // Small text file is uploaded and then downloaded again
	ftp.sendText("STOR short_test.txt", "test");
	System.out.println("Downloaded data:\n" + ftp.receiveText("RETR short_test.txt"));
	
        // New directory is created and then changed to
	ftp.sendCommand("MKD test_dir");
	ftp.sendCommand("CWD test_dir");
	
        // Big text file is uploaded and then downloaded again.
	ftp.sendText("STOR long_test.txt", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Orci phasellus egestas tellus rutrum tellus. Dui sapien eget mi proin sed libero. Id diam maecenas ultricies mi eget. Sem nulla pharetra diam sit amet nisl suscipit adipiscing bibendum. Cursus risus at ultrices mi tempus imperdiet nulla malesuada. Id nibh tortor id aliquet lectus. Odio facilisis mauris sit amet massa. Adipiscing tristique risus nec feugiat in fermentum. Feugiat vivamus at augue eget arcu dictum varius. Auctor neque vitae tempus quam pellentesque nec nam aliquam. Ornare massa eget egestas purus viverra accumsan in nisl nisi. Sed libero enim sed faucibus turpis in. Lorem dolor sed viverra ipsum nunc. Nunc lobortis mattis aliquam faucibus purus in massa tempor nec. Faucibus a pellentesque sit amet porttitor. Nunc mattis enim ut tellus elementum sagittis vitae et. Fames ac turpis egestas integer. Sed libero enim sed faucibus turpis in eu mi bibendum. Est ultricies integer quis auctor elit. Pellentesque sit amet porttitor eget dolor morbi non. Odio eu feugiat pretium nibh ipsum consequat nisl. Massa tempor nec feugiat nisl pretium fusce id velit ut. Sit amet dictum sit amet justo. Pellentesque pulvinar pellentesque habitant morbi. Neque aliquam vestibulum morbi blandit cursus risus. Urna porttitor rhoncus dolor purus non enim. Molestie nunc non blandit massa enim nec dui nunc. Pharetra vel turpis nunc eget lorem dolor sed. Quam lacus suspendisse faucibus interdum posuere lorem ipsum. Adipiscing enim eu turpis egestas. Elementum sagittis vitae et leo duis ut. Aliquam sem et tortor consequat id porta nibh venenatis cras. Curabitur vitae nunc sed velit dignissim sodales ut eu sem. Morbi quis commodo odio aenean. Donec pretium vulputate sapien nec sagittis aliquam malesuada bibendum arcu. Aliquam eleifend mi in nulla posuere sollicitudin aliquam ultrices. Nibh cras pulvinar mattis nunc sed blandit libero volutpat sed. Dictum varius duis at consectetur lorem. In eu mi bibendum neque egestas congue quisque egestas diam. Odio aenean sed adipiscing diam. Leo in vitae turpis massa sed elementum tempus egestas sed. Et molestie ac feugiat sed lectus vestibulum. Libero justo laoreet sit amet cursus. Amet mauris commodo quis imperdiet. Nisl rhoncus mattis rhoncus urna neque viverra. Iaculis urna id volutpat lacus laoreet non curabitur. Cras tincidunt lobortis feugiat vivamus at. Sodales ut eu sem integer vitae justo eget. Non curabitur gravida arcu ac tortor dignissim convallis aenean.");
	System.out.println("Downloaded data:\n" + ftp.receiveText("RETR long_test.txt"));
	
        // Big file downloaded and stored as a local file
	ftp.receiveText("RETR long_test.txt", "file.txt");
        // Change to parent directory
        ftp.sendCommand("CDUP");
        // Small files downloaded and stored as another local file
        ftp.receiveText("RETR short_test.txt", "file2.txt");
	
        // Send QUIT command and indicating that the connection has been terminated by the server
	try
	{
	    ftp.receiveText("QUIT");
	} catch (Exception SocketException)
	{
	    System.out.println("Connection terminated by server.");
	}
    }
}
