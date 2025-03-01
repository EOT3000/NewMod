package me.fly.newmod.technology.data;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.block.BlockManager;
import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.block.data.ModBlockData;
import me.fly.newmod.api.block.data.ModBlockDataSerializer;
import me.fly.newmod.api.util.PersistentDataUtils;
import me.fly.newmod.technology.EnergyComponent;

import java.util.Map;

public class EnergyHolderBlockDataImpl extends ModBlockData.AbstractModBlockData implements EnergyHolderBlockData {
    private int charge;
    private int capacity;

    protected EnergyHolderBlockDataImpl(ModBlockType type, int charge, int capacity) {
        super(type);

        this.charge = charge;
        this.capacity = capacity;
    }

    @Override
    public ModBlockData cloneBlock() {
        return new EnergyHolderBlockDataImpl(getType(), charge, capacity);
    }

    @Override
    public int getCharge() {
        return charge;
    }

    @Override
    public void setCharge(int charge) {
        this.charge = charge;
    }

    @Override
    public int addCharge(int chargeAdd) {
        int oldCharge = charge;

        charge = Math.min(charge+chargeAdd, capacity);

        return chargeAdd-(charge-oldCharge);
    }

    @Override
    public int removeCharge(int chargeRem) {
        int oldCharge = charge;

        charge = Math.max(charge-chargeRem, 0);

        return chargeRem+(charge-oldCharge);
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void transferTo(EnergyHolderBlockData data) {
        int moveable = Math.min(data.getCapacity()-data.getCharge(), charge);

        data.addCharge(moveable);
        charge-=moveable;
    }

    @Override
    public String toString() {
        return "EnergyHolderBlockDataImpl{" +
                "charge=" + charge +
                ", capacity=" + capacity +
                '}';
    }

    public static class EnergyHolderBlockDataSerializer extends ModBlockDataSerializer<EnergyHolderBlockDataImpl> {
        public EnergyHolderBlockDataSerializer() {
            super(EnergyHolderBlockDataImpl.class);
        }

        @Override
        public EnergyHolderBlockDataImpl getBlockData(Map<String, String> map) {
            BlockManager manager = NewMod.get().getBlockManager();
            ModBlockType type = manager.getType(map);
            int def = ((EnergyComponent) type).getCapacity();

            return new EnergyHolderBlockDataImpl(type, p(map.get("charge"), 0), p(map.get("capacity"), def));
        }

        private int p(String s, int def) {
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                return def;
            }
        }

        @Override
        public EnergyHolderBlockDataImpl defaultMeta(ModBlockType type) {
            return new EnergyHolderBlockDataImpl(type, 0, ((EnergyComponent) type).getCapacity());
        }

        @Override
        public boolean applyData(Map<String, String> map, EnergyHolderBlockDataImpl energyHolderBlockData) {
            map.put("charge", energyHolderBlockData.charge + "");
            map.put("capacity", energyHolderBlockData.capacity + "");
            map.put("id", PersistentDataUtils.NAMESPACED_KEY.toPrimitive(energyHolderBlockData.getType().getId(), null));

            return true;
        }
    }
}
