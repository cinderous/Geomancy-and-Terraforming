package net.cinderous.geomancyandterraforming.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class TickHandler {
    private String currentMethod;
    private final Map<String, Supplier<?>> variables;
    private final Map<String, TickData> methods;

    public TickHandler() {
        currentMethod = null;
        variables = new HashMap<>();
        methods = new HashMap<>();
    }

    public void tick() {
        currentMethod = Objects.requireNonNull(methods.get(currentMethod), "You tried to access a method that hasn't been initialized: " + currentMethod).run();
    }

    public <T> void addVariable(String name, T variable) {
        variables.put(name, () -> variable);
    }

    public void addRepeatedDelayedMethod(String name, int timesToRun, int tickDelay, TickMethod method) {
        methods.put(name, new TickData(timesToRun, tickDelay, name, method));

        if (currentMethod == null) currentMethod = name;
    }

    public void addDelayedMethod(String name, int tickDelay, TickMethod method) {
        addRepeatedDelayedMethod(name, 1, tickDelay, method);
    }

    public void addRepeatedMethod(String name, int timesToRun, TickMethod method) {
        addRepeatedDelayedMethod(name, timesToRun, 0, method);
    }

    public void addMethod(String name, TickMethod method) {
        addRepeatedDelayedMethod(name, 1, 0, method);
    }

    public Supplier<?> getVariable(String name) {
        return Objects.requireNonNull(variables.get(name), "You tried to access a variable that hasn't been initialized: " + name);
    }

    public <T> void setVariable(String name, T data) {
        variables.replace(name, () -> data);
    }

    private static class TickData {
        private int timesRun, currentDelay;
        private final int timesToRun, tickDelay;
        private final String name;
        private final TickMethod method;

        public TickData(int timesToRun, int tickDelay, String name, TickMethod method) {
            this.timesToRun = timesToRun;
            this.tickDelay = tickDelay;
            this.name = name;
            this.method = method;
            timesRun = 0;
            currentDelay = 0;
        }

        public String run() {
            String nextMethod = name;

            if (currentDelay++ >= tickDelay) {
                nextMethod = method.run(timesRun);
                currentDelay = 0;
                timesRun++;
            }

            if (timesRun >= timesToRun) {
                timesRun = 0;
                return nextMethod;
            }

            return name;
        }
    }

    public interface TickMethod {
        String run(int timesRun);
    }
}