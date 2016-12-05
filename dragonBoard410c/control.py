import os
import time

if __name__ == "__main__":
    i = 0
    while True:
        if  os.path.exists(r'request'):
            i = 0
            file = r'request'
            if os.path.exists(file):
                os.remove(file)
            #print "hello"
            os.system("./control.sh")
        time.sleep(1)
        i+=1