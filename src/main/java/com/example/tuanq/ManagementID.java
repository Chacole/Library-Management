package com.example.tuanq;

import java.util.*;

public class ManagementID {
    private List<Integer> availableDocIDs = new ArrayList<> ();
    private List<Integer> availableUserID = new ArrayList<> ();
    private static int DocID = 1;
    private static int UserID = 1;

    /**
     * enables every document have the only ID.
     */
    public int generateDocID() {
        if (!availableDocIDs.isEmpty()) {
            return availableDocIDs.remove(0);
        }
        return DocID++;
    }
    public void recycleDocID(int id) {
        availableDocIDs.add(id);
    }

    /**
     * enables every user have the only ID.
     */
    public int generateUserID() {
        if (!availableUserID.isEmpty()) {
            return availableUserID.remove(0);
        }
        return UserID++;
    }
    public void recycleUserID(int id) {
        availableUserID.add(id);
    }

}
