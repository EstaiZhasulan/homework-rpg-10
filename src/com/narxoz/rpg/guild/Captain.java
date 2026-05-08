package com.narxoz.rpg.guild;

public class Captain extends GuildMember {

    private int ordersIssued = 0;

    public Captain(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void issueOrder(String payload) {
        System.out.println("[" + getName() + "] Issuing order: " + payload);
        getMediator().dispatch(GuildHall.TOPIC_ORDERS, this, payload);
        ordersIssued++;
    }

    public void declareEmergency(String payload) {
        System.out.println("[" + getName() + "] DECLARING EMERGENCY: " + payload);
        getMediator().dispatch(GuildHall.TOPIC_EMERGENCY, this, payload);
        ordersIssued++;
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case GuildHall.TOPIC_SUPPLIES:
                System.out.println("[" + getName() + "] Supply report from "
                        + from.getName() + ": logged — " + payload);
                break;
            case GuildHall.TOPIC_SCOUTING:
                System.out.println("[" + getName() + "] Scout report from "
                        + from.getName() + ": updating battle plan — " + payload);
                break;
            case GuildHall.TOPIC_HEALING:
                System.out.println("[" + getName() + "] Medic report from "
                        + from.getName() + ": noted — " + payload);
                break;
            case GuildHall.TOPIC_ORDERS:
                System.out.println("[" + getName() + "] Order acknowledged from "
                        + from.getName() + ": standing by — " + payload);
                break;
            case GuildHall.TOPIC_EMERGENCY:
                System.out.println("[" + getName() + "] Emergency from "
                        + from.getName() + ": mobilising all units — " + payload);
                break;
            default:
                System.out.println("[" + getName() + "] Unhandled topic '"
                        + topic + "' from " + from.getName());
        }
    }

    public int getOrdersIssued() { return ordersIssued; }
}