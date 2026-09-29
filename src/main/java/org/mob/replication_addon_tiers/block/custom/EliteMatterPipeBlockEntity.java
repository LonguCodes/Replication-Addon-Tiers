package org.mob.replication_addon_tiers.block.custom;

import com.buuz135.replication.ReplicationConfig;
import com.buuz135.replication.block.tile.NetworkBlockEntity;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block_network.element.NetworkElement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import org.jetbrains.annotations.NotNull;
import org.mob.replication_addon_tiers.Config;

public class EliteMatterPipeBlockEntity extends NetworkBlockEntity<EliteMatterPipeBlockEntity> {


    @Save
    private boolean needsToRecreateEnergyStorage;

    private final int powerTransferRate = ReplicationConfig.MatterPipe.POWER_TRANSFER * Config.eliteMatterPipe;


    public EliteMatterPipeBlockEntity(BasicTileBlock<EliteMatterPipeBlockEntity> base, BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(base, blockEntityType, pos, state);
        this.needsToRecreateEnergyStorage = false;
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state, EliteMatterPipeBlockEntity blockEntity) {
        super.serverTick(level, pos, state, blockEntity);

        if (level.getGameTime() % 2 == 0){
            if (needsToRecreateEnergyStorage) {
                level.invalidateCapabilities(this.worldPosition);
                this.needsToRecreateEnergyStorage = false;
                this.level.updateNeighborsAt(this.worldPosition, this.getBlockState().getBlock());
            }
            for (Direction value : Direction.values()) {
                var capability = this.level.getCapability(Capabilities.EnergyStorage.BLOCK, this.worldPosition.relative(value), value.getOpposite());
                var tile = this.level.getBlockEntity(this.worldPosition.relative(value));
                if (capability != null && !(tile instanceof EliteMatterPipeBlockEntity) && this.getNetwork() != null){
                    var simulatedExtract = this.getNetwork().getEnergyStorage().extractEnergy(powerTransferRate, true);
                    var realExtracted = capability.receiveEnergy(simulatedExtract, false);
                    this.getNetwork().getEnergyStorage().extractEnergy(realExtracted, false);
                }
            }
        }
    }

    @NotNull
    @Override
    public EliteMatterPipeBlockEntity getSelf() {
        return this;
    }

    @Override
    protected NetworkElement createElement(Level level, BlockPos pos) {
        return super.createElement(level, pos);
    }

    public void setNeedsToRecreateEnergyStorage(boolean needsToRecreateEnergyStorage) {
        this.needsToRecreateEnergyStorage = needsToRecreateEnergyStorage;
    }
}