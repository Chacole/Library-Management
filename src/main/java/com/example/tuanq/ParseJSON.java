package com.example.tuanq;

import com.example.tuanq.admin.Documents;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseJSON {

    public List<Documents> parseJSON(String json) {
        List<Documents> documentList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray itemsArray = jsonObject.optJSONArray("items");

        if (itemsArray != null) {
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject item = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = item.optJSONObject("volumeInfo");

                // Lấy các trường dữ liệu từ JSON
                String title = volumeInfo.optString("title", "Can't find Title");
                String author = volumeInfo.optJSONArray("authors") != null
                        ? volumeInfo.getJSONArray("authors").optString(0)
                        : "Can't find Author";
                int yearPublished = parseYear(volumeInfo.optString("publishedDate", "0"));

                String type = volumeInfo.optString("printType", "Unknown Type");
                String image = extractImageLink(volumeInfo);

                Documents document = new Documents();
                document.setTitle(title);
                document.setAuthor(author);
                document.setYear(yearPublished);
                document.setType(type);
                document.setUrl(image);

                documentList.add(document);
            }
        }
        return documentList;
    }

    private int parseYear(String yearString) {
        try {
            String cleanedYear = yearString.replaceAll("[^\\d]", "");

            if (cleanedYear.length() >= 4) {
                return Integer.parseInt(cleanedYear.substring(0, 4));
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid year format: " + yearString);
        }
        return 0;
    }

    public String extractImageLink(JSONObject volumeInfo) {
        if (volumeInfo.has("imageLinks")) {
            return volumeInfo.getJSONObject("imageLinks").optString("thumbnail", "No Image Available");
        }
        return "No Image Available";
    }
}