#!/usr/bin/env python  
 
import socket
import time
import sys
import os
 
HOST_IP = "10.105.70.196"
#HOST_IP = "127.0.0.1"
HOST_PORT = 5654
 
print("Starting socket: TCP...")
host_addr = (HOST_IP, HOST_PORT)
socket_tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
 
print("TCP server listen @ %s:%d!" %(HOST_IP, HOST_PORT) )
socket_tcp.bind(host_addr)
socket_tcp.listen(1)
 
socket_con, (client_ip, client_port) = socket_tcp.accept()
print("Connection accepted from %s." %client_ip)
socket_con.send("Welcome to RPi TCP server!")
 
print("Receiving package...")
while True:
    try:
        data = socket_con.recv(64)
        if len(data)>0: 
            print("Received: %s" %data)
            if os.path.exists(r'/yjdata/www/www/dragonBoard/request')):
                socket_con.send("request")
            else :
                socket_con.send("toClient")
            time.sleep(1)
            continue
    except Exception:  
        socket_tcp.close()
        sys.exit(1)