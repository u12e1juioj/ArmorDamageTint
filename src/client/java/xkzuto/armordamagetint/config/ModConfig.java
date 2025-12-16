package xkzuto.armordamagetint.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("armordamagetint.json");

    private static ModConfig INSTANCE;

    // Toggle damage tint on player armor
    public boolean enabled = true;

    // Custom color components (0-255) - only used when preset is "Custom"
    public int red = 255;
    public int green = 0;
    public int blue = 0;

    // Intensity/opacity (0-100%)
    public int intensity = 25;

    // Preset color selection
    public String preset = "Vanilla Red";

    public static ModConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = load();
        }
        return INSTANCE;
    }

    public static ModConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                ModConfig config = GSON.fromJson(json, ModConfig.class);
                if (config != null) {
                    return config;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ModConfig config = new ModConfig();
        config.save();
        return config;
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get the effective RGB based on preset or custom values
    private int[] getEffectiveRGB() {
        switch (preset) {
            case "Vanilla Red":
                return new int[]{255, 0, 0};
            case "Light Red":
                return new int[]{255, 100, 100};
            case "Blue":
                return new int[]{0, 100, 255};
            case "Green":
                return new int[]{0, 255, 100};
            case "Purple":
                return new int[]{200, 0, 255};
            case "Orange":
                return new int[]{255, 150, 0};
            case "Yellow":
                return new int[]{255, 255, 0};
            case "White":
                return new int[]{255, 255, 255};
            case "Custom":
            default:
                return new int[]{red, green, blue};
        }
    }

    public int getTintColor() {
        int[] rgb = getEffectiveRGB();
        int alpha = (int) (255 * (intensity / 100.0));
        return (alpha << 24) | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
    }

    // Called when user changes RGB sliders - switches to Custom
    public void setCustomColor(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.preset = "Custom";
    }
}
