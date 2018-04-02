package daemonumbra.serverrules.eventhandlers;

import daemonumbra.serverrules.ServerRules;
import daemonumbra.serverrules.config.ServerRulesSettings;
import daemonumbra.serverrules.util.PlayerLocker;
import daemonumbra.serverrules.util.ServerRulesUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.Level;

/**
 * Handles events that happen on the Server, but not in a World
 */
@Mod.EventBusSubscriber(modid = ServerRules.MOD_ID)
public class ServerEvents {

    @SubscribeEvent
    public static void OnPlayerLogin(PlayerEvent.PlayerLoggedInEvent event){
        //Ensure HasAcceptedRules Exists
        if(!event.player.getEntityData().hasKey(ServerRules.NBT_TAG)){
            event.player.getEntityData().setBoolean(ServerRules.NBT_TAG,false);
        }
        Boolean AcceptedRules = event.player.getEntityData().getBoolean(ServerRules.NBT_TAG);
        if(!AcceptedRules){
            PlayerLocker.LockPlayer(event.player);
            ServerRulesUtil.PrintRules(event.player);
        }
    }

    @SubscribeEvent
    public static void OnPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event){
        try {
            if (PlayerLocker.isPlayerLocked(event.player)) {
                PlayerLocker.UnlockPlayer(event.player);
            }
        }catch(Exception ex){
            return;
        }
    }

    @SubscribeEvent
    public static void OnPlayerTick(TickEvent.PlayerTickEvent event) {
        if (PlayerLocker.isPlayerLocked(event.player)) {
            Integer lockedPlayerIndex = PlayerLocker.getPlayerIndex(event.player);
            if (lockedPlayerIndex != null) {
                PlayerLocker.LockedPlayer lockedInstance = PlayerLocker.lockedPlayers.get(lockedPlayerIndex);
                if (lockedInstance != null) {
                    float Distance = ServerRulesUtil.DistanceBetween(ServerRulesUtil.Vector3fFromDoubles(event.player.posX, event.player.posY, event.player.posZ), lockedInstance.position);
                    if (Distance > ServerRulesSettings.MaxDist) {
                        if(ServerRulesSettings.VerboseLog) {
                            ServerRules.logger.log(Level.INFO, "Resetting Player " + event.player.getName() + " at Location: " + ServerRulesUtil.Vector3fFromDoubles(event.player.posX, event.player.posY, event.player.posZ) + " to Location: " + lockedInstance.position + ", Distance is: " + Distance);
                        }
                        ((EntityPlayerMP)event.player).connection.setPlayerLocation(lockedInstance.position.x,lockedInstance.position.y,lockedInstance.position.z,event.player.rotationYaw,event.player.rotationPitch);
                    }
                }
            }
        }
    }
}
