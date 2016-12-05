#!/usr/bin/python
import threading
import time

from libsoc import gpio

def main(gpio_input_id):
    gpio_in = gpio.GPIO(gpio_input_id, gpio.DIRECTION_INPUT)
    with gpio.request_gpios((gpio_in)):
        assert gpio.DIRECTION_INPUT == gpio_in.get_direction()
        while True :
            if gpio_in.is_high():
                print 'high'
                os.system("chmod +x trigger.sh")
                os.system("sudo ./trigger.sh")
            else :
                print 'low'
            time.sleep(1)




if __name__ == '__main__':
    import os
    gpio_input_id = int(os.environ.get('GPIO_IN', '28'))
    main(gpio_input_id)
