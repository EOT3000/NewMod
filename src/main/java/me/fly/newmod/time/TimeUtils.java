package me.fly.newmod.time;

import net.minecraft.world.entity.animal.EntityBee;

public class TimeUtils {
    public class a {

    }

    class b {

    }

    protected class c {

    }

    private class d {

    }

    public static class e {

    }

    public static void main(String[] args) {

        for(Class<?> clazz : TimeUtils.class.getDeclaredClasses()) {
            System.out.println(clazz.getName());
        }

        System.exit(0);

        for(int i = 0; i <= 12000; i+=1) {
            if(time(i, false) == 12542) {
                System.out.println(i);
                System.out.println("n");
                System.out.println();
            }

            if(time(i, true) == 23460) {
                System.out.println(i);
                System.out.println("m");
                System.out.println();
            }
        }
    }

    public static int timeMorning(int brightness) {
        if(brightness < 6000) {
            return 18000+brightness;
        } else {
            return brightness-6000;
        }
    }

    public static int timeNight(int brightness) {
        return 18000-(brightness);
    }

    public static int time(int brightness, boolean morning) {
        if(morning) {
            return timeMorning(brightness);
        } else {
            return timeNight(brightness);
        }
    }

    public enum TimeQuarter {
        FIRST(0,6000,
                6000,12000, true),
        SECOND(6000,12000,
                12000,6000, false),
        THIRD(12000,18000,
                6000,0, false),
        FOURTH(18000,24000,
                0,6000, true);

        public final int startTime;
        public final int endTime;

        public final int startBrightness;
        public final int endBrightness;

        public final boolean morning;

        TimeQuarter(int startTime, int endTime, int startBrightness, int endBrightness, boolean morning) {
            this.startTime = startTime;
            this.endTime = endTime;

            this.startBrightness = startBrightness;
            this.endBrightness = endBrightness;

            this.morning = morning;
        }
    }
}
