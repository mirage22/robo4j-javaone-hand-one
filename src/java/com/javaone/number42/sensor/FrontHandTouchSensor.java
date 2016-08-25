package com.javaone.number42.sensor;

import com.robo4j.commons.annotation.RoboSensor;
import com.robo4j.lego.control.LegoSensor;
import com.robo4j.lego.enums.LegoEnginePartEnum;
import com.robo4j.lego.enums.LegoSensorEnum;
import com.robo4j.lego.enums.LegoSensorPortEnum;

/**
 * @author Miro Kopecky (@miragemiko)
 * @since 22.08.2016
 */

@RoboSensor(value = "frontHandSensor")
public class FrontHandTouchSensor implements LegoSensor {

    private LegoSensorPortEnum port;
    LegoEnginePartEnum part;
    private LegoSensorEnum sensor;
    private String holder;

    private String limit;


    public FrontHandTouchSensor() {
        this.port = LegoSensorPortEnum.S4;
        this.part = LegoEnginePartEnum.HAND;
        this.sensor = LegoSensorEnum.TOUCH;
        this.holder = "frontHandEngine";
        this.limit = "500";
    }

    @Override
    public LegoSensorPortEnum getPort() {
        return port;
    }

    @Override
    public LegoEnginePartEnum getPart() {
        return part;
    }

    @Override
    public LegoSensorEnum getSensor() {
        return sensor;
    }

    public String getHolder() {
        return holder;
    }

    public String getLimit() {
        return limit;
    }

    @Override
    public String toString() {
        return "FrontHandTouchSensor{" +
                "port=" + port +
                ", part=" + part +
                ", sensor=" + sensor +
                ", holder='" + holder + '\'' +
                ", limit='" + limit + '\'' +
                '}';
    }

}
