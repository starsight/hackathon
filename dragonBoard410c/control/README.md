##源码说明
- [server.py](https://github.com/starsight/hackathon/blob/master/dragonBoard410c/server.py) 服务器端（与410c通信）程序

- [send.py](https://github.com/starsight/hackathon/blob/master/dragonBoard410c/send.py) 410c（与服务器端通信）程序

- [control.py](https://github.com/starsight/hackathon/blob/master/dragonBoard410c/control.py) 410c端运行程序，检测410c上是否创建request文件，就从而判断手机端是否请求拍摄图片，若检测到则拍着图片并发送到服务器。

- [control.sh](https://github.com/starsight/hackathon/blob/master/dragonBoard410c/control.sh) 在 [control.py](https://github.com/starsight/hackathon/blob/master/dragonBoard410c/control.py) 中被调用执行


##流程
当服务器端 [server.py](https://github.com/starsight/hackathon/blob/master/dragonBoard410c/server.py)  目录下出现request 文件时，服务器端程序通过 [server.py](https://github.com/starsight/hackathon/blob/master/dragonBoard410c/server.py)  向410c发送"request"请求。
410c端 [send.py](https://github.com/starsight/hackathon/blob/master/dragonBoard410c/send.py)接收到请求后，创建request文件，[control.py](https://github.com/starsight/hackathon/blob/master/dragonBoard410c/control.py)发现request文件创建，则进行图片拍摄，上传图片到服务器等操作。



##使用方法

切换到dragonBoard410c目录内

####服务器端
```
python server.py
```


####dragonBoard 410c
2.打开一个终端
```
python control.py
```

3.新开一个终端
```
python send.py
```

**注意**：服务器端的服务要在410c服务开启前开启
