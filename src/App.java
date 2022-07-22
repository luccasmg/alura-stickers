import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
   public static void main(String[] args) throws Exception {

        String url = "https://alura-filmes.herokuapp.com/conteudos";
        ExtratorConteudo extrator = new ExtratorConteudoImdb();
        
        //String url = "https://api.nasa.gov/planetary/apod?api_key=KMeIMPLoh1dgHnzGEtaYTBHbvsLtt5D5ndJgpaU4&start_date=2022-06-12&end_date=2022-06-14";
        //ExtratorConteudo extrator = new ExtratorConteudoNasa();

        var http = new ClienteHTTP();
        String json = http.buscaDados(url);
        
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var gerador = new GeradorStickers();
        
        for (int i = 0; i < 3; i++) {
            
            Conteudo conteudo = conteudos.get(i); 
            
            InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();
            String nomeArquivo = "saida/" + conteudo.getTitulo().replace(":","-") + ".png";

            gerador.cria(inputStream, nomeArquivo);

            System.out.println(conteudo.getTitulo());
            System.out.println();

        }
    }
}