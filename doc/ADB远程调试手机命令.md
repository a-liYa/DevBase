
1. 手机通过usb与电脑连接；

2. 重启手机上的adbd，开启网络调试功能；
	输入：
		adb tcpip 5555
	返回：
		restarting in TCP mode port: 5555
		
3. 连接，成功后可拔出 USB；
	输入：
		adb connect 192.168.31.214:5555
	返回：
		connected to 192.168.31.214:5555

4.  断开连接；
	输入：
		adb disconnect 192.168.31.214
		
注意：ip是手机ip，电脑要与手机连接同一WiFi，端口号可以随便写但要保持一致。