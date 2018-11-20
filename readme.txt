1. 创建hardware目录，在该目录下创建HardCor.java文件(参考HelloWorldActivity.java文件)
2. 在build.gradle(app路径)文件中添加指定so文件的目录，so文件的源文件是C语言编写的，可以放在任意一个目录，它就是JNI文件
3. 如果工程里app目录下没有libs目录，那就自己在app目录下创建一个libs目录，然后创建armeabi目录，把编译出的so文件放入这个目录里，这样的做法是把库编译进apk文件里。
4. 编译.so文件的命令：arm-linux-gnueabi-gcc -I/usr/lib/jvm/java-7-openjdk-amd64/include/ -fPIC -shared -o libhardcor.so hardcor.c  
	以上的命令编译出的.so文件在下载到开发板上，点击屏幕上的按键以后会报错：缺少libc.so.6文件，所以替换成如下命令：
	arm-linux-gnueabi-gcc  -fPIC -shared hardcor.c -o libhardcor.so  -I /usr/lib/jvm/java-7-openjdk-amd64/include/ -nostdlib /home/root/cqa64_android_v5.1/android/prebuilts/ndk/9/platforms/android-21/arch-arm/usr/lib/libc.so
	该命令的-nostdlib选项意思是取消默认的链接库文件，后边的路径是指定自己要用的库文件。在用以上命令编译so文件的时候有个错误卡了好久：
	“root@qihua-virtual-machine:/home/root/cqa64_android_v5.1/HAL# arm-linux-gnueabihf-gcc -I/usr/lib/jvm/java-7-openjdk-amd64/include/ -fPIC -shared -o libhardcor.so hardcor.c -nostdlib /home/root/cqa64_android_v5.1/android/prebuilts/ndk/9/platforms/android-21/arch-arm/usr/lib/libc.so
	/home/root/cqa64_android_v5.1/HAL/gcc-linaro-arm-linux-gnueabihf-4.9-2014.07_linux/bin/../lib/gcc/arm-linux-gnueabihf/4.9.1/../../../../arm-linux-gnueabihf/bin/ld: error: libhardcor.so uses VFP register arguments, /home/root/cqa64_android_v5.1/android/prebuilts/ndk/9/platforms/android-21/arch-arm/usr/lib/libc.so does not
	/home/root/cqa64_android_v5.1/HAL/gcc-linaro-arm-linux-gnueabihf-4.9-2014.07_linux/bin/../lib/gcc/arm-linux-gnueabihf/4.9.1/../../../../arm-linux-gnueabihf/bin/ld: failed to merge target specific data of file /home/root/cqa64_android_v5.1/android/prebuilts/ndk/9/platforms/android-21/arch-arm/usr/lib/libc.so
	collect2: error: ld returned 1 exit status
	root@qihua-virtual-machine:/home/root/cqa64_android_v5.1/HAL# ”
	以上问题的解决办法是换了个交叉编译器好了，但是现在我也没搞明白编译so文件的编译器和编译Android源码的编译器有啥联系，以上问题的帖子链接在：http://bbs.100ask.org/forum.php?mod=viewthread&tid=22161&page=1#pid79973
5.  编译命令再次进化：
	arm-linux-gnueabi-gcc  -fPIC -shared hardcor.c -o libhardcor.so  -I /usr/lib/jvm/java-7-openjdk-amd64/include/ -nostdlib /home/root/cqa64_android_v5.1/android/prebuilts/ndk/9/platforms/android-21/arch-arm/usr/lib/libc.so -I /home/root/cqa64_android_v5.1/android/prebuilts/ndk/9/platforms/android-21/arch-arm/usr/include/ /home/root/cqa64_android_v5.1/android/prebuilts/ndk/9/platforms/android-21/arch-arm/usr/lib/liblog.so
	因为代码中使用了__android_log_print打印函数（该函数可以把调试信息打印在studio的logcat栏里）所以指定了头文件，-I 参数指定log.h头文件的路径；后边的目录指定了liblog.so的路径
	