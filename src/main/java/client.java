import question.CoinDCXWebSocketClient;

import java.net.URI;
import java.util.Scanner;
public class client{
    public static void main(String[] args) {
        double triggerPrice;

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter trigger price: ");
            triggerPrice = scanner.nextDouble();

            //URI uri = new URI("wss://api.coindcx.com/exchange/v1/ws");
            URI uri = new URI("wss://stream.coindcx.com");


            CoinDCXWebSocketClient client = new CoinDCXWebSocketClient(uri, triggerPrice);
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
