package me.fly.newmod.technology;

public interface EnergyComponent {
    EnergyComponentType getType();

    int getCapacity();



    enum EnergyComponentType {
        SENDER,
        RECEIVER,
        CONSUMER,
        PRODUCER,
        STORAGE,
        MANAGER,
        SENDER_RECEIVER
    }
}
