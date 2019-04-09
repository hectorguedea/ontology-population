package Twitter4j;

//import java.util.List;
import java.util.Scanner;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Twitter API. Interación de Twitter API usando la librería Twitter4j,
 * GitHub https://github.com/hectorguedea/ontology-population
 */
public class App {

  /**
   * Metodo principal
   *
   * @param args
   * @throws TwitterException
   */
  public static void main(String[] args) throws TwitterException {

	// TwitterFactory objeto que provee una instancia del objeto Twitter. 
    // El método getInstance(), es el objeto de Twitter con el API consumer.
    // Es el método para interactuar con Twitter API.
   
    
    Twitter twitter = new TwitterFactory().getInstance();

    boolean Salir = false;
    do {
      // Menu principal
      Scanner input = new Scanner(System.in);
      System.out.print("\n--------------------"
          + "\nT. Timeline \nB. Búsqueda \nE. Enviar un Tweet"
          + "\n--------------------"
          + "\nS. Salir"
          + "\n--------------------\n> ");
      String choice = input.nextLine();

      try {
        
        // Timeline
        if (choice.equalsIgnoreCase("T")) {

          // Mostrar nombre de usuario 
          User user = twitter.verifyCredentials();
          System.out.println("\n Timeline de @" + user.getScreenName() + ":");

          
          
          // Mostrar los tweets recientes del Timeline.
          for (Status status : twitter.getHomeTimeline()) {
            System.out.println("\n@" + status.getUser().getScreenName()
                + ": " + status.getText());
          }

        }
        
        // Búsquedas
        else if (choice.equalsIgnoreCase("B")) {

          // Pregunta al usuario.
          System.out.print("\nBúsqueda: ");
          String searchStr = input.nextLine();

          // Crea un objeto Query
          Query query = new Query(searchStr);

          // Envía la petición al API para executar la búsqueda con el query 
          QueryResult result = twitter.search(query);

          // Muestra el número de resultados
          System.out.println(" Cantidad de tweet: " + result.getCount() );
          System.out.println( "Limitante: " + result.getRateLimitStatus() );
          
          for (Status status : result.getTweets()) {
            System.out.println("\n@" + status.getUser().getName() + ": "
                + status.getText());
          }

        }
        
        // Enviar un Tweet
        else if (choice.equalsIgnoreCase("E")) {

          boolean esMuyLargo = false;
          String tweet;
          do {
            // Pregunta al usuario sobre el tweeet
            System.out.print("\nTweet: ");
            tweet = input.nextLine();

            // Resuelve si el tamaño del tweet es mayor
            if (tweet.length() > 280) {
              System.out.println("Muy largo! Sobrepasas los 280 caractéres.");
              esMuyLargo = true;
            }
          } while (esMuyLargo == true);

          // Manda una petición al API rpara crear un tweet
          Status status = twitter.updateStatus(tweet);
          System.out.println("Tu tweet se envió! : \"" + status.getText() + "\"");

        }
        
       
        // Salir
        else if (choice.equalsIgnoreCase("S")) {

          Salir = true;

        }
        
        // Mala elección
        else {

          System.out.println("Opción invalida");
        }

      } catch (IllegalStateException ise) {
        System.out.println(ise.getMessage());
      }

    } while (Salir == false);
  }
}