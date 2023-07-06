package me.fly.newmod.technology.data;

import me.fly.newmod.api.block.data.ModBlockData;

public interface EnergyHolderBlockData extends ModBlockData {
    int getCharge();
    void setCharge(int charge);
    int addCharge(int chargeAdd);
    int removeCharge(int charge);

    int getCapacity();
    void setCapacity(int capacity);

    void transferTo(EnergyHolderBlockData data);
}
