package net.cinderous.geomancyandterraforming.item;

import net.cinderous.geomancyandterraforming.GeomancyAndTerraforming;
import net.cinderous.geomancyandterraforming.util.TickHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
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

import java.util.Random;

public class SpellDev4 extends Item {
    private TickHandler handler;

    public Random rand = new Random();
    int firstHeight = rand.nextInt(9) + 5;
    public Random rand2 = new Random();
    int firstLength = rand2.nextInt(4);
    int secondLength = rand2.nextInt(6);
    int firstWidthDirection = rand.nextInt(4);
    int secondWidthDirection = rand.nextInt(4);


    int height = rand.nextInt(15) + 2;
    int numberOfPods = 2;


    public static BlockState targetblock;
    public static BlockPos targetblockpos;
    public static BlockState currentblock;
    public static BlockPos currentblockpos;
    public static int spellpower = 15;
    public static int sinkholesizetoside;
    public static int sinkholedepth;

    public static boolean isCasting = false;
    public static boolean hasCasted = true;
    public static World currentWorldMain;
    public static Entity currentPlayerMain;
    public static BlockPos targetBlockPosMain;



    public static int timesThirdRan = 0;
    public static boolean targetisnull = false;

    public int tick = 0;

    public SpellDev4(Properties properties) {
        super(properties);
        handler = new TickHandler();
        handler.addMethod("initialize", (timesRun) -> initialize());
        handler.addMethod("firstLoop", (timesRun) -> doFirstLoop());
        //
        handler.addMethod("secondLoop", (timesRun) -> doSecondLoop());
        handler.addRepeatedDelayedMethod("thirdLoop", spellpower, 10, (timesRun) -> doThirdLoop(timesThirdRan));
//            handler.addRepeatedDelayedMethod("thirdLoop", firstLength, 5, (timesRun) -> thisIsAwesome(horizontalDirection[firstWidthDirection]));
//            handler.addMethod("doLeaves", (timesRun) -> doLeaves());
//            handler.addMethod("finalizeTree", (timesRun) -> finalizeTree());
//            handler.addRepeatedDelayedMethod("doMoreTree", rand.nextInt(9) + 5, 5, (timesRun) -> doMoreTree(verticalDirection[0]));
//            handler.addRepeatedDelayedMethod("doMoreBranches", secondLength, 5, (timesRun) -> doMoreBranches(horizontalDirection[secondWidthDirection]));
    }




    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isRemote) {

            currentWorldMain = worldIn;
            currentPlayerMain = entityIn;



            handler.tick();




        }


        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

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



            if(isCasting && timesThirdRan == spellpower) {
                timesThirdRan = 0  ;
                isCasting = false;
            }
            isCasting = true;



        }return super.onItemRightClick(worldIn, playerIn, handIn);
    }


    public String initialize () {

        //handler.addVariable("currentPos", this.getPos());
        handler.addVariable("currentWorld", currentWorldMain);
        handler.addVariable("currentPlayer", currentPlayerMain);
        handler.addVariable("currentPos", new BlockPos (0,0,0));
        //currentWorldMain = (World) handler.getVariable("currentWorld").get();
        //currentPlayerMain = (Entity) handler.getVariable("currentPlayer").get();
        targetBlockPosMain = (BlockPos) handler.getVariable("currentPos").get();



        World currentWorld = (World) handler.getVariable("currentWorld").get();

            if(targetblockpos == null && !currentPlayerMain.isSneaking()) {
                targetblockpos = targetBlockPosMain;
                targetisnull = true;
            }

            if(isCasting || !targetisnull) {

                final RayTraceResult hit = rayTrace(currentPlayerMain.getEntityWorld(), (PlayerEntity) currentPlayerMain, RayTraceContext.FluidMode.NONE);

                if (hit instanceof BlockRayTraceResult) {

                    targetblockpos = ((BlockRayTraceResult) hit).getPos();
                    targetblock = currentWorldMain.getBlockState(targetblockpos);

                    //targetBlockPosMain = (BlockPos) handler.getVariable("targetBlockPos");

                    targetisnull = false;



                }
            if (!currentWorld.isRemote && isCasting  ) {

                //goal is to get a square based on spell power and sink the insides using math
                //we need a x y z that can scale dynamically in constraints
                //example is a 5x5 square
                //we need to get how far from the center is each side
                //that would be half of spellpower(5) and rounded to the floor
                //spellpower must be odd to allow the center to exist at different spellpowers

                //spellpower = 15;
                sinkholesizetoside = (int) Math.floor(spellpower * 0.5);

//                int g;
//                for(g = 0; g <= spellpower; ++g) {

                //find the northwest corner and cut out a length by spell power on x and repeat those steps through z
                currentblockpos = new BlockPos(targetblockpos.north(sinkholesizetoside).west(sinkholesizetoside).getX(), targetblockpos.north(sinkholesizetoside).west(sinkholesizetoside).getY(), targetblockpos.north(sinkholesizetoside).west(sinkholesizetoside).getZ());
                int s;

                for (s = 0; s <= spellpower - 1; ++s) {
                    int x;
                    int sinkholepowerposX = currentblockpos.south(s).getX();
                    int sinkholepowerposZ = currentblockpos.south(s).getZ();
                    for (x = currentblockpos.south(s).getX(); x <= sinkholepowerposX + spellpower - 1; ++x) {

                        BlockPos workingblockpos = new BlockPos(x, currentblockpos.getY(), sinkholepowerposZ);
                        BlockState workingblock = currentWorld.getBlockState(workingblockpos);
                        currentWorld.setBlockState(workingblockpos.down(), workingblock);
                        currentWorld.setBlockState(workingblockpos, Blocks.AIR.getDefaultState());

                    }
                }



            }

            sinkholedepth = 0;


        }

        handler.addVariable("currentPos", targetblockpos);
    GeomancyAndTerraforming.LOGGER.info(timesThirdRan);
        return "firstLoop";

    }


    public String doFirstLoop() {
        World currentWorld = (World) handler.getVariable("currentWorld").get();
        Entity currentPlayer = (Entity) handler.getVariable("currentPlayer").get();
        if (targetisnull) {
            return "initialize";
        }
        if (!targetisnull && timesThirdRan < spellpower) {
            return "thirdLoop";
        }
        isCasting = false;
        return "secondLoop";
    }

    private String doSecondLoop() {
        return "firstLoop";
    }

    private String doThirdLoop(int timesThirdRan) {
        if(timesThirdRan == spellpower){
            isCasting=false;
        }
        World currentWorld = (World) handler.getVariable("currentWorld").get();
        if (!currentWorld.isRemote && isCasting) {



            BlockPos initialtarget = (BlockPos) handler.getVariable("currentPos").get();
            GeomancyAndTerraforming.LOGGER.info(initialtarget);

//            int h;
//            for (h = 0; h <= (spellpower - timesThirdRan); ++h) {

                int innersizeworking = sinkholesizetoside - timesThirdRan;
                currentblockpos = new BlockPos(initialtarget.north(innersizeworking).west(innersizeworking).getX(), initialtarget.north(innersizeworking).west(innersizeworking).getY(), initialtarget.north(innersizeworking).west(innersizeworking).getZ());


                int s;
                for (s = 0; s <= spellpower - timesThirdRan; ++s) {
                    int x;
                    int sinkholepowerposX = currentblockpos.south(s).getX();
                    int sinkholepowerposZ = currentblockpos.south(s).getZ();
                    int sinkholedepthcurrent = currentblockpos.down(timesThirdRan).getY();
                    //we need to follow along x removing the land but stopping short from the sides but still effected by spell power
                    for (x = sinkholepowerposX; x <= sinkholepowerposX + spellpower - timesThirdRan - 1; ++x) {


                        BlockPos workingblockpos = new BlockPos(x, sinkholedepthcurrent, sinkholepowerposZ);
                        BlockState workingblock = currentWorld.getBlockState(workingblockpos);



                        currentWorld.setBlockState(workingblockpos.down(), workingblock);
                        currentWorld.setBlockState(workingblockpos, Blocks.AIR.getDefaultState());
                        handler.addVariable("currentPos", initialtarget);
                        ++timesThirdRan;








                    }
//                }

            }
        }


        return "firstLoop";
    }
}