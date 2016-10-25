#AVSDK(Android)视频旋转方案
## 转置后的方案
AVSDK1.8.2后增加了视频转置方案。打开转置后，腾讯云后台会将视频转为正向图像，这样接收端收到的图像始终是正向的，此时的图像的角度值将会被丢失，渲染客户端仅需关注图像的高宽。
### 开启转置方法
1. 监听手机旋转角度，[如LiveActivity中OrientationEventListener](https://github.com/zhaoyang21cn/Android_Suixinbo/blob/master/app/src/main/java/com/tencent/qcloud/suixinbo/views/LiveActivity.java)
2. 在监听中将手机角度设置到AVVideoCtrl.setRotation，腾讯云后台将使用这个角度值将图像转正
3. 进房间时AVRoomMulti.EnterParam.Builder中设置isDegreeFix为true。[参考资料](https://github.com/zhaoyang21cn/Android_Suixinbo/blob/master/app/src/main/java/com/tencent/qcloud/suixinbo/presenters/EnterLiveHelper.java)
4. 此时观众端收到的将是正向画面，客户可以调用canvas.rotate方法将画面旋转到自己需要的角度（如主播横屏时，观众也要满屏横屏显示）


## 兼容转置前角度方案
老版本的视频使用角度值方案来帮助客户端展示正确的画面，渲染端收到的YUVTexture中可以通过getImgAngle()方法获取主播端的角度，然后根据本地手机的角度和远端画面的角度来旋转画面。[具体的角度值原理](https://github.com/zhaoyang21cn/Android_Suixinbo/blob/master/%E6%A8%AA%E7%AB%96%E5%B1%8F%E8%A7%92%E5%BA%A6%E8%A7%A3%E9%87%8A.pdf)。
### 使用方法
1. 监听手机旋转角度
2. 在监听中将手机角度设置到AVVideoCtrl.setRotation，观众端将收到这个角度值
3. 渲染端根据需要旋转画面。[参考资料](https://github.com/zhaoyang21cn/Android_Suixinbo/blob/master/app/src/main/java/com/tencent/qcloud/suixinbo/avcontrollers/GLVideoView.java)

    int angle = mYuvTexture.getImgAngle();     
    rotation = (angle + mRotation) % 4;    
    //将图像转正    
    canvas.rotate(rotation * 90, 0, 0, 1);

