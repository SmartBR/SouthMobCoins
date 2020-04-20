package net.smart.mobcoins.command;

import net.smart.mobcoins.Main;
import net.smart.mobcoins.database.object.PlayerCoins;
import net.smart.mobcoins.shop.ShopInventoryBuilder;
import net.smart.mobcoins.shop.object.ShopInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class MobCoinsCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mobcoins")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§c[SouthMobCoins] §fComando apenas para jogadores.");
                return true;
            }
            Player p = (Player) sender;
            DecimalFormat df = new DecimalFormat();
            PlayerCoins coins = PlayerCoins.getPlayer(p.getName());

            if (args.length == 0) {
                p.sendMessage("");
                p.sendMessage("§6§l[MOB COINS] §fSeus mob coins: §6" + df.format(coins.getCoins()));
                p.sendMessage("");
                return true;
            }
            if (args.length == 1) {
                switch (args[0]) {
                    case "top":
                        p.sendMessage(" §6§lTOP MOB COINS §f(Atualiza a cada 5m)");
                        Bukkit.broadcastMessage("§aTop Coins size: " + Main.getInstance().topCoins.size());
                        if (Main.getInstance().topCoins.size() >= 9) {
                            for (int i = 0; i < 10; i++) {
                                PlayerCoins top = Main.getInstance().topCoins.get(i);
                                p.sendMessage("§a" + (i + 1) + "º §7" + top.getPlayer() + " §f- Mob Coins: §6" + df.format(top.getCoins()));
                            }
                        } else {
                            for (int i = 0; i < Main.getInstance().topCoins.size(); i++) {
                                PlayerCoins top = Main.getInstance().topCoins.get(i);
                                p.sendMessage("§a" + (i + 1) + "º §7" + top.getPlayer() + " §f- Mob Coins: §6" + df.format(top.getCoins()));
                            }
                        }
                        break;
                    case "shop":
                        ShopInventory inv = Main.getInstance().getShopInventory();
                        if (inv.isEnable()) {
                            new ShopInventoryBuilder().open(p);
                        } else {
                            p.sendMessage("§cEste sistema está desativado na configuração do plugin.");
                        }
                        break;
                    case "help":
                        getHelp(p, label);
                    default:
                        //TARGET PLAYER
                        String t = args[0];
                        PlayerCoins tCoins = PlayerCoins.getPlayer(t);
                        if (tCoins != null) {
                            p.sendMessage("");
                            p.sendMessage("§6§l[MOB COINS] §fMob coins de §6" + t + "§f: §6" + df.format(tCoins.getCoins()));
                            p.sendMessage("");
                        } else {
                            p.sendMessage("§cJogador não encontrado em nosso banco de dados.");
                        }
                }
                return true;
            }
            if (args.length == 2) {
                getHelp(p, label);
                return true;
            }
            if (args.length >= 3) {
                switch (args[0].toLowerCase()) {
                    case "enviar":
                        Boolean enviarCMD = Main.getInstance().getConfig().getBoolean("sub-commands.enviar");
                        if (enviarCMD) {
                            Player t = Bukkit.getPlayer(args[1]);
                            if (t != null) {
                                if (isNumber(args[2])) {
                                    Integer sendCoins = Integer.valueOf(args[2]);
                                    if (coins.getCoins() >= sendCoins) {
                                        coins.removeCoins(sendCoins);
                                        PlayerCoins.getPlayer(t.getName()).addCoins(sendCoins);
                                        p.sendMessage("§aVocê enviou §6" + df.format(sendCoins) + " mob coins §apara o jogador §6" + t.getName() + "§a.");
                                        t.sendMessage("§aVocê recebeu §6" + df.format(sendCoins) + " mob coins §ado jogador §6" + p.getName() + "§a.");
                                    }else {
                                        p.sendMessage("§cVocê não possui " + df.format(sendCoins) + " mob coins para enviar ao jogador " + t.getName() + ".");
                                    }
                                }else {
                                    p.sendMessage("§cDigite um número válido.");
                                }
                            }else {
                                p.sendMessage("§cJogador offline.");
                            }
                        }else {
                            disableCmd(p);
                        }
                        break;
                    case "adicionar":
                        if (p.hasPermission("southdev.mobcoins.admin")) {
                            Boolean addCMD = Main.getInstance().getConfig().getBoolean("sub-commands.adicionar");
                            if (addCMD) {
                                String t = args[1];
                                if (PlayerCoins.getPlayer(t) != null) {
                                    if (isNumber(args[2])) {
                                        Integer sendCoins = Integer.valueOf(args[2]);
                                        PlayerCoins.getPlayer(t).addCoins(sendCoins);
                                        p.sendMessage("§aVocê adicionou §6" + df.format(sendCoins) + " mob coins §apara o jogador §6" + t + "§a.");
                                    }else {
                                        p.sendMessage("§cDigite um número válido.");
                                    }
                                }else {
                                    p.sendMessage("§cJogador não encontrado no banco de dados.");
                                }
                            }else {
                                disableCmd(p);
                            }
                        }else {
                            p.sendMessage("§cVocê não tem permissão para executar este comando.");
                        }
                        break;
                    case "remover":
                        if (p.hasPermission("southdev.mobcoins.admin")) {
                            Boolean remCMD = Main.getInstance().getConfig().getBoolean("sub-commands.remover");
                            if (remCMD) {
                                String t = args[1];
                                if (PlayerCoins.getPlayer(t) != null) {
                                    if (isNumber(args[2])) {
                                        Integer sendCoins = Integer.valueOf(args[2]);
                                        PlayerCoins.getPlayer(t).removeCoins(sendCoins);
                                        p.sendMessage("§aVocê removeu §c" + df.format(sendCoins) + " mob coins §ado jogador §c" + t + "§a.");
                                    }else {
                                        p.sendMessage("§cDigite um número válido.");
                                    }
                                }else {
                                    p.sendMessage("§cJogador não encontrado no banco de dados.");
                                }
                            }else {
                                disableCmd(p);
                            }
                        }else {
                            p.sendMessage("§cVocê não tem permissão para executar este comando.");
                        }
                        break;
                    case "setar":
                        if (p.hasPermission("southdev.mobcoins.admin")) {
                            Boolean setCMD = Main.getInstance().getConfig().getBoolean("sub-commands.setar");
                            if (setCMD) {
                                String t = args[1];
                                if (PlayerCoins.getPlayer(t) != null) {
                                    if (isNumber(args[2])) {
                                        Integer sendCoins = Integer.valueOf(args[2]);
                                        PlayerCoins.getPlayer(t).setCoins(sendCoins);
                                        p.sendMessage("§aVocê setou os mob coins do jogador §c" + t + " §apara §c" + df.format(sendCoins) + "§a.");
                                    } else {
                                        p.sendMessage("§cDigite um número válido.");
                                    }
                                } else {
                                    p.sendMessage("§cJogador não encontrado no banco de dados.");
                                }
                            } else {
                                disableCmd(p);
                            }
                        } else {
                            p.sendMessage("§cVocê não tem permissão para executar este comando.");
                        }
                        break;
                    default:
                        getHelp(p, label);
                }
            }
        }
        return true;
    }
    private Boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
    }
    private void getHelp(Player p, String label){
        p.sendMessage("§3[SouthMobCoins] §fComandos:");
        p.sendMessage("");
        p.sendMessage("§3/" + label + " §f- Ver seus mob coins.");
        p.sendMessage("§3/" + label + " <jogador> §f- Ver mob coins de outro jogador.");
        p.sendMessage("§3/" + label + " enviar <jogador> <quantia> §f- Enviar mob coins para um jogador.");
        p.sendMessage("§3/" + label + " top §f- Ver os top 10 jogadores com mais mob coins.");
        p.sendMessage("§3/" + label + " shop §f- Abrir o menu de shop.");
        if (p.hasPermission("southdev.mobcoins.admin")) {
            p.sendMessage("§c/" + label + " adicionar <jogador> <quantia> §f- Adicionar mob coins para um jogador");
            p.sendMessage("§c/" + label + " remover <jogador> <quantia> §f- Remover mob coins de um jogador");
            p.sendMessage("§c/" + label + " setar <jogador> <quantia> §f- Setar mob coins para um jogador");
        }
    }
    private void disableCmd(Player p) {
        p.sendMessage("§cEste sub-comando foi desativado na configuração do plugin.");
    }
}
