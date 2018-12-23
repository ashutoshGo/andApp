package com.ashugo.wikitap;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class AutocompleteAdapter extends ArrayAdapter implements Filterable {
    private List<SearchSuggestion> mCountry;
    private String COUNTRY_URL = "https://en.wikipedia.org/w/api.php?action=opensearch&format=json&formatversion=2&search=%s&namespace=0&limit=10&suggest=true";
//            "https://restcountries.eu/rest/v2/name/";
//            "http://demo.hackerkernel.com/jqueryui_autocomplete_dropdown_with_php_and_json/country.php?term=";

    public AutocompleteAdapter(Context context, int resource) {
        super(context, resource);
        mCountry = new ArrayList<>();
    }

    public List<SearchSuggestion> getmCountry() {
        return mCountry;
    }

    public void setmCountry(List<SearchSuggestion> mCountry) {
        this.mCountry = mCountry;
    }

    @Override
    public int getCount() {
        return mCountry.get(0).getSuggestions().size();
    }

    @Override
    public String getItem(int position) {
        return mCountry.get(0).getSuggestions().get(position);
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint != null){
                    try{
                        //get data from the web
                        String term = constraint.toString();
                        mCountry = new SearchOnWiki().execute(term).get();
                    }catch (Exception e){
                        Log.d("HUS","EXCEPTION "+e);
                    }
                    filterResults.values = mCountry.get(0).getSuggestions();
                    filterResults.count = mCountry.get(0).getSuggestions().size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results != null && results.count > 0){
                    notifyDataSetChanged();
                }else{
                    notifyDataSetInvalidated();
                }
            }
        };

        return myFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.auto_complete_layout,parent,false);

        //get Country
        String contry = mCountry.get(0).getSuggestions().get(position);

        TextView countryName = (TextView) view.findViewById(R.id.countryName);

        countryName.setText(contry);

        return view;
    }

    //download mCountry list
    private class SearchOnWiki extends AsyncTask<String, String, List<SearchSuggestion>>{

        @Override
        protected List<SearchSuggestion> doInBackground(String... params) {
            try {
                //Create a new COUNTRY SEARCH url Ex "search.php?term=india"
                String NEW_URL = String.format(COUNTRY_URL, params[0]);//COUNTRY_URL + URLEncoder.encode(params[0],"UTF-8");
                Log.d("HUS", "JSON RESPONSE URL " + NEW_URL);

                URL url = new URL(NEW_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null){
                    sb.append(line).append("\n");
                }

                //parse JSON and store it in the list
                String jsonString =  sb.toString();
                List<SearchSuggestion> countryList = new ArrayList<>();

                JSONArray jsonArray = new JSONArray(jsonString);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jo = jsonArray.getJSONObject(i);
                    //store the country name
//                    List<SearchSuggestion> country = new ArrayList<>();
                    SearchSuggestion searchSuggestion = new SearchSuggestion();
                searchSuggestion.setSearchText(jsonArray.get(0).toString());
                searchSuggestion.setSuggestions(asList(jsonArray.get(1).toString().split("\\s*[^a-zA-Z]+\\s*")));
                searchSuggestion.setSuggestionLinks(asList(jsonArray.get(3).toString().split(",")));
//                country.add(searchSuggestion);

                    countryList.add(searchSuggestion);
//                }

                //return the countryList
                return countryList;

            } catch (Exception e) {
                Log.d("HUS", "EXCEPTION " + e);
                return null;
            }
        }
    }
}