package cz.moz.projects.album.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cz.moz.projects.album.domain.AlbumImage;

public interface ImagesRepository extends JpaRepository<AlbumImage, Integer>{

	AlbumImage findByUuid(String uuid);
	
	@Query("select distinct (year(dateTaken)) from #{#entityName}")
	Integer[] findAllYears();
	
	@Query("select distinct (month(dateTaken)) from #{#entityName} where year(dateTaken) = ?")
	Integer[] findMonths(Integer year);
	
	@Query("select distinct (day(dateTaken)) from #{#entityName} where year(dateTaken) = ?1 and month (dateTaken) =?2")
	Integer[] findDays(Integer year, Integer month);
}
