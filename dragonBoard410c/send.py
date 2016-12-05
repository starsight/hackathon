#!/usr/bin/env python  
  
import socket   
import time
import sys
    
SERVER_IP = "123.206.214.17"
#SERVER_IP = "127.0.0.1"
SERVER_PORT = 5654
 
print("Starting socket: TCP...")
server_addr = (SERVER_IP, SERVER_PORT)
socket_tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
 
while True:  
    try:
        print("Connecting to server @ %s:%d..." %(SERVER_IP, SERVER_PORT))
        socket_tcp.connect(server_addr)  
        break  
    except Exception:
        print("Can't connect to server, try it latter!")
        time.sleep(1)
        continue  
 
print("Receiving package...")  
while True:
    try:
        data = socket_tcp.recv(64)  
        if len(data)>0: 
            print("Received: %s" %data)
            
            socket_tcp.send("toServer")
            time.sleep(1) 
            continue
    except Exception:  
        socket_tcp.close()
        socket_tcp = None
        sys.exit(1)