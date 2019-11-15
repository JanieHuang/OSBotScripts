import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.utility.ConditionalSleep;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Created by Sean Frazier on 2/4/2016.
 * Modified by JH on 10/20/2019
 */

@ScriptManifest(author = "JH", logo = "", info ="Automatically cuts Trees anywhere till full inventory. Then lights them on fire" , version = 1.0 , name = "[WC] Tree + Fire")
public class main extends Script {

    private Position playerPosition;

    private long startTime, testTime;

    private boolean inputAccepted = false, shouldChop = true;

    private String selectedTree, selectedLog, selectedAction = "Nulling";

    private String[] groundObjects = {"Fire", "Daisies", "Fern", "Stones", "Thistle"};

    private int logsChopped = 0, firesMade = 0, startingLevelWC, startingLevelFM, pseudoAntiBanCounter;

    private String[] typesOfAxes = {"Bronze axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe", "Dragon axe"};

    private String[] inventoryList = {"Tinderbox", "Bronze axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe", "Dragon axe"};

    private String[] typesOfLogs = {"Logs", "Oak logs", "Willow logs", "Maple logs", "Mahogany logs", "Yew logs", "Magic logs"};

    private enum State { CHOP, DROP, BURN, EXIT };

    private State getState() {

        if (inventory.isFull() && (!(inventory.contains("Tinderbox")))) return State.DROP;

        if (inventory.contains(typesOfAxes) && shouldChop) return State.CHOP;

        if (inventory.contains(typesOfLogs) && inventory.contains("Tinderbox")) return State.BURN;

        return State.EXIT;
    }

    public void chopLogs() throws InterruptedException {
        log("Chopping " + selectedTree + " Trees");
        selectedAction = "Cutting";
        if (!myPlayer().isAnimating()) {
            RS2Object tree = objects.closest(selectedTree);
            if (tree != null && tree.interact("Chop down")) {
                new ConditionalSleep(5000) {
                    @Override
                    public boolean condition() { return (myPlayer().isAnimating()); }
                }.sleep();
            }
        } else if (myPlayer().isAnimating() && (!selectedTree.equalsIgnoreCase("Tree")) && mouse.isOnScreen()) { mouse.moveOutsideScreen(); }

        if (inventory.isFull()) { shouldChop = false; }
    }

    public void dropLogs() throws InterruptedException {
        log("Dropping " + selectedTree + " Logs");
        selectedAction = "Dropping";
        if (inventory.dropAll(typesOfLogs)) {
            shouldChop = true;
            new ConditionalSleep(1000) {
                @Override
                public boolean condition() { return !(inventory.isFull()); }
            }.sleep();
        }
    }

    public void burnLogs() throws InterruptedException {
        log("Burning " + selectedTree + " Logs");
        selectedAction = "Burning";

        Position pol = myPlayer().getPosition().translate(14, -2);
        for (int i = 1; i <= 14; i++) {
            if (map.canReach(pol) && map.realDistance(pol) > 0 && map.realDistance(pol) < 16) {
                WalkingEvent newPosition = new WalkingEvent(new Position(pol));
                execute(newPosition);
                break;
            } else {
                pol = myPlayer().getPosition().translate(14 - i, 0);
                if (i == 14 && !map.canReach(pol)) {
                    pol = myPlayer().getPosition().translate(14 - i, 1);
                    i = 0;
                }
            }
        }

        while (inventory.contains(typesOfLogs)) {
            log("looop");
            RS2Object groundCollision = objects.closest(groundObjects);
            while (groundCollision != null && groundCollision.getPosition().equals(myPlayer().getPosition())) {
                log("go elsewhere");
                sleep(2000);
                if (inventory.isItemSelected()) { inventory.deselectItem(); }

                // TODO: CHECK SURROUNDING AREA

                if(!(myPlayer().isAnimating()) && inventory.interact("Use", "Tinderbox")) {
                    if (inventory.isItemSelected() && inventory.interactWithNameThatContains("Use", "logs")) {
                        new ConditionalSleep(3000) {
                            @Override
                            public boolean condition() {
                                return (myPlayer().isAnimating());
                            }
                        }.sleep();
                    }
                }
            }
        }

        if (!(inventory.contains("Logs"))) {
            shouldChop = true;
            walkToStartingPosition();
        }
    }

    public void walkToStartingPosition() {
        selectedAction = "Walking";
        if (!(myPlayer().getPosition().equals(playerPosition))) {
            WalkingEvent newPosition = new WalkingEvent(new Position(playerPosition));
            execute(newPosition);
        }
    }

    @Override
    public void onStart() {

        log("Starting Script...");
        Menu gui = new Menu();
        gui.setVisible(true);

        getExperienceTracker().start(Skill.WOODCUTTING);
        startingLevelWC = getSkills().getStatic(Skill.WOODCUTTING);

        getExperienceTracker().start(Skill.FIREMAKING);
        startingLevelFM = getSkills().getStatic(Skill.FIREMAKING);

        playerPosition = myPlayer().getPosition();
        startTime = System.currentTimeMillis();
    }

