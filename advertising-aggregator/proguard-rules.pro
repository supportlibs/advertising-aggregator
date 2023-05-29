# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep public class com.lib.advertising_control.remote_config.AdRemoteConfigManager
-keep public class com.lib.advertising_control.remote_config.model.ConfigAdModel
-keep public class com.lib.advertising_control.remote_config.model.RequestState
-keep public class com.lib.advertising_control.remote_config.model.UiState
-keep public class com.lib.advertising_control.remote_config.model.RemoteConfigFetchStatus
-keep public class com.lib.advertising_control.remote_config.model.BannerState
-keep public class com.lib.advertising_control.remote_config.model.InterstitialLoadState
-keep public class com.lib.advertising_control.remote_config.model.InterstitialProgressState
-keep public class com.lib.advertising_control.remote_config.model.InterstitialShowState
-keep public class com.lib.advertising_control.remote_config.model.AdType

-keepclassmembers class * extends java.lang.Enum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}