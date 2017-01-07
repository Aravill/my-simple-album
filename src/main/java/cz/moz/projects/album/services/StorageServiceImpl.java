package cz.moz.projects.album.services;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.h2.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;

import cz.moz.projects.album.app.AppConfiguration;
import cz.moz.projects.album.domain.AlbumImage;



@Service
public class StorageServiceImpl implements StorageService {

	@Autowired
	private AppConfiguration conf;
	
	@Override
	public File findFile(AlbumImage albumEntry) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(albumEntry.getDateTaken());
		
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		
		String path = conf.getRootDir() + File.separator + year + File.separator + month + File.separator + day + File.separator + albumEntry.getUuid();
		File file = new File(path);
		if(file.exists())
			return file;
		else {
			// TODO - ERROR - File was not found
			System.out.println("Nenalezen soubor " + file.getAbsolutePath());
			return null;
		}
	}
	
	@Override
	public AlbumImage storeFile(File file, String originalFileName) {
		String mimeType = null;
		try {
			mimeType = getMimeType(file);
		} catch (IOException e) {
			// TODO - WARN - nepodarilo se ziskat mimeType
		}

		Date created = null;
		try {
			created = getDateTakenOf(file);
		} catch (Exception e) {
			// TODO - WARN - chyba pri ziskavani metadat
		}

		if(created == null) {
			try {
				BasicFileAttributes attr = Files.readAttributes(Paths.get(file.getAbsolutePath()), BasicFileAttributes.class);
				Calendar cal = GregorianCalendar.getInstance();
				cal.setTimeInMillis(attr.creationTime().toMillis());
				created = cal.getTime();
			} catch (IOException e) {
				// TODO - WARN - nepodarilo se ziskat datum vytvoreni
				Calendar cal = new GregorianCalendar();
				cal.set(1985, 1, 1);
				created = cal.getTime();
			}
		}
		
		String storedFile = storeFile(file, created);

		AlbumImage aEntry = new AlbumImage();
		aEntry.setOriginalName(originalFileName);
		aEntry.setDateTaken(created);
		aEntry.setMimeType(mimeType);
		aEntry.setUuid(storedFile);

		return aEntry;
	}

	private String storeFile(File file, Date dateCreated) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(dateCreated);

		File root = new File(conf.getRootDir());
		if (!root.exists()) {
			// TODO - logger.error("Chyba konfigurace. Neexistujici adresar")
			return null;
		}

		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

		String fileStorePath = root.getAbsolutePath() + File.separator + year + File.separator + month + file.separator
				+ day;

		File fileStore = new File(fileStorePath);
		if (!fileStore.exists())
			if (!fileStore.mkdirs()) {
				// TODO - logger.error("Nepodarilo se vytvorit adresar " +
				// fileStorePath)
				return null;
			}

		UUID id = UUID.randomUUID();

		File entry = new File(fileStore + File.separator + id.toString());
		if (!entry.exists()) {
			try {
				entry.createNewFile();

				FileOutputStream fos = new FileOutputStream(entry);
				FileInputStream fis = new FileInputStream(file);

				IOUtils.copy(fis, fos);
				fis.close();
				fos.close();
				
				//saveScaledImage(entry.getAbsolutePath(), conf.getRootDir() + File.separator + "small" + File.separator + entry.getName());

				return id.toString();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;

	}

	private Date getDateTakenOf(File file) throws MetadataException, ImageProcessingException, IOException {

		Metadata metadata = ImageMetadataReader.readMetadata(file);
		Iterator<Directory> it = metadata.getDirectories().iterator();
		while(it.hasNext()) {
			Directory dir = it.next();
			Collection<Tag> tags = dir.getTags();
			for(Tag tag : tags) {
				System.out.println(tag.getTagName());
			}
		}
		
		Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
		
		if (directory != null)
			if (directory.containsTag(ExifDirectoryBase.TAG_DATETIME))
				return directory.getDate(ExifDirectoryBase.TAG_DATETIME);

		return null;

	}
	
/*	private ImageInformation getImageInformation(File file) throws MetadataException, ImageProcessingException, IOException {

		Metadata metadata = ImageMetadataReader.readMetadata(file);
		Iterator<Directory> it = metadata.getDirectories().iterator();
		while(it.hasNext()) {
			Directory dir = it.next();
			Collection<Tag> tags = dir.getTags();
			for(Tag tag : tags) {
				System.out.println(tag.getTagName());
			}
		}
		
		Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
		JpegDirectory jpegDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
		
		int orientation = 1;
		if (directory != null){
			if (directory.containsTag(ExifDirectoryBase.TAG_ORIENTATION)){
				orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
			}
		}
		int width = jpegDirectory.getImageWidth();
		int height = jpegDirectory.getImageHeight();
		ImageInformation imageInformation = new ImageInformation(orientation, width, height);
		return imageInformation;

	}*/

	private String getMimeType(File file) throws IOException {
		return Files.probeContentType(Paths.get(file.getAbsolutePath()));
	}
	
	@Override
	public AlbumImage storeFile(File file, String[] tags) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
/*	private  void saveScaledImage(String filePath, String outputFile){
	    try {

	        BufferedImage sourceImage = ImageIO.read(new File(filePath));
	        int width = sourceImage.getWidth();
	        int height = sourceImage.getHeight();

	        if(width>height){
	            float extraSize=    height-100;
	            float percentHight = (extraSize/height)*100;
	            float percentWidth = width - ((width/100)*percentHight);
	            BufferedImage img = new BufferedImage((int)percentWidth, 100, BufferedImage.TYPE_INT_RGB);
	            Image scaledImage = sourceImage.getScaledInstance((int)percentWidth, 100, Image.SCALE_SMOOTH);
	            img.createGraphics().drawImage(scaledImage, 0, 0, null);
	            BufferedImage img2 = new BufferedImage(100, 100 ,BufferedImage.TYPE_INT_RGB);
	            img2 = img.getSubimage((int)((percentWidth-100)/2), 0, 100, 100);

	            ImageIO.write(img2, "jpg", new File(outputFile));    
	        }else{
	            float extraSize=    width-100;
	            float percentWidth = (extraSize/width)*100;
	            float  percentHight = height - ((height/100)*percentWidth);
	            BufferedImage img = new BufferedImage(100, (int)percentHight, BufferedImage.TYPE_INT_RGB);
	            Image scaledImage = sourceImage.getScaledInstance(100,(int)percentHight, Image.SCALE_SMOOTH);
	            img.createGraphics().drawImage(scaledImage, 0, 0, null);
	            BufferedImage img2 = new BufferedImage(100, 100 ,BufferedImage.TYPE_INT_RGB);
	            img2 = img.getSubimage(0, (int)((percentHight-100)/2), 100, 100);

	            ImageIO.write(img2, "jpg", new File(outputFile));
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }*/

}
	

