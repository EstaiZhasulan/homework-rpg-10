package com.narxoz.rpg.guild;

public class Scout extends GuildMember {

    private int routesReported = 0;

    public Scout(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void reportRoute(String payload) {
        System.out.println("[" + getName() + "] Filing route report: " + payload);
        getMediator().dispatch(GuildHall.TOPIC_SCOUTING, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case GuildHall.TOPIC_ORDERS:
                System.out.println("[" + getName() + "] Order from "
                        + from.getName() + ": scouting new route — " + payload);
                routesReported++;
                break;
            case GuildHall.TOPIC_SCOUTING:
                System.out.println("[" + getName() + "] Intel from "
                        + from.getName() + ": updating map — " + payload);
                break;
            case GuildHall.TOPIC_EMERGENCY:
                System.out.println("[" + getName() + "] EMERGENCY from "
                        + from.getName() + ": scouting escape routes — " + payload);
                routesReported++;
                break;
            default:
                System.out.println("[" + getName() + "] Unhandled topic '"
                        + topic + "' from " + from.getName());
        }
    }

    public int getRoutesReported() { return routesReported; }
}