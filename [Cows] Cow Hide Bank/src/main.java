import org.osbot.E;
import org.osbot.rs07.api.Objects;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.*;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;
import sun.reflect.generics.tree.Tree;

import java.util.List;
import java.util.Random;


/**
 * Created by JH on 02/18/2020.
 */

@ScriptManifest(author = "JH", logo = "", info ="Cows in Lumbridge, gets Hides, banks" , version = 1.3 , name = "[Cows]Cow Killer v1.3")
public class main extends Script {

    private int i =1;
    @Override

    public void onStart(){
    }

    public void onExit(){

    }


    public int onLoop() throws InterruptedException {

        antiBan();
        stealthDance();


        if (i >= 5){
            camera.moveYaw(random(-180,180));
            i =0;
        }

        //Cow Area
        Area gateArea = new Area(3253, 3268, 3252, 3266);
        Area cowArea = new Area(3253, 3255, 3265, 3297);

        if(!getInventory().isFull()){

            //Go to gate area first, open gate
            if(gateArea.contains(myPlayer())){
                openNearestGate();
                getWalking().webWalk(cowArea);
            }

            if(cowArea.contains(myPlayer()))
            {
                GroundItem Cowhide = getGroundItems().closest("Cowhide");

                if(Cowhide != null && myPlayer().getInteracting() == null) {
                        Cowhide.interact("take");
                }

                if (isKilling()) {
                    new ConditionalSleep(5000) {
                        @Override
                        public boolean condition() throws InterruptedException {
                            return !isKilling();
                        }
                    }.sleep();
                }  else if(attackCow())
                {
                    new ConditionalSleep(5000) {
                        @Override
                        public boolean condition() throws InterruptedException {
                            return isKilling();
                        }
                    }.sleep();
                }

            } else
            {
                getWalking().webWalk(gateArea);
            }

        }
        else {
            //Lumbridge bank
            Area bankArea = new Area(3207, 3220, 3210, 3220);

            if (bankArea.contains(myPlayer())) {
                Entity bankBooth = objects.closest(18491);

                if (bank.isOpen()) {
                    bank.depositAll();
                } else {
                    if (bankBooth != null) {
                        if (bankBooth.isVisible()) {
                            bankBooth.interact("Bank");
                            sleep(random(1500, 15000));
                        }
                    }
                }

                sleep(random(700, 1500));
                //walk to bank
            } else if(gateArea.contains(myPlayer()))
            {
                openNearestGate();
                getWalking().webWalk(bankArea);
            } else
            {
                getWalking().webWalk(bankArea);
            }
        }

        return 0;
    }

    private boolean attackCow() {
        NPC cow = npcs.closest(new Filter<NPC>() {
            @Override
            public boolean match(NPC npc) {
                return npc != null && npc.getName().contains("Cow") && !npc.getName().contains("Dairy")
                        && !npc.getName().contains("calf") && !npc.isUnderAttack() && npc.isAttackable()
                        && npc.getHealthPercent() > 0;

            }
        });

        return cow != null && cow.interact("Attack");
    }

    private boolean isKilling() {
        return myPlayer().isUnderAttack();
    }

    public boolean openNearestGate() {
        Entity closedGate = getObjects().closest("Gate");
        if (closedGate != null && closedGate.hasAction("Open")){
            return closedGate.interact("Open");
        }
        return false;
    }

