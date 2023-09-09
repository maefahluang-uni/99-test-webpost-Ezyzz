package lab.webpost.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lab.webpost.domain.Post;

@RestController
public class PostController {

    private static Logger LOGGER = LoggerFactory.getLogger(PostController.class); 

    @Autowired
    private PostRepository postRepository;

    // TODO: getting post by id
    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> optConcert = postRepository.findById(id);

        // TODO: Handle the case when no entity is found
        if (!optConcert.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(optConcert.get());
    }

    // TODO: get all Posts
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = postRepository.findAll();
        return ResponseEntity.ok(posts);
    }

    // TODO: find by title
    @GetMapping("/posts/byTitle")
    public ResponseEntity<List<Post>> getPostByTitle(String title) {
        List<Post> posts = postRepository.findByTitle(title);
        return ResponseEntity.ok(posts);
    }

    // TODO: adding new post
    @PostMapping("/posts")
    public ResponseEntity<String> createConcert(@RequestBody Post post) { // add @ResponseBody

        // TODO save concert to database using repository
        postRepository.save(post);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/posts")
    public ResponseEntity<String> addPost(@RequestBody Post post) {
        if (!postRepository.existsById(post.getId())) {
            // return error message
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        postRepository.save(post);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // TODO: delete post by id
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        if (!postRepository.existsById(id)) {
            // return error message 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // TODO: delete all posts
    @DeleteMapping("/posts")
    public ResponseEntity<String> deleteAllPosts() {
        postRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
