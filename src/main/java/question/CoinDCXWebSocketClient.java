package question;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class CoinDCXWebSocketClient extends WebSocketClient {

    double triggerPrice;

    public CoinDCXWebSocketClient(URI serverUri, double triggerPrice) {
        super(serverUri);
        this.triggerPrice=triggerPrice;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to CoinDCX WebSocket.");
        subscribeToTicker();
    }

    @Override
    public void onMessage(String message) {
        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
        handlePriceUpdate(jsonObject);
    }

    // Subscribe to ticker for a specific market(BTC/USDT)
    private void subscribeToTicker() {
        JsonObject subscribeMessage = new JsonObject();
        subscribeMessage.addProperty("channel", "ticker.BTCUSDT");
        subscribeMessage.addProperty("event", "subscribe");
        send(subscribeMessage.toString());
    }

    // Handle price update and check against trigger price
    private void handlePriceUpdate(JsonObject data) {
        double currentPrice = data.get("price").getAsDouble();
        System.out.println("Current Price: " + currentPrice);
        if (currentPrice >= triggerPrice) {
            System.out.println("Trigger price met or exceeded. Prepare to Sell an order.");
            prepareSellOrderPayload(currentPrice);
        }
        else{
            System.out.println("Trigger price met or fallen. Prepare to Buy an order.");
            prepareBuyOrderPayload(currentPrice);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from WebSocket: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("Error occurred: " + ex.getMessage());
    }

    private void prepareSellOrderPayload(double currentPrice) {

        String orderPayload = String.format(
                "{\"order_type\": \"sell\", \"quantity\": \"1\", \"price\": \"%f\", \"symbol\": \"BTCUSDT\"}",
                currentPrice
        );
        System.out.println("Order payload prepared: " + orderPayload);

    }
    private void prepareBuyOrderPayload(double currentPrice) {

        String orderPayload = String.format(
                "{\"order_type\": \"buy\", \"quantity\": \"1\", \"price\": \"%f\", \"symbol\": \"BTCUSDT\"}",
                currentPrice
        );
        System.out.println("Order payload prepared: " + orderPayload);

    }


}

