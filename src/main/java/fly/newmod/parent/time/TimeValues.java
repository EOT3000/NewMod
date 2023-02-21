package fly.newmod.parent.time;

public final class TimeValues {
    public static final int SUN_RADIUS = 80;
    public static final int FADE_RADIUS = 320;

    public static final int AXIS_COORDINATE = 0;
    public static final int START_COORDINATE = -640;
    public static final int END_COORDINATE = 640;

    public static final SunAlignment ALIGNMENT = SunAlignment.WEST_TO_EAST;


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
