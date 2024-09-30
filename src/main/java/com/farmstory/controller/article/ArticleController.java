package com.farmstory.controller.article;

import com.farmstory.dto.*;
import com.farmstory.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final EmailService emailService;
    private final FileService fileService;


    // @GetMapping("/")

    @GetMapping("/{cateGroup}/{cateName}")
    public String list(@PathVariable("cateGroup") String group,
                           @PathVariable("cateName") String cateName,
                           @RequestParam(value = "content", required = false) String content,
                           @RequestParam(value ="pg", defaultValue = "0") int pg,
                           @RequestParam(value="keyword",defaultValue = "",required = false) String keyword,
                           @RequestParam(value="type",defaultValue = "title",required = false) String type,
                           PageRequestDTO pageRequestDTO,
                           Model model){
        log.info("keyword : "+keyword);
        log.info("type : "+type);
        log.info("pageReqestDTO"+pageRequestDTO);
        log.info("cateGroup "+group+"cateName "+cateName);
        CateDTO cate = categoryService.selectCategory(group,cateName);

        log.info(cate);
        int cateNo = cate.getCateNo();
        pageRequestDTO.setCateNo(cateNo);
        log.info("cate : "+cate);
        //300번대 = croptalk ,  500대 = community
        List<ArticleDTO> articles = null;
        if ((cateNo >= 300 && cateNo < 400) || (cateNo >= 500 && cateNo < 600)) {

            PageResponseDTO pageResponseDTO = articleService.getAllArticles(pageRequestDTO,cateNo);

            log.info("start : "+pageResponseDTO.getStart());
            log.info("end : " +pageResponseDTO.getEnd());

            log.info(pageResponseDTO.getDtoList());
            model.addAttribute("pageResponseDTO",pageResponseDTO);


        }

        model.addAttribute("cate", cate);
        model.addAttribute("content", content);

        return "boardIndex";

    }


    //cs
    @GetMapping("/{uid}/community/cs")
    public String communityCsList(@PathVariable("uid") String uid, @RequestParam("content") String content,PageRequestDTO pageRequestDTO, Model model){
        CateDTO  cate = categoryService.selectCateNo(504);

        pageRequestDTO.setUid(uid);
        pageRequestDTO.setCateNo(504);

        PageResponseDTO pageResponseDTO = articleService.getAllArticles(pageRequestDTO,504);

        model.addAttribute("pageResponseDTO",pageResponseDTO);
        model.addAttribute("content", content);
        model.addAttribute("cate",cate);
        return "boardIndex";
    }

    @GetMapping("/admin/community/cs")
    public String adminCs( @RequestParam("content") String content,PageRequestDTO pageRequestDTO, Model model){
        CateDTO  cate = categoryService.selectCateNo(504);

        pageRequestDTO.setCateNo(504);

        PageResponseDTO pageResponseDTO = articleService.getAllArticles(pageRequestDTO,504);
        return null;
    }

    @GetMapping("/{cateGroup}/{cateName}/{articleNo}")
    public String view(@PathVariable("cateGroup") String group,
                       @PathVariable("cateName") String cateName,
                       @PathVariable("articleNo") int articleNo,
                       @RequestParam(value = "pg",defaultValue = "0") int pg,
                       @RequestParam(value = "content", required = false) String content,
                       Model model){

        log.info("pg : "+pg);

        CateDTO cate = categoryService.selectCategory(group,cateName);




       ArticleDTO articleDTO =  articleService.selectArticle(articleNo);
       log.info("articleDTO : "+articleDTO);
       model.addAttribute("pg",pg);
       model.addAttribute("cate", cate);
       model.addAttribute("content", content);
       model.addAttribute("article", articleDTO);
        return "boardIndex";
    }


    @PostMapping("/{cateNo}/writer")
    public String writer(@PathVariable("cateNo") int cateNo,ArticleDTO articleDTO,  @RequestParam("writer") String writer, HttpServletRequest req){
        log.info("noticeCate : "+articleDTO.getNoticeCate());
        CateDTO cate = categoryService.selectCateNo(cateNo);

        articleDTO.setCateNo(cate.getCateNo());
        articleDTO.setRegIp(req.getRemoteAddr());

        //파일 업로드
        List<FileDTO> uploadFiles = new ArrayList<>();
        if (articleDTO.getFiles() != null && !articleDTO.getFiles().isEmpty()) {
            uploadFiles = fileService.uploadFile(articleDTO); // Process file uploads
        }
        int ano;
        if(cate.getCateNo()==501 || articleDTO.getNoticeCate() != 0){
            articleDTO.setNotice(true);
            ano =articleService.insertArticle(articleDTO,cateNo,req);
        }else {
            ano = articleService.insertArticle(articleDTO, cateNo, req);
        }
        //글 저장
        //파일 저장
        if(!uploadFiles.isEmpty()){
            for(FileDTO fileDTO : uploadFiles){
                fileDTO.setAno(ano);
                int fno = fileService.insertFile(fileDTO);
            }
        }



        if(ano>0){
            return "redirect:/article/"+cate.getCateGroup()+"/"+cate.getCateName()+"?content=list";
        }
        return "redirect:/article/"+cate.getCateGroup()+"/"+cate.getCateName()+"?content=write&success=300";

    }

    @GetMapping("/delete/{cateNo}/{articleNo}")
    public String delete(@PathVariable("articleNo") int articleNo,
                         @PathVariable("cateNo") int cateNo,
                         @RequestParam("pg") int pg, Model model){

        CateDTO cate = categoryService.selectCateNo(cateNo);
        int result = articleService.deleteArticle(articleNo);
        if(result==1){
                return "redirect:/article/"+cate.getCateGroup()+"/"+cate.getCateName()+"?content=list";
        }

        return "redirect:/article/"+cate.getCateGroup()+"/"+cate.getCateName()+"/"+articleNo+"?content=view,pg="+pg;
    }









}