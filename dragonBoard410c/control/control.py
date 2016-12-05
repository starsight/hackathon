import os
import time

if __name__ == "__main__":
    while True:
        if  os.path.exists(r'request'):
            file = r'request'
            if os.path.exists(file):
                os.remove(file)
            #print "hello"
            os.system("chmod +x control.sh")
            os.system("sudo ./control.sh")
        time.sleep(1)