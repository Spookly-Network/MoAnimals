package net.spookly.moanimals.neoforge.datagen;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import dev.architectury.registry.registries.RegistrySupplier;

import net.spookly.moanimals.block.MoAnimalBlocks;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;

import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(MoAnimalBlocks.DUCKWEED);

//        blockWithItem(ModBlocks.BISMUTH_ORE);
//        blockWithItem(ModBlocks.BISMUTH_DEEPSLATE_ORE);
//
//        blockWithItem(ModBlocks.MAGIC_BLOCK);
//
//        stairsBlock(ModBlocks.BISMUTH_STAIRS.get(), blockTexture(ModBlocks.BISMUTH_BLOCK.get()));
//        slabBlock(ModBlocks.BISMUTH_SLAB.get(), blockTexture(ModBlocks.BISMUTH_BLOCK.get()), blockTexture(ModBlocks.BISMUTH_BLOCK.get()));
//
//        buttonBlock(ModBlocks.BISMUTH_BUTTON.get(), blockTexture(ModBlocks.BISMUTH_BLOCK.get()));
//        pressurePlateBlock(ModBlocks.BISMUTH_PRESSURE_PLATE.get(), blockTexture(ModBlocks.BISMUTH_BLOCK.get()));
//
//        fenceBlock(ModBlocks.BISMUTH_FENCE.get(), blockTexture(ModBlocks.BISMUTH_BLOCK.get()));
//        fenceGateBlock(ModBlocks.BISMUTH_FENCE_GATE.get(), blockTexture(ModBlocks.BISMUTH_BLOCK.get()));
//        wallBlock(ModBlocks.BISMUTH_WALL.get(), blockTexture(ModBlocks.BISMUTH_BLOCK.get()));
//
//        doorBlockWithRenderType(ModBlocks.BISMUTH_DOOR.get(), modLoc("block/bismuth_door_bottom"), modLoc("block/bismuth_door_top"), "cutout");
//        trapdoorBlockWithRenderType(ModBlocks.BISMUTH_TRAPDOOR.get(), modLoc("block/bismuth_trapdoor"), true, "cutout");
//
//        blockItem(ModBlocks.BISMUTH_STAIRS);
//        blockItem(ModBlocks.BISMUTH_SLAB);
//        blockItem(ModBlocks.BISMUTH_PRESSURE_PLATE);
//        blockItem(ModBlocks.BISMUTH_FENCE_GATE);
//        blockItem(ModBlocks.BISMUTH_TRAPDOOR, "_bottom");

//        customLamp();
    }

//    private void customLamp() {
//        getVariantBuilder(ModBlocks.BISMUTH_LAMP.get()).forAllStates(state -> {
//            if(state.getValue(BismuthLampBlock.CLICKED)) {
//                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("bismuth_lamp_on",
//                        ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, "block/" + "bismuth_lamp_on")))};
//            } else {
//                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("bismuth_lamp_off",
//                        ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, "block/" + "bismuth_lamp_off")))};
//            }
//        });
//
//        simpleBlockItem(ModBlocks.BISMUTH_LAMP.get(), models().cubeAll("bismuth_lamp_on",
//                ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, "block/" + "bismuth_lamp_on")));
//    }

    private void blockWithItem(RegistrySupplier<Block> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(RegistrySupplier<Block> block) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(MOD_ID + ":block/" + block.getId().getPath()));
    }

    private void blockItem(RegistrySupplier<Block> block, String appendix) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(MOD_ID + ":block/" + block.getId().getPath() + appendix));
    }
}
