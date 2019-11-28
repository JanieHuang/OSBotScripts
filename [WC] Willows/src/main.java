import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import javax.swing.*;
import javax.xml.bind.annotation.XmlElementDecl;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;


/**
 * Created by JH Nov 21, 2019
 */

@ScriptManifest(author = "JH", logo = "", info = "Cuts Willows near Draynor bank. Banks upon full inventory", version = 1.0, name = "[Wood] Willow Bank v1.1")
public class main extends Script {
    private String interaction = "";

    private int i = 1;
    private String keep0;
    private String keep1;
    private String keep2;
    private String keep3;
    private String keep4;
    private String keep5;

    private JComboBox treeToCut;
    private JButton startButton;
    String treeChosen;


    @Override

    public void onStart() {

        this.keep0 = "Bronze axe" ;
        this.keep1 = "Rune axe" ;
        this.keep2 = "Iron axe";
        this.keep3 = "Steel axe";
        this.keep4 = "Mithril axe";
        this.keep5 = "Adamant axe";
    }

    public void onExit() {

    }


    public int onLoop() throws InterruptedException {


        antiBan();
        stealthMusic();


        if (i >= 5) {
            camera.moveYaw(random(-180, 180));
            i = 0;
        }
        if (!getInventory().isFull()) {
            Entity object1 = objects.closest("Willow");
            if (object1 != null) {
                if (object1.isVisible()) {
                    action();
                }else {
                    //x=3083, y=3237    x= 3091 y=3227
                    Area actionArea = new Area(3083, 3237, 3091, 3227);
                    getWalking().webWalk(actionArea);
                    if (object1 != null) {
                        if (object1.isVisible()) {
                            action();
                        }
                    }
                }

            }
        } else {
            //bank
            Area bankArea = new Area(3092, 3241, 3092, 3245);
            if (bankArea.contains(myPlayer())) {
                Entity bankBooth = objects.closest(34810);

                if (bank.isOpen()) {
                    bank.depositAllExcept(keep0, keep1, keep2, keep3, keep4, keep5);
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
            } else {
                getWalking().webWalk(bankArea);
            }
        }
        //move camera

        return 0;
    }

    public void antiBan() {
        try {
            Random generator = new Random();
            int click = generator.nextInt(10);

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
                o.get(guess);
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

    //fish
    public void action() throws InterruptedException {

        Entity object1 = objects.closest("Willow");


        if (!myPlayer().isAnimating()) {
            if (!myPlayer().isMoving()) {

                object1.interact("Chop Down");
                i++;

            }
        }

        sleep(random(1500, 15000));


    }

}
