package com.chen.jeffchat.server.session;

import java.util.Set;

public class Group {
    private String groupId;
    private Set<String> members;
    Group(String groupId, Set<String> members){
        this.groupId = groupId;
        this.members = members;
    }
    public Set<String> getMembers() {
        return members;
    }

    public String getGroupId() {
        return groupId;
    }

    public void addMember(String member){
        members.add(member);
    }

    public void removeMember(String member){
        members.remove(member);
    }
}
