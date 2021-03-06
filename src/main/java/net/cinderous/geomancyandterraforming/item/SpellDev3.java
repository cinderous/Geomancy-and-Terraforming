package net.cinderous.geomancyandterraforming.item;

import net.cinderous.geomancyandterraforming.GeomancyAndTerraforming;
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

public class SpellDev3 extends Item {

    public static BlockState targetblock;
    public static BlockPos targetblockpos;
    public static BlockState currentblock;
    public static BlockPos currentblockpos;
    public static int spellpower;
    public static int sinkholesizetoside;
    public static int sinkholedepth;

    public int tick = 0;

    public SpellDev3(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {


        ////


        Vector3d playerposvec = playerIn.getPositionVec();
        BlockPos playerpos = new BlockPos(playerposvec.getX(), playerposvec.getY(), playerposvec.getZ());

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

                targetblockpos = ((BlockRayTraceResult) hit).getPos();
                targetblock = worldIn.getBlockState(targetblockpos);


            }

            if (!worldIn.isRemote) {

                //goal is to get a square based on spell power and sink the insides using math
                //we need a x y z that can scale dynamically in constraints
                //example is a 5x5 square
                //we need to get how far from the center is each side
                //that would be half of spellpower(5) and rounded to the floor
                //spellpower must be odd to allow the center to exist at different spellpowers

                spellpower = 15;
                sinkholesizetoside = (int) Math.floor(spellpower * 0.5);

//                int g;
//                for(g = 0; g <= spellpower; ++g) {

                    //find the northwest corner and cut out a length by spell power on x and repeat those steps through z
                    currentblockpos = new BlockPos(targetblockpos.north(sinkholesizetoside).west(sinkholesizetoside).getX(), targetblockpos.north(sinkholesizetoside).west(sinkholesizetoside).getY(), targetblockpos.north(sinkholesizetoside).west(sinkholesizetoside).getZ());
                    int s;

                    for (s = 0; s <= spellpower-1; ++s) {
                        int x;
                        int sinkholepowerposX = currentblockpos.south(s).getX();
                        int sinkholepowerposZ = currentblockpos.south(s).getZ();
                        for (x = currentblockpos.south(s).getX(); x <= sinkholepowerposX + spellpower - 1; ++x) {
                            GeomancyAndTerraforming.LOGGER.info(x);
                            BlockPos workingblockpos = new BlockPos(x, currentblockpos.getY(), sinkholepowerposZ);
                            BlockState workingblock = worldIn.getBlockState(workingblockpos);
                            worldIn.setBlockState(workingblockpos.down(), workingblock);
                            worldIn.setBlockState(workingblockpos, Blocks.AIR.getDefaultState());

                        }
                    }

                    //phase2really
                    //the target block layer is now sunk
                    ++sinkholedepth;
                    int h;
                    for(h = 0; h <= (spellpower - sinkholedepth); ++h) {

                        int innersizeworking = sinkholesizetoside - sinkholedepth ;
                        currentblockpos = new BlockPos(targetblockpos.north(innersizeworking).west(innersizeworking).getX(), targetblockpos.north(innersizeworking).west(innersizeworking).getY(), targetblockpos.north(innersizeworking).west(innersizeworking).getZ());


                        //we need to prepare the next level to be moved
                        ++sinkholedepth;

                        for (s = 0; s <= spellpower-sinkholedepth-1; ++s) {
                            int x;
                            int sinkholepowerposX = currentblockpos.south(s).getX();
                            int sinkholepowerposZ = currentblockpos.south(s).getZ();
                            int sinkholedepthcurrent = currentblockpos.down(h).getY();
                            //we need to follow along x removing the land but stopping short from the sides but still effected by spell power
                            for (x = sinkholepowerposX; x <= sinkholepowerposX + spellpower - sinkholedepth - 1; ++x) {


                                BlockPos workingblockpos = new BlockPos(x, sinkholedepthcurrent, sinkholepowerposZ);
                                BlockState workingblock = worldIn.getBlockState(workingblockpos);

                                GeomancyAndTerraforming.LOGGER.info(workingblockpos);

                                worldIn.setBlockState(workingblockpos.down(), workingblock);
                                worldIn.setBlockState(workingblockpos, Blocks.AIR.getDefaultState());

                                GeomancyAndTerraforming.LOGGER.info("COMPLETED A ROW TOWARDS X OF REMOVAL");


                            }
                        }
                    }

                    }
            GeomancyAndTerraforming.LOGGER.info(sinkholedepth);
            sinkholedepth = 0;







//                }


//
//                int z;
//                int sinkholepowerposZ = currentblockpos.getZ();
//                for (z = currentblockpos.getZ()+1; z <= sinkholepowerposZ+spellpower-1; ++z) {
//                    GeomancyAndTerraforming.LOGGER.info(z);
//                    BlockPos workingblockpos = new BlockPos (currentblockpos.getX(), currentblockpos.getY(), z);
//                    BlockState workingblock = worldIn.getBlockState(workingblockpos);
//                    worldIn.setBlockState(workingblockpos.down(), workingblock);
//                    worldIn.setBlockState(workingblockpos, Blocks.AIR.getDefaultState());
//
//                }
//                //find the southeast corner
//                currentblockpos = new BlockPos(targetblockpos.south(sinkholesizetoside).east(sinkholesizetoside).getX(), targetblockpos.south(sinkholesizetoside).east(sinkholesizetoside).getY(), targetblockpos.south(sinkholesizetoside).east(sinkholesizetoside).getZ());
//
//                sinkholepowerposX = currentblockpos.getX();
//                for (x = currentblockpos.getX(); x >= sinkholepowerposX-spellpower+2; --x) {
//                    GeomancyAndTerraforming.LOGGER.info(x);
//                    BlockPos workingblockpos = new BlockPos (x, currentblockpos.getY(), currentblockpos.getZ());
//                    BlockState workingblock = worldIn.getBlockState(workingblockpos);
//                    worldIn.setBlockState(workingblockpos.down(), workingblock);
//                    worldIn.setBlockState(workingblockpos, Blocks.AIR.getDefaultState());
//
//                }
//
//                sinkholepowerposZ = currentblockpos.getZ();
//                for (z = currentblockpos.getZ()-1; z >= sinkholepowerposZ-spellpower+2; --z) {
//                    GeomancyAndTerraforming.LOGGER.info(z);
//                    BlockPos workingblockpos = new BlockPos (currentblockpos.getX(), currentblockpos.getY(), z);
//                    BlockState workingblock = worldIn.getBlockState(workingblockpos);
//                    worldIn.setBlockState(workingblockpos.down(), workingblock);
//                    worldIn.setBlockState(workingblockpos, Blocks.AIR.getDefaultState());
//
//                }
//                //find the northwest corner extended 1
////            if(spellpower >=3) {
////                int s;
////                for(s=spellpower; s >= sinkholesizetoside; --s ) {
//
//                currentblockpos = new BlockPos(targetblockpos.north(sinkholesizetoside-1).west(sinkholesizetoside-1).getX(), targetblockpos.north(sinkholesizetoside-1).west(sinkholesizetoside-1).getY(), targetblockpos.north(sinkholesizetoside-1).west(sinkholesizetoside-1).getZ());
//
//                sinkholepowerposX = currentblockpos.getX();
//                for (x = currentblockpos.getX(); x <= sinkholepowerposX+spellpower-3; ++x) {
//                    GeomancyAndTerraforming.LOGGER.info(x);
//                    BlockPos workingblockpos = new BlockPos (x, currentblockpos.getY(), currentblockpos.getZ());
//                    BlockState workingblock = worldIn.getBlockState(workingblockpos);
//                    worldIn.setBlockState(workingblockpos.down(), workingblock);
//                    worldIn.setBlockState(workingblockpos, Blocks.AIR.getDefaultState());
//
//                }
//
//                currentblockpos = new BlockPos(targetblockpos.south(sinkholesizetoside-1).east(sinkholesizetoside-1).getX(), targetblockpos.south(sinkholesizetoside-1).east(sinkholesizetoside-1).getY(), targetblockpos.south(sinkholesizetoside-1).east(sinkholesizetoside-1).getZ());
//
//                sinkholepowerposX = currentblockpos.getX();
//                for (x = currentblockpos.getX(); x <= sinkholepowerposX-spellpower+3; --x) {
//                    GeomancyAndTerraforming.LOGGER.info(x);
//                    BlockPos workingblockpos = new BlockPos (x, currentblockpos.getY(), currentblockpos.getZ());
//                    BlockState workingblock = worldIn.getBlockState(workingblockpos);
//                    worldIn.setBlockState(workingblockpos.down(), workingblock);
//                    worldIn.setBlockState(workingblockpos, Blocks.AIR.getDefaultState());
//
//                }
////                }
//
//
//            }
//
//

//            worldIn.setBlockState(targetblockpos.north(sinkholesizetoside).down(), targetblock);
//            worldIn.setBlockState(targetblockpos.north(sinkholesizetoside), Blocks.AIR.getDefaultState());


                //Block below targetblock is changed into what targetblock is.
//                worldIn.setBlockState(targetblockpos.down(), targetblock);
//                //Targetblock is turned into air.
//                worldIn.setBlockState(targetblockpos, Blocks.AIR.getDefaultState());


            }return super.onItemRightClick(worldIn, playerIn, handIn);




    }
}




