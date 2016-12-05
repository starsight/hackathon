import os
import time

if __name__ == "__main__":
    i = 0
    while True:
        if  i%30==0:
            i = 0
            file = r'request'
            if os.path.exists(file):
                os.remove(file)
            #print "hello"
            os.system("sudo ./regular.sh")
        time.sleep(1)
        i+=1