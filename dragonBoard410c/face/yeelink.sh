#!/bin/sh
sudo fswebcam -d /dev/video1  /home/linaro/Desktop/Hackathon/dragonBoard410c/face/yeelink.jpg --bottom-banner --title "dragonboard@wenjie" --no-timestamp -r 1280x720

python yeelink.py

if [ -f "/home/linaro/Desktop/Hackathon/dragonBoard410c/face/yeelink_face/out.jpg" ];then
curl --request POST --data-binary @"/home/linaro/Desktop/Hackathon/dragonBoard410c/face/yeelink_face/out.jpg" --header "U-ApiKey:818c7d66df46c26d610e6a4a37ebda12" http://api.yeelink.net/v1.0/device/344726/sensor/383332/photos
fi
