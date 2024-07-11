package docvel.functionalDiagnostics.providers;

import docvel.functionalDiagnostics.model.entitys.Patient;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class PatientsProvider {

    private final WebClient webClient;

    public PatientsProvider(ReactorLoadBalancerExchangeFilterFunction loadBalancer){
        webClient = WebClient.builder()
                .baseUrl("http://registry/registry")
                .filter(loadBalancer)
                .build();
    }

    public List<Patient> getAllPats(){
        return webClient.get()
                .uri("/pats")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Patient>>() {})
                .block();
    }

    public Patient getPatById(long patId){
        return webClient.get()
                .uri("/patById/{patId}", patId)
                .retrieve()
                .bodyToMono(Patient.class)
                .block();
    }

    public List<Patient> getPatByName(String patName){
        return webClient.get()
                .uri("/patByName/{patName}", patName)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Patient>>() {})
                .block();
    }
}
