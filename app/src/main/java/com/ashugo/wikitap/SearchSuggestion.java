package com.ashugo.wikitap;

import java.util.List;

public class SearchSuggestion {
    private String searchText;
    private List<String> suggestions;
    private List<String> suggestionLinks;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public List<String> getSuggestionLinks() {
        return suggestionLinks;
    }

    public void setSuggestionLinks(List<String> suggestionLinks) {
        this.suggestionLinks = suggestionLinks;
    }
}
