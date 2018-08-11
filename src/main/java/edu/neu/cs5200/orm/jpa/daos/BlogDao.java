package edu.neu.cs5200.orm.jpa.daos;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Blog;
import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.repositories.BlogRepository;
import edu.neu.cs5200.orm.jpa.repositories.PersonRepository;

@Component
public class BlogDao {
	@Autowired
	BlogRepository blogRepo;
	
	@Autowired
	DoctorDao docDao;
	
	@Autowired
	PersonRepository personRepository;
	

	
	public Blog findBlogById(int id) {
		 Optional<Blog> blRes = blogRepo.findById(id);
		 if (blRes.isPresent())
			 return blRes.get();
		 return null;
	}
	
	public List<Blog> findAllBlogs() {
		return (List<Blog>)blogRepo.findAll();
	}
	
	public Blog createBlog(int id, Blog b) {
		Doctor d = docDao.findDoctorbyId(id);
		if (d != null) {
			Blog bl = new Blog();
			bl.setTitle(b.getTitle());
			bl.setContent(b.getContent());
			bl.setDoctor(d);
			return blogRepo.save(bl);
		}
		return null;
		
	}
	
	public Blog updateBlog(int id, Blog b) {
		Optional<Blog> blog = blogRepo.findById(id);
		if (blog.isPresent()) {
			Blog bl = blog.get();
			bl.setTitle(b.getTitle());
			bl.setContent(b.getContent());
			return blogRepo.save(bl);
		}
		return null;
	}
	
	public void deleteBlog(int id) {
		Optional<Blog> blog = blogRepo.findById(id);
		if (blog.isPresent()) {
			this.removeLikesMappingIfBlogDeleted(blog.get().getId());
			blogRepo.deleteById(blog.get().getId());
		}
	}
	
	public void deleteAll() {
		List<Blog> blogs = (List<Blog>)blogRepo.findAll();
		
		for(Blog b : blogs) {
			this.removeLikesMappingIfBlogDeleted(b.getId());
		}
		blogRepo.deleteAll();
	}
	
	
	public void removeLikesMappingIfBlogDeleted(int bid) {
		Blog b = this.findBlogById(bid);
		if (b != null && b.getPeopleLiked()!= null) {
			List<Person> likedPpl = b.getPeopleLiked();
			for(Person ppl: likedPpl) {
				Iterator<Blog> blogIt = ppl.getBlogpostsLiked().iterator();
				while(blogIt.hasNext()) {
					Blog temp = blogIt.next();
					if(temp.getId() == b.getId()) {
						blogIt.remove();
						personRepository.save(ppl);
						break;
					}
				}
			}
			
		}
	}
	
	

	
	
}
