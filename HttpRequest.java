import java.io.* ;
import java.net.* ;
import java.util.*;

public class HttpRequest implements Runnable {
    //Carriage Return Line Feed (windows \r unix \n)
    final static String CRLF = "\r\n"; 
    LoggerClient logger;
    Socket socket;
    String dir;

    public HttpRequest() throws Exception {
        ServerSocket socket = new ServerSocket((new Integer("4444")).intValue());
        String dir = "FSSTest";
    }

    public HttpRequest(Socket socket, String dir) throws Exception {
        this.socket = socket;
        this.dir = dir;
    }

    /**
    * run() from Runnable interface 
    * When an object implementing interface Runnable is used to create a thread, starting the thread causes 
    the object's run method to be called in that separately executing thread. */
    public void run() {
        try {
            System.out.println("\f");
            // HttpProcessRequest();

            //Creates and sets up a new Logger client to use the Logging abiliteis
            logger = new LoggerClient();

            //Get the IP address of the client connection to the site
            String ipAddr = Inet4Address.getLocalHost().toString();

            // Logs the client connection to the server
            logger.connection(ipAddr);
            // The HTTP Request gets logged
            HttpProcessRequest();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * Processes HTTP request through InputStream and BufferedReader
     * 
     * Precondition: none
     * Postcondition: Sends correct HTTP header if file is found, or redirects to index.html
     */
    private void HttpProcessRequest() throws Exception {
        InputStream in = socket.getInputStream();
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        //get the http request
        String request = br.readLine();

        // get the file name
        StringTokenizer tokens = new StringTokenizer(request);
        tokens.nextToken(); 
        String fileName = tokens.nextToken();  
        fileName = this.dir + fileName;

        // Open the file.
        FileInputStream fis = null ;
        boolean fileExists = true ;

        try {
            fis = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            fileExists = false ;
        }

        String headerLine = null;
        ArrayList <String> header = new ArrayList<>();

        // adds each header line to the arraylist 
        while ((headerLine = br.readLine()).length() != 0) {
            header.add(headerLine); 
        }

        // send the HTTPRequest to the logger
        logger.HTTPRequest(fileName, request, header);

        // HTTP Return Request.
        String statusLine = null;
        String contentTypeLine = null;
        String entityBody = null;
        if (fileExists) {
            statusLine = "HTTP/1.0 200 OK" + CRLF;
            contentTypeLine = "Content-Type: " + 
            contentType(fileName) + CRLF;
        } else {
            statusLine = "HTTP/1.0 404 Not Found" + CRLF;
            contentTypeLine = "Content-Type: text/html" + CRLF;
            entityBody = "<HTML>" +
            "<HEAD><TITLE>Not Found</TITLE></HEAD>" +
            "<BODY><meta http-equiv=\"refresh\" content=\"0; URL='http://localhost:4444/index.html'\" /></BODY></HTML>";
        }

        os.writeBytes(statusLine);
        os.writeBytes(contentTypeLine);
        os.writeBytes(CRLF); //end of line
        if (fileExists) {
            sendBytes(fis, os);
            fis.close();
        } else {
            os.writeBytes(entityBody) ; //sent entity body HTTP/1.0 ....
        }
        os.close();
        br.close();
        socket.close();
    }
    
    /**
     * Creates a 1k buffer to send the file into socket output
     * 
     * Precondition: Directory and file must be defined
     * Postcondition: Sends requested file into socket output
     */

    private static void sendBytes(FileInputStream fis, 
    OutputStream os) throws Exception {
        //1K buffer
        byte[] buffer = new byte[1024];
        int bytes = 0;
        //Send requested file into socket output
        while ((bytes = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytes);
        }
    }
    
    /**
     * Defines the correct MIME type based on file extension
     * 
     * Precondition: Directory and file must be defined
     * Postcondition: returns the file with correct MIME type
     */

    private static String contentType(String fileName) {
        if(fileName.endsWith(".htm") || fileName.endsWith(".html")) {
            return "text/html";
        }

        if(fileName.endsWith(".css")){
            return "text/css";
        }
        if(fileName.endsWith(".js")) {
            return "application/x-javascript";
        }

        if(fileName.endsWith(".mp3")) {
            return "audio/mpeg3";
        }
        if(fileName.endsWith(".mp4"))
        {
            return "video/mp4";
        }
        //return "application/x-php" ;
        return "text/html";
    }

}
