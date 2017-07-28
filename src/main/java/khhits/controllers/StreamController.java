package khhits.controllers;

import facebook4j.*;
import facebook4j.Comment;
import facebook4j.Post;
import facebook4j.auth.AccessToken;


import khhits.models.Page;
import khhits.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saksonita1 on 7/20/17.
 */

@Controller
public class StreamController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PageRepository pageRepository;


    @RequestMapping("login")
    public String hello() {
        return "login";
    }

    @RequestMapping(value = "/frontView/home")
    public String goToFrontEnd() {
        return "/frontView/home";
    }

    @RequestMapping(value = "admin/streamed_posts")
    public String goToAdmin() {
        return "admin/streamed_posts";
    }

    @RequestMapping(value = "admin/streaming_page")
    public String getStreamingPage() {
        return "admin/streaming_page";
    }

    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String getPage( Model model ) {
        model.addAttribute("page", new Page()); //assume SomeBean has a property called datePlanted
        return "/admin/streaming_page";
        //TODO: getvalue from input
    }

    @RequestMapping(value = "/addPage", method = RequestMethod.POST)
    public String showPage( @RequestParam("page_id") String page_id, @RequestParam("page_title") String page_title ) {
        khhits.models.Page pageModel = new khhits.models.Page();

        pageModel.setPageId(page_id);
        pageModel.setPageTitle(page_title);
//        pageModel.setId(pageModel.getId());
        pageRepository.save(pageModel);
        System.out.println("insert sucess");
        return "/admin/streaming_page";

    }
