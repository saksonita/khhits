//package khhits;
//
//import facebook4j.*;
//import facebook4j.auth.AccessToken;
//import khhits.repositories.CommentRepository;
//import khhits.repositories.LikeRepository;
//import khhits.repositories.PostRepository;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ApplicationContext;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by HP1 on 5/3/2017.
// */
//@SpringBootApplication
//public class Facebook4jMainSundayProduction {
//    public static void main(String args[]) throws FacebookException {
//        ApplicationContext context = SpringApplication.run(Facebook4jMainSundayProduction.class, args);
//
//        PostRepository postRepository = (PostRepository) context.getBean("postRepository");
//        CommentRepository commentRepository = (CommentRepository)context.getBean("commentRepository");
//        LikeRepository likeRepository = (LikeRepository) context.getBean("likeRepository");
//
//        Facebook facebook = new FacebookFactory().getInstance();
//
//        // I ADD YOU TO MY APPLICATION WITH VERSION 2.3 I DON'T WHY YOUR APPLICATION VERSION 2.9 CANNOT READ THE COMMENTS AND LIKES
//        //1. APP_ID YOU NEED TO CHANGE TOO
//        String appId = "776306919082812";
//        //1. APP_SECRET YOU NEED TO CHANGE TOO
//        String appSecret = "b2e74d95f3f04769aba7e94c9cc3096d";
//        //1. ACCESS_TOKEN YOU NEED TO CHANGE
//        // THAT'S ALL WHEN YOUR ACCESS_TOKEN HAS BEEN EXPIRED YOU CAN TAKE THE NEW ONE
//        // ON THIS URL ==> https://developers.facebook.com/tools/explorer/776306919082812
//        // IF YOU RUNNING DON'T STOP IT THE DURACTION CAN BE UNTIL 60 DAYS IF YOU STOP IT, IT CAN ONLY 1 HOUR
//        String accessToken = "EAALCDAomCzwBAPPCv5l896zOzrcBlN3HHnftm2e1THTsC1xg8mYynjUFd8x7IJFUaaZClWBmxRb9Ld3CKwDFEHHPIQWHVryFZBrqJZAP9BbZARhFWKgmQNfxeq2WA3YIg3C9t0dspaZBsjJDfWMTdFvERmntIJjCj5h9ckKJpiZBC6R4Pum2mRJY6AMqWYYuIZD";
//        facebook.setOAuthAppId(appId, appSecret);
//        //facebook.setOAuthPermissions(commaSeparetedPermissions);
//        AccessToken extendedToken = facebook.extendTokenExpiration(accessToken);
//        facebook.setOAuthAccessToken(extendedToken);
//
//        //TODO: TO GET ALL PAGES
//        //    : LOOP EACH PAGES
//
//        //TODO: TO GET POSTS FROM WHICH PAGE NOT YET DYNAMIC
//        // IF YOU WANT IT TO DYNAMIC YOU NEED TO READ THIS INFORMATION FROM THE DATABASE
//        // THAT'S ALL ANY QUESTIONS
//        // what kind of information ==> PAGE THAT YOU WANT TO GET DATA FROM
//        // okay!
//        PagableList<Post> posts = facebook.getPosts("RHM.Production"); // TODO: THIS IS THE PAGE ID
//                Paging<Post> pagingPosts;
//                do {
//                    for (Post post : posts) {
//                        if(postRepository.count(post.getId())>0){
//                            System.out.println("Post Id ==> " + post.getId() + " is exist.");
//                            continue;
//                        }
//                        System.out.println("========> " + post);
//                        kh.com.penhchet.models_nita.Post postNita = new kh.com.penhchet.models_nita.Post();
//                        postNita.setPostId(post.getId());
//                        postNita.setName(post.getName());
//                        postNita.setPageId(4); // TODO: THIS ONE IS ALSO STATIC IN THE DATABASE
//                        postNita.setLink(post.getLink() + "");
//                        postNita.setMessage(post.getMessage());
//                        postNita.setObjectId(post.getObjectId());
//                        postNita.setCreatedTime(post.getCreatedTime());
//                        postNita.setUpdatedTime(post.getUpdatedTime());
//                        postNita.setStatusType(post.getStatusType());
//                        postNita.setType(post.getType());
//                      //  postNita.setShareCount(post.getSharesCount() == null ? 0 : post.getSharesCount());
//
//                        postRepository.save(postNita);
//
//                        List<Comment> fullComments = new ArrayList<>();
//                        try {
//                            // get first few comments using getComments from post
//                            PagableList<Comment> comments = post.getComments();
//                            Paging<Comment> paging;
//                            do {
//                                for (Comment comment : comments) {
//                                    System.out.println("====> " + comment.getLikeCount());
//                                    fullComments.add(comment);
//                                    kh.com.penhchet.models_nita.Comment commentNita = new kh.com.penhchet.models_nita.Comment();
//                                    commentNita.setPostId(postNita.getPostId());
//                                    commentNita.setCommentCount(comment.getCommentCount() == null ? 0 : comment.getCommentCount());
//                                    commentNita.setUserId(comment.getFrom().getId());
//                                    commentNita.setUsername(comment.getFrom().getName());
//                                    commentNita.setCommentId(comment.getId());
//                                    commentNita.setMessage(comment.getMessage());
//                                    commentNita.setLikeCount(comment.getLikeCount());
//                                    commentNita.setCreatedTime(comment.getCreatedTime());
//
//                                    commentRepository.save(commentNita);
//                                }
//
//                                // get next page
//                                // NOTE: somehow few comments will not be included.
//                                // however, this won't affect much on our research
//                                paging = comments.getPaging();
//                            } while ((paging != null) &&
//                                    ((comments = facebook.fetchNext(paging)) != null));
//
//                        } catch (FacebookException ex) {
//                            ex.printStackTrace();
//                        }
//
//                List<Like> fullLikes = new ArrayList<>();
//                try {
//                    // get first few likes using getLikes from post
//                    PagableList<Like> likes = post.getLikes();
//                    Paging<Like> paging;
//                    do {
//                        for (Like like: likes) {
//                            System.out.println("LIKE ==> " + like);
//                            kh.com.penhchet.models_nita.Like likeNita = new kh.com.penhchet.models_nita.Like();
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
//                        paging = likes.getPaging();
//                    } while ((paging != null) &&
//                            ((likes = facebook.fetchNext(paging)) != null));
//
//                } catch (FacebookException ex) {
//                    ex.printStackTrace();
//                }
//            }
//            pagingPosts = posts.getPaging();
//        }while ((pagingPosts != null) &&
//                ((posts = facebook.fetchNext(pagingPosts)) != null));
//    }
//
//}
//
