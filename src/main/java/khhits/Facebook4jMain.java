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
//public class Facebook4jMain {
//    public static void main(String args[]) throws FacebookException {
//        ApplicationContext context = SpringApplication.run(Facebook4jMain.class, args);
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
//        String accessToken = "EAALCDAomCzwBAAs8aGG2Ki4BfZCdQ8DIqAJZBkYB1AM5XWEYth9HQ52ZC1rvREKfCx06F1q9tDw4UxtBZBC6GTBZAsGHQ5FCXDlgtxHGRAtZCpCeG7vEoLBLOyYM8tvl2o6D90Qjyl6K7ZAdSYMsaFrV7utVwTCEIcjnaWZBWzUx5cZAR6v3fO8oyyxwAkjB8wzUZD";
//        facebook.setOAuthAppId(appId, appSecret);
//        //facebook.setOAuthPermissions(commaSeparetedPermissions);
//        AccessToken extendedToken = facebook.extendTokenExpiration(accessToken);
//        facebook.setOAuthAccessToken(extendedToken);
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
//                        System.out.println("========> " + post);
////                if (post.getType() != null){
////                    if (!post.getType().trim().equals("video")) {
////                        break;
////                    }
////                }else{
////                    break;
////                }
//
//                khhits.models.Post postNita = new khhits.models.Post();
//                post.setPostId(post.getId());
//                post.setName(post.getName());
//                post.setPageId(2); // TODO: THIS ONE IS ALSO STATIC IN THE DATABASE
//                post.setLink(post.getLink() + "");
//                post.setMessage(post.getMessage());
//                post.setObjectId(post.getObjectId());
//                post.setCreatedTime(post.getCreatedTime());
//                post.setUpdatedTime(post.getUpdatedTime());
//                post.setStatusType(post.getStatusType());
//                post.setType(post.getType());
////                postNita.setShareCount(post.getSharesCount() == null ? 0 : post.getSharesCount());
//
//                postRepository.save(postNita);
//
//                List<Comment> fullComments = new ArrayList<>();
//                try {
//                    // get first few comments using getComments from post
//                    PagableList<Comment> comments = post.getComments();
//                    Paging<Comment> paging;
//                    do {
//                        for (Comment comment : comments) {
//                            System.out.println("====> " + comment.getLikeCount());
//                            fullComments.add(comment);
//                            kh.com.penhchet.models_nita.Comment commentNita = new kh.com.penhchet.models_nita.Comment();
//                            commentNita.setPostId(postNita.getPostId());
//                            commentNita.setCommentCount(comment.getCommentCount() == null ? 0 : comment.getCommentCount());
//                            commentNita.setUserId(comment.getFrom().getId());
//                            commentNita.setUsername(comment.getFrom().getName());
//                            commentNita.setCommentId(comment.getId());
//                            commentNita.setMessage(comment.getMessage());
//                            commentNita.setLikeCount(comment.getLikeCount());
//                            commentNita.setCreatedTime(comment.getCreatedTime());
//
//                            commentRepository.save(commentNita);
//                        }
//
//                        // get next page
//                        // NOTE: somehow few comments will not be included.
//                        // however, this won't affect much on our research
//                        paging = comments.getPaging();
//                    } while ((paging != null) &&
//                            ((comments = facebook.fetchNext(paging)) != null));
//
//                } catch (FacebookException ex) {
//                    ex.printStackTrace();
//                }
//
////                List<Like> fullLikes = new ArrayList<>();
////                try {
////                    // get first few likes using getLikes from post
////                    PagableList<Like> likes = post.getLikes();
////                    Paging<Like> paging;
////                    do {
////                        for (Like like: likes) {
////                            System.out.println("LIKE ==> " + like);
////                            kh.com.penhchet.models_nita.Like likeNita = new kh.com.penhchet.models_nita.Like();
////                            likeNita.setPostId(post.getId());
////                            likeNita.setUserId(like.getId());
////                            likeNita.setLikeId(like.getId());
////                            likeNita.setName(like.getName());
////                            likeRepository.save(likeNita);
////                            fullLikes.add(like);
////                        }
////
////                        // get next page
////                        // NOTE: somehow few likes will not be included.
////                        // however, this won't affect much on our research
////                        //TODO: TO GET THE NEXT PAGING
////                        paging = likes.getPaging();
////                        //TODO: TO GET NEXT LIKES WITH THE NEXT PAGE
////                    } while ((paging != null) && ((likes = facebook.fetchNext(paging)) != null));
////
////                } catch (FacebookException ex) {
////                    ex.printStackTrace();
////                }
//            }
//            //TODO: TO GET THE NEXT PAGING
//            pagingPosts = posts.getPaging();
//            //TODO: TO CHECK IF THE PAGING IS NOT NULL GET THE POSTS WITH NEXT PAGE
//        }while ((pagingPosts != null) && ((posts = facebook.fetchNext(pagingPosts)) != null));
//    }
//
//}
//
