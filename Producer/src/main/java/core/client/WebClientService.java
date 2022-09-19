package core.client;

import lombok.Getter;
import core.config.WebClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import javax.annotation.PostConstruct;

@Component
@Getter
public class WebClientService {

  @Autowired private WebClientConfig webClientConfig;

  private WebClient webClient;

  @PostConstruct
  private void initClient() {
    webClient =
        WebClient.builder()
            .baseUrl(webClientConfig.getBaseUri())
            .defaultHeader("key", webClientConfig.getApiKey())
            .build();
  }

  public Object[] sampleApiCall_LatestTransaction() {
    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/LatestTransactions")
                    .queryParam("issuer", "TSLA")
                    .queryParam("owner", 1494730)
                    .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(Object[].class)
        .log()
        .block();
  }

  public Object sampleApiCall_FinancialFactTrend() {

    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/FinancialFactTrend/")
                    .queryParam("id", "PG")
                    .queryParam("label", 0)
                    .queryParam("period", 0)
                    .queryParam("after", "20170101")
                    .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(Object.class)
        .log()
        .block();
  }
}
