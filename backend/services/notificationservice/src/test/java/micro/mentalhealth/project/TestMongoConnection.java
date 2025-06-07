package micro.mentalhealth.project;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import micro.mentalhealth.project.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestMongoConnection {

    private final NotificationRepository repository;

    @PostConstruct
    public void testConnection() {
        long count = repository.count();
        System.out.println("✅ Connexion MongoDB OK. Nombre de notifications : "  +count);
    }
}
