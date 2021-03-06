package kr.ac.ajou.lazybones.repos.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.ajou.lazybones.repos.jpa.entities.NodeEntity;
import kr.ac.ajou.lazybones.repos.jpa.entities.UserEntity;

public interface NodeRepository extends JpaRepository<NodeEntity, Long> {
	public <U extends NodeEntity> U save(NodeEntity node);

	public List<NodeEntity> findNodeEntitiesByOwner(UserEntity owner);

	public NodeEntity findById(Long id);

	public NodeEntity findBySerial(String serial);

	public void delete(NodeEntity u);
}
