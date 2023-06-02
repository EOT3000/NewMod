package me.fly.newmod.time;

public final class TimeValues {
    public static final int SUN_RADIUS = 2400;
    public static final int FADE_RADIUS = 4800;
    public static final int EFFECT_RADIUS = SUN_RADIUS+FADE_RADIUS;

    public static final int AXIS_COORDINATE = 0;
    public static final int START_COORDINATE = -24000;
    public static final int END_COORDINATE = 24000;

    public static final SunAlignment ALIGNMENT = SunAlignment.WEST_TO_EAST;

    public static final int LOOP_BACK_COORDINATE = ALIGNMENT.direction*EFFECT_RADIUS+END_COORDINATE;
    public static final int LOOP_START_COORDINATE = -ALIGNMENT.direction*EFFECT_RADIUS+START_COORDINATE;

    public static final double INCREMENT = 0.5;


    public enum SunAlignment {
        NORTH_TO_SOUTH(1, true),
        SOUTH_TO_NORTH(-1, true),
        EAST_TO_WEST(-1, false),
        WEST_TO_EAST(1, false);

        public final int direction;
        public final boolean z;

        SunAlignment(int direction, boolean z) {
            this.direction = direction;

            this.z = z;
        }
    }
}
