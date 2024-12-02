import com.google.gson.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class API {
    private static final String API_KEY = "AIzaSyB5dvT2OSJZxqpMKgS7gEw-5GN_uKpQAPs";

    public API() {
    }

    // Lấy thông tin từ URL API
    public static String searchDocument(String title, String author, String ISBN, String category, String language) {
        try {
            StringBuilder queryBuilder = new StringBuilder("q=");
            boolean isFirst = true;

            if (!title.isEmpty()) {
                queryBuilder.append(isFirst ? "" : "+").append("intitle:").append(URLEncoder.encode(title, "UTF-8"));
                isFirst = false;
            }
            if (!author.isEmpty()) {
                queryBuilder.append(isFirst ? "" : "+").append("inauthor:").append(URLEncoder.encode(author, "UTF-8"));
                isFirst = false;
            }
            if (!ISBN.isEmpty()) {
                queryBuilder.append(isFirst ? "" : "+").append("isbn:").append(URLEncoder.encode(ISBN, "UTF-8"));
                isFirst = false;
            }
            if (!category.isEmpty()) {
                queryBuilder.append(isFirst ? "" : "+").append("subject:").append(URLEncoder.encode(category, "UTF-8"));
                isFirst = false;
            }
            if (!language.isEmpty()) {
                queryBuilder.append(isFirst ? "" : "+").append("inlanguage:").append(URLEncoder.encode(language, "UTF-8"));
            }

            // Tạo URL cho truy vấn tìm kiếm
            String urlString = "https://www.googleapis.com/books/v1/volumes?" + queryBuilder.toString() + "&key=" + API_KEY;
            System.out.println("Request URL: " + urlString);

            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Kiểm tra mã phản hồi HTTP
            int responseCode = con.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) { // Không thành công
                System.out.println("Lỗi khi kết nối API: Mã phản hồi HTTP " + responseCode);
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            // Copy y nguyên những gì API trả về
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            con.disconnect();

            return response.toString();
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm kiếm tài liệu: " + e.getMessage());
        }
        return null;
    }

    // Giải mã JSON
    private static ArrayList<Document> parseDocument(String jsonResponse) {
        ArrayList<Document> result = new ArrayList<>();
        if (jsonResponse == null || jsonResponse.isEmpty()) {
            System.out.println("Phản hồi JSON rỗng hoặc null, không thể phân tích.");
            return null;
        }

        try {
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            if (jsonObject.has("items") && jsonObject.get("items").isJsonArray()) {
                JsonArray items = jsonObject.getAsJsonArray("items");
                for (int i = 0; i < Math.min(10, items.size()); i++) {
                    JsonObject item = items.get(i).getAsJsonObject();
                    JsonObject volumeInfo = item.has("volumeInfo") && item.get("volumeInfo").isJsonObject() ?
                            item.getAsJsonObject("volumeInfo") : new JsonObject();

                    String title = volumeInfo.has("title") && !volumeInfo.get("title").isJsonNull() ?
                            volumeInfo.get("title").getAsString() : "Unknown Title";

                    String author = "N/A";
                    if (volumeInfo.has("authors") && volumeInfo.get("authors").isJsonArray()) {
                        JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
                        if (authorsArray.size() > 0) {
                            author = authorsArray.get(0).getAsString();
                        }
                    }

                    String publisher = volumeInfo.has("publisher") && !volumeInfo.get("publisher").isJsonNull() ?
                            volumeInfo.get("publisher").getAsString() : "N/A";

                    int yearPublished = 0;
                    if (volumeInfo.has("publishedDate")) {
                        try {
                            yearPublished = Integer.parseInt(volumeInfo.get("publishedDate").getAsString().substring(0, 4));
                        } catch (NumberFormatException | IndexOutOfBoundsException e) {
                            yearPublished = 0;
                        }
                    }

                    String category = "N/A";
                    if (volumeInfo.has("categories") && volumeInfo.get("categories").isJsonArray()) {
                        JsonArray categoriesArray = volumeInfo.getAsJsonArray("categories");
                        if (categoriesArray.size() > 0) {
                            category = categoriesArray.get(0).getAsString();
                        }
                    }

                    String ISBN = "N/A";
                    if (volumeInfo.has("industryIdentifiers") && volumeInfo.get("industryIdentifiers").isJsonArray()) {
                        JsonArray identifiersArray = volumeInfo.getAsJsonArray("industryIdentifiers");
                        for (JsonElement identifier : identifiersArray) {
                            JsonObject identifierObj = identifier.getAsJsonObject();
                            if (identifierObj.has("type") && identifierObj.get("type").getAsString().equals("ISBN_13")) {
                                ISBN = identifierObj.get("identifier").getAsString();
                                break;
                            }
                        }
                    }

                    String language = volumeInfo.has("language") && !volumeInfo.get("language").isJsonNull() ?
                            volumeInfo.get("language").getAsString() : "N/A";

                    Book book = new Book(ISBN, "1", title, author, publisher, yearPublished, 1, category, language);
                    result.add(book);
                }
            } else {
                
                System.out.println("Không có mục nào trong phản hồi JSON.");
                return null;
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Lỗi khi phân tích JSON: " + e.getMessage());
        }
        return result;
    }

    // Lưu kết quả trả về từ API.
    public static ArrayList<Document> getArrayDocument(String title, String author, String ISBN, String category, String language) {
        String jsonResponse = searchDocument(title, author, ISBN, category, language);
        if (jsonResponse == null) {
            System.out.println("Không thể lấy dữ liệu từ API.");
            return null;
        }
        return parseDocument(jsonResponse);
    }

    public static void main(String[] args) {
        System.out.println(searchDocument("The Song of Ice and Fire", "Martin", "", "", "English"));
    }
}
