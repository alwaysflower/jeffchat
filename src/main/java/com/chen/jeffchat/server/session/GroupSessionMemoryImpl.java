package com.chen.jeffchat.server.session;

import io.netty.channel.Channel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GroupSessionMemoryImpl implements GroupSession{
    private Map<String, Group> groups = new ConcurrentHashMap<>();
    private Session session;
    @Override
    public Group createGroup(String groupId, Set<String> members) {
        if (groups.containsKey(groupId))return null;
        Group group = new Group(groupId, members);
        groups.put(groupId, group);
        return group;
    }

    @Override
    public Group joinGroup(String groupId, String memberId) {
        if (!groups.containsKey(groupId))return null;
        Group group = groups.get(groupId);
        group.addMember(memberId);
        return group;
    }

    @Override
    public Group removeMember(String groupId, String memberId) {
        if (!groups.containsKey(groupId))return null;
        Group group = groups.get(groupId);
        group.removeMember(memberId);
        return group;
    }

    @Override
    public void removeGroup(String groupId) {
        groups.remove(groupId);
    }

    @Override
    public Set<String> getMembers(String groupId) {
        if (!groups.containsKey(groupId))return null;
        return groups.get(groupId).getMembers();
    }

    @Override
    public Set<Channel> getMemberChannels(String groupId) {
        Set<Channel> channels = new HashSet<>();
        for (String member: groups.get(groupId).getMembers()){
            Channel channel = session.getChannel(member);
            if (channel != null){
                channels.add(channel);
            }else {
                return null;
            }
        }
        return channels;
    }
}
