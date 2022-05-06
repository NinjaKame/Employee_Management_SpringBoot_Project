package demo.repository;


import demo.entity.Thanos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThanosRepository extends JpaRepository<Thanos,Integer> {

}
