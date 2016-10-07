import os

os.chdir("www")

import SimpleHTTPServer
import SocketServer
import sys
import argparse

parser = argparse.ArgumentParser()
parser.add_argument("--host", default="localhost")
parser.add_argument("--port", type=int, default=8080)

args = parser.parse_args()

HOST = args.host
PORT = args.port

Handler = SimpleHTTPServer.SimpleHTTPRequestHandler

httpd = SocketServer.TCPServer((HOST, PORT), Handler)

print "serving at {}:{}".format(HOST, PORT)

try:
    httpd.serve_forever()
except KeyboardInterrupt:
    httpd.shutdown()