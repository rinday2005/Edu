package service;

import ArticalDAO.ArticleDAO;
import java.util.List;
import java.util.UUID;
import model.Article;

public class ArticleService {

    ArticleDAO dao = new ArticleDAO();

    public void create(Article a) {
        dao.create(a);
    }

    public int update(Article a) {
        return dao.update(a);
    }

    public int updateStatus(UUID articleId, String status) {
        return dao.updateStatus(articleId, status);
    }

    public int deleteById(UUID articleId) {
        return dao.deleteById(articleId);
    }

    public Article findById(UUID articleId) {
        return dao.findById(articleId);
    }

    public List<Article> findAll() {
        return dao.findAll();
    }

    public List<Article> findAllWithStats() {
        return dao.findAllWithStats();
    }

    public int increaseViewCount(UUID articleId, UUID viewerId, UUID authorId) {
        return dao.increaseViewCount(articleId, viewerId, authorId);
    }

}
