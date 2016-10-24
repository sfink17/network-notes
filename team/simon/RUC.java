import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import java.lang.*;


/*
 * Server to process ping requests over UDP.
 */
public class RUC 
{
	public static DatagramSocket socket;
	public static ByteArrayOutputStream output;
    public static BufferedWriter writer;
    public static SimpleDateFormat timeFormat;
    public static InetAddress address;
    public static DatagramPacket serverPacket;
	public static long sent;
	public static UdpWriter mWriter;
	public static UdpReader mReader;
	
	public class UdpWriterThread extends Thread{
		private final File mFile;
		private final FileInputStream mFis;
		private final BufferedInputStream mBis;
		
		public UdpWriterThread(File file){
			mFile = file;
			mFis = new FileInputStream(file);
			mBis = new BufferedInputStream(mFis);
		}
		
		public void run() {
			byte[] buf = new byte[1024];
			while (true) {
				try {
				writer.write(ping, 0, ping.length());
				writer.flush();
				byte[] buf = output.toByteArray();
				sent = System.currentTimeMillis();
				socket.send(new DatagramPacket(buf, output.size(), address, 4545));
				output.reset();
				
				}
				catch (IOException o) {
					System.out.println("Could not write ping to buffer.");
					break;
				}
			}
		}
	}
	
	public class UdpReaderThread extends Thread(){
		
		public void run() {
			while (true) {
				try{
					socket.receive(serverPacket);
					long pingMs = System.currentTimeMillis() - sent;
					byte [] buf = serverPacket.getData;
					String isGood = new String(buf, 0, buf.length);
					if (isGood.contains("ACK" + ((Integer) synack).toString){
						synack++;
						printData(pingMs, serverPacket);
					}
					else {
						synack = syn;
					}
					
				}
				catch (SocketTimeoutException s) {
						System.out.println("Ping timed out.");
				}
			}
		}
	}
	 

   public static void main(String[] args) throws Exception
   {
      // Get command line argument.
      if (args.length != 2) {
         System.out.println("Required arguments: host and file");
         return;
      }
      String host = args[0];
	  File mFile = new File(args[1]);

      // Create a datagram socket for receiving and sending UDP packets
      // through the port specified on the command line.
      socket = new DatagramSocket();
      socket.setSoTimeout(1000);
	 
	  output = new ByteArrayOutputStream(1024);
      writer = new BufferedWriter(new OutputStreamWriter(output));
      timeFormat = new SimpleDateFormat("HH:mm:ss:SSS XXX");
      address = InetAddress.getByName(host);
      serverPacket = new DatagramPacket(new byte[1024], 1024);
	  mWriter = new UdpWriter();
	  mReader = new UdpReader();
	  UdpReader.start();
	  UdpWriter.start(mFile);
     
   }

   /* 
    * Print ping data to the standard output stream.
    */
   private static void printData(long ping, DatagramPacket request) throws Exception
   {
      // Obtain references to the packet's array of bytes.
      byte[] buf = request.getData();

      // Wrap the bytes in a byte array input stream,
      // so that you can read the data as a stream of bytes.
      ByteArrayInputStream bais = new ByteArrayInputStream(buf);

      // Wrap the byte array output stream in an input stream reader,
      // so you can read the data as a stream of characters.
      InputStreamReader isr = new InputStreamReader(bais);

      // Wrap the input stream reader in a bufferred reader,
      // so you can read the character data a line at a time.
      // (A line is a sequence of chars terminated by any combination of \r and \n.) 
      BufferedReader br = new BufferedReader(isr);

      // The message data is contained in a single line, so read this line.
	  String pingStr = ((Long) ping).toString();
      String line = pingStr + "ms" + br.readLine().substring(4);

      // Print host address and data received from it.
      System.out.println(
         "Received from " + 
         request.getAddress().getHostAddress() + 
         ": " +
         new String(line) );
   }
}
