import os

os.chdir("www")

import SimpleHTTPServer
import SocketServer

PORT = 8080

Handler = SimpleHTTPServer.SimpleHTTPRequestHandler

httpd = SocketServer.TCPServer(("0.0.0.0", PORT), Handler)

print "serving at port", PORT

try:
    httpd.serve_forever()
except(KeyboardInterrupt):
    httpd.shutdown()