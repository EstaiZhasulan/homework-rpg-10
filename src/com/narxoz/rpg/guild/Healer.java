package com.narxoz.rpg.guild;

public class Healer extends GuildMember {

    private int aidsPrepared = 0;

    public Healer(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void prepareAid(String payload) {
        System.out.println("[" + getName() + "] Preparing aid: " + payload);
        getMediator().dispatch(GuildHall.TOPIC_HEALING, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case GuildHall.TOPIC_ORDERS:
                System.out.println("[" + getName() + "] Order from "
                        + from.getName() + ": readying potions — " + payload);
                aidsPrepared++;
                break;
            case GuildHall.TOPIC_HEALING:
                System.out.println("[" + getName() + "] Healing request from "
                        + from.getName() + ": triage underway — " + payload);
                aidsPrepared++;
                break;
            case GuildHall.TOPIC_EMERGENCY:
                System.out.println("[" + getName() + "] EMERGENCY from "
                        + from.getName() + ": deploying all healing supplies — " + payload);
                aidsPrepared++;
                break;
            default:
                System.out.println("[" + getName() + "] Unhandled topic '"
                        + topic + "' from " + from.getName());
        }
    }

    public int getAidsPrepared() { return aidsPrepared; }
}