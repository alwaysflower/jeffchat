package com.chen.jeffchat.server.session;
import io.netty.channel.Channel;

import java.util.Set;

public interface GroupSession {
    Group createGroup(String groupId, Set<String> members);

    Group joinGroup(String groupId, String memberId);

    Group removeMember(String groupId, String memberId);

    void removeGroup(String groupId);

    Set<String> getMembers(String groupId);

    Set<Channel> getMemberChannels(String groupId);
}