    public void antiBan() {
        try {
            Random generator = new Random();
            int click = generator.nextInt(10);

            String interaction = "";
            if ((click > 7) && (myPlayer().isMoving())) {
                interaction = "Antiban, Mouse Right-Click";
                this.mouse.click(true);
            }
            int rand = generator.nextInt(40000);
            if ((rand > 49) && (rand < 100)) {
                interaction = "Antiban, Camera Movement";
                this.camera.movePitch(random(0, 360));
                this.camera.moveYaw(random(0, 360));
            }
            if ((rand > 100) && (rand < 400)) {
                interaction = "Antiban, Camera Movement";
                this.camera.movePitch(random(0, 360));
            }
            if ((rand > 2400) && (rand < 3000)) {
                interaction = "Antiban, Camera Movement";
                this.camera.moveYaw(random(0, 360));
            }
            if ((rand > 3000) && (rand < 3500)) {
                interaction = "Antiban, Random Mouse Movement";
            }
            if ((rand > 4000) && (rand < 4500)) {
                interaction = "Antiban, Random Mouse Movement";
            }
            if ((rand > 5000) && (rand < 5500)) {
                interaction = "Antiban, Random Mouse Movement";
            }
            if ((rand > 6000) && (rand < 6200)) {
                interaction = "Antiban, Opening Tab";
                this.tabs.open(Tab.ATTACK);
                sleep(random(500, 2000));
                this.tabs.open(Tab.INVENTORY);
            }
            if ((rand > 6500) && (rand < 7000)) {
                interaction = "Antiban, Opening Tab + Checking Stats";
                this.tabs.open(Tab.SKILLS);
                sleep(random(500, 1000));
                int stat = generator.nextInt(24);
                if (stat == 0) {
                    this.mouse.move(random(568, 588), random(214, 234));
                }
                if (stat == 1) {
                    this.mouse.move(random(568, 588), random(244, 264));
                }
                if (stat == 2) {
                    this.mouse.move(random(568, 588), random(274, 294));
                }
                if (stat == 3) {
                    this.mouse.move(random(568, 588), random(304, 324));
                }
                if (stat == 4) {
                    this.mouse.move(random(568, 588), random(334, 354));
                }
                if (stat == 5) {
                    this.mouse.move(random(568, 588), random(364, 384));
                }
                if (stat == 6) {
                    this.mouse.move(random(568, 588), random(394, 414));
                }
                if (stat == 7) {
                    this.mouse.move(random(568, 588), random(424, 444));
                }
                if (stat == 8) {
                    this.mouse.move(random(638, 658), random(214, 234));
                }
                if (stat == 9) {
                    this.mouse.move(random(638, 658), random(244, 264));
                }
                if (stat == 10) {
                    this.mouse.move(random(638, 658), random(274, 294));
                }
                if (stat == 11) {
                    this.mouse.move(random(638, 658), random(304, 324));
                }
                if (stat == 12) {
                    this.mouse.move(random(638, 658), random(334, 354));
                }
                if (stat == 13) {
                    this.mouse.move(random(638, 658), random(364, 384));
                }
                if (stat == 14) {
                    this.mouse.move(random(638, 658), random(394, 414));
                }
                if (stat == 15) {
                    this.mouse.move(random(638, 658), random(424, 444));
                }
                if (stat == 16) {
                    this.mouse.move(random(708, 728), random(214, 234));
                }
                if (stat == 17) {
                    this.mouse.move(random(708, 728), random(244, 264));
                }
                if (stat == 18) {
                    this.mouse.move(random(708, 728), random(274, 294));
                }
                if (stat == 19) {
                    this.mouse.move(random(708, 728), random(304, 324));
                }
                if (stat == 20) {
                    this.mouse.move(random(708, 728), random(334, 354));
                }
                if (stat == 21) {
                    this.mouse.move(random(708, 728), random(364, 384));
                }
                if (stat == 22) {
                    this.mouse.move(random(708, 728), random(394, 414));
                }
                sleep(random(1000, 15000));
                this.tabs.open(Tab.INVENTORY);
            }
            if ((rand > 7000) && (rand < 7200)) {
                interaction = "Antiban, Opening Tab";
                this.tabs.open(Tab.SETTINGS);
                sleep(random(500, 2000));
                this.tabs.open(Tab.INVENTORY);
            }
            if ((rand > 7500) && (rand < 7700)) {
                interaction = "Antiban, Opening Tab";
                this.tabs.open(Tab.EQUIPMENT);
                sleep(random(500, 2000));
                this.tabs.open(Tab.INVENTORY);
            }
            if ((rand > 8000) && (rand < 8200)) {
                interaction = "Antiban, Opening Tab";
                this.tabs.open(Tab.FRIENDS);
                sleep(random(500, 2000));
                this.tabs.open(Tab.INVENTORY);
            }
            if ((rand > 8200) && (rand < 8700)) {
                interaction = "Antiban, Opening Tab";
                this.tabs.open(Tab.QUEST);
                sleep(random(500, 2000));
                this.tabs.open(Tab.INVENTORY);
            }
            if ((rand > 8700) && (rand < 9200)) {
                interaction = "Antiban, Opening Tab";
                this.tabs.open(Tab.PRAYER);
                sleep(random(500, 2000));
                this.tabs.open(Tab.INVENTORY);
            }
            if ((rand > 9200) && (rand < 9700)) {
                interaction = "Antiban, Opening Tab";
                this.tabs.open(Tab.MAGIC);
                sleep(random(500, 2000));
                this.tabs.open(Tab.INVENTORY);
            }
            if ((rand > 9700) && (rand < 10200)) {
                interaction = "Antiban, Opening Tab";
                this.tabs.open(Tab.EMOTES);
                sleep(random(500, 2000));
                this.tabs.open(Tab.INVENTORY);
            }
            if ((rand > 10200) && (rand < 10700)) {
                interaction = "Antiban, Opening Tab + Playing New Song";
                this.tabs.open(Tab.MUSIC);
                sleep(random(500, 1000));
                stealthMusic();
                this.tabs.open(Tab.INVENTORY);
            }
            if ((rand > 15000) && (rand < 15200)) {
                interaction = "Antiban, Moving Camera";
                this.camera.movePitch(random(0, 360));
            }
            if ((rand > 16000) && (rand < 16200)) {
                interaction = "Antiban, Moving Camera";
                this.camera.moveYaw(random(0, 360));
            }
            if ((rand > 20000) && (rand < 21000)) {
                if (!myPlayer().isAnimating() && !myPlayer().isUnderAttack()) {
                    stealthDance();
                }
            }
            if ((rand > 20000) && (rand < 27000)) {
                List<RS2Object> o = objects.getAll();
                int guess = new Random().nextInt(o.size());
                camera.toEntity(o.get(guess));
            }
            if ((rand > 27000) && (rand < 27500)) {
                List<RS2Object> o = objects.getAll();
                int guess = new Random().nextInt(o.size());
            }
            if ((rand > 28000) && (rand < 30000)) {
                List<NPC> o = npcs.getAll();
                int guess = new Random().nextInt(o.size());
                camera.toEntity(o.get(guess));
            }
            if ((rand > 30000) && (rand < 40000)) {
                List<Player> o = players.getAll();
                int guess = new Random().nextInt(o.size());
                camera.toEntity(o.get(guess));
                sleep(random(400, 1000));
                mouse.move(o.get(guess).getX(), o.get(guess).getY());
                sleep(random(600, 1000));
                //mouse.click(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stealthDance() {
        int startx = 565;
        int starty = 215;
        int row = new Random().nextInt(5);
        int col = new Random().nextInt(4);
        int rowMultiplier = 50;
        int colMultiplier = 45;
        mouse.move(startx + (col + 1) * colMultiplier,
                starty + (row + 1) * rowMultiplier);
    }

    private void stealthMusic() {
        try {
            // randomly picks music for the game
            Random gen = new Random();
            int guess = gen.nextInt(2);
            if (guess == 0) {
                mouse.scrollDown();
                sleep(random(600, 1200));
                mouse.move(random(560, 600), random(286, 440));
                sleep(random(500, 900));
                mouse.click(false);
            } else {
                // up
                mouse.scrollUp();
                sleep(random(600, 1200));
                mouse.move(random(560, 600), random(286, 440));
                sleep(random(500, 900));
                mouse.click(false);
            }
        } catch (Exception e) {

        }
    }





}
