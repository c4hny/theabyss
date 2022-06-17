
package net.yezon.theabyss.item;

import net.yezon.theabyss.events.AbyssTotemEvent;
import net.yezon.theabyss.init.TheabyssModTabs;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.Component;

import java.util.List;

public class TotemOfAbyssItem extends Item {
	public TotemOfAbyssItem() {
		super(new Item.Properties().tab(TheabyssModTabs.TAB_THE_ABYSS).durability(100).rarity(Rarity.EPIC));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.EAT;
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 0;
	}

	@Override
	public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
		return 0F;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new TextComponent("\u00A7a+\u00A7f Activates when below 3 hearts"));
		list.add(new TextComponent("\u00A7c-\u00A7f don\u2019t work on creeper explosions"));
		list.add(new TextComponent("\u00A7c-\u00A7f don\u2019t work by heavy damage"));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected)
			AbyssTotemEvent.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
		AbyssTotemEvent.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
	}
}
