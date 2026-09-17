package org.mob.replication_addon_tiers.block;

import com.buuz135.replication.ReplicationConfig;
import com.buuz135.replication.ReplicationRegistry;
import com.buuz135.replication.block.shapes.DisintegratorShapes;
import com.hrznstudio.titanium.block.RotatableBlock;
import com.hrznstudio.titanium.block_network.INetworkDirectionalConnection;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.hrznstudio.titanium.util.FacingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.mob.replication_addon_tiers.Config;
import org.mob.replication_addon_tiers.block.custom.AdvancedDisintegratorBlockEntity;
import org.mob.replication_addon_tiers.registry.ModRegistry;

import java.util.List;

public class AdvancedDisintegratorBlock extends RotatableBlock<AdvancedDisintegratorBlockEntity> implements INetworkDirectionalConnection {

    public AdvancedDisintegratorBlock() {
        super("advanced_disintegrator", Properties.ofFullCopy(Blocks.IRON_BLOCK), AdvancedDisintegratorBlockEntity.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return (pos, blockState) -> new AdvancedDisintegratorBlockEntity(this, ModRegistry.ADVANCED_DISINTEGRATOR_BE.get(), pos, blockState);
    }

    @NotNull
    @Override
    public RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }

    @NotNull
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext selectionContext) {
        var rotation = state.getValue(FACING_HORIZONTAL);
        if (rotation == Direction.NORTH){
            return DisintegratorShapes.NORTH;
        }
        if (rotation == Direction.SOUTH){
            return DisintegratorShapes.SOUTH;
        }
        if (rotation == Direction.EAST){
            return DisintegratorShapes.EAST;
        }
        if (rotation == Direction.WEST){
            return DisintegratorShapes.WEST;
        }
        return super.getCollisionShape(state, world, pos, selectionContext);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        var rotation = state.getValue(FACING_HORIZONTAL);
        if (rotation == Direction.NORTH){
            return DisintegratorShapes.NORTH;
        }
        if (rotation == Direction.SOUTH){
            return DisintegratorShapes.SOUTH;
        }
        if (rotation == Direction.EAST){
            return DisintegratorShapes.EAST;
        }
        if (rotation == Direction.WEST){
            return DisintegratorShapes.WEST;
        }
        return super.getShape(state, blockGetter, blockPos, collisionContext);
    }

    @Override
    public boolean canConnect(Level level, BlockPos pos, BlockState state, Direction direction) {
        var sideness = FacingUtil.getFacingRelative(direction, state.getValue(FACING_HORIZONTAL));
        return sideness == FacingUtil.Sideness.BOTTOM || sideness == FacingUtil.Sideness.BACK;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.replication_addon_tiers.disintegrator" , (ReplicationConfig.Disintegrator.MAX_PROGRESS / 2) , (ReplicationConfig.Disintegrator.MAX_PROGRESS)));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}