package xkzuto.armordamagetint.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.List;

public class ConfigScreen {

    private static final List<String> PRESETS = Arrays.asList(
            "Vanilla Red", "Light Red", "Blue", "Green", "Purple", "Orange", "Yellow", "White", "Custom"
    );

    public static Screen create(Screen parent) {
        ModConfig config = ModConfig.getInstance();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("config.armordamagetint.title"))
                .setSavingRunnable(config::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("config.armordamagetint.category.general"));

        // Enable/Disable toggle
        general.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("config.armordamagetint.enabled"),
                        config.enabled)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.armordamagetint.enabled.tooltip"))
                .setSaveConsumer(value -> config.enabled = value)
                .build());

        // Preset color dropdown
        general.addEntry(entryBuilder.startStringDropdownMenu(
                        Component.translatable("config.armordamagetint.preset"),
                        config.preset,
                        s -> Component.literal(s))
                .setSelections(PRESETS)
                .setDefaultValue("Vanilla Red")
                .setTooltip(Component.translatable("config.armordamagetint.preset.tooltip"))
                .setSaveConsumer(value -> config.preset = value)
                .build());

        // Intensity slider
        general.addEntry(entryBuilder.startIntSlider(
                        Component.translatable("config.armordamagetint.intensity"),
                        config.intensity, 0, 100)
                .setDefaultValue(25)
                .setTooltip(Component.translatable("config.armordamagetint.intensity.tooltip"))
                .setSaveConsumer(value -> config.intensity = value)
                .setTextGetter(value -> Component.literal(value + "%"))
                .build());

        // Custom Color category
        ConfigCategory colorCategory = builder.getOrCreateCategory(Component.translatable("config.armordamagetint.category.color"));

        // Red slider
        colorCategory.addEntry(entryBuilder.startIntSlider(
                        Component.translatable("config.armordamagetint.red"),
                        config.red, 0, 255)
                .setDefaultValue(255)
                .setTooltip(Component.translatable("config.armordamagetint.color.tooltip"))
                .setSaveConsumer(value -> {
                    if (value != config.red) {
                        config.red = value;
                        config.preset = "Custom";
                    }
                })
                .setTextGetter(value -> Component.literal(String.valueOf(value)))
                .build());

        // Green slider
        colorCategory.addEntry(entryBuilder.startIntSlider(
                        Component.translatable("config.armordamagetint.green"),
                        config.green, 0, 255)
                .setDefaultValue(0)
                .setTooltip(Component.translatable("config.armordamagetint.color.tooltip"))
                .setSaveConsumer(value -> {
                    if (value != config.green) {
                        config.green = value;
                        config.preset = "Custom";
                    }
                })
                .setTextGetter(value -> Component.literal(String.valueOf(value)))
                .build());

        // Blue slider
        colorCategory.addEntry(entryBuilder.startIntSlider(
                        Component.translatable("config.armordamagetint.blue"),
                        config.blue, 0, 255)
                .setDefaultValue(0)
                .setTooltip(Component.translatable("config.armordamagetint.color.tooltip"))
                .setSaveConsumer(value -> {
                    if (value != config.blue) {
                        config.blue = value;
                        config.preset = "Custom";
                    }
                })
                .setTextGetter(value -> Component.literal(String.valueOf(value)))
                .build());

        return builder.build();
    }
}
