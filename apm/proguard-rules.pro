# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/maohongbin01/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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


# ProGuard configurationsfor NetworkBench Lens
#-keep class com.meiyou.common.apm.** { *; }
-keep class * extends java.lang.annotation.Annotation { *; }
-keep interface * extends java.lang.annotation.Annotation { *; }

-dontwarn com.meiyou.common.apm.**
-keepattributes Exceptions, Signature, InnerClasses

-keep @com.meiyou.common.apm.core.Proguard class * {
    *;
}

-keep class com.meiyou.common.apm.okhttp.internal.**{ *;}
# End APM Lens