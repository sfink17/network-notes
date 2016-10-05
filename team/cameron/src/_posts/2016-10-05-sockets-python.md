---
title: Socket Programming in Python
layout: default
date: 2016-10-05 13:54:00
---

Here's an example of a simple server in Python that prints what it recieves, 
and sends back the same data uppercased.

``` python
import socketserver
    
class MyTCPHandler(socketserver.StreamRequestHandler):
    """
    The request handler class for our server.

    It is instantiated once per connection to the server, and must
    override the handle() method to implement communication to the
    client.
    """

    def handle(self):
        # self.rfile is a file-like object created by the handler;
        # we can now use e.g. readline() instead of raw recv() calls
        self.data = self.rfile.readline().strip()
        print("{} wrote:".format(self.client_address[0]))
        print(self.data)
        # Likewise, self.wfile is a file-like object used to write back
        # to the client
        self.wfile.write(self.data.upper())

class ForkedTCPServer(socketserver.ForkingMixIn, socketserver.TCPServer):
    pass

if __name__ == "__main__":
    HOST, PORT = "localhost", 9999

    # Create the server, binding to localhost on port 9999
    server = ForkedTCPServer((HOST, PORT), MyTCPHandler)

    # Activate the server; this will keep running until you
    # interrupt the program with Ctrl-C
    server.serve_forever()
```

Note that this uses utilities supplied by Python 3, the `socketserver` package,
which did not exist in Python 2.

Also, it uses the `ForkingMixIn`, which creates a new process for each request,
for greater availability.