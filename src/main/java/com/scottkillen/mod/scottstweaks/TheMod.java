package com.scottkillen.mod.scottstweaks;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.scottkillen.mod.koresample.compat.Integrates;
import com.scottkillen.mod.koresample.config.ConfigEventHandler;
import com.scottkillen.mod.scottstweaks.config.Settings;
import com.scottkillen.mod.scottstweaks.tweaks.chicken.ChickenPlucker;
import com.scottkillen.mod.scottstweaks.tweaks.endermen.EnderDumper;
import com.scottkillen.mod.scottstweaks.tweaks.planting.Planter;
import com.scottkillen.mod.scottstweaks.tweaks.spawncontrol.SpawnGovernor;
import com.scottkillen.mod.scottstweaks.tweaks.worldgen.ClayGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import java.util.List;

@SuppressWarnings({
        "StaticNonFinalField", "WeakerAccess", "StaticVariableMayNotBeInitialized", "NonConstantFieldWithUpperCaseName"
})
@Mod(modid = TheMod.MOD_ID, name = TheMod.MOD_NAME, version = TheMod.MOD_VERSION, useMetadata = true, guiFactory = TheMod.MOD_GUI_FACTORY)
public class TheMod
{
    static final String MOD_GUI_FACTORY = "com.scottkillen.mod.scottstweaks.config.client.ModGuiFactory";
    public static final String MOD_ID = "scottstweaks";
    static final String MOD_NAME = "Scott's Tweaks";
    static final String MOD_VERSION = "${mod_version}";
    @Instance(MOD_ID)
    public static TheMod INSTANCE;
    private final List<Integrates> integrators = Lists.newArrayList();
    private Optional<ConfigEventHandler> configEventHandler = Optional.absent();

    public Configuration configuration()
    {
        if (configEventHandler.isPresent()) return configEventHandler.get().configuration();
        return new Configuration();
    }

    @EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event)
    {
        configEventHandler = Optional.of(
                new ConfigEventHandler(MOD_ID, event.getSuggestedConfigurationFile(), Settings.INSTANCE,
                        Settings.CONFIG_VERSION));
        configEventHandler.get().activate();
    }

    @SuppressWarnings("MethodMayBeStatic")
    @EventHandler
    public void onFMLInitialization(FMLInitializationEvent unused)
    {
        new ChickenPlucker().listen(MinecraftForge.EVENT_BUS);
        new Planter().listen(MinecraftForge.EVENT_BUS);
        new ClayGenerator().install();
        new SpawnGovernor().listen(MinecraftForge.EVENT_BUS);
        new EnderDumper().listen(MinecraftForge.EVENT_BUS);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("integrators", integrators)
                .add("configEventHandler", configEventHandler).toString();
    }
}
