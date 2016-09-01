#!/bin/sh

# Copyright (C) 2016. Miroslav Kopecky
# This LegoPadKeyEnum.java is part of robo4j.
#
# robo4j is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# robo4j is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with robo4j .  If not, see <http://www.gnu.org/licenses/>.

function prepare {
  JAR_FILE_NAME="Robo4jNumber42.jar" 
  echo "SPECIFIED IP: $1 file : $JAR_FILE_NAME"
  rm -R ./out/*
  cp ~/JAVA/JAVA8/robo4j/robo4j-brick-j1-client/build/libs/robo4j-brick-j1-client-all-alfa-0.2.jar ./libs/
  cp ./libs/robo4j-brick-j1-client-all-alfa-0.2.jar ./out/  
  javac -cp ./src/main/java/ ./src/main/java/com/javaone/number42/Number42Robot.java -classpath ./libs/robo4j-brick-j1-client-all-alfa-0.2.jar -d ./out/ 
  cd ./out
  jar xf ./robo4j-brick-j1-client-all-alfa-0.2.jar
  rm ./robo4j-brick-j1-client-all-alfa-0.2.jar
  cd ..
  jar cvfm "$JAR_FILE_NAME" ./src/main/resources/META-INF/MANIFEST.MF -C ./out/ . 
  scp ./"$JAR_FILE_NAME" root@"$1":/home/root/lejos/samples 
  echo "Robo4j.IO Number42 DONE" 
}

if [ $# -eq 0 ] ; then
    echo "please, specify ip address"
    exit 1
else
 prepare $1
 exit 0
fi
