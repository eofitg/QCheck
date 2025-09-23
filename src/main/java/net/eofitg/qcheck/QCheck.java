package net.eofitg.qcheck;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.eofitg.qcheck.command.QCheckCommand;
import net.eofitg.qcheck.config.QCheckConfig;
import net.eofitg.qcheck.listener.DropListener;
import net.eofitg.qcheck.util.Reference;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;

@Mod(
        modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        version = Reference.MOD_VERSION,
        acceptedMinecraftVersions = "[1.8.9]",
        clientSideOnly = true
)
public class QCheck {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static File configFile = null;
    public static QCheckConfig config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        configFile = new File(e.getModConfigurationDirectory(), "qcheck.json");
        loadConfig();
        Runtime.getRuntime().addShutdownHook(new Thread(QCheck::saveConfig));
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        registerListeners(new DropListener());
        registerCommands(new QCheckCommand());
    }

    private void registerListeners(Object... listeners) {
        Arrays.stream(listeners).forEachOrdered(MinecraftForge.EVENT_BUS::register);
    }

    private void registerCommands(ICommand... commands) {
        Arrays.stream(commands).forEachOrdered(ClientCommandHandler.instance::registerCommand);
    }

    public static void loadConfig() {
        if (configFile == null) return;
        if (configFile.exists()) {
            try {
                String json = FileUtils.readFileToString(configFile);
                config = gson.fromJson(json, QCheckConfig.class);
            } catch (Exception ignored) {}
        } else {
            config = new QCheckConfig();
            saveConfig();
        }
    }

    public static void saveConfig() {
        if (configFile == null) return;
        try {
            String json = gson.toJson(config);
            FileUtils.write(configFile, json);
        } catch (Exception ignored) {}
    }

}
