/*
 * Copyright (C) 2016. Miroslav Kopecky
 * This Number42Robot.java is part of robo4j (http://www.robo4j.io).
 *
 *     robo4j-javaone-hand-one is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     robo4j-javaone-hand-one is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with  robo4j (http://www.robo4j.io).  If not, see <http://www.gnu.org/licenses/>.
 */

package com.javaone.number42;


import com.javaone.number42.init.RobotHandRelation;
import com.javaone.number42.init.RobotLCD;
import com.javaone.number42.init.RobotMotor;
import com.javaone.number42.init.RobotNumber42;
import com.javaone.number42.init.RobotPAD;
import com.javaone.number42.init.RobotPlatformRelation;
import com.javaone.number42.init.RobotTouchSensor;

/**
 * @author Miro Kopecky (@miragemiko)
 * @since 26.08.2016
 */
public class Number42Robot {

    public static void main(String[] args) {
        RobotNumber42 robot = new RobotNumber42();
        RobotTouchSensor robotHandTouchSensor = new RobotTouchSensor("S4");
        RobotMotor robotHandMotor = new RobotMotor("A");
        RobotMotor robotLeftMotor = new RobotMotor("B");
        RobotMotor robotRightMotor = new RobotMotor("C");


        robot.setHandMotor(robotHandMotor);
        robot.setHandSensor(robotHandTouchSensor);
        robot.setHandRelation(new RobotHandRelation(robotHandTouchSensor,robotHandMotor));
        robot.setMotorLeft(robotLeftMotor);
        robot.setMotorRight(robotRightMotor);
        robot.setPlatformRelation(new RobotPlatformRelation(robotLeftMotor, robotRightMotor));

        robot.setLCD(new RobotLCD("JavaOne Robot: Number42 FUll"));
        robot.setPAD(new RobotPAD());
        robot.build();
        if(robot.check()){
            robot.activate();
        }
    }
}
