@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@echo off

setlocal

set MAVEN_PROJECT_BDIR=%~dp0
set MAVEN_PROJECT_BDIR=%MAVEN_PROJECT_BDIR:~0,-1%

set MAVEN_HOME=
set MAVEN_OPTS=
set MAVEN_CMD_LINE_ARGS=%*

if not "%MAVEN_SKIP_RC%" == "" goto skipRc
if exist "%HOMEDRIVE%%HOMEPATH%\mavenrc_pre.cmd" call "%HOMEDRIVE%%HOMEPATH%\mavenrc_pre.cmd"
if exist "%HOMEDRIVE%%HOMEPATH%\mavenrc_pre.bat" call "%HOMEDRIVE%%HOMEPATH%\mavenrc_pre.bat"
:skipRc

if not "%MAVEN_SKIP_RC%" == "" goto skipReadSettings
if exist "%MAVEN_PROJECT_BDIR%\.mvn\maven.config" (
  for /F "usebackq tokens=*" %%A in ("%MAVEN_PROJECT_BDIR%\.mvn\maven.config") do set MAVEN_OPTS=%%A
)
:skipReadSettings

set WRAPPER_JAR="%MAVEN_PROJECT_BDIR%\.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_PROPS="%MAVEN_PROJECT_BDIR%\.mvn\wrapper\maven-wrapper.properties"

if exist "%WRAPPER_JAR%" goto jarExists

echo Downloading %WRAPPER_URL%
if exist "%MAVEN_PROJECT_BDIR%\.mvn\wrapper\maven-wrapper.jar.tmp" del "%MAVEN_PROJECT_BDIR%\.mvn\wrapper\maven-wrapper.jar.tmp"
if exist "%MAVEN_PROJECT_BDIR%\.mvn\wrapper\maven-wrapper.jar" del "%MAVEN_PROJECT_BDIR%\.mvn\wrapper\maven-wrapper.jar"

for /F "usebackq tokens=*" %%A in ('%~dp0\.mvn\wrapper\maven-wrapper.properties') do (
  set WRAPPER_URL=%%A
)
set WRAPPER_URL=%WRAPPER_URL:distributionUrl=%

powershell.exe -Command "(New-Object Net.WebClient).DownloadFile('%WRAPPER_URL%','%WRAPPER_JAR%.tmp')"
move "%WRAPPER_JAR%.tmp" "%WRAPPER_JAR%"

:jarExists

if not "%JAVA_HOME%" == "" goto okJavaHome
for %%i in (java.exe) do set JAVA_HOME=%%~f$PATH:i
set JAVA_HOME=%JAVA_HOME:\bin\java.exe=%

:okJavaHome
set MAVEN_JAVA_EXE="%JAVA_HOME%\bin\java.exe"

%MAVEN_JAVA_EXE% %MAVEN_OPTS% -jar "%WRAPPER_JAR%" %MAVEN_CMD_LINE_ARGS%

if not "%MAVEN_SKIP_RC%" == "" goto end
if exist "%HOMEDRIVE%%HOMEPATH%\mavenrc_post.cmd" call "%HOMEDRIVE%%HOMEPATH%\mavenrc_post.cmd"
if exist "%HOMEDRIVE%%HOMEPATH%\mavenrc_post.bat" call "%HOMEDRIVE%%HOMEPATH%\mavenrc_post.bat"
:end

if "%MAVEN_TERMINATE_CMD%" == "on" (
  exit %ERRORLEVEL%
)

endlocal