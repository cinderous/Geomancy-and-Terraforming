package net.cinderous.geomancyandterraforming.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.property.Properties;

public class SpellDev extends Item {

    public static BlockState targetblock;
    public static BlockPos targetblockPos;
    public static BlockState currentblock;
    public static BlockPos currentblockpos;

    public SpellDev(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        Vector3d playerposvec = playerIn.getPositionVec();
        BlockPos playerpos = new BlockPos(playerposvec.getX(), playerposvec.getY(),playerposvec.getZ());

        World playerworld = playerIn.getEntityWorld();
        Direction playerdirection = playerIn.getHorizontalFacing();

        if (playerIn.isSneaking()) {

//            BlockRayTraceResult rayblockback = worldIn.rayTraceBlocks();
//            RayTraceResult rayback = rayTrace(playerIn.getEntityWorld(), playerIn, RayTraceContext.FluidMode.NONE);
//
//
//            if (rayback.getHitVec() != null) {
//                rayvec = rayback.getHitVec();
//
//
//
//                BlockPos rayblock = new BlockPos(rayvec);
//                if (rayback.getType() == RayTraceResult.Type.BLOCK) {
//                    targetblock = worldIn.getBlockState(rayblock).getBlock();
//                    Hyperlane.LOGGER.info(targetblock);
//                }
//            }


            final RayTraceResult hit = rayTrace(playerIn.getEntityWorld(), playerIn, RayTraceContext.FluidMode.NONE);

            if (hit instanceof BlockRayTraceResult) {

                targetblockPos = ((BlockRayTraceResult) hit).getPos();
                targetblock = worldIn.getBlockState(targetblockPos);


            }

            //Block below targetblock is changed into what targetblock is.
            worldIn.setBlockState(targetblockPos.down(),targetblock);
            //Targetblock is turned into air.
            worldIn.setBlockState(targetblockPos, Blocks.AIR.getDefaultState());

            //NORTH
            //set variable containing pos north of the target block
            currentblockpos = targetblockPos.north();
            //sets variable containing the block state of currentblock pos
            currentblock = worldIn.getBlockState(currentblockpos);
            //sets the block under current block to the current block state
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            //turns currentblock into air
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //WEST
            //set variable containing pos west of the target block
            currentblockpos = targetblockPos.west();
            //sets variable containing the block state of currentblock pos
            currentblock = worldIn.getBlockState(currentblockpos);
            //sets the block under current block to the current block state
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            //turns currentblock into air
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //SOUTH
            //set variable containing pos south of the target block
            currentblockpos = targetblockPos.south();
            //sets variable containing the block state of currentblock pos
            currentblock = worldIn.getBlockState(currentblockpos);
            //sets the block under current block to the current block state
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            //turns currentblock into air
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //EAST
            //set variable containing pos east of the target block
            currentblockpos = targetblockPos.east();
            //sets variable containing the block state of currentblock pos
            currentblock = worldIn.getBlockState(currentblockpos);
            //sets the block under current block to the current block state
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            //turns currentblock into air
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());



            //PHASE 2
            //center block going down
            currentblockpos = new BlockPos(targetblockPos.down().getX(), targetblockPos.down().getY(), targetblockPos.down().getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //extending layer NORTH
            currentblockpos = new BlockPos(targetblockPos.north(2).getX(), targetblockPos.north(2).getY(), targetblockPos.north(2).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //extending layer WEST
            currentblockpos = new BlockPos(targetblockPos.west(2).getX(), targetblockPos.west(2).getY(), targetblockPos.west(2).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //extending layer SOUTH
            currentblockpos = new BlockPos(targetblockPos.south(2).getX(), targetblockPos.south(2).getY(), targetblockPos.south(2).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //extending layer EAST
            currentblockpos = new BlockPos(targetblockPos.east(2).getX(), targetblockPos.east(2).getY(), targetblockPos.east(2).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //corners taken care of
            currentblockpos = new BlockPos(targetblockPos.north(1).west(1).getX(), targetblockPos.north(1).west(1).getY(), targetblockPos.north(1).west(1).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            currentblockpos = new BlockPos(targetblockPos.west(1).south(1).getX(), targetblockPos.west(1).south(1).getY(), targetblockPos.west(1).south(1).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            currentblockpos = new BlockPos(targetblockPos.south(1).east(1).getX(), targetblockPos.south(1).east(1).getY(), targetblockPos.south(1).east(1).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            currentblockpos = new BlockPos(targetblockPos.east(1).north(1).getX(), targetblockPos.east(1).north(1).getY(), targetblockPos.east(1).north(1).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //PHASE 3
            //go deeper
            currentblockpos = new BlockPos(targetblockPos.down(2).getX(), targetblockPos.down(2).getY(), targetblockPos.down(2).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //northwest corner
            currentblockpos = new BlockPos(targetblockPos.north(2).west(1).getX(), targetblockPos.north(2).west(1).getY(), targetblockPos.north(2).west(1).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());
            currentblockpos = new BlockPos(targetblockPos.north(2).west(2).getX(), targetblockPos.north(2).west(2).getY(), targetblockPos.north(2).west(2).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());
            currentblockpos = new BlockPos(targetblockPos.north(1).west(2).getX(), targetblockPos.north(1).west(2).getY(), targetblockPos.north(1).west(2).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //southwest corner
            currentblockpos = new BlockPos(targetblockPos.south(2).west(1).getX(), targetblockPos.south(2).west(1).getY(), targetblockPos.south(2).west(1).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());
            currentblockpos = new BlockPos(targetblockPos.south(2).west(2).getX(), targetblockPos.south(2).west(2).getY(), targetblockPos.south(2).west(2).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());
            currentblockpos = new BlockPos(targetblockPos.south(1).west(2).getX(), targetblockPos.south(1).west(2).getY(), targetblockPos.south(1).west(2).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //southeast corner
            currentblockpos = new BlockPos(targetblockPos.south(2).east(1).getX(), targetblockPos.south(2).east(1).getY(), targetblockPos.south(2).east(1).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());
            currentblockpos = new BlockPos(targetblockPos.south(2).east(2).getX(), targetblockPos.south(2).east(2).getY(), targetblockPos.south(2).east(2).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());
            currentblockpos = new BlockPos(targetblockPos.south(1).east(2).getX(), targetblockPos.south(1).east(2).getY(), targetblockPos.south(1).east(2).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //southeast corner
            currentblockpos = new BlockPos(targetblockPos.south(2).east(1).getX(), targetblockPos.south(2).east(1).getY(), targetblockPos.south(2).east(1).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());
            currentblockpos = new BlockPos(targetblockPos.south(2).east(2).getX(), targetblockPos.south(2).east(2).getY(), targetblockPos.south(2).east(2).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());
            currentblockpos = new BlockPos(targetblockPos.south(1).east(2).getX(), targetblockPos.south(1).east(2).getY(), targetblockPos.south(1).east(2).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //northeast corner
            currentblockpos = new BlockPos(targetblockPos.north(2).east(1).getX(), targetblockPos.north(2).east(1).getY(), targetblockPos.north(2).east(1).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());
            currentblockpos = new BlockPos(targetblockPos.north(2).east(2).getX(), targetblockPos.north(2).east(2).getY(), targetblockPos.north(2).east(2).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());
            currentblockpos = new BlockPos(targetblockPos.north(1).east(2).getX(), targetblockPos.north(1).east(2).getY(), targetblockPos.north(1).east(2).getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //PAHSE 4
            //north from center deeper
            currentblockpos = new BlockPos(targetblockPos.down(1).north().getX(), targetblockPos.down(1).north().getY(), targetblockPos.down(1).north().getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //west from center deeper
            currentblockpos = new BlockPos(targetblockPos.down(1).west().getX(), targetblockPos.down(1).west().getY(), targetblockPos.down(1).west().getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //south from center deeper
            currentblockpos = new BlockPos(targetblockPos.down(1).south().getX(), targetblockPos.down(1).south().getY(), targetblockPos.down(1).south().getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());


            //east from center deeper
            currentblockpos = new BlockPos(targetblockPos.down(1).east().getX(), targetblockPos.down(1).east().getY(), targetblockPos.down(1).east().getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //PAHSE 5
            //north from center deeper
            currentblockpos = new BlockPos(targetblockPos.down(2).north().getX(), targetblockPos.down(2).north().getY(), targetblockPos.down(2).north().getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //west from center deeper
            currentblockpos = new BlockPos(targetblockPos.down(2).west().getX(), targetblockPos.down(2).west().getY(), targetblockPos.down(2).west().getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());

            //south from center deeper
            currentblockpos = new BlockPos(targetblockPos.down(2).south().getX(), targetblockPos.down(2).south().getY(), targetblockPos.down(2).south().getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());


            //east from center deeper
            currentblockpos = new BlockPos(targetblockPos.down(1).east().getX(), targetblockPos.down(1).east().getY(), targetblockPos.down(1).east().getZ());
            currentblock = worldIn.getBlockState(currentblockpos);
            worldIn.setBlockState(currentblockpos.down(), currentblock);
            worldIn.setBlockState(currentblockpos, Blocks.AIR.getDefaultState());


            //FINAL PHASE
            //need to change targetblock into air because client sees it as air but server sees it as dirt, requiring a block to be placed on targetblock to reveal the block that is actually there.
            worldIn.setBlockState(targetblockPos, Blocks.AIR.getDefaultState());



        }



        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

}