package com.cpjd.models.events;

import com.cpjd.sorting.Sortable;
import com.cpjd.sorting.SortingType;
import lombok.Data;

import java.io.Serializable;

/**
 * OPR, DPR, and CCWM for a single team
 * @since 1.0.0
 * @author Will Davies
 */
@Data
public class EventOPR extends Sortable<EventOPR> implements Serializable {
    /**
     * The team's key
     */
    private String teamKey;
    /**
     * The team's opr
     */
    private double opr;
    /**
     * The team's dpr
     */
    private double dpr;
    /**
     * The team's ccwm
     */
    private double ccwm;

    @Override
    public int sort(SortingType type, boolean ascending, EventOPR t2) {
        if(type == SortingType.DEFAULT || type == SortingType.RANK) {
            return Double.compare(opr, t2.getOpr());
        } else if(type == SortingType.TEAM) return teamKey.compareTo(t2.getTeamKey());

        throw new RuntimeException("Unsupported sort type for EventOPR.");
    }
}
