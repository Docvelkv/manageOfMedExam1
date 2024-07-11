package docvel.functionalDiagnostics.providers;

import docvel.functionalDiagnostics.exceptionsHandling.IllegalParameter;
import docvel.functionalDiagnostics.model.entitys.Department;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class DepartmentsProvider {

    private final WebClient webClient;

    public DepartmentsProvider(ReactorLoadBalancerExchangeFilterFunction loadBalancer) {
        this.webClient = WebClient.builder()
                .baseUrl("http://registry/registry")
                .filter(loadBalancer)
                .build();
    }

    public List<Department> getAllDepartment(){
        return webClient.get()
                .uri("/depts")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Department>>() {})
                .block();
    }

    public long getCurrentDeptId(){
        Department dept = getAllDepartment().stream()
                .filter(department -> department.getDeptName().equals("Функциональная диагностика"))
                .findFirst().orElseThrow(
                        () -> new NoSuchElementException("Такого отделения не существует"));
        return dept.getDeptId();
    }

    public Department getDeptById(long deptId){
        return webClient.get()
                .uri("/deptById/{deptId}", deptId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        error -> Mono.error(new IllegalParameter(String
                                .format("Отделения с id %d не найдено", deptId))))
                .bodyToMono(Department.class)
                .block();
    }

    public Map<Integer, String> getAllExamsOfDept(long deptId){
        return webClient.get()
                .uri("/getAllExams/{deptId}", deptId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<Integer, String>>() {})
                .block();
    }

    public String getExamById(long deptId, Integer examId){
        return getAllExamsOfDept(deptId).get(examId);
    }
}
