package com.codewithdurgesh.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.Payloads.PostDto;
import com.codewithdurgesh.blog.controller.PostResponse;
import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.exception.ResourceNotFoundException;
import com.codewithdurgesh.blog.repositories.CategoryRepo;
import com.codewithdurgesh.blog.repositories.PostRepo;
import com.codewithdurgesh.blog.repositories.UserRepo;
import com.codewithdurgesh.blog.service.PostService;
@Service
public class PostServiceImp implements PostService {
	
   @Autowired
	private PostRepo postRepo;
   
   @Autowired
   private ModelMapper modelMapper;
   
   @Autowired
   private UserRepo userRepo;
   
   @Autowired
   private CategoryRepo categoryRepo;
   
  
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId)
	{
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "category Id", categoryId));
		Post post =this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost=this.postRepo.save(post);
		
		return this.modelMapper.map(newPost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) 
	{
		Post post =this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		 post.setTitle(postDto.getTitle());
		 post.setContent(postDto.getContent());
		 post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost,PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) 
	{
		Post post =this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		 this.postRepo.delete(post);
		
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) 
	{
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
	
	    PageRequest p= PageRequest.of(pageNumber, pageSize,sort);
		Page<Post>pagePost =this.postRepo.findAll(p);
		List<Post>allPosts=pagePost.getContent();
		
		List<PostDto>postDtos=allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse=  new PostResponse();
		
		postResponse .setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post =this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) 
	{
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "category Id", categoryId));
		List<Post>posts=this.postRepo.findByCategory(cat);
		List<PostDto> postdtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postdtos; 
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId)
	{
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
		List<Post>posts = this .postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto>searchByTitle(String keyword) {
		List<Post> posts = this.postRepo.searchByTitle(keyword);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPost() {
		// TODO Auto-generated method stub
		return null;
	}

}
