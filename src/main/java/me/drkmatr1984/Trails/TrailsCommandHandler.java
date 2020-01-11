package me.drkmatr1984.Trails;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.drkmatr1984.Trails.TrailsConfigManager.LanguageConfig;
import net.md_5.bungee.api.ChatColor;


public class TrailsCommandHandler implements CommandExecutor
{
	private Trails plugin;
	private LanguageConfig langConfig;
	
	public TrailsCommandHandler(Trails plugin){
		this.plugin = plugin;
		this.langConfig = this.plugin.getConfigManager().getLanguageConfig();
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		try{
			if (cmd.getName().equalsIgnoreCase("trails") || cmd.getName().equalsIgnoreCase("paths")) {
				if(sender.hasPermission("trails.use")) {
					if(sender instanceof Player) {
						Player player = (Player)sender;
						if ((args.length == 0) || (args.equals(null)))
						{
							if(player.hasPermission("trails.toggle.self")) {
								if(this.plugin.isToggled(player)) {
									this.plugin.setToggled(player, false);
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', langConfig.prefix + langConfig.trailsToggledOff));
								}else {
									this.plugin.setToggled(player, true);
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', langConfig.prefix + langConfig.trailsToggledOn));
								}
								return true;
							}
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', langConfig.prefix + langConfig.noPerms));
							return true;
						}
						if(args.length == 1){
							if(args[0].equalsIgnoreCase("on")){
								if(player.hasPermission("trails.toggle.self")) {
									if(!this.plugin.isToggled(player)) {
										this.plugin.setToggled(player, true);
									}
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', langConfig.prefix + langConfig.trailsToggledOn));
									return true;
								}
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', langConfig.prefix + langConfig.noPerms));
								return true;
							}
							if(args[0].equalsIgnoreCase("off")){
								if(player.hasPermission("trails.toggle.self")) {
									if(this.plugin.isToggled(player)) {
										this.plugin.setToggled(player, false);
									}
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', langConfig.prefix + langConfig.trailsToggledOff));
									return true;
								}
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', langConfig.prefix + langConfig.noPerms));
								return true;
							}
							if(args[0].equalsIgnoreCase("toggle")) {
								if(player.hasPermission("trails.toggle.self")) {
									if(this.plugin.isToggled(player)) {
										this.plugin.setToggled(player, false);
										player.sendMessage(ChatColor.translateAlternateColorCodes('&', langConfig.prefix + langConfig.trailsToggledOff));
									}else {
										this.plugin.setToggled(player, true);
										player.sendMessage(ChatColor.translateAlternateColorCodes('&', langConfig.prefix + langConfig.trailsToggledOn));
									}
									return true;
								}
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', langConfig.prefix + langConfig.noPerms));
								return true;
							}
						}
					}
					if(args.length > 0){
						if(args[0].equalsIgnoreCase("reload")) {
							if(sender.hasPermission("trails.reload")) {
								this.plugin.getConfigManager().reloadConfigs();
								sendSenderCorrect(sender, langConfig.prefix + langConfig.reload);
								return true;
							}
							sendSenderCorrect(sender, langConfig.prefix + langConfig.noPerms);
							return true;
						}
						if(args[0].equalsIgnoreCase("toggle")) {
							if(args.length==2) {
								if(sender.hasPermission("trails.toggle.others")) {
									String playerName = args[1];
									UUID uuid = UUIDFetcher.getUUID(playerName);
									if(uuid!=null && Bukkit.getOfflinePlayer(uuid).hasPlayedBefore()) {
										if(this.plugin.isToggled(uuid)) {
											this.plugin.setToggled(uuid, false);
											if(Bukkit.getOfflinePlayer(uuid).isOnline()) {
												Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', langConfig.prefix + langConfig.trailsToggledOff));
											}
											sendSenderCorrect(sender, langConfig.prefix + langConfig.toggledOffOther.replace("%player%", playerName));
										}else {
											this.plugin.setToggled(uuid, true);
											if(Bukkit.getOfflinePlayer(uuid).isOnline()) {
												Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', langConfig.prefix + langConfig.trailsToggledOn));
											}
											sendSenderCorrect(sender, langConfig.prefix + langConfig.toggledOnOther.replace("%player%", playerName));
										}
										return true;
									}else {
									    sendSenderCorrect(sender, langConfig.prefix + langConfig.cannotFindPlayer.replace("%player%", playerName));
										return true;
									}	
								}
								sendSenderCorrect(sender, langConfig.prefix + langConfig.noPerms);
								return true;
							}
						}
					}
					sendSenderCorrect(sender, langConfig.prefix + langConfig.unknownCommand);
					return true;
				}
				sendSenderCorrect(sender, langConfig.prefix + langConfig.noPerms);
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void sendSenderCorrect(CommandSender sender, String message) {
		if(sender instanceof Player) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
		}else {
			sender.sendMessage(ChatColor.stripColor(message));
		}
	}
}
