package com.moqi.bean.spel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author moqi
 * @date 4/4/22 18:59
 */
public class Society {

    private String name;

    public static final String ADVISORS = "advisors";
    public static final String PRESIDENT = "president";

    private List<Inventor> members = new ArrayList<>();
    private Map<?, ?> officers = new HashMap<>();

    public List<Inventor> getMembers() {
        return members;
    }

    public Map<?, ?> getOfficers() {
        return officers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMember(String name) {
        for (Inventor inventor : members) {
            if (inventor.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
