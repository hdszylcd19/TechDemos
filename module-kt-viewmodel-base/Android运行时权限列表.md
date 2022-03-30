## 运行时权限

### CALENDAR（日历）

| 权限                                           | API版本 |
| ---------------------------------------------- | ------- |
| READ_CALENDAR（允许程序读取用户的日程信息）    | 1       |
| WRITE_CALENDAR（允许程序写入日程，但不可读取） | 1       |

### CAMERA（相机）

| 权限                                 | API版本 |
| ------------------------------------ | ------- |
| CAMERA（允许程序访问摄像头进行拍摄） | 1       |

### CONTACTS（联系人）

| 权限                                             | API版本 |
| ------------------------------------------------ | ------- |
| READ_CONTACTS（允许程序访问联系人通讯录信息）    | 1       |
| WRITE_CONTACTS（允许程序写入联系人，但不可读取） | 1       |
| GET_ACCOUNTS（允许程序访问账户Gmail列表）        | 1       |

### LOCATION（位置）

| 权限                       | API版本 |
| -------------------------- | ------- |
| ACCESS_FINE_LOCATION       | 1       |
| ACCESS_COARSE_LOCATION     | 1       |
| ACCESS_BACKGROUND_LOCATION | 29      |
| ACCESS_MEDIA_LOCATION      | 29      |

### MICROPHONE（麦克风）

| 权限                | API版本 |
| ------------------- | ------- |
| RECORD_AUDIO        | 1       |

### PHONE（手机）

| 权限                       | API版本 |
| -------------------------- | ------- |
| READ_PHONE_STATE           | 1       |
| CALL_PHONE                 | 1       |
| READ_CALL_LOG              | 16      |
| WRITE_CALL_LOG             | 16      |
| ADD_VOICEMAIL              | 14      |
| USE_SIP                    | 9       |
| ANSWER_PHONE_CALLS         | 26      |
| READ_PHONE_NUMBERS         | 26      |
| PROCESS_OUTGOING_CALLS     | 1（api29已过时）|

### SENSORS（传感器）

| 权限                       | API版本 |
| -------------------------- | ------- |
| BODY_SENSORS               | 20      |

### SMS（短信）

| 权限                       | API版本 |
| -------------------------- | ------- |
| SEND_SMS                   | 1       |
| RECEIVE_SMS                | 1       |
| READ_SMS                   | 1       |
| RECEIVE_WAP_PUSH           | 1       |
| RECEIVE_MMS                | 1       |

### BLUETOOTH（蓝牙）

Android 12 在运行时权限方面又有了一些新的变化。

之前 Android 系统中有一个很奇怪的现象，当我们在应用中使用蓝牙扫描附近设备的时候，需要申请地理位置权限。

蓝牙权限并不是运行时权限，但地理位置权限却是。这就给很多开发者造成了一些困扰，明明只是想要使用蓝牙的功能，却让用户误以为想要定位设备的地理位置。

这个问题在 2021 年的 Goole I/O 大会上有专门提及。据说是从 Android 1.0 版本就是这样设计的，用户不明白为什么，开发者也不明白为什么，就连 Google 自己都不明白为什么？

于是在 Android 12 系统中，Google 对蓝牙权限重新进行了设计，从而修复了这个已经存在了十几年的 bug。

从 Android 12 开始，过去的蓝牙权限被拆分成了 3 个新的权限，并且全都是运行时权限：

| 权限                                                         | API版本 |
| ------------------------------------------------------------ | ------- |
| BLUETOOTH_SCAN（用于使用蓝牙扫描附近其它的蓝牙设备）         | 31      |
| BLUETOOTH_ADVERTISE（用于允许当前的设备可以被其它的蓝牙设备所发现） | 31      |
| BLUETOOTH_CONNECT（用于连接之前已经配对过的蓝牙设备）        | 31      |

### STORAGE（存储卡）

| 权限                       | API版本 |
| -------------------------- | ------- |
| WRITE_EXTERNAL_STORAGE     | 4       |
| READ_EXTERNAL_STORAGE      | 16      |

### ACCEPT_HANDOVER

| 权限                  | API版本 |
| --------------------- | ------- |
| ACCEPT_HANDOVER       | 28      |

### ACTIVITY_RECOGNITION

| 权限                       | API版本 |
| -------------------------- | ------- |
| ACTIVITY_RECOGNITION       | 29      |

### UWB_RANGING

| 权限                       | API版本 |
| -------------------------- | ------- |
| UWB_RANGING                | 31      |                 


>
> 参考资料：
> 
> [Android 中的权限](https://developer.android.com/guide/topics/permissions/overview?hl=zh-cn)
>
> [Android 所有权限](https://blog.csdn.net/qq_33731155/article/details/86612900)