package com.javaone.number42.unit;

import com.javaone.number42.fronthand.ClientFrontHandEngineConsumer;
import com.javaone.number42.fronthand.ClientFrontHandTouchProducer;
import com.robo4j.brick.logging.SimpleLoggingUtil;
import com.robo4j.brick.service.LcdService;
import com.robo4j.brick.util.LegoClientUnitProviderUtil;
import com.robo4j.brick.client.request.RequestProcessorFactory;
import com.robo4j.commons.agent.AgentConsumer;
import com.robo4j.commons.agent.AgentProducer;
import com.robo4j.commons.agent.AgentStatus;
import com.robo4j.commons.agent.GenericAgent;
import com.robo4j.commons.agent.ProcessAgent;
import com.robo4j.commons.agent.ProcessAgentBuilder;
import com.robo4j.commons.annotation.RoboUnit;
import com.robo4j.commons.command.RoboUnitCommand;
import com.robo4j.commons.unit.DefaultUnit;
import com.robo4j.lego.control.LegoBrickRemote;
import com.robo4j.lego.control.LegoEngine;
import com.robo4j.lego.control.LegoSensor;
import com.robo4j.lego.control.LegoUnit;
import com.robo4j.lego.enums.LegoEnginePartEnum;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;


/**
 * @author Miro Kopecky (@miragemiko)
 * @since 03.07.2016
 */

@RoboUnit(value = com.javaone.number42.unit.FrontHandUnit.UNIT_NAME,
        system = com.javaone.number42.unit.FrontHandUnit.SYSTEM_NAME,
        producer = com.javaone.number42.unit.FrontHandUnit.PRODUCER_NAME,
        consumer = com.javaone.number42.unit.FrontHandUnit.CONSUMER_NAME)
public class FrontHandUnit extends DefaultUnit implements LegoUnit {

    /* all connected pars  */
    static final String UNIT_NAME = "frontHandUnit";
    static final String SYSTEM_NAME = "legoBrick1";
    static final String PRODUCER_NAME = "frontHandSensor";
    static final String CONSUMER_NAME = "frontHandEngine";

    public FrontHandUnit() {
    }

    @Override
    public void setExecutor(final ExecutorService executor){
        this.executorForAgents = executor;
    }

    @Override
    public LegoUnit init(final LegoBrickRemote legoBrickRemote,
                         final Map<String, LegoEngine> engineCache,
                         final Map<String, LegoSensor> sensorCache){

        if(Objects.nonNull(executorForAgents)){

            this.agents = new ArrayList<>();

            this.active = new AtomicBoolean(true);

            final Exchanger<Boolean> frontHandExchanger = new Exchanger<>();

            SimpleLoggingUtil.print(FrontHandUnit.class, "INIT FRONT HAND agent");
            executorForAgents.execute(new ClientFrontHandTouchProducer(frontHandExchanger, sensorCache.entrySet().stream()
                    .filter(sensorEntry -> sensorEntry.getValue().getPart().equals(LegoEnginePartEnum.HAND))
                    .map(Map.Entry::getValue)
                    .map(LegoClientUnitProviderUtil::createTouchSensor)
                    .reduce(null, (e1, e2) -> e2)));
            executorForAgents.submit(new ClientFrontHandEngineConsumer(frontHandExchanger,  engineCache.entrySet().stream()
                    .filter(entry -> entry.getValue().getPart().equals(LegoEnginePartEnum.HAND))
                    .map(Map.Entry::getValue)
                    .map(LegoClientUnitProviderUtil::createEngine)
                    .reduce(null, (e1, e2) -> e2)));
        }


        return this;

    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public boolean process(RoboUnitCommand command){
        return false;
    }

    @Override
    public boolean isActive() {
        return active.get();
    }

    @Override
    public String getUnitName() {
        return UNIT_NAME;
    }

    @Override
    public String getSystemName() {
        return SYSTEM_NAME;
    }

    @Override
    public String getProducerName() {
        return PRODUCER_NAME;
    }

    @Override
    public String getConsumerName() {
        return CONSUMER_NAME;
    }

    //Protected Methods
    @Override
    protected GenericAgent createAgent(String name, AgentProducer producer, AgentConsumer consumer) {
        return Objects.nonNull(producer) && Objects.nonNull(consumer) ? ProcessAgentBuilder.Builder(executorForAgents)
                .setProducer(producer)
                .setConsumer(consumer)
                .build() : null;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public Map<RoboUnitCommand, Function<ProcessAgent, AgentStatus>> initLogic(){
        return Collections.unmodifiableMap(Collections.EMPTY_MAP);
    }

}
