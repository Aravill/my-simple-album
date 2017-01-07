package cz.moz.projects.album.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.moz.projects.album.domain.Tag;

public interface TagsRepository extends JpaRepository<Tag, Integer> {
	Tag findById(int id);
	Tag findByName(String name);
}
