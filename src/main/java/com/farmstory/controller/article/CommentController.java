package com.farmstory.controller.article;


import com.farmstory.dto.CommentDTO;
import com.farmstory.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/comment/write")
    public ResponseEntity<CommentDTO> write(@RequestBody CommentDTO commentDTO, HttpServletRequest req) {

        String regip = req.getRemoteAddr();
        commentDTO.setRegip(regip);
        log.info(commentDTO);

        CommentDTO dto = commentService.insertComment(commentDTO);
        log.info("dto : "+dto);
        return ResponseEntity.ok().body(dto);

    }

    @GetMapping("/comment/delete")
    public String delete(@RequestParam int no, @RequestParam int pg, @RequestParam String uid) {

        commentService.selectCommentByuid(no, uid);

        commentService.deleteComment(no);
        return "redirect:/article/view?no="+no+"&pg="+pg;
    }


}
