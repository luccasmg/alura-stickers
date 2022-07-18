import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

    public static final String ANSI_BLUE_BOLD = "\u001b[1m \u001b[48;2;81;119;201m";
    public static final String ANSI_WHITE_BOLD = "\u001b[1m \u001b[38;2;52;86;158m \u001b[48;2;252;252;252m";
    public static final String ANSI_RESET = "\u001b[m";
    public static final String ANSI_ITALIC = "\u001b[3m";
    
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // Fazer conexão HTTP e buscar as Top 250 Series
        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Extrair somente os dados necessários (Tíitulo, poster, rating)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeSeries = parser.parse(body);
        
        // Exibir e manipular os dados
        for (Map<String,String> serie : listaDeSeries) {
            
            var imDbRating = serie.get("imDbRating");
            double stars = Double.parseDouble(imDbRating);
            
            //var stars = Math.round(rating);
           
            var starsRating = "\u2B50";

            if (stars >= 3 & stars < 6){
                starsRating ="\u2B50 \u2B50";
            }

            if (stars >= 6 & stars < 8){
                starsRating = "\u2B50 \u2B50 \u2B50";
            }

            if (stars >= 8 & stars < 9){
                starsRating = "\u2B50 \u2B50 \u2B50 \u2B50";
            }

            if (stars >= 9){
                starsRating = "\u2B50 \u2B50 \u2B50 \u2B50 \u2B50";
            }
            
            System.out.println("Título:" + ANSI_BLUE_BOLD + " " + serie.get("title") + " " + ANSI_RESET + " " + starsRating);
            System.out.println("Poster: " + ANSI_ITALIC + serie.get("image") + ANSI_RESET);
            System.out.println(ANSI_WHITE_BOLD + " " + "Nota: " + (serie.get("imDbRating")) + " " + ANSI_RESET);
            System.out.println();
        }
    }
}