package daemonumbra.serverrules.util;

import daemonumbra.serverrules.ServerRules;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Player Locking Utility for ServerRules Mod
 */
public class PlayerLocker {

    public static List<LockedPlayer> lockedPlayers = new ArrayList<>();
    public static final AttributeModifier LOCKED_PLAYER_ATTRIBUTE_MOD = new AttributeModifier("LockedPlayerAttributeModifier",-ServerRulesUtil.DEFAULT_PLAYER_SPEED,0);


//    public static void LockPlayers(){
//        for (LockedPlayer lockedPlayer: lockedPlayers) {
//            EntityPlayer player = lockedPlayer.player;
//            Vector3f position = lockedPlayer.position;
//            player.world = lockedPlayer.world;
//            float distance = ServerRulesUtil.DistanceBetween(position, ServerRulesUtil.Vector3fFromDoubles(player.posX,player.posY,player.posZ));
//            if(distance > ServerRules.INSTANCE.serverRulesSettings.MaxDist) {
//                ServerRules.logger.log(Level.INFO, "Resetting position of " + player.getName() + " to " + position);
//                player.setPosition(position.getX(), position.getY(), position.getZ());
//                ((EntityPlayerMP)player).connection.setPlayerLocation(player.posX,player.posY,player.posZ,player.cameraYaw,player.cameraPitch);
//            }
//        }
//    }

    public static Integer getPlayerIndex(PlayerLocker.LockedPlayer player){
        for(int i = 0;i<lockedPlayers.toArray().length;i++){
            if(lockedPlayers.get(i) == player){
                return i;
            }
        }
        return null;
    }

    public static List<EntityPlayer> GetLockedPlayers(){
        List<EntityPlayer> output = new ArrayList<EntityPlayer>();
        for (LockedPlayer lockedPlayer: lockedPlayers) {
            output.add(lockedPlayer.player);
        }
        return output;
    }

    public static Integer getPlayerIndex(EntityPlayer player){
        for(int i = 0;i<lockedPlayers.toArray().length;i++){
            if(lockedPlayers.get(i).player == player){
                return i;
            }
        }
        return null;
    }

    /**
     * Locks a player in place, preventing the player from interacting with the environment in any way
     * @param player
     */
    public static void LockPlayer(EntityPlayer player){
        player.capabilities.allowEdit = false;
        player.capabilities.disableDamage = true;
        if(!player.getAttributeMap().getAttributeInstanceByName("generic.movementSpeed").hasModifier(LOCKED_PLAYER_ATTRIBUTE_MOD)) {
            player.getAttributeMap().getAttributeInstanceByName("generic.movementSpeed").applyModifier(LOCKED_PLAYER_ATTRIBUTE_MOD);
        }

        //HACK: Intellij thinks cast is extraneous if I don't do it this way
        EntityPlayerMP forcedMPPlayer = (EntityPlayerMP)player;
        forcedMPPlayer.sendPlayerAbilities();
        lockedPlayers.add(new LockedPlayer(player));
    }

    /**
     * Unlocks a player, resotring their ability to move, break and place blocks, and pick up items.
     * @param player
     */
    public static void UnlockPlayer(EntityPlayer player){
        player.capabilities.allowEdit = true;
        player.capabilities.disableDamage = false;
        if(player.getAttributeMap().getAttributeInstanceByName("generic.movementSpeed").hasModifier(LOCKED_PLAYER_ATTRIBUTE_MOD)) {
            player.getAttributeMap().getAttributeInstanceByName("generic.movementSpeed").removeModifier(LOCKED_PLAYER_ATTRIBUTE_MOD);
        }

        //HACK: Intellij thinks cast is extraneous if I don't do it this way
        EntityPlayerMP forcedMPPlayer = (EntityPlayerMP)player;
        forcedMPPlayer.sendPlayerAbilities();
        lockedPlayers.remove(lockedPlayers.get(getPlayerIndex(player)));

    }

    public static boolean isPlayerLocked(EntityPlayer player) {
        return GetLockedPlayers().contains(player);
    }

    public static class LockedPlayer {
        public EntityPlayer player;
        public World world;
        public Vector3f position;

        public LockedPlayer(EntityPlayer player, World world, Vector3f position) {
            this.player = player;
            this.world = world;
            this.position = position;
        }

        public LockedPlayer(EntityPlayer player){
            this.player = player;
            this.world = this.player.world;
            this.position = new Vector3f((float)player.posX,(float)player.posY,(float)player.posZ);
        }
    }
}
