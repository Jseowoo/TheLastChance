package the.last.chance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import the.last.chance.dto.RequestBlogSearchDTO;
import the.last.chance.service.BlogService;

import java.util.Map;

@RestController
@RequestMapping(value = "/blog/*", method = RequestMethod.GET)
public class BlogController {

    private BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/search")
    public ResponseEntity searchBlogs(RequestBlogSearchDTO dto) {
        Map<String, Object> result = blogService.searchBlog(dto);
        return ResponseEntity.ok(result);
    }
}
