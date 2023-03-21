package the.last.chance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import the.last.chance.dto.RequestKakaoSearchDTO;
import the.last.chance.service.BlogService;

import java.util.Map;

@RestController
@RequestMapping(value = "/blog/*", method = RequestMethod.GET)
public class BlogController {

    private BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/kakao/search")
    public ResponseEntity searchBlogsByKakao(RequestKakaoSearchDTO dto) {
        Map<String, Object> result = blogService.searchBlog(dto);
        return ResponseEntity.ok(result);
    }

    //추후 카카오 API 이외에 새로운 검색 소스가 추가될 경우...
    //ex) searchBlogsByNaver, searchBlogsByGoogle ...
}
