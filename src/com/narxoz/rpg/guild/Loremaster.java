package com.narxoz.rpg.guild;

public class Loremaster extends GuildMember {

    private int loreRecorded = 0;

    public Loremaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void publishLore(String payload) {
        System.out.println("[" + getName() + "] Publishing lore: " + payload);
        getMediator().dispatch(GuildHall.TOPIC_LORE, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case GuildHall.TOPIC_LORE:
                System.out.println("[" + getName() + "] Lore update from "
                        + from.getName() + ": archiving — " + payload);
                loreRecorded++;
                break;
            case GuildHall.TOPIC_SCOUTING:
                System.out.println("[" + getName() + "] Scout intel from "
                        + from.getName()
                        + ": cross-referencing ancient maps — " + payload);
                break;
            case GuildHall.TOPIC_ORDERS:
                System.out.println("[" + getName() + "] Order from "
                        + from.getName()
                        + ": researching quest background — " + payload);
                loreRecorded++;
                break;
            default:
                System.out.println("[" + getName() + "] Unhandled topic '"
                        + topic + "' from " + from.getName());
        }
    }

    public int getLoreRecorded() { return loreRecorded; }
}