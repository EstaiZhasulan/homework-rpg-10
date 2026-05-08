package com.narxoz.rpg.guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuildHall implements GuildMediator {

    public static final String TOPIC_SUPPLIES  = "supplies";
    public static final String TOPIC_SCOUTING  = "scouting";
    public static final String TOPIC_HEALING   = "healing";
    public static final String TOPIC_ORDERS    = "orders";
    public static final String TOPIC_EMERGENCY = "emergency";
    public static final String TOPIC_LORE      = "lore";

    private final Map<String, List<GuildMember>> membersByTopic = new HashMap<>();
    private int totalDispatches    = 0;
    private int totalNotifications = 0;

    @Override
    public void register(GuildMember member) {
        String role = member.getClass().getSimpleName();
        switch (role) {
            case "Quartermaster":
                addSubscriber(TOPIC_SUPPLIES,  member);
                addSubscriber(TOPIC_ORDERS,    member);
                addSubscriber(TOPIC_EMERGENCY, member);
                break;
            case "Scout":
                addSubscriber(TOPIC_SCOUTING,  member);
                addSubscriber(TOPIC_ORDERS,    member);
                addSubscriber(TOPIC_EMERGENCY, member);
                break;
            case "Healer":
                addSubscriber(TOPIC_HEALING,   member);
                addSubscriber(TOPIC_ORDERS,    member);
                addSubscriber(TOPIC_EMERGENCY, member);
                break;
            case "Captain":
                addSubscriber(TOPIC_SUPPLIES,  member);
                addSubscriber(TOPIC_SCOUTING,  member);
                addSubscriber(TOPIC_HEALING,   member);
                addSubscriber(TOPIC_ORDERS,    member);
                addSubscriber(TOPIC_EMERGENCY, member);
                break;
            case "Loremaster":
                addSubscriber(TOPIC_LORE,     member);
                addSubscriber(TOPIC_ORDERS,   member);
                addSubscriber(TOPIC_SCOUTING, member);
                break;
            default:
                addSubscriber(TOPIC_ORDERS,    member);
                addSubscriber(TOPIC_EMERGENCY, member);
                break;
        }
        System.out.println("[GuildHall] Registered: " + member.getName()
                + " (" + role + ")");
    }

    @Override
    public void dispatch(String topic, GuildMember from, String payload) {
        totalDispatches++;
        List<GuildMember> subscribers = subscribersFor(topic);
        System.out.println("[GuildHall] DISPATCH topic='" + topic
                + "' from=" + from.getName()
                + " payload=\"" + payload + "\"");
        int notified = 0;
        for (GuildMember member : subscribers) {
            if (member == from) continue;
            member.receive(topic, from, payload);
            notified++;
        }
        totalNotifications += notified;
        if (notified == 0) {
            System.out.println("[GuildHall]   (no subscribers for topic '"
                    + topic + "')");
        }
    }

    public int getTotalDispatches()    { return totalDispatches; }
    public int getTotalNotifications() { return totalNotifications; }

    protected void addSubscriber(String topic, GuildMember member) {
        membersByTopic.computeIfAbsent(topic, key -> new ArrayList<>()).add(member);
    }

    protected List<GuildMember> subscribersFor(String topic) {
        return membersByTopic.getOrDefault(topic, List.of());
    }
}