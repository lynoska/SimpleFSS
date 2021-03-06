# SimpleFSS
###### Simple File Sharing System in Java with Logging -- Lynoska Garcia and Akshat Patel

# Introduction
## 1.1 Purpose

The purpose of this project is to create a simple file-sharing system in 
which the clients can access files from a pre-determined directory.

## 1.2 Scope  

The scope of the Simple File-Sharing System (FSS) is for it to be simple
and provide the ability for a client to access the website and services 
hosted by the Server through a Web Server. The Server also runs a logger
which logs connections made and records the files accessed. The system 
has no login systemas it meant to be open to public.

# Functionality

| Class | Description |
| --- | --- |
| FileServer | Creaters the ServerSocker with the given port |
| | Processes the HTTP Requests |
| | ↳ Accepts the TCP connection requests |
| | ↳ Initializes the HTTP requets givent the TCP connection request and directory where the files are located |
| | ↳ Creates Threads for each requests and initialzes them |
| HttpRequest | Creates the HttpRequests with option to create object with different sockets and directories than the ones set |
| | Implements Runnable to create the threat and create the web server |
| | Initializes the logger at run time to ensure connections are log as soon as there is one |
| | Once the client conntects, their IP address is grabbed |
| | The client connection is logged |
| | HTTPRequest is created |
| | ↳ HTTP request is obtained from the browser |
| | ↳ StringTokenizer extracts the filename from the request line  |
| | ↳ The file stream is initalized to process the files available |
| | ↳ The header lines are formatted and logged |
| | ↳ HTTP return requests are created, formatted and sent |
| LoggerClient | Creates a Logger client which logs to external pre-determined file (`simplefss-logs.txt`) |
| | Logs are formatted for readability |
| | Client connections to server are logged with timestamp and IP address |
| | Client's HTTP request logged with timestamp, IP address and and request |

# Design Overview  
## 1.1 Overall class structure  
![Class structure](https://github.com/lynoska/SimpleFSS/blob/master/img/project-architecture.PNG)

## 1.2 Logs structure
```
May 14, 2019 9:38:21 PM LoggerClient connection
INFO: Lynoska/192.168.0.6 - connection established

May 14, 2019 9:38:21 PM LoggerClient HTTPRequest
INFO: Lynoska/192.168.0.6 - HTTP Request 
GET /index.html HTTP/1.1
Filename: Home\/index.html
Host: localhost:4444
Connection: keep-alive
Cache-Control: max-age=0
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3
Referer: http://localhost:4444/Videos/Videos.html
Accept-Encoding: gzip, deflate, br
Accept-Language: en-US,en;q=0.9
```

## 1.3 Website structure  
![Website structure](https://github.com/lynoska/SimpleFSS/blob/master/img/web-structure.PNG)

# Protocols
* TCP

# How-to use
1. Compile the code
2. Create a new FileServer Object  
![FileServer Method Call](https://github.com/lynoska/SimpleFSS/blob/master/img/fileserver-methodcall.PNG)  
(This will start a socket on your localhost and port 4444)  
3. On your browser go to: http://localhost:4444/
4. Browse through

# Issues and Solutions
## 1.1 Anticipated issues  
* Displaying the directories onto the web server with Java
* Creating logs
* Upon connection grabbing the user's IP Address

## 1.2 Anticipated Solutions
* Using telnet to request files would not require the direstory being listed

## 1.3 Actual issues
### Web Server Issues
* Executing php script on java web server
* Defining correct MIME (Multi-Purpose Internet Mail Extensions) type for different file extensions
* Inconsistency or missing files in local database might cause synchronization issues

### Logger Issues
* Logger would only write to console
* Logger would erase previously written logs
* Format of logs caused readibility issues
* Logger would log to multiple files for every file on the web server directory

## 1.4 Solutions to Actual Problem
### Web Server Solutions
* Creating a php-java bridge, i.e., connecting a native script engine like php with Java virtual machine would execute php script on the web server
* Defining multiple mime types for a particular extension (example: application/javascript, text/javascript)
* Having a backup database or check the database for inconsistencies everytime a change is made to local directory

### Logger Issues
* Add a file handler to send the logs to
* Allow for appending to file
* User a formatter to format the logs
* Set up the Logger in a try-catch with the destination being a final variable

## Testing
We tested this project by created a simple version of what our final project became. A simple log system that just noted a client connection to the server verified that the server was created properly, despite it having no directory yet. 

Testing via the logger was crucial because it informed us when we made progress in our project. We had run into the issue that despite us specifying the directory where the files were located, we could not get them to actually list. The loger confirmed that the web server was set up correctly but the function was not correct. 

The logger also verified the correctness of the header lines and the HTTP requests as it logged everything to the console. This also helped us make sure the requests were properly formatted.

## Conclusion
Given more time, we would have liked to implement more features and expand out project:
1. Create a login system which would track the connection by IP an usernames
2. Implement a download ability so that when a file is clicked, it is automatically downloaded
3. Given that the download ability is implemented, have the loggerClient also log this
4. Have the files in the directory formatted automatically in the HTML site so we don't have to hardcode them
