#!/bin/sh
sudo fswebcam -d /dev/video1  control.jpg --bottom-banner --title "dragonboard@wenjie" --no-timestamp -r 1280x720

if [ -f "control.jpg" ];then
curl --request POST --data-binary @"control.jpg" --header "U-ApiKey:818c7d66df46c26d610e6a4a37ebda12" http://api.yeelink.net/v1.0/device/344726/sensor/383332/photos
fi
