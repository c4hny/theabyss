package net.yezon.theabyss.events;

import net.yezon.theabyss.network.TheabyssModVariables;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.TextComponent;

public class ManaDurabilityUpgradeResultEvent {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if ((entity.getCapability(TheabyssModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new TheabyssModVariables.PlayerVariables())).ManaDamage > 4.5) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(new TextComponent("that had no \u00A7beffect\u00A7f .."), (true));
		} else {
			{
				double _setval = (entity.getCapability(TheabyssModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TheabyssModVariables.PlayerVariables())).ManaDamage + 1;
				entity.getCapability(TheabyssModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.ManaDamage = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}
