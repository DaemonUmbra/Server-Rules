package daemonumbra.serverrules.commands;

import daemonumbra.serverrules.ServerRules;
import daemonumbra.serverrules.config.ServerRulesSettings;
import daemonumbra.serverrules.util.AlphaComparer;
import daemonumbra.serverrules.util.PlayerLocker;
import daemonumbra.serverrules.util.ServerRulesUtil;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Rules command
 */
public class Rules implements ICommand {

    List<String> aliases;
    List<String> tab;

    public Rules(){
        aliases = new ArrayList<>();
        aliases.add("rules");
        tab = new ArrayList<>();
        tab.add("");
        tab.add("accept");
        tab.add("reject");
    }

    /**
     * Gets the name of the command
     */
    @Override
    public String getName() {
        return "Rules";
    }

    /**
     * Gets the usage string for the command.
     *
     * @param sender
     */
    @Override
    public String getUsage(ICommandSender sender) {
        return "/rules <accept/reject>";
    }

    /**
     * Get a list of aliases for this command. <b>Never return null!</b>
     */
    @Override
    public List<String> getAliases() {
        return aliases;
    }

    /**
     * Callback for when the command is executed
     *
     * @param server
     * @param sender
     * @param args
     */
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 1){
            EntityPlayer player = server.getPlayerList().getPlayerByUsername(sender.getName());
            switch(args[0].toLowerCase()){
                case "accept":{
                    if(player != null) {
                        player.getEntityData().setBoolean(ServerRules.NBT_TAG, true);
                        player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(ServerRulesUtil.DEFAULT_PLAYER_SPEED);
                        EntityPlayer commandPlayer = sender.getEntityWorld().getPlayerEntityByName(sender.getName());
                        if(commandPlayer != null) {
                            PlayerLocker.UnlockPlayer(commandPlayer);
                            player.sendMessage(new TextComponentString(ServerRulesSettings.AcceptMessage));
                        }
                    }
                    break;
                }
                case "reject":{
                    if(player != null) {
                        if(ServerRulesSettings.PlayerCanRejectAfterAccepting) {
                            player.getEntityData().setBoolean(ServerRules.NBT_TAG, false);
                            ((EntityPlayerMP) player).connection.disconnect(new TextComponentString(ServerRulesSettings.RejectMessage));
                        }
                    }
                    break;
                }
                default:{
                    ServerRulesUtil.PrintRules(sender);
                    break;
                }
            }
        }
        else {
            ServerRulesUtil.PrintRules(sender);
        }
    }

    /**
     * Check if the given ICommandSender has permission to execute this command
     *
     * @param server
     * @param sender
     */
    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    /**
     * Get a list of options for when the user presses the TAB key
     *
     * @param server
     * @param sender
     * @param args
     * @param targetPos
     */
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return tab;
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     *
     * @param args
     * @param index
     */
    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(ICommand o) {
            return AlphaComparer.Compare(getName(), o.getName());
    }
}
