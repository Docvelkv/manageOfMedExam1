package docvel.functionalDiagnostics.providers;

import docvel.functionalDiagnostics.model.entitys.RefForMedicalExam;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Component
public class ReferralsProvider {

    private final WebClient webClient;
    private DepartmentsProvider deptProvider;

    public ReferralsProvider(ReactorLoadBalancerExchangeFilterFunction loadBalancer) {
        webClient = WebClient.builder()
                .baseUrl("http://registry/registry")
                .filter(loadBalancer)
                .build();
    }

    public List<RefForMedicalExam> getRefByDeptAndByStatusOnCurDate(int status) {
        long deptId = deptProvider.getCurrentDeptId();
        return webClient.get()
                .uri("refOnCurDate/{deptId}/{status}", deptId, status)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<RefForMedicalExam>>() {})
                .block();
    }

    public Optional<RefForMedicalExam> getRefById(long refId){
        return webClient.get()
                .uri("/refById/{refId}", refId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(RefForMedicalExam.class)
                .blockOptional();
    }

    public void updateRef(long refId, int status){
        webClient.get()
                .uri("/updateRef/{refId}/{status}", refId, status)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