//
//    @RequestMapping(value = "/getFacebookPost", method = RequestMethod.GET)
//    public String getFacebookPost( Model model ) {
//        List<Page> page = new ArrayList<>();
//        model.addAttribute("page", page);
//        Iterable<Page> pages = CrudRepository.findAll();
//        model.addAttribute("tests", pages);
//
//        return "getFacebookPost";
//    }

    @RequestMapping(value = "/getFacebookPost", method = RequestMethod.POST)
    public String getFacebookPage( @Valid Page page, BindingResult bindingResult, Model model ) {
        if (bindingResult.hasErrors()) {
            return "/admin/streaming_page";
        }

        return "redirect:result";
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String showAllPosts( Model model ) {
        model.addAttribute("pages", pageRepository.findAll());
        return "result";
    }

    //TODO: We can use the Scheduler in the Spring it can allow us to run our code 1 week a time. that's good,,

    //TODO: It will run on Monday at 7:00:00 AM it is okay>>>>
    @Scheduled(cron = "0 0 7 * * MON")
    //@Scheduled(fixedRate = 1000)

    public void facebookAPI() throws FacebookException {
        System.out.println("FacebookAPI on Monday at 7:00 AM");

        Facebook facebook = new FacebookFactory().getInstance();

        // I ADD YOU TO MY APPLICATION WITH VERSION 2.3 I DON'T WHY YOUR APPLICATION VERSION 2.9 CANNOT READ THE COMMENTS AND LIKES
        //1. APP_ID YOU NEED TO CHANGE TOO
        String appId = "776306919082812";
        //1. APP_SECRET YOU NEED TO CHANGE TOO
        String appSecret = "b2e74d95f3f04769aba7e94c9cc3096d";
        //1. ACCESS_TOKEN YOU NEED TO CHANGE
        // THAT'S ALL WHEN YOUR ACCESS_TOKEN HAS BEEN EXPIRED YOU CAN TAKE THE NEW ONE
        // ON THIS URL ==> https://developers.facebook.com/tools/explorer/776306919082812
        // IF YOU RUNNING DON'T STOP IT THE DURACTION CAN BE UNTIL 60 DAYS IF YOU STOP IT, IT CAN ONLY 1 HOUR
        String accessToken = "EAALCDAomCzwBAEbAj2Rt3yyOQNwbs1U9IdFP7H6FZAojnjXsuOInH1fb0nYpu7JIZCilZCiCt5n7OeQ7vIpFhb1G66dcXViXKGQ7sYb9wbW1DBU3AK8FeF9iGb6CUmB02viAZAvP0KqN62AVfQ8AeTffJdmlOQoPDZCvL9X2ZBDT8n1GbOZBnJDRR55ZCF449DgZD";
        facebook.setOAuthAppId(appId, appSecret);
        //facebook.setOAuthPermissions(commaSeparetedPermissions);
        AccessToken extendedToken = facebook.extendTokenExpiration(accessToken);
        facebook.setOAuthAccessToken(extendedToken);

        //TODO: TO GET POSTS FROM WHICH PAGE NOT YET DYNAMIC
        // IF YOU WANT IT TO DYNAMIC YOU NEED TO READ THIS INFORMATION FROM THE DATABASE
        // THAT'S ALL ANY QUESTIONS
        // what kind of information ==> PAGE THAT YOU WANT TO GET DATA FROM

        PagableList<Post> posts = facebook.getPosts("RHM.Production"); // TODO: THIS IS THE PAGE ID
        Paging<Post> pagingPosts;
        do {
            for (Post post : posts) {
                System.out.println("========> " + post);
//                if (post.getType() != null){
//                    if (!post.getType().trim().equals("video")) {
//                        break;
//                    }
//                }else{
//                    break;
//                }

                khhits.models.Post postNita = new khhits.models.Post();
                postNita.setPostId(post.getId());
                postNita.setName(post.getName());
                postNita.setPageId(2); // TODO: THIS ONE IS ALSO STATIC IN THE DATABASE
                postNita.setMessage(post.getMessage());
                postNita.setObjectId(post.getObjectId());
                postNita.setUpdatedTime(post.getUpdatedTime());
                postNita.setStatusType(post.getStatusType());
                postNita.setType(post.getType());
//                postNita.setShareCount(post.getSharesCount() == null ? 0 : post.getSharesCount());

                postRepository.save(postNita);

                List<Comment> fullComments = new ArrayList<>();
                try {
                    // get first few comments using getComments from post
                    PagableList<Comment> comments = post.getComments();
                    Paging<Comment> paging;
                    do {
                        for (Comment comment : comments) {
                            System.out.println("====> " + comment.getLikeCount());
                            fullComments.add(comment);
                            khhits.models.Comment commentNita = new khhits.models.Comment();
                            commentNita.setPostId(postNita.getPostId());
                            commentNita.setCommentCount(comment.getCommentCount() == null ? 0 : comment.getCommentCount());
                            commentNita.setUserId(comment.getFrom().getId());
                            commentNita.setUsername(comment.getFrom().getName());
                            commentNita.setCommentId(comment.getId());
                            commentNita.setMessage(comment.getMessage());
                            commentNita.setLikeCount(comment.getLikeCount());
                            commentNita.setCreatedTime(comment.getCreatedTime());

                            commentRepository.save(commentNita);
                        }

                        // get next page
                        // NOTE: somehow few comments will not be included.
                        // however, this won't affect much on our research
                        paging = comments.getPaging();
                    } while ((paging != null) && ((comments = facebook.fetchNext(paging)) != null));

                } catch (FacebookException ex) {
                    ex.printStackTrace();
                }

//                List<Like> fullLikes = new ArrayList<>();
//                try {
//                    // get first few likes using getLikes from post
//                    PagableList<Like> likes = post.getLikes();
//                    Paging<Like> paging;
//                    do {
//                        for (Like like: likes) {
//                            System.out.println("LIKE ==> " + like);
//                            khhits.models.Like likeNita = new khhits.models.Like();
//                            likeNita.setPostId(post.getId());
//                            likeNita.setUserId(like.getId());
//                            likeNita.setLikeId(like.getId());
//                            likeNita.setName(like.getName());
//                            likeRepository.save(likeNita);
//                            fullLikes.add(like);
//                        }
//
//                        // get next page
//                        // NOTE: somehow few likes will not be included.
//                        // however, this won't affect much on our research
//                        //TODO: TO GET THE NEXT PAGING
//                        paging = likes.getPaging();
//                        //TODO: TO GET NEXT LIKES WITH THE NEXT PAGE
//                    } while ((paging != null) && ((likes = facebook.fetchNext(paging)) != null));
//
//                } catch (FacebookException ex) {
//                    ex.printStackTrace();
//                }
            }
            //TODO: TO GET THE NEXT PAGING
            pagingPosts = posts.getPaging();
            //TODO: TO CHECK IF THE PAGING IS NOT NULL GET THE POSTS WITH NEXT PAGE
        } while ((pagingPosts != null) && ((posts = facebook.fetchNext(pagingPosts)) != null));
    }
}
