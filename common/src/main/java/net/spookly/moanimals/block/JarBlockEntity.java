package net.spookly.moanimals.block;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.ticks.ContainerSingleItem;

public class JarBlockEntity extends BlockEntity implements RandomizableContainer, ContainerSingleItem.BlockContainerSingleItem {

    public static final String TAG_ITEM = "item";

    private ItemStack item = ItemStack.EMPTY;
    @Nullable protected ResourceKey<LootTable> lootTable;
    protected long lootTableSeed;

    public JarBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MoAnimalsBlockEntityTypes.JAR.get(), blockPos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        if (!this.trySaveLootTable(compoundTag) && !this.item.isEmpty()) {
            compoundTag.put("item", this.item.save(provider));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        if (!this.tryLoadLootTable(compoundTag)) {
            if (compoundTag.contains("item", 10)) {
                this.item = (ItemStack)ItemStack.parse(provider, compoundTag.getCompound("item")).orElse(ItemStack.EMPTY);
            } else {
                this.item = ItemStack.EMPTY;
            }
        }
    }

    @Nullable @Override
    public ResourceKey<LootTable> getLootTable() {
        return this.lootTable;
    }

    @Override
    public void setLootTable(@Nullable ResourceKey<LootTable> resourceKey) {
        this.lootTable = resourceKey;
    }

    @Override
    public long getLootTableSeed() {
        return this.lootTableSeed;
    }

    @Override
    public void setLootTableSeed(long l) {
        this.lootTableSeed = l;
    }

    @Override
    public @NotNull BlockEntity getContainerBlockEntity() {
        return this;
    }

    @Override
    public @NotNull ItemStack getTheItem() {
        this.unpackLootTable(null);
        return this.item;
    }

    @Override
    public void setTheItem(ItemStack itemStack) {
        this.unpackLootTable(null);
        this.item = itemStack;
    }
}
