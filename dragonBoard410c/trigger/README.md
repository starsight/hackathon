##源码说明
- [trigger.py](https://github.com/starsight/hackathon/blob/master/dragonBoard410c/trigger/trigger.py) IO口检测电平变化主程序

- [trigger.sh](https://github.com/starsight/hackathon/blob/master/dragonBoard410c/trigger/trigger.sh) 检测到电平变化后的功能程序


##流程
dragonBoard Low speed Expansion connector 33脚（APQ GPIO_28）接收到低电平，触发功能程序，进行拍照，上传图片操作。用来模拟触发红外传感器后，电平改变，触发dragonBoard拍照，上传图片操作。


##使用方法

切换到 dragonBoard410c/trigger 目录内

```
sudo python trigger.py
```

将33脚（APQ GPIO_28，默认低电平）连接35脚（+1.8V，高电平），[trigger.py](https://github.com/starsight/hackathon/blob/master/dragonBoard410c/trigger/trigger.py) 捕获到高电平，调用 [trigger.sh](https://github.com/starsight/hackathon/blob/master/dragonBoard410c/trigger/trigger.sh) 拍照，上传图片

**注意**：执行 [trigger.py](https://github.com/starsight/hackathon/blob/master/dragonBoard410c/trigger/trigger.py) 要加sudo
