package me.drkmatr1984.Trails;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TrailsCommandHandler implements CommandExecutor
{
	private Trails plugin;
	
    public TrailsCommandHandler(Trails plugin){
    	this.plugin = plugin; 
    }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		try{
			if (cmd.getName().equalsIgnoreCase("trails")) {
				if(sender instanceof Player) {
					if ((args.length == 0) || (args.equals(null)))
					{
						//toggle  trails for player
					}
					if(args.length > 0){
						if(args[0].equalsIgnoreCase("reload")) {
							//reload the configs and shiz
						}
						if(args[0].equalsIgnoreCase("on")){
							//manually turn trails on for the player, but checking if their in the list, and if not, adding them.
						}
						if(args[0].equalsIgnoreCase("toggle")) {
							
						}
					}
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
