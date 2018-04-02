package daemonumbra.serverrules.util;

import daemonumbra.serverrules.ServerRules;
import daemonumbra.serverrules.config.ServerRulesSettings;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

/**
 * Misc Utility Class for ServerRules Mod
 */
public class ServerRulesUtil {
    public static void PrintRules(ICommandSender sender) {
        for (String s:ServerRulesSettings.RulesMessage) {
            sender.sendMessage(new TextComponentString(s));
        }
    }

    public static float DistanceBetween(Vector3f Pos1, Vector3f Pos2){
        float output = (float)Math.sqrt(
                Math.pow(Pos1.x - Pos2.x,2) +
                Math.pow(Pos1.y - Pos2.y,2) +
                Math.pow(Pos1.z - Pos2.z,2)
        );
        return output;
    }

    public static Vector3f Vector3fFromDoubles(double posX, double posY, double posZ) {
        return new Vector3f((float)posX,(float)posY,(float)posZ);
    }

    public static final double DEFAULT_PLAYER_SPEED = 0.10000000149011612D;
}
