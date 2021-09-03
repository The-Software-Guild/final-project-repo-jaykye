package jaykye.finalproject.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class SearchHistory {
    private int id;
    private LocalDateTime searchDatetime;
    private Venue venue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getSearchDatetime() {
        return searchDatetime;
    }

    public void setSearchDatetime(LocalDateTime searchDatetime) {
        this.searchDatetime = searchDatetime;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchHistory that = (SearchHistory) o;
        return id == that.id && Objects.equals(searchDatetime, that.searchDatetime) && Objects.equals(venue, that.venue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, searchDatetime, venue);
    }
}
