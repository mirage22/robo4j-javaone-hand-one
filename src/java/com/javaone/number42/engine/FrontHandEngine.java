package com.javaone.number42.engine;

import com.robo4j.commons.annotation.RoboEngine;
import com.robo4j.lego.control.LegoEngine;
import com.robo4j.lego.enums.LegoAnalogPortEnum;
import com.robo4j.lego.enums.LegoEngineEnum;
import com.robo4j.lego.enums.LegoEnginePartEnum;

/**
 * @author Miro Kopecky (@miragemiko)
 * @since 22.08.2016
 */
@RoboEngine(value = "frontHandEngine")
public class FrontHandEngine implements LegoEngine {

    private final LegoAnalogPortEnum port;
    private final LegoEngineEnum engine;
    private final LegoEnginePartEnum part;

    public FrontHandEngine() {
        port = LegoAnalogPortEnum.A;
        engine = LegoEngineEnum.MEDIUM;
        part = LegoEnginePartEnum.HAND;
    }

    @Override
    public LegoAnalogPortEnum getPort() {
        return port;
    }

    @Override
    public LegoEngineEnum getEngine() {
        return engine;
    }

    @Override
    public LegoEnginePartEnum getPart() {
        return part;
    }

    @Override
    public String toString() {
        return "FrontHandEngine{" +
                "port=" + port +
                ", engine=" + engine +
                ", part=" + part +
                '}';
    }

}
