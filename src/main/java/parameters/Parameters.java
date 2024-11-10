package parameters;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static processing.core.PApplet.min;

public final class Parameters {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    public static final long SEED = 23;
    public static final Color BACKGROUND_COLOR = new Color(247);
    public static final int NUMBER_OF_FORTIFICATIONS = 4;
    public static final float FORTIFICATION_RADIUS = .125f * min(WIDTH, HEIGHT);
    public static final float CITY_RADIUS = .4f * min(WIDTH, HEIGHT);
    public static final int NUMBER_OF_ITERATIONS = 10000;
    public static final int NUMBER_OF_RANDOM_PICKS = 10;
    public static final float MIN_SEGMENT_OFFSET = .1f;
    public static final float MAX_SEGMENT_OFFSET = .9f;
    public static final Color STROKE_COLOR = new Color(0, 15, 35);
    public static final float MIN_STROKEWEIGHT = .5f;
    public static final float MAX_STROKEWEIGHT = 10;
    public static final Color FILL_COLOR = new Color(55, 35, 0, 25.5f);
    public static final float FILL_STROKEWEIGHT = 1;
    public static final float FILL_MIN_LINELENGTH_FACTOR = .25f;
    public static final float FILL_MAX_LINELENGTH_FACTOR = .5f;
    public static final float ANGLE_MIN_OFFSET = 1.25f;
    public static final float ANGLE_MID_OFFSET = 2.5f;
    public static final float ANGLE_MAX_OFFSET = 3.75f;
    public static final float ANGLE_OFFSET_DIVISOR = 4;

    /**
     * Helper method to extract the constants in order to save them to a json file
     *
     * @return a Map of the constants (name -> value)
     */
    public static Map<String, ?> toJsonMap() throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();

        Field[] declaredFields = Parameters.class.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(Parameters.class));
        }

        return Collections.singletonMap(Parameters.class.getSimpleName(), map);
    }

    public record Color(float red, float green, float blue, float alpha) {
        public Color(float red, float green, float blue) {
            this(red, green, blue, 255);
        }

        public Color(float grayscale, float alpha) {
            this(grayscale, grayscale, grayscale, alpha);
        }

        public Color(float grayscale) {
            this(grayscale, 255);
        }

        public Color(String hexCode) {
            this(decode(hexCode));
        }

        public Color(Color color) {
            this(color.red, color.green, color.blue, color.alpha);
        }

        public static Color decode(String hexCode) {
            return switch (hexCode.length()) {
                case 2 -> new Color(Integer.valueOf(hexCode, 16));
                case 4 -> new Color(Integer.valueOf(hexCode.substring(0, 2), 16),
                        Integer.valueOf(hexCode.substring(2, 4), 16));
                case 6 -> new Color(Integer.valueOf(hexCode.substring(0, 2), 16),
                        Integer.valueOf(hexCode.substring(2, 4), 16),
                        Integer.valueOf(hexCode.substring(4, 6), 16));
                case 8 -> new Color(Integer.valueOf(hexCode.substring(0, 2), 16),
                        Integer.valueOf(hexCode.substring(2, 4), 16),
                        Integer.valueOf(hexCode.substring(4, 6), 16),
                        Integer.valueOf(hexCode.substring(6, 8), 16));
                default -> throw new IllegalArgumentException();
            };
        }
    }
}
