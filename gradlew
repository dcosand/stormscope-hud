#!/bin/sh

#
# SPDX-License-Identifier: Apache-2.0
#

APP_PATH=$0

while [ -h "$APP_PATH" ]; do
    ls=$(ls -ld "$APP_PATH")
    link=$(expr "$ls" : '.*-> \(.*\)$')
    if expr "$link" : '/.*' >/dev/null; then
        APP_PATH=$link
    else
        APP_PATH=$(dirname "$APP_PATH")"/$link"
    fi
done

APP_HOME=$(cd "$(dirname "$APP_PATH")" && pwd -P)

CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

JAVA_HOME=${JAVA_HOME:-}
if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ]; then
    JAVACMD="$JAVA_HOME/bin/java"
else
    JAVACMD=java
fi

exec "$JAVACMD" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
