package rose_atelier;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository
        extends JpaRepository<OrderHistory, Long> {
}