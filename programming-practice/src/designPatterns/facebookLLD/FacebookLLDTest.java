package designPatterns.facebookLLD;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;

public class FacebookLLDTest {
    public static void main(String[] args) {


    }
}

interface PostContent {}
class TextContent implements PostContent {
    String content;
}

class ImageContent implements PostContent {
    byte[] image;
}

final class Post {
    UUID postId;
    UUID authorId;
    PostContent postContent;
    long createdDateTs;

    public Post(UUID postId, UUID authorId, PostContent postContent, long createdDateTs) {
        this.postId = postId;
        this.authorId = authorId;
        this.postContent = postContent;
        this.createdDateTs = createdDateTs;
    }
}

interface PostRepository {
    void save(Post post);
    Post get(UUID postId);
}

class PostService implements PostRepository {
    private final Map<UUID, Post> postDB = new ConcurrentHashMap<>();

    @Override
    public void save(Post post) {
        postDB.put(post.postId, post);
    }

    @Override
    public Post get(UUID postId) {
        return postDB.get(postId);
    }
}

interface FanoutPost {
    void fanout(UUID authorId, UUID postId);
}

class FanoutService implements FanoutPost {
    private NewsFeed newsFeed;
    private FollowerRepository followerRepository;

    public FanoutService(FollowerRepository followerRepository, NewsFeed newsFeed) {
        this.followerRepository = followerRepository;
        this.newsFeed = newsFeed;
    }

    @Override
    public void fanout(UUID authorId, UUID postId) {

        Set<UUID> followerIds = followerRepository.getFollowers(authorId);
        CompletableFuture.runAsync(() -> {
            followerIds.forEach(follower -> {
                newsFeed.addPost(follower, new FeedItem(postId, System.currentTimeMillis()));
            });
        });
    }
}

class FeedItem {
    UUID postId;
    long createdDateTs;

    public FeedItem(UUID postId, long createdDateTs) {
        this.postId = postId;
        this.createdDateTs = createdDateTs;
    }
}

interface NewsFeed {
    void addPost(UUID userId, FeedItem feedItem);
    List<FeedItem> getFeed(UUID userId, int limit);
}

class NewsFeedService implements NewsFeed {
    private final ConcurrentHashMap<UUID, Deque<FeedItem>> feedsDb = new ConcurrentHashMap<>();

    @Override
    public void addPost(UUID userId, FeedItem feedItem) {
        feedsDb.computeIfAbsent(userId, k -> new ConcurrentLinkedDeque<>()).add(feedItem);
    }

    @Override
    public List<FeedItem> getFeed(UUID userId, int limit) {
        return feedsDb.get(userId)
                        .stream()
                .sorted(Comparator.comparing(f -> f.createdDateTs, Comparator.reverseOrder()))
                .limit(limit)
                .toList();
    }
}

interface FollowerRepository {
    void addFollower(UUID userId, UUID followerId);
    Set<UUID> getFollowers(UUID userId);
}

class FollowerService implements FollowerRepository {
    private final Map<UUID, Set<UUID>> followers = new ConcurrentHashMap<>();

    @Override
    public void addFollower(UUID userId, UUID followerId) {
        followers.computeIfAbsent(userId, k -> ConcurrentHashMap.newKeySet())
                .add(followerId);
    }

    @Override
    public Set<UUID> getFollowers(UUID userId) {
        return Collections.unmodifiableSet(followers.getOrDefault(userId, new HashSet<>()));
    }
}


