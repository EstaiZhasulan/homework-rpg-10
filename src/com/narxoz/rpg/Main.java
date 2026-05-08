package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.council.CouncilRunResult;
import com.narxoz.rpg.guild.*;
import com.narxoz.rpg.quest.*;

import java.util.Arrays;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 10 Demo: Iterator + Mediator ===\n");

        Hero erlan = new Hero("Erlan", 120, 80, 25, 10, 200);
        Hero aisha = new Hero("Aisha", 100, 150, 15,  8, 350);
        List<Hero> party = Arrays.asList(erlan, aisha);

        System.out.println("=== Party ===");
        for (Hero h : party) System.out.println("  " + h);

        QuestLog questLog = new QuestLog();
        questLog.add(new Quest("Hunt the Wyvern of Ashfeld",      QuestPriority.HIGH,   800, false));
        questLog.add(new Quest("Escort the Merchant Caravan",     QuestPriority.NORMAL, 300, false));
        questLog.add(new Quest("Clear the Cursed Ruins",          QuestPriority.URGENT, 1200, true));
        questLog.add(new Quest("Collect Herbs for the Healer",    QuestPriority.LOW,    100, false));
        questLog.add(new Quest("Investigate Missing Scouts",      QuestPriority.HIGH,   600, false));
        questLog.add(new Quest("Defeat the Bandit King",          QuestPriority.URGENT, 950, true));
        questLog.add(new Quest("Deliver Letter to the King",      QuestPriority.NORMAL, 200, false));

        System.out.println("\n=== Quest Log (" + questLog.size() + " quests) ===");
        System.out.println("  (internals hidden — accessed only via iterators)");

        GuildHall hall = new GuildHall();

        System.out.println("\n=== Registering Guild Members ===");
        Quartermaster quartermaster = new Quartermaster("Bolat",   hall);
        Scout         scout         = new Scout        ("Daniyar", hall);
        Healer        healer        = new Healer       ("Zarina",  hall);
        Captain       captain       = new Captain      ("Ruslan",  hall);
        Loremaster    loremaster    = new Loremaster   ("Aibek",   hall);  // OCP proof

        System.out.println("\n=== Pre-Council Briefing (Mediator demo) ===");
        scout.reportRoute("Northern pass is clear — no goblin activity");
        healer.prepareAid("20 healing potions packed for high-risk quests");
        quartermaster.requestSupplies("Need rope, torches, and antidotes for cursed ruins");
        loremaster.publishLore("Cursed Ruins contain a seal — breaking it triggers trap");

        CouncilEngine engine = new CouncilEngine();
        CouncilRunResult result = engine.runCouncil(party, questLog, hall);

        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║         WAR COUNCIL COMPLETE             ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf( "║  Quests traversed  : %-19d ║%n", result.getQuestsTraversed());
        System.out.printf( "║  Messages routed   : %-19d ║%n", result.getMessagesRouted());
        System.out.printf( "║  Members notified  : %-19d ║%n", result.getMembersNotified());
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println("\nRaw result: " + result);
    }
}
