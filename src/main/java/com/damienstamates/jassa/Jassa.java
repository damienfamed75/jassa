package com.damienstamates.jassa;

import com.damienstamates.jassa.armor.JassaMaterial;
import com.damienstamates.jassa.item.SlimeSlingshot;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Jassa implements ModInitializer {
	// Constants
	public static final String MOD_ID = "jassa";
	public static final ItemGroup JASSA_GROUP = FabricItemGroupBuilder.build(
		new Identifier(MOD_ID, "general"),
		() -> new ItemStack(Blocks.COBBLESTONE));
	
	// Tools
	public static final Item SLIME_SLINGSHOT = new SlimeSlingshot(
		new Item.Settings().group(Jassa.JASSA_GROUP).maxCount(1));
	
	// Armor
	public static final Item SLIME_HAT = new ArmorItem(JassaMaterial.SLIME,
		EquipmentSlot.HEAD, (new Item.Settings().group(Jassa.JASSA_GROUP)));
	public static final Item SLIME_SHIRT = new ArmorItem(JassaMaterial.SLIME,
		EquipmentSlot.CHEST, (new Item.Settings().group(Jassa.JASSA_GROUP)));
	public static final Item SLIME_PANTS = new ArmorItem(JassaMaterial.SLIME,
		EquipmentSlot.LEGS, (new Item.Settings().group(Jassa.JASSA_GROUP)));
	public static final Item SLIME_BOOTS = new ArmorItem(JassaMaterial.SLIME,
		EquipmentSlot.FEET, (new Item.Settings().group(Jassa.JASSA_GROUP)));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		initializeItems();
		initializeArmor();

		System.out.println("Hello from Jassa!");
	}

	private void initializeItems() {
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "slime_slingshot"), SLIME_SLINGSHOT);
	}

	private void initializeArmor() {
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "slime_hat"), SLIME_HAT);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "slime_shirt"), SLIME_SHIRT);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "slime_pants"), SLIME_PANTS);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "slime_boots"), SLIME_BOOTS);
	}

	public static boolean isSlimeArmor(ItemStack stack) {
		Item item = stack.getItem();
		return item instanceof ArmorItem && ((ArmorItem) item).getMaterial() instanceof JassaMaterial;
	}
}
