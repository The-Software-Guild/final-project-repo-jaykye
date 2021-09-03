package jaykye.finalproject.dao;

import jaykye.finalproject.model.SearchHistory;
import jaykye.finalproject.model.Venue;

import java.util.List;

public interface SearchHistoryDao {
    SearchHistory getSearchHistoryById(int id);
    List<SearchHistory> getAllSearchHistory();
    List<SearchHistory> getRecentSearchHistory(int limit);
    SearchHistory addSearchHistory(SearchHistory searchHistory);
    void updateSearchHistory(SearchHistory searchHistory);
    void deleteSearchHistoryById(int id);
}
