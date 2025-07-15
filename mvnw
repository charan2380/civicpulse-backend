#!/bin/sh
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

MAVEN_PROJECT_BDIR=$(dirname "$0")

MAVEN_OPTS="$(
  if [ -f "${MAVEN_PROJECT_BDIR}/.mvn/maven.config" ]; then
    if [ "$(uname)" = "Windows_NT" ]; then
      # On Windows, Maven reads the config file in a special way.
      # We do the same to get the same results.
      # The quoting is just to make it safe.
      "C:\Program Files\Java\jdk-11\bin\java.exe" -Dfile.encoding=UTF-8 -classpath "${MAVEN_PROJECT_BDIR}/.mvn/wrapper/maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain --get-maven-opts "${MAVEN_PROJECT_BDIR}/.mvn/maven.config"
    else
      cat "${MAVEN_PROJECT_BDIR}/.mvn/maven.config"
    fi
  fi
)"
export MAVEN_OPTS

exec "${MAVEN_PROJECT_BDIR}/.mvn/wrapper/maven-wrapper.jar" "$@"