package com.farmstory.controller.article;


import com.farmstory.dto.CateDTO;
import com.farmstory.dto.CommentDTO;
import com.farmstory.service.CategoryService;
import com.farmstory.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Log4j2
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CategoryService categoryService;


    @PostMapping("/comment/write")
    public ResponseEntity<CommentDTO> write(@RequestBody CommentDTO commentDTO,

                                            HttpServletRequest req) {

        String regip = req.getRemoteAddr();
        commentDTO.setRegip(regip);
        log.info(commentDTO);

        CommentDTO dto = commentService.insertComment(commentDTO);
        log.info("dto : "+dto);
        return ResponseEntity.ok().body(dto);

    }

    @GetMapping("/comment/delete/{cateNo}")
    public String delete(@PathVariable("cateNo") int cateNo,
            @RequestParam int no, @RequestParam int pg, @RequestParam("writer") String writer) {

        CateDTO cate = categoryService.selectCateNo(cateNo);

        int parentNo=  commentService.deleteComment(no);
     if(parentNo > 0 ){
         return "redirect:/article/"+cate.getCateGroup()+"/"+cate.getCateName()+"/"+parentNo+"?content=view&pg="+pg;

     }
        return "redirect:/article/"+cate.getCateGroup()+"/"+cate.getCateName()+"/"+parentNo+"?content=view&success=false&pg="+pg;

    }


}
