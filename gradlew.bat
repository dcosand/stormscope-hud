@rem SPDX-License-Identifier: Apache-2.0
@echo off
setlocal

set APP_HOME=%~dp0
set APP_HOME=%APP_HOME:~0,-1%

set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

if defined JAVA_HOME (
  set JAVACMD=%JAVA_HOME%\bin\java.exe
) else (
  set JAVACMD=java.exe
)

"%JAVACMD%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
endlocal
