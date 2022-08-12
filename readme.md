# Кастомный системный сервис для ОС Android.

## Описание
кастомный сервис который сообщает в лог системы сообщение об удалении и установке приложений. Протестировано на Android 9 последней ревизии. При тестировании использовался продукт aosp_x86_64-eng

## инструкция по внедрению в AOSP.
1. Переместить папку AppMonitorService в /frameworks/base/services/
2. Добавить в файл /frameworks/base/services/Android.bp в раздел static_libs строку `"services.appmonitor",`
3. Добавить строку:
`"services/AppMonitorService/java/com/android/server/appmonitor/IAppMonitorService.aidl",`
в файл /frameworks/base/services/Android.bp в раздел srcs.
4. Отредактируйте файл /frameworks/base/services/java/com/android/server/SystemServer.java

4.1 Добавьте импорт библиотеки 
`import com.android.server.appmonitor.AppMonitorService;`

4.2 Добавьте переменную 
`private static final String APP_MONITOR_SERVICE="com.android.server.appmonitor.AppMonitorService";`

4.3 Добавьте вызов сервиса в метод startOtherServices() (в моем случае добавлял в строку 872):
            `            

    //Add AppMonitorService
     try{
    	Slog.i(TAG, "Starting APP_MONITOR_SERVICE");
    	mSystemServiceManager.startService(APP_MONITOR_SERVICE);
    	Slog.i(TAG, "APP_MONITOR_SERVICE Started");
    } 
	catch (Throwable e) {
    Slog.e(TAG, "Failure starting APP_MONITOR_SERVICE Service", e);
    }
