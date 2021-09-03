package jaykye.finalproject.model;

import java.util.Objects;

public class Recommendation {
    private Venue venue;
    private String reason;
    private String tip;

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(venue, that.venue) && Objects.equals(reason, that.reason) && Objects.equals(tip, that.tip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(venue, reason, tip);
    }
}
