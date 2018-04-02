package daemonumbra.serverrules.config;

import daemonumbra.serverrules.ServerRules;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * The Settings class for ServerRules Mod
 */
@Mod.EventBusSubscriber(modid = ServerRules.MOD_ID)
@Config(modid = ServerRules.MOD_ID,type = Config.Type.INSTANCE,name = "ServerRules")
public class ServerRulesSettings {

    @Config.Name("Rules Message")
    @Config.Comment("This is the message that will be shown to people joining your server for the first time and when the player types /rules.")
    @Config.LangKey("RulesMessage")
    public static String[] RulesMessage = {
            "This is the Default ServerRules Rules List.",
            "You can customize this message in the Server Config.",
            "Type \"/rules accept\" to accept these rules and begin playing."
    };

    @Config.Name("Reject Message")
    @Config.Comment("This is the message shown to people who reject the rules, as they are kicked.")
    @Config.LangKey("RejectMessage")
    public static String RejectMessage = "You must accept the rules to play on this server.";

    @Config.Name("Accept Message")
    @Config.Comment("This is the message shown to people who reject the rules, as they are kicked.")
    @Config.LangKey("AcceptMessage")
    public static String AcceptMessage = "Have Fun!";

    @Config.Name("Max Wiggle Room")
    @Config.Comment("This is how far a player can get from their initial spawn without accepting the rules.")
    @Config.LangKey("WiggleRoom")
    public static double MaxDist = 1D;

    @Config.Name("Player Can Reject Rules After Accepting")
    @Config.Comment("Sometimes people have second thoughts, this decides if you want to let people go back on their word. (Useful for my own testing)")
    @Config.LangKey("CanRejectAfterAccepting")
    public static boolean PlayerCanRejectAfterAccepting;

    @Config.Name("Verbose Logging")
    @Config.Comment("Enables more log output at the expense of more log spam.")
    @Config.LangKey("VerboseLog")
    public static boolean VerboseLog;

    @SubscribeEvent
    public static void OnConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
        //Shut up Intellij, I like using == for everything, I'm not going to use .equals() for one type just because you're complaining
        if(event.getModID() == ServerRules.MOD_ID) {
            ConfigManager.sync(ServerRules.MOD_ID, Config.Type.INSTANCE);
        }
    }

}
