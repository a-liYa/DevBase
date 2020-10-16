# Activity的四种启动模式

### 1. Standard  
    默认启动模式，依附在调用方所在 Task id。
    
### 2. SingleTop  
    需要启动的Activity刚好处于任务栈顶时，复用此Activity。 任务栈：
        1. 若调用方配置FLAG_ACTIVITY_NEW_TASK，依附的 Task id 为 taskAffinity指定。
        2. 否则，如同 Standard 模式。
    
### 3. SingleTask  
    如果当前任务栈已有该Activity实例，重用该实例，并移除其上的其他Activity。依附的 Task id 为 taskAffinity指定。

### 4. SingleInstance  
    全局单例模式，并独占一个任务栈。一般同一个taskAffinity对应同一个task，此时会新开 Task，导致 affinity 一对多个 Task。

## Activity Intent 的 Flags  

### 1. Intent.FLAG_ACTIVITY_NEW_TASK  
    Context#startActivity(Intent),在 API 28（Android 9.0）及以上必须添加该标志，否则崩溃。
    
### 2. Intent.FLAG_ACTIVITY_SINGLE_TOP  
    作用等同于 Manifest 指定 SingleTop 启动模式。

### 3. Intent.FLAG_ACTIVITY_CLEAR_TOP 
    作用等同于 Manifest 指定 SingleTask 启动模式。
    
### 4. Intent.FLAG_ACTIVITY_CLEAR_TASK 
    结合FLAG_ACTIVITY_NEW_TASK，会清空当前任务栈所有Activity，否则无效。
    
### 5. Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
     如果当前任务栈已有该Activity实例，重用该实例，但不会清空其上面Activity。eg: A,B,C,D D#startActivity(B) -> A,C,D,B


注意：前台才存在叠加的多个 Task，在进入后台的第一时间就会被拆开。
最近任务键，一个任务栈对应一个 taskAffinity，返回键是根据 Task 依次返回的。


属性 - android:taskAffinity 默认为包名，配置不同的属性值，会开启对应不同的 Task id。
Android 9.0 及以后 Stack id 一一对应 Task id；Android 9.0以前
    
应用Activity栈查看命令：adb shell dumpsys activity activities

 TaskRecord{17ef6de #69 A=com.aliya.base.sample U=0 StackId=62 sz=1}
        Run #0: ActivityRecord{fdaf73a u0 com.aliya.base.sample/.ui.activity.launch.SingleInstanceActivity t69}
        
 TaskRecord{b854c0b #85 A=com.aliya.base.sample U=0 StackId=78 sz=2}
        Run #1: ActivityRecord{1c66cc u0 com.aliya.base.sample/.MainActivity t85}
        Run #0: ActivityRecord{dd3d2a4 u0 com.aliya.base.sample/.SplashActivity t85 f}

不同任务栈(TaskStack)开启Activity时，若被开启Activity的launchMode="singleTask"时，逻辑与同一 TaskID 相似