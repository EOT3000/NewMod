package me.fly.newmod.plants.type;

public enum Crop {
    PITCHER_PLANT(0.4f),
    TORCHFLOWER(1.3f),

    BEETROOT(0.55f),
    CARROT(0.65f),
    POTATO(0.55f),
    WHEAT(0.75f),

    MELON(0.75f),
    PUMPKIN(0.75f),

    ACACIA(1.2f),
    ;

    public final float temperature;

    Crop(float temperature) {
        this.temperature = temperature;
    }
}
