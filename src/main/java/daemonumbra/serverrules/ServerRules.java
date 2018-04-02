package daemonumbra.serverrules;

import daemonumbra.serverrules.commands.Rules;
import daemonumbra.serverrules.config.ServerRulesSettings;
import daemonumbra.serverrules.eventhandlers.ServerEvents;
import daemonumbra.serverrules.eventhandlers.WorldEvents;
import daemonumbra.serverrules.util.PlayerLocker;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.event.RegistryEvent;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * The main mod class for ServerRules Mod
 */
@Mod(
        modid = ServerRules.MOD_ID,
        name = ServerRules.MOD_NAME,
        version = ServerRules.VERSION,
        acceptedMinecraftVersions = ServerRules.MC_VERSIONS,
        acceptableRemoteVersions = "*",
        updateJSON = ServerRules.UPDATE_URL,
        serverSideOnly = true
)
public class ServerRules {

    public static final String MOD_ID = "serverrules";
    public static final String MOD_NAME = "ServerRules";
    public static final String VERSION = "1.12.2-1.0.0.0";
    public static final String MC_VERSIONS = "[1.12,1.13)";
    public static final String UPDATE_URL = "https://raw.githubusercontent.com/DaemonUmbra/Server-Rules/master/Versions.json";
    public static final String NBT_TAG = "ServerRules.HasAcceptedRules";

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static ServerRules INSTANCE;

    public static Logger logger;
    public static PlayerLocker locker;

    public static ServerEvents serverEvents;
    public static WorldEvents worldEvents;

    public ServerRulesSettings serverRulesSettings;


    public ServerRules() {
        logger = LogManager.getLogger(MOD_NAME);
        logger.log(Level.INFO,"Acquired Logger");
        locker = new PlayerLocker();
    }

    /**
     * Fires when the Server is started
     * @param event
     */
    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        ServerRules.logger.log(Level.INFO,"Registering Commands");
        event.registerServerCommand(new Rules());
        ServerRules.logger.log(Level.INFO,"Commands Registered");
    }

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger.log(Level.DEBUG,"PreInit");
        logger.log(Level.INFO, "Instantiating EventHandlers");
        serverEvents = new ServerEvents();
        worldEvents = new WorldEvents();
        logger.log(Level.INFO, "EventHandlers Instantiated");
        logger.log(Level.INFO, "Loading Config");
        serverRulesSettings = new ServerRulesSettings();
        logger.log(Level.INFO,"Config Loaded");
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        logger.log(Level.DEBUG,"Init");
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        logger.log(Level.DEBUG, "PostInit");
    }

    /**
     * Called when the Disable button is clicked in the mod listing
     * @param event
     */
    @Mod.EventHandler
    public void onDeactivate(FMLModDisabledEvent event){
        throw new NotImplementedException("");
        //ServerRules.logger.log(Level.INFO,"Deactivating " + ServerRules.MOD_NAME);
    }
}
