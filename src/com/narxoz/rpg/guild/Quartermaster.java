package com.narxoz.rpg.guild;

public class Quartermaster extends GuildMember {

    private int suppliesDispatched = 0;

    public Quartermaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void requestSupplies(String payload) {
        System.out.println("[" + getName() + "] Requesting supplies: " + payload);
        getMediator().dispatch(GuildHall.TOPIC_SUPPLIES, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case GuildHall.TOPIC_ORDERS:
                System.out.println("[" + getName() + "] Order from "
                        + from.getName() + ": preparing gear — " + payload);
                suppliesDispatched++;
                break;
            case GuildHall.TOPIC_SUPPLIES:
                System.out.println("[" + getName() + "] Supply update from "
                        + from.getName() + ": acknowledged — " + payload);
                break;
            case GuildHall.TOPIC_EMERGENCY:
                System.out.println("[" + getName() + "] EMERGENCY from "
                        + from.getName() + ": rushing emergency rations — " + payload);
                suppliesDispatched++;
                break;
            default:
                System.out.println("[" + getName() + "] Unhandled topic '"
                        + topic + "' from " + from.getName());
        }
    }

    public int getSuppliesDispatched() { return suppliesDispatched; }
}