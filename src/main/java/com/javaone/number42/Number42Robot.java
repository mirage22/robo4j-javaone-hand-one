package com.javaone.number42;


import com.javaone.number42.init.RobotLCD;
import com.javaone.number42.init.RobotMotor;
import com.javaone.number42.init.RobotNumber42;
import com.javaone.number42.init.RobotPAD;
import com.javaone.number42.init.RobotTouchSensor;
import com.javaone.number42.init.RobotUnitsRelation;

/**
 * @author Miro Kopecky (@miragemiko)
 * @since 26.08.2016
 */
public class Number42Robot {

    public static void main(String[] args) {
        RobotNumber42 robot = new RobotNumber42();
        RobotTouchSensor robotTouchSensor = new RobotTouchSensor("S4");
        RobotMotor robotMotor = new RobotMotor("A");

        robot.setMotor(robotMotor);
        robot.setSensor(robotTouchSensor);
        robot.setRelation(new RobotUnitsRelation(robotTouchSensor,robotMotor));
        robot.setLCD(new RobotLCD("MAGIC ROBOT"));
        robot.setPAD(new RobotPAD());
        robot.build();
        if(robot.check()){
            robot.activate();
        }
    }
}
