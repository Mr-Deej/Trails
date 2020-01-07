package me.drkmatr1984.Trails;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TrailsCommandHandler implements CommandExecutor
{
	public TrailsCommandHandler(){ 
    }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		try{
			if (cmd.getName().equalsIgnoreCase("trails") || cmd.getName().equalsIgnoreCase("paths")) {
				if(sender instanceof Player) {
					if ((args.length == 0) || (args.equals(null)))
					{
						//toggle  trails for player
						return true;
					}
					if(args.length > 0){
						if(args[0].equalsIgnoreCase("reload")) {
							//reload the configs and shiz
							return true;
						}
						if(args[0].equalsIgnoreCase("on")){
							//manually turn trails on for the player, by checking if their in the list, and if not, adding them.
							return true;
						}
						if(args[0].equalsIgnoreCase("off")){
							//manually turn trails ff for the player, by checking if their in the list, and if they are, removing them.
							return true;
						}
						if(args[0].equalsIgnoreCase("toggle")) {
							if(args.length>1) {
								//toggle trails for a <player> named here if they have the perm
								return true;
							}
							//toggle trails for the player
							return true;
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
