package fr.azzizcouscous.benjaminloison.main;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.base.Throwables;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.azzizcouscous.benjaminloison.api.Consequence;
import fr.azzizcouscous.benjaminloison.api.FileAPI;
import fr.azzizcouscous.benjaminloison.api.Language;
import fr.azzizcouscous.benjaminloison.skywar.SkyWarManager;

public class Main extends JavaPlugin
{
    public static final String MODID = "azzizcouscous", NAME = "AzzizCouscous";
    public static Main plugin;
    public static EventController eventController;

    @Override
    public void onEnable()
    {
        plugin = this;
        if(getWorldEdit() == null)
        {
            fatal("WORLDEDIT PLUGIN IS REQUIRED");
            plugin.setEnabled(false);
            return;
        }
        FileAPI.initialize();
        Language.initialize();
        SkyWarManager.initialize();
        eventController = new EventController();
        info(Language.translate("Launched !"));
    }

    @Override
    public void onDisable()
    {
        info(Language.translate("Disabled !"));
    }
    
    public WorldEditPlugin getWorldEdit()
    {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");
        if(plugin == null || !(plugin instanceof WorldEditPlugin))
            return null;
        return (WorldEditPlugin)plugin;
    }

    private static void print(Object object, Consequence consequence)
    {
        Logger logger = plugin.getLogger();
        String toPrint = object.toString();
        switch(consequence)
        {
            case Info:
                logger.info(toPrint);
                break;
            case Warn:
                logger.warning(toPrint);
                break;
            case Fatal:
                logger.severe("/!\\ " + toPrint + " /!\\");
                break;
        }
    }
    
    public static void info(Object object)
    {
        print(object, Consequence.Info);
    }
    
    public static void warn(Object object)
    {
        print(object, Consequence.Warn);
    }
    
    public static void fatal(Object object)
    {
        print(object, Consequence.Fatal);
    }
}
