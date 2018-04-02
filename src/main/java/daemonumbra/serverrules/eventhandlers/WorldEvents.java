package daemonumbra.serverrules.eventhandlers;

import daemonumbra.serverrules.ServerRules;
import daemonumbra.serverrules.util.PlayerLocker;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Handles events that occur in the World
 */
@Mod.EventBusSubscriber(modid = ServerRules.MOD_ID)
public class WorldEvents {

    @SubscribeEvent
    public static void OnBlockBreak(BlockEvent.BreakEvent event){
        if(PlayerLocker.getPlayerIndex(event.getPlayer()) != null){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static  void OnItemPickup(EntityItemPickupEvent event){
        if(PlayerLocker.getPlayerIndex(event.getEntityPlayer()) != null){
            event.setCanceled(true);
        }
    }
}