    @Override
    public int onLoop() throws InterruptedException {

        if(inputAccepted) {

            if (myPlayer().isUnderAttack()) { walkToStartingPosition(); }

            switch (getState()) {

                case CHOP:
                    chopLogs();
                    break;

                case BURN:
                    burnLogs();
                    break;

                case DROP:
                    dropLogs();
                    break;

                case EXIT:
                    log("Missing some items, exiting script");
                    stop();
                    break;
            }
        }

        return random(300, 3000);
    }

    @Override
    public void onExit() { log("Thank You For Using My Script. Please Report All Bugs To Noidlox On OSBot"); }

    @Override
    public void onMessage(Message m) {

        if (inputAccepted) {
            if (m.getMessage().contains("You get some")) logsChopped++;
            if (m.getMessage().contains("The fire catches")) firesMade++;
            //if (m.getMessage().contains("You can't light")) movePosition();
            if (m.getMessage().contains("You need a Firemaking level of")) stop();
            if (m.getMessage().contains("You do not have an axe which you have")) stop();
            if (m.getMessage().contains("I can't reach")) walkToStartingPosition();
        }
    }

    @Override
    public void onPaint(Graphics2D g) {

        if(inputAccepted) {

            String currentTime = formatTime(System.currentTimeMillis() - startTime);
            testTime = (System.currentTimeMillis() - startTime) / 60000;
            g.setColor(Color.black);
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(7, 345, 506, 130);

            Font font = new Font("Times New Roman", Font.BOLD, 22);
            g.setFont(font);

            g.setColor(Color.white);
            g.drawString("Chop, Drop, & Burn | By Noidlox | v" + getVersion(), 17, 374);

            font = new Font("Arial", Font.PLAIN, 12);
            g.setFont(font);

            g.drawString("Time Ran: " + currentTime, 20, 398);
            g.drawString("Cutting " + selectedTree + " Trees", 140, 398);
            g.drawString("We Are " + selectedAction, 280, 398);
            //g.drawString("pAB Actions: " + pseudoAntiBanCounter, 380, 398);

            g.drawString("Logs Chopped: " + logsChopped, 20, 418);
            g.drawString("W.C. XP Gained: " + getExperienceTracker().getGainedXP(Skill.WOODCUTTING), 140, 418);
            g.drawString("Per Hour: " + getExperienceTracker().getGainedXPPerHour(Skill.WOODCUTTING) , 280, 418);
            g.drawString("Started At: " + startingLevelWC + " (" + getExperienceTracker().getGainedLevels(Skill.WOODCUTTING) + ")"
                    , 380, 418);

            g.drawString("Fires Made: " + firesMade, 20, 438);
            g.drawString("F.M. XP Gained: " + getExperienceTracker().getGainedXP(Skill.FIREMAKING), 140, 438);
            g.drawString("Per Hour: " + getExperienceTracker().getGainedXPPerHour(Skill.FIREMAKING) , 280, 438);
            g.drawString("Started At: " + startingLevelFM + " (" + getExperienceTracker().getGainedLevels(Skill.FIREMAKING) + ")"
                    , 380, 438);

            g.drawString("Please Report All Bugs To Me On OSBot | Feel Free To PM Me | Cheers, Noidlox", 20, 471);
        }
    }

    public final String formatTime(final long ms) {
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60; m %= 60; h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    public class Menu extends JFrame {

        private JPanel contentPane;

        public void main(String[] args) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        Menu frame = new Menu();
                        frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public Menu() {

            setResizable(false);
            setAlwaysOnTop(true);
            setTitle("G.U.I");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(100, 100, 170, 140);
            contentPane = new JPanel();
            contentPane.setForeground(Color.WHITE);
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            setContentPane(contentPane);
            contentPane.setLayout(null);

            JComboBox cmbSelectTree = new JComboBox();
            cmbSelectTree.setModel(new DefaultComboBoxModel(new String[] {"Tree", "Oak", "Willow", "Maple", "Mahogany", "Yew", "Magic"}));
            cmbSelectTree.setMaximumRowCount(7);
            cmbSelectTree.setBounds(20, 25, 130, 27);
            contentPane.add(cmbSelectTree);

            JButton btnStart = new JButton("Start");
            btnStart.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    selectedTree = (String)cmbSelectTree.getSelectedItem();

                    if (selectedTree.equalsIgnoreCase("Tree")) { selectedLog = "Logs"; }
                    else { selectedLog = selectedTree + "logs"; }

                    inputAccepted = true;
                    setVisible(false);

                }
            });
            btnStart.setBounds(20, 65, 130, 29);
            contentPane.add(btnStart);
        }
    }

}
